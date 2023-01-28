import java.util.*;
import java.io.*;

// 15963 : CASIO

/**
 * Example
 * 1 1
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_15963_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        if (st.nextToken().equals(st.nextToken())) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
    }
}