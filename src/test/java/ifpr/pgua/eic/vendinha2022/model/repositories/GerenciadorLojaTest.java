package ifpr.pgua.eic.vendinha2022.model.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ifpr.pgua.eic.vendinha2022.model.results.Result;
import ifpr.pgua.eic.vendinha2022.model.results.SuccessResult;

public class GerenciadorLojaTest {
    
    private Connection con;

    @BeforeEach
    void setup(){
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost/app", "root", "");
            con.setAutoCommit(false);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    
    
    @Test
    void check_can_create_new_produto() {
        String nome = "Produto 1";
        String descricao = "Descricao";
        Double valor = 10.5;
        Double quantidadeEstoque = 10.0;

        GerenciadorLoja gerenciador = new GerenciadorLoja();

        Result resultado =gerenciador.adicionarProduto(nome, descricao, valor, quantidadeEstoque);

        assertTrue(resultado instanceof SuccessResult);
        assertEquals(1,gerenciador.getProdutos().size());
        assertEquals(nome,gerenciador.getProdutos().get(0).getNome());


    }

    @AfterEach
    void tearDown(){
        try{
            con.rollback();
        }catch(SQLException e ){
            e.printStackTrace();
        }
        
    }
}
