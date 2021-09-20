package sample;


import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class testPurpose {
    public static void main(String[] args) {
        try{
            FileInputStream fis = new FileInputStream("src/Database File/QACinfoOfUser.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            HashMap<String,HashMap> mainHM = (HashMap<String, HashMap>) ois.readObject();
            HashMap<String,ArrayList<UserQuestion>> hm = mainHM.get("question");
            ArrayList<UserQuestion> ar = hm.get("all");
            for(UserQuestion uq:ar){
                System.out.println("user: "+ uq.userName);
                System.out.println("title: "+uq.title);
                System.out.println("detail: "+uq.detail);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
class a{
    String x;
    a(String x){
        this.x = x;
    }
}
