import java.io.*;
import java.util.*;

// 3733 : shares

/**
 * example
 * 1 100
 * 2 7
 * 10 9
 * 10 10
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_3733_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        while (true) {
            String line = br.readLine();

            if (line == null) {
                break;
            }

            st = new StringTokenizer(line);
            int numberOfGroup = Integer.parseInt(st.nextToken());
            int stock = Integer.parseInt(st.nextToken());

            bw.write(stock / (numberOfGroup + 1) + "\n");
        }

        bw.flush();
        bw.close();
    }
}
