import java.util.*;
import java.io.*;

public class Main {
    public static int[][] map , line , resultLine;
    public static int[] cons = new int[5] , result = new int[5];
    public static int n , ans = Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(input.readLine());
        map = new int[n][n];
        line = new int[n][n]; //경계선은 1로 표시하자.
        resultLine = new int[n][n];
        //항상 하나를 시도한 뒤에 경계선은 지워야함

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < n; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        //모든 y , x 지점을 돌면서 , d1 , d2를 길이를 하나하나 다 시도해본다.
        //그럼 이제 경계를 표시하는 것이 중요한데
        //1 , 2 , 3 , 4 선거구를 잘 하는 것이 중요할 듯
        //애초에 선거구를 나눈 다음에 어떠한 표시를 한 뒤 다른 맵을 만들어서
        for(int d1 = 1; d1 < n - 1; d1++){
            for(int d2 = 1; d2 < n - 1; d2++){
                if(d1 + d2 > n - 1){
                    continue;
                }
                for(int i = 0; i < n; i++){
                    for(int j = 0; j < n; j++){
                        if(i + d1 + d2 >= n){
                            continue;
                        }
                        if(j - d1 < 0 || j + d2 >= n){
                            continue;
                        }
                        calculateCons(i , j , d1 , d2);
                    }
                }
            }
        }
        System.out.println(ans);
    }
    public static void calculateCons(int y , int x , int d1 , int d2){
        clear();
        cons[4] = map[y][x];
        int v = 0;
        boolean start = false;
        for(int i = 0; i < y + d1; i++){ //1번 선거구
            if(start){
                v++;
            }
            if(i == y){
                v = 1;
                start = true;
            }
            for(int j = 0; j <= x - v; j++){
                cons[0] += map[i][j];
            }
        }
        v = 0;
        start = false;
        for(int i = 0; i <= y + d2; i++){ //2번 선거구
            if(start){
                v++;
            }
            if(i == y + 1){
                v = 1;
                start = true;
            }
            for(int j = x + 1 + v; j < n; j++){
                cons[1] += map[i][j];
            }
        }
        for(int i = y + d1; i < n; i++){ //3번 선거구
            if(v != 0){
                v--;
            }
            if(i == y + d1){
                v = d2;
            }
            for(int j = 0; j < x - d1 + d2 - v; j++){
                cons[2] += map[i][j];
            }
        }
        for(int i = y + d2 + 1; i < n; i++){ //4번 선거구
            if(v != 0){
                v--;
            }
            if(i == y + d2 + 1){
                v = d1;
            }
            for(int j = x - d1 + d2 + v; j < n; j++){
                cons[3] += map[i][j];
            }
        }
        int index = 1;
        for(int i = y + 1; i <= y + d1 + d2; i++){ //5번 선거구
            if(index <= d1 && index <= d2) {
                for(int j = x - index; j <= x + index; j++){
                    cons[4] += map[i][j];
                }
            }
            else if(index > d1 && index > d2){
                for(int j = x - d1 + (index - d1); j <= x + d2 - (index - d2); j++){
                    cons[4] += map[i][j];
                }
            }
            else if(index > d1){ //d1만 커진 경우
                for(int j = x - d1 + (index - d1); j <= x + index; j++){
                    cons[4] += map[i][j];
                }
            }
            else{ //d2만 커진 경우
                for(int j = x - index; j <= x + d2 - (index - d2); j++){
                    cons[4] += map[i][j];
                }
            }
            index++;
        } //이거 한 다음에 구하는 게 가장 이상적인 듯 여기서 decideMin까지 호출 하자
        decideMin();
    }
    public static void clear(){
        for(int i = 0; i < 5; i++){
            cons[i] = 0;
        }
    }
    public static void decideMin(){
        int min = Integer.MAX_VALUE;
        int max = 0;
        for(int i = 0; i < 5; i++){
            if(cons[i] > max){
                max = cons[i];
            }
            if(cons[i] < min){
                min = cons[i];
            }
        }

        if(ans > (max - min)){
            ans = max - min;
        }
    }
}
