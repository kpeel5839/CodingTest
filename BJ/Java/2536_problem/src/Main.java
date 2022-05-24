import java.util.*;
import java.io.*;
import java.util.function.Function;

// 2536 : 버스 갈아타기

/**
 * -- 전제조건
 * 수직선과, 수평선이 만나는 교차점들 중 왼쪽 아래점의 위치는 (1, 1) 가장 오른쪽 위에 있는 정점은 (m, n) 이다.
 * 도로망을 운행하는 버스들은 k 개가 있다.
 *
 * 그래서, 이 버스들은 도로망을 같은 수직선상, 혹은 수평선상에서만 왕복을 하게 되는데
 * 두개의 교차점, (i1, j1), (i2, j2) 를 기준으로 왕복을 하고, 중간 교차로에서 다 멈출 수 있다 (끝 교차로도 포함)
 * 그래서, 출발 지점과, 목적 지점이 주어졌을 때, 목적지점까지 갈 수 있는 최소의 버스 개수를 구하여라
 *
 * 출발지와 목적지는 무조건 다르게 주어지며, 무조건 가는 방법은 한 가지 이상이 존재한다.
 *
 * 그리고 첫번 째 줄에는 m, n 이 주어진다. m 이 col, n 이 row 이니까 내가 생각하던 개념으로 생각하면 안된다.
 * 두번 째 줄에는 버스의 개수
 * 그 다음에는 버스들의 번호와 두 교차로의 좌표가 주어지고
 * 마지막에 Sx, Sy, Dx, Dy 가 주어지고 S 는 출발지점, D는 목적지이다.
 *
 * -- 틀 설계
 * 음 일단, 절대로 m, n을 이용하여서 배열을 만들생각은 꿈에도 하면 안된다.
 * m, n 의 최대값은 10만이기에, 최대 값으로 배정해보았을 때 배열의 사이즈는 10만 * 10만 이다.
 * 그렇기 때문에 당연히 overflow 가 나게 될 것이다.
 *
 * 그래서, 내 생각은 분리집합을 사용해야 하지 않을까 싶다.
 * 절대로 m, n 을 실질적으로 배열을 만들어서 하는 경우는 말이 안되고,
 *
 * 어떤 지점은, 어떤 버스에 속하고
 * 그것을 통해서 다익스트라를 진행하면 가능하지 않을까? 라는 생각이다.
 *
 * 최소 버스를 이용해야 하는 것이니까
 * 목적지까지의 최소 버스를 이용해야 한다.
 *
 * 첫째, 시작 지점을 포함하고 있는 정점을 찾아라.
 * 둘째, 마지막 지점을 포함하고 있는 정점을 찾아라.
 * 셋째, 각 버스와 버스를 이용해, 교차하는 지점, 즉 연결되어 있는 버스들은 무방향 그래프로 이어야 한다.
 * 넷째, 시작 지점을 포함하고 있는 정점을 기준으로, 시작을 하여서, 답을 구하여라
 * (만일 시작 지점을 포함하고 있는 버스가 많다라면, 그것을 담아놨다고 하나하나 시작해보면서 bfs 를 실행해보자.)
 */
public class Main {
    static int M;
    static int N;
    static int K;
    static int res;
    static List<ArrayList<Integer>> graph = new ArrayList<>();
    static int[] start;
    static int[] end;
    static int[][] busList;
    static boolean[] startList; // 시작 정점들, 시작 지점을 포함하고 있으면 true
    static boolean[] endList; // 마지막 정점들, 마지막 지점을 포함하고 있으면 true
    static boolean[] visited; // 하나하나 bfs 실행할 때, 방문처리를 위해서

    static void bfs(int number) {
        visited = new boolean[K + 1];
        Queue<int[]> queue = new LinkedList<>();
        visited[number] = true;
        queue.add(new int[] {number, 1});

        while (!queue.isEmpty()) {
            int[] a = queue.poll();

            if (endList[a[0]]) {
                res = a[1];
                return;
            }

            for (Integer next : graph.get(a[0])) {
                if (!visited[next]) { // 방문 안한 경우에만 진행한다.
                    queue.add(new int[] {next, a[1] + 1});
                }
            }
        }
    }

