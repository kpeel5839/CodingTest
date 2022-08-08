import java.util.*;
import java.io.*;

/**
 * 예제
 * 6
 * 1 1 0 0 1 0
 * 0 0 1 0 1 0
 * 0 1 1 0 0 1
 * 1 1 0 1 1 1
 * 1 0 0 0 1 0
 * 0 1 1 1 0 0
 * 6
 * 1 0 0 1 1 0
 * 1 0 1 0 1 0
 * 0 1 1 0 1 1
 * 0 0 1 0 0 0
 * 1 1 0 1 1 0
 * 0 1 0 0 0 0
 *
 * 이 문제는 다 맞게 잘 작성했다.
 * 하지만, 간과하고 있었던 게 있었다.
 * 난 당연히 dfs 로 돌리면서 계속 돌아갔다가 하면서 풀 생각을 하고 있었다
 *
 * 하지만, 그럴 필요가 없었다.
 * 시간초과가 나고나서 다른 사람들은 비교연산을 어떻게 실행하느냐 보려고, 답을 보고 있는데 다 spin, 어떻게 퍼즐을 추출하는지에 대한 별로 어렵지 않은 부분들에 대해서 얘기하고있었다.
 *
 * 그래서, 나는 아니 어떻게 비교했는데 이렇게 했는데도 다 통과했지? 생각을 하면서 보고 있는데, 생각이 번뜩하고 들었다.
 * 아 퍼즐을 맞추면? 여기에 들어갈 수 있는 놈은 회전했을 때, 얘와 같은놈이다. 호긍ㄴ 회전하지 않아도 같은 놈일수도 있다. 사실 근데, 그것은 중요치 않다.
 *
 * 결국 회전해서 같다? 이거는 같다라는 의미다. 그냥 같은 것이다.
 *
 * 그렇기 때문에, 그냥 같은 것들이니까, 다시 돌아가서 다른애들을 넣어보는 시도 따윈 하지 않아도 되겠구나, 왜냐? 다른애들을 또 넣으면? 그냥 똑같은놈 또 넣는 것이다.
 * 즉, 쓸데 없는 중복 연산을 해서 시간을 허비하는 것이 되었다.
 *
 * 그래서, 이 부분을 그냥 board 에서 추출한 도형들을 순서대로 돌면서 남아있는 퍼즐 조각을 채워넣는 형식 (맞은 애가 있으면 그냥 바로 넣는다. 여기 들어갈 수 있는 애가 또 있다면 걔는 나랑 같은 놈이기 때문에, 짜피 상관이 없음)
 * 으로 바꿨더니, 굉장히 빠르게 통과하였다.
 *
 * 가끔 시간에 쫓겨 생각을 너무 짧게 하는 경향이 있는 것 같다.
 *
 * 삼성 PRO 시험 볼때에는 시간이 많아서 충분히 사고 할 수 있어서 좋았다. 앞으로도 계속 사고 하는 습관을 기르고, 오래 사고하는 것을 두려워하지 말아야겠다.
 *
 * 오래 사고할수록 성장하는 것이니까
 */
