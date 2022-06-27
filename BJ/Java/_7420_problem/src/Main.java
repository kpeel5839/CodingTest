import java.util.*;
import java.io.*;
import java.util.function.Function;

// 7420 : 맹독 방벽

/**
 * -- 전제조건
 * 각 지점의 개수, 그리고 L 이 주어졌을 때
 * 맹독 방벽의 길이를 구하시오.
 *
 * -- 틀 설계
 * 진짜 대박이다.
 * 볼록껍질이라는 힌트는 당연히 얻었다.
 *
 * 그래서 주변의 길이를 어떻게 잴 수 있을까라는 생각을 했다.
 * 처음에는 원을 그리면서 진행해보았다.
 *
 * 근데, 생각보다 그렇게 잘 안됐다.
 * 특히나 답이 그렇지 않았다.
 *
 * 그래서 다시 생각해보았는데
 * 꼭짓점은 무조건 원모양의 방벽이 생기기 마련이다
 *
 * 왜? 거리 100을 유지해야 하니까
 * 그렇기 때문에 꼭짓점에는 무조건 원이 생기기 마련이다.
 *
 * 그래서 무조건 L * pi * 2 의 원이 하나 생긴다
 * 왜냐하면 꼭짓점이 아무리 많아도 생기는 원들을 다 더하면 하나의 원이 되기 때문이다.
 *
 * 그러면 이제 직선 방벽의 길이를 구해야 한다.
 * 이것은 너무 쉽다.
 *
 * 포인트 끼리의 거리를 빗변 공식으로 구해주고 (피타고라스)
 * 그냥 더해주면 된다.
 *
 * 즉, 볼록껍질을 구한 뒤에 2 * pi * L 과 선택된 볼록껍질간을 순서대로 빗변을 구해주면?
 * 방벽의 길이를 구할 수 있다.
 *
 * 마지막에 꼭 소숫점 첫째자리를 반올림 해주어야 한다라는 것도 잊으면 안된다.
 * 즉, 정수를 만들어야 한다.
 *
 * -- 결과
 * 정말 야물딱지게 기분좋게 맞았다.
 * 정말 해결 방안을 생각하고 나서 쉽게 풀었다.
 * 그래서 기분이 너무 기모찌하다.
 */
public class Main {
    static int N;
    static int L;
    static Point root;
    static List<Point> points = new ArrayList<>();
    static Stack<Point> stack = new Stack<>();

    static class Point {
        int y;
        int x;

        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static int ccw(Point p1, Point p2, Point p3) {
        long angle = ((p1.x * p2.y) + (p2.x * p3.y) + (p3.x * p1.y))
                - ((p1.y * p2.x) + (p2.y * p3.x) + (p3.y * p1.x));

        return (angle > 0) ? 1
                : (angle < 0) ? -1 : 0;
    }

    static void convexHull() {
        // convexHull 알고리즘은 흔히 볼록껍질이라고 불리는 알고리즘이다.
        root = points.get(0);

        for (int i = 1; i < points.size(); i++) {
            Point point = points.get(i);
            if (root.y < point.y) {
                root = point;
            } else if (root.y == point.y) {
                if (point.x < root.x) {
                    root = point;
                }
            }
        }

        Collections.sort(points, (p1, p2) -> {
            int result = ccw(root, p1, p2);

            if (result == 1) {
                return -1;
            } else if (result == -1) {
                return 1;
            } else {
                double distance1 = getDistance(root, p1);
                double distance2 = getDistance(root, p2); // 더 가까운 것이 앞에 오도록

                return Double.compare(distance1, distance2);
            }
        });

        stack.push(root);

        for (int i = 1; i < N; i++) {
            while (stack.size() > 1 && ccw(stack.get(stack.size() - 2), stack.get(stack.size() - 1), points.get(i)) <= 0) {
                stack.pop();
            }

            stack.push(points.get(i));
        }
    }

    static double getDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow((p1.y - p2.y), 2) + Math.pow((p1.x - p2.x), 2));
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        L = fun.apply(input[1]);
        double ans = 0;

        for (int i = 0; i < N; i++) {
            input = br.readLine().split(" ");
            points.add(new Point(fun.apply(input[1]), fun.apply(input[0])));
        }

        convexHull();

        for (int i = 0; i < stack.size(); i++) {
            int pair = i - 1 == -1 ? stack.size() - 1 : i - 1;
            ans += getDistance(stack.get(pair), stack.get(i));
        }

        ans += (2 * Math.PI * L);
        System.out.println((int) Math.round(ans));
    }
}