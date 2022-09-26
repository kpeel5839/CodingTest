import java.util.*;
import java.io.*;

// 3003 : 킹, 퀸, 룩, 비숍, 나이트, 폰

/**
 * 예제
 * 0 1 2 2 2 7
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_3003_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] arr = {1, 1, 2, 2, 2, 8};

        for (int i = 0; i < arr.length; i++) {
            System.out.print((arr[i] - Integer.parseInt(st.nextToken())) + " ");
        }
    }
}
