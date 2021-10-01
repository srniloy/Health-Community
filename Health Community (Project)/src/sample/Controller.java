package sample;

import com.sun.javafx.collections.ArrayListenerHelper;
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
import java.util.*;

public class Controller extends Components implements Initializable {

    @FXML
    private TextField searchTextBox;
    @FXML
    private VBox AllQuestionsBox;

    @FXML
    private StackPane HomeContentsPanel;

    private  String userName;
    private DatabaseHandler databaseHandler = new DatabaseHandler();


    // Question Posting Panel Controlls------------------------------------>

    @FXML
    private AnchorPane QuestionPostingPanel;
    @FXML
    private TextField questionPostingPanelTitle;
    @FXML
    private TextArea questionPostingPanelDetail;
    @FXML
    private  ScrollPane AllQuestionViewPanel;
    @FXML
    private ScrollPane QuestionViewPanelForUser;
    @FXML
    private VBox QuestionsBoxForUser;

    private String QACinfoOfUserTxtFilePath = "src/Database File/QACinfoOfUser.txt";

    @FXML
    void  AddQuestionBtnAction(ActionEvent event) {
        Node[] node = {QuestionViewPanelForUser,QuestionWithDetailPanel,AllQuestionViewPanel};
        HideNodes(node);
        QuestionPostingPanel.setVisible(true);
    }

    @FXML
    void questionPostingPanelCloseBtnAction(MouseEvent event) {
        questionPostingPanelTitle.clear();
        questionPostingPanelDetail.clear();
        QuestionPostingPanel.setVisible(false);
        QuestionViewPanelForUser.setVisible(true);
    }



    private Hashtable<String,ArrayList> userVsQuestionHT = new Hashtable<>();
    private ArrayList<UserQuestion> userQuestionsList = new ArrayList<>();
    private ArrayList<UserQuestion> allUserQuestionsList = new ArrayList<>();




