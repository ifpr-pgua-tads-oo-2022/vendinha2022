package ifpr.pgua.eic.vendinha2022.controllers.ViewModels;

import ifpr.pgua.eic.vendinha2022.model.entities.Produto;
import ifpr.pgua.eic.vendinha2022.model.repositories.GerenciadorLoja;
import ifpr.pgua.eic.vendinha2022.model.repositories.ProdutosRepository;
import ifpr.pgua.eic.vendinha2022.model.results.Result;
import ifpr.pgua.eic.vendinha2022.model.results.SuccessResult;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TelaProdutosViewModel {
    
    private StringProperty nomeProperty = new SimpleStringProperty();
    private StringProperty descricaoProperty = new SimpleStringProperty();
    private StringProperty valorProperty = new SimpleStringProperty("0.0");
    private StringProperty quantidadeEstoqueProperty = new SimpleStringProperty("0.0");

    private ObservableList<ProdutoRow> produtos = FXCollections.observableArrayList();

    private ProdutosRepository repository;

    public TelaProdutosViewModel(ProdutosRepository gerenciador){
        this.repository = gerenciador;

        updateList();
    }

        /*Atualiza a lista observável de clientes, que por consequência irá
     * atualizar o conteúdo mostrado pela TableView.
     */
    private void updateList(){
        produtos.clear();
        for(Produto produto:repository.getProdutos()){
            produtos.add(new ProdutoRow(produto));
        }
    }

    public StringProperty getNomeProperty() {
        return nomeProperty;
    }

    public StringProperty getDescricaoProperty() {
        return descricaoProperty;
    }

    public StringProperty getValorProperty() {
        return valorProperty;
    }

    public StringProperty getQuantidadeEstoqueProperty() {
        return quantidadeEstoqueProperty;
    }

    public ObservableList<ProdutoRow> getProdutos() {
        return produtos;
    }

    
    public Result adicionar(){
        
        String nome = nomeProperty.getValue();
        String descricao = descricaoProperty.getValue();
        Double valor = 0.0;
        Double quantidade = 0.0;

        try{
            valor = Double.parseDouble(valorProperty.getValue());
        }catch(NumberFormatException e){
            return Result.fail("Valor inválido!");
        }

        try{
            quantidade = Double.parseDouble(quantidadeEstoqueProperty.getValue());
        }catch(NumberFormatException e){
            return Result.fail("Quantidade inválida!");
        }

        Result resultado = repository.adicionarProduto(nome, descricao, valor, quantidade);
        if(resultado instanceof SuccessResult){
            limpar();
            updateList();
        }

        return resultado;
    }

    public void limpar(){
        nomeProperty.setValue("");
        descricaoProperty.setValue("");
        valorProperty.setValue("0.0");
        quantidadeEstoqueProperty.setValue("0.0");
    }


    


}
