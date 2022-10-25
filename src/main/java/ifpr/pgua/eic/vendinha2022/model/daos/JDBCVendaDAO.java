package ifpr.pgua.eic.vendinha2022.model.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ifpr.pgua.eic.vendinha2022.model.FabricaConexoes;
import ifpr.pgua.eic.vendinha2022.model.entities.ItemVenda;
import ifpr.pgua.eic.vendinha2022.model.entities.Venda;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public class JDBCVendaDAO implements VendaDAO {

    private static final String INSERT = "INSERT INTO vendas(dataHora,idCliente,total,desconto) VALUES (?,?,?,?)";
    private static final String INSERT_ITEM = "INSERT INTO itensvenda(idVenda,idProduto,valor,quantidade) VALUES (?,?,?,?)";
    private static final String SELECT_ALL = "SELECT * FROM vendas";

    private FabricaConexoes fabricaConexoes;

    public JDBCVendaDAO(FabricaConexoes fabricaConexoes){
        this.fabricaConexoes = fabricaConexoes;
    }


    @Override
    public Result create(Venda venda) {
        try{
            Connection con = fabricaConexoes.getConnection();

            PreparedStatement pstm = con.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS);

            pstm.setTimestamp(1, Timestamp.valueOf(venda.getDataHora()));
            pstm.setInt(2, venda.getCliente().getId());
            pstm.setDouble(3, venda.getTotal());
            pstm.setDouble(4, venda.getDesconto());

            pstm.execute();

            //Pegar o id gerado por autoincremento
            ResultSet resultSet = pstm.getGeneratedKeys();
            resultSet.next();
            int idVenda = resultSet.getInt(1);

            PreparedStatement pstmItem = con.prepareStatement(INSERT_ITEM);

            for(ItemVenda item:venda.getItens()){
                pstmItem.setInt(1, idVenda);
                pstmItem.setInt(2, item.getProduto().getId());
                pstmItem.setDouble(3, item.getValorVenda());
                pstmItem.setDouble(4, item.getQuantidade());

                pstmItem.execute();
            }

            pstmItem.close();
            pstm.close();

            con.close();

            return Result.success("Venda criada com sucesso!");


        }catch(SQLException e){
            return Result.fail(e.getMessage());
        }
    }

    private Venda buildFrom(ResultSet resultSet) throws SQLException{

        int id = resultSet.getInt("id");
        LocalDateTime dataHora = resultSet.getTimestamp("dataHora").toLocalDateTime();
        double total = resultSet.getDouble("total");
        double desconto = resultSet.getDouble("desconto");

        Venda venda = new Venda(id, dataHora, total, desconto);

        return venda;

    }

    private List<ItemVenda> loadItens(int idVenda) throws SQLException{
        List<ItemVenda> itens = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM itensvenda WHERE idVenda=?");

        pstm.setInt(1,idVenda);

        ResultSet result = pstm.executeQuery();

        while(result.next()){
            int idItem = result.getInt("id");
            double valor = result.getDouble("valor");
            double quantidade = result.getDouble("quantidade");

            ItemVenda item = new ItemVenda();
            item.setId(idItem);
            item.setValorVenda(valor);
            item.setQuantidade(quantidade);

            itens.add(item);
        }

        result.close();
        pstm.close();
        con.close();

        return itens;


    }

    @Override
    public List<Venda> getAll() {
        List<Venda> lista = new ArrayList<>();

        try{
            Connection con = fabricaConexoes.getConnection();

            PreparedStatement pstm = con.prepareStatement(SELECT_ALL);

            ResultSet resultSet = pstm.executeQuery();

            while(resultSet.next()){
                Venda venda = buildFrom(resultSet);
                venda.setItens(loadItens(venda.getId()));
                lista.add(venda);
            }

            resultSet.close();
            pstm.close();
            con.close();

            return lista;

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }


    
    public double totalVendas() throws Exception {
        System.out.println("Passei aqui...");
        Connection con = fabricaConexoes.getConnection();

        String sql = "CALL total_vendas(?)";

        CallableStatement call = con.prepareCall(sql);

        call.registerOutParameter(1, Types.REAL);

        call.execute();

        double totalVendas = call.getDouble(1);

        call.close();
        con.close();
        
        return totalVendas;
    }

    
    public double totalVendasPessoa(int idPessoa) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "CALL total_vendas_cliente(?,?)";

        CallableStatement call = con.prepareCall(sql);

        call.setInt(1,idPessoa);
        call.registerOutParameter(2, Types.REAL);

        call.execute();

        double total = call.getDouble(2);

        call.close();
        con.close();

        return total;
    }
    
    
}