    static void judgeCross(int a, int b) {
        // a, b 번 버스가 교차하는 지 확인한다, 즉 그래프를 생성하는 부분임
        int aDir = judgeDir(a);
        int bDir = judgeDir(b); // 서로 각자의 dir 을 얻어냄

        if (aDir == bDir) { // 방향이 같은 부분

        } else { // 방향이 서로 다를 때,

        }
    }

    static int judgeDir(int a) {
        // a 번 버스의 방향을 판단.
        int[] bus = busList[a];
        if (bus[0] == bus[2]) {
            return 1; // 세로
        } else {
            return 0; // 가로
        }
    }

    static void judge(int a, int judgeNumber) { // 시작정점인지 아닌지 판단은 완료
        // a 번 버스가 judge = 0 이면 start 랑 겹치는지, judge = 1 이면 end 랑 겹치는지 확인한다.
        int[] bus = busList[a]; // 해당 bus 정보 뽑아냄
        int startX = Math.min(bus[1], bus[3]);
        int endX = Math.max(bus[1], bus[3]);
        int startY = Math.min(bus[0], bus[2]);
        int endY = Math.max(bus[0], bus[2]);

        if (bus[0] == bus[2]) { // y 가 같은 경우는
            System.out.println("bus y equals : " + Arrays.toString(bus));
            if (judgeNumber == 0) { // start 정점이 포함되는지
                if ((start[0] == bus[0]) && startX <= start[1] && start[1] <= endX) {
                    startList[a] = true;
                }
            } else { // end 정점이 포함되는 지
                if ((end[0] == bus[0]) && startX <= end[1] && end[1] <= endX) {
                    endList[a] = true;
                }
            }
        }

        if (bus[1] == bus[3]) { // x 가 같은 경우는
            System.out.println("bus x equals : " + Arrays.toString(bus));
            if (judgeNumber == 0) { // start 정점이 포함되는지
                if ((start[1] == bus[1]) && startY <= start[0] && start[0] <= endY) {
                    startList[a] = true;
                }
            } else { // end 정점이 포함되는 지
                if ((end[1] == bus[1]) && startY <= end[0] && end[0] <= endY) {
                    endList[a] = true;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        M = fun.apply(input[0]);
        N = fun.apply(input[0]); // 내가 하는 방법이 아닌가... 왜 M, N 이 필요하지?
        K = fun.apply(br.readLine());

        startList = new boolean[K + 1];
        endList = new boolean[K + 1];
        busList = new int[K + 1][4]; // 시작 지점과 끝 지점을 나타냄
        start = new int[2];
        end = new int[2]; // 시작 정점과 끝 정점을 판단 가능하도록

        for (int i = 0; i <= K; i++) { // 그래프 초기화
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < K; i++) {
            input = br.readLine().split(" ");

            int busNumber = fun.apply(input[0]);
            int sx = fun.apply(input[1]);
            int sy = fun.apply(input[2]);
            int dx = fun.apply(input[3]);
            int dy = fun.apply(input[4]);

            busList[busNumber][0] = sy;
            busList[busNumber][1] = sx; // 출발 지점
            busList[busNumber][2] = dy;
            busList[busNumber][3] = dx; // 끝 지점
        }

        input = br.readLine().split(" ");
        start[0] = fun.apply(input[1]); // y
        start[1] = fun.apply(input[0]); // x
        end[0] = fun.apply(input[3]); // y
        end[1] = fun.apply(input[2]); // x

        for (int i = 1; i <= K; i++) {
            for (int j = 1; j <= K; j++) {
                if (i != j) {
                    judgeCross(i, j);
                }
            }
        }

        for (int i = 1; i <= K; i++) {
            judge(i, 0);
            judge(i, 1);
        }

        for (int i = 1; i <= K; i++) {
            if (startList[i]) { // 출발 정점이면
                bfs(i);
            }
        }

        System.out.println(res);
    }
}
