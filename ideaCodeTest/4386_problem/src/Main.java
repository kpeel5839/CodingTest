import java.util.*;
import java.io.*;

// 4386 : 별자리 만들기

/*
-- 전제조건
도현이는 우주의 God 이다.
아무렇게나 널브려져 있는 n 개의 별들을 이어서 별자리를 하나 만들 예정이다.
별자리를 이루는 선은 서로 다른 두 별을 일직선으로 이은 형태이고
모든 별들은 별자리 위의 선을 통해 직/간접적으로 이어져 있어야 한다.(즉 연결 그래프여야 한다.)

그럴 때 , 최소 비용을 구하여라
x , y 좌표가 실수 형태로 주어지고 , 소수점 둘째 자리가 주어진다.
-- 틀 설계
그냥 Point 클래스 만들어서 각각 Point 배열에다가 순차적으로 담고
N 크기 만큼의 Parent 도 만든다 ,Point , Parent 배열 다 N + 1 로 진행하여야 한다.
그리고 find , union 도 정의한다 , find 는 항상 부모 집합을 찾아주는 역할을 하여야 하고
그리고 , union 은 서로 집합을 합쳐주는 역할을 하여야 한다.

그래서 일단 각각 좌표를 가지고 , 피타고라스를 이용하여서 , 한 지점에서 , 모든 좌표로 통하는 edge 들을 생성해준다.
그 다음에 , edge 들을 정렬을 진행해주고 , 가장 첫번째 edge 부터 진행하면서 ,
그리디하게 선택한다. (그 대신 , 그리디하게 선택을 하지만 , 최소신장트리는 사이클이 형성되면 안된다 , 왜냐하면 최소 개수를 선택하려면 n - 1 개를 선택하여야 하고 사이클이 발생하게 되면 최소 값을 구할 수 없다.)
그래서 최소 신장트리는 사이클이 형성되면 안된다.

그래서 서순은 일단 좌표를 받고
parent를 초기화한다.
그 다음에 모든 좌표들이 다른 모든 좌표로 가게 되는 edge 들을 생성시켜주고
edge.cost 들로 정렬을 진행해준다음에
그리디하게 선택을 하면서 find , union 을 통해서 서로 같은 집합이 아니라면 union
서로 같은 집합이라면 continue 를 해주면서 결과값을 도출하면 된다.

쉬운 문제라서 , 빠르게 맞았음
 */
public class Main {
    public static double res = 0;
    public static int[] parent;
    public static Point[] node;
    public static int N;
    public static List<Edge> edge = new ArrayList<>();

    public static int find(int a){
        if(parent[a] == a) return a;
        return parent[a] = find(parent[a]);
    }

    public static void union(int a, int b){
        parent[b] = a; // b 의 부모를 a로 변경한다.
    }

    public static class Point{
        double y;
        double x;
        public Point(double y , double x){
            this.y = y;
            this.x = x;
        }
        // Distance 를 알 수 있는 메소드
        public double getDistance(Point other){
            return Math.sqrt(Math.pow(Math.abs(this.y - other.y) , 2) + Math.pow(Math.abs(this.x - other.x) , 2));
        }
    }

    public static class Edge{
        int v1;
        int v2;
        double cost;
        public Edge(int v1 , int v2 , double cost){
            this.v1 = v1;
            this.v2 = v2;
            this.cost = cost;
        }
        @Override
        public String toString(){
            return "v1 : " + v1 + " v2 : " + v2 + " cost : " + cost;
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());

        node = new Point[N + 1];
        parent = new int[N + 1];

        for(int i = 1; i <= N; i++){
            parent[i] = i;
        }

        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(input.readLine());

            // node 받아오기
            node[i] = new Point(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));
        }

        for(int i = 1; i <= N; i++){
            for(int j = 1; j <= N; j++){
                if(i == j) continue;
                double distance = node[i].getDistance(node[j]);

                edge.add(new Edge(i , j , distance));
            }
        }

        Collections.sort(edge , (o1 , o2) -> Double.compare(o1.cost , o2.cost));

//        System.out.println(edge);

        for(Edge innerEdge : edge){
            int a = find(innerEdge.v1);
            int b = find(innerEdge.v2);

            if(a == b) continue;

            res += innerEdge.cost;
            union(a , b);
        }

        System.out.println(res);
    }
}