    @FXML
    void questionPostingPanelPostBtn(ActionEvent event) {
        String title = questionPostingPanelTitle.getText();
        String detail = questionPostingPanelDetail.getText();
        UserQuestion questionObject = new UserQuestion(title,detail,userName);


        QuestionsBoxForUser.getChildren().add(0,addQuestionContainerLt(title,detail,userName,0));
        AllQuestionsBox.getChildren().add(0,addQuestionContainerLt(title,detail,userName,0));
        databaseHandler.setQuestionsFile(questionObject,userName);

        questionPostingPanelTitle.clear();
        questionPostingPanelDetail.clear();

        RecentBtnClickedAction();
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
    private FlowPane qaBtn;
    @FXML
    private FlowPane bloodBtn;
    @FXML
    private FlowPane ambulanceBtn;
    @FXML
    private AnchorPane QuestionWithDetailPanel;
    @FXML
    ScrollPane AmbulancePanel;
    @FXML
    ScrollPane BloodPanel;



    private String recentIconDefPath = "src/Additional-items/recent-trans.png";
    private String qaIconDefPath = "src/Additional-items/QA-trans.png";
    private String bloodIconDefPath = "src/Additional-items/blood-donation-trans.png";
    private String ambulanceIconDefPath = "src/Additional-items/ambulance-trans.png";

    private Boolean ambulanceBtnclicked = false;
    private Boolean recentBtnclicked = false;
    private Boolean qaBtnclicked = false;
    private Boolean bloodBtnclicked = false;
    private Boolean staredBtnclicked = false;

    void RemoveColors(){
        ImageView iv[] = {recentIcon,qaIcon,bloodIcon,ambulanceIcon};
        Text txt[] = {recentText,qaText,bloodText,ambulanceText};
        String path[] = {recentIconDefPath,qaIconDefPath,bloodIconDefPath,ambulanceIconDefPath};
        removeColorForAll(iv,txt,path);
    }
    @FXML
    void AmbulanceBtnMouseClicked(MouseEvent event) {
        FlowPane fp[] = {recentBtn,qaBtn,bloodBtn,ambulanceBtn};
        RemoveColors();
        setTransparentAllNode(fp);
        setColor(ambulanceIcon,ambulanceText,"src/Additional-items/ambulance-colored.png");
        ambulanceBtn.setStyle("-fx-background-color: #fff");
        Node[] nodes = {QuestionPostingPanel,AllQuestionViewPanel,QuestionWithDetailPanel,AnswerPostingPanel,BloodPanel,AmbulancePanel};
        HideNodes(nodes);
        AmbulancePanel.setVisible(true);
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
        FlowPane iv[] = {recentBtn,qaBtn,bloodBtn,ambulanceBtn};
        RemoveColors();
        setTransparentAllNode(iv);
        setColor(bloodIcon,bloodText,"src/Additional-items/blood-donation-colored.png");
        bloodBtn.setStyle("-fx-background-color: #fff");
        Node[] nodes = {QuestionPostingPanel,AllQuestionViewPanel,QuestionWithDetailPanel,AnswerPostingPanel,AmbulancePanel,BloodPanel};
        HideNodes(nodes);
        BloodPanel.setVisible(true);
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
    void QABtnMouseClicked(MouseEvent event) {QABtnClickedAction();}
    private void QABtnClickedAction(){
        FlowPane iv[] = {recentBtn,qaBtn,bloodBtn,ambulanceBtn};
        RemoveColors();
        setTransparentAllNode(iv);
        setColor(qaIcon,qaText,"src/Additional-items/QA-colored.png");
        qaBtn.setStyle("-fx-background-color: #fff");
        Node[] nodes = {QuestionPostingPanel,AllQuestionViewPanel,QuestionWithDetailPanel,AnswerPostingPanel,AmbulancePanel,BloodPanel};
        HideNodes(nodes);
        QuestionViewPanelForUser.setVisible(true);
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
    void RecentBtnMouseClicked(MouseEvent event) {RecentBtnClickedAction();}
    private void RecentBtnClickedAction(){
        FlowPane iv[] = {recentBtn,qaBtn,bloodBtn,ambulanceBtn};
        RemoveColors();
        setTransparentAllNode(iv);
        setColor(recentIcon,recentText,"src/Additional-items/recent-colored.png");
        recentBtn.setStyle("-fx-background-color: #fff");
        Node[] nodes = {QuestionPostingPanel,QuestionViewPanelForUser,QuestionWithDetailPanel,AmbulancePanel,BloodPanel};
        HideNodes(nodes);
        AllQuestionViewPanel.setVisible(true);
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
        passingDQPInfo();
        InitialUserPosts(QuestionsBoxForUser,userName,QACinfoOfUserTxtFilePath);
    }

    @FXML
    void LogoutBtnAction(ActionEvent event) {
        Stage st = (Stage)((Node)event.getSource()).getScene().getWindow();
        try {
            Parent rt = FXMLLoader.load(getClass().getResource(loginScenePath));
            Stage stage =new Stage();
            stage.setScene(new Scene(rt));
            stage.setTitle("Health Community (Log in)");
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

    @FXML
    private Text qdTitleTextBox;
    @FXML
    private Text qdDetailTextBox;
    @FXML
    private Text qdUserNameTextBox;
    @FXML
    private ImageView qdpCloseBtn;
    @FXML
    private TextArea answerPostingTextBox;
    @FXML
    private Text mSolutionNumberTextBox;
    @FXML
    private AnchorPane AnswerPostingPanel;
    @FXML
    private VBox AnswersPanel;
    @FXML
    private Button addSolutionBtn;
    private int answersNumber = 0;

    void passingDQPInfo(){
        Node[] nodes = {QuestionPostingPanel,QuestionsBoxForUser,AllQuestionViewPanel,QuestionWithDetailPanel,AnswerPostingPanel};
        setQuestionWithDetailPanelsInfo(QuestionWithDetailPanel,nodes,qdTitleTextBox,qdDetailTextBox,qdUserNameTextBox,qdpCloseBtn
                ,addSolutionBtn,AnswerPostingPanel,AnswersPanel,mSolutionNumberTextBox,userName);
    }



    @FXML
    void AddSolutionBtnAction(ActionEvent e){
//        System.out.println("niloy's test: " +qdTitleTextBox.getText());
//        AnswerPostingPanel.setVisible(true);
//        Node[] nodes = {QuestionPostingPanel,QuestionsBoxForUser,AllQuestionViewPanel,QuestionWithDetailPanel};
//        HideNodes(nodes);
    }

    @FXML
    void answerPostingPostBtnAction(ActionEvent event){
//        answersNumber++;
//        mSolutionNumberTextBox.setText(answersNumber+" Solutions ->");
//
//
//
//        AnswerOfQuestions aq = new AnswerOfQuestions(answerPostingTextBox.getText(),userName,answersNumber);
//        HashMap<String,ArrayList<AnswerOfQuestions>> hm = null;
//        ArrayList<AnswerOfQuestions> ar = null;
//
//        try{
//            FileInputStream fis = new FileInputStream(QACinfoOfUserTxtFilePath);
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            FileOutputStream fos = new FileOutputStream(QACinfoOfUserTxtFilePath);
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//
//            Object ob = ois.readObject();
//            HashMap<String,HashMap> mainHM = (HashMap<String,HashMap>) ob;
//            String questionText = qdTitleTextBox.getText();
//
//            if(mainHM.containsKey("answer")){
//                hm = mainHM.get("answer");
//                ar = hm.get(questionText);
//                ar.add(aq);
//                hm.put(questionText,ar);
//                mainHM.put("answer",hm);
//                oos.writeObject(mainHM);
//            }
//            else{
//                hm = new HashMap<>();
//                ar = new ArrayList<>();
//                ar.add(aq);
//                hm.put(questionText,ar);
//                mainHM.put("answer",hm);
//                oos.writeObject(mainHM);
//            }
//            fis.close(); fos.close(); ois.close(); oos.close();
//        }catch (IOException | ClassNotFoundException e){
//            e.printStackTrace();
//        }
//
//        AnswersPanel.getChildren().add(addAnswers(aq));
//        answerPostingTextBox.clear();
//        QuestionWithDetailPanel.setVisible(true);
//        Node[] nodes = {QuestionPostingPanel,QuestionsBoxForUser,AllQuestionViewPanel,AnswerPostingPanel};
//        HideNodes(nodes);
//        setAnswersHM(hm);
    }
    @FXML
    void AnswerPostingPanelCloseBtn(MouseEvent event){
        AnswerPostingPanel.setVisible(false);
    }


    // ==================================================
    // <-------------------------------------------------
    // ==================================================


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        passingDQPInfo();
        InitialAllPosts(AllQuestionsBox,QACinfoOfUserTxtFilePath);
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

class AnswerOfQuestions implements Serializable{
    String answerText ,userName;
    int answerNumber;
    AnswerOfQuestions(String answerText,String userName,int answerNumber){
        this.answerNumber = answerNumber;
        this.answerText = answerText;
        this.userName = userName;
    }
}

class ViewsObj implements Serializable{
    int viewsNumber;
    ViewsObj(int viewsNumber){
        this.viewsNumber = viewsNumber;
    }
}