import java.io.*;
import java.util.*;

public class Main{
    public static int r , c;
    public static char[][] map;
    public static boolean[][][][] visited;
    public static Point red , blue;
    public static int holeX ,holeY;
    public static int[] rollY = {-1 , 0 , 1 , 0};
    public static int[] rollX = {0 , 1 , 0 , -1}; // 0 , 1 , 2 , 3 상 , 우 , 하 , 좌 라고 가정

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        map = new char[r][c];
        visited = new boolean[r][c][r][c];

        for (int i = 0; i < r; i++){
            String string = input.readLine();
            for(int j = 0; j < c; j++){
                map[i][j] = string.charAt(j);
                if (map[i][j] == 'O'){
                    holeX = j;
                    holeY = i;
                }
                else if(map[i][j] == 'R'){
                    red = new Point(j , i , 0 ,0 ,1);
                }
                else if(map[i][j] == 'B'){
                    blue = new Point(0 , 0 , j  ,  i , 1);
                }
            }
        }
        System.out.println(bfs());
    }
    public static int bfs(){
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(red.redX , red.redY , blue.blueX , blue.blueY , 1));
        visited[red.redY][red.redX][blue.blueY][blue.blueX] = true;

        while(!queue.isEmpty()){ //queue 가 빌때까지 돌리는데 .... 그거를 어떻게? 이제 그거를 한 위치씩 받아가면서 근데 for(int i = 0; i < 4; i++) 이렇게 상하좌우로,
            //그러면 다 새로운 좌표를 받겠지? 일단 for 안에서 해결 되는 것이다.
            Point newP= queue.poll();
            int curRx = newP.redX;
            int curRy = newP.redY;
            int curBx = newP.blueX;
            int curBy = newP.blueY;
            int curCount = newP.count;

            if (curCount > 10){
                return -1;
            }

            for(int i =0; i <4; i++) {
                int newRx = curRx;
                int newRy = curRy;
                int newBx = curBx;
                int newBy = curBy;

                boolean holeInRed = false;
                boolean holeInBlue = false;

                while (map[newRy + rollY[i]][newRx + rollX[i]] != '#'){
                    newRx += rollX[i];
                    newRy += rollY[i];
                    if (holeX == newRx && holeY == newRy){
                        holeInRed = true;
                        break;
                    }
                }
                while (map[newBy + rollY[i]][newBx + rollX[i]] != '#'){
                    newBx += rollX[i];
                    newBy += rollY[i];
                    if (holeX == newBx && holeY == newBy){
                        holeInBlue = true;
                        break;
                    }
                }

                if (holeInBlue == true){
                    continue;
                }
                if (holeInRed == true){
                    return curCount;
                }

                if (newRx == newBx && newRy == newBy){
                    if (i == 0){ //위로 간 경우
                        if(curRy > curBy){
                            newRy += 1;
                        } else{
                            newBy += 1;
                        }
                    }
                    else if (i == 1){ //오른쪽으로 간 경우
                        if(curRx > curBx){
                            newBx -= 1;
                        } else{
                            newRx -= 1;
                        }
                    }
                    else if (i == 2){ //아래로 간 경우
                        if(curRy > curBy){
                            newBy -= 1;
                        } else{
                            newRy -= 1;
                        }
                    }
                    else if (i == 3){ //왼쪽으로 간 경우
                        if(curRx > curBx){
                            newRx += 1;
                        } else{
                            newBx += 1;
                        }
                    }
                }
                if(!visited[newRy][newRx][newBy][newBx]){
                    visited[newRy][newRx][newBy][newBx] = true;
                    queue.add(new Point(newRx , newRy , newBx ,newBy, curCount + 1));
                }
            }
        }
        return -1;
    }
    public static class Point{
        public int redY;
        public int redX;
        public int blueY;
        public int blueX;
        public int count;

        public Point(int redX , int redY , int blueX , int blueY , int count){
            this.redX = redX;
            this.redY = redY;
            this.blueX = blueX;
            this.blueY = blueY;
            this.count = count;
        }
    }
}