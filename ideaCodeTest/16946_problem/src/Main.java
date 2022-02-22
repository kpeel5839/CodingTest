import java.util.*;
import java.io.*;

// 16956 : 벽 부수고 이동하기 4
/*
-- 전체 설계
각 벽을 부시고 , 이동할 수 있는 칸으로 변경한다음
거기서 이동할 수 있는 칸의 개수를 출력하는 데 10으로 나눠서 한다.
-- 틀 설계
펑범한 생각으로 해당 1마다 , bfs를 돌려서 count 해서 다 셀 수 있다,
근데 당연히 이 문제에서 그렇게 평범한 생각으로 하면 시간초과이다.
다른 방법을 찾아야 한다.

0 인 곳만 탐방해서 , 영역을 매겨 놓는데,
영역 번호를 붙히고 , 거기 영역에 몇개있는지를 다 집어넣어놓는다.
space class 를 만들어서 space map을 만들어야 한다라는 것이다.

그렇게 해서 , bfs로 영역의 숫자들을 다 채워놓은 다음에 ,
근데 이게 문제이다 , 같은 것들을 어떻게 골라내냐 , 솔직히 , 노가다로 하면 되긴 하는데 , 그게 너무 싫다.

 */
public class Main {
    public static int w , h , spaceNumber = 0;
    public static BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int[][] map;
    public static Space[][] spaceMap;
    public static boolean[][] visited;
    public static int toInt(String number){
        return Integer.parseInt(number);
    }
    public static class Space{
        int value;
        int index;
        public Space(int value , int index){
            this.value = value;
            this.index = index;
        }
    }
    public static class Point{
        int y;
        int x;
        public Point(int y , int x){
            this.y = y;
            this.x = x;
        }
    }
    public static void print(int y , int x) throws IOException{
        /*
        main 에서 해당 좌표를 주면
        해당 좌표의 값을 판명해서 , output.write 할 것임
        근데 여기서 중요한 것은 , 주변에 있는 것들을 더할 때 , space 가 같은 것이 있는지 보는 것이다.
        일단 그냥 더해보자 , 한번 실험으로 깔끔하게 할 수 있다.
        본인이 방문한 곳의 space number를 모으고 , 계속 확인하는 것이다.
        짜피 4개 밖에 안되니까 , 이미 들어온 spaceNumber면 ? 들어오지 않게 하면 됨
         */
        int result = 1; // 본인자리 포함
        List<Integer> spaceNumberList = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            boolean add = true;
            int ny = y + dy[i];
            int nx = x + dx[i];
            if(ny < 0 || ny >= h || nx < 0 || nx >= w || map[ny][nx] == 1) continue;
            for(Integer space : spaceNumberList){ // spaceNumberList로 지금까지 들어온 영역의 번호를 저장하고 , 현재 내가 내 공간에 추가하려는 영역이
                // 이미 들어온 영역과 겹치는 게 있나 확인
                if(spaceMap[ny][nx].index == space){
                    add = false;
                    break;
                }
            }
            if(add) {
                result += spaceMap[ny][nx].value;
                spaceNumberList.add(spaceMap[ny][nx].index);
            }

        }

        output.write(result % 10 +"");
    }
    public static void bfs(int y , int x){
        /*
        넘어오는 좌표로 방문처리하면서 , 지금 현재에서 돌 수 있는 0들을 쫙 돈다음
        다 돌면 , 총 영역의 개수들을 space 클래스로 채워넣는데 index 에다가는 spaceNumber 를 집어넣어야 한다.
         */
        spaceNumber++;
        int value = 0;
        Queue<Point> queue = new LinkedList<>();
        Queue<Point> fill = new LinkedList<>();
        visited[y][x] = true;
        queue.add(new Point(y , x));

        while(!queue.isEmpty()){
            value++;
            Point point = queue.poll();
            fill.add(new Point(point.y , point.x));
            for(int i = 0; i < 4; i++){
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                if(ny < 0 || ny >= h || nx < 0 || nx >= w || visited[ny][nx] || map[ny][nx] == 1) continue;
                queue.add(new Point(ny , nx));
                visited[ny][nx] = true;
            }
        }

        while(!fill.isEmpty()){
            Point point = fill.poll();
            spaceMap[point.y][point.x] = new Space(value , spaceNumber);
        }

    }
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        h = toInt(st.nextToken());
        w = toInt(st.nextToken());

        map = new int[h][w];
        spaceMap = new Space[h][w];
        visited = new boolean[h][w];

        for(int i = 0; i < h; i++){
            String string = input.readLine();
            for(int j = 0; j < w; j++){
                map[i][j] = string.charAt(j) - '0';
            }
        }

//        for(int i = 0; i < h; i++) System.out.println(Arrays.toString(map[i]));

        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                if(map[i][j] == 0 && !visited[i][j]) bfs(i , j);
            }
        }

        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                if(map[i][j] == 0) output.write("0");
                else print(i , j);
            }
            output.write("\n");
        }

        output.flush();
        output.close();
    }
}