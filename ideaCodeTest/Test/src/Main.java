import java.io.*;
import java.util.*;

public class Main {
    public static List<String> stringList = new ArrayList<>();

    public static boolean inArray(String string) {
        for (String compare : stringList) {
            if(compare.equals(string)){return true;}
        }
        return false;
    }

    public static void bfs(String string, int count) {
        if (count == 5) {
            if(inArray(string)){ //중복 했던 게 존재했음
                return;
            }
            else{
                stringList.add(string);
                return;
            }
        }
        for (int i = 0; i < 4; i++) {
            string += Integer.toString(i);
            bfs(string, count + 1);
            string = string.substring(0, string.length() - 1);
        }
    }

    public static void main(String[] args) {
        bfs("", 0);
        System.out.println(stringList.size());
    }
}
