package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.*;

public class LoginController {

    @FXML
    private AnchorPane LoginPanel;
    @FXML
    private TextField loginEmailBox;
    @FXML
    private PasswordField loginPasswordBox;
    @FXML
    private Button loginSubmitBtn;
    @FXML
    private ImageView closeBtn;



    Components cp = new Components();
    int serialNum = cp.getFilesSerialNo("D:\\JAVA\\Health Community (Project)\\src\\Database File\\loginUser.txt");

    @FXML
    void LoginSubmitAction(ActionEvent event) {
        System.out.println(loginEmailBox.getText());
        System.out.println(loginPasswordBox.getText());
        String email = loginEmailBox.getText();
        String pass = loginPasswordBox.getText();


        loginPasswordBox.clear();
        loginEmailBox.clear();

        if(email.compareTo("admin") == 0 && pass.compareTo("admin") == 0 ){
            System.out.println("worked");
            Parent mainPanel = null;
            try {
                mainPanel = FXMLLoader.load(getClass().getResource("/FXML Files/sample.fxml"));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            Stage st =(Stage) ((Node)event.getSource()).getScene().getWindow();
            Stage stage = new Stage();
            stage.setScene(new Scene(mainPanel));
            stage.centerOnScreen();
            st.close();
            stage.show();
        }
    }

    @FXML
    void closeBtnAction(MouseEvent event) {
        StackPane st =(StackPane) closeBtn.getScene().getRoot();
        st.getChildren().remove(LoginPanel);
//        try {
//            closeBtn.setImage(new Image(new FileInputStream("D:\\JAVA\\Extra Health Community - Copy\\src\\aditional items\\menu thick.png")));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }
    @FXML
    void CreateAccountBtnAction(ActionEvent e){
        Parent signUp = null;
        try {
            signUp = FXMLLoader.load(getClass().getResource("/FXML Files/signUpScene.fxml"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        Stage st = (Stage)((Node)e.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        stage.setScene(new Scene(signUp));
        stage.centerOnScreen();
        st.close();
        stage.show();
    }

}