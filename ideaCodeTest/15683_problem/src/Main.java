import java.util.*;
import java.io.*;

public class Main {
    public static int[] rollX = {0 , 1 , 0 , -1};
    public static int[] rollY = {-1 , 0 , 1 , 0};
    public static char[][] useMap;
    public static char[][] originMap;
    public static int r, c;
    public static int cctvCount;
    public static Point[] cctvPoint;
    public static int min = 64;
    public static HashSet<String> stringList = new HashSet<String>();

    public static void main(String[] args)throws IOException{
        BufferedReader input =new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        useMap = new char[r][c];
        originMap = new char[r][c];

        for(int i = 0; i < r; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < c; j++){
                originMap[i][j] = st.nextToken().charAt(0);
                useMap[i][j] = originMap[i][j];
                if(originMap[i][j] != '0' && originMap[i][j] != '6'){
                    cctvCount++;
                }
            }
        }
        cctvPoint = new Point[cctvCount];
        int cctvIndex = 0;
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                if(originMap[i][j] != '0' && originMap[i][j] != '6'){
                    cctvPoint[cctvIndex] = new Point(i , j , Integer.parseInt(Character.toString(originMap[i][j])));
                    cctvIndex++;
                }
            }
        }
        instruct();
        System.out.println(min);
    }

    public static void cctv(int cctvNum , int dir , int y, int x){
        if(cctvNum == 1){
            watch(dir , y, x);
        }
        else if(cctvNum == 2){
            watch(dir, y, x);
            watch((dir + 2) % 4 , y , x);
        }
        else if(cctvNum == 3){
            watch(dir , y , x);
            watch(++dir > 3 ? 0 : dir , y , x);
        }
        else if(cctvNum == 4){
            watch(dir , y , x);
            dir = ++dir > 3 ? 0 : dir;
            watch(dir , y , x);
            dir = ++dir > 3 ? 0 : dir;
            watch(dir , y , x);
        }
        else if(cctvNum == 5){
            watch(0, y , x);
            watch(1, y , x);
            watch(2, y , x);
            watch(3, y , x);
        }
    }

    public static void watch(int dir , int y, int x){
        int endR = -1 , endC = -1;
        if(dir == 1 || dir == 2){endR = r; endC = c;}
        while((y + rollY[dir] != endR && x + rollX[dir] != endC) && useMap[y + rollY[dir]][x + rollX[dir]] != '6'){
            x += rollX[dir];
            y += rollY[dir];
            if(useMap[y][x] != '0'){
                continue;
            }
            useMap[y][x] = '#';
        }
    }
    public static void makeSequence(String string , int count){
        if(count == cctvCount){
            stringList.add(string);
            return;
        }
        for(int i = 0; i < 4; i++){
            string += i;
            makeSequence(string , count + 1);
            string = string.substring(0 , string.length() - 1);
        }
    }
    public static void instruct(){
        makeSequence("" , 0);
        for(String string : stringList){
            for(int i = 0; i < string.length(); i++){
                cctv(cctvPoint[i].num ,  Integer.parseInt(Character.toString(string.charAt(i))) , cctvPoint[i].y, cctvPoint[i].x);
            }
            int zeroCount = 0;
            for(int i = 0; i < r; i++){
                for(int j = 0; j < c; j++){
                    if(useMap[i][j] == '0')
                        zeroCount++;
                }
            }
            if(zeroCount < min){
                min = zeroCount;
            }
            for(int i = 0; i < r; i++){
                for(int j = 0; j < c; j++){
                    useMap[i][j] = originMap[i][j];
                }
            }
        }
    }
    public static class Point{
        int y;
        int x;
        int num;
        public Point(int y, int x , int num){
            this.y = y;
            this.x = x;
            this.num = num;
        }
    }
}

