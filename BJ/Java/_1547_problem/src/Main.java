import java.util.*;
import java.io.*;

// 1547 : 공

/**
 * Example
 * 4
 * 3 1
 * 2 3
 * 3 1
 * 3 2
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1547_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int numberOfSwap = Integer.parseInt(br.readLine());
        boolean[] isBall = new boolean[4];
        isBall[1] = true;

        for (int i = 0; i < numberOfSwap; i++) {
            st = new StringTokenizer(br.readLine());
            int first = Integer.parseInt(st.nextToken());
            int second = Integer.parseInt(st.nextToken());
            boolean temp = isBall[first];
            isBall[first] = isBall[second];
            isBall[second] = temp;
        }

        for (int i = 1; i < isBall.length; i++) {
            if (isBall[i]) {
                System.out.println(i);
            }
        }
    }
}
