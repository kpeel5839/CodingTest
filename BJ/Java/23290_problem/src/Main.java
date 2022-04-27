import java.util.*;
import java.io.*;

// 23290 : 마법사 상어와 복제
/*
-- 전제조건
1. 4 x 4 크기의 격자에서 시작한다.
2. 격자에는 물고기 M마리가 있다 , 각 물고기는 격자의 칸 하나에 들어가 있으며 이동 방향을 가지고 있다.
3. 이도 방향은 8가지 방향을 가지고 있다 , 마법사 상어도 연습을 위해서 격자에 들어가 있다.
4. 상어도 격자의 한 칸에 들어가 있다 , 둘 이상의 물고기가 같은 칸에 있을 수도 있으며 , 마법사 상어와 물고기가 같은 칸에 있을 수도 있음
5. 일단 순차적으로 상어의 마법 연습 한번은 해당 순서의 작업을 거친다.
6. 1. 상어가 모든 물고기에게 복제 마법을 시전한다, 근데 이 모든 작업이 완료된 후에 복제된다 -> 2. 모든 물고기가 자신의 방향으로 한칸 이동한다 , 상어가 있는 칸 물고기의 냄새가 있는 칸 , 격자의 범위를 벗어나는 칸으로는 이동할 수 없다.
, 그렇기 떄문에 자신이 가지고 있는 이동방향이 이동할 수 있는 칸을 향할 때까지 방향을 45도 반시계 방향으로 계속 회전 시킨다 , 만일 다시 본인의 방향으로 돌아올 때까지 이동할 칸이 없었다 그러면 이동하지 않는다.
-> 3. 상어가 연속해서 3칸 이동한다 , 상어는 현재 칸에서 상하 좌우로 인접한 칸으로 이동할 수 있다 , 연속해서 이동하는 칸 중에 격자의 범위를 벗어나는 칸이 있으면 , 그 방법은 불가능한 이동 방법이다(무조건 3칸 움직여야 하는 것)
, 연속해서 이동하는 중에 물고기를 지나치면 물고기는 다 뒤지고 그 물고기들은 냄새를 남긴다 , 가능한 이동 방법 중에서 제외되는 물고기의 수가 가장 많은 방법으로 이동한다 , 만일 그러한 방법이 여러가지라면 , 사전 순으로 가장 앞서는 방법을 선택한다.
-> 4. 그리고 이때 이제 2번전의 물고기의 냄새가 격자에서 사라진다. 그러니까 물고기의 냄새 유효기간은 3으로 하고 물고기가 격자에서 사라지면서 3을 남기고 그 다음에 바로 냄새를 저하시키면 될 듯
-> 5. 이제 이전에 복사해놨던 물고기가 1에서의 위치와 방향을 그대로 갖게 된 상태에서 격자에 생겨난다.
-- 틀 설계
1. PriorityQueue 로 관리하면 될 듯 상어의 최종 움직임은(상 , 상 , 상) 부터 하면 된다. (makeMovingList() 함수를 만들어서 만들자)
2. smallWeek() 함수를 만들어서 냄새를 저하시킨다 , 그냥 -1 만 다해주면 됨 , 0이 아니면
3. 따로 smell , map 등을 만들어서 관리한다 , map은 일단 fish 객체로 다 할 것인데 여기서 이제 물고기를 추가하는 방법에서
4. 물고기를 추가할 때 본인과 같은 dir을 가진 물고기가 있다면 거기다가 추가하고 , 마리수를 높여주는 것 , 아니라면 그냥 list.add를 하면된다.
5. 그러면 복제 마법은 따로 함수 만들지 말고 일단 deepCopy로 deepMap을 관리해놓는다.
6. 그러고서 fish들이 움직이는 fishMove() 함수를 만들어서 물고기를 움직이게 하고
7. 상어가 움직이면서 smell을 남긴다 , 일단 상어의 이동 배열과 , 현재 위치를 넣어주면 몇번째가 가장효율적인지 선택해주는 함수인
8. decideMove() 함수도 만들어준다. 그러면서 decideMove하면서 결정된 위치로 상어를 실제로 move시켜주는
9. move() 함수를 만들어주고 move를 시켜준다음에 smellWeek를 시켜주고 그 다음에 deepMap으로 해놓은 것들을 이미 존재하면 없애주고 , 그런식으로 해서 진행하고
10. 마지막에 4 x 4 격자를 다 돌면서 물고기가 총 몇마리가 남았는지 확인 후 출력한다.
-- 해맸던 점
1. decideMove 함수에서 innerMap 을 새로 만들때 count 위치를 잘 못 선언해서 애좀 먹었음
2. move 함수에서 처음 들어온 방향을 처리하는 것을 처리를 안해서도 애먹었음
3. 그리고 또 dir - 1 < 0 ? 7 : dir - 1 이래야하는데 < 를 == 로해서 해맸음
4. fishMove 에서 그냥 이동한 다음에 이동한 장소에 있었던 애들 0으로 만들어버림 , count를
5. 근데 그게 아니라 map[i][j].get(c).count - fish.count 즉 지금 움직이는 물고기들만 빼줬어야 했음 , deepMap에 지금 현재 그 칸에 존재하는 애들만큼 빼줘야했었음 근데 이것을 안했고
6. 입력받을 때 putFish에서 그냥 1마리씩 받을 때에는 다 1씩 넣으면 되는데 괜히 map[i][j].get(c).count + 1 이렇게 해가지고 괜히 더 늘어나게 했음 그래서 값 이상하게 나왔었음
7. 이번에도 설계는 다 괜찮았는데 실수가 너무 많았고 , 잘못 생각한 부분들도 있었음 , 시간을 줄이려면 조금 더어어어 생각해야할 듯 , 사소한 것 하나하나 까지도 신경을 써야할 듯함
 */
