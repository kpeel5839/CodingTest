import java.util.*;
import java.io.*;

// 2875 : 대회 or 인턴

/**
 * Example
 * 6 10 3
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2875_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        int t = Math.min(a / 2, b);
        int aa = a + b - t * 3;

        if (aa >= c) {
            System.out.println(t);
        } else {
            aa = c - aa;
            t -= (int) Math.ceil((float) aa / 3f);
            System.out.println(t);
        }
    }
}