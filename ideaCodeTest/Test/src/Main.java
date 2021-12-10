import java.util.*;
import java.io.*;
public class Main{
    public static List<Integer> number = new ArrayList<>();
    public static void main(String[] args){
        for(int i = 4; i != -1; i--){
            number.add(i);
            System.out.print(i + " ");
        }
        System.out.println();
        Collections.sort(number);
        for(int num : number){
            System.out.print(num + " " );
        }
        System.out.println();

        System.out.println(number.get(number.size() - 1));
    }
}