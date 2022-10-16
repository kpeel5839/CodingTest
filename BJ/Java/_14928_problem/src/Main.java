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

        BigInteger a = new BigInteger(br.readLine());
        BigInteger b = new BigInteger("20000303");

        System.out.println(a.mod(b));
    }
}
