import java.util.*;
import java.io.*;

// 5235 : Even Sum More Than Odd Sum

/**
 * Example
 * 4
 * 8 5 7 2 1 10 13 6 12
 * 4 5 2 3 4
 * 8 1 2 1 2 1 1 1 1
 * 6 1 1 2 3 5 8
 */
public class Main {

    public static int[] largeBoard;

    public static int[] getSumEvenAndOdd() {
        int[] sums = new int[2];

        for (int index = 0; index < largeBoard.length; index++) {
            if (largeBoard[index] % 2 == 0) { // is even
                sums[0] += largeBoard[index];
            } else { // is odd
                sums[1] += largeBoard[index];
            }
        }

        return sums;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5235_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int testCases = Integer.parseInt(br.readLine());
        for (int testCase = 0; testCase < testCases; testCase++) {
            st = new StringTokenizer(br.readLine());
            largeBoard = new int[st.countTokens()];
            int tokenIndex = 0;
            st.nextToken();

            while (st.hasMoreTokens()) {
                largeBoard[tokenIndex++] = Integer.parseInt(st.nextToken());
            }

            int[] sums = getSumEvenAndOdd();

            if (sums[0] < sums[1]) {
                bw.write("ODD\n");
            }

            if (sums[0] > sums[1]) {
                bw.write("EVEN\n");
            }

            if (sums[0] == sums[1]) {
                bw.write("TIE\n");
            }
        }

        bw.flush();
        bw.close();
    }
}
