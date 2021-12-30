import java.util.*;
import java.io.*;

public class Main{
    /*
    -- 실험 설계
    1. 만약 배열을 deepcopy를 했는데 거기서 배열 안에 class로 존재하고 그 class를 변경하면? 그러면 결과가 바뀔까?
    2. 그럴려면 point 클래스를 선언하고 random number로 2차원 배열로 채워넣을 것임, 그 다음에 그 안에 있는 point 값을 변경하면 실제 값은 변경이 될까?
     */
    public static class Point{
        int value;
        public Point(int value){
            this.value = value;
        }
        @Override
        public String toString(){
            return Integer.toString(value);
        }
    }
    public static void dfs(Point[][] map){
        Random random = new Random();
        Point[][] deepMap = new Point[3][3];
        for(int i = 0;i < map.length; i++){
            System.arraycopy(map[i] , 0 , deepMap[i] , 0 , map[i].length);
        }
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                deepMap[i][j] = new Point(random.nextInt(9));
            }
        }
        System.out.println("after map modified");
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                System.out.print(deepMap[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) throws IOException{
        Random random = new Random();
        Point[][] map = new Point[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                map[i][j] = new Point(random.nextInt(9));
            }
        }
        System.out.println("before array status");
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        dfs(map);
        System.out.println("after array status");
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println(-1 * 2);
    }
}














































