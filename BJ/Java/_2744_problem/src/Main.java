import java.util.*;
import java.io.*;

// 2744 : 

/**
 * 예제
 * WrongAnswer
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2744_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String s = br.readLine();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isUpperCase(c)) {
                bw.write(c + 32);
            } else {
                bw.write(c - 32);
            }
        }

        bw.flush();
        bw.close();
    }
}
