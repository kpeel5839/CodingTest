import java.util.*;
import java.io.*;

// 2480 : 주사위 세개

/**
 * 예제
 * 3 3 6
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2480_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] dice = new int[3];
        for (int i = 0; i < 3; i++) {
            dice[i] = Integer.parseInt(st.nextToken());
        }

        int cnt = 0;
        int number = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = i + 1; j < 3; j++) {
                if (dice[i] == dice[j]) {
                    number = dice[i];
                    cnt++;
                }
            }
        }

        if (cnt == 3) {
            System.out.println(10000 + number * 1000);
        } else if (cnt == 1) {
            System.out.println(1000 + number * 100);
        } else {
            System.out.println(Math.max(dice[0], Math.max(dice[1], dice[2])) * 100);
        }
    }
}
