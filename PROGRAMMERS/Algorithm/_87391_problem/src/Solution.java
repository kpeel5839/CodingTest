import java.util.*;
import java.io.*;

/**
 * 되게 재밌던 문제
 * 해맸던 점은
 * 첫쨰, max, min 에 대한 처리를 안해주었었음
 * 둘쨰, x, y 가 x 가 행을, y 가 열을 의미했었음
 * 셋쩨, 내가 그림으로 그려보면서는 체크했던 부분인, 가능하지 않은 경우에 대한 처리를 전혀 하지 않았었음
 */
class Solution {
    public long solution(int n, int m, int x, int y, int[][] queries) {
        long xs = y;
        long xe = y;
        long ys = x;
        long ye = x;

        for (int i = queries.length - 1; i != -1; i--) {
            long dir = (long) queries[i][0];
            long dx = (long) queries[i][1];

            if (dir <= 1) { // 왼쪽 혹은 오른쪽, m = 열
                if (dir == 0) { // 왼쪽
                    if (xs == 0) { // xs 가 0 이라서, 예외인 경우
                        // xs 는 그대로 두고, xe 에다가 + dx 해준다, 근데 이 경우 m - 1 보다 커지면 안됨
                        xe = Math.min(xe + dx, m - 1);
                    } else { // 예외가 아닌 경우
                        xs = xs + dx;
                        xe = xe + dx;

                        if (xs > m - 1 && xe > m - 1) {
                            return 0;
                        }

                        xs = Math.min(xs, m - 1);
                        xe = Math.min(xe, m - 1);
                    }
                } else { // 오른쪽
                    if (xe == (m - 1)) { // xe 가 n - 1 이라서, 예외인 경우
                        // xe 는 그대로 두고, xs 가 0 보다 작아지지 않도록 해줌
                        xs = Math.max(xs - dx, 0);
                    } else { // 예외가 아닌 경우
                        xs = xs - dx;
                        xe = xe - dx;

                        if (xs < 0 && xe < 0) {
                            return 0;
                        }

                        xs = Math.max(xs, 0);
                        xe = Math.max(xe, 0);
                    }
                }
            } else { // 위쪽 혹은 아래 n = 행
                if (dir == 2) { // 위쪽
                    if (ys == 0) { // 예외
                        // ys 는 그대로 두고, ye 가 n - 1 보다 커지지 않도록 해야함
                        ye = Math.min(ye + dx, n - 1);
                    } else { // 아닌 경우
                        ys = ys + dx;
                        ye = ye + dx;

                        if (ys > n - 1 && ye > n - 1) {
                            return 0;
                        }

                        ys = Math.min(ys, n - 1);
                        ye = Math.min(ye, n - 1);
                    }
                } else { // 아래쪽
                    if (ye == (n - 1)) { // 예외
                        ys = Math.max(ys - dx, 0);
                    } else { // 아닌 경우
                        ys = ys - dx;
                        ye = ye - dx;

                        if (ys < 0 && ye < 0) {
                            return 0;
                        }

                        ys = Math.max(ys, 0);
                        ye = Math.max(ye, 0);
                    }
                }
            }

            // System.out.println("(" + xs + ", " + xe + ")");
            // System.out.println("(" + ys + ", " + ye + ")");
        }

        return (xe - xs + 1) * (ye - ys + 1);
    }
}