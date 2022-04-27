import java.util.*;
import java.io.*;
import java.util.function.Function;

// 17386 : 선분 교차 1

/**
 * -- 전제 조건
 * 이전 문제들 처럼, 선분이 주어지면, 선분이 교차하는지 빠르게 계산하면 된다.
 * 그냥 선분이 교차하면 1, 아니면 0이다.
 * -- 틀 설계
 * ccw 를 이용해서, p123, p124와, p341, p342 가 서로 방향이 반대방향이면?
 * 그러면 교차한다라고 단정지을 수 이쏙, 아니면 교차하지 않는 경우다.
 * 세 점이 일직선에 놓여있는 상황은 고려할 필요가 없으니까,
 * 즉 두 선분이 교차한 경우는, 일직선 상에 존재하는, 즉 평행하여서 교차하는 경우는 존재하지 않는다.
 *
 * 그렇기 때문에, 그냥 ccw 와 선분교차 알고리즘을 사용하면 된다.
 *
 * -- 해맸던 점
 * 그냥 귀찮아가지고, angle 1, -1 으로 안바꾸고, 그냥 내보냈는데, OverFlow 나가지고, 계속 틀렸었음
 * 귀찮아서 꽤 오래날림
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Long> fun = Long::parseLong;

        String[] input = br.readLine().split(" ");
        long[][] points = new long[4][2];

        points[0][0] = fun.apply(input[0]);
        points[0][1] = fun.apply(input[1]);
        points[1][0] = fun.apply(input[2]);
        points[1][1] = fun.apply(input[3]);

        input = br.readLine().split(" ");

        points[2][0] = fun.apply(input[0]);
        points[2][1] = fun.apply(input[1]);
        points[3][0] = fun.apply(input[2]);
        points[3][1] = fun.apply(input[3]);

        System.out.println(checkCross(points));
    }

    public static long checkCross(long[][] points) {
        long p123 = ccw(points[0][0], points[0][1], points[1][0], points[1][1], points[2][0], points[2][1]);
        long p124 = ccw(points[0][0], points[0][1], points[1][0], points[1][1], points[3][0], points[3][1]);
        long p341 = ccw(points[2][0], points[2][1], points[3][0], points[3][1], points[0][0], points[0][1]);
        long p342 = ccw(points[2][0], points[2][1], points[3][0], points[3][1], points[1][0], points[1][1]);

        long result = 0;

        // 선분이 교차하는 경우는 딱 한가지 경우임, 둘다 음수인 경우 (세점이 일직선 상에 존재하는 경우가 없기에)
        if (p123 * p124 < 0 && p341 * p342 < 0) {
            result = 1;
        }

        return result;
    }

    // 범위를 벗어나서, overflow 가 나는 현상을 방지하기 위해서, long 으로 다 선언
    public static long ccw(long x1, long y1, long x2, long y2, long x3, long y3) {
        long angle = ((x1 * y2) + (x2 * y3) + (x3 * y1)) - ((y1 * x2) + (y2 * x3) + (y3 * x1));
        return angle < 0 ? -1 : (angle > 0 ? 1 : angle);
    }
}
