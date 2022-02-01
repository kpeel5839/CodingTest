import java.util.*;
import java.io.*;

// 9370 : 미확인 도착지
/*
-- 전제조건
서커스 요원들을 B100요원이 발견하였다
예술가 한 쌍의 목적지 후보를 알고 있고 , 그들의 목적지 후보들을 알고 있다.
그리고 그들은 시간이 없어서 무조건 최단거리로 이동할 것이라는 것도 알고 있다.
그리고 g , h 사이의 교차로에서 그들이 움직였다는 신호를 체크하였다.
그들의 목적지가 될 수 있는 리스트들을 출력하자.
-- 틀 설계
그냥 최단거리로 목적지 후보들을 다 구한다음
parent 배열로 경로를 추적한다.
그 다음에 목적지 후보들을 순서대로 parent 타고서 재귀적으로 경로를 역추적하면서
g , h 를 지난적이 있는지 확인한다.
만약 없다면 그것은 목적지가 될 수 없다 , 왜냐하면 최단경로로 가기 위해서 g , h 사이의 도로를 지나지 않았을 것이기 때문이다.

일단 테스트 케이스 개수 입력을 받는다.
첫 줄은 n , m , t로 입력을 받는다 , n 개 만큼 선언하고 , dist[n + 1] , parent[n + 1]로 선언을 한다.
그리고 두 번째 줄을 받으면서 start , g , h를 받는다.
그 다음에 정상적으로 다익스트라 알고리즘을 실행한다음에
find함수를 이용해서 재귀적으로 g , h를 지나는지 확인한다.

그럼 이렇게 풀어보는 거 어떨까.
일단은 먼저 해당 목적지까지 최소경로로 이동한다.
그 다음에 g를 시작으로 하는 최소경로와 , h를 시작으로 하는 모든 노드들의 최소 경로비용도 구한다.
그 다음에 비교를 한다.
dist[g][목적지 후보들] + dist[s][g] == dist[s][목적지 후보] 이거랑
dist[h][목적지 후보들] + dist[s][h] == dist[s][목적지 후보] 이러면 요거랑 같은 목적지 후보들은 갈 수가 있는 것이다.
일단 가장 유력하니 한번해보자.
 */
public class Main {
    public static int[][] dist;
    public static int n , m ,t , g , h , s;
//    public static boolean findG , findH;
    public static List<ArrayList<Edge>> graph;
    public static class Edge{
        int idx;
        int cost;
        public Edge(int idx , int cost){
            this.idx = idx;
            this.cost = cost;
        }
    }
//    public static void find(int vertex){
//        /*
//        main 함수에서 목적지 정점을 주면 find에서 지나가면서 g , h를 찾으면 findG, findH를 true로 만들어준다.
//        그것만 하면된다.
//         */
//        if(vertex == g) findG = true;
//        if(vertex == h) findH = true;
//        if(parent[vertex] == vertex){
//            return;
//        }else{
//            find(parent[vertex]);
//        }
//    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int T = Integer.parseInt(input.readLine());

        for(int i = 0; i < T; i++){
            st = new StringTokenizer(input.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            t = Integer.parseInt(st.nextToken());
            dist = new int[n + 1][n + 1];
            graph = new ArrayList<>();

            st = new StringTokenizer(input.readLine());
            s = Integer.parseInt(st.nextToken());
            g = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());

            for(int j = 0; j <= n; j++){
                graph.add(new ArrayList<>());
            }

            for(int j = 0; j < m; j++){
                st = new StringTokenizer(input.readLine());
                int vertex1 = Integer.parseInt(st.nextToken());
                int vertex2 = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());

                graph.get(vertex1).add(new Edge(vertex2 , cost));
                graph.get(vertex2).add(new Edge(vertex1 , cost));
            }

            for(int j = 1; j <= n; j++) Arrays.fill(dist[j] , Integer.MAX_VALUE);

            for(int j = 0; j < 3; j++) {
                PriorityQueue<Edge> queue = new PriorityQueue<>((o1 , o2) -> Integer.compare(o1.cost , o2.cost));
                int start = 0;
                if(j == 0) start = s;
                else if(j == 1) start = g;
                else start = h;
                queue.add(new Edge(start, 0));
                dist[start][start] = 0;
                while (!queue.isEmpty()) {
                    Edge edge = queue.poll();
                    if (edge.cost > dist[start][edge.idx]) continue;
                    for (int c = 0; c < graph.get(edge.idx).size(); c++) {
                        Edge innerEdge = graph.get(edge.idx).get(c);
                        if (dist[start][innerEdge.idx] > edge.cost + innerEdge.cost) {
                            dist[start][innerEdge.idx] = edge.cost + innerEdge.cost;
                            queue.add(new Edge(innerEdge.idx, dist[start][innerEdge.idx]));
                        }
                    }
                }
            }

            int[] destination = new int[t];
            for(int j = 0; j < t; j++){
                destination[j] = Integer.parseInt(input.readLine());
            }

//            for(int j = 1; j <= n; j++){
//                output.write(Arrays.toString(dist[j]) + "\n");
//            }
            /*
            출력할 정답 배열을 만들고
            그 다음에 목적지가 가능한 것들은 1로 채우는 가정으로 간다.
            여기서 이제 dist[s][g] + dist[g][목적지 후보들] == dist[s][목적지 후보들] 인지 확인하면서 맞으면 리스트에다가 넣는다.
            dist[s][h] + dist[h][목적지 후보들] == dist[s][목적지 후보들] 이것도 같다.
             */

            int[] result = new int[n + 1];

            for(int j = 0; j < destination.length; j++){
                if(dist[s][g] + dist[g][destination[j]] == dist[s][destination[j]]) result[destination[j]] = 1;
                if(dist[s][h] + dist[h][destination[j]] == dist[s][destination[j]]) result[destination[j]] = 1;
            }

            for(int j = 1; j < result.length; j++){
                if(result[j] == 1) output.write(j + " ");
            }
            /*
            이 방법대로 풀면
            1
            4 4 2
            1 1 3
            1 2 1
            2 4 1
            1 3 1
            3 4 1
            3
            4
            이 경우에 대해서 반례가 존재함
            4도 최소 경로로 1 -> 3 -> 4 로 갈 수 있지만
            다익스트라 알고리즘에서 1 -> 2 -> 4 로 가는 방향을 선택해서 안됨
            그렇기 때문에 이 경우를 해결할 수 있는 만일 가중치가 같은 두 경로가 존재할 때
            두 경로를 다 고려할 수 있어야함

            애초에 그래서 그냥 작거나 같은 경우에 들어오는 경우에서 edge.idx == g , h 고
            innerEdge.idx == g , h이면? 근데 이것도 아닌 것 같다..
             */
//            for(int j = 0; j < t; j++){
//                findG = false;
//                findH = false;
//                find(destination[j]);
//                if(findG && findH){
//                    output.write(destination[j] + " ");
//                }
//            }
//            output.write(Arrays.toString(dist) + "\n");
//            output.write(Arrays.toString(parent) + "\n");
            output.write("\n");
        }

        output.flush();
        output.close();
    }
}
