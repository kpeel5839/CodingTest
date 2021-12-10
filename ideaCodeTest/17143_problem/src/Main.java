import java.util.*;
import java.io.*;
public class Main {
    public static int r , c , sharkCount , total = 0 , globalDir;
    public static Shark[][] sharks; //shark의 정보를 배열에다가 집어넣자.
    public static int[] dx = {0 , 0 , 1 , -1} , dy = {-1 , 1 , 0 , 0}; //0 위 , 1 아래 , 2 오른쪽 , 3 왼쪽
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
            sharks[Integer.parseInt(st.nextToken()) - 1][Integer.parseInt(st.nextToken()) - 1] = new Shark(Integer.parseInt(st.nextToken()) ,Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()));
        }
        //낚시왕이 잡고서 움직여야함 ,
        for(int i = 0; i < c; i++){
            fishing(i);
            System.out.println("fishing after");
            for(int j = 0; j < r; j++){
                for(int v = 0; v < c; v++){
                    if(sharks[j][v] == null){
                        System.out.print(0 + " ");
                        continue;
                    }
                    System.out.print(1 + " ");
                }
                System.out.println();
            }
            instructMove();
            System.out.println("move after");
            for(int j = 0; j < r; j++){
                for(int v = 0; v < c; v++){
                    if(sharks[j][v] == null){
                        System.out.print(0 + " ");
                        continue;
                    }
                    System.out.print(1 + " ");
                }
                System.out.println();
            }
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
    public static void instructMove(){
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                if(sharks[i][j] != null){ //여기서부터 move 들어간다
                    Point point = move(i , j);
                    Shark shark = sharks[i][j];
                    shark.dir = globalDir;
                    sharks[i][j] = null;
                    if(sharks[point.y][point.x] != null){
                        if(shark.size > sharks[point.y][point.x].size){
                            sharks[point.y][point.x] = shark;
                        }
                    }
                    else{
                        sharks[point.y][point.x] = shark;
                    }
                }
            }
        }
    }
    public static Point move(int row , int col){
        Shark shark = sharks[row][col];
        int speed = shark.speed;
        int dir = shark.dir;
//        if(dir == 2 || dir == 3){ //x축에 관해서 연산
//            int end = dir == 2 ? c - 1 : 0;
//            if(speed < Math.abs(end - col)){
//                globalDir = dir;
//                return new Point(row , col + speed * dx[dir]);
//            }
//            speed -= Math.abs(end - col);
//            int start;
//            boolean reverse = false;
//            if(speed / (c - 1) % 2 == 0){ //짝수면 같은 방향 x
//                start = (end == c - 1) ? c - 1 : 0;
//                reverse = true;
//            }
//            else{
//                start = (end == c - 1) ? 0 : c - 1;
//            }
//            int dis = speed % (c - 1);
//            globalDir = reverse == false ? dir : dir + 1 == 2 ? 0 : dir + 1 == 4 ? 2 : dir + 1;
//            return new Point(row , start + (dis * dx[globalDir]));
//        }
//        else{ //y축에 관해서 연산
//            int end = dir == 1 ? r - 1 : 0;
//            if(speed < Math.abs(end - row)){
//                globalDir = dir;
//                return new Point(row + speed * dy[dir] , col);
//            }
//            speed -= Math.abs(end - col);
//            int start;
//            boolean reverse = false;
//            if(speed / (r - 1) % 2 == 0){ //짝수면 같은 방향 x
//                start = (end == r - 1) ? r - 1 : 0;
//                reverse = true;
//            }
//            else{
//                start = (end == r - 1) ? 0 : r - 1;
//            }
//            int dis = speed % (r - 1);
//            globalDir = reverse == false ? dir : dir + 1 == 2 ? 0 : dir + 1 == 4 ? 2 : dir + 1;
//            return new Point(start + (dis * dy[globalDir]) , col);
//        }
    }

    public static class Shark{
        int speed;
        int dir;
        int size;
        public Shark(int speed , int dir , int size){
            this.speed = speed;
            this.dir = dir;
            this.size = size;
        }
        @Override
        public String toString(){
            return "speed : " + speed + " dir : " + dir + " size : " + size;
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
