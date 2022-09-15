import java.util.*;
import java.io.*;

// 1028 : 다이아몬드 광산

/**
 * 예제
 * 5 5
 * 01100
 * 01011
 * 11111
 * 01111
 * 11111
 */
public class Main {
    static int R;
    static int C;
    static int ans = 0;
    static int[][] left;
    static int[][] right;
    static int[][] map;

    static boolean outOfRange(int y, int x) {
        if (y < 0 || y >= R || x < 0 || x >= C) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1028_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        left = new int[R][C];
        right = new int[R][C];
        map = new int[R][C];

        for (int i = 0; i < R; i++) {
            String string = br.readLine();
            for (int j = 0; j < C; j++) {
                map[i][j] = string.charAt(j) - '0';
            }
        }

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] == 1) {
                    int y = i - 1;
                    int xLeft = j - 1;
                    int xRight = j + 1;

                    if (!outOfRange(y, xLeft) && map[y][xLeft] == 1) {
                        left[i][j] = left[y][xLeft] + 1;
                    }

                    if (!outOfRange(y, xRight) && map[y][xRight] == 1) {
                        right[i][j] = right[y][xRight] + 1;
                    }
                }
            }
        }

//        for (int c = 0; c < 2; c++) {
//            System.out.println(c == 0 ? "Left" : "right");
//            for (int i = 0; i < R; i++) {
//                for (int j = 0; j < C; j++) {
//                    System.out.print((c == 0 ? left[i][j] : right[i][j]) + " ");
//                }
//                System.out.println();
//            }
//        }

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] == 1) {
                    for (int k = 0; k <= Math.min(left[i][j], right[i][j]); k++) {
                        boolean diamond = false; // 다이아몬드가 맞으면 true 로 남아있음

                        int y = i - k;
                        int xLeft = j - k;
                        int xRight = j + k;

                        if (k <= right[y][xLeft] && k <= left[y][xRight]) {
                            diamond = true;
                        }

                        if (diamond) {
                            ans = Math.max(ans, k + 1);
                        }
                    }
                }
            }
        }

        System.out.println(ans);
    }
}
