import java.util.*;
import java.io.*;
import java.util.function.Function;

// 2749 : 피보나치 수 3

/**
 * -- 전제 조건
 * 겁나 큰 피보나치 수가 주어지면 그것을 구하고
 * 1,000,000 로 나눈 수를 출력한다.
 *
 * -- 틀 설계
 * 수학적으로 증명을 거쳐
 * [1] * [1 1] ^ n - 2
 * [1]   [1 0]
 *
 * 이런식이 나온다라는 것을 구했다.
 * 피보나치수는
 * f(n) = f(n - 1) + f(n - 2) 인 것으로 부터 구할 수가 있다.
 *
 * 이렇게 해서 거듭제곱을 빨리 구하는 방법과
 * 행렬곱을 이용하면 문제를 쉽게 해결할 수 있다.
 *
 * -- 해맸던 점
 * 그래 틀릴리가 없었는데
 * 이상했음
 *
 * 알고보니까 MOD 연산을 res[i][j] = res[i][j] + (m1[i][k] * m2[k][j] % MOD) % MOD 여기서 이지랄 하고 있었음
 * 이렇게 되면 그냥 저기다가 MOD 연산한번 더하는 개 쓸데 없는 짓하는 거였는데..
 *
 * 그래서 이거 고치니까 맞았음
 */
public class Main {
    static long[][] fib = {{1}, {1}};
    static long[][] mat = {{1, 1}, {1, 0}}; // 두개의 2차원 행렬 선언
    static final long MOD = 1_000_000;

    static long[][] matrix(long[][] m1, long[][] m2) { // 2개의 matrix 를 받아서
        long[][] res = new long[m1.length][m2[0].length];

        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                for (int k = 0; k < m1[0].length; k++) {
                    res[i][j] = (res[i][j] + ((m1[i][k] * m2[k][j]) % MOD)) % MOD;
                }
            }
        }

        return res;
    }

    static long[][] pow(long digit) { // 각각이 2 차원 배열을 반환해야 한다.
        if (digit == 1) {
            return mat.clone();
        }

        if (digit % 2 == 0) { // 바로 2로 나누어서 보낸다.
            long[][] res = pow(digit / 2);
            return matrix(res, res); // 제곱을 해주어서 보낸다.
        } else { // mat 을 하나를 빼주고 2로 나누어서 보낸다.
            long[][] res = pow((digit - 1) / 2);
            return matrix(mat, matrix(res, res));
        }
    }

    public static void main(String[] args) throws IOException {
//        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2749_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        long N = Long.parseLong(br.readLine());
        if (N > 2) {
            System.out.println(matrix(pow(N - 2), fib)[0][0]);
        } else {
            System.out.println(1);
        }
    }
}
