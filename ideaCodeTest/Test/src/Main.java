import java.util.*;
import java.io.*;
public class Main{
    public static void main(String[] args){
        int n = 3;
        int[][] map = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                map[i][j] = j;
            }
        }

        System.out.println("original break test");
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){ //그냥 일반적인 break 문 test
                System.out.print(map[i][j] + " ");
                if(map[i][j] == 1){
                    break;
                }
            }
            System.out.println();
        }

        System.out.println("loop break test ");
        Loop1:
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                System.out.print(map[i][j] + " " );
                if(map[i][j] == 1){
                    break Loop1;
                }
            }
            System.out.println();
        }
    }
}