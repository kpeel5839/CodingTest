import java.util.*;
import java.io.*;

// 2643 : 색종이 올려 놓기

/**
 * 예제
 * 7
 * 1 2
 * 8 7
 * 20 10
 * 20 20
 * 15 12
 * 12 14
 * 11 12
 */
public class Main {
    static int N;
    static Paper[] paper;
    static int[][] dp;

    static class Paper {
        int y;
        int x;

        Paper(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public String toString() {
            return "y : " + y + " x : " + x;
        }
    }

    static boolean compare(Paper o1, Paper o2) { // o1 이 더 작은 경우 교체 (올라갈 수 있는 종이의 개수가)
        int o1Cnt = 0;
        int o2Cnt = 0;

        for (int i = 0; i <= N; i++) {
            if (o1.y >= paper[i].y && o1.x >= paper[i].x
                    || o1.x >= paper[i].y && o1.y >= paper[i].x) {
                o1Cnt++;
            }

            if (o2.y >= paper[i].y && o2.x >= paper[i].x
                    || o2.x >= paper[i].y && o2.y >= paper[i].x) {
                o2Cnt++;
            }
        }

        return o1Cnt < o2Cnt;
    }

    static int dfs(int depth, int use) {
        if (depth == N + 1) {
            return 0;
        }

        if (dp[depth][use] != -1) {
            return dp[depth][use];
        }

        if ((paper[use].y >= paper[depth].y && paper[use].x >= paper[depth].x)
                || (paper[use].x >= paper[depth].y && paper[use].y >= paper[depth].x)) { // 사용할 수 있는 경우
            dp[depth][use] = 1 + dfs(depth + 1, depth);
        }

        dp[depth][use] = Math.max(dp[depth][use], dfs(depth + 1, use));

        return dp[depth][use];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2643_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        dp = new int[N + 1][N + 1];
        paper = new Paper[N + 1];
        paper[0] = new Paper(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Arrays.fill(dp[0], -1);

        for (int i = 1; i <= N; i++) {
            Arrays.fill(dp[i], -1);
            st = new StringTokenizer(br.readLine());

            int width = Integer.parseInt(st.nextToken());
            int height = Integer.parseInt(st.nextToken());

            if (width < height) {
                int tmp = width;
                width = height;
                height = tmp;
            }

            paper[i] = new Paper(width, height);
        }

        Arrays.sort(paper, (o1, o2) -> {
            if (o1.y == o2.y) {
                return o2.x - o1.x;
            }

            return o2.y - o1.y;
        });

        System.out.println(dfs(1, 0));
    }
}
