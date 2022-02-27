import java.util.*;
import java.io.*;

// 14938 : 서강그라운드
/*
-- 전체 설계
예은이가 아이템을 어디에서 , 몇 개의 아이템을 얻을 수 있다는 정보는 받게 된다.
그래서 , 거리들과 , 수색범위가 주어졌을 때 , 가장 많이 얻을 수 있는 아이템의 개수를 구하라
그리고 , 수색범위는 1 <= 수색범위 <= 15 이고 , 길들의 길이도 1 <= 길의 길이 <= 15 이다.
-- 틀 설계
다익스트라로 , 갈 수 있는데를 정리하고 (최소거리로) (100개 밖에 안되니까 , 100 , 100 으로 충분할 듯)
그 다음에 배열 순회하면서 , 도달할 수 있는 곳들의 아이템들을 다 더해서
저장해놓으면 , 거기서 가장 큰 것을 출력하면 될 것 같다.

그러니까 , 아이템을 저장해놓는 배열
result 를 저장해놓는 배열
플로이드 워샬로 최소거리를 저장한다.
그 다음에 , 하나하나 배열 돌면서 , result 에다가 저장한다.
아이템의 개수를 먹을 수 있는 , 그러면서 , 거기서 바로 max 를 구한다. (솔직히 result 를 저장해놓는 배열 없어도 될 것 같긴함)
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int v = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        final int INIT = 100;

        int[] item = new int[v + 1];
        int[][] dist = new int[v + 1][v + 1]; // 플로이드 워샬 준비

        st = new StringTokenizer(input.readLine());
        for(int i = 1; i <= v; i++){ // item 저장
            item[i] = Integer.parseInt(st.nextToken());
            Arrays.fill(dist[i] , INIT); // dist 길로 들어올 수 있는 수보다 큰 값으로  초기화
        }

        for(int i = 1; i <= v; i++){
            dist[i][i] = 0; // 본인에서 본인으로 가는 것은 0
        }

        for(int i = 0; i < e; i++){
            st = new StringTokenizer(input.readLine());

            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            dist[v1][v2] = Math.min(dist[v1][v2] , cost);
            dist[v2][v1] = Math.min(dist[v2][v1] , cost);
        } // 원래 가지고 있던 값과 , 비교해서 , 왜냐하면 똑같은 길의 길이가 또 주어질 수도 있으니까

        for(int k = 1; k <= v; k++){
            for(int i = 1; i <= v; i++){
                for(int j = 1; j <= v; j++){
                    // i -> j 를 구하는 것이니까 , 원래의 값이 더크냐 , k를 들렸다가 가는게 더 크냐를 판단.
                    // 길이 없는 경우는 고려하지 않아도 됨 , 그냥 더해져도 짜피 100 이상이니까 , 수색범위 이내에 들어오지 않음 , 그래서 신경 x
                    dist[i][j] = Math.min(dist[i][j] , dist[i][k] + dist[k][j]);
                }
            }
        } // 최소거리로 갱신 완료

        int ans = 0;

        for(int i = 1; i <= v; i++){
            int sum = 0;
            for(int j = 1; j <= v; j++){ // 이제부터 탐색을 하면서 , max 값을 찾을 것임
                if(dist[i][j] <= m) sum += item[j]; // 수색범위 안에 들어오면 item 을 더 해줌
            }
            ans = Math.max(ans , sum);
        }

        System.out.println(ans);
    }
}