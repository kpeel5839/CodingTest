import java.util.*;
import java.io.*;

// 1197 : 최소 스패닝 트리
/*
-- 전제조건
1. 그래프가 주어졌을 때 최소신장 트리르 구하는 프로그램을 작성하시오
2. 최소 스패닝 트리 - 주어진 그래프의 모든 정점들을 연결하는 부분 그래프 중에서 가중치의 합이 최소인 트리를 찾는다.(무조건 어디 정점에서 시작하더라도 모든 정점을 방문 가능해야함)
3. 1번부터 v번 까지 정점 번호가 주어지고
4. 간선들도 주어진다.
-- 틀 설계
1. 입력을 받는다. (v = 정점의 개수 , m = 간선의 개수)
2. parent 배열로 해당 인덱스는 정점 , 해당 인덱스에 해당하는 배열의 값은 해당 정점의 부모라고 한다.
3. 자신의 부모값을 찾는 즉 , 자신의 속한 집합을 찾는 find()를 선언한다.
4. 그리고 서로가 다른 부모의 값을 가지고 있으면 즉, 서로 다른 집합이면 union을 실행하는데 union은 서로 다른 집합을 하나의 집합으로 만들어준다. (두 집합의 부모를 하나에 몬다 , 원래는 사이즈를 보고 해야하는데 그냥 한다.)
5. find() 함수에서는 그리고 모든 정점들이 부모를 바로 가르킬 수 있도록 find 하면서 연산을 실행한다.
6. Edge class 를 선언하는데 Comparable 을 implements 하고 Edge List를 다 받은다음에 Collections.sort를 진행한다.
7. 그러고서 최소치의 간선부터 순서대로 다른 집합이라면 해당 간선을 채택한다 , 만약 같은 집합이라면 사이클이 생기기 때문에 채택하지 않는다.
8. score += weight 를 계속 해서 마지막에 score 를 출력한다.
 */
public class Main {
    public static int v , m , score;
    public static int[] parent;
    public static List<Edge> edgeList = new ArrayList<>();
    public static class Edge implements Comparable<Edge>{
        int vertex1;
        int vertex2;
        int weight;

        public Edge(int vertex1 , int vertex2 , int weight){
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other){
            if(this.weight >= other.weight){
                return 1;
            }else{
                return -1;
            }
        }
    }
    public static int find(int vertexNumber){
        /*
        1. 인자로 넘어온 값이 parent[vertexNumber] 와 같으면 인자로 넘어온 값이 즉 부모이기에 바로 반환한다.
        2. 그렇지 않으면 본인의 부모 값으로 재귀 적으로 실행한다.
        3. 그러고서 이제 다시 return이 순차적으로 될 때 최종적으로 부모의 인덱스가 계속 반환되게 되는데 그러면서 find 하면서 방문한 노드들의 부모를 다 최종 루트로 바꾼다.
        4. 이게 시간을 엄청 줄임
         */
        if(vertexNumber == parent[vertexNumber]){
            return vertexNumber;
        }else{
            return parent[vertexNumber] = find(parent[vertexNumber]);
        }
    }
    public static void union(int vertex1 , int vertex2){
        /*
        1. 두개의 집합의 부모를 찾은다음.
        2. vertex2의 부모인 parent2의 부모 값을 vertex1 의 부모인 parent1으로 바꾸면서
        3. 두개의 집합을 합친다.
         */
        int parent1 = find(vertex1);
        int parent2 = find(vertex2);
        parent[parent2] = parent1;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        v = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        parent = new int[v + 1];

        for(int i = 1; i <= v; i++){
            parent[i] = i;
        }
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(input.readLine());
            edgeList.add(new Edge(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()) , Integer.parseInt(st.nextToken())));
        }

        Collections.sort(edgeList);

        for(Edge edge : edgeList){
            int parent1 = find(edge.vertex1);
            int parent2 = find(edge.vertex2);
            if(parent1 != parent2){
                union(parent1 , parent2);
                score += edge.weight;
            }
        }

        System.out.println(score);
    }
}
