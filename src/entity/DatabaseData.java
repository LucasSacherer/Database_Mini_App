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

    public void modifyNode(Node n) throws SQLException{
        Connection con = DriverManager.getConnection("jdbc:derby:mini-app");
        Statement stmt = con.createStatement();
        stmt.executeUpdate("UPDATE NODE SET xcoord = '"+n.getXcoord()+
                "', ycoord = '"+n.getYcoord()+
                "', floor = '"+n.getFloor()+
                "', building = '"+n.getBuilding()+
                "', nodeType = '"+n.getNodeType()+
                "', longName = '"+n.getLongName()+
                "', shortName = '"+n.getShortName()+
                "', teamAssigned = '"+n.getTeamAssigned()+
                "' WHERE nodeID = '"+n.getNodeID()+"'");
        stmt.close();
        con.close();
    }

    public void modifyEdge(Edge e) throws SQLException{
        Connection con = DriverManager.getConnection("jdbc:derby:mini-app");
        Statement stmt = con.createStatement();
        stmt.executeUpdate("UPDATE EDGE SET startNode = '"+e.getStartNode()+
                "', endNode = '"+e.getEndNode()+
                "' WHERE edgeID = '"+e.getEdgeID()+"'");
        stmt.close();
        con.close();
    }

    public void updateEdges() throws SQLException{
        edges.clear();

        Connection con = DriverManager.getConnection("jdbc:derby:mini-app");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM EDGE");

        while(rs.next()){
            String edgeID = rs.getString("EDGEID");
            String startNode = rs.getString("STARTNODE");
            String endNode = rs.getString("ENDNODE");

            edges.put(edgeID, new Edge(edgeID, startNode, endNode));
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

    public ArrayList<String> getEdgeIDs(){
        ArrayList<String> IDs = new ArrayList<>();
        Set<String> keys = edges.keySet();

        IDs.addAll(keys);
        return IDs;
    }

    public Node getNode(String nodeID){
        return nodes.get(nodeID);
    }

    public Edge getEdge(String nodeID){
        return edges.get(nodeID);
    }
}
