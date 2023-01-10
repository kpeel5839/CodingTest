import java.util.*;
import java.io.*;

// 4470 : 줄번호

/**
 * Example
 * 5
 * Lionel Cosgrove
 * Alice
 * Columbus and Tallahassee
 * Shaun and Ed
 * Fido
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_4470_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());

        for (int i = 1; i <= N; i++) {
            bw.write(i + ". " + br.readLine() + "\n");
        }

        bw.flush();
        bw.close();
    }
}
