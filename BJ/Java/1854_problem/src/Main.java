import java.util.*;
import java.io.*;

// 1854 : K번째 최단경로 찾기
/*
-- 전제조건
n , m , k 가 주어지고 n 은 도시의 개수 m 은 간선의 개수이다.
그리고 k는 내가 찾아야 할 경로이다.
그리고서 이제 1번부터 k 번째 경로를 순서대로 출력한다.
1 -> 1
1 -> 2
1 -> 3 k번째 빠른 경로
k 번째 최단 경로라는 것을 잊으면 안된다.
... 이런식으로
-- 틀 설계
그냥 원래 하던 다익스트라 방식에서 dist 배열을 priorityQueue로 선언해주는 것일 뿐이고
거기서 k개만 유지되도록 하면 된다.

근데 이제 만약에
5 4 3 2 1 이라는 경로비용들이 존재했다.
근데 4번째를 알고 싶다.
그렇다는 건 4가 필요하다는 것이다.
근데 경로 비용이 5 3 2 4 1 이렇게 들어온다고 가정하고 k개로 유지하면서 k번째를 뽑아낼 수 있는 방법을 생각해보자.
5 그대로 유지
5 3 2 4 이것까지는 유지를 한다.
그 다음에 1이 들어오면 가장 높은 숫자인 5를 빼야한다.
3 2 4 1 이렇게 그러면 최단 경로를 4번째 것을 구할 수가 있다.

그럴려면 priorityQueue에서 최고 비용이 가장 위에 올라와있어야한다.
그러면 내림차순으로 정렬이 되어있어야한다.
그 점만 제외하면 내림차순으로 정렬한다음 k보다 priorityQueue의 사이즈가 더 크게 되면 그 때 peek 것과 바꾸면 된다.
근데 이제 peek 값보다 edge.cost + innerEdge.cost 가 더 작아야지 바꿀 수 있다는 것을 명심해야한다.

그렇게 해서 전체적인 설계를 하면
일단 입력을 받아서 그래프를 형성하고
dist 배열을 초기화한다.

그 다음에 1로 다익스트라를 시작해서 dist를 채워넣는다.
근데 이제 이 채워넣는 과정에서 queue에다가 집어넣을 때 잘 집어넣어야 한다 , 위에서 말한 조건을 토대로 집어넣으면 된다.

그 다음에 다 끝났다 그러면 , dist 인덱스를 하나하나씩 돌면서 dist.size() == k 이면 출력
그렇지 않으면 -1을 출력하면 된다.

-- 결론
일단은 k개의 경로들이 다 나올때 까지는 게속 다익스트라로 탐색을 진행한다.
그리고 k개의 경로들이 해당 정점에 다 나왔다면 , 거기에서 부터는 이제 dist[i] 에서 가장 최고 높은 비용과
비교해서 지금 현재 비용이 더 높으면 갈 필요가 없는 것이 확실하고 만약에 낮다면
dist[i] 에 있는 순서들을 바꿔야한다 , 그렇기 때문에 priorityqueue를 사용하는 것이다.

그러니까 결론은 그냥 비용들을 k개까지는 그냥 받은 다음에 , 이제 그 다음부터 경로들을 걸러서 받는 것이라고 보면된다.
그렇게 해서 k개의 경로가 모든 정점에 다 차게 되면 서서히 반복이 끝나간다.

그렇기 때문에 그냥 일반 다익스트라 알고리즘보다 시간은 훨씬 많이 걸리지만, k번째 비용의 경로를 찾을 수 있다.

-- 해맸던 점
dist[innerEdge.idx].poll 을 해서 dist를 솎아내야하는데,
진짜 멍청하게 queue.poll() 하고 있었음
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
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        PriorityQueue<Integer>[] dist = new PriorityQueue[n + 1];
        List<ArrayList<Edge>> graph = new ArrayList<>();
        PriorityQueue<Edge> queue = new PriorityQueue<>((o1 , o2) -> Integer.compare(o1.cost , o2.cost));

        for(int i = 0; i <= n; i++){
            graph.add(new ArrayList<>());
            dist[i] = new PriorityQueue<>((o1 , o2) -> o2 - o1);
        }

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(input.readLine());
            graph.get(Integer.parseInt(st.nextToken())).add(new Edge(Integer.parseInt(st.nextToken()) , Integer.parseInt(st.nextToken())));
        }

        queue.add(new Edge(1 , 0));
        dist[1].add(0);

        while(!queue.isEmpty()){
            Edge edge = queue.poll();
            for(int i = 0; i < graph.get(edge.idx).size(); i++){
                Edge innerEdge = graph.get(edge.idx).get(i);
                if(dist[innerEdge.idx].size() < k){ //k 보다 작을 때까지만 추가해야지 k개까지 유지됨
                    dist[innerEdge.idx].add(edge.cost + innerEdge.cost);
                    queue.add(new Edge(innerEdge.idx , edge.cost + innerEdge.cost));
                }else if(dist[innerEdge.idx].peek() > innerEdge.cost + edge.cost){
//                    output.write(queue.peek() + "\n");
                    dist[innerEdge.idx].poll();
                    dist[innerEdge.idx].add(edge.cost + innerEdge.cost);
                    queue.add(new Edge(innerEdge.idx , edge.cost + innerEdge.cost));
                }

//                output.write(edge.cost + innerEdge.cost + "\n");
            }
        }

        for(int i = 1; i <= n; i++){
//            output.write(dist[i].peek() + "\n");
//            output.write(dist[i] + "\n");
            if(dist[i].size() == k) {
                output.write(dist[i].peek() + "\n");
            }
            else output.write(-1 + "\n");
        }

        output.flush();
        output.close();
    }
}