import java.util.*;
import java.io.*;

public class Main {
    public static int r, c, day = 0;
    public static int[][] visited;
    public static char[][] map;
    public static Point selfS, otherS;
    public static int[] dx = {0, 1, 0, -1};
    public static int[] dy = {-1, 0, 1, 0}; // 위 , 오른쪽 ,아래 , 왼쪽
    public static Deque<Point> nextMelt = new LinkedList<>(), nowMelt = new LinkedList<>();
    public static Deque<Point> nowQueue = new LinkedList<>(), nextQueue = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        //다시 구성해보자 ...
        //일단 처음에 다 돌면서 녹일 수 있는 얼음들을 다 녹인다 , 근데 그거를 queue에다가 집어 넣으면서 다음 녹이는 얼음의 위치를 큐에다가 담는다.
        //처음 맵만 딱 돌아야하지 않을까 처음에는 돌아야 할 것 같은데...
        //백조도 visited로 처음에 관리하면서 , 벽에 막힌 놈들을 queue에다가 담음 , 그러고서 그걸로 진행하는 거야 그러면 visited로 관리하면서 queue에서 빼면 되니까
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        visited = new int[r][c];
        map = new char[r][c];

        for (int i = 0; i < r; i++) {
            String string = input.readLine();
            for (int j = 0; j < c; j++) {
                map[i][j] = string.charAt(j);
                if (map[i][j] == 'L') {
                    nextMelt.add(new Point(i, j));
                    if (selfS == null) {
                        selfS = new Point(i, j);
                    } else {
                        otherS = new Point(i, j);
                    }
                }
                if (map[i][j] == '.') {
                    nextMelt.add(new Point(i, j));
                }
            }
        } //전처리 완료
        bfs(); //먼저 처음부터 만날 수 있나 확인 일단 얘는 이걸로 전처리하자 일단 , bfs해보고 바로 되면 0이 나와야함 , 근데 이제 아니다? 그러면 먼저 firstMelt를 해줘야함
        while (visited[otherS.y][otherS.x] != 1) {
            day++;
            melt(); //해준다음
            bfs(); //해서 visited[otherS.y][otherS.x]에 찍혀있으면 끄타는 것
//            for (int q = 0; q < 2; q++) {
//                if (q == 0) {
//                    System.out.println("lake");
//                }
//                if (q == 1) {
//                    System.out.println("visited");
//                }
//                for (int i = 0; i < r; i++) {
//                    for (int j = 0; j < c; j++) {
//                        if (q == 0) {
//                            System.out.print(map[i][j] + " ");
//                        }
//                        if (q == 1) {
//                            System.out.print(visited[i][j] + " ");
//                        }
//                    }
//                    System.out.println();
//                }
//            }
        }
        System.out.println(day);

    }

    public static void melt() { //이거는 얼음을 녹이기 before에서 after를 녹여야함 , 그니까 before 를 진행하면서 after를 녹이는 것
        for (Point point : nextMelt) { //deep copy
            nowMelt.add(point);
        }
        nextMelt.clear(); //자 일단 비우고
        while (!nowMelt.isEmpty()) {
            Point curP = nowMelt.poll(); //nowMelt에서는 계속 뽑으면서
            int curX = curP.x;
            int curY = curP.y;
            for (int i = 0; i < 4; i++) { //4방향으로 순환함
                int newY = curY + dy[i];
                int newX = curX + dx[i];
                if ((newY < 0 || newY >= r) || (newX < 0 || newX >= c) || map[newY][newX] != 'X') { //그러면서 X가 아닌 것들만 넘기고
                    continue;
                }
                nextMelt.add(new Point(newY, newX));
                map[newY][newX] = '.';
            }
        }

    }

    public static void bfs() { //무조건 스타트는 selfS 에서 이건 백조가 서로 만날 수 있냐 판단.
        if (day == 0) {
            nowQueue.add(selfS); //시작 백조를 집어넣고
            visited[selfS.y][selfS.x] = 1; //현재 시작 백조의 위치에다가 방문 표시를함
        } else {
            for (Point point : nextQueue) { //deep copy
                nowQueue.add(point); //일단 여기까지 왔으면 이전에 queue가 다 비워져있는 상태임
            }
            nextQueue.clear(); //자 일단 비우고
        }
        Loop1:
        while (!nowQueue.isEmpty()) { //queue가 비지 않았을 때 true , 진행이라는 것
            Point curP = nowQueue.poll();
            int curX = curP.x;
            int curY = curP.y;
            for (int i = 0; i < 4; i++) {
                int newY = curY + dy[i];
                int newX = curX + dx[i]; //사전에 이미 방문하였음..
                if ((newY < 0 || newY >= r) || (newX < 0 || newX >= c) || visited[newY][newX] == 1 || map[newY][newX] == 'X') {
                    continue;
                } //범위를 넘었거나 , 아니면 이미 방문한 곳이면 그냥 넘어감
                //이제 다 처리했으니까 그냥 넣음
                for(int j = 0; j < 4; j++) { //미리 간거에서 예측을 해서 주변을 다 탐색해서 찾아봄 이게 끝에 다다른 놈인지
                    if (!(newY + dy[j] < 0 || newY + dy[j] >= r) && !(newX + dx[j] < 0 || newX + dx[j] >= c) && map[newY + dy[j]][newX + dx[j]] == 'X') {
                        nextQueue.add(new Point(newY, newX)); //한칸 간다음 해당 방향으로 한칸 더 가서 x이면 애를 넣어줌 ,
                        break;
                    }
                }
                visited[newY][newX] = 1;
                if (newX == otherS.x && newY == otherS.y) {
                    break Loop1;
                } //일단 처음에 만나면 장땡임
                nowQueue.add(new Point(newY, newX)); //queue에다가 그냥 넣었음 , 근데 여기서 이제 해당 point가 L이면 끝내야함
            }
        }
    }

    public static class Point {
        int y;
        int x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}
