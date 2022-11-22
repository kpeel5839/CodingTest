import java.util.*;
import java.io.*;

// 5220 : Error Detection

/**
 * example
 * 4
 * 34173 1
 * 45 1
 * 15 0
 * 31 0
 */
public class Main {

    static int countOneOfBinaryNumber(int decimalNumber) {
        int count = 0;
//        String binaryNumber = decimalNumber == 0 ? "0" : "";

        while (decimalNumber != 0) {
            if (decimalNumber % 2 == 1) {
                count++;
            }

//            binaryNumber = (decimalNumber % 2) + binaryNumber;
            decimalNumber = decimalNumber >> 1;
        }

//        System.out.println(binaryNumber);
        return count;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5220_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int numberOfTestCase = Integer.parseInt(br.readLine());

        for (int test = 0; test < numberOfTestCase; test++) {
            st = new StringTokenizer(br.readLine());
            int divided = Integer.parseInt(st.nextToken());
            boolean even = Integer.parseInt(st.nextToken()) == 0;
            int count = countOneOfBinaryNumber(divided);
            String answer = "";

            if (count % 2 == 0) {
                if (even) {
                    answer = "Valid";
                } else {
                    answer = "Corrupt";
                }
            } else {
                if (even) {
                    answer = "Corrupt";
                } else {
                    answer = "Valid";
                }
            }

            bw.write(answer + "\n");
        }

        bw.flush();
        bw.close();
    }
}
