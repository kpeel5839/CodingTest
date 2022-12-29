import java.util.*;
import java.io.*;

// 2845 : 파티가 끝나고 난 뒤

/**
 * Example
 * 1 10
 * 10 10 10 10 10
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2845_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int realNumberOfGuest = Integer.parseInt(st.nextToken()) * Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 5; i++) {
            System.out.print(Integer.parseInt(st.nextToken()) - realNumberOfGuest + " ");
        }
    }
}
