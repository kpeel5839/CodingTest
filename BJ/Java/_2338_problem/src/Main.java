import java.math.BigInteger;
import java.util.*;
import java.io.*;

// 2338 : 긴자리 계산

/**
 * 예제
 * 1
 * -1
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2338_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        BigInteger n = new BigInteger(br.readLine());
        BigInteger m = new BigInteger(br.readLine());

        System.out.println(n.add(m));
        System.out.println(n.subtract(m));
        System.out.println(n.multiply(m));
    }
}
