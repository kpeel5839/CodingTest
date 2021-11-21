import java.util.*;
import java.io.*;

public class Main{
    public static HashSet<String> stringList = new HashSet<String>();
    public static int count = 8;
    public static void makeSequence(){

    }
    public static void main(String[] args){
        String string = "<b>hello</b><b>hello</b>";
        string = string.replace("<b>" , "");
        string = string.replace("</b>" , "");
        System.out.println(string);
    }
}
