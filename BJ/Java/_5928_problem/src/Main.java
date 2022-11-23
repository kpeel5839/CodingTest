import java.util.*;
import java.io.*;

// 5928 : Contest Timing

/**
 * example
 * 12 13 14
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5928_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int minuteOfOneDay = 60 * 24;
        int minuteOfOneHour = 60;
        int standardMinuteOfTime = minuteOfOneDay * 11 + minuteOfOneHour * 11 + 11;
        int inputMinuteOfTime = (Integer.parseInt(st.nextToken()) * minuteOfOneDay)
                + (Integer.parseInt(st.nextToken()) * minuteOfOneHour) + Integer.parseInt(st.nextToken());

        if (inputMinuteOfTime < standardMinuteOfTime) {
            System.out.println(-1);
        } else {
            System.out.println(inputMinuteOfTime - standardMinuteOfTime);
        }
    }
}
