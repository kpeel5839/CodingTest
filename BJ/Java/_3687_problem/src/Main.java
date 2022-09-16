import java.math.BigInteger;
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
    static String[] maxDp;
    static String[] minDp;

    static String findMax(int remain) {
        if (remain <= 0) {
            if (remain == 0) {
                return "Y";
            } else {
                return "N";
            }
        }

        if (maxDp[remain] != null) {
            return maxDp[remain];
        }

        for (int i = 0; i <= 9; i++) {
            if (remain == N && i == 0) {
                continue;
            }

            String res = findMax(remain - matchStick[i]);

            if (res.charAt(res.length() - 1) == 'Y') {
                maxDp[remain] = max(maxDp[remain], (i + "") + res);
            }
        }

        if (maxDp[remain] == null) {
            maxDp[remain] = "N";
        }

        return maxDp[remain];
    }

    static String findMin(int remain) {
        if (remain <= 0) {
            if (remain == 0) {
                return "Y";
            } else {
                return "N";
            }
        }

        if (minDp[remain] != null) {
            return minDp[remain];
        }

        for (int i = 0; i <= 9; i++) {
            if (remain == N && i == 0) {
                continue;
            }

            String res = findMin(remain - matchStick[i]);

            if (res.charAt(res.length() - 1) == 'Y') {
                minDp[remain] = min(minDp[remain], (i + "") + res);
            }
        }

        if (minDp[remain] == null) {
            minDp[remain] = "N";
        }

        return minDp[remain];
    }

    static String max(String s1, String s2) {
        if (s1 == null) {
            return s2;
        }

        BigInteger i1 = new BigInteger(s1.substring(0, s1.length() - 1));
        BigInteger i2 = new BigInteger(s2.substring(0, s2.length() - 1));

        if (i1.max(i2).equals(i1)) {
            if (s1.charAt(0) == '0') {
                return "0" + i1 + "Y";
            }
        } else {
            if (s2.charAt(0) == '0') {
                return "0" + i2 + "Y";
            }
        }

        return i1.max(i2) + "Y"; // 이렇게 반환하면 될 것 같다.
    }

    static String min(String s1, String s2) {
        if (s1 == null) {
            return s2;
        }

        BigInteger i1 = new BigInteger(s1.substring(0, s1.length() - 1));
        BigInteger i2 = new BigInteger(s2.substring(0, s2.length() - 1));

        if (i1.min(i2).equals(i1)) {
            if (s1.charAt(0) == '0') {
                return "0" + i1 + "Y";
            }
        } else {
            if (s2.charAt(0) == '0') {
                return "0" + i2 + "Y";
            }
        }

        return i1.min(i2) + "Y"; // 이렇게 반환하면 될 것 같다.
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_3687_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            N = Integer.parseInt(br.readLine());

            maxDp = new String[N + 1];
            minDp = new String[N + 1];
            String minString = findMin(N);
            String maxString = findMax(N);

            bw.write(minString.substring(0, minString.length() - 1) + " " + maxString.substring(0, maxString.length() - 1) + "\n");
        }

        bw.flush();
        bw.close();
    }
}
