import java.util.*;
import java.io.*;

public class Main {
    public static int n = 300;
    public static char[][] map = new char[n][n];
    public static List<Integer> real = new ArrayList<>();
    public static Stack<Integer> temp = new Stack<>();
    public static int count = 0;
    public static Point[] instruct;
    public static int[] dx = {1 , 0 , -1 , 0};
    public static int[] dy = {0 , -1 , 0 , 1}; // 0 == 오른쪽 , 1 == 위 , 2 == 왼쪽 , 3 == 아래
    public static void main(String[] args) throws IOException{
        //드래곤 커브 n세대 만드는 방법 , 일단 방향 + 1 해주고 거꾸로 부터 추가해주면 됨
        //그런다음 x , y 크기 100인 거 만들어주고 시작방향에다가 내가 만든 세대로 천천히 집어넣으면 됨
        //일단 처음에 시작점 찍고 그 방향대로 찍으면 됨 그다음 한점에서 n - 1 , n - 1까지만 돌면서 4개있나 확인하면 됨
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        count = Integer.parseInt(input.readLine());
        instruct = new Point[count];

        for(int i =0; i < count; i++){
            st = new StringTokenizer(input.readLine());
            instruct[i] = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()) ,Integer.parseInt(st.nextToken()));
        }

        for(int i = 0; i < count; i++){
            draw(instruct[i].y , instruct[i].x , instruct[i].dir , instruct[i].level);
        }

        count = 0;

        for(int i = 0; i < n - 1; i++){
            for(int j = 0; j < n - 1; j++){
                if(map[i][j] == '*' && map[i + 1][j] == '*' && map[i][j + 1] == '*' && map[i + 1][j + 1] == '*'){
                    count++;
                }
            }
        }
        System.out.println(count);
    }

    public static void draw(int y, int x, int dir, int level){
        make(dir , level);
        map[y][x] = '*';
        for(Integer number : real){
            y += dy[number];
            x += dx[number];
            map[y][x] = '*';
        }
        real = new ArrayList<>();
        temp = new Stack<>();
    }

    public static void make(int dir , int level){
        real.add(dir);
        for(int i = 0;i < level; i++){ //일단 처음에 더하면서 꺼내면서 스택에다가 쌓고 다시 pop으로 집어넣으면 됨
            for(Integer number : real){
                temp.push((number + 1) % 4 );
            }
            while(!temp.empty()){
                real.add(temp.pop());
            }
        }
    }

    public static class Point{
        int y;
        int x;
        int dir;
        int level;

        public Point(int x, int y, int dir , int level){
            this.y = y;
            this.x = x;
            this.dir = dir;
            this.level = level;
        }
    }
}
