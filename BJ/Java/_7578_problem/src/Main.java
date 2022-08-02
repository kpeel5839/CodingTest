import java.util.*;
import java.io.*;

// 7578 : 공장

/**
 * 예제
 * 5
 * 132 392 311 351 231
 * 392 351 132 311 231
 */
public class Main {
    static int N;
    static int S;
    static long ans = 0;
    static long[] tree;
    static HashMap<String, Integer> map = new HashMap<>();

    static long sum(int cur, int left, int right, int sumLeft, int sumRight) {
        if (sumRight < left || right < sumLeft) { // 이 경우 아무것도 반환 x
            return 0;
        }

        if (sumLeft <= left && right <= sumRight) { // 완전 포함하고 있는 경우
            return tree[cur];
        }

        return sum(cur * 2, left, (left + right) / 2, sumLeft, sumRight)
                + sum(cur * 2 + 1, (left + right) / 2 + 1, right, sumLeft, sumRight);
    }

    static void update(int cur, int left, int right, int index) { // 더해주는 것은 무조건 1더해주면 된다.
        if (!(left <= index && index <= right)) {
            return;
        }

        tree[cur]++;

        if (left != right) {
            update(cur * 2, left, (left + right) / 2, index);
            update(cur * 2 + 1, (left + right) / 2 + 1, right, index);
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_7578_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        S = 1 << ((int) Math.ceil(Math.log(N) / Math.log(2)));
        tree = new long[S * 2];

        int[] index = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            map.put(st.nextToken(), i);
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            index[i] = map.get(st.nextToken()); // index 를 얻는다.
        }

        for (int i = N - 1; i != -1; i--) {
            int find = index[i];

            if (find != N) { // N 인 경우는 짜피 앞에 아무것도 없어서 update 만 해주면 됨
                ans += N - find - sum(1, 1, S, find + 1, N);
            }

            update(1, 1, S, find); // update
        }

        System.out.println(ans);
    }
}
