package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main extends Application {
    public Stage loginStage = new Stage();
    @Override
    public void start(Stage primaryStage){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/FXML Files/loginScene.fxml"));
        } catch (IOException e) {
            System.out.println("sample fxml file error");
        }


        primaryStage.setTitle("Health Community (Login)");
        Scene homeScene = new Scene(root);
        primaryStage.setScene(homeScene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }


    public static void main(String[] args) {


       launch(args);
    }
}
