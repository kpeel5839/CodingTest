import java.util.*;
import java.io.*;
import java.util.function.Function;

// 2162 : 선분 그룹

/**
 * -- 전제 조건
 * 여러개의 선분들이 주어졌을 때, 교차하는 선분들의 그룹의 개수를 출력하고,
 * 그 그룹 중에서, 선분이 많은 그룹의 선분 개수를 출력하라.
 *
 * -- 틀 설계
 * 기하학으로 해결하는 문제이고, 거기에다가 union find 까지 써야하는 문제이다.
 *
 * notion 에다가 정리해놓았다.
 * https://www.notion.so/2162-ed1e5e1fca20407dad8317c40daec746
 */
public class Main2 {
    public static int[] parent; // parent 저장할 변수
    public static int[] count; // parent 를 통해서, 어떠한 인덱스의 포인터가, 어떠한 그룹에 속해있고, 그 그룹에는 몇개의 선분이 있는지 확인하기 위한 변수
    public static final int INF = 5000; // 이 문제에서의 x, y 최대 값, 즉 유효하지 않은 숫자.

    public static class Point {
        double x1;
        double y1;
        double x2;
        double y2;

        public Point(double x1, double y1, double x2, double y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        } // 생성자 정의
    }

    public static int find(int a) {
        if (parent[a] == a) {
            return a;
        }

        return parent[a] = find(parent[a]); // 최종적으로 반환된, 부모 노드를 본인의 부모노드로 등록
    }

    public static void union(int a, int b) {
        parent[b] = a; // b 집합의 부모로 a 를 집어넣는다.
    }

    // 교점 x를 구하는 method
    public static double getX(Point p1, Point p2) {
        double x1 = p1.x1;
        double y1 = p1.y1;
        double x2 = p1.x2;
        double y2 = p1.y2;

        double x3 = p2.x1;
        double y3 = p2.y1;
        double x4 = p2.x2;
        double y4 = p2.y2; // x1, x2, x3, x4, y1, y2, y3, y4 즉 두 선분의 포인터들을 다 뽑아냄, (가독성 좋게

        double deno = ((x1 - x2) * (y3 - y4)) - ((x3 - x4) * (y1 - y2)); // 분모, (x1 - x2)(y3 - y4) - (x3 - x4)(y1 - y2)
        double mole = (((x1 * y2) - (x2 * y1)) * (x3 - x4)) - (((x3 * y4) - (x4 * y3)) * (x1 - x2)); // 분자, (x1y2 - x2y1)(x3 - x4) - (x3y4 - x4y3)(x1 - x2)

        if (deno == 0) {
            return INF + 1;
        }

        return mole / deno;
    }

