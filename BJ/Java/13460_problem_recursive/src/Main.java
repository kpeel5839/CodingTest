import java.io.*;
import java.util.*;

public class Main { //bfs로 풀면 못 풀음 , 왜냐하면 이전 상태로 돌아갈 수가 없음 , 이 방향이 틀렸다고 생각하고 이전 카운트로 돌아갈 수 있어야 하는데 그게 안됨
    public static char[][] map;
    public static int holeX, holeY;
    public static boolean[][][][] visited;
    public static int r , c;
    public static int[] rollX = {0 , 1 , 0 , -1};
    public static int[] rollY = {-1 , 0 , 1 , 0};
    public static List<Integer> resultList = new ArrayList<>();
    public static boolean success = false;

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine());

        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        map = new char[r][c];
        visited = new boolean[r][c][r][c];
        Point red = new Point();
        Point blue = new Point();

        for(int i = 0; i < r; i++){
            String string = input.readLine();
            for (int j = 0; j < c; j++){
                map[i][j] = string.charAt(j);

                if (map[i][j] == 'O'){
                    holeX = j;
                    holeY = i;
                }
                else if(map[i][j] == 'R'){
                    red = new Point(i , j);
                }
                else if(map[i][j] == 'B'){
                    blue = new Point(i , j);
                }
            }
        }
        bfs(red , blue , 0); //일단은 정보를 다 넘김

        if(resultList.size() == 0){
            System.out.println(-1);
        }
        else{
            System.out.println(Collections.min(resultList));
        }
    }

    public static void bfs(Point red , Point blue , int count){ //실제 움직임을 지시하는 곳
        if (success == true) { // 제동을 담당
            resultList.add(count);
            success = false;
            return; //한번 끝나고 나면 false를 해줌 그러면서 하나의 경우의 수를 제거
        }
        if (visited[red.y][red.x][blue.y][blue.x] == true){ //왔는데 이미 방문한 곳이면 다시 돌아감
            map[red.y][red.x] = '.';
            map[blue.y][blue.x] = '.';
            return;
        }
        else{ //방문하지 않은 곳이면 visited에다가 이제 방문 표시를 함
            visited[red.y][red.x][blue.y][blue.x] = true;
        }
        System.out.println("next");
        for (int i =0; i < r; i++){
            for (int j =0; j < c; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        int redY= red.y;
        int redX = red.x;
        int blueY = blue.y;
        int blueX = blue.x;
        for (int i = 0; i < 4; i++){ //이게 가능한 움직임일까를 누가 판단할 수 있을까? 판단할 필요 없다 그냥 왔을 때 짜르면 됨 그럼 이제 hole에 빠졌을 떄를 어떻게 처리해야 할까...
            map[redY][redX] = '.';
            map[blueY][blueX] = '.';
            int[] location = move(redY , redX , blueY , blueX , i);
            //location[0] = y , location[1] = x , ...
            bfs(new Point(location[0], location[1]) , new Point(location[2],location[3]) , count + 1);
        }
    }

    public static int[] move(int redY , int redX , int blueY , int blueX , int direction){//여기서는 실제로 움직이는 구문이다. 여기서 어떻게 할까가 관건이다.
        //움직이자
        char first = 'R';
        boolean holeInRed = false;
        boolean holeInBlue = false;
        int[] location = {redY ,redX , blueY , blueX};

        while(map[redY + rollY[direction]][redX + rollX[direction]] != '#'){
            redY += rollY[direction];
            redX += rollX[direction];
            if (redY == holeY && redX == holeX){
                holeInRed = true;
                break;
            }
        }
        while(map[blueY + rollY[direction]][blueX + rollX[direction]] != '#'){
            blueY += rollY[direction];
            blueX += rollX[direction];
            if (blueY == holeY && blueX == holeX){
                holeInBlue = true;
                break;
            }
        }
        if (holeInBlue == true){
            return location;
        }
        if (holeInRed == true){
            success = true;
            return location;
        }
        if (redX == blueX && redY == blueY) {
            if (direction == 0) { //위
                if (redY > blueY) {redY += 1;}
                else{blueY += 1;}
            } else if (direction == 1) { //오른쪽
                if (redX > blueX) {blueX -= 1;}
                else{redX -= 1;}
            } else if (direction == 2) { //아래
                if (redY > blueY) {blueY -= 1;}
                else{redY -= 1;}
            } else if (direction == 3) { //왼쪽
                if (redX > blueX) {redX += 1;}
                else{blueX += 1;}
            }
        }
        location[0] = redY;
        location[1] = redX;
        location[2] = blueY;
        location[3] = blueX;
        map[location[0]][location[1]] = 'R';
        map[location[2]][location[3]] = 'B';
        return location;
    }

    public static class Point{
        public int y;
        public int x;

        public Point(){}
        public Point(int y,  int x){ //첫번째 인자가 y , 두번째 인자가 x
            this.y = y;
            this.x = x;
        }
    }
}
