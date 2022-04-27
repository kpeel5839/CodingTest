import java.util.*;
import java.io.*;

// 1865 : 웜홀

/*
-- 전제조건
그냥 벨만포드 알고리즘이고 , 사이클이 존재하는지 확인하는 것이다.
사이클이 존재하게 된다면 , 어떻게든 모든 지점에 값을 더 줄여서 갈 수 있다 왜냐하면 , 가중치의 합이 음수인 사이클을 100만번
돌면 어딜 가든 더 이전보다 최소 비용으로 갈 수 있게 되는 것이다.

TestCase 개수 가 첫줄에 주어지고
테스트 케이스의 첫번째 줄은 지점의 수 즉 v가 주어지고 , 도로의 개수 e가 웜홀의 개수 w가 주어진다.
그리고 s e t 가 주어지고 e 만큼 , 그다음에 w만큼 웜홀의 개수가 주어지는데
s = 시작 지점 , e = 도착 지점 , t = 줄어드는 시간을 의미한다.

w가 주어질 떄에는 단방향에 음수라는 것을 잘 인지해야 한다.
-- 틀 설계
엣지들 리스트를 만들어서 다 추가한다.
그 다음에 dist = 를 v + 1 만큼 만들고 , visited 도 만드낟.
그러면서 간선의 개수만큼 진행하면 된다. V 번 진행하면 된다.
그러면서 마지막 진행에서 dist 정보가 바뀐다면 사이클이 발생 Yes
안 바뀐다면 음수사이클이 없으니 no를 출력한다.

-- 결론
사실 이 문제는 평범한 벨만 포드 문제인 줄 알았다 , 근데 아니였다
예외인 점은 이 그래프가 연결 그래프라는 것이 보장이 되지 않는다는 것이다 , 하나의 지점에서 출발을 하였을 때 , 다른 지점으로 도달하지 못하는 경우가 존재한다는 것이다.
그렇다는 것은 모든 정점에 대해서 사이클을 찾아야 하는 , 즉 그래프 전체에 사이클이 존재하냐 안하냐를 판단해야하는 이 문제에서 한 정점을 기준으로
다른 정점까지의 비용을 갱신하면서 하는 일반적인 벨만포드 방법은 좋지 않다는 생각이 들었다. (질문 게시판도 보고)

그래서 , 한번 0으로 다 초기화해보았다 , 만일 음수 값의 가중치가 존재하지 않는다면?
값들은 0으로 계속 유지가 될 것이다 , 근데 이 경우는 볼 필요도 없다 , 음의 가중치가 존재하지 않는다면 , 사이클 조차 존재하지 않는 일반적인 평범한 , 그래프이니까,
그럼 이제 음수 값의 가중치가 있지만 , 사이클이 존재하지 안흔ㄴ 경우를 고려할 수 있다 , 그런 경우에는 몇몇의 정점들이 음수값으로 초기화 될 것이다.
그렇게 되면 예를 들어서
   1
  / \
 2 - 3
이렇게 되어있다고 가정했을 때 3에서 출발을 한다고 가정해보자 , (딱히 출발지점을 정해놓지는 않는다.)
그래서 3 -> 1 : -4 , 1 -> 2 : 3 2 -> 3 : 1 이렇다고 가정하자
그러면 dist[1] 은 당연하게도 -4 로 초기화 될 것이다 , 그 다음에 dist[2] 는 -1로 초기화 될 것이다.
이제 3은? 2 -> 3 : 1 or 0 인데 두개의 값이 똑같다 dist[2] + cost 하나 , dist[3] 이나 같다라는 것이다.
그래서 , 여기서 볼 수 있는 것은 이제 음의 사이클이 없다면 , 0으로 가중치를 설정하더라도 정확한 최소비용은 당연하게도 나오지 않는다 , 만일 최소비용을 구하려면 , 올바르지 않은 풀이 방법이다.
하지만 , 이 문제는 사이클의 유무를 확인하는 문제이기 때문에 , v - 1 개의 간선을 선택한 경우 , 1 개라도 더 선택하였을 때 , 음수의 사이클이 형성이 되냐를 보는 거다 , 혹은 이전에(이전에 사이클이 형성화 되어 있더라면 , 당연하게도 계속 값들이 갱신 될 것이다.)
(계속 사이클로 연결되어 있는 애들은 본인의 사이클의 가중치의 합만큼 계속 감소가 될 것이다.)
이렇게 해서 , 위에 예제에서 볼 수 있는 점은 사이클이 있지 않다면 , 0으로 초기값을 설정하더라도 , 변하지 않는다는 것이다 , 사이클이 존재하지 않으니까
만일 근데 1 -> 2 : 2 로 바뀐다면? 사이클이 형성 되어 있다 그래서 3은 -1로 초기화 될것이고 , 출발 지점을 특정화 하지 않는다면 더욱 더 빠르게 감소할 것이다.

그래서 쨋든 0으로 설정하여도 음수의 사이클은 확인할 수 있다라는 것은 꺠달았으니 , 이제 모든 정점들이 간선을 동시에 택해도 왜 되는 건지를 보자.
이 문제는 계속 말했듯이 시작정점을 특정하지 않는다 , 그렇기 때문에 이전에 시작 정점을 특정했던 이유는 시작 정점이 가장 먼 정점으로 부터 v - 1 개의 간선을 택하기 위함이였다,
근데 이 문제는 지금 시작정점이 특정되지 않았으니 , 그 행위를 모든 정점이 동시에 시작한다고 보면 된다 , 그렇기 때문에 이전처럼 Arrays.fill(dist , Integer.MAX_VALUE) , dist[1] = 0 이렇게 하는 것이 아닌
모든 정점의 값을 0으로 초기화 하고 시작하는 것이다 , 물론 최소비용은 정확히 나오지 않지만 위에서 말했던 것과 같이 음의 사이클은 찾아 낼 수가 있다.

그래서 결론적으로는 단편적으로 보았을 때 , 물론 실제로 값들이 그런 식으로 변동되지는 않겠지만 , 모든 정점들이 동시에 간선들을 찾아나가며 , 그렇기에 0으로 모두 초기화된 상태에서 시작해야하는 것이고
음의 사이클이 존재하는 것만을 찾아내면 되니 , 최소비용을 구태여 찾을 필요가 없다 , 이것이 이 문제의 핵심 키워드라고 할 수 있는 부분인 것 같다.

 */
