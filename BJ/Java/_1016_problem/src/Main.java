import java.util.*;
import java.io.*;
import java.util.function.Function;

// 1016 : 제곱 ㄴㄴ 수

/**
 * -- 전제 조건
 * min, max 가 주어졌을 때, 제곱 ㄴㄴ 수가 얼마나 있는지 구하는 문제이다.
 *
 * -- 틀 설계
 * 이 경우 max 를 루트를 씌우면 100만
 * 그리고 2로 1조를 만들기 위해서는 40 번의 연산이 필요한다.
 *
 * 그러면 2가 가장 작은 수이니까
 * 100만 * 40번 즉, 4000만 번의 연산으로 답을 구할 수 있다.
 *
 * 이게 맞으면 너무 쉬운 문제일텐데
 * 이게 맞을리가 없을 것 같다.
 *
 * -- 해맸던 점
 * 실제 연산 진행하는 부분에서 long 으로 안하고 int 로 나누고 있었어서 틀리고 있었음
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1016_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long min = Long.parseLong(st.nextToken());
        long max = Long.parseLong(st.nextToken());
        boolean[] visited = new boolean[(int) (max - min + 1)];
        boolean[] isNotPrime = new boolean[(int) Math.floor(Math.sqrt(max)) + 1];

        for (int i = 2; i <= (int) Math.floor(Math.sqrt(Math.floor(Math.sqrt(max)))); i++) {
            if (!isNotPrime[i]) {
                for (int j = i * 2; j <= (int) Math.floor(Math.sqrt(max)); j += i) {
                    isNotPrime[j] = true;
                }
            }
        }

        for (long i = 2; i <= (int) Math.floor(Math.sqrt(max)); i++) {
            if (!isNotPrime[(int) i]) { // 소수인 경우만
                long start = min / (long) Math.pow(i, 2); // 여기서 long 으로 안하고 int 로 나누고 있었어서 틀리고 있었음

                for (long j = ((long) Math.pow(i, 2) * start); j <= max; j += (long) Math.pow(i, 2)) { // 최대 경우 40 이다.
                    if (j < min) {
                        continue;
                    }

                    visited[(int) (j - min)] = true;
                }
            }
        }

        int ans = 0;

        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                ans++;
            }
        }

        System.out.println(ans);
    }
}