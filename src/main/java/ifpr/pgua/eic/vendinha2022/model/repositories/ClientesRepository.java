package ifpr.pgua.eic.vendinha2022.model.repositories;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import ifpr.pgua.eic.vendinha2022.model.daos.ClienteDAO;
import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public class ClientesRepository {
    
    private List<Cliente> clientes;
    private ClienteDAO dao;

    public ClientesRepository(ClienteDAO dao){
        this.dao = dao;
    }

    public Result adicionarCliente(String nome, String cpf, String email, String telefone){

        Optional<Cliente> busca = clientes.stream().filter((cli)->cli.getCpf().equals(cpf)).findFirst();
        
        if(busca.isPresent()){
            return Result.fail("Cliente já cadastrado!");
        }

        Cliente cliente = new Cliente(nome,cpf,email,telefone);
        
        return dao.create(cliente);
            
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
        clientes = dao.listAll();
        return Collections.unmodifiableList(clientes);
    }



}
