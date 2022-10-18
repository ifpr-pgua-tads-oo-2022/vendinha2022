package ifpr.pgua.eic.vendinha2022.model.repositories;

import java.time.LocalDateTime;
import java.util.List;

import ifpr.pgua.eic.vendinha2022.model.daos.VendaDAO;
import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
import ifpr.pgua.eic.vendinha2022.model.entities.ItemVenda;
import ifpr.pgua.eic.vendinha2022.model.entities.Venda;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public class VendaRepository {
    

    private VendaDAO dao;

    public VendaRepository(VendaDAO dao){
        this.dao = dao;
    }

    public Result cadastrar(LocalDateTime dataHora, Cliente cliente, List<ItemVenda> itens){

        if(cliente == null){
            return Result.fail("Cliente inválido!");
        }

        if(dataHora.isAfter(LocalDateTime.now())){
            return Result.fail("Data e hora inválida!");
        }

        if(itens.size() == 0){
            return Result.fail("Nenhum item selecionado!");
        }

        Venda venda = new Venda(cliente,dataHora,itens);

        return dao.create(venda);


    }
}
