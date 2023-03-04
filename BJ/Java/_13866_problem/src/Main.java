import java.util.*;
import java.io.*;

// 13866 : 팀 나누기

/**
 * Example
 * 4 7 10 20
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_13866_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int sum = 0;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < 4; i++) {
            int player = Integer.parseInt(st.nextToken());
            sum += player;
            min = Math.min(min, player);
            max = Math.max(max, player);
        }

        System.out.println(Math.abs(min + max - (sum - min - max)));
    }
}