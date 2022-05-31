import java.util.*;
import java.io.*;
import java.util.function.Function;

// 14925 : 목장 건설하기

/**
 * -- 전제조건
 * 랜드씨는 퇴직금으로 목장을 지으려고 하는데
 * 0인 위치에만 목장을 건설이 가능하다.
 *
 * 그럴 때, 0인 위치에만 목장을 지어서 가장 큰 정사각형 목자으이 한변의 크기를 출력하시오
 * -- 틀 설계
 * dp 문제이고, 이전에 풀어본 well - known 문제이다.
 * 근데, 확실하게 나는 dp 에 약한 것 같다.
 *
 * 이 문제는 솔직히 어떻게 하면 빨리 풀 수 있을 지
 * 어떻게 하면 효율적일지에 대해서, 그리고 이 문제가 well - known 인지 조차 인지를 못하고 있었다.
 *
 * 이 문제의 핵심은 이것이다.
 * dp[i][j] 는 정사각형의 꼭짓점이다.
 * 그리고, dp[i][j] 는 정사각형의 꼭짓점이고, 그 해당 정사각형의 길이를 입력한다.
 *
 * 그렇다면, 입력할 수 있는 내용들은 1, 2, 3 ... 이런 정수일 것이다.
 * 그러면 고려를 해볼 요소들은 딱 3가지이다 dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1] 이다.
 * 이 3가지 요소중 가장 작은 정사각형을 골라서 + 1 해주면 된다.
 * 왜그럴까?
 * 일단, 내가 지금 탐색하고 있는 dp[i][j] 가 정사각형의 끝점이 되고
 * 2 이상이려면, 최소한 위의 3 요소들은 모두 정사각형이여야 한다 (이 문제에서는 map[i][j] == 0 이여야한다.)
 *
 * 그리고 위의 요소들의 값이 만일 2 이상이라고 생각해보자.
 * 그러면 당연하게도 머릿속으로 그려지는 정사각형은 3이다.
 * 왜냐? 다 정사각형의 끝점이고 2라면 정사각형의 변이 2인 것이니까
 *
 * 그러면 머릿속으로 그려보았을 때, 현재 위치의 정사각형은 2 + 1 이 된다라는 것을 알 수 있다.
 *
 * 만일 근데 크기가 다르다면? 2, 1, 1 이라면?
 * 그러면 머릿속으로 그려봤을 때, 절대적으로 3은 아니다.
 * 왜냐하면 임시적으로 표현해보면 (1 == 정사각형, 0 == x)
 * 1 1 0
 * 1 1 1
 * 0 1 1
 * 인 것이다.
 *
 * 그리고 만일 1, 2, 2 라면
 * 0 1 1
 * 1 1 1
 * 1 1 1
 * 이다. 이정도면 시각화가 많이 되었다라고 생각한다.
 *
 * 그럼 이 조건을 하기 위해서 어떤식으로 dp 를 구성해야 할까
 * 일단, 테두리를 0으로 채우기 위해서 dp[H + 1][W + 1] 로 구성해야 한다.
 * 그리고, 무조건적으로 map[i][j] == 0 인 구간만 이 연산을 진행해야 한다.
 *
 * 그렇게 하게 되면 이 문제는 굉장히 쉽게 해결할 수 있다.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        int H = fun.apply(input[0]);
        int W = fun.apply(input[1]);

        int[][] map = new int[H + 1][W + 1];
        int[][] dp = new int[H + 1][W + 1];

        for (int i = 1; i <= H; i++) {
            input = br.readLine().split(" ");
            for (int j = 1; j <= W; j++) {
                map[i][j] = fun.apply(input[j - 1]);
            }
        }

        int res = 0;

        for (int i = 1; i <= H; i++) {
            for (int j = 1; j <= W; j++) {
                if (map[i][j] == 0) {
                    dp[i][j] = Math.min(dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                    res = Math.max(res, dp[i][j]);
                }
            }
        }

        System.out.println(res);
    }
}