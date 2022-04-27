import java.util.*;
import java.io.*;

// 6416 : 트리인가?
/*
--전제조건
트리의 구조를 만족하는지 판단을 해서 출력을 하여야함
세가지의 조건을 만족해야함

노드가 아얘 없는 경우도 트리의 조건을 만족하는 것이다.

1. 들어오는 간선이 하나도 없는 단 하나의 노드 모든 노드의 출발 지점인 root 노드를 의미한다.
2. 루트 노드를 제외한 모든 노드들은 반드시 단 하나의 들어오는 간선이 존재해야 한다.
3. 루트에서 다른 노드로 가는 경로는 반드시 가능하며 , 유일하다 , 이는 류트를 제외한 모든 노드에 성립해야 한다. (즉 루트 노드에서 모든 노드를 갈 수 있는 경로는 무조건 존재해야함)

입력은 여러개의 TC로 이루어져 있고 , 입력의 끝에는 종료가 가능하도록 음의 정수가 두개가 주어진다.
그리고 각각의 케이스마다 정수들이 주어지는데 2개씩 주어진다.
앞에는 u , 뒤에는 v 정점이고 u -> v 로 갈 수 있다라는 간선을 의미하고 , 단 방향이다.
TC의 번호는 1부터 시작하며 순서대로 받는 트리에 대해서 설명할 때
Case k is [not] a tree. 이런식으로 출력하면 된다. not은 당연히 트리의 조건을 만족하지 않을 때 붙히면 된다.
--틀 설계
루느 노드를 찾는 것이 중요하고 , 정점이 몇번 부터 몇번까지 있는 지 찾는 것도 중요할 듯
자 일단은 입력을 받으면서 HashMap에다가 6 - 0 , 8 - 1 번 vertexNumber를 따로 해서 HashSet 에다가 집어 넣어놔서 중복된 숫자가 들어오면 vertexNumber를 증가시키면서
HashMap에다가 집어넣는다 , HashSet에 없으면 HashSet에다가도 집어넣는다. list에다가도 당연히 집어넣고 있다.
그러면서 이제 다시 돌면서 간선으로 오지 않는 것 만일 그것을 다 제거했는데 HashSet이 size 가 2 이상이다 ? 그러면 틀린 트리
그리고 바로 0 0 이렇게 들어오는 것도 맞는 트리라고 출력한다.
그 다음에 HashSet이 size == 1 이라서 루트노드를 찾는다. 그런 다음에
루트 노드에서 bfs를 돌린다. 근데 만일 visited 로 vertexNumber를 선언한다. 일단 1 ~ n 까지 했으니
처음 HashSet의 사이즈를 저장해놨었어야 한다.
그러면 HashSet 사이즈만큼 + 1 해서 visited를 관리하고 만일 visited에 중복 방문을 하는 것이 있으면
그러면 나가리고 , 아니다 그러면 맞는 트리인 것이다.
받은 배열들을 돌면서 뒤에 들어오지 않은 정점을 찾는다. (들어오는 간선이 없는 것이 루트 노드이기 때문에 뒤에 한번도 존재하지 않았던 것이 루트 노드일 것이다.)

-- 해맸던 점
continue 예외 처리를 몇개 실수 하였고
입력을 받는 과정에서도 입력을 다 받고 연산을 처리해야 하는데 코드를 잘못 입력해서 한줄 받고 처리하고 그런 식으로 가서 결과가 이상하게 나왔었음
그리고 graph를 형성할 때 HashMap으로 get을 해서 나온 value로 graph를 형성해야하는데 실수함
 */
public class Main {
    public static class Edge{
        int start;
        int destination;
        public Edge(int destination){
            this.destination = destination;
        }
        public Edge(int start , int destination){
            this.start = start;
            this.destination = destination;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        /*

         */
        Loop1:
        for(int i = 1; i > 0; i++){
            List<Edge> edgeList = new ArrayList<>();
            List<ArrayList<Edge>> graph = new ArrayList<>();
            HashMap<Integer, Integer> vertexMap = new HashMap<>();
            HashSet<Integer> vertexGroup = new HashSet<>();
            int vertexNumber = 1;
            Loop2:
            while(true){
                st = new StringTokenizer(input.readLine());
                if(st.countTokens() == 0){
                    continue;
                }
                while(st.hasMoreTokens()){
                    int start = Integer.parseInt(st.nextToken());
                    int destination = Integer.parseInt(st.nextToken());
                    if(start == -1 && destination == -1){
                        break Loop1;
                    }
                    if(start == 0 && destination == 0){
                        break Loop2;
                    }
                    if(!vertexGroup.contains(start)) { // 처음나오는 숫자이면 map , set에다가 집어넣는다.
                        vertexMap.put(start, vertexNumber++);
                        vertexGroup.add(start);
                    }
                    if(!vertexGroup.contains(destination)){ // 없으면 map , set에다가 집어넣는다.
                        vertexMap.put(destination , vertexNumber++);
                        vertexGroup.add(destination);
                    }
                    edgeList.add(new Edge(start , destination));
                }

                /*
                전처리는 완료했으니까 rootNode를 골라야함
                여기서 부터 edgeList를 하나씩 돌으면서 HashSet에서 깐다.
                그러면서 트리는 게속 만든다.
                 */
            }
            if(vertexNumber == 1){
                System.out.println("Case " + i + " is a tree.");
                continue;
            }
            int size = vertexGroup.size();
            int[] visited = new int[size + 1];
            for(int j = 0; j <= size; j++){
                graph.add(new ArrayList<>());
            }
            int rootVertex = 0;
            for(int j = 0; j < edgeList.size(); j++){
                int start = edgeList.get(j).start;
                int destination = edgeList.get(j).destination;
                graph.get(vertexMap.get(start)).add(new Edge(vertexMap.get(destination))); // graph 형성
                if(vertexGroup.contains(destination)) vertexGroup.remove(destination);
            }
            if(vertexGroup.size() != 1){
                System.out.println("Case " + i + " is not a tree.");
                continue;
            }else{
                Iterator<Integer> it = vertexGroup.iterator();
                rootVertex = vertexMap.get(it.next());
            }
            Queue<Integer> queue = new LinkedList<>();
            queue.add(rootVertex);
            visited[rootVertex] = 1;
            boolean tree = true;
            Loop3:
            while(!queue.isEmpty()){
                Integer number = queue.poll();
                for(int j = 0; j < graph.get(number).size(); j++){
                    Edge edge = graph.get(number).get(j);
                    if(visited[edge.destination] == 1){
                        System.out.println("Case " + i + " is not a tree.");
                        tree = false;
                        break Loop3;
                    }
                    queue.add(edge.destination);
                    visited[edge.destination] = 1;
                }
            }
            if(tree) System.out.println("Case " + i + " is a tree.");
        }
    }
}
