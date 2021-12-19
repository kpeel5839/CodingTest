import java.util.*;
import java.io.*;

public class Main {
    public static int n , m , t;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static float[] average;
    public static int[][] sector , tempSector;
    public static Queue<Point> queue = new LinkedList<>();
    public static List<Point> close = new ArrayList<>();
    public static int[][] instruct , visited;
    public static class Point{
        int y;
        int x;
        public Point(int y, int x){
            this.y = y;
            this.x = x;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken()); //t는 회전하는 명령 개수

        sector = new int[n][m];
        tempSector = new int[n][m];
        instruct = new int[t][3];
        visited = new int[n][m];
        average = new float[n];

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < m; j++){
                sector[i][j] = Integer.parseInt(st.nextToken());
                tempSector[i][j] = sector[i][j];
            }
        }
        //지워진 수는 -1로 표현하자

        for(int i = 0; i < t; i++){
            st = new StringTokenizer(input.readLine());
            instruct[i][0] = Integer.parseInt(st.nextToken()); //원판의 배수
            //이 부분은 추후에 배수로 회전시킬 때 , 그 때 sector[배수 - 1][] 이렇게 회전시켜야함
            //조건은 곱하다가 사이즈가 더 커지면 멈추면 됨
            instruct[i][1] = Integer.parseInt(st.nextToken()); //방향
            instruct[i][2] = Integer.parseInt(st.nextToken()); //회전시키는 횟수
        }

        for(int i = 0; i < t; i++){
            clean();
            spin(instruct[i][0] , instruct[i][1] , instruct[i][2]);
            boolean existsClose = false;
            for(int j = 0; j < n; j++){
                for(int c = 0; c < m; c++){
                    if(visited[j][c] == 1){
                        continue;
                    }
                    if(sector[j][c] == -1){
                        continue;
                    }
                    bfs(j , c);
                    if(close.size() != 1){
                        existsClose = true;
                        for(Point point : close){
                            sector[point.y][point.x] = -1;
                        }
                        copy();
                    }
                    close = new ArrayList<>();
                }
            }
            float average = 0;
            int sum = 0;
            int count = 0;
            if(!existsClose){
                for(int v = 0; v < 2; v++) {
                    for (int j = 0; j < n; j++) {
                        for (int c = 0; c < m; c++) {
                            if(sector[j][c] == -1){
                                continue;
                            }
                            if(v == 0){
                                count++;
                                sum += sector[j][c];
                            }
                            else{
                                if(average > sector[j][c]){
                                    sector[j][c] += 1;
                                }
                                else if(average < sector[j][c]){
                                    sector[j][c] -= 1;
                                }
                            }
                        }
                    }
                    average = (float)sum / (float)count ;
                }
                copy();
            }
        }

        int ans = 0;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
               if(sector[i][j] == -1){
                   continue;
               }
               ans += sector[i][j];
            }
        }
        System.out.println(ans);
    }
    public static void bfs(int y , int x){ // 이거는 그냥 모든 sector를 돌아보면서 bfs를 하고 list size가 1 이상이면 해당 list에 있는 point들 다 -1로 바꾸면 될 듯
        //bfs로 하나라도 움직였으면 바로 list에다가 집어넣는다. 일단 처음에 자기도 list에다가 집어넣는다. queue에다가도 넣고 visited도 표시하고
        //첫째 sector[0][i] 가 가장 안쪽 그리고 sector[n - 1][i] 가 가장 바깥쪽
        visited[y][x] = 1;
        queue.add(new Point(y , x));
        close.add(new Point(y , x));
        while(!queue.isEmpty()){
            Point point = queue.poll();
            int value = sector[point.y][point.x];
            for(int i = 0; i < 4; i++){
                int nx = point.x + dx[i];
                nx = nx == -1 ? m - 1 : nx % m;
                int ny = point.y + dy[i];
                if(ny < 0 || ny >= n){
                    continue;
                }
                if(sector[ny][nx] == -1 || visited[ny][nx] == 1){
                    continue;
                }
                if(sector[ny][nx] == value){
                    close.add(new Point(ny, nx));
                    visited[ny][nx] = 1;
                    queue.add(new Point(ny, nx));
                }
            }
        }
    }
    public static void spin(int mul, int dir , int time) { // 0 = 시계 방향 , 1 = 반 시계 방향
        //mul의 배수들을 돌려야함
        int start;
        int end;
        if (dir == 0) {
            dir = 1;
            start = 0;
            end = m;

        } else {
            dir = -1;
            start = m - 1;
            end = -1;
        }
        for (int c = 0; c < time; c++) {
            int mulNum = 1;
            for (int i = mul; i <= n; ) {
                mulNum++;
                for (int j = start; j != end; j += dir) {
                    sector[i - 1][j + dir == -1 ? m - 1 : (j + dir) % m] = tempSector[i - 1][j];
                }
                i = mul * mulNum;
            }
            copy();
        }
    }
    public static void copy(){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                tempSector[i][j] = sector[i][j];
            }
        }
    }
    public static void clean(){
        for(int i =0; i < n; i++){
            for(int j = 0; j < m; j++){
                visited[i][j] = 0;
            }
        }
    }
}