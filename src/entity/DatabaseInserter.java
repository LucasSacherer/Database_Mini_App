package entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseInserter {

    final DatabaseSetup setup = new DatabaseSetup();

    public void insertNodes(ArrayList<String[]> content) throws SQLException{

        setup.setupNodes(); // resets the node table

        Connection con = DriverManager.getConnection("jdbc:derby:mini-app");
        Statement stmt = con.createStatement();

        for (String[] row : content) {
            StringBuilder toUpdate = new StringBuilder("INSERT INTO NODE VALUES ('");
            for (String value : row) {
                toUpdate.append(value);
                toUpdate.append("','");
            }
            toUpdate.delete(toUpdate.length() - 2, toUpdate.length()); //removes the last ",'"
            toUpdate.append(")");

            stmt.executeUpdate(toUpdate.toString());
        }

        stmt.close();
        con.close();
    }

    public void insertEdges(ArrayList<String[]> content) throws SQLException{

        setup.setupEdges(); //rests the edge table

        Connection con = DriverManager.getConnection("jdbc:derby:mini-app");
        Statement stmt = con.createStatement();

        for (String[] row : content) {
            StringBuilder toUpdate = new StringBuilder("INSERT INTO EDGE VALUES ('");
            for (String value : row) {
                toUpdate.append(value);
                toUpdate.append("','");
            }
            toUpdate.delete(toUpdate.length() - 2, toUpdate.length()); //removes the last ",'"
            toUpdate.append(")");

            stmt.executeUpdate(toUpdate.toString());
        }

        stmt.close();
        con.close();
    }
}
