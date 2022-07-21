import java.util.*;
import java.io.*;
import java.util.function.Function;

// 8980 : 택배

/**
 * -- 전제 조건
 * 보내는 마을 받는 마을이 있고,
 * 각 배송은 절대로 뒤로 돌아오지 않는다.
 *
 * 이럴 때, 가장 많이 배송을 하는 방법은?
 * -- 틀 설계
 * 솔직히 이거 답을 봤는데 답을 봐도 이해가 가지 않는다.
 *
 * 일단, 방법은 이러하다.
 * 상자를 각각 받고, 상자의 정보를 가지고 받는마을 오름차순, 같은 경우는 보내는 마을 오름차순으로 정렬을 해준다.
 *
 * 그리고, 각 상자를 순서대로 돌면서,
 * from -> to 까지 가면서
 * box[i] 즉, 현재 이미 실려있는 박스의 양을 보고, 거기서 최대값을 취한다.
 *
 * 최대값을 취한 뒤, C - max, cnt 를 하여서,
 * 실을 수 있는 양을 정하고
 * 각 마을에 더해준다.
 *
 * 물론 답에도 더해준다.
 * 이게 전부인데
 * 솔직히 진짜 모르겠다. 왜 이거는 이렇게 모르겠지?
 * 졸려서 머리가 안돌아가는건가?
 */
public class Main {
    static class Town {
        int from;
        int to;
        int cnt;

        Town(int from, int to, int cnt) {
            this.from = from;
            this.to = to;
            this.cnt = cnt;
        }
    }
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_8980_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        int N = fun.apply(input[0]);
        int C = fun.apply(input[1]);
        int M = fun.apply(br.readLine());
        Town[] town = new Town[M];

        for (int i = 0; i < M; i++) {
            input = br.readLine().split(" ");

            town[i] = new Town(fun.apply(input[0]), fun.apply(input[1]), fun.apply(input[2]));
        }

        Arrays.sort(town, (o1, o2) -> {
            if (o1.to == o2.to) {
                return o1.from - o2.from;
            }

            return o1.to - o2.to;
        });

        int[] box = new int[N + 1]; // 마을에 이미 실려있는 택배량
        int ans = 0;

        for (int i = 0; i < M; i++) {
            int f = town[i].from;
            int t = town[i].to;
            int c = town[i].cnt;

            int max = 0;
            for (int j = f; j < t; j++) {
                max = Math.max(max, box[j]);
            }

            int possible = Math.min(C - max, c);
            ans += possible;

            for (int j = f; j < t; j++) {
                box[j] += possible;
            }
        }

        System.out.println(ans);
    }
}
