import java.util.*;
import java.io.*;

// 10217 : KCM Travel
/*
--전제 조건
찬민이는 M원의 비용만을 여행비로써 비행기를 타야하고 , 그래서 LA까지 M원 이하로 사용하면서 , 가장 빨리 도착하는 길을 차선책으로
선택해야한다 , 그래서 테스트 케이스가 주어지면 , 목적지까지 가장 빠른 지점을 구하면 된다.
인천은 언제나 1번 도시이고 , LA는 언제나 N번 도시이다.

--틀 설계
결국 해답을 봤다 , 여기서 말하는 해답은 이것이다.
일단 어떠한 지점에 도착하였을 때 , dp[v][cost] = time 으로 기록해놓는다.
그러면 여기서 이런 이론이 발생하게 된다 , 해당 정점까지 cost의 비용으로 time을 기록했다면,
지금 현재 time 보다 더 높은 time 과 더 높은 cost로 방문하게 되면 , 이것은 말이 안되는 것이다.
그러니까 , 이렇게 오면 이미 비효율적이라는 것을 인증하게 되는 것이다.

그래서 dist[v][cost] 로 오게 되면 cost 부터 다음 것 까지 현재의 비용으로 더 높은 dist[v][cost + 1 ...... MaxCost] 값들을 현재 본인의 time 으로 초기화하는 것이다.
어떠한 경우에 ? 위에 cost들을 조사하였을 때 본인의 time 보다 높을 때 , 이것은 이미 얘보다 느린 것이기 때문에

이렇게 dynamic 하게 하면서 , 다익스트라를 이어나가면 , 답을 구할 수 있다.

-- 결론
항상 생각하지만 , 생각을 조금 더 해봐야 할 것 같다. 기본적인 다익스트라 알고리즘으로 그냥 접근한 것도 괜찮았지만
다이나믹 프로그래밍을 생각하는 것도 중요할 것 같다.

그래서 한마디로 이 문제는 이전에 내가 해당 정점을 해당 cost로 왔었던 것을 기억하고 ,
해당 정점까지 비용이 더 많이 들면서 , 시간이 더 드는 것은 필요가 없다라는 가장 기본적인 접근으로 푸는 문제였다.
 */
public class Main2 {
    public static class Edge{
        int idx;
        int cost;
        int time;
        public Edge(int idx , int cost , int time){
            this.idx = idx;
            this.cost = cost;
            this.time = time;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int T = Integer.parseInt(input.readLine());

        for(int i = 0; i < T; i++){
            st = new StringTokenizer(input.readLine());

            int V = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            int[][] dp = new int[V + 1][M + 1];
            PriorityQueue<Edge> queue = new PriorityQueue<>((o1 , o2) -> Integer.compare(o1.cost , o2.cost));
            List<ArrayList<Edge>> graph = new ArrayList<>();

            for(int j = 0; j <= V; j++){
                Arrays.fill(dp[j] , Integer.MAX_VALUE); // time 값 전부다 , Integer.MAX_VALUE 로 초기화 왜냐하면 , 처음 방문하는 time 값은 dist[v][cost] 에 대해서 넣어야 하기 때문이다.
                // 이게 또 dist[v][cost] < edge.time 이면 진행할 필요가 없음 , 같아도 진행할 필요 x
                graph.add(new ArrayList<>());
            }

            for(int j = 0; j < K; j++){
                st = new StringTokenizer(input.readLine());

                int sta = Integer.parseInt(st.nextToken());
                int des = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());
                int time = Integer.parseInt(st.nextToken());

                graph.get(sta).add(new Edge(des , cost , time));
            }

            queue.add(new Edge(1 , 0 , 0));

            while(!queue.isEmpty()){
                Edge edge = queue.poll();

                if(dp[edge.idx][edge.cost] < edge.time) continue;

                for(int j = edge.cost; j <= M; j++){ // 현재 cost 부터 더 높은 비용들의 시간들을 보면서 본인보다 높으면 바꿈
                    if(dp[edge.idx][j] > edge.time) dp[edge.idx][j] = edge.time;
                }

                for(Edge innerEdge : graph.get(edge.idx)){ // 이제 방문하는 곳에 현재의 cost + edge.cost 를 해서 방문해서 해당 시간보다 , 낮으면 넣는다 , 같아도 안 넣어도 됨
                    int sum = innerEdge.cost + edge.cost;
                    if(sum > M) continue;
                    if(dp[innerEdge.idx][sum] > innerEdge.time + edge.time){
                        dp[innerEdge.idx][sum] = innerEdge.time + edge.time;
                        queue.add(new Edge(innerEdge.idx , sum , innerEdge.time + edge.time));
                    }
                }
            }
            int min = Integer.MAX_VALUE;
            for(int j = 1; j <= M; j++){
                if(min > dp[V][j]) min = dp[V][j];
            }

            if(min == Integer.MAX_VALUE) output.write("Poor KCM" + "\n");
            else output.write(min + " \n");
        }
        output.flush();
        output.close();
    }
}
