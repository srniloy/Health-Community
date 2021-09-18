package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

public class Components {


    int getQInfoSerial(String path){  // ------------------------------- GET FILE SERIAL NO
        int user = 0;
        try {
            FileInputStream bf = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(bf);


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    int getFilesSerialNo(String path){  // ------------------------------- GET FILE SERIAL NO
        int user = 0;
        try {
            File f = new File(path);
            BufferedReader bf = new BufferedReader(new FileReader(f));

            while ((bf.readLine() != null)){
                user++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public void InitialPosts(VBox QuestionBox,Node n, String path){  // ------------------------- INITIAL POSTS ADDING
        ArrayList<ArrayList<String>> QInfoList = new ArrayList<>();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(path));
            String s;

            while ((s=bf.readLine())!=null){
                String[] str = s.split("~");
                ArrayList<String> al = new ArrayList<>();
                al.add(str[0]);
                al.add(str[1]);
                al.add(str[2]);
                QInfoList.add(al);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = QInfoList.size()-1; i >= 0; i--) {
            QuestionBox.getChildren().add(addQuestionContainerLt(QInfoList.get(i).get(1),QInfoList.get(i).get(2),i,i+15,i+12,"Mr Niloy",n));

        }

    }



    VBox addQuestionContainerLt(String qTitle,String qdetail,int numOfSolutions,int numOfViews,int numOfLikes,String nameOfUser,Node n){
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
        Button solutionNum = (Button) v.lookup("#qSolutionNumBtnOfLtContainer") ;
        Button viewNum = (Button) v.lookup("#qViewsNumBtnOfLtContainer") ;
        Button likeNum = (Button) v.lookup("#qLikesNumBtnOfLtContainer") ;
        Qtitle.setText(qTitle);
        Qdetail.setText(qdetail);
        QUserName.setText(nameOfUser);
        solutionNum.setText(numOfSolutions + " Solutions");
        viewNum.setText(numOfViews + " Views");
        likeNum.setText(numOfLikes + " Likes");
        Qtitle.setOnMouseClicked(mouseEvent -> {
            System.out.println("Clicked");
//            QuestionViewPanel.setVisible(false);
            n.setVisible(true);
//            Controller c = new Controller();
//            c.QTextAction();
        });
        return v;
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












