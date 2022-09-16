import java.util.*;
import java.io.*;

// 3687 : 성냥개비

/**
 * 예제
 * 4
 * 3
 * 6
 * 7
 * 15
 */
public class Main {
    static int N;
    static int[] matchStick = {6, 2, 5, 5, 4, 5, 6, 3, 7, 6};
    static String[] minDp;
    static String[] maxDp;

    static String findMax(int remain) {
        if (remain == 0) {
            return "";
        }

        if (remain < 0) {
            return null;
        }

        if (maxDp[remain] == null || !maxDp[remain].equals("")) {
            return maxDp[remain];
        }

        for (int i = 0; i <= 9; i++) {
            if (remain == N && i == 0) {
                continue;
            }

            String res = findMax(remain - matchStick[i]);

            if (res != null) {
                maxDp[remain] = max(maxDp[remain], (i + "") + res);
            }
        }

        if (maxDp[remain].length() == 0) {
            maxDp[remain] = null;
        }

        return maxDp[remain];
    }


    static String findMin(int remain) {
        if (remain == 0) {
            return "";
        }

        if (remain < 0) { // null 로 처리하면서 조금 더 간단하게 문제를 변화시켰음
            return null;
        }

        if (minDp[remain] == null || !minDp[remain].equals("")) {
            return minDp[remain];
        }

        for (int i = 0; i <= 9; i++) {
            if (remain == N && i == 0) {
                continue;
            }

            String res = findMin(remain - matchStick[i]);

            if (res != null) {
                minDp[remain] = min(minDp[remain], (i + "") + res);
            }
        }

        if (minDp[remain].length() == 0) { // 아직 공백이면, 이 경우 remain == 0 에 도달하는 방법이 없었던 것으로 판단, 다시는 쓰지 못하게 null 로 변경한다.
            minDp[remain] = null;
        }

        return minDp[remain];
    }

    static String max(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return s1;
        } else if (s1.length() == s2.length()) {
            for (int i = 0; i < s1.length(); i++) {
                int i1 = s1.charAt(i) - '0';
                int i2 = s2.charAt(i) - '0';

                if (i1 == i2) {
                    continue;
                }

                if (i1 > i2) { // i1 이 더 큰경우
                    return s1;
                } else { // i2 가 더 큰 경우
                    return s2;
                }
            }
        } else {
            return s2;
        }

        return s1; // 완전히 같은 경우
    }

    static String min(String s1, String s2) {
        if (s1.length() == 0) { // min 같은 경우는 더 작은 것을 가져가기 때문에 공백과, 작은 숫자가 붙게 되더라도 공백이 이긴다, 그래서 max 와 달리 예외처리를 해줌
            return s2;
        }

        if (s1.length() > s2.length()) {
            return s2;
        } else if (s1.length() == s2.length()) {
            for (int i = 0; i < s1.length(); i++) {
                int i1 = s1.charAt(i) - '0';
                int i2 = s2.charAt(i) - '0';

                if (i1 == i2) {
                    continue;
                }

                if (i1 > i2) { // i1 이 더 큰경우
                    return s2;
                } else { // i2 가 더 큰 경우
                    return s1;
                }
            }
        } else {
            return s1;
        }

        return s1; // 완전히 같은 경우
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_3687_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            N = Integer.parseInt(br.readLine());

            minDp = new String[N + 1];
            maxDp = new String[N + 1];
            Arrays.fill(minDp, "");
            Arrays.fill(maxDp, "");

            bw.write(findMin(N) + " " + findMax(N) + "\n");
        }

        bw.flush();
        bw.close();
    }
}
