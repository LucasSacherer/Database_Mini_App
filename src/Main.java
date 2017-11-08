
import entity.DatabaseSetup;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    final private DatabaseSetup setup = new DatabaseSetup();

    @Override
    public void start(Stage primaryStage) throws Exception{
        //makes sure that the database is setup
        if (!(setup.loadDriver()&&setup.setupNodes()&&setup.setupEdges())){
            //driver or database setup failed
            System.out.println("Failed to setup database!");
        }

        //starts the GUI application
        Parent root = FXMLLoader.load(getClass().getResource("boundary/main.fxml"));
        primaryStage.setTitle("Database Editor");
        primaryStage.setScene(new Scene(root, 880, 550));
        primaryStage.show();
    }


    public static void main(String[] args) {
        //starts the GUI
        launch(args);
    }
}
