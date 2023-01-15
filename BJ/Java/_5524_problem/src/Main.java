import java.util.*;
import java.io.*;

// 5524 : 입실 관리

/**
 * Example
 * 3
 * WatanabE
 * ITO
 * YamaMoto
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5524_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < line.length(); j++) {
                bw.write(Character.toLowerCase(line.charAt(j)));
            }

            bw.write("\n");
        }

        bw.flush();
        bw.close();
    }
}
