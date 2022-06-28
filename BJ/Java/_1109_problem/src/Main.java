import java.io.*;
import java.util.*;
import java.util.function.Function;

// 1109 : 섬

/**
 * -- 전제 조건
 * 일단 이 문제는
 * 섬 안에 섬이 있는 구조의 문제인데
 *
 * 어떤 섬이 어떠한 섬을 포함하고 있을 때,
 * 안 쪽에 있는 섬 중 가장 높은 높이의 섬 + 1 의 섬의 높이를 가진다고 한다.
 *
 * 그러면 섬의 높이가 K 까지 있다라고 했을 때,
 * 0 부터 K 까지 높이의 섬의 개수를 출력해라.
 *
 * -- 틀 설계
 * 일단 섬 번호를 bfs 를 통해서 나누는 것은 당연하다.
 *
 * 그리고서 그 다음이 문제인데
 *
 * 내 생각에는 어떠 섬에서 출발해서 포함이 되지 않는 경우는 outOfRange 가 나지 않는 경우이고,
 * 만일 어떠한 섬이 어떤 섬에 포함이 되어 있는 경우는 절대로 그 본인을 포함하고 있는 섬의 땅을 제일 많이 만날 수 밖에 없다.
 * 일단, 내가 여러가지 예외 사항들을 생각해보았을 때, 그렇다.
 *
 * 그러면 일단 그런식으로 어떤 섬이 어떤 섬에 포함되는지 구조를 알았다라고 가정하자.
 * 그것은 분리집합처럼 parent 로 관리하자.
 *
 * 그러면 본인을 포함하고 있는 섬의 높이는? 본인의 높이 + 1 이다.
 * 무조건이다.
 *
 * 하지만, 본인과 같이 이 섬에 포함되어 있는 놈의 높이가 더 높다면 그렇지 않을 수도 있다.
 *
 * 그렇기 때문에 여기서 이런식으로 식을 세울 수가 있다.
 * 본인의 높이 + 1 을 다음 섬으로 하는데 Math.min 으로 이미 그 섬의 높이와 본인의 섬 높이 + 1 을 해주는 것이다.
 *
 * 그럼 예를 들어서
 *
 * 5 번 섬이 3 4 번 섬을 포함하고 있고
 * 4 번 섬은 2 번 섬을 포함하고 있다라고 해보자.
 *
 * 그러고서 4 번 섬부터 시작한다고 가정해보자. (높이를 정하는 행위)
 *
 * 4 번은 5번 섬에 포함되어 있다.
 * 그렇기 때문에 5 번 섬이 1 이 된다. (5번 섬은 이미 진행했다고 가정하자 짜피 상관없음 부모가 없어서)
 * 2 3 4 5
 * 0 0 0 1
 *
 * 3 번을 진행하자.
 * 그러면 3 -> 5 (Math.min(1, 1))
 *
 * 2 3 4 5
 * 0 0 0 1
 *
 * 2 번을 진행하자.
 * 2 -> 4 -> 5
 *
 * 2 3 4 5
 * 0 0 1 2
 *
 * 이런식으로 변경이 된다.
 * 이렇게 하면 얼추 맞는 것 같은데?
 *
 * 한번 진행해보자.
 *
 * 그러니까 순서가
 *
 * 1. 섬의 번호를 지정 (1번 부터)
 * 2. 섬에서 시작해서 어떤 섬이 본인을 포함하고 있는지 정해서, outOfRange 가 나지 않으면, parent 로 가장 많이 만남 섬을 지정
 * 3. parent 를 가지고 재귀적으로 부모를 타고 올라가면서 섬의 번호를 갱신
 * 4. 섬의 높이가 가장 높은 섬을 (height 배열) M 으로 지정후 배열을 만들어 (cnt 배열) 각 M 까지 세줌
 * 5. 그리고 순서대로 cnt 배열을 출력
 */
