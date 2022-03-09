import java.util.*;
import java.io.*;

// 1647 : 도시 분할 계획

/*
-- 전제 조건
도시를 두개의 마을로 분할하는데 , 각 안의 마을들의 경로가 항상 존재하고 ,
모든 엣지들의 유지비를 최소로 하고 싶다.

간단하다 , 최소신장트리 알고리즘을 사용하면서 , 간선의 개수는 항상 n - 1 개로 유지해야한다.
근데 , 마을을 2개로 분할하고 , 각 마을들의 경로를 다 이어지게 만들라는 것은 간선을 최소신장트리에 맞게 , n - 2 개를 골랐을 때의 거리의 합을 구하면 된다.
-- 틀 설계
union , find 로 N + 1 크기만한 parent 를 만들고 , 다 부모를 본인으로 한다.
그 다음에 union 은 각 집합을 합쳐주고 , find 는 해당 엣지에 이어진 정점 두개의 부모를 찾아주는 역할을 한다.

그리고 n - 2 개를 선택할 때 까지 가면서 , 선택한 cost 들의 합을 출력하면 될 것 같다.

-- 해맸던 점
이상하다 , 항상 comparable 객체로 정렬을 진행했었는데 , 자꾸 illegalArgument exception 이 나서
그냥 람다식으로 정렬을 해주었더니 해결이 되었다.
 */
public class Main {
    public static int[] parent;
    public static int V , E , ans = 0;
    public static List<Edge> edgeList = new ArrayList<>();
    public static int find(int number){
        /*
        parent 를 찾아주면서 ,
        parent 를 찾으면 모두 parent를 최상위 부모로 바꾼다.
         */
        if(number == parent[number]){
            return number;
        }
        return parent[number] = find(parent[number]); // 일단 부모를 찾으러 현재 자신의 부모로 점프하고 , find로 인해서 , 최종적으로 반환되는 부모값을 본인의 parent 값으로 바꾼다.
    }
    public static void union(int a, int b){
        /*
        a , b 의 부모를 얻고,
        parent[b] = a 를 넣어서
        두개의 집합을 합친다.
         */

        a = find(a);
        b = find(b);

        parent[b] = a; // a의 부모를 b의 부모의 부모로 지정한다. 그렇게 되면서 두개의 집합이 같아지게 된다.
    }
    public static class Edge{
        int v1;
        int v2;
        int cost;
        public Edge(int v1 , int v2 , int cost){
            this.v1 = v1;
            this.v2 = v2;
            this.cost = cost;
        }
//
//        @Override
//        public int compareTo(Edge o){
//            if(this.cost >= o.cost){
//                return 1;
//            }
//            else {
//                return -1;
//            }
//        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        parent = new int[V + 1];

        for(int i = 0; i <= V; i++){
            parent[i] = i;
        }

        for(int i = 0; i < E; i++){
            st = new StringTokenizer(input.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            edgeList.add(new Edge(v1 , v2 , cost)); // 무방향 그래프
        }

        Collections.sort(edgeList, (o1 , o2) -> Integer.compare(o1.cost , o2.cost)); // 정렬 정상적으로 완료

//        for(Edge edge : edgeList){
//            System.out.println(edge.cost);
//        }

        int count = 0;
        for(Edge edge : edgeList){
            if(count == V - 2) break;

            int a = find(edge.v1);
            int b = find(edge.v2); // 두개의 부모가 같으면 진행하면 안됨

            if(a == b) continue;

            // 여기까지 내려온 건 아니라는 것을 의미함
            union(a , b);
            ans += edge.cost;
            count++;
        }

        System.out.println(ans);
    }
}