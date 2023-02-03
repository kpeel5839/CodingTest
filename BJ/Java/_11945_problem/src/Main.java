import java.util.*;
import java.io.*;

// 11945 : 뜨거운 붕어빵

/**
 * Example
 * 5 7
 * 0010000
 * 0111010
 * 1111111
 * 0111010
 * 0010000
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_11945_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = line.length() - 1; j != -1; j--) {
                bw.write(line.charAt(j));
            }

            bw.write("\n");
        }

        bw.flush();
        bw.close();
    }
}