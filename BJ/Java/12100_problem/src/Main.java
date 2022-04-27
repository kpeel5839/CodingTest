import java.util.*;
import java.io.*;

public class Main {

    public static int[][] map;
    public static int[][] tempMap;
    public static int[] rollX = {0 , 1 , 0 , -1};
    public static int[] rollY = {-1 , 0 , 1 ,0};
    public static int n;
    public static int max = 0;
    public static boolean[][] visited;
    public static List<String> stringList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(input.readLine());

        map = new int[n][n];
        tempMap = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(input.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                tempMap[i][j] = map[i][j];
            }
        }
        visited = new boolean[n][n];
        sequence("", 0);
        for (String string : stringList) {
            bfs(string);
        }
        System.out.println(max);
    }

    public static void instructMove(int direction){
        for(int i = 0; i < n; i++){
            for (int j =0; j< n; j++){
                visited[i][j] = false;
            }
        }
        if(direction == 0){ //위 (위에서 부터 시작)
            for(int i = 0; i < n; i++){
                for(int j =0; j < n; j++){
                    move(i , j, 0);
                }
            }
        }
        if(direction == 1){ //오른쪽 (오른쪽 부터 시작 , n - 1)
            for(int i = n - 1; i != -1; i--){
                for (int j = 0; j < n; j++){
                    move(j, i ,1);
                }
            }
        }
        if(direction == 2){ //아래 (아래부터 시작)
            for(int i = n - 1; i != -1; i--){
                for (int j = 0; j < n; j++){
                    move(i, j ,2);
                }
            }
        }
        if(direction == 3){ //왼쪽 (왼쪽부터 시작)
            for(int i = 0; i < n; i++){
                for (int j = 0; j < n; j++){
                    move(j, i ,3);
                }
            }
        }
    }

    public static void move(int r, int c , int direction){
        int end = 0;
        int nowNum = map[r][c];
        map[r][c] = 0;
        int curR = r;
        int curC = c;
        if (direction == 1 || direction == 2){ end = n;} else{ end = -1;}
        while(r != end && c != end && map[r][c] == 0){
            r += rollY[direction];
            c += rollX[direction];
        }
        if (r == curR && c == curC){
            map[r][c] = nowNum;
        }
        else if (r != end && c != end && map[r][c] == nowNum && !visited[r][c]){
            map[r][c] += nowNum;
            visited[r][c] = true;
        }
        else{
            map[r - rollY[direction]][c - rollX[direction]] = nowNum;
        }
    }

    public static int findMax(int[][] map){
        for(int i =0; i < n; i++){
            for(int j =0; j < n; j++){
                max = Math.max(max , map[i][j]);
            }
        }
        return max;
    }
    public static boolean inArray(String string){
        for(String compare : stringList){
            if (compare.equals(string)){
                return false;
            }
        }
        return true;
    }
    public static void sequence(String string , int count){
        if(count == 5){
            if(inArray(string)){
                stringList.add(string);
            }
            return;
        }
        for(int i = 0; i < 4; i++){
            string += i;
            sequence(string,count + 1);
            string = string.substring(0 , string.length() - 1);
        }
    }

    public static void bfs(String string){
        for (int i =0; i < string.length(); i++){
            instructMove(Integer.parseInt(Character.toString(string.charAt(i))));
        }
        findMax(map);
        for(int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                map[i][j] = tempMap[i][j];
            }
        }
    }
}
