package ifpr.pgua.eic.vendinha2022.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ifpr.pgua.eic.vendinha2022.model.FabricaConexoes;
import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public class JDBCClienteDAO implements ClienteDAO{

    private FabricaConexoes fabricaConexoes;

    public JDBCClienteDAO(FabricaConexoes fabricaConexoes){
        this.fabricaConexoes = fabricaConexoes;
    }


    @Override
    public Result create(Cliente cliente) {
        try{
            //criando uma conexão
            Connection con = fabricaConexoes.getConnection();

            //preparando o comando sql
            PreparedStatement pstm = con.prepareStatement("INSERT INTO clientes(nome,cpf,email,telefone) VALUES (?,?,?,?)");
            
            //ajustando os parâmetros do comando
            pstm.setString(1, cliente.getNome());
            pstm.setString(2, cliente.getCpf());
            pstm.setString(3, cliente.getEmail());
            pstm.setString(4, cliente.getTelefone());

            pstm.execute();

            pstm.close();
            con.close();
            return Result.success("Cliente criado com sucesso!");

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return Result.fail(e.getMessage());
        }

    }

    @Override
    public Result update(int id, Cliente cliente) {
        // TODO Auto-generated method stub
        return null;
    }


    private Cliente buildFrom(ResultSet result) throws SQLException{
        int id = result.getInt("id");
        String nome = result.getString("nome");
        String cpf = result.getString("cpf");
        String email = result.getString("email");
        String telefone = result.getString("telefone");

        Cliente cliente = new Cliente(id,nome, cpf, email, telefone);

        return cliente;
    }


    @Override
    public List<Cliente> listAll() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try{
            //criando uma conexão
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM clientes");

            ResultSet rs = pstm.executeQuery();
            
            while(rs.next()){
                Cliente cliente = buildFrom(rs);
                clientes.add(cliente);
            }


            rs.close();
            pstm.close();
            con.close();

            return clientes;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Cliente getById(int id) {
        
        Cliente cliente = null;
        try{
            //criando uma conexão
            Connection con = fabricaConexoes.getConnection(); 
            
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM clientes WHERE id=?");

            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();
            
            while(rs.next()){
                cliente = buildFrom(rs);
            }


            rs.close();
            pstm.close();
            con.close();

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }

        return cliente;
    }

    @Override
    public Result delete(int id) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Cliente getClienteFromVenda(int vendaId) {
        Cliente c = null;
        try{   

            Connection con = fabricaConexoes.getConnection();

            PreparedStatement pstm = con.prepareStatement("SELECT idCliente FROM vendas WHERE id=?");

            pstm.setInt(1, vendaId);

            ResultSet resultSetIdCliente = pstm.executeQuery();
            resultSetIdCliente.next();

            int idCliente = resultSetIdCliente.getInt("idCliente");

            c = getById(idCliente);

            resultSetIdCliente.close();
            pstm.close();
            con.close();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return c;
    }
    



}
