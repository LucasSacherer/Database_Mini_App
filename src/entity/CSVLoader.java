package entity;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class CSVLoader {

    public boolean isValidCSV(String filePath){
        String extension = "";
        int i = filePath.lastIndexOf('.');
        if (i > 0){
            extension = filePath.substring(i+1);
        }

        return extension.equals("csv");
    }

    /**
     * Loads the given csv file into the node database
     * @param filePath - CSV file to be loaded into the database
     */
    public ArrayList<String[]> loadNodeCSVFile(String filePath) throws IOException{
        ArrayList<String[]> content = new ArrayList<>();

        Reader in = new FileReader(filePath);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);

        for (CSVRecord record: records){
            //check if the file is a node file
            if (record.size() != 9) {
                throw new IOException();
            }

            //here just to make clear what each value means
            String nodeID = record.get(0);
            String xcoord = record.get(1);
            String ycoord = record.get(2);
            String floor = record.get(3);
            String building = record.get(4);
            String nodeType = record.get(5);
            String longName = record.get(6);
            String shortName = record.get(7);
            String teamAssigned = record.get(8);

            String[] values = new String[9];
            values[0] = nodeID;
            values[1] = xcoord;
            values[2] = ycoord;
            values[3] = floor;
            values[4] = building;
            values[5] = nodeType;
            values[6] = longName;
            values[7] = shortName;
            values[8] = teamAssigned;

            content.add(values);
        }
        //remove the first line since it's the titles
        content.remove(0);

        in.close();

        return content;
    }

    public ArrayList<String[]> loadEdgeCSVFile (String filePath) throws IOException{
        ArrayList<String[]> content = new ArrayList<>();

        Reader in = new FileReader(filePath);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
        for (CSVRecord record: records){
            //check if the file is a edge file
            if (record.size() != 3) {
                throw new IOException();
            }

            //here just to make clear what each value means
            String edgeID = record.get(0);
            String startNode = record.get(1);
            String endNode = record.get(2);

            String[] values = new String[3];
            values[0] = edgeID;
            values[1] = startNode;
            values[2] = endNode;

            content.add(values);
        }
        //remove the first line since it's the titles
        content.remove(0);

        in.close();

        return content;
    }

}
