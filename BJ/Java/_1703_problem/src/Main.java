import java.util.*;
import java.io.*;

// 1703 : 생장점

/**
 * Example
 * 1 3 0
 * 2 3 0 2 0
 * 3 3 0 2 0 2 0
 * 3 3 0 2 1 2 3
 * 2 4 1 3 4
 * 4 5 0 5 1 5 2 5 101
 * 0
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1703_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        while (true) {
            st = new StringTokenizer(br.readLine());
            int ageOfTree = Integer.parseInt(st.nextToken());

            if (ageOfTree == 0) {
                break;
            }

            int numberOfBranch = 1;

            for (int i = 0; i < ageOfTree; i++) {
                int splitFactor = Integer.parseInt(st.nextToken());
                int cutBranch = Integer.parseInt(st.nextToken());

                numberOfBranch = numberOfBranch * splitFactor - cutBranch;
            }

            bw.write(numberOfBranch + "\n");
        }

        bw.flush();
        bw.close();
    }
}
