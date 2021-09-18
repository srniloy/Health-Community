package sample;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.nio.channels.AcceptPendingException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

public class SignUpController {

    @FXML
    private AnchorPane SignupPanel;
    @FXML
    private TextField firstNameTextBox;
    @FXML
    private TextField lastNameTextBox;
    @FXML
    private TextField contactTextBox;
    @FXML
    private PasswordField passwordTextBox;
    @FXML
    private TextField ageTextBox;
    @FXML
    private RadioButton genderMaleBtn;
    @FXML
    private ToggleGroup gender;
    @FXML
    private RadioButton genderFemaleBtn;
    @FXML
    private RadioButton genderCustomBtn;
    @FXML
    private PasswordField retypePassTextBox;
    @FXML
    private CheckBox privacyCheckBox;
    @FXML
    private Button signUpSubmitBtn;
    @FXML
    private RadioButton userTypeDoctorBtn;
    @FXML
    private ToggleGroup profession;
    @FXML
    private RadioButton userTypePatientBtn;
    @FXML
    private ImageView signupCloseBtn;
    @FXML
    private  TextField userNameTextBox;
    @FXML
    private DatePicker birthDateTextBox;
    @FXML
    private TextField addressTextBox;
    private  String birthDate = "";
    private String userInformationTxtPath = "src/Database File/userInformation.txt";


    @FXML
    void PrivacyCheckBoxAction(ActionEvent event) {
         if(privacyCheckBox.isSelected()){
             signUpSubmitBtn.setDisable(false);
         }
         else if(!privacyCheckBox.isSelected()){
             signUpSubmitBtn.setDisable(true);
         }
    }


    @FXML
    void BirthDateTextBoxAction(ActionEvent event){
        LocalDate ld = birthDateTextBox.getValue();
        birthDate =  ld.toString();
    }

    @FXML
    void SignUpSubmitBtnAction(ActionEvent event) {
        RadioButton userGender = (RadioButton) gender.getSelectedToggle();
        RadioButton userType = (RadioButton) profession.getSelectedToggle();
        String userName = userNameTextBox.getText().trim();

        UserSignUpInfo userSignUpInfo = new UserSignUpInfo(firstNameTextBox.getText(),lastNameTextBox.getText(),userName,ageTextBox.getText(),birthDate,addressTextBox.getText(),userGender.getText(),userType.getText(),contactTextBox.getText(),passwordTextBox.getText(),retypePassTextBox.getText());
        File f = new File(userInformationTxtPath);

        if(f.length() == 0){

            HashSet<String> hs = new HashSet<>();
            HashMap<String,UserSignUpInfo> hm = new HashMap<>();
            if(!hs.contains(userName)){
                hs.add(userName);
                hm.put(userName,userSignUpInfo);
            }else{  // ----------------------------------------------------------------------------<<<<<<<<<<<<
                System.exit(5);
            }
            try {
                FileOutputStream fos = new FileOutputStream(userInformationTxtPath);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(hs);
                oos.writeObject(hm);
                oos.close();
            } catch (Exception e) {
                System.out.println("Exception during File Serialization : "+ e);
            }
        }
        else{
            try {
                FileInputStream fis = new FileInputStream(userInformationTxtPath);
                ObjectInputStream ois = new ObjectInputStream(fis);
                HashSet<String> hs = (HashSet<String>) ois.readObject();
                HashMap<String,UserSignUpInfo> hm = (HashMap<String, UserSignUpInfo>) ois.readObject();

                if(hs.contains(userName)){
                    System.out.println("Contact are matched with other users contact, try again with new contact information.");
                    System.exit(0);
                }
                hs.add(userName);
                for (String user:hs){
                    System.out.println("UserName: "+user);
                }

                String un = hs.iterator().next();
                System.out.println("getLastUserName: "+ un);
                hm.put(userName,userSignUpInfo);
                hm.get(userName).print();

                FileOutputStream fos = new FileOutputStream(userInformationTxtPath);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(hs);
                oos.writeObject(hm);
                oos.close();
                ois.close();
                fis.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }








        Parent mainPanel = null;
        try {
            mainPanel = FXMLLoader.load(getClass().getResource("/FXML Files/sample.fxml"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        Stage st = (Stage)((Node)event.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        stage.setScene(new Scene(mainPanel));
        stage.centerOnScreen();
        stage.show();
        st.close();
    }


    @FXML
    void SignupCloseBtnAction(MouseEvent event) {

    }


}

class UserLoginInfo implements Serializable{
    String contact;
    String password;
    UserLoginInfo(String contact,String password){
        this.contact = contact;
        this.password = password;
    }
}

class UserSignUpInfo implements Serializable{
    String Fname;
    String Lname;
    String userName;
    String age;
    String dateOfBirth;
    String contact;
    String password;
    String retypePassword;
    String gender;
    String userType;
    String address;

    UserSignUpInfo(String Fname,String Lname,String userName,String age,String dateOfBirth,String address,String gender,String userType,String contact,String password,String retypePassword){
        this.Fname = Fname;
        this.Lname = Lname;
        this.userName = userName;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.gender = gender;
        this.userType = userType;
        this.contact = contact;
        this.password = password;
        this.retypePassword = retypePassword;
    }

    void print(){
        System.out.println(Fname);
        System.out.println(Lname);
        System.out.println(userName);
        System.out.println(age);
        System.out.println(dateOfBirth);
        System.out.println(address);
        System.out.println(gender);
        System.out.println(userType);
        System.out.println(contact);
        System.out.println(password);
        System.out.println(retypePassword);
    }

}