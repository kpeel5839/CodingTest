import java.util.*;
import java.io.*;

public class Main {
    public static int[][] map;
    public static String[][] chess;
    public static int n , k , turn = 0;
    public static int[] dx = {1 , -1 , 0 , 0} , dy = {0 , 0 , -1 , 1};
    public static Stack<Horse> red = new Stack<>();
    public static Queue<Horse> white = new LinkedList<>();
    public static List<Horse> horse = new ArrayList<>();
    public static void main(String[] args)throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        map = new int[n][n];
        chess = new String[n][n];

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < n; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                chess[i][j] = "";
            }
        }

        for(int i = 0; i < k; i++){
            st = new StringTokenizer(input.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;
            int dir = Integer.parseInt(st.nextToken()) - 1;
            chess[y][x] += i;
            horse.add(new Horse(y , x , dir , i));
        }

        while(true){
            turn++;
            int gameOver = instruct();
            if(gameOver == 1){
                break;
            }
            if(turn == 1001){
                break;
            }
        }
        if(turn != 1001) {
            System.out.println(turn);
        }
        else{
            System.out.println(-1);
        }
    }
    public static int instruct(){
      //게임이 중간에 끝나면 1 아니면 계속 -1을 반환하면 됨
        for(Horse ho : horse){
            int success = move(ho.y , ho.x , ho.dir , ho.num);
            if(success == 1){
                return success;
            }
        }
        return -1;
    }
    public static int move(int r, int c , int dir , int num){//2 이면 파랑색 그것만 그냥 하나만 옮기고 나머지는 그냥 그 요소부터 다 제하면 됨
        int nx = c + dx[dir];
        int ny = r + dy[dir];
        String sNum = Integer.toString(num);
        int index = 0;
        if(nx < 0 || nx >= n || ny < 0 || ny >= n || map[ny][nx] == 2){ //파랑인 경우
            for(Horse ho : horse){
                if(ho.num == num){
                    break;
                }
                index++;
            }//해당 인덱스 구했음
            dir = dir + 1 == 2 ? 0 : dir + 1 == 4 ? 2 : dir + 1;
            nx = c + dx[dir];
            ny = r + dy[dir];
            horse.set(index, new Horse(r , c , dir , num));
            if(nx < 0 || nx >= n || ny < 0 || ny >= n || map[ny][nx] == 2){ //반대 방향도 파랑색인 경우
                return -1;
            }
            return move(r , c , dir , num);
        }
        else if(map[ny][nx] == 1){ //빨강인 경우 (빨강 , 하양의 경우에는 string length 가 4가 넘었는지도 확인해주어야 함 움직이고서
            for(int i = chess[r][c].indexOf(sNum); i < chess[r][c].length(); i++){
                red.push(horse.get(Integer.parseInt(Character.toString(chess[r][c].charAt(i)))));
            }
            chess[r][c] = chess[r][c].substring(0, chess[r][c].indexOf(sNum));
            while(!red.isEmpty()){
                Horse ho = red.pop();
                horse.set(ho.num , new Horse(ny , nx , ho.dir , ho.num));
                chess[ny][nx] += Integer.toString(ho.num);
            }
            if(chess[ny][nx].length() >= 4){
                return 1;
            }
            return -1;
        }
        else{ //하양인 경우
            for(int i = chess[r][c].indexOf(sNum); i < chess[r][c].length(); i++){
                white.add(horse.get(Integer.parseInt(Character.toString(chess[r][c].charAt(i)))));
            }
            chess[r][c] = chess[r][c].substring(0, chess[r][c].indexOf(sNum));
            while(!white.isEmpty()){
                Horse ho = white.poll();
                horse.set(ho.num , new Horse(ny , nx , ho.dir , ho.num));
                chess[ny][nx] += Integer.toString(ho.num);
            }
            if(chess[ny][nx].length() >= 4){
                return 1;
            }
            return -1;
        }
    }
    public static class Horse{
        int y;
        int x;
        int dir;
        int num;

        public Horse(int y, int x , int dir, int num){
            this.y = y;
            this.x = x;
            this.dir = dir;
            this.num = num;
        }
    }
}
