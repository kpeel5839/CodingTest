import java.util.*;
import java.io.*;

// 2056 : 작업

/**
 * 예제
 *
 * 7
 * 5 0
 * 1 1 1
 * 3 1 2
 * 6 1 1
 * 1 2 2 4
 * 8 2 2 4
 * 4 3 3 5 6
 *
 * 내가 간과하고 있던 점은 무조건 끝에 있는 정점이 정답일 것이라는 오만한 생각이였다.
 * 그래서, 그렇게 하지 않고 바꾸니까 맞음
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2056_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int[] dp = new int[N + 1];
        List<ArrayList<Integer>> relate = new ArrayList<>();
        relate.add(new ArrayList<>());

        for (int i = 1; i <= N; i++) {
            relate.add(new ArrayList<>());
            st = new StringTokenizer(br.readLine());
            dp[i] = Integer.parseInt(st.nextToken());
            int size = Integer.parseInt(st.nextToken());

            for (int j = 0; j < size; j++) {
                relate.get(i).add(Integer.parseInt(st.nextToken()));
            }
        }

        for (int i = 1; i <= N; i++) {
            int max = 0;

            for (Integer next : relate.get(i)) {
                max = Math.max(max, dp[next]);
            }

            dp[i] = max + dp[i];
        }

        int ans = 0;

        for (int i = 1; i <= N; i++) {
            ans = Math.max(ans, dp[i]);
        }

        System.out.print(ans);
    }
}
