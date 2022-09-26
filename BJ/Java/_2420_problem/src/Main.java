import java.util.*;
import java.io.*;

// 2420 : 사파리 월드

/**
 * 예제
 * -2 5
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2420_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long n = Long.parseLong(st.nextToken());
        long m = Long.parseLong(st.nextToken());

        System.out.println(Math.abs(n - m));
    }
}
