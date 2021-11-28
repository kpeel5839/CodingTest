import java.util.*;
import java.io.*;

public class Main {
    public static char[][] map ,  ladder;
    public static int r, c, ladderCount;
    public static Point[] ladderList;
    public static int ans = - 1;
    public static boolean decide = false;
    public static int[] dx = {};
    public static int[] dy = {}; //아래  , 왼쪽 , 오른쪽 밖에없음
    public static void main(String[] args)throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine());
        c = Integer.parseInt(st.nextToken());
        ladderCount = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());

        map = new char[r][c];
        ladder = new char[r][c - 1];
        ladderList = new Point[r * (c - 1)];

        for(int i = 0; i < ladderCount; i++){
            st = new StringTokenizer(input.readLine());
            ladder[Integer.parseInt(st.nextToken()) - 1][Integer.parseInt(st.nextToken()) - 1] = '*';
        }

        int ladderIndex = 0;

        for(int i = 0; i < r; i++){
            for(int j = 0; j < c - 1; j++){
                if(ladder[i][j] != '*'){
                    ladder[i][j] = '.';
                    if( (j == 0 || ladder[i][j - 1] != '*') && (j == c - 2 || ladder[i][j + 1] != '*')){
                        ladderList[ladderIndex++] = new Point(i , j);
                    }
                }


            }
        }

        if(move() == 1) ans = 0;

        if(!decide) { //일단 주변에 사다리가 놓아져 있는지 확인해야함
            for (int i = 0; i < ladderList.length; i++) {
                if (ladderList[i] == null) {
                    break;
                }
                ladder[ladderList[i].y][ladderList[i].x] = '*';
                if (move() == 1) {ans = 1; break;}
                ladder[ladderList[i].y][ladderList[i].x] = '.';
            }
        }
        if (!decide){
            for(int i = 0; i < ladderList.length - 1; i++){
                if(ladderList[i] == null){
                    break;
                }
                for(int j = i + 1; j < ladderList.length; j++){
                    if(ladderList[j] == null){
                        break;
                    }
                    if(ladderList[i].y == ladderList[j].y && Math.abs(ladderList[i].x - ladderList[j].x) == 1){
                        continue;
                    }
                    ladder[ladderList[i].y][ladderList[i].x] = '*';
                    ladder[ladderList[j].y][ladderList[j].x] = '*';
                    if (move() == 1) {ans = 2; break;}
                    ladder[ladderList[i].y][ladderList[i].x] = '.';
                    ladder[ladderList[j].y][ladderList[j].x] = '.';
                }
            }
        }
        if (!decide){
            for(int i = 0; i < ladderList.length - 2; i++){
                if(ladderList[i] == null){
                    break;
                }
                for(int j = i + 1; j < ladderList.length - 1; j++){
                    if(ladderList[j] == null){
                        break;
                    }
                    if(ladderList[i].y == ladderList[j].y && Math.abs(ladderList[i].x - ladderList[j].x) == 1){
                        continue;
                    }
                    for(int c = j + 1; c < ladderList.length - 1; c++){
                        if(ladderList[c] == null){
                            break;
                        }
                        if(ladderList[j].y == ladderList[c].y && Math.abs(ladderList[j].x - ladderList[c].x) == 1){
                            continue;
                        }
                        ladder[ladderList[i].y][ladderList[i].x] = '*';
                        ladder[ladderList[j].y][ladderList[j].x] = '*';
                        ladder[ladderList[c].y][ladderList[c].x] = '*';
                        if (move() == 1) {ans = 3; break;}
                        ladder[ladderList[i].y][ladderList[i].x] = '.';
                        ladder[ladderList[j].y][ladderList[j].x] = '.';
                        ladder[ladderList[c].y][ladderList[c].x] = '.';
                    }
                }
            }
        }

        System.out.println(ans);
    }

    public static int move(){ //그냥 사다리 main에서 놔주고 넘겨주면 성공이면 1 , 아니면 -1 그리고 성공하면 decide = true 로 바꿔주기
        for(int i = 0; i < c; i++){
            int y= 0;
            int x =i;
            while(true){ //break;을 그냥 무조건 끝에 도달한다음 사다리 없는지 있는지 확인하고 , 그러면 항상 사다리를 먼저 확인해야함 이동한다음에
                //자신과 같은 숫자의 사다리면 오른쪽 , 자신보다 낮은 숫자이면 왼쪽 , 0번째와 , 마지막 번째는 왼쪽과 오른쪽을 검사하면 안됨
                if(x == 0) {
                    if (ladder[y][x] == '*') {
                        x += 1;
                    }
                }
                else if(x == (c - 1)) {
                    if (ladder[y][x - 1] == '*') {
                        x -= 1;
                    }
                }
                else{
                    if(ladder[y][x] == '*'){
                        x += 1;
                    }
                    else if(ladder[y][x - 1] == '*'){
                        x -= 1;
                    }
                }
                if(y == r - 1){
                    if(x != i){
                        return -1;
                    }
                    break;
                }
                y += 1;
            }
        }
        decide = true;
        return 1;
    }


    public static class Point{
        int y;
        int x;

        public Point(int y,  int x){
            this.y = y;
            this.x = x;
        }
    }
}
