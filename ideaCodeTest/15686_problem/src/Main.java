import java.util.*;
import java.io.*;

public class Main {
    public static int minValue = 1000000;
    public static int n , m , min = minValue , houseCount = 0 , chickenCount = 0; //최소 치킨 값을 저장할 것
    public static HashSet<String> sequence= new HashSet<>();
    public static int[][] map;
    public static Point[] house;
    public static Point[] chicken;
    public static List<Point> open = new ArrayList<>();

    public static void main(String[] args) throws IOException{ //치킨집은 따로 관리 , 그리고 집도 관리 ... 하는 방식으로 가고
        //집하나씩 돌아가면서 치킨집에 대한 거리를 다 구해야 한다, 그렇다는 건 , sequence가 지정하는 치킨집대로 , point가 있어야한다는 것
        //그러면 일단은 house chicken 다 구한 뒤에 string 에서 1인 것만 list에다가 집어넣는다 그러면서 돌아가면서 최소값을 구하는 것 한 집 한 집 최소값을 다 더해서 그 더 한 값에서 최소값을 찾으면 될 듯
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(input.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][n];

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < n; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] == 1){houseCount++;}
                if(map[i][j] == 2){chickenCount++;}
            }
        }

        house = new Point[houseCount];
        chicken = new Point[chickenCount];

        houseCount = 0;
        chickenCount = 0;

        for(int i =0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(map[i][j] == 1){
                    house[houseCount++] = new Point(i , j);
                }
                if(map[i][j] == 2){
                    chicken[chickenCount++] = new Point(i , j);
                }
            }
        }

        makeSequence("", 0); //시퀀스 만듦

        //시퀀스 만들었으면 그 시퀀스 대로 해당 list에다가 집어넣고 거기서 이제 최소거리를 구해야 함,
        for(String string : sequence){ //일단 시퀀스대로 진행
            for(int i = 0; i < string.length(); i++){
                if(string.charAt(i) == '1'){ //오픈매장에다가 시퀀스 대로 오픈한 치킨 집을 집어넣음
                    open.add(chicken[i]); //오픈한 치킨 집 집어넣었음
                }
            }
            distance(); //그리고 해당 open 한 치킨집 거리들 최소 거리로 구해서 min 구하고
            open = new ArrayList<>(); //그러면서 거리 구한다음 다음 시퀀스로 이동할 준비를 한다.
        }
        System.out.println(min);
    }

    public static void distance(){
        int sum = 0; //이제 여기서 open 한 집과의 거리를 house리스트에서 다 구해본다음에 , 거기서 다 더해서 min을 구해야 함
        for(int i = 0; i < house.length; i++){ //집을 다 돌면서 모든 치킨집과 비교해서 그 중 최소 값을 sum에다가 더해야함
            int innerMin = minValue; //한 집과 치킨집의 최소거리를 구함
            for(Point point : open){
                int value = Math.abs(point.x - house[i].x) + Math.abs(point.y - house[i].y);
                if(innerMin > value){
                    innerMin = value;
                }
            }//모든 치킨집을 돌아주면서 구할 것임 최소값을 구했음
            sum += innerMin;
        }
        if(min > sum){
            min = sum;
        }
    }
    public static void makeSequence(String string , int count){ //sequence를 만들었음
        if(string.length() > m){
            if(countLetter(string) > m){
                return;
            }
        }

        if(count == chicken.length){ //여기서 잘못했었음
            sequence.add(string);
            return;
        }

        for(int i = 0; i < 2; i++){
            string += i;
            makeSequence(string , count + 1);
            string = string.substring(0 , string.length() - 1);
        }
    }

    public static int countLetter(String string){
        int letterCount = 0;
        for(int i = 0; i < string.length(); i++){
            if(string.charAt(i) == '1'){
                letterCount++;
            }
        }
        return letterCount;
    }

    public static class Point{
        int y;
        int x;

        public Point(int y,  int x){
            this.y = y;
            this.x = x;
        }
    }
}
