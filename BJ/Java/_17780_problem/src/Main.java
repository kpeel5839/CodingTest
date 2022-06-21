import java.util.*;
import java.io.*;
import java.util.function.Function;

// 17780 : 새로운 게임

/**
 * -- 전제조건
 * 체스판에서 말을 가지고 게임을 하는 것이다.
 *
 * 일단 말을 이동할 때 규칙이 있다.
 * 모두 현재 내가 가지고 있는 방향으로 한칸 움직일 경우
 * 다음 칸에 따라서 이동조건은 분기 되게 된다.
 *
 * 체스판의 각 칸은 흰색, 빨간색, 파란색으로 이루어져 있다.
 * 그리고 게임을 진행할 떄 체스판 위에 말 K 개를 놓고 시작한다.
 *
 * 방향은 인접한 4방향이다.
 * 턴 한번은 1번 말부터 K 번 말 까지 순서대로 이동시키기만 하면 된다.
 *
 * 여기서 또 내가 간과할 수도 있었던 조건이 있다. 가장 아래에 있는 말만 이동할 수 있다라는 것이다.
 * 턴이 진행되던 중 말이 4개 이상 쌓이게 되면 게임은 종료된다.
 *
 * A번 말이 이동하려는 칸이 흰색인 경우에는 그냥 이동하려는 칸에 말을 옮겨 놓으면 된다.
 * 근데, 본인의 위에 있는 말들도 다 같이 옮겨야 한다.
 * 보다시피 흰색은 별거 없다.
 *
 * 빨간색인 경우에는 이동한 후에 A 번 말과 그 위에 있는 모든 말의 쌓여있는 순서가 반대로 바뀐다.
 * 근데 당연하게도 모든 경우에 이미 말이 있던 자리에는 절대 걔내들이 바뀌지는 않는다.
 *
 * 파란색인 경우에는 A 번 말의 이동방향을 반대로 하고 한칸 이동한다.
 * 반대로 가려했는데 거기도 파란색이다 (파란색 자리 혹은 체스판 바깥) 그러면 그냥 방향을 바꾼 그 상태를 유지하면 된다.
 *
 * 체스판의 크기와 말의 위치 이동 방향이 모두 주어졌을 때, 게임이 종료되는 턴의 번호를 구해라.
 * 0 = 흰, 1 = 빨, 2 = 파란색이다.
 * 이동 방향은 4보다 작거나 같고, 1 = 오, 2 = 왼, 3 = 위, 4 = 아래를 가진다.
 *
 * -- 틀 설계
 * 그냥 빡 구현 문제이다.
 * 솔직히 생각할 것도 많이 없다.
 *
 * 일단 map 을 입력받고
 * 내가 이동하려는 칸을 찾기 이전에 가장 중요한 것이 있다.
 * 턴을 진행함에 있어서 모든 말을 진행해야 한다.
 * 그리고 그 말이 가장 마지막에 있는 경우에만 진행한다.
 *
 * 이 문제는 정말 너무 쉬운 것이
 * 그냥 아래만 검사해주면 된다.
 *
 * 그래서 Point 를 말의 개수만큼 선언해준다음에
 * 처음에 주어진 말의 위치를 List[][] 배열에다가 넣어준다.
 *
 * 그 다음에 조건에 따라서 행동해주기만 하면 된다.
 * 일단 Point 를 계속 조정하면서 모든 말을 찾아준다.
 *
 * 그 다음에 말의 위치에 가서 .get(0) 이 말의 번호가 맞는지 확인한다.
 * 아니면 진행하지 않는다.
 *
 * 만일 맞다 그러면 말이 가지고 있는 방향대로 진행해준다.
 * 그리고 흰색이면 그대로 그냥 List 그대로 옮겨주고 초기화 해준다.
 *
 * 빨간색이면 그냥 반대로 센다 그 다음에 초기화 해준다.
 *
 * 파란색이면 그냥 방향 반대로 돌려서 그 방향에 있는 칸에 맞춰서 진행해준다.
 * 근데 여기서 다시 파란색인 경우 혹은 빈칸인 경우는 예외적으로 처리해주어야 한다.
 *
 * -- 결과
 * 진짜 개 어이없다
 * 로직 완벽하게 짜놓고서
 * 도대체 뭐가 틀리지 이러면서 디버깅만 오지게 하면서 버렸음
 *
 * 문제는 이거였음
 * 파란색 타일을 밟는 경우에
 * 뭐 파란색 타일을 만나서 반대로 가가지고 4개가 되는 경우가 있었다고 가정하면
 *
 * 일단, 움직인 다음에 4개가 되었으니까 true 를 반환하긴 할 것이다.
 *
 * 하지만 이렇게 바보같이 생각한 것이 문제였다.
 * 당연한거 였다 해당 함수를 호출한놈은 gameStart 의 for 문 안에서 호출했다.
 *
 * 그리고 파란색 타일을 만나서 반대도 파란색이 아니여서 이동한 경우, 즉 체스말이 4개가 모일 수 있는 가능성을 가진 경우에서
 * 함수를 호출하는 것은?
 * gameStart 가 아닌 execute 함수이다.
 *
 * 즉, 체스말이 4개가 되가지고 true 를 반환한다고 하더라도 반환을 받는 것은 execute 함수이고
 * 그래서 그 값을 return execute(y, x) 했으면 해결이 되었을 텐데 개 바보같이 거기서 또 아래로 가서 return 했다 내가 도대체 왜 이랬지?
 * 너무 다른 것에 눈이 멀었었다. 다른 것으로 틀렸을 것이라고 생각했었다.
 *
 * 그래서 execute 함수가 true 를 return 받게 되고 이 값으로 아무 것도 처리하지 않으니까 무의미한 true 값이다.
 * 즉 이전에 문제는 파란색 타일을 만나 reverseDir 해서 반대로 가서 체스말이 4개가 모인다고 하더라도 종료할 수가 없었던 것이다.
 *
 * 그거해봤자 짜피 사용하지도 않고 if else 문 빠져나가가지고 당연히 본인 말은 움직이지 않거나 이미 움직여서 clear 되었을 테니까
 * true 를 반환할 수가 없다 당연히 false 가 되는 것이다.
 *
 * 이 경우 boolean gameOver 가 당연하게 true 가 될 수가 없다. 본인이 부른 execute 메소드는 false 를 반환했으니까 지가 안에서 메소드 재귀적으로 호출해서
 * 값 받은 거 처리도 안하고 그냥 본인한테 false 를 반환한 개 멍청한 메소드였던 것이다.
 *
 * 그래서 gameOver를 전역변수로 만들어서 execute 에서 아무것도 반환하지 않게 하던가 혹은
 * return execute(y, x) 를 하는 방법이 있음..
 *
 * 진짜 이것 때문에 1시간 정도 날렸는데 진짜 개멍청하다 재연아..
 */

