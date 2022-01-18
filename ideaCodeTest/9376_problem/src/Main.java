import java.util.*;
import java.io.*;

// 9376 : 탈옥
/*
-- 전제조건
상근이는 감옥에서 죄수 두명을 탈옥시켜야한다.
평면도를 얻어서 모든 벽과 문이 나타나 있다.(평면도에)
상근이는 특별한 기술을 이용해서 제어실을 통하지 않고 문을 열려고 한다.
문은 한번 열리면 계속 열린 상태로 있다 , 두명이 탈출하려면 최소 몇개의 문을 열어야 하는 가?
테스트 케이스는 최대 100개가 주어지고 , '.' = 빈공간 , '*' = 벽 , '#' = 문 , '$' = 죄수의 위치이다.
-- 틀 설계
결국 인터넷을 보고서 힌트를 얻음
일단 죄수 1 , 죄수 2 그리고 밖에 있는 상근이 , 이 3명이서 만나는 지점을 다 구하는 것이다.
그러니까 죄수 1 이 해당 지점까지 가는데에 거친 문의 개수
그리고 죄수 2가 해당 지점까지 가는데에 거친 문의 개수
그리고 상근이 , 가 거친 문의 개수가 최소가 되는 지점을 찾아야하는 것이다.
근데 그 최소지점은 무조건 출구가 될 것이다. 일단 그렇게 설계를 하고 설계만 하면 어렵지는 않은 문제인듯
다만 생각하는게 거의 불가능에 가까울 뿐이다.
그리고 어떻게든 문까지는 최소 거리로 가야하기 때문에 #인 경우 뒤에다가 집어넣고 , . 인 경우 앞에다가 집어넣으면 된다.
그러면 해당 지점으로 부터 최소 거리가 구해진다.
-- 해맸던 점
상근이와 죄수 두명이 모두 못가는 위치를 고려하지 못하였음 ('.' 임에도 못가는 공간)
처음에는 dist를 고려해서 짰지만 모든 위치로부터 최소로 지나치는 문을 구하기 위해서는 0 - 1 bfs를 쓰는게 더 맞다고 생각해서
0 - 1 bfs 를 사용하였음
위에를 제외하고는 설계를 알고 나서는 구현에 막힘 없었음
근데 상근이 , 죄수 2명의 만나는 지점을 다 구해서 최소값을 구하는 것이 답이라는 것은 생각을 더 해봐야 할 것 같음
-- 3명이서 bfs를 하는 것 , 즉 3명이서 돌아다닐 때 만나는 지점의 최소값으로 정한 이유 (addPrint 함수로 시각화 하였음)

5 9
****#****
*..#.#..*
****.****
*$#.#.#$*
*********

이 경우에서 3명의 bfs distance 값을 합하면 , (문에서 만났을 경우에는 셋이 동시에 하나의 문을 연 것이니 -2를 해주었음)

6 6 6 6 6 6 6 6 6 6 6
6 0 0 0 0 5 0 0 0 0 6
6 0 8 8 6 5 6 8 8 0 6
6 0 0 0 0 5 0 0 0 0 6
6 0 6 5 5 4 5 5 6 0 6
6 0 0 0 0 0 0 0 0 0 6
6 6 6 6 6 6 6 6 6 6 6

이런식의 배열이 나온다.
정확히 저 4가 찍혀있는 지점 , 저 지점이 최소 값이다 , (0은 무시)
저기서 잘 보면은 #인 부분 정확히 주어진 맵에서는 (3, 4) 에서 만난 것을 볼 수가 있다.
이 경우가 왜 최소값이냐 , 세명이서 움직이면서 문을 마지막에 동시에 연 것을 빼고 연 문이 겹치는 것이 없다
그렇기 때문에 최소값이 될 수 있는 것이다.
상근이를 밖에다가 위치한 이유는 탈출구와 연관이 되서 문을 열어야 하기 때문에 그런 것이다. 왜냐하면 상근이가 밖에서 안으로 들어오면서 탈출구에 문이 있다면 , 혹은 없다고 하더라도
경로의 문을 딸 것이기 때문이다 , 그래서 결국 세명이서 bfs를 돌렸을 때 해당 지점까지 가는데의 따는 문의 최소 개수들을 각각 더해서 거기서 최솟값이 답인 이유는
위의 배열에서도 볼 수 있듯이 셋이 문을 겹치게 따지 않는 선에서의(만나는 지점이 문일 경우를 제외 그때는 -2) 최소 값을 구하는 것이다. 그게 바로 탈출구 까지 따는 문의 최소 값이 되는 것이니까
그래서 나중에 봐도 이해할 수 있도록 더 예제를 해놓으면

7 9
*#**#**#*
*#**.**#*
*#*#.#*#*
*$##*##$*
*#*****#*
*.#...#.*
*********
-------------
6 6 6 6 6 6 6 6 6 6 6
6 0 6 0 0 7 0 0 6 0 6
6 0 7 0 0 7 0 0 7 0 6
6 0 7 0 7 7 7 0 7 0 6
6 0 7 8 8 0 8 8 7 0 6
6 0 7 0 0 0 0 0 7 0 6
6 0 8 8 9 9 9 8 8 0 6
6 0 0 0 0 0 0 0 0 0 6
6 6 6 6 6 6 6 6 6 6 6

여기서 보았을 때 $ 두명은 각각 탈출구 (0,1) , (0,7) 을 선택한 것을 볼 수 있다
이때 서로 만나는 지점은 (0, 1) 혹 (0, 7)인데 이때의 경로는 (3명이 만나는)

--(0, 1) 으로 탈출하는 경우 (세 명이 (0, 1) 에서 만나는 경우)
  ->    <-   (3, 7) 의 죄수는 (0, 7)으로 나와서 화살표 방향으로 이동해서 (0,1)에서 만난 것이다 세명이서 상근이도 (0, 1)로 왼쪽의 화살표 방향대로 이동한 것이다.
   *#**#**#*
   *#**.**#*
   *#*#.#*#*
   *$##*##$*
   *#*****#*
   *.#...#.*
   *********

--(0, 7) 로 탈출하는 경우 (세 명이 (0, 7) 에서 만나는 경우)
     ->      이 경우 (3 , 7) 의 죄수는 다이렉트로 (0, 7)까지 상근이는 화살표 방향대로 , (3, 1)의 죄수도 (0, 1)로 다이렉트로 나온다음에 화살표 방향대로 움직여서 (0, 7)로 이동한 것이다.
   *#**#**#*
   *#**.**#*
   *#*#.#*#*
   *$##*##$*
   *#*****#*
   *.#...#.*
   *********

-- 결론
결국 결론적으로는 세 개의 합이 최소가 아닌 값들은 겹친 문이 없었는데 최소 경로가 아니라거나 , 혹은 서로 겹친 문이 있기에 최적의 경로 뿐만 아니라 틀린 값이라는 것이다.
그래서 세 개의 배열의 합에서 최소인 값을 찾으면 최적의 경로에 , 서로 경로 중 마지막을 제외하고 연 문이 겹치는 것이 없다는 것이다.
 */
