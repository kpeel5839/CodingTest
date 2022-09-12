import java.util.*;
import java.io.*;

// 2613 : 숫자 구슬

/**
 * 예제
 * 8 3
 * 5 4 2 6 9 3 8 7
 */
public class Main {
    static int N;
    static int M;
    static int[] marble;
    static int[][] dp;
    static int[][] tracking;

    static int dfs(int depth, int cnt) {
        if (depth == N) {
            if (cnt != M) {
                return Integer.MAX_VALUE;
            } else {
                return 0;
            }
        }

        if (M == cnt) { // depth == N 에 도달하지도 않았는데 M == cnt 이면 제대로된 경우가 아니기 때문에 MAX_VALUE 를 반환하여, 사용하지 못하도록 한다.
            return Integer.MAX_VALUE;
        }

        if (dp[depth][cnt] != -1) {
            return dp[depth][cnt];
        }

        // tracking 에다가 현재에서 최적의 그룹으로 선택한 맨 끝놈의 index 를 넣어줌
        // 그리고 dp 에다가 그 max 값이 가장 작은 값을 선택
        int sum = 0;
        int index = 0;
        dp[depth][cnt] = Integer.MAX_VALUE;

        for (int i = depth; i < N; i++) {
            sum += marble[i];
            int res = Math.max(sum, dfs(i + 1, cnt + 1)); // sum 과 이렇게 보냈을 때의 결과 값을 받아옴

            if (res < dp[depth][cnt]) {
                index = i;
                dp[depth][cnt] = res;
            }
        }

        tracking[depth][cnt] = index;
        return dp[depth][cnt];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2613_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        dp = new int[N][M + 1];
        tracking = new int[N][M + 1];
        marble = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], -1);
            marble[i] = Integer.parseInt(st.nextToken());
        }

        bw.write(dfs(0, 0) + "\n");
        int depth = 0;
        int cnt = 0;

        while (depth != N) {
            bw.write(tracking[depth][cnt] - depth + 1 + " ");
            depth = tracking[depth][cnt] + 1;
            cnt++;
        }

        bw.flush();
        bw.close();
    }
}
