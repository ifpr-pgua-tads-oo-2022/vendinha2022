package ifpr.pgua.eic.vendinha2022.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ifpr.pgua.eic.vendinha2022.controllers.ViewModels.ClienteRow;
import ifpr.pgua.eic.vendinha2022.controllers.ViewModels.TelaClientesViewModel;
import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
import ifpr.pgua.eic.vendinha2022.model.results.Result;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class TelaClientes extends BaseController implements Initializable{

    @FXML
    private TextField tfNome;

    @FXML
    private TableColumn<ClienteRow,String> tbcCpf;

    @FXML
    private TableColumn<ClienteRow, String> tbcEmail;

    @FXML
    private TableColumn<ClienteRow, String> tbcId;

    @FXML
    private TableColumn<ClienteRow, String> tbcNome;

    @FXML
    private TableColumn<ClienteRow, String> tbcTelefone;

    @FXML
    private TableView<ClienteRow> tbClientes;

    @FXML
    private TextField tfCPF;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfTelefone;

    @FXML
    private Button btCadastrar;
    
    @FXML
    private Button btLimpar;


    private TelaClientesViewModel viewModel;


    public TelaClientes(TelaClientesViewModel viewModel){
        this.viewModel = viewModel;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        

        //define quais serão as propriedades que servirão para preencher
        //o valor da coluna. Note que o nome da propriedade deve possuir
        //um get equivalente no modelo que representa a linha da tabela.
        tbcCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tbcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbcTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tbcId.setCellValueFactory(new PropertyValueFactory<>("id"));

        //liga o conjunto de itens da tabela com a lista de clientes do viewmodel
        tbClientes.setItems(viewModel.getClientes());

        //liga a propriedade selecionado do viewmodel com a tabela
        viewModel.selecionadoProperty().bind(tbClientes.getSelectionModel().selectedItemProperty());

        viewModel.alertProperty().addListener((ChangeListener<Result>) (observable, oldVal, newVal) -> {
            // TODO Auto-generated method stub
            showMessage(newVal);
        });

        //liga a propriedade texto do textfield nome com a propriedade do viewmodel
        tfNome.textProperty().bindBidirectional(viewModel.nomeProperty());
        //liga a propriedade editavel do textfield com a propriedade do viewmodel
        tfNome.editableProperty().bind(viewModel.podeEditarProperty());
        
        
        tfCPF.textProperty().bindBidirectional(viewModel.cpfProperty());
        tfCPF.editableProperty().bind(viewModel.podeEditarProperty());

        tfEmail.textProperty().bindBidirectional(viewModel.emailProperty());
        tfTelefone.textProperty().bindBidirectional(viewModel.telefoneProperty());

        btCadastrar.textProperty().bind(viewModel.operacaoProperty());
    }

    @FXML
    private void cadastrar(){
        viewModel.cadastrar();
    }

    @FXML
    private void limpar(){
        viewModel.limpar();
    }

    @FXML
    private void atualizar(MouseEvent event){
        if(event.getClickCount() == 2){
            viewModel.atualizar();
        }
        
    }


}
