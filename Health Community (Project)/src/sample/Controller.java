package sample;

import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;

public class Controller extends Components implements Initializable {

    @FXML
    private TextField searchTextBox;
    @FXML
    private VBox QuestionsBox;

    private  String userName;



    // Question Posting Panel Controlls------------------------------------>

    @FXML
    private AnchorPane QuestionPostingPanel;
    @FXML
    private TextField questionPostingPanelTitle;
    @FXML
    private TextArea questionPostingPanelDetail;
    @FXML
    private  ScrollPane QuestionViewPanel;
    @FXML
    private ScrollPane QuestionViewPanelForUser;
    @FXML
    private VBox QuestionsBoxForUser;

    private String QACinfoOfUserTxtFilePath = "src/Database File/QACinfoOfUser.txt";

    @FXML
    void  AddQuestionBtnAction(ActionEvent event) {
        QuestionPostingPanel.setVisible(true);
        QuestionViewPanel.setVisible(false);
        QuestionViewPanelForUser.setVisible(false);

    }

    @FXML
    void questionPostingPanelCloseBtnAction(MouseEvent event) {
        questionPostingPanelTitle.clear();
        questionPostingPanelDetail.clear();
        QuestionPostingPanel.setVisible(false);
        QuestionViewPanelForUser.setVisible(true);
    }

