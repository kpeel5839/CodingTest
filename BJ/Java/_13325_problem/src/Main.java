import java.util.*;
import java.io.*;

// 13325 : 이진 트리

/**
 * 예제
 * 2
 * 2 2 2 1 1 3
 */
public class Main {
    static int N;
    static int size;
    static int max = 0;
    static int ans = 0;
    static int[] edge;
    static int[] sum;

    static void dfs(int e, int v) {
        if (size < e) {
            return;
        }

        int diff = v - sum[e];
        dfs(e * 2 + 1, v - edge[e] - diff);
        dfs(e * 2 + 2, v - edge[e] - diff);
        ans += edge[e] + diff;
    }

    static int getSum(int e) {
        if (size < e) {
            return 0;
        }

        sum[e] = Math.max(getSum(e * 2 + 1), getSum(e * 2 + 2));
        sum[e] += edge[e];

        return sum[e];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_13325_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        size = (int) (Math.pow(2, N + 1) - 2);
        edge = new int[size + 1];
        sum = new int[size + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= size; i++) {
            edge[i] = Integer.parseInt(st.nextToken());
        }

        max = Math.max(getSum(1), getSum(2));
        dfs(1, max);
        dfs(2, max);
        System.out.println(ans);
    }
}
