import java.util.*;
import java.io.*;

public class Main{
    public static int[][] map = new int[3][3];
    public static void rotate(Point start , Point finish){
        /*
        1. 첫점과 끝점을 받아서 for문으로 90도 돌려준다.
        2. 그냥 map을 돌려주기만 하면 역할 끝
        3. 원래 읽던 대로가 아니라 왼쪽 하단부터 열 순서대로 읽어서 집어넣으면 됨
        4. 메모리를 조금 아끼기 위해서 start , finish를 봐서 세로는 finish - start + 1이고 (행) 가로는 finish.x - start.x + 1 (열)이다
        5. 이 사이즈대로 int 배열을 선언해놓고 카피 해놓은 다음에 deepCopy로
        6. 그 다음 카피 해놓은 것에서 순서대로 읽어서 map의 내용을 바꾸면 될 듯
         */
        int[][] tempMap = new int[finish.y - start.y + 1][finish.x - start.x + 1];
        for(int i = start.y; i <= finish.y; i++){
            for(int j = start.x; j <= finish.x; j++){
                tempMap[i][j] = map[i][j];
            }
        }
        int y = start.y;
        int x = start.x; // 1 , 1 - 2, 2 라고 가정해보자
        for(int i = start.x; i <= finish.x; i++){
            for(int j = finish.y; j != start.y - 1; j--){
//                System.out.println("y : " + y + " x : " + x);
                map[y][x++] = tempMap[j][i];
            }
            y++;
            x %= 3;
        }
    }
    public static class Point{
        int y;
        int x;
        public Point(int y , int x){
            this.y = y;
            this.x = x;
        }
    }
    public static void main(String[] args) throws IOException{
        Random random = new Random();
        for(int i = 0; i < 3; i++){
            for(int j = 0;j < 3; j++){
                map[i][j] = random.nextInt(9);
            }
        }
        System.out.println("--------before----------");
        for(int i = 0; i < 3; i++){
            for(int j = 0;j < 3; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        rotate(new Point(0, 0) , new Point(2, 2));
        System.out.println("--------after----------");
        for(int i = 0; i < 3; i++){
            for(int j = 0;j < 3; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}














































