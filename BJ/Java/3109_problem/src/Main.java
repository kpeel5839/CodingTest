import java.util.*;
import java.io.*;
import java.util.function.Function;

// 3109 : 빵집

/**
 * -- 전제 조건
 * 빵집과 가스관을 연결하는데
 * 가스관은 1열이고, 마지막 열은 빵집이다.
 *
 * 이렇게 주어졌을 때, 각 파이프라인은 오른쪽, 오른쪽 위, 오른쪽 아래 이런 식으로 진행시킬 수 있고
 * 가스관들은 겹쳐서는 안된다.
 * 이럴 경우, 최대 몇개의 파이프 라인을 연결할 수 있는지 구하여라
 * -- 틀 설계
 * 일단 map 을 먼저 만든다.
 * 솔직히 너무 귀찮아서 문제 카테고리를 봤는데 그리디가 카테고리로 있었다.
 *
 * 그 말은 즉, dfs 를 통해서, 항상 그리디한 선택을 진행한다라는 것이다.
 *
 * 일단, bfs 가 아닌 dfs 를 통해서 하는 이유는
 * 파이프를 선택했을 때, 결국 연결이 되었을 때, 그때 visited 를 해야할 것 같아서이다.
 *
 * 그래서 dfs 를 통해서 위, 오른쪽, 아래 이 순으로 1행에서부터 시작해야 할 것 같다.
 * 일단, R * C 이니, 10000개의 행이 존재하지만 길이는 500 정도로 바껭 안되니 충분히 시도해볼만한
 * 아이디어인 것 같다.
 *
 * 만약에 된다? 그러면 생각보다 간단한 문제로 보인다.
 *
 * -- 해맸던 점
 * 생각보다 DP 적인 요소들도 활용해야 하는 문제였음
 * 일단, 첫번째 visited 배열로 하게 되면 문제점이 무엇이였냐
 * 어떤 경로로 갔을 때, 실패하게 되면? 그 경로에 사용된 모든 정점은?
 * 재 사용되면 안된다. (0번 째 열에서부터 시작해서 최적의 경로를 못찾은 경우는)
 * 그럴려면 vistied 로도 가능하지만. dp 식으로 해서, 해결하지 못하였더라도 무조건 방문 처리를 해줘야 하는 것이다.
 * 이것은 어떻게 이용한 것이냐면, 이미 방문했을 때, 성공하던 실패하던 성공하면 가치있지만, 이미 파이프가 자리를 차지 하고 있고
 * 실패하면 파이프가 자리를 차지하고 있지는 않지만, 여기로 가면 성공을 하지 못한다, 즉 성공하는 겨웅의 수가 존재하지 않는다라는 것을 알 수 있음
 *
 * 물론 이전 방법도 맞지만, 시간 초과가 무조건 나는 코드였음
 * 그래서 이런식으로 바꾸니까 맞았고
 * 해맸던 점으로는 dp[y][x] != 0 부분에서 return 0 을 해서, res = 0 이 되서
 * dp[y][x] = 0 이 채워지게 되면서 마치 아직 방문하지 않은 것처럼 되었음
 *
 * 그리고 return dp[y][x] 를 했을 때에는, 성공하지 못했는데,
 * 마치 성공한 것처럼 다른 자리를 찾아보지도 못하고 반복문이 끝나게되었음
 * 근데 그냥 dp[y][x] = 0, 1, 2 로 관리하는 것이 아닌
 * boolean 변수로도 가능할 것 같다. 한번 해보자.
 *
 * 위와 같은 이론을 적용해, boolean 변수로 정리하니 훨씬 깔끔하다.
 */
public class Main {
    static int H;
    static int W;
    static int count = 0;
    static int[] dx = {1, 1, 1};
    static int[] dy = {-1, 0, 1};
    static char[][] map;
    static boolean[][] visited;

    static boolean dfs(int y, int x) {
//        System.out.print("(" + y + ", " + x + ") -> ");
        if (x == (W - 1)) { // 끝에 도달한 경우, true 를 반환하면서, 해당 지점을 visited[y][x] = true 로 바꿔준다.
            count++;
            visited[y][x] = true;
            return true;
        }

        if (visited[y][x]) { // 여기로 오면 실패인지, 아닌지 알려준다. (아직 방문하지 않았다면 0임)
            return false;
        }

        boolean res = false;

        for (int i = 0; i < 3; i++) { // 오른쪽 위부터 선택하는 그리디한 선택을 한다.
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (outOfRange(ny, nx) || map[ny][nx] == 'x') { // 못가는 곳이나, 이미 방문한 곳일 때
                continue;
            }

            res = dfs(ny, nx);

            if (res) { // 성공한 경우
                break;
            }
        }

        visited[y][x] = true; // 여기는 그냥 성공하거나, 실패하였으니 오면 안된다.
        return res;
    }

    static boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= H) || (x < 0 || x >= W)) {
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
        map = new char[H][W];
        visited = new boolean[H][W];

        for (int i = 0; i < H; i++) {
            String string = br.readLine();
            for (int j = 0; j < W; j++) {
                map[i][j] = string.charAt(j);
            }
        }

        for (int i = 0; i < H; i++) {
//            System.out.print("시작 : (" + i + ", 0) -> ");
            dfs(i, 0);
//            System.out.println();
        }

        System.out.println(count);
    }
}