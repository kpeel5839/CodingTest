import java.math.BigInteger;
import java.util.*;
import java.io.*;

// 2547 : 사탕 선생 고창명

/**
 * Example
 * 2
 *
 * 5
 * 5
 * 2
 * 7
 * 3
 * 8
 *
 * 6
 * 7
 * 11
 * 2
 * 7
 * 3
 * 4
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2547_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            br.readLine();
            String N = br.readLine();
            BigInteger sum = new BigInteger("0");

            for (int i = 0; i < Integer.parseInt(N); i++) {
                sum = sum.add(new BigInteger(br.readLine()));
            }

            if (sum.mod(new BigInteger(N)).equals(new BigInteger("0"))) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }
}