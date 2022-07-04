import java.util.*;
import java.io.*;
import java.util.function.Function;

// 18427 : 함께 블록 쌓기

/**
 * -- 전제조건
 * 블록을 쌓는 가짓수를 구하는 문제이다.
 * N, M, H 가 주어졌을 때
 *
 * 최종적으로 한 학생당 하나만 해서 H 를 만드는 가짓수를 10007 로 나눠서 출력하라.
 *
 * -- 틀 설계
 * 난 DP 가 너무 싫다.
 * 그래도 설계는 한 것 같다.
 * 일단, 0번째 학생부터 N - 1 번째 학생까지 진행하는 느낌으로 하면 된다.
 * 즉, 0 번째 학생부터 만들 수 있는 가짓수를 차츰차츰 정립해나가면 된다.
 *
 * 그래서 이것을 놓는 경우, 놓지 않는 경우를 갱신하기 위해서는
 * 일단, 첫번째, 이전 열을 복사해서 붙혀넣어야함
 *
 * 즉, 계산하기 이전에, 메인 로직이 들어가기 이전에
 * dp[i - 1][j] == dp[i][j] 여야 한다라는 것이다.
 *
 * 둘 째, 현재 가지고 있는 배열을 순서대로 돌면서
 * 받아낸 값을 근거로 만들어지는 수 인덱스에다가 해당 가짓수를 더해준다.
 * 즉, 내가 현재 이 값을 선택하면 이 만큼의 가짓수를 만들어낼 수 있는 것이니까
 *
 * 예를 들어서
 * (1 번째 학생이 1, 2 를 가지고 있으면)
 *   0 1
 * 0 0 0
 * 1 1 1
 * 2 0 0 + 1 (1 + 1)
 * 3 1 1 + 1 (1 + 2)
 *
 * 4 (3 + 1)도 만들 수 있고, 5 (3 + 2)도 만들 수 있지만, 높이가 음수가 주어지지 않기에,
 * 목표 height 를 넘어서게 만드는 가짓수는 중요하지 않다.
 *
 * 이런 식으로 계속해서 값을 갱신해나가면?
 * 결국 답은 dp[N - 1][H] % MOD 다.
 *
 * -- 해맸던 점
 * MOD 연산을 하지 않아서 조금 해맸었음
 * 그것 외에는 막히는 부분은 없었음
 *
 * 설계하는데에 조금 애를 먹었던 듯
 */
public class Main {
    static final int MOD = 10_007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        int N = fun.apply(input[0]);
        int M = fun.apply(input[1]);
        int H = fun.apply(input[2]);
        int[][] dp = new int[N][H + 1];
        int[][] block = new int[N][];

        for (int i = 0; i < N; i++) {
            input = br.readLine().split(" ");
            block[i] = new int[input.length]; // 그 만큼 선언해준다.

            for (int j = 0; j < input.length; j++) {
                block[i][j] = fun.apply(input[j]);
            }
        }

        /**
         * 먼저 i - 1 을 복사해주는 행위
         * 그리고, 본인이 가진 블럭들을 가지고 해당 인덱스에다가 가짓수를 더해준다. (H 를 넘어서면? 그 값은 의미없기에 continue)
         *
         * 그러면 끝이다.
         */
        for (int i = 0; i < N; i++) {
            if (i != 0) {
                for (int j = 0; j <= H; j++) {
                    dp[i][j] = dp[i - 1][j]; // 복사
                }
            }

            for (int j = 0; j < block[i].length; j++) {
                int value = block[i][j];
                dp[i][value]++; // 가짓수를 더해준다. (본인만 선택하는 경우)

                if (i != 0) {
                    for (int k = 0; k <= H; k++) { // 가짓수가 0이 아닌놈과 더해준다.
                        if (dp[i - 1][k] != 0) { // 가짓수가 있는 것들은
                            if (k + value <= H) { // k + value 가 H 이하일 때만 진행
                                dp[i][k + value] = (dp[i][k + value] + dp[i - 1][k]) % MOD;
                            }
                        }
                    }
                }
            }
        }

//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j <= H; j++) {
//                System.out.print(dp[i][j] + " ");
//            }
//            System.out.println();
//        }

        System.out.println(dp[N - 1][H] % MOD);
    }
}
