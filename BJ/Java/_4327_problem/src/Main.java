import java.util.*;
import java.io.*;

// 4327 : Combination Lock

/**
 * Example
 * 0 30 0 30
 * 5 35 5 35
 * 0 20 0 20
 * 7 27 7 27
 * 0 10 0 10
 * 9 19 9 19
 * 0 0 0 0
 */
public class Main {

    public static final int CLOCKWISE = -1;
    public static final int COUNTER_CLOCKWISE = 1;
    public static final int DEGREE_BETWEEN_DIAL = 9;

    public static int calculateEdge(int value) {
        return (value == -1) ? 39 :
                (value == 40) ? 0 : value;
    }

    public static int getNumberOfRotate(int startDial, int destinationDial, int direction) {
        // direction is -1 or 1
        int numberOfRotate = 0;

        while (true) {
            if (startDial == destinationDial) {
                break;
            }

            numberOfRotate++;
            startDial = calculateEdge(startDial + direction);
        }

        return numberOfRotate * DEGREE_BETWEEN_DIAL;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_4327_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        /**
         * 1. 시계방향으로 시작 위치로부터 2바퀴 돌림
         * 2. 시계방향으로 첫번째 숫자 나올 때까지
         * 3. 시계반댓 방향으로 1바퀴 돌림
         * 4. 두번째 숫자 나올때까지 시계 반대로
         * 5. 세번째 숫자 나올때까지 시계 제대로
         * 6. 참고로 시계 방향으로 돌 때 순서대로 0 -> 39 -> 38 ... 이렇게임, 이거 생각하다가 개오래 생각함
         */

        while (true) {
            st = new StringTokenizer(br.readLine());
            int startDial = Integer.parseInt(st.nextToken());
            int firstDial = Integer.parseInt(st.nextToken());
            int secondDial = Integer.parseInt(st.nextToken());
            int thirdDial = Integer.parseInt(st.nextToken());

            if (startDial == 0 && firstDial == 0 && secondDial == 0 && thirdDial == 0) {
                break;
            }

            int totalDegree = 360 * 3;
            // 수학적으로 말고 그냥 기계적으로 처리하는 것이 빠를 듯
            // 시계 방향은 -, 반시계 방향은 +임, 40이면 0, 0이면 40 으로 넘겨주는 것이 필요함

            // first, clockwise 처리
            totalDegree += getNumberOfRotate(startDial, firstDial, CLOCKWISE);
            totalDegree += getNumberOfRotate(firstDial, secondDial, COUNTER_CLOCKWISE);
            totalDegree += getNumberOfRotate(secondDial, thirdDial, CLOCKWISE);

            bw.write(totalDegree + "\n");
        }

        bw.flush();
        bw.close();
    }
}
