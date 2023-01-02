import java.util.*;
import java.io.*;

// 1517 : 버블소트

/**
 * Example
 * 3
 * 3 2 1
 */
public class Main {
    static int N;
    static int numberOfLeafNode;
    static int[] tree;
    static PriorityQueue<int[]> max = new PriorityQueue<>((o1, o2) -> { // {index, value}
        if (o1[1] == o2[1]) {
            return o2[0] - o1[0];
        }

        return o2[1] - o1[1];
    });

    static int search(int cur, int left, int right, int findLeft, int findRight) {
        if (findRight < left || right < findLeft) {
            return 0;
        }

        if (findLeft <= left && right <= findRight) {
            return tree[cur];
        }

        return search(cur * 2, left, (left + right) / 2, findLeft, findRight)
                + search(cur * 2 + 1, (left + right) / 2 + 1, right, findLeft, findRight);
    }

    static void update(int cur, int left, int right, int targetIndex) {
        if (!(left <= targetIndex && targetIndex <= right)) {
            return;
        }

        tree[cur]--;

        if (left != right) {
            update(cur * 2, left, (left + right) / 2, targetIndex);
            update(cur * 2 + 1, (left + right) / 2 + 1, right, targetIndex);
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1517_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        numberOfLeafNode = (int) Math.pow(2, Math.ceil(Math.log(N) / Math.log(2)));
        tree = new int[numberOfLeafNode * 2];

        st = new StringTokenizer(br.readLine());
        for (int i = numberOfLeafNode; i < N + numberOfLeafNode; i++) {
            int number = Integer.parseInt(st.nextToken());
            max.add(new int[] {i - numberOfLeafNode + 1, number});
            tree[i] = 1; // 있다는 의미
        }

        for (int i = (int) (Math.log(numberOfLeafNode) / Math.log(2)) - 1; i != -1; i--) {
            int startNodeNumber = (int) Math.pow(2, i);

            for (int j = startNodeNumber; j < Math.pow(2, i + 1); j++) {
                tree[j] = tree[j * 2] + tree[j * 2 + 1];
            }
        }

        long answer = 0;
        while (!max.isEmpty()) {
            int[] now = max.poll();

            answer += search(1, 1, numberOfLeafNode, now[0] + 1, N);
            update(1, 1, numberOfLeafNode, now[0]);
        }

        System.out.println(answer);
    }
}
