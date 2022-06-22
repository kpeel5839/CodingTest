import java.util.*;
import java.io.*;
import java.util.function.Function;

// 9240 : 로버트 후드

/**
 * -- 전제조건
 * 화살을 마구잡이로 쏜다.
 * 그리고 점수를 그냥 가장 먼 화살 두개의 거리로 나타낸다.
 *
 * 근데 화살의 개수가 무려 10만 개이다.
 * 이 때 가장 먼 화살 두개의 거리를 구하시오.
 *
 * -- 틀 설계
 * 당연히 2차원 좌표이기에 피타고라스 공식을 사용하면 된다.
 *
 * 근데 어떻게 해야할까 생각을 해봤다.
 * 당연히 10만개를 무지성으로 비교하면 쉽게 답을 구할 수 있다
 * 하지만, 시간내에 절대로 해결하지 못한다.
 *
 * 그래서 어떻게 할까라는 생각을 했다.
 * 근데 이전에 풀었던 블록껍질과 같은 카테고리를 가지고 있다라는 것을 확인했고
 * 그 방향에 집중해서 생각해봤다
 *
 * 근데 생각해보니까 당연히 화살이 있다라고 하면 가장 먼 거리에 있는 것들은 가장 외곽의 화살들이다.
 *
 * 즉 화살들을 가지고 다각형을 구성한 뒤
 * 다각형 내에 있는 화살들을 다 무시한다라는 마인드로 접근하면 된다.
 *
 * 그래서, 생각을 해보았다.
 * 예를 들어서 4개의 점이 있고 이루어진 다각형은 삼각형이라고 가정해보자.
 *
 * 그러면 내부의 점과 아무 점이랑 이어봐도 절대 최대 거리가 나올 수가 없다.
 * 당연한 것이다 원의 경우로 생각해보았을 때, 반지름이 크겠나 지름이 크겠나
 * 당연한 결과이다.
 * 쨌든 다각형 내에 존재하기에 절대로 클 수 없다.
 *
 * 그러면 또 하나 생각을 해보자.
 * 그러면 같은 일직선 상에 있는 점들을 포함해야 할까?
 *
 * 그것도 절대 아니다
 *
 * 생각을 많이 해보았다.
 * 내 머릿속으로 상상했던 다각형은 이등변 삼각형이였다.
 *
 * 우리가 흔하게 많이들 그리는
 *    1
 *  1  1
 * 1 1 1
 *
 * 이런 다각형이 생성됬다고 해보자.
 * 그러면 당연히 끝점들만 포함할 것이다
 *
 * 즉, 다각형을 이루는 꼭짓점들만이 포함될 것이다.
 * 그 입장에서 생각해보면 내부의 점들은 위에서 말했든 생각하지말고
 * 일직선상 존재하여서 외곽에 존재하는 point 이지만 도형을 이루는 점에 포함되지 않은 점들을 생각하고,
 * 그 점들을 이용해서 최대 거리를 만들어보려고 해보면
 * 항상 대안이 있는 것을 볼 수 있다
 *
 * 대안이 있을 수도 있고 혹은 무조건 그것보다 더 나은 방안이 있는 경우다
 *
 * 그렇기 때문에 평행한 점들을 포함시키지 않아도 된다라는 생각이 들었고,
 * 또한 문제의 특성상 효율적으로 10만개의 점들 중 최대거리를 찾는 것이기에
 *
 * 분명히 악의적인 테케가 있을 것이라고 생각했다.
 * 만일 평행한 점들을 모두 포함시키면
 *
 * 10만개의 점이 모두 일직선 상에 존재한다라면 절대로 시간내에 구할 수가 없다.
 * 이러한 생각들로 인해서
 *
 * 설계는 먼저 다각형을 이루는 점들을 구하고
 * 거기서 최대거리를 구하면 된다.
 *
 * -- 결과
 * 실수를 했었던 점이
 * 반시계 방향으로 정렬하려면, 반시계에 방향에 위치하는 것이 뒤로가야 한다.
 * 음 볼록껍질을 그린다라는 입장에서 설명하면 p1, p2 순으로 이어진다라고 가정해야 한다.
 * 즉, p1 -> p2 이런 순으로 이어질 수 있도록 정렬이 되어야 한다라는 말이다.
 *
 * root, p1, p2 를 넣었을 때, 반시계 방향이다라는 의미는 p1 -> p2 이렇게 이어져야 한다라는 것이고
 * 만약에 그렇지 않다라면 p2 -> p1 이렇게 이어져야 한다라는 것이다.
 *
 * 그러면 당연하게 ccw(root, p1, p2) 값이 1 일 경우 p2 가 뒤로 가야하기 때문에 -1 을 반환해야 한다 (p1 이 앞에 올 수 있도록)
 * 만약에 -1 일 경우에는 p2 -> p1 으로 진행이 되어야하니까 1을 반환하여 p1이 뒤로 간다.
 * 이 점 실수한 것 외에는 굉장히 매끄럽게 진행되었다.
 *
 * 그리고, 자꾸 개같은 illegalArgument Exception 떠가지고 개빡쳤는데
 * 이 에러의 이유가 정렬조건이 모호해서 그렇다고 한다.
 * 뭐가 모호해 개같은넘
 * 하지만 Java 입장에서 보면 얘는 왜 return 0 이 없지? 라고 생각할 수 있긴하다.
 * 그리고, 이 문제에서 같은 지점에다가 쏘지 않았다라는 얘기는 없으니
 * 일직선상에 있는 경우, return 0 도 할 수 있도록
 * Double.compare 로 바꾸어주었더니, AC 처리를 받게 되었다.
 *
 * 즉 실수한점은
 * 1. 반시계 방향으로 정렬할 때, return 을 잘못 생각해서 잘못하고 있었고 (위에서 설명한대로 반시계 방향으로 진행되어야 하기 때문에 root, p1, p2 == 1 일 때에는 return -1 을 해서 p1 이 앞에 와야함)
 * 2. 똑같은 지점에 화살을 쐈을 수도 있는데 그거 생각못하고 그냥 return 1, return -1 만 명시해놨음 그래서 dir == 0 일 때, 같은 지점에 있을 수도 있기 때문에, Double.compare 로 변경하여
 * 서로 같은 지점에 있는 경우에는 return 0 을 하여서 예외를 던지지 않고 정렬을 계속 진행할 수 있도록 하였음
 */
