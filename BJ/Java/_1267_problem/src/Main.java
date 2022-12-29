import java.util.*;
import java.io.*;

// 1267 : 핸드폰 요금

/**
 * Example
 * 3
 * 40 40 40
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1267_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 30 초마다 10원 Y, 60 초 마다 15원 M

        int N = Integer.parseInt(br.readLine());
        int yongSikBill = 0;
        int minSikBill = 0;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            double callRecord = Double.parseDouble(st.nextToken()) + 1;

            yongSikBill = yongSikBill + (int) (Math.ceil(callRecord / 30d) * 10);
            minSikBill = minSikBill + (int) (Math.ceil(callRecord / 60d) * 15);
        }

        if (yongSikBill < minSikBill) {
            System.out.println("Y " + yongSikBill);
        } else if (yongSikBill > minSikBill) {
            System.out.println("M " + minSikBill);
        } else {
            System.out.println("Y " + "M " + yongSikBill);
        }
    }
}

