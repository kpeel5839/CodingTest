import java.util.*;
import java.io.*;
public class Main{
    public static void main(String[] args){
        for(int i = 0; i < 4; i++){
            System.out.println("now index : " + i);
            System.out.println("reverse index : " + (i + 1 == 2 ? 0 : i + 1 == 4 ? 2 : i + 1));
        }
    }
}