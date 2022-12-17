import java.util.*;
import java.io.*;

// 2083 : 럭비클럽

/**
 * Example
 * Joe 16 34
 * Bill 18 65
 * Billy 17 65
 * Sam 17 85
 * # 0 0
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2083_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        while (true) {
            st = new StringTokenizer(br.readLine());

            String name = st.nextToken();
            int height = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            if (name.equals("#") && height == 0 && weight == 0) {
                break;
            }

            bw.write(name + " ");

            if (17 < height || 80 <= weight) {
                bw.write("Senior");
            } else {
                bw.write("Junior");
            }

            bw.write("\n");
        }

        bw.flush();
        bw.close();
    }
}
