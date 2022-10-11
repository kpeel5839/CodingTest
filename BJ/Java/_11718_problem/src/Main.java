import java.util.*;
import java.io.*;

// 11718 : 그대로 출력하기

/**
 * 예제
 * Hello
 * Baekjoon
 * Online Judge
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_11718_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        while (true) {
            String s = br.readLine();

            if (s == null) {
                break;
            }

            bw.write(s + "\n");
        }

        bw.flush();
        bw.close();
    }
}
