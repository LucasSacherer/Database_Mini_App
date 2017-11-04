package entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseInserter {

    public void insertNodes(ArrayList<String[]> content) throws SQLException{

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
}
