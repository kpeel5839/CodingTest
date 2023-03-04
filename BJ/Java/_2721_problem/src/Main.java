import java.net.Inet4Address;
import java.util.*;
import java.io.*;

// 2721 : 삼각수의 합

/**
 * Example
 * 4
 * 3
 * 4
 * 5
 * 10
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2721_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        long[] t = new long[302];
        long[] w = new long[301];
        t[1] = 1;
        for (int i = 2; i < t.length; i++) {
            t[i] = i + t[i - 1];
        }

        for (int i = 1; i < w.length; i++) {
            for (int j = i; j > 0; j--) {
                w[i] += (j * t[j + 1]);
            }
        }

        int N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            int index = Integer.parseInt(br.readLine());
            System.out.println(w[index]);
        }
    }
}