public class Main {
    static int N;
    static int K;
//    static boolean gameOver = false;
    static int cnt = 0;
    static Point[] horseList;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static int[][] map;
    static List<ArrayList<ArrayList<Horse>>> chess = new ArrayList<>(); // 실질적으로 여기 안에다가 horse 객체를 넣어놓을 것이다. 걔내들은 방향도 가지고 있음

    static class Point {
        int y;
        int x;

        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public String toString() {
            return "y : " + y + ", x : " + x;
        }
    }

    static class Horse {
        int number;
        int dir;

        public Horse(int number, int dir) {
            this.number = number;
            this.dir = dir;
        }

        @Override
        public String toString() {
            return "number : " + (number + 1) + ", dir : " + dir;
        }
    }

    static int convertDir(int dir) {
        return (dir == 3) ? 0
                : (dir == 4) ? 2
                : (dir == 2) ? 3 : dir;
    }

    static int reverseDir(int dir) {
        return (dir + 2) % 4; // 0 -> 2, 2 -> 0, 1 -> 3, 3 -> 1 이 가능한 마법의 구문
    }

    static boolean outOfRange(int y, int x) {
        if (y < 0 || y >= N || x < 0 || x >= N) {
            return true;
        } else {
            return false;
        }
    }

    static boolean gameStart() {
        /**
         * 턴을 실행하는 메소드이다.
         * chess 를 가지고 1번부터 실행하면 된다.
         * Point 를 순서대로 돌면서 진행하면 된다.
         */
//        gameOver = false;
        boolean gameOver = false;

        for (int i = 0; i < K; i++) { // 4 개가 쌓이는 경우는 게임을 끝내주어야 한다.
            int y = horseList[i].y;
            int x = horseList[i].x;

            if (chess.get(y).get(x).get(0).number == i) { // 이 경우에만 진행한다. 가장 아래에 깔려 있는 경우 가장 먼저 추가된 경우
//                execute(y, x);
                gameOver = execute(y, x); // 여기를 실행하라고 넘겨준다.
            }

//            print(cnt);

            if (gameOver) {
                break;
            }
        }

        return gameOver; // true 이면 게임이 끝날 수 있도록
    }

