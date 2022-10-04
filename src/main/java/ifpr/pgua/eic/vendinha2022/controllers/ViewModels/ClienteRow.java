package ifpr.pgua.eic.vendinha2022.controllers.ViewModels;

import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Classe que representa uma linha da tabela de clientes. Cada
 * uma das colunas da tabela é ligada com um dos métodos definidos nesta
 * classe, a fim de determinar qual o valor que irá ser mostrado na coluna.
 */

public class ClienteRow {
    
    private Cliente cliente;
    
    public ClienteRow(Cliente cliente){
        this.cliente = cliente;
    }

    public Cliente getCliente(){
        return cliente;
    }


    /**
     * Propriedade para representar o atributo id do cliente.
     * 
     * @return SimpleStringProperty com o valor do id do cliente.
     */
    public StringProperty idProperty(){
        return new SimpleStringProperty(String.valueOf(cliente.getId()));
    }

    /**
     * Propriedade para representar o atributo nome do cliente.
     * 
     * @return SimpleStringProperty com o valor do nome do cliente.
     */

    public StringProperty nomeProperty(){
        return new SimpleStringProperty(cliente.getNome());
    }

    /**
     * Propriedade para representar o atributo telefone do cliente.
     * 
     * @return SimpleStringProperty com o valor do telefone do cliente.
     */
    public StringProperty telefoneProperty(){
        return new SimpleStringProperty(cliente.getTelefone());
    }

    /**
     * Propriedade para representar o atributo email do cliente.
     * 
     * @return SimpleStringProperty com o valor do email do cliente.
     */
    public StringProperty emailProperty(){
        return new SimpleStringProperty(cliente.getEmail());
    }

    /**
     * Propriedade para representar o atributo cpf do cliente.
     * 
     * @return SimpleStringProperty com o valor do cpf do cliente.
     */
    public StringProperty cpfProperty(){
        return new SimpleStringProperty(cliente.getCpf());
    }



}
