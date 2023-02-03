import java.util.*;
import java.io.*;

// 5676 : 음주코딩

/**
 * Example
 * 4 6
 * -2 6 0 -1
 * C 1 10
 * P 1 4
 * C 3 7
 * P 2 2
 * C 4 -5
 * P 1 4
 * 5 9
 * 1 5 -2 4 3
 * P 1 2
 * P 1 5
 * C 4 -5
 * P 1 5
 * P 4 5
 * C 3 0
 * P 1 5
 * C 4 -5
 * C 4 -5
 */
public class Main {
    public static int height;
    public static int[] tree;

    public static char intToChar(int result) {
        if (result < 0) {
            return '-';
        }

        if (result > 0) {
            return '+';
        }

        return '0';
    }

    public static int convert(int value) {
        if (value < 0) {
            return -1;
        }

        if (value > 0) {
            return 1;
        }

        return value;
    }

    public static int search(int cur, int left, int right, int searchLeft, int searchRight) {
        if (searchRight < left || right < searchLeft) {
            return 1; // 결과에 아무 영향을 끼치지 않는 값
        }

        if (searchLeft <= left && right <= searchRight) {
            return tree[cur];
        }

        return search(cur * 2, left, (left + right) / 2, searchLeft, searchRight)
                * search(cur * 2 + 1, (left + right) / 2 + 1, right, searchLeft, searchRight);
    }

    public static void update(int cur, int target, int left, int right) {
        if (!(left <= target && target <= right)) {
            return;
        }

        if (left != right) {
            update(cur * 2, target, left, (left + right) / 2);
            update(cur * 2 + 1, target, (left + right) / 2 + 1, right);
            tree[cur] = tree[cur * 2] * tree[cur * 2 + 1];
        }
    }

    public static void init() {
        for (int i = height - 1; i != -1; i--) {
            for (int j = (int) Math.pow(2, i); j < (int) Math.pow(2, i + 1); j++) {
                tree[j] = tree[j * 2] * tree[j * 2 + 1];
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5676_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        while (true) {
            String line = br.readLine();

            if (line == null) {
                break;
            }

            st = new StringTokenizer(line);
            int N = Integer.parseInt(st.nextToken());
            int QUERY = Integer.parseInt(st.nextToken());
            height = (int) Math.ceil(Math.log(N) / Math.log(2));
            tree = new int[(int) Math.pow(2, height) * 2];
            st = new StringTokenizer(br.readLine());
            Arrays.fill(tree, 1);

            for (int i = 0; i < N; i++) {
                tree[(int) Math.pow(2, height) + i] = convert(Integer.parseInt(st.nextToken()));
            }

            init();

            for (int i = 0; i < QUERY; i++) {
                st = new StringTokenizer(br.readLine());
                char operationType = st.nextToken().charAt(0);

                if (operationType == 'C') { // 변경
                    int index = Integer.parseInt(st.nextToken());
                    int value = convert(Integer.parseInt(st.nextToken()));
                    tree[(int) Math.pow(2, height) + index - 1] = value;

                    update(1, index, 1, (int) Math.pow(2, height));
                }

                if (operationType == 'P') { // 곱셈
                    int left = Integer.parseInt(st.nextToken());
                    int right = Integer.parseInt(st.nextToken());
                    bw.write(intToChar(search(1, 1, (int) Math.pow(2, height), left, right)));
                }
            }

            bw.write("\n");
        }

        bw.flush();
        bw.close();
    }
}
