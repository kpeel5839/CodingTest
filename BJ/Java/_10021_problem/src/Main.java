import java.util.*;
import java.io.*;
import java.util.function.Function;

// 10021 : Watering the Fields

/**
 * -- 전제조건
 * 파이프를 연결해야할 지점들이 주어지고
 * 파이프를 모두 연결하고, 아얘 연결 그래프로 만드는 것이 목적이다.
 *
 * 근데, N = 포인트 수 C = 파이프 최소 설치 비용
 * 이 주어지고 최소 C 는 넘어야지 포인트 사이에 파이프를 설치할 수 있다.
 *
 * 그리고, 파이프 설치 비용은 (xi - xj) ^ 2 + (yi - yj) ^ 2 해당 식을 따라서 설치해야 한다.
 *
 * 이럴 때, 최소의 비용으로 파이프를 설치할 경우에 드는 비용을 출력하라
 * 만약 2 지점이 있다고 했을 때, 두 지점간의 거리가 너무 가까워서 파이프를 설치 못하는 경우 등등
 * 다양한 경우, 사유로 인해 연결할 수 없는 경우에는 -1 을 출력하면 된다.
 *
 * -- 틀 설계
 * 그냥 최소 스패닝 트리 사용하면 된다.
 * find, union 정의하고 유클리드 제곱을 반환하는 메소드를 만든다.
 *
 * 그리고서 그냥 포인트 간의 거리 다 담아서 Sort 를 진행한 뒤에 스패닝 트리 알고리즘 적용하면 빠르게 해결할 수 있을 것 같다.
 * 문제가 영어라 설명이 내가 못 알아먹은 건지 몰라도 최대 간선의 개수가 200 만개가 나오게 되는데 이런 경우 시간초과가 날 수 밖에 없을 것 같다.
 *
 * 그래서 파이프의 비용을 C 로 제한하는 거 아닐까 ? (최소 C)
 *
 * 그리고 짜피 엣지를 저장하는 것이다.
 * 즉 방향성이 전혀 없다.
 *
 * 그렇기 때문에 N ^ 2 이 아니라 (N ^ 2) / 2 의 방법을 사용할 것이다.
 */
public class Main {
    static int N;
    static int C;
    static int ans = 0;
    static int[] parent;
    static Point[] points;
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

    static class Point {
        int y;
        int x;

        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static int find(int a) {
        if (a == parent[a]) { // 대표 정점을 찾은 경우
            return a;
        } else {
            parent[a] = find(parent[a]);
            return parent[a];
        }
    }

    static void union(int a, int b) {
        parent[b] = a; // b 의 부모를 a 로 지정하면서 집합을 합쳐준다.
    }

    static long getCost(Point p1, Point p2) {
        // (xi - xj) ^ 2 + (yi - yj) ^ 2 반환
        return (long) (Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        C = fun.apply(input[1]);
        parent = new int[N + 1];
        points = new Point[N + 1];

        for (int i = 0; i < N; i++) {
            input = br.readLine().split(" ");
            parent[i + 1] = i + 1; // parent 초기화
            points[i + 1] = new Point(fun.apply(input[1]), fun.apply(input[0])); // 기존이 x, y 값 순으로 들어오기 때문에 반대로 삽입해준다.
        }

        for (int i = 1; i < N; i++) {
            for (int j = i + 1; j < N + 1; j++) {
                long cost = getCost(points[i], points[j]);
                if (C <= cost) { // 값이 C 이상인 경우만 진행
                    edges.add(new Edge(i, j, cost));
                }
            }
        }

        Collections.sort(edges, (e1, e2) -> Long.compare(e1.cost, e2.cost));

        for (Edge edge : edges) {
            int a = find(edge.a);
            int b = find(edge.b);

            if (a != b) {
                union(a, b);
                ans += edge.cost;
            }
        }

        int rootParent = find(1); // N 이 0 인 경우가 문제로 주어지지 않는다면 전혀 문제가 되지 않음

        for (int i = 2; i < N + 1; i++) {
            if (find(i) != rootParent) {
                ans = -1;
                break;
            }
        }

        System.out.println(ans);
    }
}
