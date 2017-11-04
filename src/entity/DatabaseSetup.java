package entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    public boolean loadDriver(){
        final String driver = "org.apache.derby.jdbc.EmbeddedDriver";

        try {
            Class.forName(driver).newInstance();
            return true;
        }catch (Exception ex){
            System.out.println("Failed to find Embedded JavaDB driver!");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean initialSetup(){
        try {
            Connection con = DriverManager.getConnection("jdbc:derby:mini-app");
            Statement stmt = con.createStatement();
            try {
                stmt.executeUpdate("DROP TABLE node");
            }catch (SQLException ex){
                //table does not exist
            }
            try {
                stmt.executeUpdate("DROP TABLE edge");
            }catch (SQLException ex){
                //table does not exist
            }

            stmt.execute("CREATE TABLE node (\n" +
                    " nodeID varchar(20) PRIMARY KEY,\n" +
                    " xcoord varchar(20) NOT NULL,\n" +
                    " ycoord varchar(20) NOT NULL,\n" +
                    " floor varchar(3) NOT NULL,\n" +
                    " building varchar(20) NOT NULL,\n" +
                    " nodeType varchar(10) NOT NULL,\n" +
                    " longName varchar(30) NOT NULL,\n" +
                    " shortName varchar(20) NOT NULL,\n" +
                    " teamAssigned varchar(10) NOT NULL\n" +
                    ")");
            stmt.execute("CREATE TABLE edge (\n" +
                    " edgeID vARCHAR(30) PRIMARY KEY,\n" +
                    " startNode varchar(20) NOT NULL,\n" +
                    " endNode varchar(20) NOT NULL\n" +
                    ")");

            stmt.close();
            con.close();
            return true;
        }catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
}