public class Main {
    static int H;
    static int W;
    static int sumNumber = 0; // 섬의 번호를 붙힐 것임, 이게 섬의 개수를 의미하기도 한다.
    static int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
    static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1}; // 8 방향
    static int[] parent;
    static int[] height;
    static int[] cnt;
    static boolean[][] visited;
    static char[][] map;
    static int[][] sum;
    static HashSet<Integer> set = new HashSet<>();

    static boolean outOfRange(int y, int x) {
        if (y < 0 || y >= H || x < 0 || x >= W) {
            return true;
        } else {
            return false;
        }
    }

    static void bfs(int y, int x, boolean init) {
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(y, x));
        visited[y][x] = true; // 방문처리

        int[] meetCount = null;

        if (init) { // 섬번호를 지정하는 것이면
            sum[y][x] = sumNumber;
        } else {
            meetCount = new int[sumNumber + 1];
        }

        while (!queue.isEmpty()) { // queue 가 빌때까지
            Point point = queue.poll();

            if (init) { // 조사면 8 방향
                for (int i = 0; i < 8; i++) {
                    int ny = point.y + dy[i];
                    int nx = point.x + dx[i];

                    if (outOfRange(ny, nx) || visited[ny][nx] || map[ny][nx] == '.') { // . 이거나 이미 방문하거나 범위를 벗어나면 continue
                        continue;
                    }

                    sum[ny][nx] = sumNumber; // 섬번호 붙혀줌
                    queue.add(new Point(ny, nx)); // queue 에 넣어줘서 계속 이어갈 수 있도록
                    visited[ny][nx] = true;
                }
            } else { // 조사가 아니면 4 방향
                for (int i = 0; i < 8; i += 2) {
                    int ny = point.y + dy[i];
                    int nx = point.x + dx[i];

                    if (outOfRange(ny, nx)) { // 이거면 아얘 parent 로 아무것도 안들어감 그니까 그냥 바로 빠져나감
                        set.add(sum[y][x]); // 현재 섬 넘버는 이제 볼 필요가 없음 짜피 포함되지 않으니까
                        return;
                    }

                    if (visited[ny][nx]) {
                        continue;
                    }

                    if (map[ny][nx] == 'x' && sum[ny][nx] != sum[y][x]) {
                        meetCount[sum[ny][nx]]++; // 증가시켜주고
                        continue;
                    }

                    queue.add(new Point(ny, nx));
                    visited[ny][nx] = true;
                }
            }
        }

        if (!init) {
            int max = 0;

            for (int i = 1; i <= sumNumber; i++) {
                if (i != sum[y][x]) { // 현재 섬 넘버가 아닌 것들에서 가장 높은 것을 뽑을 것임
                    if (meetCount[i] > meetCount[max]) {
                        max = i;
                    }
                }
            }

            set.add(sum[y][x]);
            parent[sum[y][x]] = max; // sum[y][x] 의 parent 는 max 로 정함 (즉 가장 많이 만난놈)
        }
    }

    static class Point {
        int y;
        int x;

        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static void find(int a, int value) {
        if (a != parent[a]) {
            height[parent[a]] = Math.max(height[parent[a]], value + 1); // 본인이 가진 value + 1 이나 혹은 이미 정해져 있던 높이
            find(parent[a], height[parent[a]]); // 그 중 높은 것으로 올려보낸다.
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("./_1109_problem/src/sample_input.txt")));
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        H = fun.apply(input[0]);
        W = fun.apply(input[1]);

        map = new char[H][W];
        sum = new int[H][W];

        for (int i = 0; i < H; i++) {
            String string = br.readLine();
            for (int j = 0; j < W; j++) {
                map[i][j] = string.charAt(j);
            }
        }

        visited = new boolean[H][W];
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (map[i][j] == 'x' && sum[i][j] == 0) { // 맵이 섬의 일부이면서 아직 섬 번호가 붙혀지지 않은 곳
                    sumNumber++;
                    bfs(i, j, true); // 섬 번호를 조사하는 bfs
                }
            }
        }

//        for (int i = 0; i < H; i++) {
//            for (int j = 0; j < W; j++) {
//                System.out.print(sum[i][j] + " ");
//            }
//            System.out.println();
//        }

        parent = new int[sumNumber + 1];
        height = new int[sumNumber + 1];

        for (int i = 1; i <= sumNumber; i++) {
            parent[i] = i; // 본인의 부모는 본인으로 초기화
        }


        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (map[i][j] == 'x' && !set.contains(sum[i][j])) { // x 여야 하고 set 에 섬 넘버가 들어가서는 안됨
                    visited = new boolean[H][W];
                    bfs(i, j, false); // parent 를 정하는 bfs
                }
            }
        }

        for (int i = 1; i <= sumNumber; i++) {
            find(i, height[i]); // i 와 현재 i 가 가지고 있는 높이를 보내서 섬의 높이들을 갱신시킴
//            System.out.println(i + " 실행 " + Arrays.toString(height));
        }

//        System.out.println(Arrays.toString(parent));
//        System.out.println(Arrays.toString(height));

        int M = 0;
        for (int i = 1; i <= sumNumber; i++) {
            M = Math.max(M, height[i]);
        }

        cnt = new int[M + 1];

        for (int i = 1; i <= sumNumber; i++) {
            cnt[height[i]]++;
        }

        for (int i = 0; i <= M; i++) {
            sb.append(cnt[i] + " ");
        }

        System.out.println(sumNumber == 0 ? -1 : sb); // sumNumber 가 없다면 이럼
    }
}
