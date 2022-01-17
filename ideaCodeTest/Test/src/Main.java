import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        int index = 0;
        for(int i = 0; i < list.size(); i++){
            index++;
//            System.out.println(list.get(i));
            if(index % 2 == 0){
                list.remove(i);
                i--;
            }
        }

//        System.out.println(list.size());
    }
}












































