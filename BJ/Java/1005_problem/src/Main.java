import java.util.*;
import java.io.*;

// 1005 : ACM Craft
/*
-- 전체 설계
모든 건물이 건설 가능하도록 진행이 되고 , (위상정렬)
어떠한 건물을 짓는 데 , 최소 비용 , 해당 건물까지의 건물을 짓는데의 최소비용을 출력하라.
-- 틀 설계
만일 최대로 모든 건물을 다 지어야지만 , 가능한 최대 값이 , 1000 * 100,000 이다.
그러면 int 범위를 벗어나지 않기에 , 그냥 int로 선언하면 된다.

위상정렬은 그냥 진입차선이 0인 정점을 찾아서 , 시작을 하고,
0인 정점들을 큐에다가 집어넣으면서 , 큐에서 뺄 때 , 해당 정점이 가진 간선을 제거해주면,
진입 차선이 0인 애들이 생겨나게 될 것이다. 그렇다는 것은 해당 정점은 시작하기 위한 조건을 만족한 것이기 때문이니 ,
시작이 가능한 것 이 문제에 대입해보면 , 건물을 지을 수 있는 조건을 만족한 것이다.

이 점을 만족하면 , 순서를 지키는 , 즉 위상정렬은 만족할 수 있다.

이제 문제를 풀기위한 관건은 해당 건물을 짓기 위한 , 행위를 다 만족하였을 때,
즉 해당 타겟 건물을 짓는 조건을 모두 만족하였을 때의 최소비용을 구하는 것이다.

해당 지점의 최소비용을 구하려면 , 다이나믹 프로그래밍을 사용하여야 한다.

이렇게 하면 가능하지 않을까?

dp 배열을 만들어서 , 정점 개수만큼 관리한다.
위상 정렬을 진행할 때 , 해당 간선에 진입차선을 제거하는데,
진입차선이 0인 것에다가는 dp[정점] = 본인 건설시간
넣어놓고 , 게속 그런식으로 관리를 해놓으면 , 진입차선을 제거할 때 , dp[진입차선을 감소시키는 정점] += dp[본인 정점]

이렇게 큐에다가 관리를 해놓으면서 , 진행하면 될 것 같다.

그래서 필요한 것은 queue , dp , vertexValue , 진입 차선의 개수를 저장하는 entry를 선언해놓고 , 관리하면 된다.

맞았다 .. 위상정렬 유튜브 영상 하나 보고 맞추니까 너무 기모찌하다..
 */
public class Main {
    public static int T , V , E , W;
//    public static final int INF = 100000000;
    public static int[] vertexValue , dp , entry;
    public static Queue<Integer> queue;
    public static List<ArrayList<Edge>> graph;
    public static class Edge{
        int idx;
        public Edge(int idx){
            this.idx = idx;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        T = Integer.parseInt(input.readLine());

        for(int i = 0; i < T; i++){
            st = new StringTokenizer(input.readLine());

            V = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());

            dp = new int[V + 1];
            entry = new int[V + 1];
            vertexValue = new int[V + 1];
            queue = new LinkedList<>();
            graph = new ArrayList<>();

//            Arrays.fill(dp , INF);

            st = new StringTokenizer(input.readLine());
            graph.add(new ArrayList<>());

            for(int j = 1; j <= V; j++){
                vertexValue[j] = Integer.parseInt(st.nextToken());
                graph.add(new ArrayList<>());
            }

            for(int j = 0; j < E; j++){
                st = new StringTokenizer(input.readLine());

                int sta = Integer.parseInt(st.nextToken());
                int des = Integer.parseInt(st.nextToken());

                graph.get(sta).add(new Edge(des)); // 간선 추가해주고
                entry[des]++; // des 에 진입차선을 추가해준다.
            }

            for(int j = 1; j <= V; j++){ // 탐색해주면서 , 어떠한 정점이 진입차선이 0인지를 구한다.
                if(entry[j] == 0){
                    queue.add(j);
                    dp[j] = vertexValue[j]; // 0인 애들은 본인 값으로
                }
            }

            /*
            그리고 잘못 본게 있었다.
            진입 차선이 여러개인 애들은 , 얘내들이 동시에 진행할 수 있냐를 보고,
            동시에 진행할 수 있으면 , 더 큰 수를 선택해야 한다.
            그럼 여기서 조금만 더 조건을 추가하면 될 듯하다.
            해당 시간은 본인이 완성되기 까지의 시간이다
            결국 , 해당 시간 중 , 가장 큰 시간이 , 즉 , 진입차선들의 모음 중
            가장 큰 시간 , 즉 그 시간이 본인의 시간이다.

            그래서 , dp[진입차선 제거하는 정점] += dp[본인 정점]
            이 아니라 , dp[진입차선 제거하는 정점] = Math.max(dp[진입차선 제거하는 정점] , dp[본인 정점] + vertexValue[진입차선 제거하는 정점])
            이런식의 연산을 진행한다면 ? 구할 수 있을 것 같다.
             */

            W = Integer.parseInt(input.readLine());

            while(!queue.isEmpty()){ // 따로 사이클이 있는 경우는 체크 x , 모든 건물을 다 지을 수 있는 경우만 주어지니까
                Integer vertexNumber = queue.poll();

                if(W == vertexNumber){
                    output.write(dp[vertexNumber] + "\n");
                    break;
                }

                for(Edge edge : graph.get(vertexNumber)){
                    // 본인과 연결된 간선들 진입차선 제거해주면서 , 본인과 목적지 정점 중 더 큰 것을 골라야함
                    // 더 큰 것을 골라야지 , 해당 정점을 모두 지었을 때 정확한 건설 시간을 알 수 있음

                    if(--entry[edge.idx] == 0){ // 0이 된 것이면 큐에다가 집어넣는다.
                        queue.add(edge.idx);
                    }

                    // 그리고 무조건 dp 연산을 실행한다.
                    dp[edge.idx] = Math.max(dp[edge.idx] , dp[vertexNumber] + vertexValue[edge.idx]);
                }
            }
        }

        output.flush();
        output.close();
    }
}