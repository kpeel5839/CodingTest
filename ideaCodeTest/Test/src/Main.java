import java.util.*;
import java.io.*;

public class Main{
    /*
    1. 실험을 해볼 것임
    2. arrayCopy를 하였을 때 그 안에 있는 포인터의 값이 변경되었을 때
    3. 실험 필요 없을 듯 서로 영향을 줄 일을 안할 듯
     */
    public static int[][] map = new int[4][4];
    public static int n = 4;
    public static void unroll(){
        /*
        1. 다시 일렬로 정리하는 함수
         */
        int x = 0;
        for(int i = 0; i < n; i++){
            for(int j = n - 1; j != -1; j--){
                if(map[j][i] != 0){
                    map[map.length - 1][x++] = map[j][i];
                    map[j][i] = 0;
                }
            }
        }
    }
    public static void main(String[] args) throws IOException{
//        dfs(0, new int[]{0 , 0 , 0});
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        Random random = new Random();

        map[3][2] = random.nextInt(9);
        map[2][2] = random.nextInt(9);
        map[3][3] = random.nextInt(9);
        map[2][3] = random.nextInt(9);
        for(int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(map[i]));
        }
        unroll();
        for(int i = 0; i < n; i++) {
            System.out.println(Arrays.toString(map[i]));
        }
    }
}













































