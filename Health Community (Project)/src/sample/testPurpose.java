package sample;


import java.util.HashMap;
import java.util.HashSet;

public class testPurpose {
    public static void main(String[] args) {
            a A = new a("niloy");
            a B = new a("niloy");
            HashSet<a> hs = new HashSet<>();
            hs.add(A);
        System.out.println(hs);
    }
}
class a{
    String x;
    a(String x){
        this.x = x;
    }
}
class b{
    String y;
    b(String y){
        this.y = y;
    }
}