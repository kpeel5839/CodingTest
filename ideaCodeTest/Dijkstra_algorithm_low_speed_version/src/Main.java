import java.util.*;
import java.io.*;

// 다익스트라 시간 복잡도 높은 버전
/*
-- 틀 설계
1. 정점의 개수와 간선의 개수를 받고
2. 정점의 개수 + 1만큼 List를 add하고 , visited , dist 배열을 선언한다. (dist 배열은 현재 start node에서 해당 node 까지로의 현재 최소비용이다.)
3. 일단 처음에는 dist를 모두 Integer.MAX_VALUE 로 세팅하고 , 그리고 start 지점의 dist만 0으로 초기화한다 , 그 다음 visited 와 보고서 실행한다.
4. 일단 visited , dist , 그리고 그와 연결된 Edge 를 이용하는 것이 이 알고리즘의 핵심임
 */
public class Main {
    public static class Edge{
        int nxtNode;
        int cost;
        public Edge(int nxtNode , int cost){
            this.nxtNode = nxtNode;
            this.cost = cost;
        }
    }
    public static void find(int vertexNumber , int[] parent){
        if(vertexNumber == 1){ //start를 그냥 1이라고 고정하고 , 만약 고정이 안되있는 거면 그냥 start 전역으로 만들거나 넘겨주면 됨
            System.out.println(vertexNumber + "]");
            return;
        }else{
            if(vertexNumber == 6) { //end를 그냥 8이라고 고정하고 시작
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
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken()); //이거는 나중에 짜고 나서 end 지점까지 지정해서 거기까지의 최소 경로를 찾을 때 쓰도록 하자 , end 지점까지의 최소 비용 경로가 나오게 되면 더 할 필요가 없으니까
        /*
        1. 일단 vertexList로 관리하는데 이것은 일단 2차원의 List로 관리를 하고
        2. 가장 겉의 인덱스는 해당 정점을 뽑아 내는 것 , 그리고 안쪽의 index는 해당 정점이 가지고 있는 edge의 정보를 나타낸다.
        3. 일단 dist는 모두 Integer.MAX_VALUE 초기화해준다.
        4. 그 다음에 startNode의 dist만 0 으로 초기화해준다. 그런 다음에 결국 모든 vertex를 한번씩 다 돌아야하기 때문에 for(int i = 0; i < v; i++) 만큼 배열을 돌면서
        5. 계속 현재 상태에서 dist가 최소인 배열을 찾아서 그 해당 node를 curNode로 뽑아내고 curNode와 연결된 dist를 다 재설정한다
        6. 그러고서 마지막에 dist배열을 전부다 출력하면 start 지점에서부터의 모든 지점까지의 최단경로가 나오게 됨
        7. 경로까지 추적할려면 내 생각에는 경로에 또 정점 배열을 놓고 자신의 부모를 기록하는 것임
        8. 그러고서 재귀적으로 본인의 부모를 타고 계속 올라가면서 출력하면 시작노드까지 가게 되면 경로가 나올 듯
         */
        List<ArrayList<Edge>> vertexList = new ArrayList<>();
        int[] dist = new int[v + 1];
        int[] visited = new int[v + 1];
        int[] parent = new int[v + 1];

        for(int i = 0; i <= v; i++){
            vertexList.add(new ArrayList<>());
        }

        for(int i = 1; i <= e; i++){
            st = new StringTokenizer(input.readLine());
            int vertex1 = Integer.parseInt(st.nextToken());
            int vertex2 = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            vertexList.get(vertex1).add(new Edge(vertex2 , cost));
        }

        for(int i = 1; i <= v; i++){
            dist[i] = Integer.MAX_VALUE;
        }

        dist[start] = 0;
        parent[start] = 1;

        for(int i = 0; i < v; i++){
            int curNodeIdx = 0;
            int minCost = Integer.MAX_VALUE;

            for(int j = 1; j <= v; j++){
                if(minCost > dist[j] && visited[j] != 1){
                    minCost = dist[j];
                    curNodeIdx = j;
                }
            }//최소 비용인 것을 뽑아냈음 이제 그러면 이 curNode를 뽑아내고 해당 node의 간선을 다돌아야함

            if(end == curNodeIdx){
                System.out.println(dist[curNodeIdx]);
                break;
            }

            visited[curNodeIdx] = 1;

            for(int j = 0; j < vertexList.get(curNodeIdx).size(); j++){
                Edge edge = vertexList.get(curNodeIdx).get(j);

                if(visited[edge.nxtNode] == 1){
                    continue;
                }

                if(dist[edge.nxtNode] > dist[curNodeIdx] + edge.cost){
                    dist[edge.nxtNode] = dist[curNodeIdx] + edge.cost;
                    parent[edge.nxtNode] = curNodeIdx;
                }
            }
        }
        find(6 , parent);
//        System.out.println(Arrays.toString(dist));
    }
}
