import java.util.*;
import java.io.*;

// 2096 : 내려가기
/*
--전제 조건
돌 다리 건너기랑 비슷한 문제인 것 같다.
일단 3가지의 경우가 존재한다.
해당 위치에 이동하게 되면, 그 인접한 칸으로만 다음에 이동할 수 있다
이 경우에 , 최대 점수와 최소 점수를 띄어서 출력하라
--틀 설계
무조건 1 , 2 , 3 번 째 col 을 선택해야한다.
그리고 row는 1부터 시작한다.
col == 0 일때에는 0 , 1 를 보고
col == 1 일 때에는 0 , 1 , 2 을 보고
col == 2 일 때에는 1 , 2 을 보고 큰 것을 뽑아서 현재의 수와 더하면 된다.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(input.readLine());

        int[][] maxDp = new int[n][3];
        int[][] minDp = new int[n][3];

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < 3; j++){
                maxDp[i][j] = Integer.parseInt(st.nextToken());
                minDp[i][j] = maxDp[i][j];
            }
        }

        for(int i = 1; i < n; i++){
            for(int j = 0; j < 3; j++){
                if(j == 0){ //0 , 1 중에서 큰 것을 더함
                    maxDp[i][j] = maxDp[i][j] + Math.max(maxDp[i - 1][0] , maxDp[i - 1][1]);
                    minDp[i][j] = minDp[i][j] + Math.min(minDp[i - 1][0] , minDp[i - 1][1]);
                }
                if(j == 1){ // 0 , 1 , 2 중에서 큰 것을 더함
                    int max = 0;
                    int min = Integer.MAX_VALUE;
                    for(int c = 0; c < 3; c++){
                        if(max < maxDp[i - 1][c]) max = maxDp[i - 1][c];
                        if(min > minDp[i - 1][c]) min = minDp[i - 1][c];
                    }
                    maxDp[i][j] = maxDp[i][j] + max;
                    minDp[i][j] = minDp[i][j] + min;
                }
                if(j == 2){ // 1 , 2 중에서 큰 것을 더함
                    maxDp[i][j] = maxDp[i][j] + Math.max(maxDp[i - 1][1] , maxDp[i - 1][2]);
                    minDp[i][j] = minDp[i][j] + Math.min(minDp[i - 1][1] , minDp[i - 1][2]);
                }
            }
        }

        int min = Integer.MAX_VALUE;
        int max = 0;
        for(int i = 0; i < 3; i++){
            if(max < maxDp[n - 1][i]) max = maxDp[n - 1][i];
            if(min > minDp[n - 1][i]) min = minDp[n - 1][i];
        }

        System.out.println(max + " " + min);
    }
}

