import java.util.*;
import java.io.*;

// 5717 : 상근이의 친구들

/**
 * Example
 * 2 2
 * 2 3
 * 5 5
 * 1 1
 * 0 0
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5717_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        while (true) {
            String line = br.readLine();
            if (line.equals("0 0")) {
                break;
            }

            st = new StringTokenizer(line);
            System.out.println(Integer.parseInt(st.nextToken()) + Integer.parseInt(st.nextToken()));
        }
    }
}

