import java.util.*;
import java.io.*;
import java.util.function.Function;

// N 으로 표현

class Solution {
    public static int res = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        System.out.println(solution(fun.apply(input[0]), fun.apply(input[1])));
    }

    public static int solution(int N, int number) {
        dfs(0, 0, N, number);

        if (res == Integer.MAX_VALUE) { // 실행 하고 나왔는데, 아직 res 없으면, 맞는 수 없음, 그러면 -1 을 넣어준다.
            res = -1;
        }

        return res;
    }

    public static void dfs(int depth, int value, int N, int target) {
        if (depth > 8) { // 8 개 이상 써버리면, 끝
            return;
        }

        if (value == target) {
            res = Math.min(res, depth);
            return;
        }

        int temp = 0;
        for (int i = 0; i < 8; i++) {
            if (depth + i < 8) { // depth + i 을 했을 때, 즉 개수가 8개가 넘어가면 continue                
                temp = temp * 10 + N;
                dfs(depth + i + 1, value + temp, N, target);
                dfs(depth + i + 1, value - temp, N, target);
                dfs(depth + i + 1, value * temp, N, target);
                dfs(depth + i + 1, value / temp, N, target);
            }
        }
    }
}