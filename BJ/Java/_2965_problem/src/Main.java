import java.util.*;
import java.io.*;

// 2965 : 캥거루 세마리

/**
 * Example
 * 3 5 9
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2965_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] arr = new int[3];
        for (int i = 0; i < 3; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(Math.max(arr[1] - arr[0] - 1, arr[2] - arr[1] - 1));
    }
}