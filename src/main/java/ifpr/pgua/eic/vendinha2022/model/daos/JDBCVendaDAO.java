package ifpr.pgua.eic.vendinha2022.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import ifpr.pgua.eic.vendinha2022.model.FabricaConexoes;
import ifpr.pgua.eic.vendinha2022.model.entities.ItemVenda;
import ifpr.pgua.eic.vendinha2022.model.entities.Venda;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public class JDBCVendaDAO implements VendaDAO {

    private static final String INSERT = "INSERT INTO vendas(dataHora,idCliente,total,desconto) VALUES (?,?,?,?)";
    private static final String INSERT_ITEM = "INSERT INTO itensvenda(idVenda,idProduto,valor,quantidade) VALUES (?,?,?,?)";

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
    
}
