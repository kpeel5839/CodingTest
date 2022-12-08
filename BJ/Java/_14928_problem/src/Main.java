import java.math.BigInteger;
import java.util.*;
import java.io.*;

// 14928 : 큰 수 (BIG)

/**
 * 예제
 * 20000303200003032000030320000303200003032000030320000303200003032000030320000303
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_14928_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String soBigFuckingNumber = br.readLine();
        final int MOD = 20000303;
        long temp = 0;

        for (int i = 0; i < soBigFuckingNumber.length(); i++) {
            temp = ((temp * 10) + (soBigFuckingNumber.charAt(i) - '0')) % MOD;
        }

        System.out.println(temp);
    }
}