public class Main{
    public static int n , r , c , ans = 0;
    public static char[][] map;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static int[][] bfs(Point per){
        /*
        일단 visited 배열을 선언하고
        지금 받은 person의 정보로부터 시작하는데 해당 지점까지의 문의 최소 개수를 구할 것임
        그럴려면 일단은 시작 지점에는 new Point(per.y , per.x , 0) 이렇게 넣어주고
        #일 때에는 queue.add 하고 , .일 떄에는 queue.addFirst 하고 map[ny][nx] 에다가도 해당 value 를 집어넣어준다. #일 때에는 value + 1 을 넣어주고
         */
        int[][] visited = new int[r][c];
        LinkedList<Point> queue = new LinkedList<>();
        queue.add(new Point(per.y , per.x , 0));

        for(int i = 0; i < visited.length; i++){
            Arrays.fill(visited[i] , -1);
        }

        visited[per.y][per.x] = 0;

        while(!queue.isEmpty()){
            Point point = queue.poll();
            int value = point.value;
            for(int i = 0; i < 4; i++){
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                if(ny < 0 || ny >= r || nx < 0 || nx >= c || visited[ny][nx] != -1 || map[ny][nx] == '*'){
                    continue;
                }
                if(map[ny][nx] == '.'){
                    queue.addFirst(new Point(ny , nx , value));
                    visited[ny][nx] = value;
                }
                if(map[ny][nx] == '#'){
                    queue.add(new Point(ny , nx , value + 1));
                    visited[ny][nx] = value + 1;
                }
            }
        }

        return visited;

    }
    public static class Point{
        int y;
        int x;
        int value;
        public Point(int y, int x , int value){
            this.y = y;
            this.x = x;
            this.value = value;
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        /*
        상근이를 밖에다가 두기 위해서 map을 조금 더 넓게 선언해야한다.
        그리고 map은 변경하지 않고 visited만 변경할 것이다.
        해당 지점까지 갈 때의 최소 몇개의 문을 거쳐야 하는지를 구할 것이다.
        그것은 쉽게 0-1bfs를 이용해서 할 수 있다.
        그리고 결국 이 문제의 의도는 최소 지점을 찾는 것이고 , 최소 지점은 3명이서 만났을 때의 열어야 했을 문의 개수가 최소인 지점인데 이것은 차차 이해하면 될 것 같다.
         */
        n = Integer.parseInt(input.readLine());
        for(int i = 0 ;i < n; i++){
            st = new StringTokenizer(input.readLine());
            r = Integer.parseInt(st.nextToken()) + 2;
            c = Integer.parseInt(st.nextToken()) + 2;
            List<Point> person = new ArrayList<>();
            map = new char[r][c];

            for(int j = 1; j < r - 1; j++){
                String string = input.readLine();
                for(int v = 1; v < c - 1; v++){
                    map[j][0] = map[j][c - 1] = '.';
                    map[j][v] = string.charAt(v - 1);
                    if(map[j][v] == '$'){
                        person.add(new Point(j , v , 0));
                        map[j][v] = '.';
                    }
                }
            }

            for(int j = 0; j < c; j++){
                map[0][j] = map[r - 1][j] = '.';
            }

            person.add(new Point(0 , 0 , 0));

            int[][] dist1 = bfs(person.get(0));
            int[][] dist2 = bfs(person.get(1));
            int[][] dist3 = bfs(person.get(2));

            addPrint(dist1 , dist2, dist3); //
            ans = Integer.MAX_VALUE;

//            visitedPrint(dist1);
//            visitedPrint(dist2);
//            visitedPrint(dist3);

            for(int j = 0; j < r; j++){
                for(int v = 0; v < c; v++){
                    if(map[j][v] == '.' && dist1[j][v] == -1) continue;
                    if(map[j][v] == '*') continue;
                    int value = dist1[j][v] + dist2[j][v] + dist3[j][v];
                    if(map[j][v] == '#'){
                        value -= 2;
                    }
//                    System.out.println("y : " + j + " x : " + v + " value : " + value);
                    ans = Math.min(ans , value);
                }
//                mapPrint(map);
            }
            System.out.println(ans);
        }
    }
    public static void addPrint(int[][] dist1 , int[][] dist2 , int[][] dist3){
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                int value = dist1[i][j] + dist2[i][j] + dist3[i][j];
                if(map[i][j] == '*'){
                    System.out.print(0 + " ");
                }
                if(map[i][j] == '#'){
                    System.out.print(value - 2 + " ");
                }
                if(map[i][j] == '.'){
                    System.out.print(value + " ");
                }
            }
            System.out.println();
        }
    }
//    public static void mapPrint(char[][] dist){
//        System.out.println("----------dist--------------");
//        for(int i = 0; i < r; i++){
//            for(int j = 0; j < c; j++){
//                System.out.print(dist[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }
//    public static void visitedPrint(int[][] dist){
//        System.out.println("----------dist--------------");
//        for(int i = 0; i < r; i++){
//            for(int j = 0; j < c; j++){
//                System.out.print(dist[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }
}
