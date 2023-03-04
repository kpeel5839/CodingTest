import java.util.*;
import java.io.*;

// 4299 : AFC 윔블던

/**
 * Example
 * 3 1
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_4299_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        if ((double) (a + b) % 2d != 0) {
            System.out.println(-1);
        } else {
            System.out.println(((a + b) / 2) + " " + (((a + b) / 2) - b));
        }
    }
}
