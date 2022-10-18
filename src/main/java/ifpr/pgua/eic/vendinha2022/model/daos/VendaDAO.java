package ifpr.pgua.eic.vendinha2022.model.daos;

import ifpr.pgua.eic.vendinha2022.model.entities.Venda;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public interface VendaDAO {
    Result create(Venda venda);
}
