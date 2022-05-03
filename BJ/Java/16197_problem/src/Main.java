import java.util.*;
import java.io.*;
import java.util.function.Function;

// 16197 : 두 동전

/**
 * -- 전제 조건
 * 동전이 두개가 주어지고, '.' 은 빈칸 (이동할 수 있는 칸)
 * '#' 은 벽, 'o' 는 동전으로 주어진다.
 *
 * 동전들은 상, 하, 좌, 우로 움직일 수 있고, 동전하나가 움직이면,
 * 나머지 동전도 같이 움직인다.
 *
 * 이러한 식의 게임이 주어졌을 때, 버튼을 최소한으로 눌러서 동전을 바깥으로 나가게 하였을 때, 누른 횟수를 구하시오
 *
 * -- 틀 설계
 * 일단, 완전 전체 경우로 다 해서, bfs 로 진행하면?
 * 그냥 가장 빨리 끝난 것을 체크 하면 된다.
 *
 * 근데, 그렇게 하면 선택지가 4개이다.
 * 그러면 오래 걸리지 않을까?
 *
 * 20 * 20 의 맵이 주어지고, 만일
 * 정말 맵이 400 칸을 다 돌아야 한다면? (그럴일은 없지만)
 * 4 ^ 400 이다.
 *
 * 물론 저럴 수는 없지만, 확실한 것은 엄청나게 많은 경우의 수가 나올 것이라는 것이다.
 * 그러면 어떻게 할 수 있을까
 *
 * 둘다 움직이지 않는 경우?
 * 둘다 벽이 있는 경우, 가려는 방향에
 * 그 경우는 queue 에다가 넣지 않아야 한다.
 *
 * 그리고 만약에, 두 동전의 위치가 이전과 똑같다면? 그것은 더 이상 진행할 필요가 없음
 * 어떤식으로 이동하는 경우냐 그런 것은
 * ->, <- 이러한 경우는 당연하게 동일한 경우이다.
 *
 * 하지만, 뭐 오른쪽, 아래, 왼쪽, 위쪽 이런식으로 진행했을 때 두 동전의 위치는 같지 않을 수도 있다.
 * 물론 ->, <- 를 했을 때도, 두 동전의 위치가 다르다면? 다른 경우이다.
 *
 * 동전의 위치로 방문처리가 가능할 것 같다.
 * 4차원 배열로서 관리하여 두 동전의 위치를 기억하자.
 *
 * 이런식으로 대충 백트래킹이 가능할 것 같고
 * 이제는 bfs 로 진행하면서, 동시에 나가는 경우는 그냥 버리고,
 * 그러지 않는 경우만을 살려서, 하나만 나가는 경우를 찾으면 된다.
 *
 * 일단, 동전 두개의 위치를 queue 로써 관리를 하고
 * 이동한 횟수를 저장하자.
 * 그래서 queue 에 들어가는 1차원 배열은 5개 짜리 배열이다.
 */
public class Main {
    public static int H;
    public static int W;
    public static int[] coin1 = new int[2]; // 첫번째 동전 위치
    public static int[] coin2 = new int[2]; // 두번째 동전 위치, 0 = y, 1 = x
    public static int res = -1;
    public static Queue<int[]> queue = new LinkedList<>();
    public static char[][] map;
    public static int[] dx = {0, 1, 0, -1};
    public static int[] dy = {-1, 0, 1, 0};
    public static boolean[][][][] visited;

    public static boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= H) || (x < 0 || x >= W)) {
            return true;
        } else {
            return false;
        }
    }

    public static void bfs() {
        queue.add(new int[] {coin1[0], coin1[1], coin2[0], coin2[1], 0});
        visited[coin1[0]][coin1[1]][coin2[0]][coin2[1]] = true;

        while (!queue.isEmpty()) {
            int[] point = queue.poll();

            if (point[4] >= 10) { // 이미 10번 눌렀는데, 안 끝났으면 action 해서 끝나면 11번임, 여기서 끊으면 됨
                continue;
            }

            for (int i = 0; i < 4; i++) {
                int ny1 = point[0] + dy[i];
                int nx1 = point[1] + dx[i];
                int ny2 = point[2] + dy[i];
                int nx2 = point[3] + dx[i];

                if (outOfRange(ny1, nx1) && outOfRange(ny2, nx2)) { // 둘다 나간 경우는 넘김
                    continue;
                } else if ((!outOfRange(ny1, nx1) && outOfRange(ny2, nx2)) // 메소드를 아얘 끝내준다, 이 경우까지는 벽에 막힐 일이 전혀 없음, 가장 자리에 있는 것이기 때문에, 벽에 떨어질 수가 없음
                        || (outOfRange(ny1, nx1) && !outOfRange(ny2, nx2))) { // 둘중 하나만 나간 경우
//                    System.out.println("coin1 : " + "(" + ny1 + ", " + nx1 + ")");
//                    System.out.println("coin2 : " + "(" + ny2 + ", " + nx2 + ")");
//                    System.out.println("value : " + point[4]);
                    res = point[4] + 1;
                    return;
                }

                if (map[ny1][nx1] == '#' && map[ny2][nx2] == '#') { // 둘다 벽인 경우는 갈 필요자체가 없음
                    continue;
                } else if (map[ny1][nx1] == '#') { // 원래 자리로 돌려줌
                    ny1 = point[0];
                    nx1 = point[1];
                } else if (map[ny2][nx2] == '#') { // 원래 자리로 돌려줌
                    ny2 = point[2];
                    nx2 = point[3];
                }

                if (visited[ny1][nx1][ny2][nx2]) { // 이미 방문한적이 있으면
                    continue;
                } else { // 방문한적도 없고, 둘다 벽인 것도 아니고, 둘다 나간것도, 끝난 것도 아니면 이제 움직여서 넣어주면 됨
                    visited[ny1][nx1][ny2][nx2] = true; // 동전이 이미 있는 것에 다른 동전이 가는 경우를 처리하라면 귀찮아지지만, 동전이 겹치는 게 가능해서 나이스
                    queue.add(new int[] {ny1, nx1, ny2, nx2, point[4] + 1});
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        H = fun.apply(input[0]);
        W = fun.apply(input[1]);

        map = new char[H][W];
        visited = new boolean[H][W][H][W];
        Arrays.fill(coin1, -1); // coin1 을 아직 못찾았으면 -1 임 y, x 값이

        for (int i = 0; i < H; i++) {
            String string = br.readLine();
            for (int j = 0; j < W; j++) {
                map[i][j] = string.charAt(j);

                if (coin1[0] == -1 && map[i][j] == 'o') { // coin1 찾음
                    coin1[0] = i;
                    coin1[1] = j;
                } else if (map[i][j] == 'o') { // coin2 찾음
                    coin2[0] = i;
                    coin2[1] = j;
                }
            }
        }

        bfs();

        System.out.println(res);
    }
}
