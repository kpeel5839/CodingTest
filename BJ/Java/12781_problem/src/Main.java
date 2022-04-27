import java.util.*;
import java.io.*;
import java.util.function.Function;

// 12781 : PIZZA ALVOLOC

/**
 * -- 전제 조건
 * 피자를 가르는데, 점을 4개를 선택한다.
 * 그리고서, 순서대로 a, b, c, d 선분을 선택을 하는데,
 * 이 때, 피자가 4조각으로 나뉘어 지는지 판단하는 프로그램을 작성해라.
 * -- 틀 설계
 * 그냥, 이전에 풀었던 선분 교차 2 에서 훨씬 쉬운 문제라고 볼 수 있을 것 같다.
 * 이전에, 선분 교차 2 에서는, 두 점이 한 곳에 위치할 때의 경우인, 그리고, 4점이 일직선일 때의 경우인, 예외사항들이 존재하였는데
 * 이거는 그런것이 없음
 *
 * 즉, ccw 를 각각 구해서, 그 값이 곱하였을 떄, 같은 경우도 아니고, 무조건 적으로 음수인 경우
 * 즉, 서로 방향이 다른 경우만 가져가면 된다.
 *
 * 그래서 p123, p124, p341, p342 이런 식으로 ccw 를 구한 다음에,
 * 이것들의 곱을 보고서 결정하면 된다.
 *
 * 애초에, 하나의 곱이 음수라면? 그러면, 다른 곱도 음수일 수 밖에 없다.
 * 근데, 그냥 그대로 가져가보자.
 *
 * -- 결론
 * 아주 쉽게 맞았음
 * 생각했던 것이 맞았음
*/
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        int[][] points = new int[4][2];

        for (int i = 0; i < 4; i++) {
            points[i][0] = fun.apply(input[i * 2]);
            points[i][1] = fun.apply(input[(i * 2) + 1]);
        }

        System.out.println(checkCross(points));
    }

    public static int checkCross(int[][] points) {
        /**
         * Point 들이 교차하면, 1을 반환 (4조각 가능)
         * 아니면, 0을 반환하면 된다.
         */
        int p123 = ccw(points[0][0], points[0][1], points[1][0], points[1][1], points[2][0], points[2][1]);
        int p124 = ccw(points[0][0], points[0][1], points[1][0], points[1][1], points[3][0], points[3][1]);
        int p341 = ccw(points[2][0], points[2][1], points[3][0], points[3][1], points[0][0], points[0][1]);
        int p342 = ccw(points[2][0], points[2][1], points[3][0], points[3][1], points[1][0], points[1][1]);

        if ((p123 * p124 < 0) && (p341 * p342 < 0)) { // 교차하는 경우, 즉 방향이 반대인 경우
            return 1;
        } else { // 교차하지 않는 경우, 일직선 상에 존재하거나, 방향이 동일한 경우
            return 0;
        }
    }

   public static int ccw(int x1, int y1, int x2, int y2, int x3, int y3) {
        int angle = ((x1 * y2) + (x2 * y3) + (x3 * y1)) - ((y1 * x2) + (y2 * x3) + (y3 * x1));

        return angle < 0 ? -1 : (angle > 0 ? 1 : angle);
    }
}