public class Main {
    public static int n , s , ans = 0 , sharkMovingListIndex = 0;
    public static List<Fish>[][] map = new ArrayList[4][4];
    public static int[][] smell = new int[4][4];
    public static Point sharkPoint;
    public static int[][] sharkMovingList = new int[64][3]; // 상어 이동 리스트
    public static int[] dx = {-1 , -1 , 0 , 1 , 1 , 1 , 0 , -1} , dy = {0 , -1 , -1 , -1 , 0 , 1 , 1 , 1};
    public static void makeMoveList(int depth, int[] arr){
        /*
        1. 말 그대로 상어의 움직임을 만드는 과정임
        2. 그래서 그냥 움직임의 경우의 수가 64가지가 있는데 그냥 다 한번 만들어놓고 시작하는 것
         */
        if(depth == 3){
            sharkMovingList[sharkMovingListIndex++] = arr.clone();
            return;
        }

        for(int i = 0; i < 4; i++){//상 , 좌 , 하 , 우 순이고 상 = 2 좌 = 0 , 하 = 6 우 = 4
            int dir = i == 0 ? 2 : i == 1 ? 0 : i == 2 ? 6 : 4;
            arr[depth] = dir;
            makeMoveList(depth + 1 , arr);
        }

    }
    public static int decideMove(){
        /*
        1. 현재 지금 상어 위치는 sharkPoint로 얻는다.
        2. 현재 상어의 위치를 얻고 for 문으로 계속 돌면서 sharkMovingList를
        3. 어떤 것이 제일 많이 물고기를 잡아먹나 max를 구하고
        4. 마지막으로 그 max 값으로 한번 더 지역변수 배열인 eat 배열을 탐색하면서 가장 처음에 발견된 index를 반환한다.
         */
        int[] eat = new int[64];
        int max = 0;
        int[][] innerMap = new int[4][4];
        int[][] deepMap = new int[4][4];
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                int count = 0;
                for(Fish fish : map[i][j]){
                    count += fish.count;
                }
                innerMap[i][j] = count;
            }
        }
        for(int i = 0; i < 64; i++){
//            System.out.println(Arrays.toString(sharkMovingList[i]));
            int y = sharkPoint.y;
            int x = sharkPoint.x;
            int eatCount = 0;
            boolean select = true;
//            System.out.println("fucking next");
            for(int c = 0; c < 4; c++){
                System.arraycopy(innerMap[c] , 0 , deepMap[c] , 0 , innerMap[c].length);
//                System.out.println(Arrays.toString(deepMap[c]));
            }
            for(int j = 0; j < 3; j++){
                y = y + dy[sharkMovingList[i][j]];
                x = x + dx[sharkMovingList[i][j]];
                if(y < 0 || y >= 4 || x < 0 || x >= 4){
//                    System.out.println(i);
                    select = false;
                    break;
                }
                eatCount += deepMap[y][x];
                deepMap[y][x] = 0;
            }
            if(select){
                eat[i] = eatCount;
            }else{
                eat[i] = -1;
            }
            if(select && eatCount > max){
                max = eatCount;
//                System.out.println(i);
            }
        }
