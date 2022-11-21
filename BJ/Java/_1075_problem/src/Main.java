import java.util.*;
import java.io.*;

// 1075 : 나누기

/**
 * 예제
 * 1000
 * 3
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1075_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String N = br.readLine();
        int F = Integer.parseInt(br.readLine());
        String answer = "";

        for (int i = 0; i <= 99; i++) {
            // 한자리이면 앞에 한자리 넣어준다.
            String twoLetterOfLast = Integer.toString(i);
            if (twoLetterOfLast.length() == 1) {
                twoLetterOfLast = "0" + twoLetterOfLast;
            }

            int newNumber = Integer.parseInt(N.substring(0, N.length() - 2) + twoLetterOfLast);
            if (newNumber % F == 0) {
                answer = twoLetterOfLast;
                break;
            }
        }

        System.out.println(answer);
    }
}
