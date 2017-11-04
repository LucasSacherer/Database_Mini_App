package entity;

public class Node {
    private String nodeID;
    private String xcoord;
    private String ycoord;
    private String floor;
    private String building;
    private String nodeType;
    private String longName;
    private String shortName;
    private String teamAssigned;

    public Node(String nodeID, String xcoord, String ycoord, String floor, String building, String nodeType,
                String longName, String shortName, String teamAssigned){
        this.nodeID = nodeID;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.floor = floor;
        this.building = building;
        this.nodeType = nodeType;
        this.longName = longName;
        this.shortName = shortName;
        this.teamAssigned = teamAssigned;
    }

    public String getNodeID() {
        return nodeID;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }

    public String getXcoord() {
        return xcoord;
    }

    public void setXcoord(String xcoord) {
        this.xcoord = xcoord;
    }

    public String getYcoord() {
        return ycoord;
    }

    public void setYcoord(String ycoord) {
        this.ycoord = ycoord;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTeamAssigned() {
        return teamAssigned;
    }

    public void setTeamAssigned(String teamAssigned) {
        this.teamAssigned = teamAssigned;
    }
}
