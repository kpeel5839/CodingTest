import java.util.*;
import java.io.*;

// 5163 : Isn't it how a bear likes honey?

/**
 * Example
 * 2
 * 2 5000.0
 * 0.0
 * 126.31
 * 5 10000.0
 * 78.0
 * 78.0
 * 78.0
 * 78.0
 * 78.0
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5163_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int testCases = Integer.parseInt(br.readLine());

        for (int index = 0; index < testCases; index++) {
            st = new StringTokenizer(br.readLine());
            int numberOfBalloon = Integer.parseInt(st.nextToken());
            double poohWeight = Double.parseDouble(st.nextToken());

            for (int i = 0; i < numberOfBalloon; i++) {
                poohWeight = poohWeight - (((4d / 3d) * Math.PI * Math.pow(Double.parseDouble(br.readLine()), 3)) / 1000d);
            }

            bw.write("Data Set " + (index + 1) + ":\n");

            if (poohWeight <= 0) {
                bw.write("Yes\n\n");
            } else {
                bw.write("No\n\n");
            }
        }

        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();

        bw.flush();
        bw.close();
    }
}
