import java.util.*;
import java.io.*;

// 최소 신장 트리 (SDS 하계 프로 시험)

/**
 * 예제
 *
 * 3
 * 3 3 1
 * 1 2 3
 * 2 3 5
 * 1 3 9
 * 3
 * 5 4 6
 * 1 2 2
 * 1 3 3
 * 1 4 4
 * 1 5 5
 * 1
 * 2
 * 3
 * 4
 * 5
 * 6
 * 5 4 6
 * 1 2 2
 * 1 3 3
 * 1 4 4
 * 1 5 5
 * 6
 * 5
 * 4
 * 3
 * 2
 * 1
 *
 * 답은 순서대로
 * # 1
 * # 20
 * # 50
 */
public class MinimumSpanningTree {
    static int N;
    static int M;
    static int Q;
    static int nMax = 100000;
    static int mMax = 100000;
    static int[] parent = new int[nMax + 1];
    static int[] arr = new int[mMax + 1];

    static int find(int a) {
        if (parent[a] == a) {
            return a;
        } else {
            return parent[a] = find(parent[a]);
        }
    }

    static void union(int a, int b) {
        parent[b] = a;
    }

    static class Edge {
        int a;
        int b;
        int cost;

        Edge(int a, int b, int cost) {
            this.a = a;
            this.b = b;
            this.cost = cost;
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_22_sds_summer_pro_exam/src/input2.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        int index = 1;

        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            Q = Integer.parseInt(st.nextToken());
            arr[M] = 0;

            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 0, 0));

            for (int i = 0; i <= N; i++) {
                parent[i] = i;
            }

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());

                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());

                arr[i] = 0;
                edges.add(new Edge(a, b, c));
            }

            Collections.sort(edges, (o1, o2) -> o1.cost - o2.cost);
            arr[0] = N;
            int cnt = N;
            long ans = 0;

            for (int i = 1; i < edges.size(); i++) {
                Edge edge = edges.get(i);

                int a = find(edge.a);
                int b = find(edge.b);

                if (a != b) {
                    cnt--;
                    union(a, b);
                    arr[i] = cnt;
                } else {
                    arr[i] = cnt;
                }
            }

            for (int i = 1; i <= Q; i++) {
                int k = Integer.parseInt(br.readLine());

                int res = 0;
                int left = 0;
                int right = edges.size() - 1;

                while (left <= right) {
                    int mid = (left + right) / 2;

                    if (edges.get(mid).cost <= k) {
                        res = mid;
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }

                ans += ((long) i) * ((long) (arr[res] - 1));
            }

            bw.write("#" + index + " " + ans + "\n");
            index++;
        }

        bw.flush();
        bw.close();
    }
}
