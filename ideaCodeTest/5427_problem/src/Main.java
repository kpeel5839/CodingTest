import java.util.*;
import java.io.*;

// 5427 : 불

/*
-- 전제 조건
상근이는 불을 피해서 출구로 나가야 한다.
상근이는 불이 이미 붙은 곳 , 그리고 붙으려고 하는 것으로 이동할 수 없다.
그리고 상근이와 불은 동시에 움직인다.

그 사실은 상근이가 있는 칸에 불이 옮겨옴과 동시에 다른 칸으로 이동할 수 있다. 이 문구를 통해서 알 수 있다.

그래서 상근이가 밖으로 나가는 시각을 구하시오.
-- 틀 설계
일단 동시에 움직이는 것인데 , 상근이가 옮겨간데에 불 붙으면 상근이 죽여버리면 되니까 상근이의 움직임을 먼저 처리하자.
일단 상근이의 움직임 , 불의 움직임 다 큐로 관리할 수 있을 것 같다.
불은 절대적이다 . 불은 그냥 큐에다가 담으면서 queue 를 빼면서 , 다음 움직일 곳을 치는데
이미 거기에 불이 있다면 큐에다가 담지만 않으면 된다.

그리고 , 상근이의 움직임, 상근이는 먼저 움직인다 , 그렇기 떄문에 상근이가 갈 수 있는 곳을 탐색하고 가기 시작할 때 , 그 때
큐에다가 담아놓은 값을 확인하면서 , 그 위치에 상근이가 있는 지 확인한다.
그리고 상근이의 위치를 담을 때에는 value 도 필요하다.
불은 필요 없지만 , 이것은 생성자를 두 종류로 만들어서 해결 할 수 있을 것 같다.

그리고 , 상근이가 움직이면서 , 바깥으로 나가는 경우가 생긴다?
그러면 그건 탈출한 것임으로 , 바로 출력하고
queue.isEmpty 하다 ? 그러면 impossible 을 출력하면 된다.

만들 것이 , 불이 옮겨갈 때 ,
상근이가 움직일 때
게임을 시작하는 메소드

-- 해맸던 점
새로운 escape 를 시작할 떄 , 즉 테케를 하나 더 시작할 떄 ,
ArrayList 두개 즉 , fire , human 을 초기화 시켜줬었야 했는데
그렇게 안해서 틀렸었음
 */
public class Main {
    public static BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
    public static Queue<Point> fire = new LinkedList<>() , human = new LinkedList<>();
    public static int H , W , T;
    public static char[][] map;
    public static boolean escapeSuccess = false;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};

    public static class Point{
        int y;
        int x;
        int value;
        public Point(int y , int x , int value){
            this.y = y;
            this.x = x;
            this.value = value;
        }
        public Point(int y , int x){
            this.y = y;
            this.x = x;
        }
    }

    public static void move() throws IOException{
        /*
        상근이의 움직임

        상근이는 불이 있지 않은 곳으로만 움직이고
        그렇지 않다라고 하면은 상근이나 혹은 벽이 없는 곳으로 이동하면 된다.
        그리고 항상 움직일 때 , checkEscape 를 통해서 escape 하는 움직임인지 확인하고
        만약에 맞다면 바로 output.write(value + "\n") return; 해주고 지나간다.

        escape 하지 않았는데 , empty 이다?
        그러면 Impossible 을 출력하는데 , 이것은 escape 함수에서 해줘야 할 듯하다.
         */

        int size = human.size();

        for(int i = 0; i < size; i++){
            // 일단 포인트를 꺼내서 상근이가 아직 살아있는지를 확인해야 한다.
            Point point = human.poll();
            if(map[point.y][point.x] != '@') continue;
            for(int j = 0; j < 4; j++){
                int ny = point.y + dy[j];
                int nx = point.x + dx[j];

                if(outOfRange(ny , nx)){
                    output.write((point.value + 1) + "\n");
                    escapeSuccess = true;
                    return;
                } // 범위를 벗어나면 탈출

                // 아니면 , 불이 있나 확인하고 벽인지 , 확인한다. 즉 .인지만 확인하면 된다.
                // 근데 그냥 불인가 , 벽인가 확인하는게 편하다.
                if(!(map[ny][nx] == '#' || map[ny][nx] == '*' || map[ny][nx] == '@')){
                    human.add(new Point(ny , nx , point.value + 1));
                    map[ny][nx] = '@';
                }
            }
        }
    }
    public static void spread(){
        /*
        불의 확산

        불의 확산은 그냥 queue 에 담겨 있는 , 불들을 확산하면서
        이미 불이 있는 것은 queue 에다가 집어넣지 않고
        새로 불을 집어넣은 곳은 큐에다가 집어넣는다.
        그렇게 하기 위해서는 불을 큐에다가 먼저 집어넣는 것이 좋다.

        그리고 불이 이미 있는 곳을 큐에다가 담지 않는 것이지,
        상근이는 죽여버려야한다. 불은 벽도 고려하자 , 그리고 이게 밖으로 나가는 것은 넘겨줘야 한다.
        outOfRange 를 사용하면 된다.
         */
        int size = fire.size();

        for(int i = 0; i < size; i++){ // fire 에다가 담는다. 번지게 하면서
            Point point = fire.poll();
            for(int j = 0; j < 4; j++){
                int ny = point.y + dy[j];
                int nx = point.x + dx[j];

                // ny , nx 가 불이거나 벽일 때 queue 에다가 담지 않는다.
                if(outOfRange(ny , nx) || map[ny][nx] == '#' || map[ny][nx] == '*') continue;

                // 걸러지지 않는 경우는 map에다가 넣고 , 큐에다가 담는다.
                map[ny][nx] = '*';
                fire.add(new Point(ny , nx));
            }
        }
    }
    public static void escape() throws IOException{
        /*
        탈출 시작
         */
        escapeSuccess = false; // 탈출을 알리는 escape 변수

        for(int i = 0; i < H; i++){
            for(int j = 0; j < W; j++){
                if(map[i][j] == '@'){
                    human.add(new Point(i , j , 0));
                }
                else if(map[i][j] == '*'){
                    fire.add(new Point(i , j));
                }
            }
        } // 일단 불과 사람위치를 구했음

        while(!escapeSuccess){
            mapPrint();
            if(human.isEmpty()){
                output.write("IMPOSSIBLE" + "\n");
                break;
            }
            move();
            spread();
        }
    }
    public static boolean outOfRange(int y , int x){
        /*
        escape 하는 순간인지 아닌지를 판단
         */
        if(y < 0 || y >= H || x < 0 || x >= W) return true; // escape 하는 순간
        return false;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        T = Integer.parseInt(input.readLine());

        for(int i = 0; i < T; i++){
            st = new StringTokenizer(input.readLine());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());

            fire = new LinkedList<>();
            human = new LinkedList<>();
            map = new char[H][W];

            for(int j = 0; j < H; j++){
                String string = input.readLine();
                for(int c = 0; c < W; c++){
                    map[j][c] = string.charAt(c);
                }
            }

            escape();
        }

        output.flush();
        output.close();
    }
    public static void mapPrint(){
        System.out.println("next");
        for(int i = 0; i < H; i++){
            for(int j = 0; j < W; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}