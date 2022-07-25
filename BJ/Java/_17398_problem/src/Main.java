import java.util.*;
import java.io.*;
import java.util.function.Function;

// 17398 : 통신망 분할

/**
 * -- 전제 조건
 * 분리집합을 제거해가면서 비용을 구해가는 문제이다.
 *
 * -- 틀 설계
 * 설계로 놀라운 사실을 알아냈다.
 * 주어진 제거하는 간선을 제외한 채로
 * 분리집합을 실행해놓고,
 * 입력받은 것을 Stack 에다가 저장해놨다가 실행하면 되는 문제이다.
 *
 * 이게 가능한 이유는 얘가 제거하는 순서를, 그 flow 를 역으로 생각해봤는데
 * 생각해보니까 제거될 때, 이미 제거된 간선들은 다 제거가 되어 있는 상태에서 제거가 되니까, 아얘 실행 안해놓고 역으로 하면 그 상황이랑 똑같은 상황이잖아?
 * 하는 상황에서 나온 발상이다.
 *
 * 그리고 이미 연결되어 있는 집합들은 연결할 필요가 없다.
 * 왜냐? 이미 같은 집합인데 연산을 할 이유가 전혀 없다.
 */
public class Main {
    static int[] parent;
    static long[] size;
    static long ans = 0;

    static int find(int a) {
        if (parent[a] == a) {
            return a;
        } else {
            parent[a] = find(parent[a]);
            return parent[a];
        }
    }

    static void union(int a, int b) {
        parent[b] = a;
        size[a] += size[b]; // size 를 더해준다.
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_17398_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        Stack<Integer> stack = new Stack<>();
        String[] input = br.readLine().split(" ");

        int N = fun.apply(input[0]);
        int M = fun.apply(input[1]);
        int Q = fun.apply(input[2]);
        parent = new int[N + 1];
        size = new long[N + 1];

        for (int i = 1; i <= N; i++) {
            parent[i] = i;
            size[i] = 1; // 본인을 포함하고 있으니까 size == 1 이다.
        }

        int[][] edge = new int[M + 1][2];
        boolean[] visited = new boolean[M + 1];

        for (int i = 1; i <= M; i++) {
            input = br.readLine().split(" ");

            edge[i][0] = fun.apply(input[0]);
            edge[i][1] = fun.apply(input[1]);
        }

        for (int i = 0; i < Q; i++) {
            int a = fun.apply(br.readLine());
            visited[a] = true;
            stack.add(a);
        }

        for (int i = 1; i <= M; i++) {
            if (!visited[i]) { // 실행할 수 있는 애들만
                int a = find(edge[i][0]);
                int b = find(edge[i][1]);

                if (a != b) { // 서로 같지 않은 상황, 즉 집합이 같지 않은 상황에서만 진행한다.
                    union(a, b);
                }
            }
        }

        while (!stack.isEmpty()) {
            int index = stack.pop();

            int a = find(edge[index][0]);
            int b = find(edge[index][1]);

//            System.out.println(Arrays.toString(parent));
//            System.out.println("a : " + a + " b : " + b);
//            System.out.println("a size : " + size[a]);
//            System.out.println("b size : " + size[b]);
            // a != b 인 경우 둘의 size 를 곱해주고 더해준다음, union 연산을 진행해준다.
            if (a != b) {
                ans += size[a] * size[b];
                union(a, b);
            }
        }

        System.out.println(ans);
    }
}