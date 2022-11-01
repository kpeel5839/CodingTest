import java.util.*;
import java.io.*;

// 2525 : 오븐 시계

/**
 * 예제
 * 23 48
 * 25
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2525_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int totalTime = 60 * Integer.parseInt(st.nextToken());
        totalTime += Integer.parseInt(st.nextToken());
        totalTime += Integer.parseInt(br.readLine());
        totalTime %= 1440; // 하루

        System.out.println(totalTime / 60 + " " + totalTime % 60);
    }
}
