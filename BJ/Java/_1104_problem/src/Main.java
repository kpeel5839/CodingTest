import java.util.*;
import java.io.*;

// 1014 : 컨닝

/**
 * 예제
 * 4
 * 2 3
 * ...
 * ...
 * 2 3
 * x.x
 * xxx
 * 2 3
 * x.x
 * x.x
 * 10 10
 * ....x.....
 * ..........
 * ..........
 * ..x.......
 * ..........
 * x...x.x...
 * .........x
 * ...x......
 * ........x.
 * .x...x....
 */
public class Main {
    static int N;
    static int H;
    static int W;
    static int[] bitCnt;
    static char[][] map;
    static int[][] dp;
    static List<Integer> bitSet = new ArrayList<>();

    static boolean checkX(int row, int bit) {
        for (int i = 0; i < W; i++) {
            if ((bit & (1 << i)) != 0 && map[row][i] == 'x') {
                return false;
            }
        }

        return true;
    }

    static boolean checkAdj(int bit) {
        for (int i = 0; i < W - 1; i++) {
            if ((bit & (1 << i)) != 0 && (bit & (1 << (i + 1))) != 0) {
                return false;
            }
        }

        return true;
    }

    static void countBit(int bit) { // 여기서 bitCnt 값도 채워줄 것임
        int cnt = 0;

        for (int i = 0; i < W; i++) {
            if ((bit & (1 << i)) != 0) {
                cnt++;
            }
        }

        bitCnt[bit] = cnt;
    }

    static boolean possible(int frontBit, int bit) {
        for (int i = 0; i < W; i++) {
            if ((frontBit & (1 << i)) != 0) {
                if (i == 0) {
                    if ((bit & (1 << (i + 1))) != 0) {
                        return false;
                    }
                } else if (i == W - 1){
                    if ((bit & (1 << (i - 1))) != 0) {
                        return false;
                    }
                } else {
                    if ((bit & (1 << (i + 1))) != 0) {
                        return false;
                    }

                    if ((bit & (1 << (i - 1))) != 0) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1104_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());

            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());

            dp = new int[1 << W][H];
            map = new char[H][W];
            bitCnt = new int[1 << W];
            bitSet = new ArrayList<>();

            int ans = 0;

            for (int i = 0; i < H; i++) {
                String string = br.readLine();
                for (int j = 0; j < W; j++) {
                    map[i][j] = string.charAt(j);
                }
            }

            for (int i = 0; i < 1 << W; i++) {
                if (checkAdj(i)) { // 올바른 bit 인지
                    countBit(i);
                    bitSet.add(i);

                    if (checkX(0, i)) {
                        dp[i][0] = bitCnt[i]; // 초기화
                        ans = Math.max(ans, dp[i][0]);
                    }
                }
            }

//            System.out.println(T + " H : " + H + " W : " + W);
            for (int i = 1; i < H; i++) {
                for (int bit : bitSet) {
                    if (checkX(i, bit)) {
                        for (int frontBit : bitSet) {
                            if (possible(frontBit, bit)) { // 앞에 front 배치는 이러하고, 현재 bit 는 이러할 때 가능한지
                                // 가능하다면, 최대값으로 갱신하기 위해서 연산을 진행해준다.
                                dp[bit][i] = Math.max(dp[bit][i], dp[frontBit][i - 1] + bitCnt[bit]);
                                ans = Math.max(ans, dp[bit][i]);
                            }
                        }
                    }
                }
            }

            bw.write(ans + "\n");
        }

        bw.flush();
        bw.close();
    }
}
