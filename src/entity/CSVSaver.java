package entity;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;


public class CSVSaver {

    public void saveNodeResultSet(String filePath) throws SQLException, IOException{

        Connection con = DriverManager.getConnection("jdbc:derby:mini-app");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM NODE");

        saveCSVFile(rs, filePath);

        rs.close();
        stmt.close();
        con.close();
    }

    public void saveEdgeResultSet(String filePath) throws SQLException, IOException{

        Connection con = DriverManager.getConnection("jdbc:derby:mini-app");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM EDGE");

        saveCSVFile(rs, filePath);

        rs.close();
        stmt.close();
        con.close();
    }

    public void saveCSVFile(ResultSet rs, String filePath) throws SQLException, IOException {
        File out = new File(filePath);
        FileWriter writer = new FileWriter(out);
        final CSVPrinter printer = CSVFormat.DEFAULT.withHeader(rs).print(writer);
        printer.printRecords(rs);

        writer.close();

    }


}
