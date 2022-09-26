import java.math.BigInteger;
import java.util.*;
import java.io.*;

// 1271 : 엄청난 부자 2

/**
 * 예제
 * 1000 100
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1271_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        BigInteger n = new BigInteger(st.nextToken());
        BigInteger m = new BigInteger(st.nextToken());

        System.out.println(n.divide(m));
        System.out.println(n.mod(m));
    }
}
