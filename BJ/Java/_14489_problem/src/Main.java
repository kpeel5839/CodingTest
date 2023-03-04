import java.util.*;
import java.io.*;

// 14489 : 치킨 두마리(..)

/**
 * Example
 * 87 31
 * 20000
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_14489_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int money = Integer.parseInt(st.nextToken()) + Integer.parseInt(st.nextToken());
        int price = Integer.parseInt(br.readLine()) * 2;

        if (money < price) {
            System.out.println(money);
        } else {
            System.out.println(money - price);
        }
    }
}