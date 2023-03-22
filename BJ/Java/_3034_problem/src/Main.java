import java.util.*;
import java.io.*;

// 3034 : 앵그리 창영

/**
 * Example
 * 2 12 17
 * 21
 * 20
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_3034_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int max = (int) Math.sqrt(Math.pow(Integer.parseInt(st.nextToken()), 2) + Math.pow(Integer.parseInt(st.nextToken()), 2));

        for (int i = 0; i < n; i++) {
            if (Integer.parseInt(br.readLine()) <= max) {
                System.out.println("DA");
            } else {
                System.out.println("NE");
            }
        }
    }
}