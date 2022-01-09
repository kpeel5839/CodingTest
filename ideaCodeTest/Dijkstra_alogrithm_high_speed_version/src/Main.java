import java.util.*;
import java.io.*;

// 다익스트라 시간 복잡도 낮은 버전
/*
-- 틀 설계
1. 이전 버전과 다르게 이것은
2. priorityQueue를 사용하여서
3. dist는 사용하지만 , visited를 사용하지 않는 방법이다.
4. 그렇기 때문에 재 방문이 가능하기에 현재의 node가 재방문이 되지 않기를 설계해야함
5. 그리고 priorityQueue를 이용해서 최소 비용의 그 지점까지 가는 최소 비용의 edge들을 우선적으로 실행하기에
6. dist[curNodex.idx] < curNode.cost 이면 continue; 해줘야함 , 그러니까 다음 가려는 edge 즉 지금 뽑힌 엣지가 저 다음 경로로 최소비용이 아니라는 의미인 것 , 그러니까 무의마하다는 것이다.
7. 그렇기에 continue; 해준다.
8. 그 다음에 다음에 갈 edge가 queue에서 뽑혔으니 거기로 가서 갱신 해준다음에 거기서 또 탐색해서 갱신되는 경우에만 queue에다가 집어 넣는다.
 */
public class Main {
    public static int start , end;
    public static class Edge{
        int idx;
        int cost;
        public Edge(int idx , int cost){
            this.idx = idx;
            this.cost = cost;
        }
    }
    public static void find(int vertexNumber , int[] parent){
        if(vertexNumber == start){ //start를 그냥 고정하고 , 만약 고정이 안되있는 거면 그냥 start 전역으로 만들거나 넘겨주면 됨
            System.out.println(vertexNumber + "]");
            return;
        }else{
            if(vertexNumber == end) { //end를 그냥 고정하고 시작
                System.out.print("[" + vertexNumber + " -> ");
            }
            else{
                System.out.print(vertexNumber + " -> ");
            }
            find(parent[vertexNumber] , parent);
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());

        int[] dist = new int[v + 1];
        List<ArrayList<Edge>> vertexList = new ArrayList<>();
        PriorityQueue<Edge> queue = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost , o2.cost));
        int[] parent = new int[v + 1];

        for(int i = 0; i <= v; i++){
            vertexList.add(new ArrayList<>());
        }

        for(int i = 1; i <= v; i++){
            dist[i] = Integer.MAX_VALUE;
        }

        for(int i = 1; i <= e; i++){
            st = new StringTokenizer(input.readLine());
            int vertex1 = Integer.parseInt(st.nextToken());
            int vertex2 = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            vertexList.get(vertex1).add(new Edge(vertex2 , cost));
        }

        dist[start] = 0;
        parent[start] = 1;
        queue.offer(new Edge(start , 0));

        while(!queue.isEmpty()){
            Edge edge = queue.poll();

            /*
            1. 이 구문은 내가 만약 1 -> 2로 갔음
            2. 그러면 dist[2] 는 1 -> 2로 간 것으로 설정이 될 것임
            3. 근데 여기서 만일 다른 곳에서 2 로 간 경우가 또 존재했다고 가정하자
            4. 근데 그것은 값이 높아서 우선순위 때문에 이제야 나왔다고 가정하자
            5. 그럼 지금 뽑힌 이 cost는 2에 가는 cost일 텐데
            6. 얘는 이미 다른 애들이 dist를 바꿔놔서 현재 이 Edge 가 가지는 cost보다
            7. dist가 더 작거나 같아
            8. 그러면 그냥 이 edge는 가봤자이니까 소용 없는 것
            9. 이미 distance가 설정되어 있고 더 낮고 하면 얘가 거기까지 가봤자임, 짜피 비용은 더 높거나 같으니까 탐색할 이유가 없는 것
            10. 그렇기 때문에 일단 아래 반복문에서 dist[innerEdge.idx] 즉 해당 edge.idx와 연결되어 있는 간선에 최소비용과
            11. 현재 idx까지 온 cost + innerEdge.idx까지 갈 cost를 더한 값이 dist[innerEdge.idx] 보다 작으면 넣고 queue에다가 집어넣더라도
            12. 이게 순서가 위에 말한 것처럼 뒤죽 박죽 되어서 여기를 이제 굳이 안가도 되는데 가야할 수도 있는 상황을 방지하는 것임
             */
            if(dist[edge.idx] < edge.cost) {
                continue;
            }

            for(int j = 0; j < vertexList.get(edge.idx).size(); j++){
                Edge innerEdge = vertexList.get(edge.idx).get(j);

                if(dist[innerEdge.idx] > edge.cost + innerEdge.cost){
                    dist[innerEdge.idx] = edge.cost + innerEdge.cost;
                    queue.offer(new Edge(innerEdge.idx , dist[innerEdge.idx]));
                    parent[innerEdge.idx] = edge.idx;
                }
            }
        }
        find(end , parent);
        System.out.println(Arrays.toString(dist));
    }
}
