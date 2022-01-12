import java.util.*;
import java.io.*;

public class Main{
    /*
    1. 실험을 해볼 것임
    2. arrayCopy를 하였을 때 그 안에 있는 포인터의 값이 변경되었을 때
    3. 실험 필요 없을 듯 서로 영향을 줄 일을 안할 듯함
     */
    public static void dfs(int depth , int[] arr){
        if(depth == 3){
            for(int i = 0; i < 3; i++){
                System.out.print(arr[i]);
            }
            System.out.println();
            return;
        }
        for(int i = 1; i < 5; i++){
            arr[depth] = i;
            dfs(depth + 1 , arr);
        }
    }
    public static void main(String[] args) throws IOException{
        dfs(0, new int[]{0 , 0 , 0});
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    }
}













































