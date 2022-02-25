import java.util.*;
import java.io.*;

// 11779 : 최소비용 구하기
/*
-- 전체 설계
출발지점과 도착지점이 주어졌을 때 , 최소비용과 , 그에 맞는 경로를 출력하라.
-- 틀 설계
다익스트라 알고리즘으로 짜는데 ,
PriorityQueue 의 특성을 이용해서 도착지점으로 도착했을 때 break; 하고
path 배열을 이용해서 , 지점을 선택할 때 마다 현재의 지점에서 선택한 지점의 정점 번호를 입력한다.
그 다음에 재귀적으로 시작지점에서 도착지점까지 경로를 추적하면 된다.
-- 해맸던 점
원래는 당연하게 dist[innerEdge.idx] = edge.idx 즉 내가 선택하는 지점에다가 나에서 왔다는 식의 경로작성을 했지만
이번에는 PriorityQueue 의 특성을 이용해서 dist[edge.idx] = innerEdge.idx 로 해봤는데 잘 안됐다.
그래서 , 왜 그럴까 곰곰히 생각해보니까 , 내가 다른 지점으로 가는 방향을 선택할 때는 여러가지이지만, 선택받는 애들은 한 정점에게서만 선택을 받는다,
그래서 내가 1 -> 4 로 정확히 갔다고 하더라도 다른 정점들로 가는 간선이 존재한다면 , 결국 path[1] = 2 로 되어 있을 수도 있는 것이다.
그래서 이러한 점 때문에 , 내가 맞는 경로로 잘 갔다고 하더라도 , 결국 최종적으로 방문하는 최소비용의 정점을 본인의 경로로 집어넣기 때문에 dist[edge.idx] = innerEdge.idx 같은 경우는
그래서 dist[innerEdge.idx] = edge.idx 로 내가 선택한 정점에 본인을 집어넣는 것 , 본인에게서 왔다라는 증표를 남겨놓는 것 이것이 맞는 해결방안이였다.
 */
public class Main {
    public static int v , e , start , end;
    public static int[] path , dist;
    public static PriorityQueue<Edge> queue;
    public static List<ArrayList<Edge>> graph = new ArrayList<>();
    public static List<Integer> resultPath = new ArrayList<>();
    public static void find(int vertex){
        /*
        순차대로 다음 vertex를 찾다가
        end 에서 종료한다.
         */
        if(start == vertex){
            resultPath.add(vertex);
            return;
        }else{
            find(path[vertex]);
            resultPath.add(vertex);
        }
    }
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
        StringTokenizer st;
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));

        v = Integer.parseInt(input.readLine());
        e = Integer.parseInt(input.readLine());

        dist = new int[v + 1];
        path = new int[v + 1];

        for(int i = 0; i <= v; i++){
            graph.add(new ArrayList<>());
            dist[i] = Integer.MAX_VALUE;
        }
        for(int i = 0; i < e; i++){
            st = new StringTokenizer(input.readLine());
            int sta = Integer.parseInt(st.nextToken());
            int des = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            graph.get(sta).add(new Edge(des , cost));
        }

        st = new StringTokenizer(input.readLine());

        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());

//        System.out.println(start + " " + end);

        queue = new PriorityQueue<>((o1 , o2) -> Integer.compare(o1.cost , o2.cost));

        dist[start] = 0;
        queue.add(new Edge(start , 0));

        while(!queue.isEmpty()){
            Edge edge = queue.poll();

            if(dist[edge.idx] < edge.cost) continue;

//            System.out.println(edge.idx + " cost : " + edge.cost);

            if(edge.idx == end) break;

            for(Edge innerEdge : graph.get(edge.idx)){
                if(dist[innerEdge.idx] > edge.cost + innerEdge.cost){
                    dist[innerEdge.idx] = edge.cost + innerEdge.cost;
                    path[innerEdge.idx] = edge.idx;
                    queue.add(new Edge(innerEdge.idx , dist[innerEdge.idx]));
                }
            }
        }

//        System.out.println(Arrays.toString(path));
        find(end);
        output.write(dist[end] + "\n");
        output.write(resultPath.size() + "\n");
        for(Integer number : resultPath){
            output.write(number + " ");
        }
        output.flush();
        output.close();
    }
}
