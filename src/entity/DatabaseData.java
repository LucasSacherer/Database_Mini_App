package entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class DatabaseData {
    HashMap<String,Node> nodes;
    HashMap<String,Edge> edges;

    public DatabaseData(){
        nodes = new HashMap<>();
        edges = new HashMap<>();
    }

    public void updateNodes() throws SQLException{
        nodes.clear();

        Connection con = DriverManager.getConnection("jdbc:derby:mini-app");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM NODE");

        while(rs.next()){
            String nodeID = rs.getString("NODEID");
            String xcoord = rs.getString("XCOORD");
            String ycoord = rs.getString("YCOORD");
            String floor = rs.getString("FLOOR");
            String building = rs.getString("BUILDING");
            String nodeType = rs.getString("NODETYPE");
            String longName =  rs.getString("LONGNAME");
            String shortName = rs.getString("SHORTNAME");
            String teamAssigned = rs.getString("TEAMASSIGNED");

            nodes.put(nodeID, new Node(nodeID, xcoord, ycoord, floor, building, nodeType, longName,
                    shortName, teamAssigned));
        }

        rs.close();
        stmt.close();
        con.close();
    }

    public ArrayList<String> getNodeIDs(){
        ArrayList<String> IDs = new ArrayList<>();
        Set<String> keys = nodes.keySet();

        IDs.addAll(keys);
        return IDs;
    }

    public Node getNode(String nodeID){
        return nodes.get(nodeID);
    }
}
