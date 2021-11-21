import java.util.*;
import java.io.*;

public class Main {
    public static int cleanCount = 0;
    public static int[] rollY = {-1 , 0 , 1 , 0};
    public static int[] rollX = {0 , 1 , 0 , -1}; //0 북 , 1 동 , 2 남 , 3 서
    public static int r, c;
    public static int x , y , dir;
    public static int[][] map;
    public static int stack = 0;

    public static void main(String[] args)throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        map = new int[r][c];

        st = new StringTokenizer(input.readLine());
        y = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());
        dir = Integer.parseInt(st.nextToken());

        for(int i = 0; i < r; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < c; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        map[y][x] = 3; //청소기 위치 생성

        instruct();
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                if(map[i][j] == 2) {
                    cleanCount++;
                }
            }
        }
        System.out.println(cleanCount);
    }

    public static int clean(){
        int endR=-1 , endC=-1;
        if (dir == 1 || dir == 2) {endR = r;endC = c;}
        if(stack == 4){
            int tempDir = (dir + 2) % 4;
            if((y + rollY[tempDir] == endR || x + rollX[tempDir] == endC) || map[y + rollY[tempDir]][x + rollX[tempDir]] == 1){
                map[y][x] = 2; //청소 하고 떠남
                return -1;
            } //뒷 공간이 벽이거나 , 아니면 못가는 공간일떄
            else{ //뒷 공간으로 갈 수 있을 때
                stack = 0;
                map[y][x] = 2;
                y += rollY[tempDir];
                x += rollX[tempDir];
                map[y][x] = 3;
                return 1;
            }
        }
        else {
            dir = --dir < 0 ? 3 : dir;
            if ((y + rollY[dir] == endR || x + rollX[dir] == endC) || map[y + rollY[dir]][x + rollX[dir]] != 0) {
                stack++;
                return 1;
            } //일단 stack 1 쌓임 옆에 공간이 없거나 아니면 이미 청소를 했거나 벽일 떄
            map[y][x] = 2;
            y += rollY[dir];
            x += rollX[dir];
            map[y][x] = 3;
            stack = 0;
            return 1;
        }
    }

    public static void instruct(){
        while(true){
            int success = clean();
            if(success == -1){
                break;
            }
        }
    }
}
