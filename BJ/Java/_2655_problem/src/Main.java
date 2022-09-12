import java.util.*;
import java.io.*;

// 2655 : 가장 높은 탑 쌓기

/**
 * 예제
 * 5
 * 25 3 4
 * 4 4 6
 * 9 2 3
 * 16 2 5
 * 1 5 2
 */
public class Main {
    static int N;
    static int[][] rock;
    static int[][] dp;
    static boolean[][] tracking;
    static Stack<Integer> stack = new Stack<>();

    static int dfs(int depth, int pre) {
        if (depth == (N + 1)) {
            return 0;
        }

        if (dp[depth][pre] != -1) {
            return dp[depth][pre];
        }

        dp[depth][pre] = 0;

        // 현재를 선택하거나 말거나, 정렬을 했으니까, 이전 것을 무조건 선택 가능함
        int select = -1;

        if (rock[depth][2] < rock[pre][2]) {
            select = dfs(depth + 1, depth) + rock[depth][1];
        }

        int notSelect = dfs(depth + 1, pre);

        if (select > notSelect) {
            tracking[depth][pre] = true;
        }

        dp[depth][pre] = Math.max(select, notSelect);
        return dp[depth][pre];
    }

    static void track() {
        int depth = 1;
        int pre = 0;

        while (depth != N + 1) {
            if (tracking[depth][pre]) {
                stack.push(rock[depth][3]);
                pre = depth;
                depth++;
            } else {
                depth++;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2655_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        dp = new int[N + 1][N + 1];
        tracking = new boolean[N + 1][N + 1];
        rock = new int[N + 1][4];
        rock[0][0] = Integer.MAX_VALUE;
        rock[0][2] = Integer.MAX_VALUE;
        Arrays.fill(dp[0], -1);

        for (int i = 1; i <= N; i++) {
            Arrays.fill(dp[i], -1);
            st = new StringTokenizer(br.readLine());

            rock[i][0] = Integer.parseInt(st.nextToken());
            rock[i][1] = Integer.parseInt(st.nextToken());
            rock[i][2] = Integer.parseInt(st.nextToken());
            rock[i][3] = i;
        }

        Arrays.sort(rock, (o1, o2) -> {
            if (o1[0] == o2[0]) { // 넓이가 같을 때
                return o2[2] - o1[2];
            }

            return o2[0] - o1[0];
        });

//        for (int i = 0; i <= N; i++) {
//            System.out.println(Arrays.toString(rock[i]));
//        }

        dfs(1, 0);
        track();
        bw.write(stack.size() + "\n");

        while (!stack.isEmpty()) {
            bw.write(stack.pop() + "\n");
        }

        bw.flush();
        bw.close();
    }
}