public class Main {
    public static class Edge{
        int sta;
        int des;
        int cost;
        public Edge(int sta , int des , int cost){
            this.sta = sta;
            this.des = des;
            this.cost = cost;
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
            int E = Integer.parseInt(st.nextToken());
            int W = Integer.parseInt(st.nextToken());

            int[] dist = new int[V + 1];
            boolean[] visited = new boolean[V + 1];
            List<Edge> edge = new ArrayList<>();

//            Arrays.fill(dist , Integer.MAX_VALUE);
//            Arrays.fill(visited , false);

            for(int j = 0; j < E; j++){ // 도로를 받을 때에는 양방향에 양수임
                st = new StringTokenizer(input.readLine());
                int sta = Integer.parseInt(st.nextToken());
                int des = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());

                edge.add(new Edge(sta , des , cost));
                edge.add(new Edge(des , sta , cost));
            } // 양방향으로 추가

            for(int j = 0; j < W; j++){
                st = new StringTokenizer(input.readLine());
                int sta = Integer.parseInt(st.nextToken());
                int des = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken()) * -1;

                edge.add(new Edge(sta , des , cost));
            }

            for(int j = 1; j <= V; j++){
                edge.add(new Edge(0, j , 0));
            }

//            dist[0] = 0;
            boolean cycle = false;
//            visited[0] = true;

            for(int j = 0; j < V; j++){
                for(Edge innerEdge : edge){ // 이미 방문을 했으면서 , innerEdge.cost + dist[innerEdg.sta] 로 한다.
                    // 벨만 포드의 기본 이념은 플로이드 와샬과 비슷하게 , 이 곳을 경유했을 때 더 빠른지를 보는 거싱다.
                    if(dist[innerEdge.des] > dist[innerEdge.sta] + innerEdge.cost){
                        dist[innerEdge.des] = dist[innerEdge.sta] + innerEdge.cost;
                        if(j == V - 1) cycle = true;
                    }
                }

                for(int c = 1; c <= V; c++){
                    if(dist[c] != Integer.MAX_VALUE) visited[c] = true;
                }
            }

//            System.out.println(Arrays.toString(dist));

            if(cycle) output.write("YES" + "\n");
            else output.write("NO" + "\n");
        }

        output.flush();
        output.close();
    }
}
