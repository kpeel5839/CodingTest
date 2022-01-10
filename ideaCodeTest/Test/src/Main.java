import java.util.*;
import java.io.*;

public class Main{
    public static class Move{
        int[] dir;
        int cost;
        public Move(int[] dir , int cost){
            this.dir = dir;
            this.cost = cost;
        }
    }
    public static void dfs(int depth , char[] dir){
        if(depth == 3){
            System.out.println(Arrays.toString(dir));// 여기서 출력이 아니라 priorityQueue에다가 집어넣기만 하면 상어 문제에 사용 가능
            return;
        }

        char[] deepDir = dir.clone();

        for(int i = 0; i < 4; i++){
            char add = 0;
            if(i == 0){
                add = '상';
            }else if(i == 1){
                add = '좌';
            }else if(i == 2){
                add = '하';
            }else{
                add = '우';
            }
            deepDir[depth] = add;
            dfs(depth + 1 , deepDir);
        }

    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int r = 7;
        int c = 8;
        Random random = new Random();
//        dfs(0 , new char[]{0 , 0 , 0});
        int[][] map = new int[r][c];
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                map[i][j] = random.nextInt(9);
            }
        }
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        int[][] deepMap = new int[r][c];
        for(int i = 0; i < map.length; i++){
            System.arraycopy(map[i] , 0 , deepMap[i] , 0 , map[i].length);
        }
        System.out.println("next");
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                System.out.print(deepMap[i][j] + " ");
            }
            System.out.println();
        }
    }
}













































