import java.util.*;
import java.io.*;

public class Main{
    public static int r, c;
    public static int[][] originMap;
    public static int[][] useMap;
    public static Zero[] zeros;
    public static int max;

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        int zeroCount = 0;
        originMap = new int[r][c];
        useMap = new int[r][c];

        for(int i = 0; i < r; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < c; j++){
                originMap[i][j] = Integer.parseInt(st.nextToken());
                useMap[i][j] = originMap[i][j];
                if(originMap[i][j] == 0){
                    zeroCount += 1;
                }
            }
        }
        int zeroIndex = 0;
        zeros = new Zero[zeroCount];
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                if(originMap[i][j] == 0){
                    zeros[zeroIndex] = new Zero(i, j);
                    zeroIndex++;
                }
            }
        }
        for(int i = 0; i < zeroCount - 2; i++){
            for(int j = i+1; j < zeroCount - 1; j++){
                for(int e = j + 1; e < zeroCount; e++){
                    int count = 0;
                    useMap[zeros[i].y][zeros[i].x] = 1;
                    useMap[zeros[j].y][zeros[j].x] = 1;
                    useMap[zeros[e].y][zeros[e].x] = 1;
                    for(int q = 0; q < r; q++){
                        for(int w = 0; w < c; w++){
                            if(useMap[q][w] == 2){
                                bfs(q , w);
                            }
                        }
                    }
                    for(int q = 0; q < r; q++){
                        for(int w = 0; w < c; w++){
                            if(useMap[q][w] == 0){
                                count++;
                            }
                        }
                    }
                    if(max < count){
                        max = count;
                    }
                    for(int q = 0; q < r; q++){
                        for(int w = 0; w < c; w++){
                            useMap[q][w] = originMap[q][w];
                        }
                    }
                }
            }
        }
        System.out.println(max);
    }
    public static class Zero{
        int y;
        int x;

        public Zero(int y , int x){
            this.y = y;
            this.x = x;
        }
    }
    public static void bfs(int y , int x){
        useMap[y][x] = 2;
        if(y - 1 != -1 && useMap[y - 1][x] == 0){bfs(y - 1, x);} //위
        if(x + 1 != c && useMap[y][x + 1] == 0){bfs(y , x + 1);} //오른쪽
        if(y + 1 != r && useMap[y + 1][x] == 0){bfs(y + 1, x);} //아래
        if(x - 1 != -1 && useMap[y][x - 1] == 0){bfs(y , x - 1);} //왼쪽
    }
}
