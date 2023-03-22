import java.util.*;
import java.io.*;

// 2991 : 사나운 개

/**
 * Example
 * 2 2 3 3
 * 1 3 4
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2991_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] n = new int[4];

        for (int i = 0; i < 4; i++) {
            n[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 3; i++) {
            int dog = 0;
            int s = 0;
            int d = Integer.parseInt(st.nextToken());

            while (true) {
                if (s + 1 <= d && d <= s + n[0]) {
                    dog++;
                }

                s += n[0] + n[1];

                if (d <= s) {
                    break;
                }
            }

            s = 0;
            while (true) {
                if (s + 1 <= d && d <= s + n[2]) {
                    dog++;
                }

                s += n[2] + n[3];

                if (d <= s) {
                    break;
                }
            }

            System.out.println(dog);
        }
    }
}
