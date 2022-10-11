package ifpr.pgua.eic.vendinha2022.controllers.ViewModels;

import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
import ifpr.pgua.eic.vendinha2022.model.repositories.ClientesRepository;
import ifpr.pgua.eic.vendinha2022.model.repositories.GerenciadorLoja;
import ifpr.pgua.eic.vendinha2022.model.results.Result;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Classe para representar os dados da tela de clientes, bem
 * como controlar o que irá ocorrer.
 */

public class TelaClientesViewModel {

    /*
     * Aqui são definidas a propriedades que serão ligadas com os
     * textfield da tela.
     */
    private StringProperty spNome = new SimpleStringProperty();
    private StringProperty spCpf = new SimpleStringProperty();
    private StringProperty spTelefone = new SimpleStringProperty();
    private StringProperty spEmail = new SimpleStringProperty();

    /*
     * Aqui são definidas duas propriedades para controlar o texto
     * de um dos botões da tela e também se os textfields tfNome e tfCpf podem
     * ser editados.
     */
    private StringProperty operacao = new SimpleStringProperty("Cadastrar");
    private BooleanProperty podeEditar = new SimpleBooleanProperty(true);
    private boolean atualizar = false;

    /* Lista que será utilizada para povar a TableView */
    private ObservableList<ClienteRow> obsClientes = FXCollections.observableArrayList();

    /* Objeto que serve para indicar qual linha da tabela está selecionada. */
    private ObjectProperty<ClienteRow> selecionado = new SimpleObjectProperty<>();

    private ObjectProperty<Result> alertProperty = new SimpleObjectProperty<>();

    private ClientesRepository repository;

    public TelaClientesViewModel(ClientesRepository repository) {

        this.repository = repository;

        updateList();

    }

    /*
     * Atualiza a lista observável de clientes, que por consequência irá
     * atualizar o conteúdo mostrado pela TableView.
     */
    private void updateList() {
        obsClientes.clear();
        for (Cliente c : repository.getClientes()) {
            obsClientes.add(new ClienteRow(c));
        }
    }

    public ObservableList<ClienteRow> getClientes() {
        return this.obsClientes;
    }

    public ObjectProperty<Result> alertProperty() {
        return alertProperty;
    }

    /* Métodos para acesso as propriedades. */

    public StringProperty operacaoProperty() {
        return operacao;
    }

    public BooleanProperty podeEditarProperty() {
        return podeEditar;
    }

    public StringProperty nomeProperty() {
        return this.spNome;
    }

    public StringProperty cpfProperty() {
        return this.spCpf;
    }

    public StringProperty telefoneProperty() {
        return this.spTelefone;
    }

    public StringProperty emailProperty() {
        return this.spEmail;
    }

    public ObjectProperty<ClienteRow> selecionadoProperty() {
        return selecionado;
    }

    /*
     * Método que será invocado quando
     * o botão de cadastrar for clicado na tela.
     */

    public void cadastrar() {

        // acessa os valores das propriedades, que por consequência
        // contém os valores digitados nos textfields.
        String nome = spNome.getValue();
        String cpf = spCpf.getValue();
        String telefone = spTelefone.getValue();
        String email = spEmail.getValue();

        if (atualizar) {
            repository.atualizarCliente(cpf, email, telefone);
        } else {
            repository.adicionarCliente(nome, cpf, email, telefone);
        }

        updateList();
        limpar();
    }

    public void atualizar() {
        operacao.setValue("Atualizar");
        podeEditar.setValue(false);
        atualizar = true;
        Cliente cliente = selecionado.get().getCliente();
        spNome.setValue(cliente.getNome());
        spCpf.setValue(cliente.getCpf());
        spEmail.setValue(cliente.getEmail());
        spTelefone.setValue(cliente.getTelefone());

        alertProperty.setValue(Result.fail("Teste"));

    }

    public void limpar() {
        spNome.setValue("");
        spCpf.setValue("");
        spTelefone.setValue("");
        spEmail.setValue("");
        podeEditar.setValue(true);
        atualizar = false;
        operacao.setValue("Cadastrar");
    }

}
