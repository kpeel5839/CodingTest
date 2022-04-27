import java.io.*;
import java.util.function.*;

// 17387 : 선분교차2

/*
-- 전제조건
포인터 P1, P2, P3, P4 가 주어진다.
P1, P2를 이은 선분과, P3, P4를 이은 선분이 교차하면 1, 아니면 0을 반환하라.
-- 틀설계
ccw 와 그에 대한 예외처리를 이용해서 구하는 문제이다.

notion 에다가 자세하게 정리를 해놓았음

17387 선분교차 2 (notion)
https://www.notion.so/17387-2-12b60034c1ee422fb29c785c2dc4ffa0
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // String -> Integer 해주는 function 저장 , 앞으로 Integer.parseInt 이런식으로 하지말고 그냥 fun.apply(input[0]) 이런식으로 진행하면 된다.
        Function<String , Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");

        long x1 = fun.apply(input[0]);
        long y1 = fun.apply(input[1]);
        long x2 = fun.apply(input[2]);
        long y2 = fun.apply(input[3]); // L1 입력 받음

        input = br.readLine().split(" ");

        long x3 = fun.apply(input[0]);
        long y3 = fun.apply(input[1]);
        long x4 = fun.apply(input[2]);
        long y4 = fun.apply(input[3]); // L2 입력 받음

        System.out.println(checkCcw(x1, x2, x3, x4, y1, y2, y3, y4));
    }

    // ccw 를 확인하여서, 선분이 교차하는지 안하는지를 확인하는 method
    public static int checkCcw(long x1, long x2, long x3, long x4,
                               long y1, long y2, long y3, long y4) {
        /*
        두개의 조건으로 할 수 있을 것 같다.
        A ≤ C ≤ B, or A ≤ D ≤ B 이거나,
        C ≤ A ≤ D, or C ≤ B ≤ D 여기 조건에 걸리면 무조건적으로 result = 0 이지 않을까?

        result = 0 으로 시작을 하고, 교차하는 경우에만 1로 바꿔주는 방식으로 진행하자.

        2 -> 3, 2 -> 4 와 4 -> 1, 4 -> 2 의 방향을 알면 된다.
        그래서, 서로 방향이 다르거나, ccw123, ccw124 와 ccw341, ccw342 에 0 이 포함되어 있더라도, 무조건 교차하는 것이다.

        근데 위에 조건처럼 하려고 해보니까, 이게 2차원 존재라는 것을 까먹고 있었음..
        그래서, 아얘 그냥 교차되는 경우를 제외하고, 교차하지 않는다고 단언할 수 있는데,
        그 경우는
        A - C - B - D 이런식으로 존재한다고 했을 때, 두개가 겹치려면 무조건 맞아야 하는 경우는, 이것이다.
        A 는 일단 D 보다, 작거나 같아야 한다. (한 점에 아얘 다 모여있는 경우도 생각해야 함, 점들이 겹쳐있는 경우도 생각해야 하고)
        그리고, 그리고, C 는 B 보다 작아야 한다.
        하지만, 우리는 B - D - A - C 이런식으로 이루어져 있는지 정확히 알지 못한다.
        그렇기 때문에, 포인터에 붙혀놨던 이름은 잠시 잊어두고,
        그냥 두개의 점 중에, 더 작은 것이 A, C 그리고 더 큰 것은 B, D 라고 가정하고, 진행하면 된다.
        전혀 문제될 게 없다.

        그렇기 때문에, 저런식으로 고려하고, x, y 둘다 고려해주기만 하면 된다.
         */
        int result = 0;
        int p123 = ccw(x1, y1, x2, y2, x3, y3);
        int p124 = ccw(x1, y1, x2, y2, x4, y4);
        int p341 = ccw(x3, y3, x4, y4, x1, y1);
        int p342 = ccw(x3, y3, x4, y4, x2, y2);

        // 같다도 포함해야 함
        // A 가 D 보다 작다라는 것을 의미
        boolean compare1 = Math.min(x1, x2) <= Math.max(x3, x4);
        // C 가 B 보다 작다라는 것을 의미 (x 값)
        boolean compare2 = Math.min(x3, x4) <= Math.max(x1, x2);
        // A 가 D 보다 작다라는 것을 의미
        boolean compare3 = Math.min(y1, y2) <= Math.max(y3, y4);
        // C 가 B 보다 작다라는 것을 의미 (y 값)
        boolean compare4 = Math.min(y3, y4) <= Math.max(y1, y2);

        // 먼저, 4점이 모두 일직선 상에 존재하는 경우
        if ((p123 == 0 && p124 == 0) && (p341 == 0 && p342 == 0)) {
            // 이 경우에는, 무조건적으로 위에서 명시한 범위에 들어야지 교차하는 것임
            if((compare1 && compare2) && (compare3 && compare4)) {
                result = 1;
            }
        } else if (((p123 * p124) <= 0) && ((p341 * p342) <= 0)) {
            // 이 경우는 선분이 무조건적으로 교차함
            result = 1;
        }

        return result;
    }

    // ccw 를 확인해서, 방향을 주는데, -1 == 시계방향, 0 == 일직선, 1 == 반시계방향 이다.
    public static int ccw(long x1, long y1, long x2, long y2, long x3, long y3) {
        long angle = ((x1 * y2) + (x2 * y3) + (x3 * y1)) - ((y1 * x2) + (y2 * x3) + (y3 * x1));

        // angle 이 음수이면, 시계방향, 양수이면, 반시계방향, 아니면 0을 주어 일직선임을 명시한다.
        return (int) (angle < 0 ? -1 : (angle > 0 ? 1 : angle));
    }
}
