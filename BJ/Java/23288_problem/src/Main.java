import java.util.*;
import java.io.*;

// 23288 : 주사위 굴리기 2
/*
-- 전제조건
1. 주사위는 처음에는 밑면이 6인 상태로 시작한다.
2. 처음에는 (0, 0)에서 동쪽으로 한칸 이동한다.
3. 그러면 당연히 주사위는 그에 맞게 동적으로 움직여야 한다.
4. 주사위가 그 칸에 가면 첫번째로 점수를 획득한다 지금 현재 내 밑면에 있는 숫자와 같은 인접한 그룹의 사이즈 * 지금 내 밑면에 있는 숫자이다.
5. 그 다음에 내 주사위 아랫면이 a라고 하고 맵에 있는 숫자가 b 라고 한다면 a > b 이면 90도 방향으로 시계 방향 회전
6. 그리고 a < b인 경우에는 90도 반시계 방향으로 회전 시킨다.
7. a == b인 경우에는 이동 방향의 변화는 없다.
8. 그리고 만일 내가 가려한 방향에 이제 더 이상 칸이 없으면 방향을 반대로한다.
9. 이동하는 횟수가 주어지면 그만큼 이동하면 된다.
-- 틀 설계
1. 입력을 받는다, (주사위의 숫자는 처음에는 [0] = 2, [1] = 1, [2] = 5, [3] = 6, [4] = 4, [5] = 3 이다)
2. 주사위의 윗면은 [1] , 그리고 주사위의 아랫면은 [3] 이며 주사위를 해당 방향으로 굴렸을 때 나의 dice를 바뀔 수 있는 roll() 함수를 만든다.
3. 주사위가 그쪽 방향으로 움직였을 때 점수를 얻게 해주는 getScore()를 정의한다.
4. 그 다음에 움직인 다음에 다음의 방향을 결정하는 move() 함수를 만든다.
5. 이제 그러면 for(int i = 0; i < k; i++) 루프에
6. dir을 처음에 1로 결정하고 (동쪽) 바로 move()하고 (move 내에 roll()을 선언) 그 다음에 -> getScore() 을 하면 될 듯하다
7. 그리고서 score 출력
-- 해맸던 점
1. 없음
 */
public class Main {
    public static int r, c , k , score = 0 , dir = 1;
    public static int[][] map , visited;
    public static Point dicePoint = new Point(0,0);
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1, 0} , dice = {2 , 6 , 5 , 1 , 4 , 3};
    public static class Point{
        int y;
        int x;
        public Point(int y, int x){
            this.y = y;
            this.x = x;
        }
    }
    public static void move(){
        /*
        1. 일단 먼저 이동을 시킨다 , 지금 dir 방향으로 1칸을 dicePoint를
        2. 만일 dicePoint에서 1칸 이동시키는데 반대편이다 그러면 반대방향으로 한다. 0 <-> 2 , 1 <-> 3 이렇게 변환시키면 되고
        3. 이동 시킨다음에 dicePoint를 재설정하고 해당 dicePoint의 map 값을 확인한다. (roll을 이미 한 다음에) (dice의 밑면은 1이라고 하자 그냥)
        4. map 값을 확인해서 본인보다 작으면 dir + 1 을 해주고 본인보다 크면 dir - 1 본인과 같으면 변경하지 않는다.
        */
        int y = dicePoint.y;
        int x = dicePoint.x;
        if(y + dy[dir] < 0 || y + dy[dir] >= r || x + dx[dir] < 0 || x + dx[dir] >= c){
            dir = dir == 0 ? 2 : dir == 2 ? 0 : dir == 1 ? 3 : 1;
            move();
            return;
        }
        y += dy[dir];
        x += dx[dir];
        dicePoint = new Point(y , x);
        roll();
        if(map[y][x] > dice[1]){
            dir = dir - 1 < 0 ? 3 : dir - 1;
        }else if(map[y][x] < dice[1]){
            dir = dir + 1 == 4 ? 0 : dir + 1;
        }
    }
    public static void roll(){
        /*
        1. 현재 dir을 가지고서 주사위를 변경하면 됨 dice
        2. 4가지의 방향에 대해서 처리하면 된다.
         */
        int[] tempDice = new int[dice.length];
        tempDice = dice.clone();
        // a -> b 에서 dice[b] = tempDice[a] 이다
        if(dir == 0){ //위
            /*
            1. 0 -> 1 , 1 -> 2 , 2 -> 3 , 3 -> 0
             */
            dice[1] = tempDice[0];
            dice[2] = tempDice[1];
            dice[3] = tempDice[2];
            dice[0] = tempDice[3];
        } else if(dir == 1){ //오른쪽
            /*
            1. 1 -> 4 , 3 -> 5 , 5 -> 1 , 4 -> 3
             */
            dice[4] = tempDice[1];
            dice[5] = tempDice[3];
            dice[1] = tempDice[5];
            dice[3] = tempDice[4];
        } else if(dir == 2){ //아래
            /*
            1. 0 -> 3 , 1 -> 0 , 2 -> 1 , 3 -> 2
             */
            dice[3] = tempDice[0];
            dice[0] = tempDice[1];
            dice[1] = tempDice[2];
            dice[2] = tempDice[3];
        } else{ //왼쪽
            /*
            1. 1 -> 5 , 3 -> 4 , 5 -> 3 , 4 -> 1
             */
            dice[5] = tempDice[1];
            dice[4] = tempDice[3];
            dice[3] = tempDice[5];
            dice[1] = tempDice[4];
        }
    }
    public static void getScore(){
        /*
        1. 현재 dicePoint를 가지고 map[dicePoint.y][dicePoint.x] 값을 뽑아낸다.
        2. 그리고 해당 값과 같은 것을 가지는 것이 있는지 찾아낸다 (size를 기록)
        3. 그리고서 size * value 해서 score에 추가한다.
        4. 그리고 clean을 시킨다.
         */
        Queue<Point> queue = new LinkedList<>();
        int y = dicePoint.y , x = dicePoint.x , value = map[y][x] , size = 1;
        visited[y][x] = 1;
        queue.add(new Point(y, x));
        while(!queue.isEmpty()){
            Point point = queue.poll();
            for(int i = 0; i < 4; i++){
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                if(ny < 0 || ny >= r || nx < 0 || nx >= c || visited[ny][nx] == 1 || map[ny][nx] != value){
                    continue;
                }
                queue.add(new Point(ny , nx));
                visited[ny][nx] = 1;
                size++;
            }
        }
        score += size * value;
        clean();
    }
    public static void clean(){
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                visited[i][j] = 0;
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        map = new int[r][c];
        visited = new int[r][c];

        for(int i = 0; i < r; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < c; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i < k; i++){
            move();
            getScore();
        }

        System.out.println(score);
    }
}
