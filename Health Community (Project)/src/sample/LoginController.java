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
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.HashSet;

public class LoginController {

    @FXML
    private TextField userNameTextBox;

    @FXML
    private PasswordField loginPasswordBox;

    @FXML
    private Button loginSubmitBtn;

    @FXML
    private Button createAccountBtn;

    private String userInformationTxtPath = "src/Database File/userInformation.txt";
    @FXML
    void CreateAccountBtnAction(ActionEvent event) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/FXML Files/signUpScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage st = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Health Community (SignUp/Create account)");
            stage.centerOnScreen();
            st.close();
            stage.show();
    }

    @FXML
    void LoginSubmitAction(ActionEvent event) {
        String userName = userNameTextBox.getText().trim();
        String password = loginPasswordBox.getText();
        try{
            FileInputStream fis = new FileInputStream(userInformationTxtPath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            HashSet<String> hs = (HashSet<String>) ois.readObject();
            HashMap<String,UserSignUpInfo> hm = (HashMap<String, UserSignUpInfo>) ois.readObject();


            if(hs.contains(userName) && hm.get(userName).password.compareTo(password) == 0){
                System.out.println("ini-userName: "+userName+" ini-Password: "+ password);
                System.out.println("store-userName: "+hs.contains(userName)+" store-Password: "+ hm.get(userName).password);

                FXMLLoader fl = new FXMLLoader(getClass().getResource("/FXML Files/sample.fxml"));
                Parent  mainPanel = fl.load();
                Controller cntrl = fl.getController();
                cntrl.setProfileInfo(userName);
                Stage st = (Stage)((Node)event.getSource()).getScene().getWindow();
                Stage stage = new Stage();
                stage.setScene(new Scene(mainPanel,880,580));
                stage.setTitle("Health Community");
                stage.setResizable(false);
                stage.centerOnScreen();
                stage.show();
                st.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
