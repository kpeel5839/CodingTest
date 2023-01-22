import java.util.*;
import java.io.*;

// 10156 : 과자

/**
 * Example
 * 300 4 1000
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_10156_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int snackPrice = Integer.parseInt(st.nextToken());
        int numberOfBuy = Integer.parseInt(st.nextToken());
        int hasMoney = Integer.parseInt(st.nextToken());

        System.out.println(Math.max(0, snackPrice * numberOfBuy - hasMoney));
    }
}
