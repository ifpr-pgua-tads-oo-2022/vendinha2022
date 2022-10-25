package ifpr.pgua.eic.vendinha2022.controllers.ViewModels;

import ifpr.pgua.eic.vendinha2022.model.entities.ItemVenda;
import ifpr.pgua.eic.vendinha2022.model.entities.Venda;
import ifpr.pgua.eic.vendinha2022.model.repositories.VendaRepository;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TelaVendasViewModel {
    

    private ObservableList<Venda> vendas = FXCollections.observableArrayList();
    private ObjectProperty<Venda> selecionada = new SimpleObjectProperty<>();
    private ObservableList<ItemVenda> itens = FXCollections.observableArrayList();

    private VendaRepository repository;


    public TelaVendasViewModel(VendaRepository repository){
        this.repository = repository;

        selecionada.addListener(new ChangeListener<Venda>() {
            @Override
            public void changed(ObservableValue<? extends Venda> arg0, Venda arg1, Venda arg2) {
                itens.clear();
                itens.addAll(arg2.getItens());
            }
        });
    }

    public void atualizar(){
        vendas.clear();
        vendas.addAll(repository.listar());
    }

    public ObservableList<Venda> getVendas(){
        return vendas;
    }

    public ObservableList<ItemVenda> getItens(){
        return itens;
    }

    public ObjectProperty<Venda> getSelecionadaProperty(){
        return selecionada;
    }


}
