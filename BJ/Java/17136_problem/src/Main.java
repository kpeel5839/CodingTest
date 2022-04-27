import java.util.*;
import java.io.*;

// 17136 : 색종이 붙히기
/*
-- 전제조건
10 * 10 의 크기의 정사각형 맵이 있고
1이 적혀있는 곳에 색종이로 채워야한다.
근데 1 * 1 , 2 * 2.... 5 * 5 까지의 색종이가 각 5개씩 가지고 있다.
이 때 1인 곳을 다 채우는 경우에서의 색종이의 최솟값을 출력하면 된다.

0인 곳에는 절대로 색종이를 붙혀서는 안된다.
-- 틀 설계
일단 map을 입력받을 때 들어가야하는 종이의 개수를 센다.
그 다음에 dfs로 그 수만큼의 색종이를 할당시킬 수 있도록 1 * 1 ~ 5 * 5 색종이를 paper 배열에다가 저장한다.(인덱스가 색종이 크기 , 값이 개수)
그 다음에 dfs로 해서 depth == 5 && remain == 0일 때에 이제 fill 함수를 호출한다.
fill함수는 이제 paper 에 적혀있는 숫자대로 진행하는데 여기서도 재귀적으로 해결해야 한다.

-- 해맸던 점
map[ny][nx] == 0 일때에는 그냥 다음 fill을 호출해주어야 하는데
그것을 처리안해줘서 살짝 해맸었음
 */
public class Main {
    public static int n = 10 , ans = Integer.MAX_VALUE , needPaperCount;
    public static int[] paper = new int[5];
    public static int[][] map = new int[n][n];
    public static int[][] fillPaper = new int[n][n];
    public static class Point{
        int y;
        int x;
        public Point(int y , int x){
            this.y = y;
            this.x = x;
        }
    }
    public static void fill(int y , int x , int count , int remain){
        /*
        이제 paper 배열을 받을 것이다. 그래서 0 , 0 에서 부터 재귀적으로 채우기를 시작할 것이다.
        지금 현재에서 1을 만나게 되면 0 ~ 4까지의 색종이를 하나씩 써본다.
        그러면서 색종이만큼 칠한다. (fillPaper 배열에다가)
        그러면서 반복문으로 채워갈 것인데 , 채워지면 remain - 색칠한 개수 , 하고 만일 안되면 하지 않는다.
        항상 x++를 하고 만일 x 가 n - 1 과 같으면 y ++ 를 해주고 x = 0 으로 해준다.
        그 다음에 채워줄 때도 아얘 채우면서 확인하다가 , 안되면 다시 똑같이 지운다.
        그리고 paper 배열도 하나 쓰면 -- 해주고 , 다시 재귀호출에서 돌아오면 ++ 해준다.
        그래야지 유지할 수 있다.
        끝내는 조건은 y == n && x == 0 일때이다, 이때 remain == 0 이면 그때 ans와 비교해서
        지금까지 쓴 색종이의 수를 출력하면 된다.
         */

//        for(int i = 0; i < n; i++){
//            for(int j = 0; j < n; j++){
//                System.out.print(fillPaper[i][j] + " ");
//            }
//            System.out.println();
//        }

        if(y == n && x == 0){
            if(remain == 0) ans = Math.min(ans , count);
            return;
        }

        if(fillPaper[y][x] == 1 || map[y][x] == 0){ // 이미 채웠을 때 , 혹은 색종이를 채우지 않아도 될 때
            if(x == n - 1) fill(y + 1 , 0 , count , remain);
            else fill(y , x + 1 , count , remain);
        }

        for(int i = 0; i < 5; i++){
            boolean able = true;
            Queue<Point> queue = new LinkedList<>();
            if(paper[i] != 0) { // 색종이가 있을 때 , 색종이 안남았으면 여기까지 가지도 않음
                Loop:
                for (int j = y; j < y + i + 1; j++) {
                    for (int c = x; c < x + i + 1; c++) {
                        if (j < 0 || j >= n || c < 0 || c >= n || fillPaper[j][c] == 1 || map[j][c] == 0) {
                            able = false; //이미 내가 채운 구간이거나? 아니면 색종이를 붙힐 공간이 아니면 그냥 끝냄
                            break Loop;
                        }
                        fillPaper[j][c] = 1;
                        queue.add(new Point(j, c));
                    }
                }

                if (able) { // 실패했을 경우
                    paper[i]--;
                    if (x == n - 1) fill(y + 1, 0, count + 1, remain - (int) Math.pow(i + 1, 2));
                    else fill(y, x + 1, count + 1, remain - (int) Math.pow(i + 1, 2));
                    paper[i]++;
                }

                while (!queue.isEmpty()) {
                    Point point = queue.poll();
                    fillPaper[point.y][point.x] = 0;
                }
            }
        }
    }
    public static void dfs(int depth , int remain){
        /*
        if(depth == 5) 하고 remain == 0 이면 fill(0 , 0 , needPaperCount)를 호출한다.
        그 다음에 for문에서는 for(int i = 0; i <= 5; i++) 를 실시하면서 paper[depth] 에다가 계속 넣어주고
        넣은 숫자에다가 Math.pow(depth + 1 , 2) * i 만큼 remain을 제해준다.
         */

        if(depth == 5){
            if(remain == 0) {
//                System.out.println(Arrays.toString(paper));
                fill(0, 0 , 0 , needPaperCount);
            }
            return;
        }

        for(int i = 0; i <= 5; i++){
            paper[depth] = i;
            dfs(depth + 1 , remain - (int)Math.pow(depth + 1 , 2) * i);
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        needPaperCount = 0;
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < n; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] == 1) needPaperCount++;
            }
        }

        dfs(0, needPaperCount);
        System.out.println(ans == Integer.MAX_VALUE ? -1 : ans);
    }
}