import java.util.*;
import java.io.*;

// 1284 : 집 주소

/**
 * Example
 * 120
 * 5611
 * 100
 * 0
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1284_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i <= 9; i++) {
            map.put(i, 3);
        }

        map.put(0, 4);
        map.put(1, 2);

        while (true) {
            int N = Integer.parseInt(br.readLine());

            if (N == 0) {
                break;
            }

            String nToString = Integer.toString(N);
            int answer = nToString.length() + 1;
            for (int i = 0; i < nToString.length(); i++) {
                answer += map.get(nToString.charAt(i) - '0');
            }

            System.out.println(answer);
        }
    }
}
