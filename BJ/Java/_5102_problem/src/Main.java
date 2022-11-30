import java.util.*;
import java.io.*;

// 5102 : Sarah's toys
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5102_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        while (true) {
            st = new StringTokenizer(br.readLine());
            int totalToys = Integer.parseInt(st.nextToken());
            int leftToys = Integer.parseInt(st.nextToken());

            if (totalToys == 0 && leftToys == 0) {
                break;
            }

            int remainToys = totalToys - leftToys;
            int numberOfGroup = remainToys / 2;
            if (remainToys % 2 == 0 || numberOfGroup == 0) { // don't exist group of 3
                bw.write(numberOfGroup + " 0\n");
            } else { // exist group of 3
                bw.write((numberOfGroup - 1) + " 1\n");
            }
        }

        bw.flush();
        bw.close();
    }
}
