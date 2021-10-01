package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Components extends DatabaseHandler {

    public void HideNodes(Node[] nodes){
        for(Node n:nodes){
            n.setVisible(false);
        }
    }


    public  void InitialUserPosts(VBox questionBox, String userName , String path){
        try {
            File f = new File(path);
            if(f.length() != 0){
                FileInputStream fis =  new FileInputStream(path);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Hashtable<String,Hashtable> mainHM = (Hashtable<String, Hashtable>) ois.readObject();
                Hashtable<String,ArrayList<UserQuestion>> hm = mainHM.get("question");
                if(hm.containsKey(userName)){
                    ArrayList<UserQuestion> ar = hm.get(userName);

                    Hashtable<String,ArrayList<AnswerOfQuestions>> qVSaHT;
                    if(hm.containsKey("answer")){
                        qVSaHT = mainHM.get("answer");
                    }else{
                        qVSaHT = new Hashtable<>();
                    }

                    int solNum=0;
                    for(UserQuestion uq:ar){
                        if(qVSaHT.containsKey(uq.title)){
                            solNum = qVSaHT.get(uq.title).size();
                        }
                        questionBox.getChildren().add(0,addQuestionContainerLt(uq.title,uq.detail,uq.userName,solNum));
                    }
                }
                fis.close();
                ois.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void InitialAllPosts(VBox QuestionBox, String path){  // ------------------------- INITIAL POSTS ADDING
        try {
            File f = new File(path);
            if(f.length() != 0){
                FileInputStream fis =  new FileInputStream(path);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Hashtable<String,Hashtable> mainHM =(Hashtable<String, Hashtable>) ois.readObject();
                Hashtable<String,ArrayList<UserQuestion>> hm = mainHM.get("question");

                Hashtable<String,ArrayList<AnswerOfQuestions>> qVSaHT;
                if(mainHM.containsKey("answer")){
                    qVSaHT = mainHM.get("answer");
                }else{
                    qVSaHT = new Hashtable<>();
                }



                ArrayList<UserQuestion> all = hm.get("all");
                int solNum=0;
                for(UserQuestion uq: all){

                    if(qVSaHT.containsKey(uq.title)){
                        solNum = qVSaHT.get(uq.title).size();
                    }
                    QuestionBox.getChildren().add(0,addQuestionContainerLt(uq.title,uq.detail, uq.userName,solNum));
                }

                fis.close();
                ois.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // GET VALUE FROM CONTROLLER -------------------------------------------------------------------->

    private AnchorPane QuestionWithDetailPanel;
    private AnchorPane AnswerPostingPanel;
    private VBox AnswersPanel;
    private Text qdTitleTextBox;
    private Text qdDetailTextBox;
    private Text qdUserNameTextBox;
    private ImageView qdpCloseBtn;
    private Button addSolutionBtn;
    private Node[] nodes ;
    private String userName;
    private Text mSolutionNumberTextBox;
    private String mainUserName;

    void setQuestionWithDetailPanelsInfo(AnchorPane QuestionWithDetailPanel,Node[] nodes,Text qdTitleTextBox, Text qdDetailTextBox
            ,Text qdUserNameTextBox,ImageView qdpCloseBtn,Button addSolutionBtn,AnchorPane AnswerPostingPanel,VBox AnswersPanel
            , Text mSolutionNumberTextBox,String userName){
        this.qdTitleTextBox = qdTitleTextBox;
        this.qdDetailTextBox = qdDetailTextBox;
        this.qdUserNameTextBox = qdUserNameTextBox;
        this.qdpCloseBtn = qdpCloseBtn;
        this.addSolutionBtn = addSolutionBtn;
        this.nodes = nodes;
        this.AnswersPanel = AnswersPanel;
        this.AnswerPostingPanel = AnswerPostingPanel;
        this.QuestionWithDetailPanel =QuestionWithDetailPanel;
        this.userName = qdUserNameTextBox.getText();
        this.mSolutionNumberTextBox = mSolutionNumberTextBox;
        this.mainUserName = userName;
    }

    void showAllQuestionsPanel(){
        for(int n=0;n<nodes.length;n++){
            if(n==2){
                nodes[n].setVisible(true);
            }
            else{
                nodes[n].setVisible(false);
            }
        }
    }

    void showUserQuestionsPanel(){
        for(int n=0;n<nodes.length;n++){
            if(n==1){
                nodes[n].setVisible(true);
            }
            else{
                nodes[n].setVisible(false);
            }
        }
    }
//    void showAllQuestionsPanel(){
//        HideNodes(nodes);
//        nodes[2].setVisible(true);
//    }
//    void showAllQuestionsPanel(){
//        HideNodes(nodes);
//        nodes[2].setVisible(true);
//    }
//    void showAllQuestionsPanel(){
//        HideNodes(nodes);
//        nodes[2].setVisible(true);
//    }












    // <--------------------------------------------------------------------------------------------------------------===================================











    public VBox addQuestionContainerLt(String qTitle,String qdetail,String nameOfUser,int solutionsNumber){
        Parent p = null;
        try {
            p = FXMLLoader.load(getClass().getResource("/FXML Files/partsOfPanel.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene s = new Scene(p);
        VBox v =(VBox) s.lookup("#QuestionContainerLt");
        Text Qtitle =(Text) v.lookup("#qTitleOfLtContainer");
        Text Qdetail =(Text) v.lookup("#qDetailOfLtContainer");
        Text QUserName =(Text) v.lookup("#qAskedUserNameOfLtContainer");
        Text solutionNum = (Text) v.lookup("#questionSolutionsNumTextBox") ;
        Text viewNum = (Text) v.lookup("#questionViewsNumTextBox") ;
        Qtitle.setText(qTitle);
        Qdetail.setText(qdetail);
        QUserName.setText(nameOfUser);
        solutionNum.setText(String.valueOf(solutionsNumber));

        AtomicInteger viewsNumber = new AtomicInteger();
        viewsNumber.set(getViews(qTitle));

        viewNum.setText(String.valueOf(viewsNumber));


        Qtitle.setOnMouseClicked(mouseEvent -> { //--------------------======================================================================
            String qtitleVar = ((Text) mouseEvent.getTarget()).getText();
            viewsNumber.getAndIncrement();
            viewNum.setText(String.valueOf(viewsNumber));
            System.out.println("title main: "+qTitle);
            qdTitleTextBox.setText(qTitle);
            qdDetailTextBox.setText(qdetail);
            qdUserNameTextBox.setText(nameOfUser);

            ViewsObj viewsObj = new ViewsObj(viewsNumber.get());
            StoreViews(viewsObj,qTitle);

//            HideNodes(nodes);
            nodes[2].setVisible(false);
            QuestionWithDetailPanel.setVisible(true);

            ArrayList<HBox> answerPanelsList = new ArrayList<>();
            Hashtable<String,Hashtable> mainHT = getMainHT();
            Hashtable<String,ArrayList> qVSaHT;
            if(mainHT.containsKey("answer")){
                qVSaHT = mainHT.get("answer");
            }else{
                qVSaHT = new Hashtable<>();
            }

            if(qVSaHT.containsKey(qTitle)){
                ArrayList<AnswerOfQuestions> alhb = qVSaHT.get(qTitle);
                int num = 1;
                for(AnswerOfQuestions aqs: alhb){
                    answerPanelsList.add(addAnswers(aqs,num));
                    num++;
                }
                AnswersPanel.getChildren().addAll(answerPanelsList);
            }
            solutionNum.setText(String.valueOf(AnswersPanel.getChildren().size()));

            ArrayList<AnswerOfQuestions> answersObjList;
            if(qVSaHT.containsKey(qTitle)){
                answersObjList = qVSaHT.get(qTitle);
            }else {
                answersObjList = new ArrayList<>();
            }




            addSolutionBtn.setOnMouseClicked(event -> { //-------------------------------------------------------------------------------
//                HideNodes(nodes);
                AnswerPostingPanel.setVisible(true);

                Button answerPostBtn = (Button) AnswerPostingPanel.lookup("#answerPostingPostBtn");



                answerPostBtn.setOnMouseClicked(event1 -> { //---------------------------------------------------------------------------------------

                    TextArea ta = (TextArea)  AnswerPostingPanel.lookup("#answerPostingTextBox");
                    String answerText = ta.getText();
                    AnswerOfQuestions aq = new AnswerOfQuestions(answerText,mainUserName,AnswersPanel.getChildren().size()+1);



                    answersObjList.add(aq);
                    answerPanelsList.clear();
                    qVSaHT.put(qTitle, answersObjList);

                    if(qVSaHT.containsKey(qTitle)){
                        ArrayList<AnswerOfQuestions> alhb = qVSaHT.get(qTitle);
                        int num=1;
                        for(AnswerOfQuestions aqu:alhb){
                            answerPanelsList.add(addAnswers(aqu,num));
                            num++;
                        }
                    }
                    AnswersPanel.getChildren().addAll(answerPanelsList);
                    solutionNum.setText(String.valueOf(AnswersPanel.getChildren().size()));
                    mSolutionNumberTextBox.setText(String.valueOf(AnswersPanel.getChildren().size()));

                    mainHT.put("answer",qVSaHT);
                    setAnswerFile(mainHT); //--------------------------------

//                    HideNodes(nodes);
                    AnswerPostingPanel.setVisible(false);
                    ta.clear();


                });

            });


            nodes[3].visibleProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    if(observableValue.getValue() == false){
                        AnswersPanel.getChildren().removeAll(answerPanelsList);
                    }
                }
            });



        });

        qdpCloseBtn.setOnMouseClicked(event -> {
            QuestionWithDetailPanel.setVisible(false);
            nodes[2].setVisible(true);
        });



        return v;
    }




    HBox addAnswers(AnswerOfQuestions aq,int answersNumber){
        Parent root = null;
        try{
            root = FXMLLoader.load(getClass().getResource("/FXML Files/partsOfPanel.fxml"));
        }catch (IOException e){
            System.out.println(e.toString());
        }
        Scene sc = new Scene(root);
        HBox AnswerPanel = (HBox) sc.lookup("#AnswerPanel");
        Text answerTextBox = (Text) sc.lookup("#answerTextBox");
        Text answeredUserName = (Text) sc.lookup("#answeredUserName");
        Text answerNumberTextBox = (Text) sc.lookup("#answerNumberTextBox");
        Button answerLikeBtn = (Button) sc.lookup("#answerLikeBtn");
        Button answerCommentBtn = (Button) sc.lookup("#answerCommentBtn");

        TextField answerCommentBox = (TextField) sc.lookup("#answerCommentBox");
        ImageView answerCommentPostBtn = (ImageView) sc.lookup("#answerCommentPostBtn");


        VBox CommentContainerPanel = (VBox) sc.lookup("#CommentContainerPanel");
        AtomicInteger commentNumber = new AtomicInteger(1);
        // -----
        answerTextBox.setText(aq.answerText);
        answeredUserName.setText(aq.userName);
        answerNumberTextBox.setText(String.valueOf(answersNumber));
        // ------

        answerCommentPostBtn.setOnMouseClicked(event2 -> { //--------------------------------------------------------------------------
            if(answerCommentBox.getCharacters().length() >= 1){
                Parent parent = null;
                try{parent = FXMLLoader.load(getClass().getResource("/FXML Files/partsOfPanel.fxml"));}
                catch (IOException e){System.out.println(e.toString());}

                Scene scene = new Scene(parent);

                HBox CommentPanel = (HBox) scene.lookup("#commentPanel");
                Text commentNumberTextBox = (Text) scene.lookup("#commentNumberTextBox");
                Text commentTextBox = (Text) scene.lookup("#commentTextBox");
                Text commentedUserNameTextBox = (Text) scene.lookup("#commentedUserNameTextBox");

                commentTextBox.setText(answerCommentBox.getText() +" - ");
                commentedUserNameTextBox.setText(aq.userName);
                commentNumberTextBox.setText(String.valueOf(commentNumber));
                answerCommentBox.clear();
                commentNumber.getAndIncrement();
                CommentContainerPanel.getChildren().add(CommentPanel);
            }
        });
        return AnswerPanel;
    }





    protected void setTransparentAllNode(Node iv[]){
        for(Node f: iv){
            f.setStyle("-fx-background-color: transparent");
        }
    }

    protected  void setColor(ImageView iv, Text txt,String path){
        try {
            iv.setImage(new Image(new FileInputStream(path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        txt.setFill(Color.valueOf("#09f"));
    }

    protected  void removeColor(ImageView iv, Text txt,String path){
        try {
            iv.setImage(new Image(new FileInputStream(path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        txt.setFill(Color.valueOf("black"));
    }
    protected  void removeColorForAll(ImageView iv[], Text txt[],String path[]){
        try {
            for (int i=0; i<iv.length;i++){
                iv[i].setImage(new Image(new FileInputStream(path[i])));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(Text t: txt){
            t.setFill(Color.valueOf("black"));
        }
    }


}



//===============================================
// NEW CLASS - QUESTION POST DESIGN NODE ADDING
//===============================================












