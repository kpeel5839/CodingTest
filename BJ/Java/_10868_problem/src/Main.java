import java.util.*;
import java.io.*;

// 10868 : 최솟값

/**
 * Example
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
    public static int[] tree;

    public static int search(int current, int left, int right, int findLeft, int findRight) {
        if (right < findLeft || findRight < left) {
            return Integer.MAX_VALUE;
        }

        if (findLeft <= left && right <= findRight) {
            return tree[current];
        }

        return Math.min(search(current * 2, left, (left + right) / 2, findLeft, findRight), search(current * 2 + 1, (left + right) / 2 + 1, right, findLeft, findRight));
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_10868_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());
        int height = (int) Math.ceil(Math.log(N) / Math.log(2));
        tree = new int[(int) Math.pow(2, height + 1)];
        Arrays.fill(tree, Integer.MAX_VALUE);


        for (int i = 0; i < N; i++) {
            tree[(int) Math.pow(2, height) + i] = Integer.parseInt(br.readLine());
        }

        for (int i = height - 1; i != -1; i--) {
            for (int j = (int) Math.pow(2, i); j < (int) Math.pow(2, i + 1); j++) {
                tree[j] = Math.min(tree[j * 2], tree[j * 2 + 1]);
            }
        }

        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            int findLeft = Integer.parseInt(st.nextToken());
            int findRight = Integer.parseInt(st.nextToken());
            bw.write(search(1, 1, (int) Math.pow(2, height), findLeft, findRight) + "\n");
        }

        bw.flush();
        bw.close();
    }
}
