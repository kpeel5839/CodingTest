import java.util.*;
import java.io.*;

// 2863 : 이게 분수?

/**
 * Example
 * 1 2
 * 3 4
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2863_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        double[] arr = new double[4];
        for (int i = 0; i < 2; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 2; j++) {
                arr[i * 2 + j] = Integer.parseInt(st.nextToken());
            }
        }

        int maxIndex = 0;
        double max = arr[0] / arr[2] + arr[1] / arr[3]; // 0
        if (max < arr[2] / arr[3] + arr[0] / arr[1]) {
            max = arr[2] / arr[3] + arr[0] / arr[1];
            maxIndex = 1;
        }

        if (max < arr[1] / arr[0] + arr[3] / arr[2]) {
            max = arr[1] / arr[0] + arr[3] / arr[2];
            maxIndex = 3;
        }

        if (max < arr[3] / arr[1] + arr[2] / arr[0]) {
            max = arr[3] / arr[1] + arr[2] / arr[0];
            maxIndex = 2;
        }

        System.out.println(maxIndex);
    }
}