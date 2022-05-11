import java.util.*;
import java.io.*;
import java.util.function.Function;

// 1958 : LCS 3

/**
 * -- 전제조건
 * 이번에는 LCS 를 세단어로 실행하는 것이다.
 * -- 틀 설계
 * 그냥 3차원 배열로 생각하는데,
 * 쉽게 그냥 2차원 배열에서 3차원 배열로 늘은 거 말고는 달라진 거 없다고 생각하면 된다.
 *
 * 딱봐도, 그냥 3개가 다 같으면, map[z - 1][y - 1][x - 1] 에서 가져와서 + 1 해주면 되고,
 * 아니라면 z - 1, y - 1, x - 1 에서 큰 거 가져오면 된다.
 *
 * -- 결과
 * 그냥 빨리 풀었음
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String a = " " + br.readLine();
        String b = " " + br.readLine();
        String c = " " + br.readLine();

        int[][][] dp = new int[a.length()][b.length()][c.length()];
        for (int i = 1; i < a.length(); i++) {
            for (int j = 1; j < b.length(); j++) {
                for (int v = 1; v < c.length(); v++) {
                    if ((a.charAt(i) == b.charAt(j)) && (b.charAt(j) == c.charAt(v))) { // 3개가 다 같으면
                        dp[i][j][v] = dp[i - 1][j - 1][v - 1] + 1;
                    } else { // 같지 않으면
                        int max = Math.max(dp[i - 1][j][v], dp[i][j - 1][v]);
                        max = Math.max(max, dp[i][j][v - 1]);
                        dp[i][j][v] = max;
                    }
                }
            }
        }

        System.out.println(dp[a.length() - 1][b.length() - 1][c.length() - 1]);
    }
}
