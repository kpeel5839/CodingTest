import java.util.*;
import java.io.*;

// 23291 : 어항 정리
/*
-- 전제조건
1. 어항을 정리한다 , 처음에는 한줄로 각자의 물고기 개수가 있다.
2. 어항을 정리하는 과정은 물고기의 수가 가장 적은 어항에 물고기를 한마리 넣는다.
3. 만약 그러한 어항이 여러개라면 물고기의 수가 최소인 어항 모두에 한 마리씩 넣는다.
4. 이제 어항을 쌓는데 제일 처음에는 가장 왼쪽에 있는 어항을 그 어항의 오른쪽에 쌓는다.
5. 그리고 이제부터 2개 이상의 어항이 쌓여져 있는 어항을 모두 공중 부양 시킨 다음에 전체를 90도 방향으로 회전 시켜서 얹어놓음
6. 그거를 계속 하다가 이제 위에 쌓는게 아래의 어항을 넘어가게 되면 그만한다 , (안하고 그만 두는 거임)
7. 그 다음에 이제 각자 물고기의 수를 조절한다 , 이건 온풍기 안녕처럼 하면 될 듯 (나누기 5를 한다음 몫을 추가 , 그러니까 그냥 정수 나눗셈 하면 됨)
8. 그 다음에 어항을 일직선으로 다시 깔아놓는데 이 때는 가장 왼쪽의 어항부터 차례대로 위로 올라가면서 하면 된다.
9. 그 다음에는 이제 2 / n개로 나눠서 공중 부양시켜서 왼쪽 어항을 그 다음에 180도 회전한다음 다시 얹는다.
10. 그걸 한번 더한다.
11. 그 다음에 인접한 것을 물고기를 나누고 , 일렬로 세워놓는다.
12. 이것을 가장 물고기가 많은 어항과 적은 어항중
-- 틀 설계
1. 입력을 받아서 , 일단 n , n 크기의 배열을 만든다.
2. check() 가장 큰 값과 작은 값의 차이가 k 이하가 되면 true를 반환해서 while 문에서 빠져나올 수 있도록한다.
3. 그리고 처음 동작은 그냥 while 문의 첫번째 문장에서 행하면 될 것 같고
4. firstArrange() , secondArrange() 함수를 만든다.
5. 그리고 spread 함수도 만든다.(근접한 어항의 물고기가 조절되도록)
6. 그리고 다시 일렬로 피는 unroll 함수를 선언한다.
-- 해맸던 점
1. 처음에 그냥 수학적으로 해결해보려다가 시간 개날림
2. 그리고 다시 짜서 괜찮긴 했지만
3. firstArrange에서 괜히 startX , startY 이런 거 생각해서 해맸음
4. 2개 이상 쌓여있는 것이 끝나는 지점인 x 값을 찾을 때 끝까지 쌓여있는 것을 고려 안해서 무한 루프때문에 고생함
5. 그냥 2개 이상 쌓여있는 것들 높이 height 변수로 증위연산자 사용해서 재고
6. 그리고 2개 이상 쌓여있는 거 어디에서 끝나는 지 x로 찾고
7. 그 다음에 x 부터 읽어서 그냥 n - 2부터 차곡차곡 올리면 됐었고
8. 그리고 x + y >= n 이렇게 했으면 됐는데 그냥 break; 문을 괜히 x + 1 + y > n 이렇게 해서 맞는 건가 아닌 건가 헷갈리고
9. 그리고 spread에서 0인 애들이랑도 물고기 교환하고 , 이러한 실수들이 있었음
10. 그리고 자꾸 하는 실수인데 반복문에다가 i , j 헷갈려서 잘 못해놓는 경우 때문에 애먹는 경우가 많음
11. 실수가 엄청 많은 문제였는데 그래도 진짜 막막했는데 다시 생각하고 다시 처음부터 짜서 괜찮았음
12. arrange만 빼면 설계 자체는 그대로 진행하였음
 */
