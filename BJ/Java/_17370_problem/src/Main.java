import java.util.*;
import java.io.*;
import java.util.function.Function;

// 17370 : 육각형 우리 속의 개미

/**
 * -- 전제 조건
 * N 이 주어지면
 * 육각형에 개미들이 기어다닐 때, 본인 페로몬에 부딪혀서 죽는 경우가 N 번의 회전을 거쳤을 때의 경우의 수를 구해라.
 *
 * -- 틀 설계
 * N + 1 로 육각형 변수 visited 를 만든다.
 * dfs 를 돌리는데, 일단 visited[0]++ 를 해주고 N 을 넣어서 돌려준다.
 *
 * depth 를 유지하거나, + 1 을 하는 경우 2가지의 경우로 분기한다.
 * N == 5 인 경우에는 예외로 쳐서 2를 반환한다. (그냥 바로)
 *
 * 이제 dfs 에서 6이라면 return 2 를 한다.
 * 그러니까 N 을 넣어서 보낼 때, 계속 재귀호출 할 떄 remain - 1 을 해주어야 한다.
 *
 * 이렇게 구하면 어느정도 될 것 같긴한데 안될 확률이 너무 높다.
 * 왜냐하면 시간 복잡도상은 괜찮지만 예외 경우를 도저히 처리할 수가 없다.
 */
public class Main {
    static int N;
    static int[] visited;

    static int dfs(int depth, int remain) {
//        System.out.println(depth);

        if (visited[depth] == 6) {
            return 0;
        }

        if (remain == 6) {
            if (visited[depth] < 5) {
                return 2;
            } else {
                return 0;
            }
        }

        int res = 0;

        // 다음 depth 로 가는 경우
        visited[depth]++;
        res += dfs(depth + 1, remain - 1);
        visited[depth]--;

        // 현재 depth 를 유지하는 경우
        if (visited[depth] < 5) {
            visited[depth + 1]++;
            res += dfs(depth + 1, remain - 1);
            visited[depth + 1]--;
        } else {
            // 현재 depth 가 5까지 차서 현재로 갈 수 없는 경우, 이전 경우로 가준다.
            visited[depth - 1]++;
            res += dfs(depth - 1, remain - 1);
            visited[depth - 1]--;

        }

        return res;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_17370_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        visited = new int[N + 1];

        if (N == 5) {
            System.out.print(2);
        } else {
            visited[0]++;
            System.out.println(dfs(0, N));
        }
    }
}