import java.util.*;
import java.io.*;

// 2515 : 전시장

/**
 * 예제
 * 6 4
 * 15 80
 * 8 230
 * 10 100
 * 17 200
 * 20 75
 * 26 80
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2515_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(st.nextToken());
        int[] dp = new int[N + 1]; // 여기에는 해당 picture[i] 까지 고려했을 때, 최대 가격을 저장
        int[][] picture = new int[N + 1][2];
        picture[0][0] = -1_000_000_000; // Integer.MIN_VALUE 로 하니까 정렬이 제대로 안됐음, 그 이유는 아마 o1[0] - o2[0] 에서 value 가 overflow 가 되어서 잘 정렬이 되지 않은 것으로 보임
        picture[0][1] = 0;

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());

            picture[i][0] = Integer.parseInt(st.nextToken());
            picture[i][1] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(picture, (o1, o2) -> o1[0] - o2[0]);

        for (int i = 1; i <= N; i++) {
            int find = picture[i][0] - S; // 이거 이하의 값을 찾아야한다.
            int left = 0;
            int right = i - 1; // 찾는 범위는 이렇게 된다.
            int ans = 0;

            while (left <= right) {
                int mid = (left + right) / 2;

                if (picture[mid][0] <= find) {
                    ans = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            dp[i] = Math.max(dp[i - 1], picture[i][1] + dp[ans]);
        }

        System.out.println(dp[N]);
    }
}
