import java.util.*;
import java.io.*;

// 15873 : 공백없는 A + B

/**
 * Example
 * 102
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_15873_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String line = br.readLine();
        int answer = 0;

        if (line.length() == 3) {
            int pivot = 1;

            if (line.charAt(1) == '0') {
                pivot = 2;
            }

            int a = Integer.parseInt(line.substring(0, pivot));
            int b = Integer.parseInt(line.substring(pivot));
            answer = a + b;
        } else {
            int length = line.length() / 2;
            int a = Integer.parseInt(line.substring(0, length));
            int b = Integer.parseInt(line.substring(length));
            answer = a + b;
        }

        System.out.println(answer);
    }
}
