package ifpr.pgua.eic.vendinha2022.controllers.ViewModels;

import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
import ifpr.pgua.eic.vendinha2022.model.entities.Produto;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Classe que representa uma linha da tabela de clientes. Cada
 * uma das colunas da tabela é ligada com um dos métodos definidos nesta
 * classe, a fim de determinar qual o valor que irá ser mostrado na coluna.
 */

public class ProdutoRow {
    
    private Produto produto;
    
    public ProdutoRow(Produto produto){
        this.produto = produto;
    }

    public Produto getProduto(){
        return produto;
    }


    /**
     * Propriedade para representar o atributo id do cliente.
     * 
     * @return SimpleStringProperty com o valor do id do cliente.
     */
    public StringProperty idProperty(){
        return new SimpleStringProperty(String.valueOf(produto.getId()));
    }

    /**
     * Propriedade para representar o atributo nome do cliente.
     * 
     * @return SimpleStringProperty com o valor do nome do cliente.
     */

    public StringProperty nomeProperty(){
        return new SimpleStringProperty(produto.getNome());
    }

    /**
     * Propriedade para representar o atributo descricao do produto.
     * 
     * @return SimpleStringProperty com o valor da descricação do produto.
     */
    public StringProperty descricaoProperty(){
        return new SimpleStringProperty(produto.getDescricao());
    }

    /**
     * Propriedade para representar o atributo valor do produto.
     * 
     * @return SimpleStringProperty com o valor do produto.
     */
    public DoubleProperty valorProperty(){
        return new SimpleDoubleProperty(produto.getValor());
    }

    /**
     * Propriedade para representar o atributo quantidade em estoque do produto.
     * 
     * @return SimpleStringProperty com o valor da quantidade em estoque.
     */
    public DoubleProperty quantidadeEstoqueProperty(){
        return new SimpleDoubleProperty(produto.getQuantidadeEstoque());
    }



}
