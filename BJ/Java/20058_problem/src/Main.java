import java.util.*;
import java.io.*;

// 20058 : 마법사 상어와 파이어스톰
/*
-- 전제 조건
1. 2 ^ n 승의 크기의 격자에서 진행한다.
2. 각자 map[r][c] 에 얼음의 양들이 주어진다.
3. 그리고 마법을 쓰면 각자 격자들이 나눠지고 그 격자들의 얼음의 양들이 rotate 된다.
4. 마법을 쓰고 나서 얼음이 있는 칸 3개 또는 그 이상과 인접해 있지 않은 칸은 얼음의 양이 1 줄어든다.
5. 마지막으로 출력을 총 map에 남아있는 얼음의 양과 남아있는 얼음 중 가장 큰 덩어리가 차지하는 칸의 개수이다.
-- 틀 설계
1. 입력을 받는다.
2. 각자 칸을 나눠지는 division 함수를 만든다.
3. 그리고 그 나누어진 칸을 받으면 90도 회전 시켜주는 rotate 함수를 만들어준다.
4. 얼음을 녹이는 melt 함수를 만든다.
5. 마지막으로 가장 큰 덩어리를 찾아 낼 bfs 함수를 만든다.
6. 순서는 마법을 for 문으로 하나하나씩 돌면서 division 함수를 호출한다.
7. division 함수가 rotate 함수를 호출하고 끝에서 melt 함수를 호출한다.
8. 다 돌고나서 모든 A[r][c]의 얼음의 양들을 재고 bfs로 가장 큰 얼음 덩어리를 찾는다.
-- 해맸던 점
1. 내가 검은색으로 칠해져 있는 부분만 돌린다고 착각하고 있었음 .. 그냥 다 돌리는 거였음 생각보다 개 쉬운 문제였음
 */
