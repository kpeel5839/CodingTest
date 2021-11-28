import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args){
        List<Integer> real = new ArrayList<>();
        Stack<Integer> temp = new Stack<>();
        for(int i = 0; i < 4; i++){
            real.add(i);
        }
        for(Integer number : real){
            temp.push(number);
        }
        while(!temp.empty()){
            real.add(temp.pop());
        }
        for(Integer number : real){
            System.out.println(number);
        };
    }
}
