import java.util.*;
import java.io.*;
import java.util.function.Function;

// 10159 : 저울

/**
 * -- 전제 조건
 * 양팔 저울로 각 물건의 무게를 잰다.
 * 그러고서 주어진 두 물건 중 앞의 물건이 더 무겁다라고 하자.
 *
 * 그럴 때, i 번째 줄에 i 번의 물건과 비교할 수 없는 물건의 개수를 구하여라.
 *
 * -- 틀 설계
 * 이건 프로그래머스에서 순위라는 문제로 이미 접한적이 있는 문제이다.
 * 이긴 경우를 1
 * 진 경우를 -1 이라고 가정하였을 떄,
 * 플로이드 워샬 알고리즘으로 뚫려있지 않은 간선들을 연결하고
 * 최종적으로 0의 개수를 세면 본인과 연결되지 않은 간선,
 * 즉, 본인과 비교할 수 없는 물건의 개수를 알 수 있는 굉장히 알고있다면 쉬운 문제이다.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        int N = fun.apply(br.readLine());
        int M = fun.apply(br.readLine());
        int[][] compare = new int[N + 1][N + 1];

        for(int i = 0; i < M; i++) {
            String[] input = br.readLine().split(" ");
            int a = fun.apply(input[0]);
            int b = fun.apply(input[1]);

            compare[a][b] = 1; // a 가 b 보다 무겁다.
            compare[b][a] = -1; // b 가 a 보다 가볍다.
        }

        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (i != j && (compare[i][k] != 0 && compare[k][j] != 0)) { // 모순되는 경우는 주어지지 않는다, 둘중 하나라도 0이면 새로 만들 수가 없는 간선이다.
                        if (compare[i][k] == compare[k][j]) { // 0인 경우를 걸렀기 때문에 둘이 같은 경우는 추가될 수 있다.
                            compare[i][j] = compare[i][k]; // 둘은 짜피 같으니 둘중 하나의 값을 집어넣는다.
                        }
                    }
                }
            }
        }

        for (int i = 1; i <= N; i++) {
            int cnt = 0;
            for (int j = 1; j <= N; j++) {
                if (i != j && compare[i][j] == 0) {
                    cnt++;
                }
            }
            sb.append(cnt).append("\n");
        }

        System.out.print(sb);
    }
}
