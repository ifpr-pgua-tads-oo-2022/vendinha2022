package ifpr.pgua.eic.vendinha2022.model.repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.mysql.cj.xdevapi.PreparableStatement;

import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
import ifpr.pgua.eic.vendinha2022.model.entities.Produto;
import ifpr.pgua.eic.vendinha2022.model.entities.Venda;
import ifpr.pgua.eic.vendinha2022.model.results.FailResult;
import ifpr.pgua.eic.vendinha2022.model.results.Result;
import ifpr.pgua.eic.vendinha2022.model.results.SuccessResult;

public class GerenciadorLoja {
    

    private List<Cliente> clientes;
    private List<Produto> produtos;
    private List<Venda> vendas;
    private Venda venda;

    public GerenciadorLoja(){
        clientes = new ArrayList<>();
        produtos = new ArrayList<>();
        vendas = new ArrayList<>();
    }

    public void geraFakes(){
        clientes.add(new Cliente("Zé", "000.111.222.333-44", "ze@teste.com", "123-4567"));
        clientes.add(new Cliente("Maria", "111.111.222.333-44", "maria@teste.com", "123-4567"));
        clientes.add(new Cliente("Chico", "222.111.222.333-44", "chico@teste.com", "123-4567"));
        
    }


    public Result adicionarCliente(String nome, String cpf, String email, String telefone){

        Optional<Cliente> busca = clientes.stream().filter((cli)->cli.getCpf().equals(cpf)).findFirst();
        
        if(busca.isPresent()){
            return Result.fail("Cliente já cadastrado!");
        }



        try{
            //criando uma conexão
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app","root",""); 
            //wagnerweinert.com.br:3306/tads21_SEUNOME

            //preparando o comando sql
            PreparedStatement pstm = con.prepareStatement("INSERT INTO clientes(nome,cpf,email,telefone) VALUES (?,?,?,?)");
            
            //ajustando os parâmetros do comando
            pstm.setString(1, nome);
            pstm.setString(2,cpf);
            pstm.setString(3,email);
            pstm.setString(4,telefone);

            pstm.execute();

            pstm.close();
            con.close();

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return Result.fail(e.getMessage());
        }

        Cliente cliente = new Cliente(nome,cpf,email,telefone);
        clientes.add(cliente);

        return Result.success("Cliente cadastrado com sucesso!");
    }

    public Result atualizarCliente(String cpf, String novoEmail, String novoTelefone){
        Optional<Cliente> busca = clientes.stream().filter((cli)->cli.getCpf().equals(cpf)).findFirst();
        
        if(busca.isPresent()){
            Cliente cliente = busca.get();
            cliente.setEmail(novoEmail);
            cliente.setTelefone(novoTelefone);

            return Result.success("Cliente atualizado com sucesso!");
        }
        return Result.fail("Cliente não encontrado!");
    }

    public List<Cliente> getClientes(){
        clientes.clear();
        try{
            //criando uma conexão
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app","root",""); 
            
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM clientes");

            ResultSet rs = pstm.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String email = rs.getString("email");
                String telefone = rs.getString("telefone");

                Cliente c = new Cliente(id,nome, cpf, email, telefone);
                clientes.add(c);
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }

        return Collections.unmodifiableList(clientes);
    }

    public Result adicionarProduto(String nome, String descricao, double valor, double quantidade){

        Optional<Produto> busca = produtos.stream().filter((prod)->prod.getNome().equals(nome)).findFirst();

        if(busca.isPresent()){
            return Result.fail("Produto já cadastrado!");
        }

        Produto produto = new Produto(nome,descricao,valor,quantidade);
        produtos.add(produto);

        return Result.success("Produto cadastrado com sucesso!");

    }

    public List<Produto> getProdutos(){
        return Collections.unmodifiableList(produtos);
    }

    public Venda getVendaAtual(){
        return venda;
    }


    public Result iniciarVenda(Cliente cliente){
        if(venda != null){
            return Result.fail("Não foi possível iniciar uma nova venda, já existe uma inicida!");
        }

        venda = new Venda(cliente,LocalDateTime.now());

        return Result.success("Venda iniciada!");
    }

    public Result adicionarProdutoVenda(Produto produto, double quantidade){

        if(venda == null){
            return Result.fail("Venda não iniciada!");
        }

        venda.adicionarProduto(produto, quantidade);

        return Result.success("Produto adicionado!");
    }

    public Result removerProdutoVenda(Produto produto, double quantidade){
        if(venda == null){
            return Result.fail("Venda não iniciada!");
        }

        if(venda.removerProduto(produto, quantidade)){
            return Result.success("Quantidade removida!");
        }

        return Result.fail("Produto não encontrado!");
    }

    public Result inserirDescontoVenda(double desconto){
        if(venda == null){
            return Result.fail("Venda não iniciada!");
        }

        venda.setDesconto(desconto);
        return Result.success("Desconto registrado!");
    }

    public Result finalizarVenda(){
        if(venda == null){
            return Result.fail("Venda não iniciada!");
        }

        this.vendas.add(venda);
        venda = null;

        return Result.success("Venda finalizada com sucesso!");

    }

    public List<Venda> getVendas(){
        return Collections.unmodifiableList(vendas);
    }

    public void salvar(){

        Gson gson = new Gson();

        try(FileWriter fout = new FileWriter("loja.json"); BufferedWriter bout = new BufferedWriter(fout)){

            bout.write(gson.toJson(this));
        }catch(IOException e ){
            System.out.println("Problema ao salvar arquivo!");
        }
        
    }

    public void carregar(){

        Gson gson = new Gson();

        try(FileReader fin = new FileReader("loja.json"); BufferedReader bin = new BufferedReader(fin)){

            GerenciadorLoja temp = gson.fromJson(bin, GerenciadorLoja.class);

            this.clientes.addAll(temp.getClientes());
            this.produtos.addAll(temp.getProdutos());
            this.vendas.addAll(temp.getVendas());

        }catch(IOException e ){
            System.out.println("Problema ao salvar arquivo!");
        }
    }




}
