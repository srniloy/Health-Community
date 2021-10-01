package sample;


import javafx.scene.layout.VBox;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class DatabaseHandler {

    private Hashtable<String,ArrayList> quesVSansHT = new Hashtable<>();
    private String QACinfoOfUserTxtFilePath = "src/Database File/QACinfoOfUser.txt";

    void setQuestionsFile(UserQuestion uqObj,String userName){
//        userQuestionsList.add(uqObj);
//        userVsQuestionHT.put(userName,userQuestionsList);
//        allUserQuestionsList.add(uqObj);



        File file = new File(QACinfoOfUserTxtFilePath);
        try {
            if(file.length() == 0){
                Hashtable<String,Hashtable> mainHM = new Hashtable<>();
                Hashtable<String,ArrayList> userVsQuestionHT = new Hashtable<>();
                ArrayList<UserQuestion> allUserQuestionsList = new ArrayList<>();
                ArrayList<UserQuestion> userQuestionsList =  new ArrayList<>();

                allUserQuestionsList.add(uqObj);
                userQuestionsList.add(uqObj);
                userVsQuestionHT.put(userName,userQuestionsList);
                userVsQuestionHT.put("all",allUserQuestionsList);

                mainHM.put("question",userVsQuestionHT);
                FileOutputStream fos = new FileOutputStream(QACinfoOfUserTxtFilePath);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(mainHM);
                fos.close();
                oos.close();
            }else{
                FileInputStream fis = new FileInputStream(QACinfoOfUserTxtFilePath);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Hashtable<String,Hashtable> mHM = (Hashtable<String, Hashtable>) ois.readObject();
                Hashtable<String,ArrayList<UserQuestion>> uVqHM = (Hashtable<String, ArrayList<UserQuestion>>) mHM.get("question");
                ArrayList<UserQuestion> uqal;
                ArrayList<UserQuestion> alluq;

                if(uVqHM.containsKey(userName)){
                    uqal = uVqHM.get(userName);
                    alluq = uVqHM.get("all");
                    alluq.add(uqObj);
                    uqal.add(uqObj);
                }else {
                    uqal = new ArrayList<>();
                    alluq = new ArrayList<>();
                    alluq.add(uqObj);
                    uqal.add(uqObj);
                }

                uVqHM.put(userName,uqal);
                uVqHM.put("all",alluq);

                mHM.put("question",uVqHM);

                FileOutputStream fos = new FileOutputStream(QACinfoOfUserTxtFilePath);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(mHM);
                fis.close(); fos.close(); ois.close(); oos.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    void setAnswerFile(Hashtable<String,Hashtable> mainHashTable){
        try {
            FileOutputStream fos = new FileOutputStream(QACinfoOfUserTxtFilePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mainHashTable);
            fos.close(); oos.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    Hashtable getMainHT(){
        Hashtable<String,Hashtable> mainHT = null;
        try {
            FileInputStream fis = new FileInputStream(QACinfoOfUserTxtFilePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            mainHT = (Hashtable<String, Hashtable>) ois.readObject();
            fis.close(); ois.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return mainHT;
    }



    void StoreViews(ViewsObj viewsObj,String Qtitle){
        try{
            FileInputStream fis = new FileInputStream(QACinfoOfUserTxtFilePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Hashtable<String,Hashtable> mHM = (Hashtable<String, Hashtable>) ois.readObject();
            Hashtable<String,ViewsObj> viewsHT = new Hashtable<>();
            if(mHM.containsKey("view")){
                viewsHT = (Hashtable<String, ViewsObj>) mHM.get("view");
            }
            if(viewsHT.containsKey(Qtitle)){
                viewsHT.put(Qtitle,viewsObj);
            }else{
                viewsHT.put(Qtitle,viewsObj);
            }
            mHM.put("view",viewsHT);


            FileOutputStream fos = new FileOutputStream(QACinfoOfUserTxtFilePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mHM);
            fos.close(); oos.close(); fis.close(); ois.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    int getViews(String Qtitle){
        int viewsNum = 0;
        File file = new File(QACinfoOfUserTxtFilePath);
        try{
            if(file.length() != 0){
                FileInputStream fis = new FileInputStream(QACinfoOfUserTxtFilePath);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Hashtable<String,Hashtable> mHM = (Hashtable<String, Hashtable>) ois.readObject();
                if(mHM.containsKey("view")){
                    Hashtable<String,ViewsObj> viewsHT = (Hashtable<String, ViewsObj>) mHM.get("view");
                    if(viewsHT.containsKey(Qtitle)){
                        viewsNum = viewsHT.get(Qtitle).viewsNumber;
                    }
                }
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return viewsNum;
    }




}




class AnswerObj implements Serializable{
    private Hashtable<String,Hashtable> mainHashTable;
    AnswerObj(Hashtable<String,Hashtable> mainHashTable){
        this.mainHashTable = mainHashTable;
    }
    Hashtable<String, Hashtable> getMainHT(){
        return mainHashTable;
    }
}