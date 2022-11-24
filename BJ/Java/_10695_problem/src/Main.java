import java.util.*;
import java.io.*;

// 10695 : Dalia

/**
 * example
 * 2
 * 4 1 2 2 4
 * 5 1 1 3 3
 */
public class Main {
    static int[] directionOfX = {2, -2, 2, -2, 1, -1, 1, -1};
    static int[] directionOfY = {-1, -1, 1, 1, -2, -2, 2, 2};

    static boolean canReachEndingPositionFromStartingPosition(int startingOfY, int startingOfX, int endingOfY, int endingOfX) {
        for (int index = 0; index < directionOfX.length; index++) {
            int newOfY = startingOfY + directionOfY[index];
            int newOfX = startingOfX + directionOfX[index];

            if (newOfY == endingOfY && newOfX == endingOfX) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_10695_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int numberOfTestCases = Integer.parseInt(br.readLine());
        for (int testCase = 1; testCase <= numberOfTestCases; testCase++) {
            st = new StringTokenizer(br.readLine());
            st.nextToken();
            if (canReachEndingPositionFromStartingPosition(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()))) {
                bw.write("Case " + testCase + ": YES\n");
            } else {
                bw.write("Case " + testCase + ": NO\n");
            }
        }

        bw.flush();
        bw.close();
    }
}