    @FXML
    void questionPostingPanelPostBtn(ActionEvent event) {
        String title = questionPostingPanelTitle.getText();
        String detail = questionPostingPanelDetail.getText();
        UserQuestion uq = new UserQuestion(title,detail,userName);
        File file = new File(QACinfoOfUserTxtFilePath);
        try {
            if(file.length() == 0){
                System.out.println("File Length: "+file.length());
                ArrayList<UserQuestion> al = new ArrayList<>();
                ArrayList<UserQuestion> all = new ArrayList<>();
                al.add(uq);
                all.add(uq);
                HashMap<String,ArrayList<UserQuestion>> hm = new HashMap<>();
                hm.put(userName,al);
                hm.put("all",all);
                HashMap<String,HashMap> mainHM = new HashMap<>();
                mainHM.put("question",hm);
                FileOutputStream fos = new FileOutputStream(QACinfoOfUserTxtFilePath);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(mainHM);
                fos.close();
                oos.close();
            }else{
                FileInputStream fis = new FileInputStream(QACinfoOfUserTxtFilePath);
                ObjectInputStream ois = new ObjectInputStream(fis);
                HashMap<String,HashMap> mainHM = (HashMap<String, HashMap>) ois.readObject();
                HashMap<String,ArrayList<UserQuestion>> hm = (HashMap<String, ArrayList<UserQuestion>>) mainHM.get("question");

                if(hm.containsKey(userName)){
                    ArrayList<UserQuestion> al = hm.get(userName);
                    al.add(uq);
                    hm.put(userName,al);
                }else{
                    ArrayList<UserQuestion> al = new ArrayList<>();
                    al.add(uq);
                    hm.put(userName,al);
                }
                ArrayList<UserQuestion> all = hm.get("all");
                all.add(uq);
                hm.put("all",all); // -------------------------------------------------------------------------------------------------------------------------------------------
                mainHM.put("question",hm);


                FileOutputStream fos = new FileOutputStream(QACinfoOfUserTxtFilePath);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(mainHM);
                fis.close(); fos.close(); ois.close(); oos.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        //QuestionsBox.getChildren().add(0,qpd.AddingDesignedPostNode());
        QuestionsBoxForUser.getChildren().add(0,addQuestionContainerLt(title,detail,5,45,12,userName,QuestionViewPanel));
        questionPostingPanelTitle.clear();
        questionPostingPanelDetail.clear();
        QuestionPostingPanel.setVisible(false);
        QuestionViewPanelForUser.setVisible(true);
    }

    // <---------------------------------------------------------


    @FXML
    void searchBtnAction(MouseEvent event) {
        System.out.println(searchTextBox.getText());
        try {
            File file = new File("D:\\JAVA\\Extra Health Community - Copy\\src\\Database File\\searchInfo.txt");
            PrintWriter bf = new PrintWriter(new FileOutputStream(file,true));
            bf.println(searchTextBox.getText());
            bf.close();
        } catch (IOException e) { System.out.println(e.getMessage()); }
        searchTextBox.clear();
    }

    @FXML
    void searchBoxKeyPressAction(KeyEvent event) {
        String s = String.valueOf(event.getCode());
        if(s == "ENTER"){
            System.out.println(searchTextBox.getText());
            try {
                File file = new File("D:\\JAVA\\Health Community (Project)\\src\\Database File\\searchInfo.txt");
                PrintWriter bf = new PrintWriter(new FileOutputStream(file,true));
                bf.println(searchTextBox.getText());
                bf.close();
            } catch (IOException e) {System.out.println(e.getMessage());}
            searchTextBox.clear();
        }
    }

    // =================================================
    // Side Bar Buttons Event actions------------------>
    // =================================================


    @FXML
    private ImageView recentIcon;
    @FXML
    private Text recentText;
    @FXML
    private ImageView staredIcon;
    @FXML
    private Text staredText;
    @FXML
    private ImageView qaIcon;
    @FXML
    private Text qaText;
    @FXML
    private ImageView bloodIcon;
    @FXML
    private Text bloodText;
    @FXML
    private ImageView ambulanceIcon;
    @FXML
    private Text ambulanceText;
    @FXML
    private FlowPane recentBtn;
    @FXML
    private FlowPane staredBtn;
    @FXML
    private FlowPane qaBtn;
    @FXML
    private FlowPane bloodBtn;
    @FXML
    private FlowPane ambulanceBtn;
    @FXML
    private AnchorPane QuestionWithDetailPanel;

    private String recentIconDefPath = "src/Additional-items/recent-trans.png";
    private String staredIconDefPath = "src/Additional-items/star-trans.png";
    private String qaIconDefPath = "src/Additional-items/QA-trans.png";
    private String bloodIconDefPath = "src/Additional-items/blood-donation-trans.png";
    private String ambulanceIconDefPath = "src/Additional-items/ambulance-trans.png";

    private Boolean ambulanceBtnclicked = false;
    private Boolean recentBtnclicked = false;
    private Boolean qaBtnclicked = false;
    private Boolean bloodBtnclicked = false;
    private Boolean staredBtnclicked = false;

    void RemoveColors(){
        ImageView iv[] = {recentIcon,staredIcon,qaIcon,bloodIcon,ambulanceIcon};
        Text txt[] = {recentText,staredText,qaText,bloodText,ambulanceText};
        String path[] = {recentIconDefPath,staredIconDefPath,qaIconDefPath,bloodIconDefPath,ambulanceIconDefPath};
        removeColorForAll(iv,txt,path);
    }
    @FXML
    void AmbulanceBtnMouseClicked(MouseEvent event) {
        FlowPane fp[] = {recentBtn,staredBtn,qaBtn,bloodBtn,ambulanceBtn};
        RemoveColors();
        setTransparentAllNode(fp);
        setColor(ambulanceIcon,ambulanceText,"src/Additional-items/ambulance-colored.png");
        ambulanceBtn.setStyle("-fx-background-color: #fff");
        ambulanceBtnclicked = true;
        recentBtnclicked=qaBtnclicked = staredBtnclicked = bloodBtnclicked = false;
    }
    @FXML
    void AmbulanceBtnMouseEntered(MouseEvent event) {
        setColor(ambulanceIcon,ambulanceText,"src/Additional-items/ambulance-colored.png");
    }
    @FXML
    void AmbulanceBtnMouseExited(MouseEvent event) {
        if(!ambulanceBtnclicked){
            removeColor(ambulanceIcon,ambulanceText,ambulanceIconDefPath);
        }
    }
    @FXML
    void BloodBtnMouseClicked(MouseEvent event) {
        FlowPane iv[] = {recentBtn,staredBtn,qaBtn,bloodBtn,ambulanceBtn};
        RemoveColors();
        setTransparentAllNode(iv);
        setColor(bloodIcon,bloodText,"src/Additional-items/blood-donation-colored.png");
        bloodBtn.setStyle("-fx-background-color: #fff");
        bloodBtnclicked = true;
        recentBtnclicked=qaBtnclicked = staredBtnclicked = ambulanceBtnclicked = false;
    }
    @FXML
    void BloodBtnMouseEntered(MouseEvent event) {
        setColor(bloodIcon,bloodText,"src/Additional-items/blood-donation-colored.png");
    }
    @FXML
    void BloodBtnMouseExited(MouseEvent event) {
        if(!bloodBtnclicked){
            removeColor(bloodIcon,bloodText,bloodIconDefPath);
        }
    }
    @FXML
    void QABtnMouseClicked(MouseEvent event) {
        FlowPane iv[] = {recentBtn,staredBtn,qaBtn,bloodBtn,ambulanceBtn};
        RemoveColors();
        setTransparentAllNode(iv);
        setColor(qaIcon,qaText,"src/Additional-items/QA-colored.png");
        qaBtn.setStyle("-fx-background-color: #fff");
        QuestionWithDetailPanel.setVisible(false);
        QuestionViewPanelForUser.setVisible(true);
        QuestionViewPanel.setVisible(false);
        qaBtnclicked = true;
        recentBtnclicked= bloodBtnclicked = staredBtnclicked = ambulanceBtnclicked = false;
    }
    @FXML
    void QABtnMouseEntered(MouseEvent event) {
        setColor(qaIcon,qaText,"src/Additional-items/QA-colored.png");
    }
    @FXML
    void QABtnMouseExited(MouseEvent event) {
        if(!qaBtnclicked){
            removeColor(qaIcon,qaText,qaIconDefPath);
        }
    }
    @FXML
    void RecentBtnMouseClicked(MouseEvent event) {
        FlowPane iv[] = {recentBtn,staredBtn,qaBtn,bloodBtn,ambulanceBtn};
        RemoveColors();
        setTransparentAllNode(iv);
        setColor(recentIcon,recentText,"src/Additional-items/recent-colored.png");
        recentBtn.setStyle("-fx-background-color: #fff");
        QuestionViewPanel.setVisible(true);
        QuestionViewPanelForUser.setVisible(false);
        QuestionWithDetailPanel.setVisible(false);
        recentBtnclicked = true;
        qaBtnclicked = bloodBtnclicked = staredBtnclicked = ambulanceBtnclicked = false;
    }
    @FXML
    void RecentBtnMouseEntered(MouseEvent event) {
        setColor(recentIcon,recentText,"src/Additional-items/recent-colored.png");
    }
    @FXML
    void RecentBtnMouseExited(MouseEvent event) {
        if(!recentBtnclicked){
            removeColor(recentIcon,recentText,recentIconDefPath);
        }
    }
    @FXML
    void StaredBtnMouseClicked(MouseEvent event) {
        FlowPane iv[] = {recentBtn,staredBtn,qaBtn,bloodBtn,ambulanceBtn};
        RemoveColors();
        setTransparentAllNode(iv);
        setColor(staredIcon,staredText,"src/Additional-items/star-colored.png");
        staredBtn.setStyle("-fx-background-color: #fff");
        staredBtnclicked = true;
        qaBtnclicked = bloodBtnclicked = recentBtnclicked = ambulanceBtnclicked = false;
    }
    @FXML
    void StaredBtnMouseEntered(MouseEvent event) {
        setColor(staredIcon,staredText,"src/Additional-items/star-colored.png");
    }
    @FXML
    void StaredBtnMouseExited(MouseEvent event) {
        if(!staredBtnclicked){
            removeColor(staredIcon,staredText,staredIconDefPath);
        }
    }




    // ==================================================
    // <-------------------------------------------------
    // ==================================================




    // =================================================
    // Profile Event Handling -------------------------------->
    // =================================================



    @FXML
    private Text fullNameText;
    @FXML
    private Text userNameText;
    @FXML
    private Text userTypeText;
    @FXML
    private Text userAgeText;
    @FXML
    private Text userGenderText;
    @FXML
    private Text userBirthDateText;
    @FXML
    private Text userContactText;
    @FXML
    private Text userAddressText;
    @FXML
    private AnchorPane ProfileDetailPanel;

    private Boolean profilePanalClosed = true;
    private String userInformationTxtPath = "src/Database File/userInformation.txt";
    private String loginScenePath = "/FXML Files/loginScene.fxml";


    void setProfileInfo(String uName){
        userName = uName;
        try {
            FileInputStream fis =new FileInputStream(userInformationTxtPath);
            ObjectInputStream ois =new ObjectInputStream(fis);
            HashSet<String> hs =(HashSet<String>) ois.readObject();
            HashMap<String,UserSignUpInfo> hm = (HashMap<String, UserSignUpInfo>) ois.readObject();
            UserSignUpInfo usi =  hm.get(uName);

            fullNameText.setText(usi.Fname.trim() + " " + usi.Lname.trim()+" ");
            userNameText.setText("("+usi.userName.trim()+")");
            userTypeText.setText(usi.userType.trim());
            userGenderText.setText(usi.gender);
            userAgeText.setText(usi.age);
            userAddressText.setText(usi.address);
            userBirthDateText.setText(usi.dateOfBirth);
            userContactText.setText(usi.contact);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void LogoutBtnAction(ActionEvent event) {
        Stage st = (Stage)((Node)event.getSource()).getScene().getWindow();
        try {
            Parent rt = FXMLLoader.load(getClass().getResource(loginScenePath));
            Stage stage =new Stage();
            stage.setScene(new Scene(rt));
            stage.centerOnScreen();
            st.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ProfileBtnAction(MouseEvent event){
        if(profilePanalClosed){
            Timeline tl = new Timeline(new KeyFrame(Duration.millis(200),new KeyValue(ProfileDetailPanel.translateXProperty(),0,Interpolator.EASE_IN)));
            tl.play();
            ProfileDetailPanel.setVisible(true);
            profilePanalClosed = false;
        }
        else if(!profilePanalClosed){
            Timeline tl = new Timeline(new KeyFrame(Duration.millis(200),new KeyValue(ProfileDetailPanel.translateXProperty(),250,Interpolator.EASE_OUT)));
            tl.play();
            ProfileDetailPanel.setVisible(true);
            profilePanalClosed = true;
        }
    }


    // ==================================================
    // <-------------------------------------------------
    // ==================================================



    // =================================================
    // Question View Box and Q&A Details Box Event actions------------------>
    // =================================================







    // ==================================================
    // <-------------------------------------------------
    // ==================================================


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        InitialPosts(QuestionsBox,QuestionWithDetailPanel,QACinfoOfUserTxtFilePath);
    }



}



class UserQuestion implements Serializable{
    String title,detail,userName;
    UserQuestion(String title,String detail,String userName){
        this.title = title;
        this.detail = detail;
        this.userName = userName;
    }
}