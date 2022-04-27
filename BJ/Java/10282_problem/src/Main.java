import java.util.*;
import java.io.*;

// 10282 : 해킹

/*
-- 전제조건
어떠한 컴퓨터의 의존성이 주어지고,
해당 컴퓨터가 해킹 당하면 , 이 의존되어 있는 컴퓨터도 해킹 된다라는 의미이다.
즉 , a , b  ,s 가 주어지는데 b 가 해킹 당하면 a 도 s 초 이후에 해킹 당한다라는 의미이다.
그래서 이제 최종적으로 완전히 컴퓨터가 다 감염될 때까지의 시간을 공백으로 구분지어서 출력한다.
-- 틀 설계
일단 dfs 를 진행하는데 , 일반적인 방법으로 visited 처리를 하는 것이 아니라.
해당 노드에 방문하였을 때 , 지금 현재 가진 값이 dist[노드] 보다 작을 때에만 진행하면 된다.
그 이유는 , 같거나 더 클때에는 짜피 이 지점에서 또 다른 컴퓨터로 분기하기에,
이미 더 적은 시간으로 방문하였다면 더 진행할 이유가 없을 뿐더러 , 그 경로가 이미 진행하거나,
진행해야 할 상황일 것이다.
그렇기 때문에 일단 dfs 를 그런식으로 계속 visited 대신에 비교하면서 진행하는 방식으로 하고
dfs 가 끝나게 되면 getRes method 를 통해 INF 가 아닌 노드의 개수와
INF 가 아닌 애들 중 , 가장 큰 값을 골라 출력하면 될 것 같다.

-- 해맸던 점
dfs 로 처음에는 접근을 했었다.
근데 막상 코드를 짜고 보니까 다익스트라랑 너무 비슷한 방식의 풀이인 것이다.
일단 dfs 로만 했을 때에는 시간초과가 났었다.
하지만 , PriorityQueue 를 이용하여서 , dist[a[0]] < a[1] 을 진행하니까
시간초과가 풀렸다.

아무래도 dfs 로 하게 되면 이미 진행되고 있는 애를 멈출 방법이 없다.
왜냐하면 이미 걔는 진행이 되고 있으니까 , bfs 라면 모를까
예를 들어서 예제와 같이 처음 노드가 해당 지점을 8의 cost로 방문을 하고
다음 노드가 5의 cost로 방문했다고 가정하였을 때 , 5의 cost 가 방문을 하고 나면 이전에 8로 진행하던
애가 계속 판치고 다니는 것을 막아야 하는데 , dfs 로 해결할 방법이 지금의 나로서는 생각이 나지 않는다.

근데 다익스트라 알고리즘에 PrirorityQueue 를 접목한 방법으로 진행하게 되면 cost 들이 오름차순으로
진행이 되기 때문에 , 8로 진행하던 애는 우선순위가 뒤로 밀려나게 되서 , 나중에 진행되게 되고
그렇게 되면 이미 진행하던 5때문에 의미 없는 경로가 되었기 때문에 , continue 된다.

그래서 이 문제는 해당 문제점을 dfs로 막을 수가 없기 떄문에 dijkstra 알고리즘을 사용해야 한다.

-- 해맸던 점
if(dist[a[0]] < a[1]) return 으로 해서 해맸음
continue 인데
 */
public class Main {
    public static int[] dist;
    public static int T , N , D , C , resCount , resTime;
    public static final int INF = 1_000_000_000; // 10 억
    public static List<ArrayList<int[]>> graph;

    public static void dijkstra(int vertex){
        /*
        그냥 해당 방문한 내가 갈 수 있는 곳의 dist 가 내가 지금까지 온 시간 + 지금 그 지점까지 가려는 시간보다.
        작은 경우에만 방문한다 , 크거나 같다라면 방문해도 의미가 없기 때문이다.

        원래 dfs로 했는데 다익스트라로 한번 진행해보자.
         */
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1 , o2) -> o1[1] - o2[1]); // 오름차순
        queue.add(new int[]{vertex , 0});

        while(!queue.isEmpty()) {
            int[] a = queue.poll();

            if (dist[a[0]] < a[1]) continue;

            for (int[] edge : graph.get(a[0])) {
                if (dist[edge[0]] > a[1] + edge[1]) {
                    // 목적지에 지금 현재 cost 를 입력
                    dist[edge[0]] = a[1] + edge[1];
                    // 그리고 dfs 를 재귀적으로 호출하여서 다음 정점으로 이동
                    queue.add(new int[]{edge[0], dist[edge[0]]});
                }
            }
        }
    }

    public static void getRes(){
        /*
        모든 노드를 돌아보면서 INF 가 아닌 것들을 세고 , INF 가 아닌 것들 중 가장
        큰 애를 찾아서 값을 넣으면 된다.
         */
        int count = 0;
        int maxTime = 0;

        for(int i = 1; i <= N; i++){
            if(dist[i] != INF){
                count++;
                maxTime = Math.max(maxTime , dist[i]);
            }
        }

        resCount = count;
        resTime = maxTime;
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        T = Integer.parseInt(input.readLine()); // Test case count
        int loop = 0;
        while(loop++ < T){
            StringTokenizer st = new StringTokenizer(input.readLine());

            N = Integer.parseInt(st.nextToken()); // 컴퓨터 개수
            D = Integer.parseInt(st.nextToken()); // 의존성 개수
            C = Integer.parseInt(st.nextToken()); // 해킹 당한 최초의 컴퓨터

            graph = new ArrayList<>();
            dist = new int[N + 1];

            for(int i = 0; i <= N; i++){
                graph.add(new ArrayList<>()); // graph 초기화
                dist[i] = INF; // INF 값으로 초기화
            }

            for(int i = 0; i < D; i++){
                st = new StringTokenizer(input.readLine());

                // 여기서 b -> a 로 가는 방향으로 진행해야함 , 왜냐하면 b 가 걸려야지만 a 가 걸리는 것이기 때문에

                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int time = Integer.parseInt(st.nextToken());

                graph.get(b).add(new int[]{a , time});
            }

            dist[C] = 0; // C 까지 가는데 걸리는 시간은 무조건 0 , 이미 감염되서
            dijkstra(C);
            getRes();

            output.write(resCount + " " + resTime + "\n");
        }

        output.flush();
        output.close();
    }
}
