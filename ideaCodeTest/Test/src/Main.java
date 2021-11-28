import java.util.*;
import java.io.*;

public class Main{
    public static HashSet<String> sequence = new HashSet<>();
    public static int count;
    public static int length = 13;
    public static void makeSequence(String string , int lengthCount){
        if(string.length() > count){
            if(countLetter(string) > count){
                return;
            }
        }
        if(lengthCount == length){
            sequence.add(string);
            return;
        }
        for(int i = 0; i < 2; i++){
            string += i;
            makeSequence(string , lengthCount + 1);
            string = string.substring(0 , string.length() - 1);
        }
    }
    public static int countLetter(String str){
        int letterCount = 0;
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == '1'){
                letterCount++;
            }
        }
        return letterCount;
    }
    public static void main(String[] args) throws IOException{
        Scanner input = new Scanner(System.in);
        count = input.nextInt();
        makeSequence("", 0);
        for(String string : sequence){
            System.out.println(string);
        }
        System.out.println(Math.abs(2 - 1) + Math.abs(1 - 2));
    }
}
