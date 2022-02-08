import java.util.*;
import java.io.*;

// 11657 : 타임머신

/*
-- 전제조건
그냥 간선들이 주어지면 최단 경로를 구하면 되는데
음수의 가중치가 껴있기 때문에 벨만포드 알고리즘을 사용하여서 풀어야한다.
-- 틀 설계
벨만 포드 알고리즘을 사용할 것이고 ,
벨만 포드의 기본 가정은 이러하다 , 다익스트라 같은 경우는 기본이념 자체가
시작 노드에서 부터 시작해서 , 해당 간선으로 부터 뻗어 나가는 간선들로 시작 정점부터 도착 정점까지의 정점들의 비용들을 초기화하고
그것을 게속 반복해서 , 모든 정점들이 초기화되면 끝내는 개념이다.

근데 벨만포드의 기본 이념은 음수사이클이 존재하면 안된다이다.
그래서 벨만포드 알고리즘은 정점을 기준으로 연산을 하는 것이 아닌
간선들을 중점으로 알고리즘을 실행시킨다.

그래서 모든 엣지를 검사하면서 , 비용들을 초기화 시키는데,
아직 방문하지 않을 곳을 포함하려고 하면 , Integer.MAX_VALUE 로 array 를 초기화 해놨기 때문에 거를 수가 있다.
그래서 점차적으로 방문을 할 수가 있게 되는 것이다.

이게 무슨 말이냐면 , 모든 엣지에서 연산을 실행하면 값들이 중구난방이 될 수도 있다 , 예륻 들어서 1 -> 2 -> 4 이렇게 갈 수 있는데
4의 비용이 초기화 된다거나 , 이러한 현상이 발생할 수 있다 , 근데 이것을 막으면서 1 -> 2 -> 4 의 경로를 2번의 연산을 통해서 할 수가 있는 것이다.
이 말은 즉슨 edge relaxing 이 진행될 때마다 , 시작노드로부터 경로가 하나씩 추가된다라는 것이다.
그렇다는 것은 시작 노드로부터 경로를 하나하나씩 추가가 되니까 |V| - 1 번을 edge relaxing을 진행하게 되면 ? 시작 노드로부터 최대 경로까지 가능한 것이다.
근데 만일 여기서 한번이라도 더 진행한다? 그러면 사이클이 진행될 수도 있다 , 즉 , 이미 방문해온 경로를 다시 방문한다는 것이다.
근데 만약 가중치의 값이 양수라면 ? 돌아온 곳을 다시 간다는 것은 무조건 비효율적일 수 밖에 없다 , 그래서 음수 사이클이 존재하지 않는다면 , v - 1 번에서 한번 더 연산을 실행하더라도 값이 갱신되지 않을 것이다.

근데 만일 값이 갱신된다? 온 길을 다시 돌아갔는데 최단경로다? 이것은 음수사이클이 존재할 수밖에 없는 것이다.
이 점을 이용해서 벨만 포드 알고리즘을 적용할 수 있다.

그래서 마지막에 v - 1 번을 진행한다음에 1번을 진행하였을 때 값이 변경된다면 -1을 출력하고 그렇지 않다면 값들을 다 출력한다.

-- 결과
벨만포드는 위에 설명한 것과 같이 v - 1 번까지만 진행하여서 음수의 가중치를 가지는 사이클을 방지하며
v 번까지 실행해서 , v 번에서 값이 변경이 되면 음의 사이클이 존재하는지 여부를 판단이 가능하다.

edge relaxing을 한번씩 진행할 때마다 너비 우선으로 비용들이 초기화 되는 것들을 이용한 알고리즘이다.

음수의 사이클이 존재하는 정상적이지 않은 그래프인지 확인하는 용도도 가능하다.

그리고 계속 생각해봤는데 음의 사이클이 있으면 중간에 있다면 v - 1 번까지 하기 전에도 이미
사이클은 계속 돌고 있을 확률이 높다 , 그래서 플로이드 워샬 알고리즘은 항상 v번까지 실행하여서
음의 사이클이 존재하는지 확인해야 할 듯하다, 왜냐하면 그냥 출력하면 아얘 말이 안되는 값들이 나올 수도 있기 때문이다.
왜냐하면 계속 사이클을 돌고 있었을 것이니까 v 번까지 하기 전까지
 */
public class Main {
    public static int v , e;
    public static class Edge{
        int start;
        int destination;
        int cost;
        public Edge(int start , int destination, int cost){
            this.start = start;
            this.destination = destination;
            this.cost = cost;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(input.readLine());

        v = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());

        Edge[] edges = new Edge[e];
        int[] visited = new int[v + 1];
        long[] dist = new long[v + 1];

        for(int i = 0; i < e; i++){
            st = new StringTokenizer(input.readLine());
            int start = Integer.parseInt(st.nextToken());
            int destination = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            edges[i] = new Edge(start , destination , cost);
        }

        Arrays.fill(dist , Integer.MAX_VALUE);
        dist[1] = 0;
        visited[1] = 1;
        boolean cycleExist = false;
        for(int i = 0; i < v; i++){ //연산을 v번을 실행해야함 , v 번은 값이 변경된다? 그러면 바로 나가리
            for(int j = 0; j < e; j++){ // 모든 엣지를 다 돌아본다.
                //start가 이미 방문한 곳이여야함
                Edge edge = edges[j];
                if(visited[edge.start] != 0 && (dist[edge.destination] > dist[edge.start] + edge.cost)){ //시작 위치가 이미 방문한 곳이면서 , 목적지의 값이 더 큰 경우
                    dist[edge.destination] = dist[edge.start] + edge.cost;
                    if(i == v - 1){
                        cycleExist = true;
                        break;
                    }
                }
            }
            for(int j = 1; j <= v; j++){ // 값 변경하고 방문처리
                if(dist[j] != Integer.MAX_VALUE) visited[j] = 1;
            }
        }

        dist[1] = Integer.MAX_VALUE;

        if(cycleExist){
            output.write(-1 + "\n");
        }else {
            for (int i = 2; i <= v; i++) {
                if (dist[i] == Integer.MAX_VALUE) output.write(-1 + "\n");
                else output.write(dist[i] + "\n");
            }
        }

        output.flush();
        output.close();
    }
}
