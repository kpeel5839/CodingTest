import java.util.*;
import java.io.*;

// 1006 : 습격자 초라기

/**
 * 예제
 * 1
 * 8 100
 * 70 60 55 43 57 60 44 50
 * 58 40 47 90 45 52 80 40
 */
public class Main2 {
    static int N;
    static int W;
    static int[][] enemy;
    static boolean[][] visited;
    static int[][][][][][] dp;
    
    static int cal(int v) {
        return v == N + 1 ? 1 : v;
    }
    
    static int dfs(int depth, int cur, int initDown, int initUp) {
        if (depth == N + 1) {
            return 0;
        }
        
        int existUp = 0;
        int now = visited[cur][depth] ? 1 : 0; // 있는 경우 1
//        System.out.println("depth : " + depth + " initDown : " + initDown + " initUp : " + initUp);
//        visitedPrint();

        if (dp[cur][existUp][now][initDown][initUp][depth] != -1) {
//            System.out.println("dp[" + cur + "][" + existUp + "][" + now + "][" + initDown + "][" + initUp + "][" + depth + "] : " + dp[cur][existUp][now][initDown][initUp][depth]);
            return dp[cur][existUp][now][initDown][initUp][depth];
        }

        if (cur == 0) {
            existUp = visited[cur + 1][depth] ? 1 : 0;

            if (existUp != 1 && now == 0 && enemy[cur][depth] + enemy[cur + 1][depth] <= W) { // 위로 치는건 cur == 0 만 가능
                visited[0][depth] = true;
                visited[1][depth] = true;
                dp[cur][existUp][now][initDown][initUp][depth] = Math.max(dp[cur][existUp][now][initDown][initUp][depth],
                        1 + dfs(depth, 1, depth == 1 ? 1 : initDown, depth == 1 ? 1 : initUp));
                visited[0][depth] = false;
                visited[1][depth] = false;
            }
        }

        if (now == 0 && !visited[cur][cal(depth + 1)]  && (cal(depth + 1) != depth) && enemy[cur][depth] + enemy[cur][cal(depth + 1)] <= W) {
            visited[cur][depth] = true;
            visited[cur][cal(depth + 1)] = true;
            dp[cur][existUp][now][initDown][initUp][depth] = Math.max(dp[cur][existUp][now][initDown][initUp][depth],
                    1 + dfs(cur == 1 ? depth + 1 : depth, cur == 0 ? 1 : 0, (depth == 1 && cur == 0) ? 1 : initDown, (depth == 1 && cur == 1) ? 1 : initUp));
            visited[cur][depth] = false;
            visited[cur][cal(depth + 1)] = false;
        }

        dp[cur][existUp][now][initDown][initUp][depth] = Math.max(dp[cur][existUp][now][initDown][initUp][depth], dfs(cur == 1 ? depth + 1 : depth, cur == 0 ? 1 : 0, initDown, initUp));

        return dp[cur][existUp][now][initDown][initUp][depth];
    }
    
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1006_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            enemy = new int[2][N + 1];
            visited = new boolean[2][N + 1];
            dp = new int[2][2][2][2][2][N + 1];

            for (int i = 0; i < 2; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 1; j <= N; j++) {
                    enemy[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    for (int c = 0; c < 2; c++) {
                        for (int k = 0; k < 2; k++) {
                            for (int q = 0; q < 2; q++) {
                                Arrays.fill(dp[i][j][c][k][q], -1);
                            }
                        }
                    }
                }
            }

            int res = dfs(1, 0, 0, 0);
            bw.write(2 * (N - res) + res + "\n");
        }

        bw.flush();
        bw.close();
    }

    static void visitedPrint() {
        System.out.println("next\n");
        for (int i = 1; i != -1; i--) {
            if (i == 0) {
                System.out.println("Down");
            } else {
                System.out.println("Up");
            }

            for (int j = 1; j <= N; j++) {
                if (visited[i][j]) {
                    System.out.print(1 + " ");
                } else {
                    System.out.print(0 + " ");
                }
            }

            System.out.println();
        }

        System.out.println();
    }
}