class Solution {
    static int N;
    static int puzzleCnt = 0; // 퍼즐의 개수
    static int boardCnt = 0; // 보드에 퍼즐이 들어갈 빈칸의 개수
    static int ans = 0;
    static int[] cnt = new int[500]; // 각 퍼즐 도형의 정사각형 개수
    static boolean[] used = new boolean[500]; // puzzle 에서 몇번을 썼는지 나타낸다.
    static boolean[][] visited;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static int[][] numbering; // 각 도형의 번호 (퍼즐, 보드) 를 먹이기 위해서 numbering 을 하는 배열
    static boolean[][][][] puzzle = new boolean[500][4][][];
    static boolean[][][] board = new boolean[500][][]; // 각 최대 2500 개까지 가능하기 때문에 이렇게 진행한다.

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/PROGRAMMERS/Algorithm/_84021_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int[][] gameBoard = new int[N][N];
        int[][] table = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                gameBoard[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        br.readLine();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                table[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.print(solution(gameBoard, table));
    }

    static boolean matching(int boardNumber, int puzzleNumber) { // rotate 는 그냥 4 방향 무조건 본다.
        // 맞았을 경우 return true 를 해준다.
        int by = board[boardNumber].length;
        int bx = board[boardNumber][0].length;

        for (int k = 0; k < 4; k++) {
            boolean fail = false;

            int py = puzzle[puzzleNumber][k].length;
            int px = puzzle[puzzleNumber][k][0].length;

            if (by != py || bx != px) { // 두개 중 하나만 안맞아도 같을 확률 없음
                continue;
            }

            Loop:
            for (int i = 0; i < by; i++) {
                for (int j = 0; j < bx; j++) {
                    if (board[boardNumber][i][j] != puzzle[puzzleNumber][k][i][j]) { // 다른 순간 매칭 x
                        fail = true;
                        break Loop;
                    }
                }
            }

            if (!fail) {
                return true;
            }
        }

        return false;
    }

    static void rotate(int puzzleNumber) { // puzzle Number 를 받으면 해당 puzzle 0 으로 1, 2, 3 을 생성한다.        
        for (int k = 1; k < 4; k++) {
            int py = puzzle[puzzleNumber][k - 1].length;
            int px = puzzle[puzzleNumber][k - 1][0].length;
            puzzle[puzzleNumber][k] = new boolean[px][py]; // 회전하니까 반대로 뒤집어서 새로 붙힘

            for (int i = 0; i < py; i++) {
                for (int j = 0; j < px; j++) {
                    puzzle[puzzleNumber][k][j][py - 1 - i] = puzzle[puzzleNumber][k - 1][i][j];
                }
            }
        }
    }

    static void sampling(int y, int x, int number, boolean b, int[][] map) { // bfs 로 sampling 을 진행하는데, board = true 이면, board 에 대한 것이고, false 이면 puzzle 에 관한것
        // 해당 도형의 number 를 기록하고 bfs 로 먼저
        // bfs 로 돌면서 도형의 개수를 측정한다. (정사각형 요소의 개수)
        // 그리고 Y(min), Y(max), X(min), X(max) 들도 다 구해낸다.
        // 그리고 위와 같이 min, max 를 이용해서 사이즈를 구하고 board = true 이면 board = new boolean[][], false 이면 new boolean[][][4]; 를 해준다.
        // 그리고서 그냥 board 이면, 그냥 순서대로, false 이면 0번째에다가 위치에 맞게 true 를 넣어준다.
        int rectCnt = 1;
        int yMin = Integer.MAX_VALUE;
        int yMax = 0;
        int xMin = Integer.MAX_VALUE;
        int xMax = 0;

        Queue<int[]> queue = new LinkedList<>();
        visited = new boolean[N][N];
        queue.add(new int[]{y, x});
        numbering[y][x] = number;
        visited[y][x] = true;

        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            yMin = Math.min(yMin, point[0]);
            yMax = Math.max(yMax, point[0]);
            xMin = Math.min(xMin, point[1]);
            xMax = Math.max(xMax, point[1]); // 갈 수 있는 경우 max, min 을 구해준다.

            for (int i = 0; i < 4; i++) {
                int ny = point[0] + dy[i];
                int nx = point[1] + dx[i];

                if (outOfRange(ny, nx) || visited[ny][nx] || (b ? map[ny][nx] != 0 : map[ny][nx] != 1)) {
                    continue;
                }

                numbering[ny][nx] = number;
                visited[ny][nx] = true;
                queue.add(new int[] {ny, nx});
                rectCnt++; // 도형의 개수를 세준다.
            }
        }


        int ySize = yMax - yMin + 1;
        int xSize = xMax - xMin + 1;

        if (b) { // number 은 1부터 시작이니까
            board[number - 1] = new boolean[ySize][xSize];
        } else {
            puzzle[number - 1][0] = new boolean[ySize][xSize];
            cnt[number - 1] = rectCnt;
        } // 배열 선언해주고

        for (int i = yMin; i <= yMax; i++) {
            for (int j = xMin; j <= xMax; j++) {
                if (numbering[i][j] == number) { // 현재 도형으로 체크가 된 곳만
                    if (b) {
                        board[number - 1][i - yMin][j - xMin] = true;
                    } else {
                        puzzle[number - 1][0][i - yMin][j - xMin] = true;
                    }
                }
            }
        }

        if (!b) { // board 가 아닐 때에만 해야함
            rotate(number - 1); // rotate 까지 해준다.
        }
    }

    static void findAnswer() {
        // 솔직히 진짜 최대 500개로 나오면? 절대 답 제 시간안에 못구함, 이 경우 시간을 줄일 방법을 찾아야 한다.
        // 일단은 현재 방법으로는 dfs 로 돌면서 보드[depth] 와 비교하고, 맞으면 score += cnt[i] 하고 넘어가야함
        // 그거 계속 반복하면 되고, 절대로 depth 는 boardCnt 를 따라야하고,
        // puzzle 의 개수도 무조건 puzzleCnt 를 따라야 한다. 나머지는 의미없는 애들이라서
        for (int i = 0; i < boardCnt; i++) {
            for (int j = 0; j < puzzleCnt; j++) {
                if (!used[j]) { // 아직 안쓴놈만
                    if (matching(i, j)) {
                        ans += cnt[j];
                        used[j] = true;
                        break; // 맞았으니까
                    }
                }
            }
        }
    }

    static boolean outOfRange(int y, int x) {
        if (y < 0 || y >= N || x < 0 || x >= N) {
            return true;
        } else {
            return false;
        }
    }

    public static int solution(int[][] game_board, int[][] table) {
        N = game_board.length; // board, table 완전 사이즈 같음

        numbering = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (game_board[i][j] == 0 && numbering[i][j] == 0) { // 빈칸이면서, 아직 번호가 매겨져 있지 않은 곳
                    boardCnt++;
                    sampling(i, j, boardCnt, true, game_board);
                }
            }
        }

        numbering = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (table[i][j] == 1 && numbering[i][j] == 0) { // 빈칸이면서, 아직 번호가 매겨져 있지 않은 곳
                    puzzleCnt++;
                    sampling(i, j, puzzleCnt, false, table);
                }
            }
        }

        findAnswer();

        return ans;
    }
}