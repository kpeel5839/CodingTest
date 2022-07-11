import java.util.*;
import java.io.*;
import java.util.function.Function;

// 18809 : Gaaaaaaaaaarden

/**
 * -- 전제조건
 * 배양액을 뿌릴 수 있고
 * 배양액을 뿌릴 수 있는 땅은 주어진다.
 * 그리고, 빨간 배양액, 초록 배양액의 개수는 정해져있고
 * 서로 1초 마다 퍼져나간다.
 *
 * 근데, 동시에 만나게 되는 경우
 * 그 경우는 꽃이 피게 된다.
 *
 * 그러면 꽃이 피게 된 다음에는 거기서부터는 퍼져나가지 못하고 다른 놈들 부터 퍼져 나가야 한다.
 * -- 틀 설계
 * 일단 조합적으로 생각해봤을 떄,
 * 최대의 경우 빨간, 초록 배양액 5개씩 배양액을 뿌릴 수 있는 위치가 10개가 주어진다고 가정하였을 떄,
 * 순서에 상관없이, 그리고 적절히 배양액을 뿌리게 되면, 그렇게 많은 경우가 나오지는 않을 것 같다.
 *
 * 즉, 브루트포스로 상황을 구성해놓고 거기 내부의 로직만 처리하면 된다라는 것이다.
 *
 * 본문 예제
 * 5 5 2 2
 * 0 0 0 0 1
 * 0 0 0 0 2
 * 1 2 2 1 1
 * 2 1 2 0 1
 * 0 1 0 0 1
 *
 * 3 3 1 2
 * 0 0 0
 * 2 2 0
 * 0 2 0
 *
 * -- 해맸던 점
 * 진짜 이 문제 잡고 3시간 11시부터
 * 2시간 반을 했다..
 *
 * 우울증 올뻔했다.
 *
 * 일단 처음에 해맸던 점은
 * 다 잘 처리했었는데
 *
 * 꽃에서 시작하면 안된다라는 점을 로직 처리를 잘 하지 못했었다.
 * 그래서 그 부분을 고치니
 *
 * 다 맞을 수 있었지만
 *
 * 진짜 문제는 시간초과였다.
 *
 * 시간이 미치도록 느렸다.
 *
 * 그래서 어떻게 할까...
 * 하면서 사이트를 다 찾아봤는데
 *
 * 솔직히 로직 처리에 있어서는
 * 다 나랑 똑같아 보였다.
 *
 * 하나도 나은 점이 없어보였다.
 *
 * 하지만 그 로직들을 따라했고
 * 번번히 실패하였다.
 *
 * 근데, 가만보니 조합 부분, 즉 brute force 로 처리한 부분을 고치지 않았었다.
 * 한번 이 부분을 다른 사람들의 로직으로 변경해보았다.
 *
 * 마법같이 맞았다.
 * 사실 메인로직에 문제가 있었던 것이 아닌, 조합 부분이, 즉 배양하는 땅을 정하는 부분이 너무나도 비효율적이였던 것이다.
 *
 * 처리한 방법은 Green 을 먼저 땅을 배양해주고,
 * Red 를 배양해주는, 즉 Red, Green 을 따로따로 배양해줘서
 * 불필요했던 부분을 제거해주는 작업으로 보였다.
 *
 * 그리고 나는 for (int i = 0; i < 2; i++) 해서 i == 0 일 때는 선택안하고 넘어가고, i == 1 일 떄는 선택하는 형식으로 진행하였는데
 * 보통 조합은 for (int i = depth; i < size; i++) 해주고 bruteForce(i + 1, ~~) 이런식으로 해결해주는
 *
 * 즉, 순서를 고려하여, 내가 선택한 것 이후를 선택하여서 조합을 하는 형식을 많이 사용하였다.
 * 확실히 이 방법으로 해야 이러한 시간초과를 피할 수 있는 것 같다.
 *
 * 근데, 사실 아직 저렇게 하면 조합이 왜 이렇게 효율적으로 변하는지는 아직도 잘 모르겠다.
 * 하지만, 계속 이해해보려 노력하자.
 *
 * 수학 공부도 계속 하고 있으니까, 확률 부분을 다루게 되면 충분히 이해할 수 있지 않을까?
 */
public class Main {
    static int H;
    static int W;
    static int G;
    static int R;
    static int twoCount = 0;
    static int groundCount = 0;
    static int ans = 0;
    static int[] red;
    static int[] green;
    static Point[] twoGround;
    static boolean[] combVisited;
    static int[] assign;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static int[][] map;

    static class Point {
        int y;
        int x;
        int kind;

        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        Point(int y, int x, int kind) {
            this(y, x);
            this.kind = kind;
        }

        @Override
        public String toString() {
            return "y : " + y + " x : " + x;
        }
    }

    static void permGreen(int start, int r) {
        if (r == G) {
            permRed(0, 0);
            return;
        }

        for (int i = start; i < twoGround.length; i++) {
            if (!combVisited[i]) {
                combVisited[i] = true;
                green[r] = i;
                permGreen(i + 1, r + 1);
                combVisited[i] = false;
            }
        }
    }

    static void permRed(int start, int r) {
        if (r == R) {
            bfs();
            return;
        }

        for (int i = start; i < twoGround.length; i++) {
            if (!combVisited[i]) {
                combVisited[i] = true;
                red[r] = i;
                permRed(i + 1, r + 1);
                combVisited[i] = false;
            }
        }
    }

