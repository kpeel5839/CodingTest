import java.util.*;
import java.io.*;

// 10797 : 10부제

/**
 * Example
 * 1
 * 1 2 3 4 5
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_10797_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int numberOfIllegalCar = 0;
        st = new StringTokenizer(br.readLine());
        while (st.hasMoreTokens()) {
            String carNumber = st.nextToken();
            if ((carNumber.charAt(carNumber.length() - 1) - '0') == N) {
                numberOfIllegalCar++;
            }
        }

        System.out.println(numberOfIllegalCar);
    }
}