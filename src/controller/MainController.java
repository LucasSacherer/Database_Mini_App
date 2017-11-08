package controller;

import entity.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainController {
    //all the entities that will run in this controller
    final private FileSelector fileSelector = new FileSelector();
    final private CSVLoader fileLoader = new CSVLoader();
    final private DatabaseInserter inserter = new DatabaseInserter();
    final private DatabaseData data = new DatabaseData();
    final private CSVSaver saver = new CSVSaver();


    //buttons
    @FXML
    private Button btnNodeSelect;
    @FXML
    private Button btnEdgeSelect;
    @FXML
    private Button btnNodeLoad;
    @FXML
    private Button btnEdgeLoad;
    @FXML
    private Button btnUpdateNode;
    @FXML
    private Button btnUpdateEdge;

    @FXML
    private Button btnNodeSave;
    @FXML
    private Button btnEdgeSave;

    //combo boxes
    @FXML
    private ComboBox<String> nodeComboBox;
    @FXML
    private ComboBox<String> edgeComboBox;

    //labels
    @FXML
    private Label nodeFileName;
    @FXML
    private Label edgeFileName;

    //textFields
    @FXML
    private TextField txtXcoord;
    @FXML
    private TextField txtYcoord;
    @FXML
    private TextField txtFloor;
    @FXML
    private TextField txtBuilding;
    @FXML
    private TextField txtNodeType;
    @FXML
    private TextField txtLongName;
    @FXML
    private TextField txtShortName;
    @FXML
    private TextField txtTeamAssigned;
    @FXML
    private TextField txtStartNode;
    @FXML
    private TextField txtEndNode;

    //controller methods
    @FXML
    private void selectNodeFile(ActionEvent e){
        nodeFileName.setText(fileSelector.selectFile());
    }

    @FXML
    private void selectEdgeFile(ActionEvent e){
        edgeFileName.setText(fileSelector.selectFile());
    }

    @FXML
    private void loadNodeFile(ActionEvent e){
        String filePath = nodeFileName.getText();

        //checks if the file is a CSV file
        if(!fileLoader.isValidCSV(filePath)){
            //not a valid CSV file
            return; //should probably do something here
        }

        //gets the content out of the CSV file
        ArrayList<String[]> content;
        try{
            content = fileLoader.loadNodeCSVFile(filePath);
        }catch (IOException ex){
            System.out.println("This file is not a Node file!");
            nodeFileName.setText("This is not a Node file! Select a new Node file");
            ex.printStackTrace();
            return;
        }

        //tries to put the content into the database
        try {
            inserter.insertNodes(content);
        }catch (SQLException ex){
            System.out.println("Failed to insert csv into database!");
            ex.printStackTrace();
            return;
        }

        //updates the data class
        try{
            data.updateNodes();
        }catch (SQLException ex){
            System.out.println("Failed to get nodes out of the database!");
            ex.printStackTrace();
            return;
        }

        //updates the menu
        ArrayList<String> ids = data.getNodeIDs();
        nodeComboBox.getItems().clear();
        nodeComboBox.getItems().addAll(ids);
        if(ids.size() > 0){
            nodeComboBox.setValue(ids.get(0));
        }
    }

    @FXML
    private void loadEdgeFile(ActionEvent e){
        String filePath = edgeFileName.getText();

        //checks if the file is a CSV file
        if(!fileLoader.isValidCSV(filePath)){
            //not a valid CSV file
            return; //should probably do something here
        }

        //gets the content out of the CSV file
        ArrayList<String[]> content;
        try{
            content = fileLoader.loadEdgeCSVFile(filePath);
        }catch (IOException ex){
            System.out.println("This is not an Edge file!");
            edgeFileName.setText("This is not an Edge file! Select a new Edge file");
            ex.printStackTrace();
            return;
        }

        //tries to put the content into the database
        try {
            inserter.insertEdges(content);
        }catch (SQLException ex){
            System.out.println("Failed to insert csv into database!");
            ex.printStackTrace();
            return;
        }

        //updates the data class
        try{
            data.updateEdges();
        }catch (SQLException ex){
            System.out.println("Failed to get edges out of the database!");
            ex.printStackTrace();
            return;
        }

        //updates the menu
        ArrayList<String> ids = data.getEdgeIDs();
        edgeComboBox.getItems().clear();
        edgeComboBox.getItems().addAll(ids);
        if(ids.size() > 0){
            edgeComboBox.setValue(ids.get(0));
        }
    }

    @FXML
    private void nodeComboBoxChange(ActionEvent e){
        String selectedValue = nodeComboBox.getSelectionModel().getSelectedItem();
        if (selectedValue == null){
            //nothing is selected
            return;
        }

        Node newNode = data.getNode(selectedValue);
        txtXcoord.setText(newNode.getXcoord());
        txtYcoord.setText(newNode.getYcoord());
        txtFloor.setText(newNode.getFloor());
        txtBuilding.setText(newNode.getBuilding());
        txtNodeType.setText(newNode.getNodeID());
        txtLongName.setText(newNode.getLongName());
        txtShortName.setText(newNode.getShortName());
        txtTeamAssigned.setText(newNode.getTeamAssigned());
    }

    @FXML
    private void edgeComboBoxChange(ActionEvent e){
        String selectedValue = edgeComboBox.getSelectionModel().getSelectedItem();
        if (selectedValue == null){
            //nothing is selected
            return;
        }

        Edge newEdge = data.getEdge(selectedValue);
        txtStartNode.setText(newEdge.getStartNode());
        txtEndNode.setText(newEdge.getEndNode());
    }

    @FXML
    private void nodeUpdate(ActionEvent e){
        String selectedValue = nodeComboBox.getSelectionModel().getSelectedItem();
        if (selectedValue == null){
            //nothing is selected
            return;
        }

        String selectedX = txtXcoord.getText();
        String selectedY = txtYcoord.getText();
        String selectedFloor = txtFloor.getText();
        String selectedBuilding = txtBuilding.getText();
        String selectedType = txtNodeType.getText();
        String selectedLName = txtLongName.getText();
        String selectedSName = txtShortName.getText();
        String selectedTeamAssigned = txtTeamAssigned.getText();

        Node updatedNode = new Node(selectedValue, selectedX, selectedY, selectedFloor, selectedBuilding, selectedType,
                selectedLName, selectedSName, selectedTeamAssigned);

        //send database sql update
        try {
            data.modifyNode(updatedNode);
        } catch (SQLException e1) {
            System.out.println("failed to modify node");
            e1.printStackTrace();
        }

        try {
            data.updateNodes();
        } catch (SQLException e1) {
            System.out.println("failed to updates Nodes after modifying node");
            e1.printStackTrace();
        }
    }

    @FXML
    private void edgeUpdate(ActionEvent e){
        String selectedValue = edgeComboBox.getSelectionModel().getSelectedItem();
        if (selectedValue == null){
            //nothing is selected
            return;
        }

        String selectedStart = txtStartNode.getText();
        String selectedEnd = txtEndNode.getText();

        Edge updatedEdge = new Edge(selectedValue, selectedStart, selectedEnd);

        //send database sql update
        try {
            data.modifyEdge(updatedEdge);
        } catch (SQLException e1) {
            System.out.println("failed to modify edge");
            e1.printStackTrace();
        }

        try {
            data.updateEdges();
        } catch (SQLException e1) {
            System.out.println("failed to updates edges after modifying edge");
            e1.printStackTrace();
        }
    }



    @FXML
    private void saveEdgeFile(ActionEvent e){
        String filePath = edgeFileName.getText();
        try{
            saver.saveEdgeResultSet(filePath);
        }catch(SQLException ex){
            System.out.println("Failed to get from Database");
            ex.printStackTrace();
            return;
        }catch (IOException ex){
            System.out.println("Cannot Close the File or get Headers");
            ex.printStackTrace();
            return;
        }

    }

    @FXML
    private void saveNodeFile(ActionEvent e){
        String filePath = nodeFileName.getText();
        try{
            saver.saveNodeResultSet(filePath);
        }catch(SQLException ex){
            System.out.println("Failed to get from Database");
            ex.printStackTrace();
            return;
        }catch (IOException ex){
            System.out.println("Cannot Close the File or get Headers");
            ex.printStackTrace();
            return;
        }


    }

}
