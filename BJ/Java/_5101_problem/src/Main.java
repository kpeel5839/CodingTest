import java.util.*;
import java.io.*;

// 5101 : sequences

/**
 * example
 * 3 2 11
 * -1 -3 -8
 * 0 0 0
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5101_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        while (true) {
            st = new StringTokenizer(br.readLine());

            int firstNumber = Integer.parseInt(st.nextToken());
            int difference = Integer.parseInt(st.nextToken());
            int findNumber = Integer.parseInt(st.nextToken());

            if (firstNumber == 0 && difference == 0 && findNumber == 0) {
                break;
            }

            // findNumber - firstNumber 를 통해서 차를 구하고, difference 로 나눈다. 근데 만일 difference == 0 이거나, 혹은 나누어 떨어지지 않는다면 x, 아니라면 나눈 값 + 1을 출력해주면 된다.

            if (difference == 0 || (findNumber - firstNumber) % difference != 0 || (findNumber - firstNumber) * difference < 0) {
                bw.write("X\n");
            } else {
                bw.write(((findNumber - firstNumber) / difference + 1) + "\n");
            }
        }

        bw.flush();
        bw.close();
    }
}
