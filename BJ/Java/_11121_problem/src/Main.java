import java.util.*;
import java.io.*;

// 11121 : Communication Channels

/**
 * Example
 * 2
 * 10 10
 * 10 11
 */
public class Main {
    public static final String ERROR = "ERROR";
    public static final String OK = "OK";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_11121_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());

            if (st.nextToken().equals(st.nextToken())) {
                System.out.println(OK);
            } else {
                System.out.println(ERROR);
            }
        }
    }
}
