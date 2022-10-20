import java.util.*;
import java.io.*;

// 15964 : 이상한 기호

/**
 * 예제
 * 3 4
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_15964_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long a = (long) Math.pow(Long.parseLong(st.nextToken()), 2);
        long b = (long) Math.pow(Long.parseLong(st.nextToken()), 2);

        System.out.println(a - b);
    }
}
