import java.util.*;
import java.io.*;

// 2455 : 지능형 기차

/**
 * Example
 * 0 32
 * 3 13
 * 28 25
 * 39 0
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2455_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int maxPassenger = 0;
        int nowPassenger = 0;
        for (int i = 0; i < 4; i++) {
            st = new StringTokenizer(br.readLine());
            nowPassenger -= Integer.parseInt(st.nextToken());
            nowPassenger += Integer.parseInt(st.nextToken());

            maxPassenger = Math.max(maxPassenger, nowPassenger);
        }

        System.out.println(maxPassenger);
    }
}