//        System.out.println();
//        System.out.println(Arrays.toString(eat));
        for(int i = 0; i < 64; i++){
            if(max == eat[i]){
                max = i;
                break;
            }
        }
        return max;
    }
    public static Point move(Point point , int dir){
        /*
        1. fishMove에서 쓰이는 move 함수이다.
        2. 해당 지점의 point와 dir을 주면 처음에 해당 dir을 기점으로 가능한 곳이 나올 때까지 하다가 나오면 point를 반환하고
        3. 만약에 dir이 다시 원점으로 돌아올 때까지 즉 모든 방향을 탐색했는데 갈 곳이 없으면 지금 본인의 point를 반환한다.
        4. 해당 point도 결국 반환되서 결국 fishMove에서 putFish를 해야하기 때문에 어찌보면 putFish가 정말 중요한 함수이다.
        5. 그리고 반시계 방향으로 돌려면 -1을 해야한다 , 일단 possible boolean 변수로 움직이는게 가능한 곳이 있나 했을 떄 possible == true이면 있는 것이고 false면 없는 것으로
        6. for(int i = 0; i < 7; i++) 로 계속 dir을 바꿔주면 된다 , 그러면 해당 다 돌아보는 것이기 때문에 이것을 다했는데도 false이면 그냥 지금 지점 반환하면 되고
        7. 아니면 바꾼 dir로 한칸 움직인다음 보내면 됨
        8. 그리고 조건은 냄새가 있거나 , sharkPoint와 동일하지 않으면 됨
         */
        boolean possible = false; //중간에 이동가능한 point를 찾으면 true로 바꾸고 바로 빠져나오면 됨
        for(int i = 0; i < 8; i++){
            int y = point.y + dy[dir];
            int x = point.x + dx[dir];
            dir = dir - 1 < 0 ? 7 : dir - 1;
//            System.out.println("y : " + y + " x : " + x + " dir : " + dir);
            if(y < 0 || y >= 4 || x < 0 || x >= 4 || (y == sharkPoint.y && x == sharkPoint.x) || smell[y][x] != 0){
                continue;
            }
            possible = true;
            break;
        }
        if(possible){ //성공한 경우
            dir = dir + 1 == 8 ? 0 : dir + 1;
//            System.out.println(dir);
            return new Point(point.y + dy[dir] , point.x + dx[dir] , dir);
        }else{
            return new Point(point.y , point.x , dir);
        }
    }
    public static void fishMove(){
        /*
        1. 그냥 모든 격자를 돌면서 fish 들을 move 명령을 주고 받은 포인터로
        2. putFish 하면 된다
         */
        List<Fish>[][] deepMap = new ArrayList[4][4];
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                deepMap[i][j] = new ArrayList<>();
                for(int c = 0; c < 8; c++){
                    Fish fish = map[i][j].get(c);
                    deepMap[i][j].add(new Fish(c , fish.count));
                }
            }
        }
//        deepMapPrint(deepMap);
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                for(int c = 0; c < 8; c++){
                    Fish fish = deepMap[i][j].get(c);
                    if(fish.count != 0){
                        Point point = move(new Point(i , j) , fish.dir);
//                        System.out.println("previous Point : (" + i + "," + j + ") , dir : " + c);
//                        System.out.println("point : (" + point.y + "," + point.x + ")" + " , dir : " + point.dir + " count : " + fish.count);
                        if(!(point.y == i && point.x == j)){ //만일 움직이지 않았다면 그러면 아무것도 안하고 움직였다면 움직여줌
                            putFish(new Point(point.y , point.x) , point.dir , fish.count); // 다음 위치로 이동시켜줌 , 그러면서 방향도 다른 방향으로
                            map[i][j].set(c , new Fish(fish.dir , map[i][j].get(c).count - fish.count)); // 이전에 물고기들 없애고
                        }
//                        deepMapPrint(map);
                    }
                }
            }
        }
    }
    public static void smellWeek(){
        /*
        1. 그냥 smell 배열을 0이 아닌 것들을 -1을 해주면 됨
         */
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(smell[i][j] != 0){
                    smell[i][j] -= 1;
                }
            }
        }
    }
    public static void setAns(){
        /*
        1. 이것은 그냥 map[i][j] 가 null 이 아니라면 list를 돌면서 count들을 다 긁어 모아서 ans에다가 더하면 됨
         */
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                for(Fish fish : map[i][j]){
                    ans += fish.count;
                }
            }
        }
    }
    public static void putFish(Point point , int dir , int count){
        /*
        1. 그냥 넘어온 것에 지금 현재 이 dir에 있는 count와 지금 넘어온 count와 더해서 넣어주면 된다.
         */
        Fish fish = map[point.y][point.x].get(dir);
//        System.out.println("dir : " + dir + " count : " + count);
        map[point.y][point.x].set(dir , new Fish(dir , fish.count + count));
    }
    public static class Fish{
        int dir;
        int count;
        public Fish(int dir , int count){
            this.dir = dir;
            this.count = count;
        }
        @Override
        public String toString(){
            return "dir : " +dir + "count : " + count;
        }
    }
    public static class Point{
        int y;
        int x;
        int dir;
        public Point(int y , int x){
            this.y = y;
            this.x = x;
        }
        public Point(int y, int x, int dir){
            this.y = y;
            this.x = x;
            this.dir = dir;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        n = Integer.parseInt(st.nextToken());
        s = Integer.parseInt(st.nextToken());

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                map[i][j] = new ArrayList<>();
                for(int c = 0; c < 8; c++){ //전부 초기화
                    map[i][j].add(new Fish(c , 0));
                }
            }
        }

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;
            int dir = Integer.parseInt(st.nextToken()) - 1;
            putFish(new Point(y , x) , dir , 1);
        }

