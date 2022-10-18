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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private MFXTableView<ProdutoRow> tbProdutos;

    private TelaProdutosViewModel viewModel;

    public TelaProdutos(TelaProdutosViewModel viewModel){
        this.viewModel = viewModel;
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
        
        //inializando as colunas e a renderização do valor das células
        MFXTableColumn<ProdutoRow> tbcNome = new MFXTableColumn<>("Nome");
        tbcNome.setRowCellFactory(produtoRow -> new MFXTableRowCell<>(t -> t.nomeProperty().getValue()));

        MFXTableColumn<ProdutoRow> tbcId = new MFXTableColumn<>("Id");
        tbcId.setRowCellFactory(produto -> new MFXTableRowCell<>(t -> t.idProperty().getValue()));

        MFXTableColumn<ProdutoRow> tbcDescricao = new MFXTableColumn<>("Descrição");
        tbcDescricao.setRowCellFactory(produto -> new MFXTableRowCell<>(t -> t.descricaoProperty().getValue()));

        MFXTableColumn<ProdutoRow> tbcValor = new MFXTableColumn<>("Valor R$");
        tbcValor.setRowCellFactory(produto -> new MFXTableRowCell<>(t -> t.valorProperty().getValue()));
        
        MFXTableColumn<ProdutoRow> tbcQuantidadeEstoque = new MFXTableColumn<>("Quantidade Estoque");
        tbcQuantidadeEstoque.setRowCellFactory(produto -> new MFXTableRowCell<>(t -> t.quantidadeEstoqueProperty().getValue()));
        

        //adicionando as colunas na tabela
        tbProdutos.getTableColumns().addAll(tbcId,tbcNome,tbcDescricao,tbcValor,tbcQuantidadeEstoque);

        //ligando a lista de ProdutoRow com a tabela
        tbProdutos.setItems(viewModel.getProdutos());

        //ligando os textfields com as propertys

        tfNome.textProperty().bindBidirectional(viewModel.getNomeProperty());
        tfDescricao.textProperty().bindBidirectional(viewModel.getDescricaoProperty());
        tfValor.textProperty().bindBidirectional(viewModel.getValorProperty());
        tfQuantidade.textProperty().bindBidirectional(viewModel.getQuantidadeEstoqueProperty());

    }

    @FXML
    void cadastrar(ActionEvent event) {
        Result resultado = viewModel.adicionar();

        showMessage(resultado);
    }

    @FXML
    void limpar(ActionEvent event) {
        viewModel.limpar();
    }



}
