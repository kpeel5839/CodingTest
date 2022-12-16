import java.util.*;
import java.io.*;

// 5666 : Hot Dogs

/**
 * Example
 * 10 90
 * 840 11
 * 1 50
 * 33 1000
 * 34 1000
 * 36 1000
 * 37 1000
 * 1 1000
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5666_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        while (true) {
            String input = br.readLine();

            if (input == null) {
                break;
            }

            st = new StringTokenizer(input);
            double numberOfHotDog = Double.parseDouble(st.nextToken());
            double numberOfParticipant = Double.parseDouble(st.nextToken());
            double average = numberOfHotDog / numberOfParticipant;
            average = Math.round(average * 100) / 100d;
            bw.write(String.format("%.2f", average) + "\n");
        }

        bw.flush();
        bw.close();
    }
}
