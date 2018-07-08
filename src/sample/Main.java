package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load((getClass().getResource("sample.fxml")));
        primaryStage.setTitle("Склад");
        primaryStage.setScene(new Scene(root, 905, 605));
        primaryStage.getIcons().add(new Image("sample/css/img/icon.png"));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