    static void bfs() {
        // assign 을 토대로 진행하면 된다. Green = 3, Red = 4 로 진행하자
        /**
         * 여기서부터 관건이다.
         * 일단 배양하는 데까지는 문제 없이 진행하였다.
         *
         * 하지만 이제 씨를 퍼트리는 것이 문제이다.
         * 각 어떤 씨인지를 파악을 진행하고
         *
         * 주변을 그 숫자로 채워나가면 쉽다.
         * 하지만, 어떻게 동시에 퍼짐을 알 수 있을까
         *
         * 일단 퍼지는 꽃들을 전부, List 에다가 담는다.
         * 뭐 outOfRange 혹은, 이미 꽃인 부분들 등등으로는 visited 가 되어있는 부분 등을
         * 제외하고 List 에다가 담는다.
         *
         * 그 다음에 이제 List 를 순환하면서, 현재 담긴 지점에다가 너흥면 일단 이미 차있는 공간은 절대 없다.
         * 그러면 이번에 추가된 씨들이라는 것이다.
         * 즉, 그러면 넣으면서 만일 동일한 씨앗이다?
         *
         * 그러면 그냥 애초에 하지말고
         * 만일 동일한 씨앗이 아니다?
         *
         * 그러면 5를 (5 == 꽃) 심어준다.
         * 그러고서 flower++ 를 해준다.
         *
         * 이 조건들이 완성되기 위해서는, 이미 심어져 있는 것이 본인과 다른데,
         * 그게 1, 2, 5 가 아니라면 변경해주면 된다.
         *
         * 그러면 될 것 같은데
         */
        Queue<Point> queue = new LinkedList<>();
        int flower = 0; // flower count 할 변수


        for (int i = 0; i < red.length; i++) {
            int y = twoGround[red[i]].y;
            int x = twoGround[red[i]].x;

            map[y][x] = 3;
            queue.add(new Point(y, x, 3));
        }

        for (int i = 0; i < green.length; i++) {
            int y = twoGround[green[i]].y;
            int x = twoGround[green[i]].x;

            map[y][x] = 4;
            queue.add(new Point(y, x, 4));
        }

//        System.out.println(Arrays.toString(red));
//        System.out.println(Arrays.toString(green));
//        mapPrint(map);

        while (true) {
            int size = queue.size();
            Queue<Point> list = new LinkedList<>();

            for (int i = 0; i < size; i++) {
                Point point = queue.poll();

                if (map[point.y][point.x] == 5) { // 본인 지점이 5 꽃이 핀 경우
                    continue;
                }

                for (int j = 0; j < 4; j++) {
                    int ny = point.y + dy[j];
                    int nx = point.x + dx[j];

                    if (outOfRange(ny, nx) || map[ny][nx] != 1) { // 0 부분 거르고, 범위 나가면 거르고, 이미 배양된 곳이면 거른다.
                        continue;
                    }

                    list.add(new Point(ny, nx, point.kind)); // 본인의 종류와, 퍼진 공간을 담아준다.
                }
            }

            if (list.size() == 0) { // 아무것도 퍼진 것이 없다? 그러면 끝난 것
                break;
            }

            while (!list.isEmpty()) {
                Point point = list.poll();
                int y = point.y;
                int x = point.x;

                if (map[y][x] == 1) { // 현재 본인이 씨를 심으려는 곳이 1 이나 2 이면 노 상관
                    map[y][x] = point.kind; // 본인 씨앗을 거기다가 심어준다
                    queue.add(point); // queue 에다가 담아준다.
                } else { // 본인이 씨를 심으려는 곳이 1 이나 2 가 아닌 경우는 이미 씨가 뿌려진 곳
                    if (point.kind != map[y][x] && map[y][x] != 5) { // 1, 2, 5 도 아닌 곳
                        map[y][x] = 5;
                        flower++;
                    } // 이미 5 이거나, 본인과 씨가 같은 곳 queue 에다가 굳이 담아줄 이유가 전혀 없음
                }
            }
        }

//        mapPrint(map);

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (map[i][j] > 1) {
                    map[i][j] = 1;
                }
            }
        }

        ans = Math.max(ans, flower);
    }

    static boolean outOfRange(int y, int x) {
        if (y < 0 || y >= H || x < 0 || x >= W) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        H = fun.apply(input[0]);
        W = fun.apply(input[1]);
        G = fun.apply(input[2]);
        R = fun.apply(input[3]);
        map = new int[H][W];
        green = new int[G];
        red = new int[R];
        List<Point> twoList = new ArrayList<>();

        for (int i = 0; i < H; i++) {
            input = br.readLine().split(" ");
            for (int j = 0; j < W; j++) {
                map[i][j] = fun.apply(input[j]);

                if (map[i][j] == 2) { // 배양액을 뿌릴 수 있는 땅을 받았을 때
                    twoCount++;
                    twoList.add(new Point(i, j));
                    map[i][j] = 1;
                }

                if (map[i][j] == 1) {
                    groundCount++;
                }
            }
        }

        assign = new int[twoCount];
        twoGround = twoList.toArray(Point[]::new); // Array 에다가 메소드 참조할 수 있었구나 (toArray 에 어떤 타입의 배열로 만들 것인지 명시하면 됨
        combVisited = new boolean[twoGround.length];

        permGreen(0, 0);
        System.out.println(ans);
    }

    static void mapPrint(int[][] map) {
        System.out.println("next !");
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
