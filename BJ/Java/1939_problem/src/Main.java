import java.util.*;
import java.io.*;
import java.util.function.Function;

// 1939 : 중량 제한

/**
 * -- 전제조건
 * N 개의 섬으로 이루어진 나라가 있고,
 * 이들 중 몇개의 섬 사이에는 다리가 설치되어 있다.
 * 그럴 때, 섬의 번호와 다리가 버틸 수 있는 중량이 주어지고
 * 마지막 줄에 공장이 설치되어 있는 두 섬이 주어질 때,
 * 한번에 옮길 수 있는 최대 중량을 구하여라.
 * -- 틀 설계
 * 이미 카테고리를 봤기 때문에, 이분탐색 및 분리집합이라는 것을 알지만
 * 이게 왜 분리집합일까?
 * 그냥 bfs 로 풀어도 되는 부분 아닌가?
 *
 * 음 헷갈린다.
 *
 * 예제로는
 * 1 -> 2 -> 3, 을 2로 갈 수 있는 것과
 * 1 -> 3, 을 3으로 갈 수 있는 것 중 큰 것을 골랐다.
 * 이말은 즉슨 무엇이냐? 경로가 하나가 아니다라는 의미다.
 *
 * 즉 a <-> b 가 주어졌을 때,
 * 서로에게 통하는 모든 경로를 알아내어야 한다.
 * 만약에 bfs 로 하게 된다라면
 *
 * 이거는 어떨까?
 * 모든 엣지들을 정리해놓은 뒤
 * 내림차순으로 정렬을 진행하는 것이다.
 * 그 다음에 최대 신장 트리를 구성하는 것이다.
 *
 * 그러면서 계속 해서, a, b 의 집합이 같아지는지 확인해보자.
 *
 * 이게 될 수도 있는 이유는
 * 분리집합을 통해서, 계속 확인하면서, 서로의 집합이 같아지는 경우?
 * 이 경우는 무조건 서로에게 경로가 생겼다라는 것이다.
 * 그렇다라는 것은 경로가 처음으로 생긴 것이고
 * 현재 내림차순으로 edge 를 선택했으니
 *
 * 그 cost 를 출력하면 답이 될 수밖에 없다라는 것이 내 생각이다.
 *
 * -- 해맸던 점
 * 생각보다 굉장히 깔끔하게 답을 맞았음
 * 해맸던 점으로는 일단, cost 를 long 으로 하지 않았음
 * 최대 다리가 버틸 수 있는 하중이 10억이고, 섬이 10000 개가 있기 때문에 모든 섬을 다 거치는 경우
 * 9999 * 10억이다. 그렇기 때문에 int 로 하게 되면 무조건 범위를 넘어간다.
 *
 * 그리고, 또 실수했던 부분은 find(island1) == find(island2) 해야 하는데,
 * find(island1) == island2 해버렸음
 * 도대체 이상하게 항상 예제들은 이런게 맞을까
 *
 * 그래서 깔끔하게 빠르게 풀었음
 */
public class Main {
    static int V;
    static int E;
    static int[] parent;
    static List<Edge> edges = new ArrayList<>();

    static class Edge {
        int a;
        int b;
        long cost;

        Edge(int a, int b, long cost) {
            this.a = a;
            this.b = b;
            this.cost = cost;
        }
    }

    static int find(int a) {
        if (parent[a] == a) {
            return a;
        } else {
            parent[a] = find(parent[a]);
            return parent[a];
        }
    }

    static void union(int a, int b) {
        parent[b] = a; // a 를 부모로
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        V = fun.apply(input[0]);
        E = fun.apply(input[1]);
        parent = new int[V + 1];

        for (int i = 0; i <= V; i++) {
            parent[i] = i;
        }

        for (int i = 0; i < E; i++) {
            input = br.readLine().split(" ");
            int a = fun.apply(input[0]);
            int b = fun.apply(input[1]);
            long cost = fun.apply(input[2]);

            edges.add(new Edge(a, b, cost));
        }

        input = br.readLine().split(" ");
        int island1 = fun.apply(input[0]);
        int island2 = fun.apply(input[1]);
        Collections.sort(edges, (o1, o2) -> -Long.compare(o1.cost, o2.cost)); // 내림차순 정렬


        for (Edge edge : edges) {
            int a = find(edge.a);
            int b = find(edge.b);

            System.out.println("sum1 : " + edge.a + " sum2 : " + edge.b + " cost : " + edge.cost);

            if (a != b) {
                union(a, b);
            }

            if (find(island1) == find(island2)) {
                System.out.println(edge.cost);
                break;
            }
        }
    }
}
