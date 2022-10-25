package ifpr.pgua.eic.vendinha2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

import ifpr.pgua.eic.vendinha2022.model.FabricaConexoes;

public class DataBaseCreator {
    
    private static final String SQL_FILE="script_sqlite.sql";

    public static void main(String[] args) throws Exception{
        
        FileReader freader = new FileReader(SQL_FILE);
        BufferedReader breader = new BufferedReader(freader);

        String ddl = breader.lines().collect(Collectors.joining());
        
        breader.close();
        freader.close();


        Connection con = FabricaConexoes.getInstance().getConnection();
        
        Statement stm = con.createStatement();

        stm.executeLargeUpdate(ddl);
        
        stm.close();
        con.close();
    }


}
