import java.util.*;
import java.io.*;

// 1167 : 트리의 지름
/*
--전제조건
트리의 지름이란 트리에서 임의의 두 점 사이의 거리 중 가장 긴 것을 말한다, 트리의 지름을 구하는 프로그램을 작성하시오
트리가 입력으로 주어진다
먼저 첫 번째 줄에서는 트리의 정점의 개수 V가 주어지고
둘째 줄부터 V개의 줄에 걸쳐 간선의 정보가 다음과 같이 주어진다.
둘째줄부터 주어지는 것은 먼저 정점 번호가 주어지고 , 이어서 연결된 간선의 정보를 의미하는 정수가 두개씩 주어진다.
그 두개의 정수는 하나는 정점 번호 , 다른 하나는 그 정점까지의 거리이다. (가중치)
-1은 입력의 마지막이다 이렇게 주어지는 이유는
시작 정점 , (정수 2개 - (목적지 정점 번호 , 가중치)) 이런식으로 이루어져있고 이게 몇개가 올 지를 모르니까 -1로 입력이 끝나는 것임
그리고 주어지는 간선의 정보는 양방향 간선이 아닌 단방향 간선임을 알 수가 있다.
--틀설계
일단 graph 객체에서 , 모든 노드로부터 최고 정점을 구하는 방향으로 가보자
일단 하나하나 다 받으면서 해당 vertex에 대한 간선들을 다 만들어준다.
그 다음에 startNode 를 하나하나씩 던져주고 dfs로 게속 ans값을 갱신한다.
 */
public class Main {
    public static int n , ans = 0;
    public static List<ArrayList<Edge>> graph = new ArrayList<>();
    public static int[] visited;
    public static void dfs(int start , int total){
        /*
        일단 start를 받으면 visited[start] = 1을 한다.
        그러면서 방문처리를 하고 방문했던 경우는 넘어오지 않을 테니 고려하지 않는다.
        그리고 항상 ans = Math.max(total , ans) 를 해서 , 모든 지점에서의 비용들을 비교한다.
        짜피 이렇게 해도 가장 높은 비용은 나오기 마련이니까.
        그리고 이제 for(int i = 0; i < graph.get(start).size(); i++) 로 하나하나 다 돌면서 비용을 구해준다.
        물론 이 과정에서 이미 visited가 찍힌 지점이 있다면 무시하고 지나가준다.
        그리고 선택하는 지점이 있다면? 그 떄는 dfs(graph.get(start).get(i).idx , graph.get(start).get(i).cost + total)
        이런식으로 선언해주면 된다.
        그리고 visited가 찍힌 것들은 가지 않기 때문에 따로 제동을 걸어주지 않아도 됨
         */
        ans = Math.max(ans , total);

//        System.out.println("Start : " + start);
        for(int i = 0; i < graph.get(start).size(); i++){
            Edge edge = graph.get(start).get(i);
//            System.out.println("Edge idx : " + edge.idx);
            if(visited[edge.idx] == 1) continue; //이미 방문한 곳
            // 그렇지 않으면
            visited[edge.idx] = 1;
            dfs(edge.idx , edge.cost + total);
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

        n = Integer.parseInt(input.readLine());

        for(int i = 0; i <= n; i++){
            graph.add(new ArrayList<>());
        }

        for(int i = 1; i <= n; i++){
            st = new StringTokenizer(input.readLine());
            int start = Integer.parseInt(st.nextToken());
            while(st.hasMoreTokens()){
                int destination = Integer.parseInt(st.nextToken());
                if(destination == -1){
                    break;
                }
                int cost = Integer.parseInt(st.nextToken());
                graph.get(start).add(new Edge(destination , cost));
            }
        }

        for(int i = 1; i <= n; i++){
            visited = new int[n + 1];
            visited[i] = 1;
            dfs(i , 0);
        }

        System.out.println(ans);
    }
}