    static boolean execute(int y, int x) { // 해당 포지션에 있는 가장 아래에 있는 놈을 주어진 방향대로 움직여준다.
        Horse now = chess.get(y).get(x).get(0); // 현재 chess 얻어주고

        int ny = y + dy[now.dir];
        int nx = x + dx[now.dir]; // 움직일 방향을 정해줌

        if (outOfRange(ny, nx) || map[ny][nx] == 2) { // outOfRange 인 경우랑 파란색인 경우를 같이 처리
            int reverse = reverseDir(now.dir);

            now.dir = reverse; // 파란색을 만난 순간 이미 방향은 뒤집어야함
            int nny = y + dy[now.dir];
            int nnx = x + dx[now.dir];

//            if (!(outOfRange(nny, nnx) || map[nny][nnx] == 2)) { // 반대 방향은 파란색이 아닐 때
//                return execute(y, x); // 다시 현재 방향만 바꾼 상태에서 넘겨준다.
//            } // 1번째 방법

            if (!(outOfRange(nny, nnx) || map[nny][nnx] == 2)) { // 반대 방향은 파란색이 아닐 때
                return execute(y, x); // 다시 현재 방향만 바꾼 상태에서 넘겨준다.
            } // 2번째 방법
        }
        else if (map[ny][nx] == 0) { // 흰색인 경우
            // 흰색인 경우는 그냥 0 번째 순서대로 순서대로 읽어가면서 ny, nx 에다가 집어넣으면 된다.
            for (Horse horse : chess.get(y).get(x)) {
                horseList[horse.number] = new Point(ny, nx);
                chess.get(ny).get(nx).add(horse);
            }

            chess.get(y).get(x).clear();
        } else if (map[ny][nx] == 1){ // 빨간색인 경우
            int size = chess.get(y).get(x).size();

            for (int i = size - 1; i != -1; i--) { // 사이즈만큼 진행해주면서 반대로 집어넣어준다.
                Horse horse = chess.get(y).get(x).get(i);
                horseList[horse.number] = new Point(ny, nx);
                chess.get(ny).get(nx).add(horse);
            }

            chess.get(y).get(x).clear();
        }

//        if (!outOfRange(ny, nx) && chess.get(ny).get(nx).size() >= 4) { // 1번째 방법
//            gameOver = true; // 한번 이렇게 반환하는 형식이 아닌 gameOver 를 변경하는 형식을 취해보자, 근데 내가봤을 때는 이게 맞음...
//        }

        if (!outOfRange(ny, nx) && chess.get(ny).get(nx).size() >= 4) { // 2번째 방법
            return true; // 한번 이렇게 반환하는 형식이 아닌 gameOver 를 변경하는 형식을 취해보자, 근데 내가봤을 때는 이게 맞음...
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");

        N = fun.apply(input[0]);
        K = fun.apply(input[1]);
        horseList = new Point[K];
        map = new int[N][N];

        for (int i = 0; i < N; i++) {
            input = br.readLine().split(" ");
            chess.add(new ArrayList<>());
            for (int j = 0; j < N; j++) { // 입력을 받아주면서, List 를 초기화
                chess.get(i).add(new ArrayList<>());
                map[i][j] = fun.apply(input[j]);
            }
        }

        for (int i = 0; i < K; i++) { // 말을 입력받음
            input = br.readLine().split(" ");
            int y = fun.apply(input[0]) - 1;
            int x = fun.apply(input[1]) - 1;
            int dir = convertDir(fun.apply(input[2]));
            horseList[i] = new Point(y, x);
            chess.get(y).get(x).add(new Horse(i, dir));
        }

        while (true) {
            cnt++;

            if (gameStart()) {
                break;
            }

            if (1000 < cnt) { // cnt 가 1000보다 큰 경우 (게임이 끝나지 않는 경우)
                cnt = -1;
                break;
            }
        }

//        mapPrint();
//        System.out.println("chess size : (" + chess.size() + ", " + chess.get(0).size() + ")");
        System.out.println(cnt);
    }

    static void print(int turn) {
        System.out.println(turn + " 번째 턴");
        for (int i = 0; i < K; i++) {
            System.out.print((i + 1) + " 번째 말 위치 : " + horseList[i] + " 말의 방향 : ");
            for (Horse horse : chess.get(horseList[i].y).get(horseList[i].x)) {
                if (horse.number == i) {
                    System.out.println(horse.dir);
                    break;
                }
            }
        }
    }

    static void mapPrint() {
        System.out.println("mapPrint");

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void checkClear() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (chess.get(i).get(j).size() > 0) {
                    System.out.println("비어있지 않은 곳 : (" + i + ", " + j + ") size = " + chess.get(i).get(j).size());
                }

                for (Horse horse : chess.get(i).get(j)) {
                    System.out.println(horse);
                }
            }
        }
    }
}