    // 교점 Y를 구하는 method
    public static double getY(Point p1, Point p2) {
        double x1 = p1.x1;
        double y1 = p1.y1;
        double x2 = p1.x2;
        double y2 = p1.y2;

        double x3 = p2.x1;
        double y3 = p2.y1;
        double x4 = p2.x2;
        double y4 = p2.y2; // x1, x2, x3, x4, y1, y2, y3, y4 즉 두 선분의 포인터들을 다 뽑아냄, (가독성 좋게

        double deno = ((x1 - x2) * (y3 - y4)) - ((x3 - x4) * (y1 - y2)); // 분모, (x1 - x2)(y3 - y4) - (x3 - x4)(y1 - y2)
        double mole = (((x1 * y2) - (x2 * y1)) * (y3 - y4)) - (((x3 * y4) - (x4 * y3)) * (y1 - y2)); // 분자, (x1y2 - x2y1)(y3 - y4) - (x3y4 - x4y3)(y1 - y2)

        if (deno == 0) {
            return INF + 1;
        }

        return mole / deno;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Double> fun = Double::parseDouble;

        int N = Integer.parseInt(br.readLine());
        parent = new int[N];
        count = new int[N];
        Point[] point = new Point[N];
        String[] input;

        for (int i = 0; i < N; i++) {
            parent[i] = i; // 본인으로 채워줌
        }

        for (int i = 0; i < N; i++) {
            input = br.readLine().split(" ");

            double x1 = fun.apply(input[0]);
            double y1 = fun.apply(input[1]);
            double x2 = fun.apply(input[2]);
            double y2 = fun.apply(input[3]);

            point[i] = new Point(x1, y1, x2, y2);
        }

        for (int i = 0; i < N; i++) { // 비교하는 것이니까, 중복되지 않도록 이런식으로 포문을 구성해줌
            for (int j = i + 1; j < N; j++) {
                Point p1 = point[i];
                Point p2 = point[j];

                double res = getX(p1, p2); // 두 직선의 교점을 얻음

                if (res == INF + 1) { // 두 직선이 평행하거나 일치할 때
                    // x = a 인 경우
                    if ((p1.x2 - p1.x1) == 0 && (p2.x2 - p2.x1 == 0)) {
                        // 이제 평행하는지 안하는 지를 보면 된다.
                        if (p1.x1 != p2.x1) { // 평행한 경우
                            continue;
                        }
                    }

                    // y = a 인 경우
                    if ((p1.y2 - p1.y1) == 0 && (p2.y2 - p2.y1 == 0)) {
                        // 이제 평행하는지 안하는 지를 보면 된다.
                        if (p1.y1 != p2.y1) { // 평행한 경우
                            continue;
                        }
                    }

                    // x == a 가 아닌 경우, 이제 y == a 도 고려해주긴 해야함

                    // 이제, m 과, n 을 구하자.
                    // m = 기울기, n = y절편

                    // 기울기는, (y2 - y1) / (x2 - x1)
                    double m1 = (p1.y2 - p1.y1) / (p1.x2 - p1.x1);
                    double m2 = (p2.y2 - p2.y1) / (p2.x2 - p2.x1);

                    // n은 이런식으로 유도 가능하다, 보통의 일반함수는 mx + n 형태로 이루어져 있으니까 m(x - x1) - y1 에서 mx - mx1 - y1 이니까, n 은 - mx + y
                    double n1 = (-m1 * p1.x1) + p1.y1;
                    double n2 = (-m2 * p2.x1) + p2.y1;

                    if (n1 != n2) { // 둘이 일치하지 않는 경우, 일단 평행한 것은 확실하니까 기울기는 같음 res = INF + 1 이였기 때문에, 즉 평행하다.
                        continue;
                    }

                    // 그리고 서로 평행하고, 만나지 않는 경우 제거
                    if ((p1.x1 < Math.min(p2.x1, p2.x2) && p1.x2 < Math.min(p2.x1, p2.x2))
                            || (p2.x1 < Math.min(p1.x1, p1.x2) && p2.x2 < Math.min(p1.x1, p1.x2))) {
                        continue;
                    }

                    if ((p1.y1 < Math.min(p2.y1, p2.y2) && p1.y2 < Math.min(p2.y1, p2.y2))
                            || (p2.y1 < Math.min(p1.y1, p1.y2) && p2.y2 < Math.min(p1.y1, p1.y2))) {
                        continue;
                    }

                    union(find(i), find(j));
                } else { // 두 직선이 평행하거나, 일치하지 않고 교점이 존재할 때
                    // 일단 먼저 x 좌표가, 포함이 되는 지 확인 해야함, 그 다음에 y 좌표까지 확인할 것임
                    if (res >= Math.min(p1.x1, p1.x2) && res <= Math.max(p1.x1, p1.x2)
                            && res >= Math.min(p2.x1, p2.x2) && res <= Math.max(p2.x1, p2.x2)) {

                        res = getY(p1, p2); // 일단, x 좌표가 포함 되는지 확인을 했으니까, 다시 res 를 구해서, 교점 Y가 다시 포함되는지 확인해야 함

                        if (-INF <= res && res <= INF) { // 좌표가, 범위에 포함되는 지
                            if (res >= Math.min(p1.y1, p1.y2) && res <= Math.max(p1.y1, p1.y2)
                                    && res >= Math.min(p2.y1, p2.y2) && res <= Math.max(p2.y1, p2.y2)) {
                                union(find(i), find(j)); // y 좌표까지 포함이 되면, 또 union
                            }
                        }
                    }
                }
            }
        }

        // 이제 다 진행했으면, find, union 을 한 것을 가지고, count 값을 계산해주어야 한다.
        for (int i = 0; i < N; i++) {
            count[find(i)]++;
        } // i 들의 부모를 최종적으로 검색하여서, 그 그룹의 개수들을 구함

        int groupCount = 0;
        int maxCount = 0;

        for (int i = 0; i < N; i++) {
            // count 가 0 이 아닌 애들의 숫자가 group 수임, 왜냐하면, 최종 부모노드를 찾아서 count를 갱신하였기 때문에
            if (count[i] != 0) {
                groupCount++;
                maxCount = Math.max(count[i], maxCount);
            }
        }

        System.out.println(groupCount + "\n" + maxCount);
    }
}
