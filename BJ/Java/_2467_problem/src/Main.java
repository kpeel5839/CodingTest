import java.util.*;
import java.io.*;

// 2467 : 주사위 게임

/**
 * Example
 * 3
 * 3 3 6
 * 2 2 2
 * 6 2 5
 */
public class Main {
    public static int getReward(int first, int second, int third) {
        int[] diceCount = new int[7];
        int maxCount = 0;
        int maxNumber = 0;
        int maxCountNumber = 0;
        int reward = 0;

        diceCount[first]++;
        diceCount[second]++;
        diceCount[third]++;
        for (int i = 1; i <= 6; i++) {
            maxCount = Math.max(maxCount, diceCount[i]);
            if (2 <= diceCount[i]) {
                maxCountNumber = i;
            }

            if (diceCount[i] != 0) {
                maxNumber = i;
            }
        }

        if (maxCount == 1) {
            reward = maxNumber * 100;
        }

        if (maxCount == 2) {
            reward = 1000 + maxCountNumber * 100;
        }

        if (maxCount == 3) {
            reward = 10000 + maxCountNumber * 1000;
        }

//        System.out.println(Arrays.toString(diceCount));

        return reward;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2467_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int maxReward = 0;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int first = Integer.parseInt(st.nextToken());
            int second = Integer.parseInt(st.nextToken());
            int third = Integer.parseInt(st.nextToken());

            maxReward = Math.max(maxReward, getReward(first, second, third));
        }

        System.out.println(maxReward);
    }
}
