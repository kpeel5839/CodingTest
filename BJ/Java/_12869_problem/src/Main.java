import java.util.*;
import java.io.*;

// 12869 : 뮤탈리스크

/**
 * 예제
 * 3
 * 12 10 4
 */
public class Main {
    static int N;
    static int cnt = 0;
    static int INF = 10000;
    static boolean[] visited = new boolean[10];
    static int[][] attack = new int[6][3];
    static int[][][] dp = new int[60 + 1][60 + 1][60 + 1];

    static void comb(int depth, List<Integer> list) {
        if (depth == N) {
            for (int i = 0; i < list.size(); i++) {
                attack[cnt][i] = list.get(i);
            }
            cnt++;
            return;
        }

        for (int i = 9, j = 0; j < N; i /= 3, j++) {
            if (!visited[i]) {
                visited[i] = true;
                list.add(i);
                comb(depth + 1, list);
                visited[i] = false;
                list.remove(list.size() - 1);
            }
        }
    }

    static int cal(int v) {
        return Math.max(0, v);
    }

    static int dfs(int[] hp) {
        if (hp[0] == 0 && hp[1] == 0 && hp[2] == 0) {
            return 0;
        }

        if (dp[hp[0]][hp[1]][hp[2]] != -1) {
            return dp[hp[0]][hp[1]][hp[2]];
        }

        dp[hp[0]][hp[1]][hp[2]] = INF;

        for (int i = 0; i < attack.length; i++) {
            int[] nHp = hp.clone();

            for (int j = 0; j < attack[i].length; j++) {
                nHp[j] = cal(nHp[j] - attack[i][j]);
            }

            dp[hp[0]][hp[1]][hp[2]] = Math.min(dp[hp[0]][hp[1]][hp[2]], 1 + dfs(nHp));
        }

        return dp[hp[0]][hp[1]][hp[2]];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_12869_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        int[] hp = new int[3];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            hp[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i <= 60; i++) {
            for (int j = 0; j <= 60; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }

        comb(0, new ArrayList<>());
        System.out.println(dfs(hp));
    }
}