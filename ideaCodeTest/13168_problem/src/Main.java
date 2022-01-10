import java.util.*;
import java.io.*;

// 13168 : 내일로 여행
/*
-- 전제조건
1. 친구들이 여행을 간다.
2. 내일로 여행 티켓을 사면 ITX는 무조건 무료이고 , S-Train , V-Train을 50% 할인된 가격으로 이용 가능
3. 나머지는 할인 x
4. 이 경우에서 내일로 티켓을 사서 가는게 나을 까 아니면 안 사는게 나을까를 결정함
5. 사는게 나으면 Yes이고 아니면 No를 출력하면 된다.
-- 틀 설계
1. 애초에 정점은 일단 도시의 개수만큼 선언한다.
2. 도시의 이름과 vertexNumber는 HashMap으로 관리한다.
3. 처음에 받은 N 만큼 입력을 받으면서 HashMap을 추가하면서 ArrayList도 추가한다. (다익스트라 알고리즘 사용할 것임)
4. 그리고 dist1 에다가 다 구해놓음(최소비용들)
5. 그리고 이제 두 번째 vertexList 에다가는 처음에 넣을 때 부터 내일로 할인 되는 것들은 노드들을 아얘 그렇게 집어넣음
6. 할인 되는 것을 알 수 있는 방법은 그냥 할인 리스트로 만들고 확인함
7. 그러고서 dist1 , dist2 다 구한다음
8. 서로 티켓값을 확인하고 결정함
9. 여기서 티켓값 반값으로 할 때 double 혹은 float 연산으로 진행해야함
-- 해맸던 점
1. cost 반으로 나누는 거 실수 나눗셈 해야하는데 그냥하고 , cost도 int로 선언함
2. 그거 말고는 설계부터 괜찮았음
-- 번외
1. 문제 맞춘 이후 경로까지 나오도록 설정하였음
 */
