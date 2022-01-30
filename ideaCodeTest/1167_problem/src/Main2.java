import java.util.*;
import java.io.*;

// 1167 : 트리의 지름
/*
-- 전제조건
트리의 지름이란 트리에서 임의의 두 점 사이의 거리 중 가장 긴 것을 말한다, 트리의 지름을 구하는 프로그램을 작성하시오
트리가 입력으로 주어진다
먼저 첫 번째 줄에서는 트리의 정점의 개수 V가 주어지고
둘째 줄부터 V개의 줄에 걸쳐 간선의 정보가 다음과 같이 주어진다.
둘째줄부터 주어지는 것은 먼저 정점 번호가 주어지고 , 이어서 연결된 간선의 정보를 의미하는 정수가 두개씩 주어진다.
그 두개의 정수는 하나는 정점 번호 , 다른 하나는 그 정점까지의 거리이다. (가중치)
-1은 입력의 마지막이다 이렇게 주어지는 이유는
시작 정점 , (정수 2개 - (목적지 정점 번호 , 가중치)) 이런식으로 이루어져있고 이게 몇개가 올 지를 모르니까 -1로 입력이 끝나는 것임
그리고 주어지는 간선의 정보는 양방향 간선이 아닌 단방향 간선임을 알 수가 있다.
-- 틀 설계
설계는 이러하다. (시간초과 해결 못하겠어서 답 봄)
트리의 특성상 가장 먼 거리의 두 지점은 , 어떠한 정점과 어떠한 정점이 촤장거리라고 했을 때
가장 먼 거리의 두 지점이 있는 것이 , 무조건 겹친다라는 것이다
예를 들어서 1 -> 5 , 5 -> 1 이 가장 큰 거리라고 가정하자
그러면 어떠한 정점 2,3,4 에서 시작하더라도 얘내들의 최장거리에 위치해있는 정점의 번호는 1 , 5 번 두개중 하나라는 것이다.
그렇기 때문에 어떠한 임의의 정점 에서 가장 먼 거리의 정점이 구해졌다?
그러면 그 정점으로 부터 가장 먼 거리의 정점을 찾으면 그것이 가장 먼 거리라고 할 수가 있는 것이다.
그에 대한 예로 1을 선택해서 가장 먼 거리에 있는 것이 5라는 것을 밝혀냈다 , 그러면 5에서 또 가장 먼 거리를 구해내면 1까지의 거리일 것이다.
이러한 예로 봐서 , 무조건 두개의 지점중 하나는 포함이 된다라는 것을 알 수가 있다.

추가적으로 그래프를 보면서 더 생각해보자 , 내가 이전에 그리고 더 헷갈렸던 부분은 이게 트리가 아니고 그래프라고 생각했기 때문이다.
예를 들어서 트리가 이런식으로 존재한다고 해보자.

1 - 2 - 3 - 5
        |
        4
이런식으로 존재한다고 가정했을 때 모두 가중치가 1이라고 생각해보자.
그렇다면 가장 먼 거리는 1 -> 5 혹은 1 -> 4 가 될 것이다.
혹은 5 -> 1 , 4 -> 1이다.
그러면 여기서 2와 3의 가장 먼 거리를 가정해보자.
그러면 2의 가장 먼거리는 2 -> 5 , 2 -> 4 의 경우가 있고
3의 가장 먼거리는 3 -> 1이다. 여기서 볼 수 있는 것은 가장 먼 거리의 정점은 어떤 정점에서 시작해서 가장 먼 거리의 정점을 찾을 때에 무조건 포함 된다는 것을 알 수 있다.
만일 여기서 3 - 4의 가중치가 2라면 ? 1 -> 4 , 4 -> 1 이고 가장 먼 거리의 정점들은 , 2는 2 -> 4 , 3은 3 -> 1 , 3 -> 4 이다
이러한 결과가 나오는 이유는 트리의 특성에서 나오게 된다 , 사이클이 존재하지 않음으로 , 즉 하나의 정점에서 하나의 정점으로 가는데 유일하게 한 경로만이 존재한다.
그렇기 때문에 양 끝단의 노드는 존재하기 마련이다 , 양 끝단의 노드가 여러개이더라도 무조건 존재한다.
그러면 그 가중치들이 음수가 아닌이상 가는게 무조건 이득이라는 게 결론이다 , 양 끝단의 노드가 만약에 여러개라고 하면 끝까지 가되 가장 큰 가중치를 가진 노드가 가장 먼 거리의 노드가 될 것이다.
그리고 하나의 노드까지 방문하는데 무조건 경로가 하나이기에 어떤 정점에서 출발하더라도 양 끝단 노드로 가는 것이 가장 가중치가 큰 경로라는 것이다.
그렇기 때문에 여기에서의 양 끝단의 노드는 1 , 4 , 5라고 할 수 있고 가중치가 모두 같다고 했을 때 , 1 을 선택 , 4 를 선택 , 5 를 선택하는 경우로 나뉘는 것이다.
3 - 4 의 가중치가 2였던 경우는 이것역시 가중치를 보고 양 끝단의 노드 중 가장 큰 값들을 선택해서 1 , 4를 선택하는 것을 알 수 있다 , 음수가 없기 때문에 무조건 양 끝단의 노드들 중에 고르는 것이다.

아마도 이게 무조건 트리라는 조건이 명확하게 나와있었다면 분명히 생각할 수 있었겠는데 , 그게 너무 아쉽다.
 */
public class Main2 {
    public static int n , ans = 0;
    public static int[] visited;
    public static int[] dist;
    public static List<ArrayList<Edge>> graph = new ArrayList<>();
    public static class Edge{
        int idx;
        int cost;
        public Edge(int idx , int cost){
            this.idx = idx;
            this.cost = cost;
        }
    }
    public static void dfs(int vertex , int distance){
        /*
        가장 먼 거리에 있는 지점을 반환해야함
        그러면서 ans를 계산해야함
        이제 더 이상 갈데가 없다면 거기가 종점이니 그것을 이용해야함.
         */
        ans = Math.max(ans , distance);
        dist[vertex] = distance;
        for(int i = 0; i < graph.get(vertex).size(); i++){
            Edge edge = graph.get(vertex).get(i);
            if(visited[edge.idx] == 1) continue;
            visited[edge.idx] = 1;
            dfs(edge.idx , distance + edge.cost);
        }

    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(input.readLine());
        visited = new int[n + 1];
        dist = new int[n + 1];

        for(int i = 0; i <= n; i++){
            graph.add(new ArrayList<>());
        }

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            int start = Integer.parseInt(st.nextToken());
            while(st.hasMoreTokens()){
                int destination = Integer.parseInt(st.nextToken());
                if(destination == -1) break;
                int cost = Integer.parseInt(st.nextToken());
                graph.get(start).add(new Edge(destination , cost));
            }
        }

        visited[1] = 1;
        dfs(1 , 0);

        int max = 0;
        int endVertex = 0;
        for(int i = 1; i <= n; i++){
            if(max < dist[i]){
                max = dist[i];
                endVertex = i;
            }
        }

        visited = new int[n + 1];
        visited[endVertex] = 1;
        dfs(endVertex , 0);
        System.out.println(ans);
    }
}
