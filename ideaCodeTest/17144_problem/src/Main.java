import java.util.*;
import java.io.*;
public class Main {
    public static int r , c , time , airU = 0 , airD = 0 , totalDust = 0; //얘내는 row만 기억하면 됨
    public static int[][] map , tempMap;
    public static Queue<Point> dust = new LinkedList<>();
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static void main(String[] args) throws IOException{ //확산 이후 청정
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        time = Integer.parseInt(st.nextToken());
        map = new int[r][c];
        tempMap = new int[r][c];

        for(int i = 0; i < r; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < c; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] == -1){
                    if(airU == 0){
                        airU = i;
                    }
                    else{
                        airD = i;
                    }
                }
                else if(map[i][j] != 0){
                    dust.add(new Point(i , j , map[i][j]));
                }
                tempMap[i][j] = map[i][j];
            }
        }

        for(int i = 0; i < time; i++){
            spread();
            clean(airU, -1);
            clean(airD , 1);
            insertQueue();
        }

        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                if(map[i][j] == -1){
                    continue;
                }
                totalDust += map[i][j];
            }
        }
        System.out.println(totalDust);
    } //0 0 1 2
    //nowTmp = 3
    //preTmp = 3
    public static void clean(int row , int dir){//여기서는 밀어넣기 , tempMap으로 그냥 붙혀넣으면 됨
        int preTmp = map[row][1];
        map[row][1] = 0;
        int nowTmp = 0;
        int ro = row;
        int co = 1;
        int nowD = 1; //dir은 더해갈 방향 nowD를 변하게 할 변수임
        while(true){
            if(ro + dy[nowD] < 0 || ro + dy[nowD] >= r || co + dx[nowD] < 0 || co + dx[nowD] >= c){
                nowD = nowD + dir < 0 ? 3 : (nowD + dir) % 4;
                continue;
            }
            if(ro + dy[nowD] == row && co + dx[nowD] == 0){
                break;
            }
            nowTmp = map[ro + dy[nowD]][co + dx[nowD]];
            map[ro + dy[nowD]][co + dx[nowD]] = preTmp;
            preTmp = nowTmp;
            ro += dy[nowD];
            co += dx[nowD];
        }
    }
    public static void insertQueue(){//여기서는 clean 에서 다 밀어넣고 난 결과 다시 tempMap에다가 복사하면서 , 현재 dust 있는 거 queue에다가 다 집어넣기
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                if(map[i][j] != -1 && map[i][j] != 0){
                    dust.add(new Point(i , j , map[i][j]));
                }
            }
        }
    }
    public static void spread(){ //여기서는 queue에서 확산 , queue.poll() 로 확산 시키면 된다. copy를 여기서 하자 , 확산시킨다음 copy해서 맵 초기화
        //그러고서 clean으로 그냥 temp 에 있는 거 map에다가 옮겨 놓고서 그 다음에 queue에서 빼면서 확산 시키면 됨 , 근데 map에다가 그러면서 copy하면 다시 temp 에 가고 반복하면 됨
        while(!dust.isEmpty()){
            Point point = dust.poll();
            int dustValue = (int)Math.floor((double)point.value / 5.0);
            for(int i = 0; i < 4; i++){
                int nx = point.x + dx[i];
                int ny = point.y + dy[i];

                if(ny < 0 || ny >= r || nx < 0 || nx >= c || map[ny][nx] == -1){
                    continue;
                }

                map[point.y][point.x] -= dustValue;
                map[ny][nx] += dustValue;
            }
        }
    }
    public static class Point{
        int y;
        int x;
        int value;
        public Point(int y , int x , int value){
            this.y = y;
            this.x = x;
            this.value = value;
        }
    }
}
