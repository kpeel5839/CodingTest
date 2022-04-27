import java.util.*;
import java.io.*;

public class Main {
    public static int n , m , minTime = Integer.MAX_VALUE ,virusCount = 0;
    public static int[][] map, tempMap , visited;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static Queue<Point> spread = new LinkedList<>();
    public static List<Point> virusList = new ArrayList<>();
    public static HashSet<String> sequence = new HashSet<>();
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
    public static void main(String[] args) throws IOException{ //-1은 바이러스 , -2는 벽으로 가자 , 0은 그대로 빈칸
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][n];
        tempMap = new int[n][n];
        visited = new int[n][n];

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < n; j++){
                int value = Integer.parseInt(st.nextToken());
                if(value == 1){
                    value = -2;
                }
                if(value == 2){
                    value = -1;
                    virusCount++;
                    virusList.add(new Point(i , j , 0));
                }
                map[i][j] = value;
                tempMap[i][j] = value;
            }
        }
        instruct();

        if(minTime == Integer.MAX_VALUE) System.out.println(-1);
        else System.out.println(minTime);
    }
    public static void instruct(){
        getSequence(""); //sequence 구함
        for(String s : sequence){
            for(int i = 0; i < s.length(); i++){
                if(s.charAt(i) == '1'){
                    spread.add(virusList.get(i));
                }
            }
            while(!spread.isEmpty()){
                Point point = spread.poll();
                visited[point.y][point.x] = 1;
                int value = point.value;
                for(int i = 0; i < 4; i++){
                   int nx = point.x + dx[i];
                   int ny = point.y + dy[i];
                   if(nx < 0 || nx >= n || ny < 0 || ny >= n){
                       continue;
                   }
                   if(visited[ny][nx] == 1 || map[ny][nx] == -2){
                       continue;
                   }
                   spread.add(new Point(ny , nx , value + 1));
                   map[ny][nx] = value + 1;
                   visited[ny][nx] = 1;
                }
            }
            int max = 0;
            for(Point point : virusList){
                map[point.y][point.x] = -1;
            }
            Loop1:
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    if(map[i][j] == 0){
                        break Loop1;
                    }
                    if(max < map[i][j]){
                        max = map[i][j];
                    }
                    if(i == n - 1 && j == n - 1){
                        if(minTime > max){
                            minTime = max;
                        }
                    }
                }
            }
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    if(map[i][j] == -1){
                        System.out.print("2 ");
                        continue;
                    }
                    if(map[i][j] == -2){
                        System.out.print("1 ");
                        continue;
                    }
                    System.out.print(map[i][j] + " ");
                }
                System.out.println();
            }
            clean();
        }
    }
    public static void clean(){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                map[i][j] = tempMap[i][j];
                visited[i][j] = 0;
            }
        }
    }
    public static void getSequence(String s){
        if(virusCount == s.length()){
            if(check('1' , s)) {
                sequence.add(s);
            }
            return;
        }
        for(int i = 0; i < 2; i++){
            s += i;
            getSequence(s);
            s = s.substring(0 , s.length() - 1);
        }
    }
    public static boolean check(char ch , String s){
        int count = 0;
        for(int i = 0; i < s.length(); i++){
            if(ch == s.charAt(i)){
                count++;
            }
        }
        if(count == m){
            return true;
        }
        else{
            return false;
        }
    }
}
