package ifpr.pgua.eic.vendinha2022.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ifpr.pgua.eic.vendinha2022.controllers.ViewModels.ProdutoRow;
import ifpr.pgua.eic.vendinha2022.controllers.ViewModels.TelaProdutosViewModel;
import ifpr.pgua.eic.vendinha2022.model.entities.Produto;
import ifpr.pgua.eic.vendinha2022.model.results.Result;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class TelaProdutos extends BaseController implements Initializable {
    
    @FXML
    private Button btCadastrar;


    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfDescricao;

    @FXML
    private TextField tfQuantidade;

    @FXML
    private TextField tfValor;

    @FXML
    private TableView<ProdutoRow> tbProdutos;

    private TelaProdutosViewModel viewModel;

    public TelaProdutos(TelaProdutosViewModel viewModel){
        this.viewModel = viewModel;
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
        
        //inializando as colunas e a renderização do valor das células
        TableColumn<ProdutoRow, String> tbcNome = new TableColumn<>("Nome");
        tbcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<ProdutoRow,String> tbcId = new TableColumn<>("Id");
        tbcId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ProdutoRow,String> tbcDescricao = new TableColumn<>("Descrição");
        tbcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn<ProdutoRow,String> tbcValor = new TableColumn<>("Valor R$");
        tbcValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        
        TableColumn<ProdutoRow,String> tbcQuantidadeEstoque = new TableColumn<>("Quantidade Estoque");
        tbcQuantidadeEstoque.setCellValueFactory(new PropertyValueFactory<>("quantidadeEstoque"));
        
        tbProdutos.setOnMouseClicked(this::atualizar);

        //adicionando as colunas na tabela
        tbProdutos.getColumns().addAll(tbcId,tbcNome,tbcDescricao,tbcValor,tbcQuantidadeEstoque);


        //ligando a propriedade da linha selecionada com o
        viewModel.getLinhaSelecionadaProperty().bind(tbProdutos.getSelectionModel().selectedItemProperty());

        //ligando a lista de ProdutoRow com a tabela
        tbProdutos.setItems(viewModel.getProdutos());

        //ligando os textfields com as properties

        tfNome.textProperty().bindBidirectional(viewModel.getNomeProperty());
        tfDescricao.textProperty().bindBidirectional(viewModel.getDescricaoProperty());
        tfValor.textProperty().bindBidirectional(viewModel.getValorProperty());
        tfQuantidade.textProperty().bindBidirectional(viewModel.getQuantidadeEstoqueProperty());

        btCadastrar.textProperty().bindBidirectional(viewModel.getTextoBotaoProperty());
    }

    @FXML
    void cadastrar(ActionEvent event) {
        Result resultado = viewModel.adicionar();

        showMessage(resultado);
    }

    @FXML
    private void atualizar(MouseEvent evt){
        
        if(evt.getClickCount() == 2){
            viewModel.preencheTextFieldsParaAtualizar();
        }
    }

    @FXML
    void limpar(ActionEvent event) {
        viewModel.limpar();
    }

    



}
