import java.util.*;
import java.io.*;
public class Main {
    public static int r , c , sharkCount , total = 0 , globalDir;
    public static Shark[][] sharks;
    public static int[] dx = {0 , 0 , 1 , -1} , dy = {-1 , 1 , 0 , 0}; //0 위 , 1 아래 , 2 오른쪽 , 3 왼쪽
    public static Queue<Shark> moveBefore = new LinkedList<>() , moveAfter = new LinkedList<>();
    //(i + 1 == 2 ? 0 : i + 1 == 4 ? 2 : i + 1) -- 반대 방향 계산하는 방법
    public static void main(String[] args) throws IOException{ //샤크의 정보를 가지고 계속 맵에다가 그려넣는다 , 그려 넣고서 상어의 정보를 다시 받는 거지
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        sharkCount = Integer.parseInt(st.nextToken());
        sharks = new Shark[r][c];

        for(int i = 0; i < sharkCount; i++){
            st = new StringTokenizer(input.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;
            int speed = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken()) - 1;
            int size = Integer.parseInt(st.nextToken());
            sharks[y][x] = (new Shark(y , x , speed , dir , size));
        }
        //낚시왕이 잡고서 움직여야함 ,
        for(int i = 0; i < c; i++){
            fishing(i);
            instructMove();
        }
        System.out.println(total);
    }
    public static void fishing(int col){ // 해당 col에서 가장 첨으로 만나는 상어를 잡고 없애야함 , 그러고서 total에다가 더해야함
        for(int i = 0; i < r; i++){
            if(sharks[i][col] != null){
                total += sharks[i][col].size;
                sharks[i][col] = null;
                break;
            }
        }
    }
    public static void instructMove(){//shark 하나씩 돌면서 다 move 시키기 그러면서 list에다가 쌓기 그 다음에 list하나씩 다 돌면서 collection.sort한 뒤에 제일 최상위에 있는놈만 남기기
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                if(sharks[i][j] != null){
                    moveBefore.add(sharks[i][j]);
                    sharks[i][j] = null;
                }
            }
        }
        while(!moveBefore.isEmpty()){
            Shark shark = moveBefore.poll();
            Point point = move(shark);
            shark.dir = globalDir;
            shark.y = point.y;
            shark.x = point.x;
            moveAfter.add(shark);
        }
        while(!moveAfter.isEmpty()){
            Shark shark = moveAfter.poll();
            if(sharks[shark.y][shark.x] != null){
                if(shark.size > sharks[shark.y][shark.x].size){
                    sharks[shark.y][shark.x] = shark;
                }
            }
            else{
                sharks[shark.y][shark.x] = shark;
            }
        }
    }
    public static Point move(Shark shark){
        int row = shark.y;
        int col = shark.x;
        int speed = shark.speed;
        int dir = shark.dir; // 2  , 3 오른쪽(1) 왼쪽(-1) 0 , 1 위쪽(-1) 아랫쪽(1)
        if(dir == 2 || dir == 3){
            int end = dir == 2 ? c - 1 : 0;
            if(speed < Math.abs(end - col)){
                globalDir = dir; //dir 유지
                return new Point(row , col + (speed * dx[dir]));
            }
            speed -= Math.abs(end - col);
            int start;
            boolean reverse = false;
            if((speed / (c - 1)) % 2 == 0){
                start = (end == (c - 1)) ? c - 1 : 0;
                reverse = true;
            }
            else{
                start = (end == (c - 1)) ? 0 : c - 1;
            }
            int dis = speed % (c - 1);
            globalDir = (reverse == false) ? dir : (dir + 1 == 2 ? 0 : dir + 1 == 4 ? 2 : dir + 1);
            return new Point(row , start + (dis * dx[globalDir]));
        }
        else{
            int end = dir == 1 ? r - 1 : 0;
            if(speed < Math.abs(end - row)){
                globalDir = dir; //dir 유지
                return new Point(row + (speed * dy[dir]) , col);
            }
            speed -= Math.abs(end - row);
            int start;
            boolean reverse = false;
            if((speed / (r - 1)) % 2 == 0){
                start = (end == (r - 1)) ? r - 1 : 0;
                reverse = true;
            }
            else{
                start = (end == (r - 1)) ? 0 : r - 1;
            }
            int dis = speed % (r - 1);
            globalDir = (reverse == false) ? dir : (dir + 1 == 2 ? 0 : dir + 1 == 4 ? 2 : dir + 1);
            return new Point(start + (dis * dy[globalDir]) , col);
        }
    }
    public static class Shark{
        int speed;
        int dir;
        int size;
        int y;
        int x;
        public Shark(int y , int x, int speed , int dir , int size){
            this.speed = speed;
            this.dir = dir;
            this.size = size;
            this.y = y;
            this.x = x;
        }
    }
    public static class Point{
        int y;
        int x;
        public Point(int y , int x){
            this.y = y;
            this.x = x;
        }
    }
}
