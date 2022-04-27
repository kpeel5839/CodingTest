import java.util.*;
import java.io.*;

public class Main {
    public static int n , low , high , day = 0 , sum = 0;
    public static int[][] map , visited , copy; //visited 1이면 이미 방문한 곳인 것
    public static List<Point> popMove = new ArrayList<>();
    public static void main(String[] args)throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine());
        n = Integer.parseInt(st.nextToken());
        low = Integer.parseInt(st.nextToken());
        high = Integer.parseInt(st.nextToken());

        map = new int[n][n];
        visited = new int[n][n];
        copy = new int[n][n];

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < n; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                copy[i][j] = map[i][j];
            }
        }

        boolean end = false;
        while(!end){
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    if(visited[i][j] != 1){
                        bfs(i , j); //여기서 구한 거를 다 더해서 바꾸는 것을 해야함
                        change();
                        sum = 0;
                        popMove = new ArrayList<>();
                    }
                }
            }

            end = check();
            setCopy();
        }
        System.out.println(day);
    }
    public static boolean check(){//인구 이동이 한번도 일어나지 않았는지 검사한다. copy map으로
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(copy[i][j] != map[i][j]){
                    day++;
                    return false; //하나라도 다르면 다음 인구 이동도 해야함
                }
            }
        }
        return true; //하나도 다르지 않으면은 끝
    }

    public static void change(){
        sum = Math.round(sum / popMove.size());
        for(Point point : popMove){
            map[point.y][point.x] = sum;
        }
    }


    public static void setCopy(){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                copy[i][j] = map[i][j];
                visited[i][j] = 0;
            }
        }
    }
    public static void bfs(int y , int x){ //이제 여기다가 다 넘겨서 맞춰진 것들은 sum으로 등록하고 합치는 리스트에다가 넣는다.
        //자이제 여기에서는 위아래 빠져나가지만 않게 조심하고 그냥 다 더하면 됨
        visited[y][x] = 1;
        sum += map[y][x];
        popMove.add(new Point(y , x));
        if(y - 1 != -1 && visited[y - 1][x] != 1 && low <= Math.abs(map[y -1][x] - map[y][x]) && high >= Math.abs(map[y -1][x] - map[y][x])){ //위
            bfs(y - 1, x);
        }
        if(x + 1 != n && visited[y][x + 1] != 1 && low <= Math.abs(map[y][x + 1] - map[y][x]) && high >= Math.abs(map[y][x + 1] - map[y][x])){ //오른쪽
            bfs(y, x + 1);
        }
        if(y + 1 != n && visited[y + 1][x] != 1 && low <= Math.abs(map[y + 1][x] - map[y][x]) && high >= Math.abs(map[y + 1][x] - map[y][x])){ //아래
            bfs(y + 1, x);
        }
        if(x -  1 != -1 && visited[y][x - 1] != 1 && low <= Math.abs(map[y][x - 1] - map[y][x]) && high >= Math.abs(map[y][x - 1] - map[y][x])){ //왼쪽
            bfs(y, x - 1);
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
}
