package ifpr.pgua.eic.vendinha2022.model.daos;

import java.util.List;

import ifpr.pgua.eic.vendinha2022.model.entities.Produto;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

//DAO Data Access Object
public interface ProdutoDAO{ 
    Result create(Produto obj);
    Result update(int id, Produto obj);
    List<Produto> getAll();
    Produto getById(int id);
    Produto getProdutoItem(int itemId);
    Result delete(int id);
}

//CRUD - Create, Retrieve, Update, Delete