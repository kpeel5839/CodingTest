import java.util.*;
import java.io.*;

// 13118 : 뉴턴의 사과

/**
 * Example
 * -5 -2 1 5
 * 1 4 2
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_13118_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] peoplePosition = new int[5];

        for (int i = 1; i < peoplePosition.length; i++) {
            peoplePosition[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(st.nextToken());
        int answer = 0;

        for (int i = 1; i < peoplePosition.length; i++) {
            if (peoplePosition[i] == x) {
                answer = i;
            }
        }

        System.out.println(answer);
    }
}
