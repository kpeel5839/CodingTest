import java.awt.*;
import java.util.*;
import java.io.*;
import java.util.List;

public class Main{
    public static int h , w;
    public static int[][] instructList;
    public static int[][] map;
    public static List<Integer> resultList = new ArrayList<>();
    public static int[] dx = {0 , 0 , 1 , -1} , dy = {1 , -1 , 0 , 0};
    public static class Point{
        int y;
        int x;
        int value;
        public Point(int y, int x, int value){
            this.y = y;
            this.x = x;
            this.value = value;
        }
    }
    public static void swipe(int index){
        /*
        instructList에서 명령 뽑아내고,
        해당 영역의 숫자들을 swipe 하는 형식으로 해야할 듯
        queue 에다가 담고 , range 안의 숫자들을 다 0으로 초기화 해준다.
        그 다음에 , queue 에 있는 것들을 dir 에 맞게 옮겨 주면서 , 범위를 벗어나는 것들을
        다시 담아준다 , 이때 중요한 점은 queue 의 이전 사이즈만큼만 반복
        그 다음에 이전에 실행했던 반복문 대로 , 0을 찾으면 바로 queue에 집어넣어져 있던 것을 poll 해서
        집어넣으면 된다.
        그리고서 resultList 에 이 값을 더해서 resultList.add(result) 해주면 된다.
            */
        int dir = instructList[index][0] - 1;
        int r1 = instructList[index][1];
        int c1 = instructList[index][2];
        int r2 = instructList[index][3];
        int c2 = instructList[index][4];

        Queue<Point> queue = new LinkedList<>();

        for(int i = r1; i <= r2; i++){
            for(int j = c1; j <= c2; j++){
                queue.add(new Point(i , j , map[i][j]));
                map[i][j] = 0;
            }
        }

        int size = queue.size();
        int result = 0;

        for(int i = 0; i < size; i++){
            Point point = queue.poll();
            int ny = point.y + dy[dir];
            int nx = point.x + dx[dir];

            if(r1 > ny || ny > r2 || c1 > nx || nx > c2) {
                queue.add(new Point(point.y , point.x , point.value));
                result += point.value;
                continue;
            }

            map[ny][nx] = point.value;
        }

//        for(int i = 1; i <= h; i++){
//            for(int j = 1; j <= w; j++){
//                System.out.print(map[i][j] + " ");
//            }
//            System.out.println();
//        }

        for(int i = r1; i <= r2; i++){
            for(int j = c1; j <= c2; j++){
                if(map[i][j] == 0){
                    Point point = queue.poll();
                    map[i][j] = point.value;
                }
            }
        }

//        for(int i = 1; i <= h; i++){
//            for(int j = 1; j <= w; j++){
//                System.out.print(map[i][j] + " ");
//            }
//            System.out.println();
//        }

        resultList.add(result);
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());

        map = new int[h + 1][w + 1];
        int index = 1;

        for(int i = 1; i <= h; i++){
            for(int j = 1; j <= w; j++){
                map[i][j] = index++;
            }
        } // 초기 조건 완료

        instructList = new int[4][5]; // 무조건 4개만 주어진다는 가정하

        for(int i = 0; i < 4; i++){
            st = new StringTokenizer(input.readLine());
            instructList[i][0] = Integer.parseInt(st.nextToken());
            instructList[i][1] = Integer.parseInt(st.nextToken());
            instructList[i][2] = Integer.parseInt(st.nextToken());
            instructList[i][3] = Integer.parseInt(st.nextToken());
            instructList[i][4] = Integer.parseInt(st.nextToken());
        }

        for(int i = 0; i < 4; i++){
            swipe(i);
        }

        System.out.println(resultList);
    }
}