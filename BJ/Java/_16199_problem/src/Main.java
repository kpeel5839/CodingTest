import java.util.*;
import java.io.*;

// 16199 : 나이 계산하기

/**
 * Example
 * 2003 3 5
 * 2003 4 5
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_16199_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] a = new int[3];

        for (int i = 0; i < 3; i++) {
            a[i] = Integer.parseInt(st.nextToken());

        }

        st = new StringTokenizer(br.readLine());
        int[] b = new int[3];
        for (int i = 0; i < 3; i++) {
            b[i] = Integer.parseInt(st.nextToken());
        }

        int YEAR = 365;
        int MONTH = 30;
        System.out.println(((b[0] * YEAR + b[1] * MONTH + b[2]) - (a[0] * YEAR + a[1] * MONTH + a[2])) / YEAR);
        System.out.println(b[0] - a[0] + 1);
        System.out.println(b[0] - a[0]);

        List<Integer> list = new ArrayList<>();
        Collections.reverse(list);
    }
}
