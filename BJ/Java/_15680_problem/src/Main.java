import java.util.*;
import java.io.*;

// 15680 : 연세대학교

/**
 * Example
 * 0
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_15680_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int select = Integer.parseInt(br.readLine());

        if (select == 0) {
            System.out.println("YONSEI");
        }

        if (select == 1) {
            System.out.println("Leading the Way to the Future");
        }
    }
}
