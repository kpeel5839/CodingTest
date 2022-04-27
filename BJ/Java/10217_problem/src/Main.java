import java.util.*;
import java.io.*;

// 10217 : KCM Travel
/*
--전제 조건
찬민이는 M원의 비용만을 여행비로써 비행기를 타야하고 , 그래서 LA까지 M원 이하로 사용하면서 , 가장 빨리 도착하는 길을 차선책으로
선택해야한다 , 그래서 테스트 케이스가 주어지면 , 목적지까지 가장 빠른 지점을 구하면 된다.
인천은 언제나 1번 도시이고 , LA는 언제나 N번 도시이다.
--틀 설계
PriorityQueue로 구성을 하는데 , 여기서 Math.min 으로 dist에 cost를 집어넣는다.
그러니까 두 가지의 조건을 넣는 것이다 , 하나는 time이 M을 넘지 않았는지 , 넘었다면 바로 continue;
그리고 현재 time 이 m을 넘지 않았다면 무조건 계속 가야할 것 같다..

그러면 priorityQueue 의 sort 기준을 time 으로 잡고 time 이 작은 순서대로 먼저 실행하면서
cost 가 더 작을 때로 진행하면 될 것 같다. 왜냐하면 내가 지금 time 도 큰 데 이전에 들어온 것보다
비용도 커? 그러면 이건 무조건 안될 확률이 높다.

그래서 , 약간 time 을 기준으로 sort 하면서 , cost를 기준으로 걸러내면 되지 않을까 싶다.
 */
public class Main {
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

        for(int i = 0; i < T; i++){ // test case 만큼 반복
            st = new StringTokenizer(input.readLine());

            int V = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            int[] dist = new int[V + 1];
            List<ArrayList<Edge>> graph = new ArrayList<>();

            for(int j = 0; j <= V; j++){
                graph.add(new ArrayList<>());
                dist[j] = Integer.MAX_VALUE;
            }

            dist[1] = 0;

            for(int j = 0; j < K; j++){
                st = new StringTokenizer(input.readLine());
                int sta = Integer.parseInt(st.nextToken());
                int des = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());
                int time = Integer.parseInt(st.nextToken());
                graph.get(sta).add(new Edge(des , cost , time));
            } // 간선 추가

            PriorityQueue<Edge> queue = new PriorityQueue<>((o1 , o2) -> Integer.compare(o1.cost , o2.cost));
            queue.add(new Edge(1 , 0 , 0));
            int[][] visited = new int[101][100000];

            while(!queue.isEmpty()){
                Edge edge = queue.poll();

                if(edge.cost > M) continue;

                if(visited[edge.idx][edge.time] == 1) continue;

                if(dist[edge.idx] < edge.time) continue;

                visited[edge.idx][edge.time] = 1;
                dist[edge.idx] = edge.time;  // 일부로 time이 낮은 순으로 정렬해서 뽑아내고 있으니까 , time이 낮은애들만 지금 현재 비용을 등록해놨어야함
                // 그렇지 않으면 , time이 더 높은 애가 등록한 비용에 짤릴 수도 있음
                // 내가 지금 의도한 time이 높은데 한 지점에 도착했을 때 , 비용까지 높아? 그러면 희망이 없다라는 가정하에 접근한 것이다.

                for(Edge innerEdge : graph.get(edge.idx)){ // idx 가 가지고 있는 , 즉 현재 노드의 간선들 뽑아내기
                    if(dist[innerEdge.idx] > innerEdge.time + edge.time){
                        queue.add(new Edge(innerEdge.idx , innerEdge.cost + edge.cost , innerEdge.time + edge.time));
                    }
                }
            }

//            System.out.println(Arrays.toString(dist));
            if(dist[V] == Integer.MAX_VALUE) output.write("Poor KCM" + "\n");
            else output.write(dist[V] + "\n");
        }
        output.flush();
        output.close();
    }
}
