package controller;

import entity.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainController {
    //all the entities that will run in this controller
    final private FileSelector fileSelector = new FileSelector();
    final private CSVLoader fileLoader = new CSVLoader();
    final private DatabaseInserter inserter = new DatabaseInserter();
    final private DatabaseData data = new DatabaseData();

    //buttons
    @FXML
    private Button btnNodeSelect;
    @FXML
    private Button btnEdgeSelect;
    @FXML
    private Button btnNodeLoad;
    @FXML
    private Button btnEdgeLoad;

    //combo boxes
    @FXML
    private ComboBox<String> nodeComboBox;

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
            System.out.println("Failed to read file!");
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
        //TODO: do the same thing as the loadNodeFile method but for edges
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
}
