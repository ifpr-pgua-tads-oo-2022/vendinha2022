package ifpr.pgua.eic.vendinha2022.model.daos;



import java.util.List;

import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public interface ClienteDAO {
    Result create(Cliente cliente);
    Result update(int id,Cliente cliente);
    List<Cliente> listAll();
    Cliente getById(int id);
    Result delete(int id);
}
