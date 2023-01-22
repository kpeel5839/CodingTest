import java.util.*;
import java.io.*;

// 10768 : 특별한 날

/**
 * Example
 * 1
 * 7
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_10768_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int day = Integer.parseInt(br.readLine()) * 30;
        day += Integer.parseInt(br.readLine());

        if (day < (2 * 30 + 18)) {
            System.out.println("Before");
        }

        if (day > (2 * 30 + 18)) {
            System.out.println("After");
        }

        if (day == (2 * 30 + 18)) {
            System.out.println("Special");
        }
    }
}