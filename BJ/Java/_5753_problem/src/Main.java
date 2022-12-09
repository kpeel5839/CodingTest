import java.util.*;
import java.io.*;

// 5753 : Pascal Library

/**
 * Example
 * 3 3
 * 1 1 1
 * 0 1 1
 * 1 1 1
 * 7 2
 * 1 0 1 0 1 0 1
 * 0 1 0 1 0 1 0
 * 0 0
 */
public class Main {
    public static final String YES = "yes";
    public static final String NO = "no";
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5753_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        while (true) {
            st = new StringTokenizer(br.readLine());
            int numberOfStudent = Integer.parseInt(st.nextToken());
            int numberOfDinners = Integer.parseInt(st.nextToken());

            if (numberOfStudent == 0 && numberOfDinners == 0) {
                break;
            }

            boolean[] isAttend = new boolean[numberOfStudent];
            Arrays.fill(isAttend, true);

            for (int i = 0; i < numberOfDinners; i++) {
                st = new StringTokenizer(br.readLine());

                for (int j = 0; j < numberOfStudent; j++) {
                    int studentNumber = Integer.parseInt(st.nextToken());

                    if (studentNumber == 0) {
                        isAttend[j] = false;
                    }
                }
            }

            boolean isExistAttendAllDinner = false;

            for (int i = 0; i < isAttend.length; i++) {
                if (isAttend[i]) {
                    isExistAttendAllDinner = true;
                }
            }

            if (isExistAttendAllDinner) {
                bw.write(YES + "\n");
            } else {
                bw.write(NO + "\n");
            }
        }

        bw.flush();
        bw.close();
    }
}
