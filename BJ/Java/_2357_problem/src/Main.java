import java.util.*;
import java.io.*;

// 2357 : 최솟값과 최댓값

/**
 * Exmaple
 * 10 4
 * 75
 * 30
 * 100
 * 38
 * 50
 * 51
 * 52
 * 20
 * 81
 * 5
 * 1 10
 * 3 5
 * 6 9
 * 8 10
 */
public class Main {
    public static int[] maxTree;
    public static int[] minTree;

    public static int minSearch(int cur, int left, int right, int findLeft, int findRight) {
        if (findRight < left || right < findLeft) {
            return Integer.MAX_VALUE;
        }

        if (findLeft <= left && right <= findRight) {
            return minTree[cur];
        }

        return Math.min(minSearch(cur * 2, left, (left + right) / 2, findLeft, findRight),
                minSearch(cur * 2 + 1, (left + right) / 2 + 1, right, findLeft, findRight));
    }

    public static int maxSearch(int cur, int left, int right, int findLeft, int findRight) {
        if (findRight < left || right < findLeft) {
            return Integer.MIN_VALUE;
        }

        if (findLeft <= left && right <= findRight) {
            return maxTree[cur];
        }

        return Math.max(maxSearch(cur * 2, left, (left + right) / 2, findLeft, findRight),
                maxSearch(cur * 2 + 1, (left + right) / 2 + 1, right, findLeft, findRight));
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2357_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int leafNodeSize = (int) Math.pow(2, Math.ceil(Math.log(N) / Math.log(2)));
        maxTree = new int[leafNodeSize * 2];
        minTree = new int[leafNodeSize * 2];
        Arrays.fill(maxTree, Integer.MIN_VALUE);
        Arrays.fill(minTree, Integer.MAX_VALUE);

        for (int i = leafNodeSize; i < N + leafNodeSize; i++) {
            maxTree[i] = Integer.parseInt(br.readLine());
            minTree[i] = maxTree[i];
        }

        for (int i = (int) (Math.log(leafNodeSize) / Math.log(2)) - 1; i != -1; i--) {
            for (int j = (int) Math.pow(2, i); j < (int) Math.pow(2, i + 1); j++) {
                maxTree[j] = Math.max(maxTree[j * 2], maxTree[j * 2 + 1]);
                minTree[j] = Math.min(minTree[j * 2], minTree[j * 2 + 1]);
            }
        }

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());

            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());
            bw.write(minSearch(1, 1, leafNodeSize, left, right) + " " + maxSearch(1, 1, leafNodeSize, left, right) + "\n");
        }

        bw.flush();
        bw.close();
    }
}
