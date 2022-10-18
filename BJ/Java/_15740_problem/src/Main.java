import java.math.BigInteger;
import java.util.*;
import java.io.*;

// 15740 : A + B - 9

/**
 * 예제
 * 123456789123456789123456789 987654321987654321987654321
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_15740_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        System.out.println(new BigInteger(st.nextToken()).add(new BigInteger(st.nextToken())));
    }
}
