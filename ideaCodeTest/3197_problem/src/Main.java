import java.util.*;
import java.io.*;

public class Main {
    public static int r, c, day = 0;
    public static int[][] visited;
    public static char[][] map;
    public static Point selfS, otherS;
    public static int[] dx = {0, 1, 0, -1};
    public static int[] dy = {-1, 0, 1, 0}; // 위 , 오른쪽 ,아래 , 왼쪽
    public static Deque<Point> water = new LinkedList<>();
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
                    water.add(new Point(i, j));
                    if (selfS == null) {
                        selfS = new Point(i, j);
                    } else {
                        otherS = new Point(i, j);
                    }
                }
                if (map[i][j] == '.') {
                    water.add(new Point(i, j));
                }
            }
        } //전처리 완료
        bfs(); //먼저 처음부터 만날 수 있나 확인 일단 얘는 이걸로 전처리하자 일단 , bfs해보고 바로 되면 0이 나와야함 , 근데 이제 아니다? 그러면 먼저 firstMelt를 해줘야함
        while (visited[otherS.y][otherS.x] != 1) {
            day++;
            melt(); //해준다음
            bfs(); //해서 visited[otherS.y][otherS.x]에 찍혀있으면 끄타는 것
        }
        System.out.println(day);
    }
    //일단 얼음을 녹이고 한다는 가정하에 , 백조의 다음 탐색 위치를 얼음이 현재 위치하는 곳으로 지정하는 것
    public static void melt() { //이거는 얼음을 녹이기 before에서 after를 녹여야함 , 그니까 before 를 진행하면서 after를 녹이는 것
        int size = water.size();
        for(int i = 0; i < size; i++){
            Point point = water.poll();
            for(int j = 0; j < 4; j++){
                int nx = point.x + dx[j];
                int ny = point.y + dy[j];
                if(ny < 0 || ny >= r || nx < 0 || nx >= c || map[ny][nx] != 'X'){
                    continue;
                }
                water.add(new Point(ny , nx));
                map[ny][nx] = '.'; //이러면서 녹이면서 이제 다음 녹을 지점을 정함
            }
        }
    }

    public static void bfs() { //무조건 스타트는 selfS 에서 이건 백조가 서로 만날 수 있냐 판단.
        if(day == 0){
            nowQueue.add(selfS);
            visited[selfS.y][selfS.x] = 1;
        }
        else{
            nowQueue = nextQueue;
        }
        nextQueue = new LinkedList<>();
        Loop1:
        while(!nowQueue.isEmpty()){
            Point point = nowQueue.poll();
            for(int i = 0; i < 4; i++){
                int nx = point.x + dx[i];
                int ny = point.y + dy[i];
                if(ny == otherS.y && nx == otherS.x){
                    visited[ny][nx] = 1;
                    break Loop1;
                }
                if(nx < 0 || nx >= c || ny < 0 || ny >= r || visited[ny][nx] == 1){
                    continue;
                }
                if(map[ny][nx] == 'X'){
                    nextQueue.add(new Point(ny , nx));
                    visited[ny][nx] = 1;
                    continue;
                }
                visited[ny][nx] = 1;
                nowQueue.add(new Point(ny , nx));
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