public class Main {
    static int N;
    static double ans = 0;
    static List<Point> points = new ArrayList<>();
    static Stack<Point> stack = new Stack<>();
    static Point root;

    static class Point {
        long y;
        long x;
        Point(long y, long x){
            this.y = y;
            this.x = x;
        }
    }

    static double getDistance(Point p1, Point p2) { // distance 를 반환
        return Math.sqrt(Math.pow(p1.y - p2.y, 2) + Math.pow(p1.x - p2.x, 2));
    }

    static int ccw(Point p1, Point p2, Point p3) {
        long angle = ((p1.x * p2.y) + (p2.x * p3.y) + (p3.x * p1.y))
                - ((p1.y * p2.x) + (p2.y * p3.x) + (p3.y * p1.x));

        return (angle > 0) ? 1
                : (angle < 0) ? -1 : 0; // 반시계 방향이면 -1, 시계 방향이면 1, 일직선이면 0
    }

    static void makeFigure() {
        // root 를 찾아야 한다, y 가 가장 크면서 y 가 같다라면 x 값이 작은 것을 선택한다.
        root = points.get(0);

        for (int i = 1; i < points.size(); i++) {
            Point p = points.get(i);

            if (root.y < p.y) {
                root = p;
            } else if (root.y == p.y) {
                if (p.x < root.x) {
                    root = p;
                }
            }
        } // root 지점을 정의

        // 이제 Point 를 반시계 방향으로 정렬을 해야함
        Collections.sort(points, (p1, p2) -> {
            int dir = ccw(root, p1, p2);

            if (dir > 0) { // 반시계 방향인 경우이다. p2 가 뒤로 가야하니 1 을 반환한다.
                return -1;
            } else if (dir < 0) {
                return 1;
            } else { // dir == 0 인 경우
                double distance1 = getDistance(root, p1);
                double distance2 = getDistance(root, p2);

                return Double.compare(distance1, distance2);
            }
        });

        stack.push(root); // root 를 기준으로 정렬되었기 때문에 root 가 0번째 인덱스이다.

        for (int i = 1; i < N; i++) {
            while (stack.size() > 1 && ccw(stack.get(stack.size() - 2), stack.get(stack.size() - 1), points.get(i)) <= 0) { // 0 이거나 -1 일 때, 반시계 방향이 아니니 pop 해준다.
                stack.pop();
            }

            stack.push(points.get(i)); // 반시계 방향이 되었거나 stack.size() == 1 인 것이니 stack 에 집어넣어준다.
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        N = fun.apply(br.readLine());

        for (int i = 0; i < N; i++) {
            String[] input = br.readLine().split(" ");
            points.add(new Point(fun.apply(input[0]), fun.apply(input[1])));
        }

        // 이제 볼록껍질을 만들어보자.
        makeFigure();

        for (int i = 0; i < stack.size() - 1; i++) {
            for (int j = i + 1; j < stack.size(); j++) {
                Point p1 = stack.get(i);
                Point p2 = stack.get(j);

                ans = Math.max(ans, getDistance(p1, p2));
            }
        }

        System.out.println(ans);
    }
}