public class Main {
    public static int[][] map , visited;
    public static int[] magic ,dx = {0 , 1, 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static int n , magicCount , totalIce = 0 , maxSize = 0 , realSize;
    public static void division(int level){
        /*
        1. 일단 맵을 다 나눈다.
        2. 나눈 첫점과 끝점을 rotate에다가 넘긴다.
        3. 그럼 rotate가 돌려준다.
        4. 그런 다음에 melt해주면 된다.
        5. 첫점과 끝점을 결정하는 방법은 level = 1 , size = 2 (0 , 0) - (1, 1), (0, 4) - (1, 5) , (2, 2) - (3, 3) 이런식으로 계속됨
         */
        int size = (int)Math.pow(2 , level);
//        System.out.println(size);
//        int index = 0;
//        for(int i = 0; i < realSize; i += size) {
//            index++;
//            int start = 0;
//            if(index % 2 == 0){
//                start = size;
//            }
//            for(int j = start; j < realSize; j += size * 2){
//                rotate(new Point(i , j) , new Point(i + size - 1 , j + size - 1));
//            }
//        } 이거 내가 알고보니까 더 어렵게 했었음
        for(int i = 0; i < realSize; i+= size){
            for(int j = 0; j < realSize; j += size){
                rotate(new Point(i , j) , new Point(i + size - 1, j + size - 1));
            }
        }
        melt();
    }
    public static void rotate(Point start , Point finish){
        /*
        1. 첫점과 끝점을 받아서 for문으로 90도 돌려준다.
        2. 그냥 map을 돌려주기만 하면 역할 끝
        3. 원래 읽던 대로가 아니라 왼쪽 하단부터 열 순서대로 읽어서 집어넣으면 됨
        4. 메모리를 조금 아끼기 위해서 start , finish를 봐서 세로는 finish - start + 1이고 (행) 가로는 finish.x - start.x + 1 (열)이다
        5. 이 사이즈대로 int 배열을 선언해놓고 카피 해놓은 다음에 deepCopy로
        6. 그 다음 카피 해놓은 것에서 순서대로 읽어서 map의 내용을 바꾸면 될 듯
         */
        int rowSize = finish.y - start.y + 1;
        int colSize = finish.x - start.x + 1; //솔직히 colSize ,rowSize 나누지말고 하나로 해도 되는데 귀찮으니까 그냥 하나로 하자
        int[][] tempMap = new int[rowSize][colSize];
//        System.out.println("startY : " + start.y + " startX : " + start.x + " finishY : " + finish.y + " finishX : " + finish.x);
        int y = 0;
        int x = 0;
        for(int i = start.y; i <= finish.y; i++){
            for(int j = start.x; j <= finish.x; j++){
//                System.out.println("y : " + i + " x : " + j);
                tempMap[y][x++] = map[i][j];
            }
            y++;
            x %= colSize;
        }
//        System.out.println("-------------rotate before---------------");
//        for(int i = 0; i < colSize; i++){
//            for(int j = 0; j < rowSize; j++){
//                System.out.print(tempMap[i][j] + " ");
//            }
//            System.out.println();
//        }
        y = start.y;
        x = start.x; // 1 , 1 - 2, 2 라고 가정해보자
//        System.out.println("-----------rotate after --------------");
        for(int i = 0; i < colSize; i++){
            for(int j = rowSize - 1; j != -1; j--){
                map[y][x++] = tempMap[j][i];
//                System.out.print(map[y][x - 1] + " ");
            }
            y++;
            x = x > finish.x ? start.x : x;
//            System.out.println();
        }
    }
    public static void melt(){
        /*
        1. for 문으로 모든 범위를 돌면서
        2. 그 주변에 값이 0이 아닌 것들이 몇개가 있는지 본다.
        3. 만약 2개 이하라면 , ice를 1 감소시킨다.
        4. 주의해야 할 점은 ice 가 0인 경우는 그냥 넘어가야 한다는 점이다.'
        5. melt는 이전에 상황을 그대로 가지고 가야함
         */
        int[][] tempMap = new int[realSize][realSize];
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                System.arraycopy(map[i], 0 , tempMap[i] ,0 , map[i].length);
            }
        }
//        System.out.println("-------------melt before---------------");
//        for(int i = 0; i < realSize; i++){
//            for(int j = 0; j < realSize; j++){
//                System.out.print(map[i][j] + " ");
//            }
//            System.out.println();
//        }
        for(int i = 0; i < realSize; i++){
            for(int j = 0; j < realSize; j++){
                if(tempMap[i][j] != 0){
                    int iceCount = 0;
                    for(int c = 0; c < 4; c++){
                        int ny = i + dy[c];
                        int nx = j + dx[c];
                        if(ny < 0 || ny >= realSize || nx < 0 || nx >= realSize){
                            continue;
                        }
                        if(tempMap[ny][nx] != 0){
                            iceCount++;
                        }
                    }
                    if(iceCount <= 2){
                        map[i][j] -= 1;
                    }
                }
            }
        }
//        System.out.println("-------------melt after---------------");
//        for(int i = 0; i < realSize; i++){
//            for(int j = 0; j < realSize; j++){
//                System.out.print(map[i][j] + " ");
//            }
//            System.out.println();
//        }
    }
    public static void bfs(int y , int x){
        /*
        1. 일단 visited 에다가 지금 호출된 위치의 숫자를 1로 채워넣고
        2. queue로 bfs를 싹 돌고 sum에다가 저장한다 총합을
        3. 마지막에 maxLump 보다 크면 넣는다.
         */
        int size = 1;
        Queue<Point> queue = new LinkedList<>();
        visited[y][x] = 1;
        queue.add(new Point(y , x));
        while(!queue.isEmpty()){
            Point point = queue.poll();
            for(int i = 0; i < 4; i++){
                int nx = point.x + dx[i];
                int ny = point.y + dy[i];
                if(ny < 0 || ny >= realSize || nx < 0 || nx >= realSize || visited[ny][nx] == 1 || map[ny][nx] == 0){
                    continue;
                }
                visited[ny][nx] = 1;
                queue.add(new Point(ny , nx));
                size += 1;
            }
        }
        if(maxSize < size){
            maxSize = size;
        }
    }
    public static class Point{
        int y;
        int x;
        public Point(int y, int x){
            this.y = y;
            this.x = x;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        n = Integer.parseInt(st.nextToken());
        magicCount = Integer.parseInt(st.nextToken());
        realSize = (int)Math.pow(2 , n);

        map = new int[realSize][realSize];
        visited = new int[realSize][realSize];
        magic = new int[magicCount];

        for(int i = 0; i < realSize; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < realSize; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(input.readLine());
        for(int i = 0; i < magicCount; i++){
            magic[i] = Integer.parseInt(st.nextToken());
        }

        for(int i = 0; i < magicCount; i++){
            division(magic[i]);
        }
//        System.out.println("----------finish-------------");
        for(int i = 0; i < realSize; i++){
            for(int j = 0; j < realSize; j++){
                totalIce += map[i][j];
//                System.out.print(map[i][j] + " ");
                if(map[i][j] != 0 && visited[i][j] != 1){
                    bfs(i , j);
                }
            }
//            System.out.println();
        }
        System.out.print(totalIce + "\n" + maxSize);
    }
}