public class Main {
    public static String[] cityNameList;
    public static class Edge{
        int idx;
        double cost;
        public Edge(int idx , double cost){
            this.idx = idx;
            this.cost = cost;
        }
    }
    public static void find(int vertexNumber , int start , int end , int[][] parent){
        if(vertexNumber == start){
            System.out.print("[" + cityNameList[vertexNumber] + " -> ");
            return;
        }
        else{
            find(parent[start][vertexNumber] , start, end , parent);
            if(vertexNumber == end){
                System.out.println(cityNameList[vertexNumber] + "]");
            }else{
                System.out.print(cityNameList[vertexNumber] + " -> ");
            }
        }
    }
    public static String[] disHalf = {"S-Train" , "V-Train"};
    public static String[] disAll = {"ITX-Saemaeul" , "ITX-Cheongchun" , "Mugunghwa"};
    public static void main(String[] args) throws IOException{
        /*
        1. dist1 , dist2는 각각 내일로 산 버전 , 안 산버전 이렇게 두개를 운용할 것임
        2. cost1 , cost2는 이것도 내일로 산 버전 , 안 산버전 이렇게 두개
        3. 그리고 처음에 주어지는 v + 1 개수 만큼 dist[v + 1][v + 1] 이렇게 dist1 , 2 선언하고
        4. v + 1 만큼 List<ArrayList<Edge>> 도 선언할 것임
        5. 그리고 경로인 path도 HashMap을 이용해서 배열로 정리해놓을 것이고 이것은
        6. 마지막에 for(int i = 1; i < path.length; i++) cost1 += dist1[path[i - 1][path[i]] 이런식으로 진행할 것임
        7. 간선을 추가적으로 받을 때에도 HashSet을 이용할 것인데 솔직히 , KTX 이런거는 신경 안써도 된다.
        8. 그냥 disHalf 인지 disAll 인지 확인 후 graph1에는 discount하고 넣고 graph2에는 discount안하고 넣는다.
        9. 그리고 간선을 추가할 때에는 이거는 양방향이라서 둘다 해야한다.
        10. 그리고서 priority queue를 이용해서 다익스트라 알고리즘 적용하면 끝난다.
         */
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int v = Integer.parseInt(st.nextToken()), ticketCost = Integer.parseInt(st.nextToken());
        double cost1 = 0 , cost2 = 0;
        double[][] dist1 = new double[v + 1][v + 1] , dist2 = new double[v + 1][v + 1]; //dist1 = 내일로 산 거 , dist2 = 내일로 안산거
        HashMap<String ,Integer> cityList = new HashMap<>();
        cityNameList = new String[v + 1]; // 이건 경로를 표시하기 위한 배열
        int[][] parent1 = new int[v + 1][v + 1];
        int[][] parent2 = new int[v + 1][v + 1];
        List<ArrayList<Edge>> graph1 = new ArrayList<>();
        List<ArrayList<Edge>> graph2 = new ArrayList<>();
        PriorityQueue<Edge> queue;

        st = new StringTokenizer(input.readLine());
        graph1.add(new ArrayList<>());
        graph2.add(new ArrayList<>());

        for(int i = 0; i <= v; i++){
            for(int j = 0; j <= v; j++){
                dist1[i][j] = Integer.MAX_VALUE;
                dist2[i][j] = Integer.MAX_VALUE;
            }
        }

        for(int i = 1; i <= v; i++){
            String cityName = st.nextToken();
            cityList.put(cityName , i);
            cityNameList[i] = cityName;
            graph1.add(new ArrayList<>());
            graph2.add(new ArrayList<>());
        }

        int m = Integer.parseInt(input.readLine());
        st = new StringTokenizer(input.readLine());
        int[] path = new int[m];

        for(int i = 0; i < m; i++){
            int vertex = cityList.get(st.nextToken());
            path[i] = vertex;
        }

        int vehiclesCount = Integer.parseInt(input.readLine());
        for(int i = 0; i < vehiclesCount; i++){
            st = new StringTokenizer(input.readLine());
            String type = st.nextToken();
            int vertex1 = cityList.get(st.nextToken()) , vertex2 = cityList.get(st.nextToken());
            double cost = Integer.parseInt(st.nextToken());
            // 여기서는 graph1 이 내일로 산 버전이니까 그냥 버전으로 먼저 간선 집어 넣고 이거 맞는 지 확인한다음에 cost 바꾼다음에 집어넣자 graph1에다가
            graph2.get(vertex1).add(new Edge(vertex2 , cost));
            graph2.get(vertex2).add(new Edge(vertex1 , cost));
            for(int j = 0; j < disHalf.length; j++){
                if(disHalf[j].equals(type)){
                    cost = cost / 2f;
                }
            }
            for(int j = 0; j < disAll.length; j++){
                if(disAll[j].equals(type)){
                    cost = 0;
                }
            }
            graph1.get(vertex1).add(new Edge(vertex2 , cost));
            graph1.get(vertex2).add(new Edge(vertex1 , cost));
        }

        for(int j = 0; j < 2; j++) {
            List<ArrayList<Edge>> graph;
            double[][] dist;
            int[][] parent;
            if (j == 0) {
                graph = graph1;
                dist = dist1;
                parent = parent1;
            } else {
                graph = graph2;
                dist = dist2;
                parent = parent2;
            }
            for (int i = 1; i <= v; i++) {
                dist[i][i] = 0;
                queue = new PriorityQueue<>((o1, o2) -> Double.compare(o1.cost, o2.cost));
                queue.add(new Edge(i, 0));
                parent[i][i] = i;
                while (!queue.isEmpty()) {
                    Edge edge = queue.poll();

                    if(dist[i][edge.idx] < edge.cost){
                        continue;
                    }

                    for(int c = 0; c < graph.get(edge.idx).size(); c++){
                        Edge innerEdge = graph.get(edge.idx).get(c);
                        if(dist[i][innerEdge.idx] > edge.cost + innerEdge.cost){
                            dist[i][innerEdge.idx] = edge.cost + innerEdge.cost;
                            queue.add(new Edge(innerEdge.idx , dist[i][innerEdge.idx]));
                            parent[i][innerEdge.idx] = edge.idx;
                        }
                    }
                }
            }
        }

        for(int i = 0; i < 2; i++){
            double[][] dist;
            int[][] parent;
            if(i == 0){
                dist = dist1;
                parent = parent1;
                System.out.println("ticket buy after : ");
            }else{
                dist = dist2;
                parent = parent2;
                System.out.println("not ticket buy after : ");
            }
            for(int j = 1; j < m; j++){
                if(i == 0){
                    cost1 += dist[path[j - 1]][path[j]];
                    find(path[j] , path[j - 1] , path[j] , parent);
                }else{
                    cost2 += dist[path[j - 1]][path[j]];
                    find(path[j] , path[j - 1] , path[j] , parent);
                }
            }
        }

        cost1 += ticketCost;
//        System.out.println(cost1);
//        System.out.println(cost2);

        if(cost1 < cost2){
            System.out.println("Yes");
        }else{
            System.out.println("No");
        }
    }
}
