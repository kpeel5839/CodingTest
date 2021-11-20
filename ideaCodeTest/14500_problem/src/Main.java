import java.util.*;
import java.io.*;
public class Main {
    public static int r, c , y, x;
    public static int[][] map;
    public static int max = 0;
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(input.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        map = new int[r][c];
        for(int i = 0; i < r; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < c; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                int temp = 0;
                y = i;
                x = j;
                for(int c = 0; c < 5; c++){
                    temp = figure(c);
                    if(max < temp){
                        max = temp;
                    }
                }
            }
        }
        System.out.println(max);
    }
    public static int figure(int figureN){
        int innerMax = 0;
        int endC = c , endR = r;
        if(figureN == 0){ // 작대기
            int tempMax = map[y][x];
            if(x + 1 != endC){if(x + 2 != endC){if(x + 3 != endC){tempMax += map[y][x + 1] + map[y][x + 2] + map[y][x + 3];
            if(innerMax < tempMax) innerMax = tempMax;}}}
            tempMax = map[y][x];
            if(y + 1 != endR){if(y + 2 != endR){if(y + 3 != endR){tempMax += map[y + 1][x] + map[y + 2][x] + map[y + 3][x];
            if(innerMax < tempMax) innerMax = tempMax;}}}
        }
        else if(figureN == 1){//1 네모
            int tempMax = map[y][x];
            if(x + 1 != endC){if (y + 1 != endR){if (y + 1 != endR && x + 1 != endC){tempMax += map[y+1][x + 1] + map[y + 1][x] + map[y][x + 1];
            if(innerMax < tempMax) {innerMax = tempMax;}}}}
        }
        else if(figureN == 2){
            int tempMax = map[y][x];
            if(y + 1 != endR){if(y + 2 != endR){if(y + 2 != endR && x + 1 != endC){tempMax += map[y + 1][x] + map[y + 2][x] + map[y + 2][x + 1]; //1
            if(innerMax < tempMax){innerMax = tempMax;}}}}
            tempMax = map[y][x];
            if(y + 1 != endR){if(y + 1 != endR && x + 1 != endC){if(y + 1 != endR && x + 2 != endC){tempMax += map[y + 1][x] + map[y + 1][x + 1] + map[y + 1][x + 2]; //2
            if(innerMax < tempMax){innerMax = tempMax;}}}}
            tempMax = map[y][x];
            if(x + 1 != endC){if(y + 1 != endR && x + 1 != endC){if(y + 2 != endR && x + 1 != endC){tempMax += map[y][x + 1] + map[y + 1][x + 1] + map[y + 2][x + 1]; //3
            if(innerMax < tempMax){innerMax = tempMax;}}}}
            tempMax = map[y][x];
            if(x + 1 != endC){if(x + 2 != endC){if(x + 2 != endC && y + 1 != endR){tempMax += map[y][x + 1] + map[y][x + 2] + map[y + 1][x + 2]; //4
            if(innerMax < tempMax){innerMax = tempMax;}}}}
            tempMax = map[y][x];
            endR = -1;
            if(x + 1 != endC){if(x + 1 != endC && y - 1 != endR){if(y - 2 != endR && x + 1 != endC){tempMax += map[y][x + 1] + map[y - 1][x + 1] + map[y - 2][x + 1]; //5
            if(innerMax < tempMax){innerMax = tempMax;}}}}
            tempMax = map[y][x];
            if(y - 1 != endR){if(y - 1 != endR && x + 1 != endC){if(y - 1 != endR && x + 2 != endC){tempMax += map[y - 1][x] + map[y - 1][x + 1] + map[y - 1][x + 2]; //6
            if(innerMax < tempMax){innerMax = tempMax;}}}}
            tempMax = map[y][x];
            if(y - 1 != endR){if(y - 2 != endR){if(y - 2 != endR && x + 1 != endC){tempMax += map[y - 1][x] + map[y - 2][x] + map[y - 2][x + 1]; //7
            if(innerMax < tempMax){innerMax = tempMax;}}}}
            tempMax = map[y][x];
            if(x + 1 != endC){if(x + 2 != endC){if(x + 2 != endC && y - 1 != endR){tempMax += map[y][x + 1] + map[y][x + 2] + map[y - 1][x + 2]; //8
            if(innerMax < tempMax){innerMax = tempMax;}}}}
        }//2 개 같은거 8개 경우 있는 것
        else if(figureN == 3){
            int tempMax = map[y][x];
            if(x + 1 != endC){if(x + 1 != endC && y + 1 != endR){if(x + 2 != endC && y + 1 != endR){tempMax += map[y][x + 1] + map[y + 1][x + 1] + map[y + 1][x + 2];
            if(innerMax < tempMax){innerMax = tempMax;}}}}
            tempMax = map[y][x];
            if(y + 1 != endR){if(x + 1 != endC && y + 1 != endR){if(x + 1 != endC && y + 2 != endR){tempMax += map[y + 1][x] + map[y + 1][x + 1] + map[y + 2][x + 1];
            if(innerMax < tempMax){innerMax = tempMax;}}}}
            tempMax = map[y][x];
            endR = -1;
            if(x + 1 != endC){if(x + 1 != endC && y - 1 != endR){if(x + 2 != endC && y - 1 != endR){tempMax += map[y][x + 1] + map[y - 1][x + 1] + map[y - 1][x + 2];
            if(innerMax < tempMax){innerMax = tempMax;}}}}
            tempMax = map[y][x];
            if(y -  1 != endR){if(x + 1 != endC && y - 1 != endR){if(x + 1 != endC && y - 2 != endR){tempMax += map[y - 1][x] + map[y - 1][x + 1] + map[y - 2][x + 1];
            if(innerMax < tempMax){innerMax = tempMax;}}}}
        }//3 4개 있는 거
        else{ //4 뻐큐
            int tempMax = map[y][x];
            if(x + 1 != endC){if(x+ 2 != endC){if(x + 1 != endC && y + 1 != endR){tempMax += map[y][x + 1] +map[y + 1][x + 1] + map[y][x + 2];
            if(innerMax < tempMax){innerMax = tempMax;}}}}
            tempMax = map[y][x];
            endR = -1;
            if(x + 1 != endC){if(x+ 2 != endC){if(x + 1 != endC && y - 1 != endR){tempMax += map[y][x + 1] +map[y - 1][x + 1] + map[y][x + 2];
            if(innerMax < tempMax){innerMax = tempMax;}}}}
            tempMax = map[y][x];
            endC = -1; endR = -1;
            if(y - 1 != endR){if(y - 2 != endR){if(x - 1 != endC && y - 1 != endR){tempMax += map[y - 1][x] +map[y - 1][x - 1] + map[y - 2][x];
            if(innerMax < tempMax){innerMax = tempMax;}}}}
            tempMax = map[y][x];
            endC = c;
            if(y - 1 != endR){if(y - 2 != endR){if(x + 1 != endC && y - 1 != endR){tempMax += map[y - 1][x] +map[y - 2][x] + map[y - 1][x + 1];
            if(innerMax < tempMax){innerMax = tempMax;}}}}
        }
        return innerMax;
    }
}
