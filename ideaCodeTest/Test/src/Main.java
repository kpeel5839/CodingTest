import java.util.*;
import java.io.*;

public class Main{
    public static int[][] map;
    public static int n = 3;
    public static void rotate(){
        /*
        1. 맵을 90도 반시계 방향으로 회전시켜주는 역할
        2. 끝에 열부터 순서대로 읽어서 위치시키면 됨
        3. 끝에 열 첫행 부터 순서대로 읽으면서 원래의 map에다가 투입
         */
        int[][] copyMap = new int[n][n];
        for(int i = 0; i < n; i++){
            System.arraycopy(map[i] , 0 , copyMap[i] , 0 , map[i].length);
        }
        int y = 0;
        int x = 0;
        for(int i = n - 1; i != -1; i--){
            for(int j = 0; j < n; j++){
                map[y][x++] = copyMap[j][i];
            }
            y++;
            x %= n;
        }
    }
    public static void main(String[] args) throws IOException{
        Random random = new Random();
        map = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0;j  < n; j++){
                map[i][j] = random.nextInt(9);
            }
        }
        System.out.println("------------rotate before----------------");
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        rotate();
        System.out.println("------------rotate after-----------------");
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}














