//        mapPrint();

        st = new StringTokenizer(input.readLine());
        sharkPoint = new Point(Integer.parseInt(st.nextToken()) - 1 , Integer.parseInt(st.nextToken()) - 1);

        makeMoveList(0 , new int[]{0 , 0 , 0});

//        for(int i = 0; i < 64; i++){
//            System.out.println(Arrays.toString(sharkMovingList[i]));
//        }
//        mapPrint();
        for(int i = 0; i < s; i++){
            List<Fish>[][] deepMap = new ArrayList[4][4];
            for(int j = 0; j < 4; j++){
                for(int c = 0; c < 4; c++){
                    deepMap[j][c] = new ArrayList<>();
                    for(int v = 0; v < 8; v++){
                        Fish fish = map[j][c].get(v);
                        deepMap[j][c].add(new Fish(fish.dir , fish.count));
                    }
                }
            }
            fishMove();
//            mapPrint();
            int select = decideMove();
//            System.out.println(Arrays.toString(sharkMovingList[select]));
            for(int j = 0; j < 3; j++){
                int dir = sharkMovingList[select][j];
                int ny = sharkPoint.y + dy[dir];
                int nx = sharkPoint.x + dx[dir];
                boolean remainSmell = false;
                sharkPoint = new Point(ny , nx); // sharkPoint가 계속 바뀌게 하면서 움직임을 계속 따라감
                for(int c = 0; c < 8; c++){
                    if(map[ny][nx].get(c).count != 0){
                        remainSmell = true;
                    }
//                    System.out.println("ny : " + ny + " nx : " + nx);
                    map[ny][nx].set(c , new Fish(c , 0)); // 다 잡아먹는게 애초에 그냥 다시 선언하면 됬었음 0으로 만들고 , 현재 값을 변경하는 것은 deepMap이 변경될 위험이 너무커서 안됨
                }
                if(remainSmell){
                    smell[ny][nx] = 3;
                }
            }
//            System.out.println("sharkPoint : (" + sharkPoint.y + "," + sharkPoint.x + ")");
            smellWeek();
            for(int j = 0; j < 4; j++){
                for(int c = 0; c < 4; c++){
                    for(Fish fish : deepMap[j][c]){
                        if(fish.count != 0) {
                            putFish(new Point(j, c), fish.dir, fish.count);
                        }
                    }
                }
            }
//            mapPrint();
//            smellPrint();
        }

        setAns();

        System.out.println(ans);
    }
//    public static void mapPrint(){
//        System.out.println("---------mapPrint------------");
//        for(int i = 0; i < 4; i++){
//            for(int j = 0; j < 4; j++){;
//                int count = 0;
//                if(i == 0 && j == 2){
//                    System.out.println(map[i][j]);
//                }
//                for(int c = 0; c < 8; c++){
//                    count += map[i][j].get(c).count;
//                }
//                System.out.print(count + " ");
//            }
//            System.out.println();
//        }
//    }
//    public static void deepMapPrint(List<Fish>[][] deepMap){
//        System.out.println("---------deepMapPrint------------");
//        for(int i = 0; i < 4; i++){
//            for(int j = 0; j < 4; j++){;
//                int count = 0;
//                if(i == 0 && j == 2){
//                    System.out.println(map[i][j]);
//                }
//                for(int c = 0; c < 8; c++){
//                    count += deepMap[i][j].get(c).count;
//                }
//                System.out.print(count + " ");
//            }
//            System.out.println();
//        }
//    }
//    public static void smellPrint(){
//        System.out.println("----------smellPrint------------");
//        for(int i = 0; i < 4; i++){
//            for(int j = 0; j < 4; j++){
//                System.out.print(smell[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }
}
