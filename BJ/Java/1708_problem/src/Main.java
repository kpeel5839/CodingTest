import java.util.*;
import java.io.*;
import java.util.function.Function;

// 1708 : 블록껍질

/**
 * -- 전제조건
 * 다각형은 내각 모두가 180도 이하인 도형이다.
 * 그리고, 볼록 다각형은, 두 꼭짓점을 연결하는 선분이 항상 다각형 내부에 존재하는 다각형을 볼록 다각형이라고 한다.
 *
 * 그래서 2차원 평면에 N 개의 점이 주어졌을 때, 이들 중 몇개의 점을 골라 볼록 다각형을 만들어라.
 * 그리고 그 점의 개수를 출력하시오.
 * -- 틀 설계
 * 당연히 기하학 플레 문제이니까 바로 답을 봤다.
 * 일단 먼저 입력을 받아준다.
 * Point class 를 만들어서 받아준다.
 *
 * 그리고서 기준점은 Y 값이 가장 작은놈으로 담아주고,
 * 그 다음에 Y 값이 같다라면 X 값이 더 작은 애를 기준으로 한다.
 *
 * 그리고서, 시계 반대 방향으로 Point 들을 전부 Sorting 해준다.
 * 이것은 ccw 연산을 이용하면 된다.
 *
 * 만일 두개의 pointer 가 방향이 같다라고 하면 기준점에서 더 가까운 것을 우선적으로 정렬 한다.
 *
 * 그 다음에, root 를 Stack 에다가 담아주고
 * while 문으로 stack.size > 1 && ccw 값이 반시계 방향일 때까지 진행한다, 즉 <= 0 이라면 계속 진행해야 하는 것이다.
 *
 * 그러고서 while 문을 끝내주고 stack pop 해준다.
 * 그 다음에 마지막에 stack 의 size 를 반환한다.
 *
 * 일단 꽤 오래 봐버려서 flow 를 그냥 얼떨결에 외워버렸는데
 * 이 문제를 푼 이후에 알고리즘을 notion 에다가 정리하자
 */
public class Main {
    static List<Point> points = new ArrayList<>();
    static Point root = new Point(Long.MAX_VALUE, Long.MAX_VALUE); // 루트값을 찾기 위해서 max 값으로 채워넣음

    static class Point {
        long y;
        long x;

        Point(long y, long x) {
            this.y = y;
            this.x = x;
        }
    }

    static int solve() {
        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).y < root.y) { // y 값이 작은 것을 정할 것임
                root = points.get(i);
            } else if (points.get(i).y == root.y) { // 같은 경우
                if (points.get(i).x < root.x) {
                    root = points.get(i);
                }
            }
        } // 루트 값은 정함

        Collections.sort(points, (p1, p2) -> {
            int result = ccw(root, p1, p2);

            if (result > 0) { // 반시계 방향이라는 것이다. 그러면 p1 과 p2 의 관계가 반시계 관계인 것
                return -1;
            } else if (result < 0) { // 이러면 p1, p2 가 아닌 p2, p1 이 반시계
                return 1;
            } else { // 같은 경우는 평행이니 distance 를 보고 비교
                long dist1 = distance(root, p1);
                long dist2 = distance(root, p2);

                if (dist1 > dist2) {
                    return 1;
                }

                return -1;
            }
        });

        Stack<Point> stack = new Stack<>();
        stack.add(root);

        for (int i = 1; i < points.size(); i++) {
            while (stack.size() > 1 && ccw(stack.get(stack.size() - 2), stack.get(stack.size() - 1), points.get(i)) <= 0) { // 반시계 방향이 나올때까지, stack pop 해준다.
                stack.pop();
            }

            stack.add(points.get(i));
        }

        return stack.size();
    }

    static int ccw(Point p1, Point p2, Point p3) {
        long angle = ((p1.x * p2.y) + (p2.x * p3.y) + (p3.x * p1.y))
                - ((p1.y * p2.x) + (p2.y + p3.x) + (p3.y + p1.x));

        return (angle == 0) ? 0 // 0 이면 평행
                : (angle > 0) ? 1 : -1; // angle 이 양수이면 반시계, 아니면 시계 방향
    }

    static long distance(Point p1, Point p2) {
        // 굳이 루트로 나눠서 반환하지 않을 것이다. 그냥 sqrt 하지말고 보내자, 절댓값 할 필요도 x, 그냥 제곱을 두번 곱해서 해주자.
        return ((p2.y - p1.y) * (p2.y - p1.y)) + ((p2.x - p1.x) * (p2.x - p1.x));
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        int N = fun.apply(br.readLine());

        for (int i = 0; i < N; i++) {
            String[] input = br.readLine().split(" ");
            int a = fun.apply(input[0]);
            int b = fun.apply(input[1]);

            points.add(new Point(b, a)); // a 가 x 값인데, 솔직히 딱히 그것은 중요하지 않다, 어떤 것을 내가 y 값으로 치는지가 중요하다.
        }

        System.out.println(solve());
    }
}
