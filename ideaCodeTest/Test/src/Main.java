import java.util.*;
import java.io.*;
public class Main{
    public static String st= "32451";
    public static List<Integer> number = new ArrayList<>();
    public static void main(String[] args){
        int num = 3;
        for(int i = 0; i < 4; i++){
            number.add(i);
        }
        System.out.println(number.get(1));
        System.out.println(st.indexOf(Integer.toString(num)));
    }
}