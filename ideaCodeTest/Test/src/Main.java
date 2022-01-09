import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        String[] word1 = {"aa" , "aab" , "aaa" , "abc" , "ac", "ab"};
        Arrays.sort(word1);
        String[] word2 = {"daa" , "dadba" , "dab"};
        Arrays.sort(word2);

        System.out.println(Arrays.toString(word1));
        System.out.println(Arrays.toString(word2));
    }
}













































