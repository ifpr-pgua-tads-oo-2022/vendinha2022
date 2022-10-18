package ifpr.pgua.eic.vendinha2022.model.daos;

import java.util.List;

import ifpr.pgua.eic.vendinha2022.model.entities.Produto;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public interface ProdutoDAO{
    Result create(Produto obj);
    Result update(int id, Produto obj);
    List<Produto> getAll();
    Produto getById(int id);
    Result delete(int id);
}