public class Main {
    public static int n , k , ans = 0;
    public static int[][] map;
    public static boolean check(){
        /*
        1. 최소값과 최대값의 차이가 k 이하가 되었나 확인하는 함수
         */
        int max = Arrays.stream(map[n - 1]).max().getAsInt();
        int min = Arrays.stream(map[n - 1]).min().getAsInt();
        if(max - min <= k) return true;
        return false;
    }
    public static void unroll(){
        /*
        1. 다시 일렬로 정리하는 함수
         */
        int x = 0;
        for(int i = 0; i < n; i++){
            for(int j = n - 1; j != -1; j--){
                if(map[j][i] != 0){
                    int value = map[j][i];
                    map[j][i] = 0;
                    map[n - 1][x++] = value;
//                    mapPrint();
                }
            }
        }
    }
    public static void spread(){
        /*
        1. 온도를 퍼트리는 함수
        2. 그냥 for문 돌면서 나눈 값으로 주입해주면 됨
         */
        int[][] deepMap = new int[n][n];
        for(int i = 0; i < n; i++){
            System.arraycopy(map[i] , 0 , deepMap[i], 0 , map[i].length);
        }
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(i != 0){
                    if(Math.min(deepMap[i - 1][j] , deepMap[i][j]) != 0) {
                        int diff = Math.abs(deepMap[i - 1][j] - deepMap[i][j]) / 5;
                        int high = Math.max(deepMap[i - 1][j], deepMap[i][j]);
                        if (high == deepMap[i - 1][j]) {
                            map[i - 1][j] -= diff;
                            map[i][j] += diff;
                        } else {
                            map[i - 1][j] += diff;
                            map[i][j] -= diff;
                        }
                    }
                }
                if(j != 0){
                    if(Math.min(deepMap[i][j - 1] , deepMap[i][j]) != 0) {
                        int diff = Math.abs(deepMap[i][j - 1] - deepMap[i][j]) / 5;
                        int high = Math.max(deepMap[i][j - 1], deepMap[i][j]);
                        if (high == deepMap[i][j - 1]) {
                            map[i][j - 1] -= diff;
                            map[i][j] += diff;
                        } else {
                            map[i][j - 1] += diff;
                            map[i][j] -= diff;
                        }
                    }
                }
            }
        }
    }
    public static void firstArrange(){
        /*
        1. 첫번째 어항 정리
        2. 조금 기발한 생각을 해보자
        3. 일단 첫째 n - 2행에서 끝을 찾는다 (2개 이상의 어항 탑의 끝)
        4. 그리고 첫번째 n - 1 행에서 하나씩 플러스 해가면서 0이 아닌 것을 찾다가 찾으면
        5. 바로 끝까지 올라가서 높이를 측정한다
        6. 그러면 0이 아닌 것을 찾은 x에서 시작하고 끝까지 올라가면서 array에 담은다음
        7. 아까 찾은 높이 끝에서부터 하면 된다.
        8. 그럼 이제 끝내는 조건이 애매해질 수 있는데 이것은 계속 해서 끝을 찾은 것에서 + 1 한다음에
        9. 현재 2개 이상인 것의 높이를 + 했을 때 < n 이면 계속하는 거다 , 그러니까 반대로 >= n 이면 그만하는 것
         */
        int y = 0;
        int x = 0;
        map[n - 2][1] = map[n - 1][0];
        map[n - 1][0] = 0; //시작
        while(true){
            boolean find = false;
            for(int i = 0; i < n; i++){
                if(!find && map[n - 2][i] == 0){
                    continue;
                }else if(!find && map[n - 2][i] != 0){
                    find = true;
                }
                else if(find && map[n - 2][i] == 0 || i == n - 1){
                    x = i - 1;
                    break;
                }
            }
            Loop:
            for(int i = 0; i < n; i++){
                if(map[n - 1][i] == 0){
                    continue;
                }
                int height = 0;
                for(int j = n - 1; j != -1; j--){
                    if(map[j][i] == 0){
                        y = height; //이것은 이제 이게 나가나 안나가나 하는 것
                        break Loop;
                    }
                    height++;
                }
            }
            if(x + 1 + y > n){
                break;
            }
            int[] arr = new int[y];
            int index;
            int start = n - 2;
            for(int i = x; i != -1; i--){
                index = 0;
                if(map[n - 1][i] == 0){ //x 가 끝나면 끝
                    break;
                }
                for(int j = n - 1; j != -1; j--){
                    if(map[j][i] == 0){
                        break;
                    }
                    arr[index++] = map[j][i];
                    map[j][i] = 0;
                }
                index = 0;
                for(int j = x + 1; j < x + 1 + y; j++){
                    map[start][j] = arr[index++];
                }
                start--;
            }
        }
//        mapPrint();
        spread();
//        mapPrint();
        unroll();
//        mapPrint();
    }
    public static void secondArrange(){
        /*
        1. 두번째 어항 정리
        2. 이거는 일단 firstStart하면서 첫번째는 길이만큼 array를 선언한다음에 그냥 거꾸로 뒤집어서 2번째에 하면 됨
        3. 그 다음에는 무조건 2줄이니까 2줄에 size 반만큼 해서 다시 array 선언해서 그거 180도 뒤집어서 위에다가 올리면 됨
        4. 그리고 2번째는 그냥 secondStart에서부터 읽으면 됨 , secondStart 부터 읽어서 순서대로 집어넣은다음
        5. n - 1행부터 읽어서 그 다음에는 두번쨰니까 n - 4부터해서 n - 3까지 그냥 배열 순서대로 집어넣으면 됨
        6. 순서는 secondStart + 1 부터 < n 까지
         */
        int firstStart = n / 2 - 1;
        int secondStart = (n - 1 + firstStart) / 2;
        int size = n / 2;

        int[] firstArr = new int[size];
        int index = 0;
        for(int i = firstStart; i != -1; i--){
            firstArr[index++] = map[n - 1][i];
            map[n - 1][i] = 0;
        }
        index = 0;
        for(int i = firstStart + 1; i < n; i++){
            map[n - 2][i] = firstArr[index++];
        }
//        mapPrint();
        int[][] secondArr = new int[2][size / 2];
        int x = 0 , y = 0;
        for(int i = n - 1; i != -1; i--){
            if(map[i][secondStart] == 0){
                break;
            }
            for(int j = secondStart; j != firstStart; j--){
                secondArr[y][x++] = map[i][j];
                map[i][j] = 0;
            }
            y++;
            x = 0;
        }
        y = 0;
        x = 0;
//        for(int i = 0; i < 2; i++){
//            System.out.println(Arrays.toString(secondArr[i]));
//        }
//        System.out.println(secondStart);
        for(int i = n - 4; i < n -2; i++){
            for(int j = secondStart + 1; j < n; j++){
//                System.out.println(x);
//                System.out.println(j);
                map[i][j] = secondArr[y][x++];
            }
            y++;
            x = 0;
        }
//        mapPrint();
        spread();
//        mapPrint();
        unroll();
//        mapPrint();
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        map = new int[n][n];
        st = new StringTokenizer(input.readLine());

        for(int i = 0; i < n; i++){
            map[n - 1][i] = Integer.parseInt(st.nextToken());
        }

        while(true){
            int min = Arrays.stream(map[n - 1]).min().getAsInt();
            for(int i = 0; i < n; i++){
                if(min == map[n - 1][i]){
                    map[n - 1][i]++;
                }
            }
            ans++;
            firstArrange();
            secondArrange();
//            mapPrint();
            if(check()){
                break;
            }
        }
        System.out.println(ans);
    }
    public static void mapPrint(){
        System.out.println("---------mapPrint---------");
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
