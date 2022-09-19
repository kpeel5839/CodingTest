import java.util.*;
import java.io.*;

// 11402 : 이항 계수 4

/**
 * Example
 * 5 2 3
 */
public class Main {
    static long N;
    static long M;
    static long K;
    static long[][] pas;

    static long pascal(int n, int r, int p) {
        if (r == 0 || r == n) {
            return 1;
        }

        if (pas[n][r] != 0) {
            return pas[n][r];
        }

        return pas[n][r] = (pascal(n - 1, r, p) + pascal(n - 1, r - 1, p)) % p;
    }

    static List<Integer> radix(long n, long p) {
        List<Integer> res = new ArrayList<>();

        while (n != 0) {
            res.add((int) (n % p)); // p 진법으로 표현하니, 그 위의 값들은 전부 나누어 떨어져 날라가고, 첫번쨰 자리만 얻어온다.
            n /= p; // 첫째자리를 처리하였으니, p 로 나누어 진법상의 p 지수들을 하나씩 낮춰준다.
        }

        return res;
    }

    static long lucasTheory() {
        // n, k 를 p 진법으로 표현
        long res = 1;
        List<Integer> nr = radix(N, M);
        List<Integer> kr = radix(K, M);

        // 순서대로 돌면서 nr 에 맞춰서, niCki 를 진행한다.
        for (int i = 0; i < nr.size(); i++) {
            int nn = nr.get(i);
            int kk = kr.size() <= i ? 0 : kr.get(i);

            if (nn < kk) {
                return 0;
            } else {
                res = (res * pascal(nn, kk, (int) M)) % M;
            }
        }

        return res;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_11402_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Long.parseLong(st.nextToken());
        K = Long.parseLong(st.nextToken());
        M = Long.parseLong(st.nextToken());
        pas = new long[(int) (M + 1)][(int) (M + 1)];

        System.out.println(lucasTheory());
    }
}
