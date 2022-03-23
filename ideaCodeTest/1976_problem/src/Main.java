import java.util.*;
import java.io.*;

// 1976 : 여행가자

/*
-- 전제조건
N개의 도시가 있고
M개의 여행계획이 주어진다.

N개의 도시와 N개의 도시에 연결되어있는 도시들의 정보가 주어질때
M개의 여행계획이 가능한지 구하시오.
-- 틀 설계
그냥 N개의 도시들의 연결정보가 주어질 때,
그리고 M개의 여행계획들이 주어지면
1 2 3 이라고 가정했을 떄 ,
일단 1 , 2 가 가능한지 여부를 살피면 된다.

그러면 일단 주어진 map 들을 다시 정돈할 필요가 있다.
그래서 map을 일단 재귀적으로 해결하고
모든 정점들을 돌면서 방문처리를 해주고

그러면서 map을 정리를 해준다.
그 다음에 map을 정리를 해준 다음 , 다음 정점으로 넘어가서 또 해준다.
이것을 모든 도시를 반복하면 될 것 같다. (find로 정리 이후에 , map[i][j] == 1 이라면 ? map[j][i] 1 이다. 그렇기 때문에 이 부분도 바꿔준다.)

-- 해맸던 점
계속 생각하고 있었는데,
start , des 가 같은 경우가 주어지면 어떻게 하지?
라는 생각을 했었는데 반영을 안했었다.
근데 , 역시나 틀려서 고쳐봤는데 맞았다.

그래서 해맸던 점은 start , des 가 같은 경우가 주어졌을 때 , 고려하지 않았던 점이고
그리고 time complexity 가 조금 맘에 들지 않는다...
 */
public class Main {
    public static int[][] map;
    public static boolean[] visited;
    public static int N , M;
    public static boolean success = true;

    public static void find(int city , int initCity){
        /*
        그냥 계속 재귀적으로 움직여주면서 해당 정점 방문처리해주고
        방문하는 곳마다 map[initCity][city] 를 넣어주면 됨
         */

        if(visited[city]) return;

        visited[city] = true;

        for(int i = 1; i <= N; i++){ // 본인과 연결된 도시에 다 감
            if(i == city) continue; // 이거 솔직히 안해줘도 되는데 가독성 좋아지게 함
            if(map[city][i] == 1) {
                map[initCity][i] = 1; // map[city][i] 가 연결이 되었다라는 것은 곧 initCity 와 i 가 연결 되었다는 것을 의미
                // city 와 i 도 연결된 거지만 , 이미 입력 되어있음
                find(i , initCity);
            }
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());
        M = Integer.parseInt(input.readLine());

        map = new int[N + 1][N + 1];

        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 1; j <= N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 1; i <= N; i++){
            visited = new boolean[N + 1];
            find(i , i);
            for(int j = 1; j <= N; j++){
                if(map[i][j] == 1) map[j][i] = 1;
            }
        }

//        for(int i = 1; i <= N; i++) System.out.println(Arrays.toString(map[i]));

        st = new StringTokenizer(input.readLine());

        int start = Integer.parseInt(st.nextToken());

        for(int i = 1; i < M; i++){
            int des = Integer.parseInt(st.nextToken());

            if(map[start][des] != 1 && start != des) {
                success = false;
                break;
            }

            start = des; // des -> start 로 하면서 다음 것을 받을 준비를 함
        }

        if(success) System.out.println("YES");
        else System.out.println("NO");
    }
}
