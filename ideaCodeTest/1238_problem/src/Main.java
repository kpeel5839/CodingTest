import java.util.*;
import java.io.*;

// 1238 : 파티
/*
--전제조건
N개의 숫자로 구분된 각각의 마을에 한 명의 학생이 살고 있다. (각각의 마을에 한명씩 각각 살고 있는 것)
어느 날 이 N명의 학생이 x 번 마을에 모여서 파티를 벌이기로 하였다.
이 마을 사이에는 총 M개의 단 방향 도로들이 있고(단 방향 가중치) i 번째 길을 지나는데 Ti 시간을 소비한다(가중치 값)
각각의 학생들은 파티에 참석하기 위해 걸어가서 다시 그들의 마을로 돌아와야 한다.
하지만 이 학생들은 게을러서 최단 시간에 오고 가기를 원함
이 도로들은 단방향이기에 오고 가는 길이 다를지도 모른다(최단거리만 선택하니까)
N명의 학생들 중 오고 가는데 가장 많은 시간을 소비하는 학생은 누구일지 구하여라

첫 번째 줄에 N , M , X 가 주어진다. 두 번째 줄부터 각각의 간선들이 주어진다.
모든 학생들은 집에서 X까지 갈 수 있고 , X에서 집으로 돌아올 수 있는 데이터만 입력으로 주어진다.
--틀설계
일단 먼저 vertex의 정보와 edge의 정보도 받는다.
edge class 를 선언한다
dist[vertex + 1][vertex + 1] 을 구한다
그런 다음에 for(int i = 1; i <= vertex; i++) 까지 distance를 구한다.
그 다음에 구한 distance를 가지고 max 를 고를 건데
이 과정에서는 vertex 개수 만큼 돌면서 [i][x] [x][i] 를 구한다
여기서는 i == x에서는 건너 뛴다.
 */
public class Main {
    public static class Edge{
        int idx;
        int cost;
        public Edge(int idx , int cost){
            this.idx = idx;
            this.cost = cost;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        int start = Integer.parseInt(st.nextToken());

        List<ArrayList<Edge>> graph = new ArrayList<>();
        int[][] dist = new int[v + 1][v + 1];

        for(int i = 0; i <= v; i++) {
            graph.add(new ArrayList<>());
            Arrays.fill(dist[i] , Integer.MAX_VALUE);
        }

        for(int i = 0; i < e; i++){
            st = new StringTokenizer(input.readLine());
            graph.get(Integer.parseInt(st.nextToken())).add(new Edge(Integer.parseInt(st.nextToken()) , Integer.parseInt(st.nextToken())));
        }

        PriorityQueue<Edge> queue = new PriorityQueue<>((o1 , o2) -> Integer.compare(o1.cost , o2.cost));

        for(int i = 1; i <= v; i++){
            dist[i][i] = 0;
            queue.add(new Edge(i , 0));
            while(!queue.isEmpty()){
                Edge edge = queue.poll();
                if(dist[i][edge.idx] < edge.cost) continue;

                for(int j = 0; j < graph.get(edge.idx).size(); j++){
                    Edge innerEdge = graph.get(edge.idx).get(j);
                    if(dist[i][innerEdge.idx] > innerEdge.cost + edge.cost){
                        dist[i][innerEdge.idx] = innerEdge.cost + edge.cost;
                        queue.add(new Edge(innerEdge.idx , dist[i][innerEdge.idx]));
                    }
                }
            }
        }
        int max = Integer.MIN_VALUE;
        for(int i = 1; i <= v; i++){
            if(i != start){
                max = Math.max(max , dist[i][start] + dist[start][i]);
            }
        }

        System.out.println(max);
    }
}