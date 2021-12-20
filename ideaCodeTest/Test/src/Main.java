import java.util.*;
import java.io.*;

public class Main {
    public static int virusCount = 4;
    public static int m = 3;
    public static HashSet<String> sequence = new HashSet<>();
    public static void getSequence(String s){
        if(virusCount == s.length()){
            if(check('1' , s)) {
                sequence.add(s);
            }
            return;
        }
        for(int i = 0; i < 2; i++){
            s += i;
            getSequence(s);
            s = s.substring(0 , s.length() - 1);
        }
    }
    public static boolean check(char ch , String s){
        int count = 0;
        for(int i = 0; i < s.length(); i++){
            if(ch == s.charAt(i)){
                count++;
            }
        }
        if(count == m){
            return true;
        }
        else{
            return false;
        }
    }
    public static void main(String[] args){
        getSequence("");
        System.out.println(sequence);
    }
}
















































