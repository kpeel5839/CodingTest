import java.util.*;
import java.io.*;
import java.util.function.Function;

// N 으로 표현

/**
 * 답 봤음
 *
 * 신기하게, 괄호를 안쳐도
 * 답이 나온다, 그래서 dfs 로 해도 풀린다.
 *
 * 예를 들어서
 * 55 / 5 + 5 / 5 원래 정답은 이거인데
 *
 * 여기서 나온 식의 정답으로는 이러하다.
 * 5 + 55 / 5
 * 순서대로 5 + 55 = 60 / 5 = 12 이렇게 되는 것임
 *
 * 무슨 원리인지는 모르겠는데, 그냥 이렇게 해도 정답 나옴
 */
class Solution {
    public static int res = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        System.out.println(solution(fun.apply(input[0]), fun.apply(input[1])));
    }

    public static int solution(int N, int number) {
        dfs(0, 0, N, number, ""); // N과 Number 만 주고 시작

        if (res == Integer.MAX_VALUE) { // 실행 하고 나왔는데, 아직 res 없으면, 맞는 수 없음, 그러면 -1 을 넣어준다.
            res = -1;
        }

        return res;
    }

    public static void dfs(int depth, int value, int N, int target, String express) {
        if (depth > 8) { // 8 개 이상 써버리면, 끝 (n을)
            return;
        }

        if (value == target) { // 넘긴 값이, target 과 같다면, 끝내면 된다, 여기서 더 이어가서 값 더 변경하면서 해봤자 depth 더 나오기만함
            res = Math.min(res, depth); // depth 가 N의 개수
            return;
        }

        int temp = 0; // 연산하는 수는 아무것도 없는 상태에서 진행되어야 함

        // 근데, 이것은 괄호는 고려하지 않는 것인데, 어떻게 맞을까?
        for (int i = 1; i <= 8; i++) {
            if (depth + i <= 8) { // depth + i 을 했을 때, 즉 개수가 8개가 넘어가면 continue
                temp = temp * 10 + N; //
                dfs(depth + i, value + temp, N, target, express + " + " + temp);
                dfs(depth + i, value - temp, N, target, express + " - " + temp);
                dfs(depth + i, value * temp, N, target, express + " * " + temp);
                dfs(depth + i, value / temp, N, target, express + " / " + temp); // temp 로 나누고, N은 1 ~ 9 이니까, zero division exception 발생 안함
            }
        } // 이게 왜 dp 이지, 괜히 dp 로 알았다가, 더 힘들게 접근했음.... 그냥 이렇게 했으면 됐는데, 근데 이거는 괄호는 고려를 안했는데 어떻게 푼거지??
    }
}