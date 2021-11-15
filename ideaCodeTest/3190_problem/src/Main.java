import java.util.*;
import java.io.*;

public class Main {
    public static int n;
    public static char[][] map;
    public static int[] changeTime;
    public static int[] changeDirection;
    public static int time = 0;
    public static Point head = new Point(0,0);
    public static Point tail = new Point(0,0);
    public static int[] rollY = {-1 , 0 , 1, 0};
    public static int[] rollX = {0 , 1 , 0 , -1};
    public static Queue<Point> moveLog = new LinkedList<>();
    public static int sequence = 0;
    public static int instructCount = 0;


    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(input.readLine());
        int appleCount = Integer.parseInt(input.readLine());

        map = new char[n][n];
        for(int i = 0; i < n; i++){
            for(int j =0; j < n; j++){
                map[i][j] = '.';
            }
        }
        map[0][0] = '*';

        for(int i = 0; i < appleCount; i++){
            st = new StringTokenizer(input.readLine());
            map[Integer.parseInt(st.nextToken()) - 1][Integer.parseInt(st.nextToken()) - 1] = 'O'; //O는 사과
        }

        instructCount = Integer.parseInt(input.readLine());
        changeTime = new int[instructCount];
        changeDirection = new int[instructCount];

        for(int i = 0; i < instructCount; i++){
            st = new StringTokenizer(input.readLine());
            changeTime[i] = Integer.parseInt(st.nextToken());
            char change = st.nextToken().charAt(0);
            if(change == 'D'){changeDirection[i] = 1;}
            else{changeDirection[i] = -1;}
        }
        System.out.println(instructMove() + 1);
    }
    public static int move(int direction){ //방향은 instruct에서 알아서 줄 것임, 그러니까 여기서는 그냥 이동처리만 하면 됨
        int end = -1;
        if (direction == 1 || direction == 2){end = n;}
        head.y += rollY[direction];
        head.x += rollX[direction];
        moveLog.add(new Point(head.y, head.x));
        if(head.y == end || head.x == end){return -1;} //머리가 나갔을 때의 처리
        if(map[head.y][head.x] == '*'){return -1;} //그냥 꼬리가 가기전에 박으면 끝남
        if(!(map[head.y][head.x] == 'O')){ //이거면 tail을 그냥 그대로 놥둠, 근데 사과를 먹음으로써 뱀이 뒤질 수 있으니까 이거 다음에 tail을 할 것임 사고처리랑
            map[tail.y][tail.x] = '.';
            getTail();
        } //이건 사과를 먹은 특별한 경우
        map[head.y][head.x] = '*'; return 1;
    }

    public static void getTail(){
        Point point = moveLog.poll();
        tail.y = point.y;
        tail.x = point.x;
        }
    public static int instructMove(){ //방향 지시 처리
        int success = 0;
        int direction = 1;
        while(true){
            if(sequence != instructCount && time == changeTime[sequence]) {
                direction += changeDirection[sequence];
                if(direction == -1){direction = 3;}
                if(direction == 4){direction = 0;}
                sequence++;
            }
            success = move(direction);
            if(success == -1){
                break;
            }
            time++;
        }
        return time;
    }
    public static class Point{
        public int y;
        public int x;

        public Point(int y , int x){
            this.y = y;
            this.x = x;
        }
    }
}
