import java.util.*;
import java.io.*;
import java.util.function.Function;

// 2234 : 성곽

/**
 * -- 전제조건
 * 성곽이 주어지고
 * 거기서 3 개의 정보를 얻어야 한다.
 * 1. 이 성에 있는 바으이 개수
 * 2. 가장 넓은 바으이 넓이
 * 3. 하나의 벽을 제거하여 얻을 수 있는 가장 넓은 방의 크기
 *
 * 이것들을 얻을 수 있도록 프로그램을 작성하라 (N = col, M = row) (최대 15의 숫자로 주어지는데 이진수로 벽을 의미한다.)
 *
 * -- 틀 설계
 * 그냥 굉장히 쉽다.
 * 3차원 배열로 성곽으로 막혀 있는 부분은 표시를 해준다.
 * 그 다음에, 성곽에 막혀 있지 않으면 진행한다.
 *
 * 근데, 이제 이게 몇번째 섬인지도 기록한다.
 * outOfRange 가 필요 없을 것 같지만, 나중에 벽을 부실 때 필요하니 선언해놓는다.
 *
 * 그래서 처음에 방을 나누면서 room count 를 올리고
 * 그 다음에 기록하면서, 방의 개수를 세어주어서, map[i][j] 에다가 기록하면서, 최대 방의 개수를 센다.
 * 그리고 모든 부실 수 있는 벽을 부시면서 그 다음 칸으로 진행해서, 다른 방이면 합쳐 본다.
 *
 * 그러면서 이번에도 큰 것을 취하는 연산을 진행한다.
 *
 * -- 결과
 * 개 쉽게 맞았음
 */
public class Main {
    static int H;
    static int W;
    static int maxRoom = 0;
    static int countRoom = 0;
    static int crashMaxRoom = 0;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static int[][] map; // 방의 번호를 기록
    static boolean[][][] wall;
    static boolean[][] visited;
    static List<Integer> roomSizeList = new ArrayList<>();

    static void crashWall(int y, int x) {
        // 4 방향을 다 부셔봄, 사실 벽이 있는 거 따져볼 필요도 없음, 그냥 다른 방이면 가능함
        int myRoomNumber = map[y][x];
        int res = 0;

        for (int i = 0; i < 4; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (outOfRange(ny, nx) || map[ny][nx] == myRoomNumber) { // 본인의 방과 같음
                continue;
            }

            res = Math.max(res, roomSizeList.get(map[ny][nx]));
        }

        crashMaxRoom = Math.max(crashMaxRoom, res + roomSizeList.get(myRoomNumber));
    }

    static boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= H) || (x < 0 || x >= W)) {
            return true;
        } else {
            return false;
        }
    }

    static void bfs(int y, int x) {
        // bfs 를 통해서, 방의 번호를 매김
        Queue<int[]> queue = new LinkedList<>();
        visited[y][x] = true;
        queue.add(new int[] {y, x});
        int count = 0;

        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            map[point[0]][point[1]] = countRoom;
            count++;

            for (int i = 0; i < 4; i++) {
                if (wall[point[0]][point[1]][i]) { // 해당 방향으로 벽 막혀있으면 못감
                    continue;
                }

                int ny = point[0] + dy[i];
                int nx = point[1] + dx[i];

                if (outOfRange(ny, nx) || visited[ny][nx]) {
                    continue;
                }

                visited[ny][nx] = true;
                queue.add(new int[] {ny, nx});
            }
        }

        roomSizeList.add(count);
        maxRoom = Math.max(maxRoom, count);
    }

    static void fillWall(int y, int x, int value) {
        // 2 진수를 이용하여서, wall 을 채운다.
        for (int i = 0; i < 4; i++) {
            if ((value & (1 << i)) != 0) { // 해당 자리가 있을 때
                int dir = calDir(i);
                wall[y][x][dir] = true; // 벽 채우기
            }
        }
    }

    static int calDir(int dir) {
        // dir 을 계산해서 준다.
        // 원래 0 = 북, 1 = 동, 2 = 남, 3 = 서
        // 주어지는 것 0 = 서, 1 = 북, 2 = 동, 3 = 남
        return (dir == 0) ? 3
                : (dir == 1) ? 0
                : (dir == 2) ? 1 : 2;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        W = fun.apply(input[0]);
        H = fun.apply(input[1]);
        wall = new boolean[H][W][4];
        visited = new boolean[H][W];
        map = new int[H][W];

        for (int i = 0; i < H; i++) {
            input = br.readLine().split(" ");
            for (int j = 0; j < W; j++) {
                fillWall(i, j, fun.apply(input[j]));
            }
        }

        roomSizeList.add(0); // Mok data 집어넣음

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (!visited[i][j]) {
                    countRoom++; // room 증가
                    bfs(i, j);
                }
            }
        }

        System.out.println(countRoom);
        System.out.println(maxRoom);

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                crashWall(i, j); // 벽을 부셔가면서 한번 해본다.
            }
        }

        System.out.println(crashMaxRoom);
    }

    static void mapPrint() {
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
