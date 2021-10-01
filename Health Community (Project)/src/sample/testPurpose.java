package sample;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.*;

public class testPurpose {
    public static void main(String[] args) {
        String QACinfoOfUserTxtFilePath = "src/Database File/QACinfoOfUser.txt";

        try{
            FileInputStream fis = new FileInputStream(QACinfoOfUserTxtFilePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Hashtable<String,Hashtable> mainHM = (Hashtable<String, Hashtable>) ois.readObject();
            Hashtable<String,ArrayList<AnswerOfQuestions>> hm = (Hashtable<String, ArrayList<AnswerOfQuestions>>) mainHM.get("answer");
            for(Map.Entry<String,ArrayList<AnswerOfQuestions>> entry:hm.entrySet()){
                String user = entry.getKey();
                System.out.println("title: "+user);

                if(true){
                    for (AnswerOfQuestions uq: entry.getValue()){
                        System.out.println(uq.userName);
                        System.out.println(uq.answerNumber);
                        System.out.println(uq.answerText);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
