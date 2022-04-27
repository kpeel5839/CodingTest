import java.util.*;
import java.io.*;

// 2239 : 스도쿠
/*
-- 전제조건
1. 그냥 스도쿠 규칙으로 푼다.
2. 입력이 주어지면 해당 스도쿠 규칙으로 풀면 된다.
-- 틀 설계
1. 입력을 받는다.(배열로 문자 하나한 때가지고)
2. zero들의 위치를 point배열로 정리해놓는다.
3. 그리고 depth 와 zeroCount가 같아질 때까지 돌린다.
4. dfs를 돌리면서 이게 맞는지 확인해야함 계속
5. 그래서 결국 dfs , check 이 두개의 함수가 필요하다
-- 해맸던 점
1. 그냥 i != x && map[i][x] 이 부분에서 실수해가지고
2. i != y && map[i][x] 이렇게 해야하는데 왜냐하면 이러면 행을 순회하는 것이니까 내가 속한 행만 검사안하면 되는 거라서
3. 이것때문에 살짝 해맨거 말고는 굉장히 빨리 풀음
 */
public class Main {
    public static int n = 9 , zeroCount;
    public static boolean sorted = false;
    public static int[][] map = new int[n][n];
    public static Point[] zero;
    public static void dfs(int depth){
        /*
        1. 일단 depth를 가지고 처음에는 0으로 받는다
        2. 만일 depth 가 zero.length와 같아지면 그냥 출력하고 sorted 를 true 로 바꿔서 모든 재귀 함수들을 멈추게끔한다.
        3. 만일 depth 가 zero.length 와 같지 않다면 그냥 이제 스도쿠를 맞추기를 시작한다.
        4. 해당 depth의 point에 들어갈 숫자를 찾을 것인데 그것은 int배열를 이용할 것이다.
        5. list에다가 1부터 순서대로 9까지 집어넣고 나오는 숫자마다 해당 index 를 0으로 만들어준다.
        6. 해당 배열을 받으면 해당 배열을 0을 제외하고 자신의 배열로 만들고 해당 배열을 순회하면서
        7. 순서대로 넣어준다.
        8. 만일 넣을 숫자가 없어서 배열의 아무것도 받지 못하면은 그 해당 함수는 끝나게 되고
        9. 그러면 이전으로 돌아올 방법이 필요하니 항상 dfs를 호출한 뒤 아래에다가 다시 원래의 값인 0로 돌려놓는 작업을 한다.
         */
        if(sorted){
            return;
        }
        if(depth == zero.length){
            sorted = true;
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    System.out.print(map[i][j]);
                }
                System.out.println();
            }
            return;
        }else{
            int y = zero[depth].y;
            int x = zero[depth].x;
            int[] number = solveNumber(y , x).clone();
//            if(y == 0 && x == 7){
//                System.out.println(Arrays.toString(number));
//            }
            for(int i = 0;i < number.length; i++){
                if(number[i] == 0){
                    continue;
                }
                map[y][x] = number[i];
                dfs(depth + 1);
                map[y][x] = 0;
            }
        }
    }
    public static int[] solveNumber(int y, int x){
        /*
        1. 여기서는 해당 y, x를 받으면 해당 지점에서의 열 , 행을 검사한다.
        2. 그리고 자신의 3 * 3 칸도 검사한다.
        3. 검사할 때 나오는 숫자는 인덱스 값으로 제외시킴
        4. 결과적으로 나오는 숫자 배열 반환
         */
        int[] solveNumber = {0 , 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8, 9}; //일부로 그냥 인덱스 쉽게 하려고 이렇게함
        for(int i = 0; i < 9; i++){
            if(i != y && map[i][x] != 0){
                solveNumber[map[i][x]] = 0;
            }
            if(i != x && map[y][i] != 0){
//                if(y == 0 && x == 7){
//                    System.out.print("i : " + i + " ");
//                    System.out.print(map[y][i] + " ");
//                }
                solveNumber[map[y][i]] = 0;
            }
        }
        int startY = (y / 3) * 3;
        int startX = (x / 3) * 3;
        for(int i = startY; i < startY + 3; i++){
            for(int j = startX; j < startX + 3; j++){
                if(!(i == y && j == x) && map[i][j] != 0){
                    solveNumber[map[i][j]] = 0;
                }
            }
        }
        return solveNumber;
    }
    public static class Point{
        int y;
        int x;
        public Point(int y, int x){
            this.y = y;
            this.x = x;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        for(int i = 0; i < n; i++){
            String string = input.readLine();
            for(int j = 0; j < n; j++){
                map[i][j] = Integer.parseInt(Character.toString(string.charAt(j)));
                if(string.charAt(j) == '0'){
                    zeroCount++;
                }
            }
        }

        zero = new Point[zeroCount];
        int index = 0;

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(map[i][j] == 0){
                    zero[index++] = new Point(i , j);
                }
            }
        }

        dfs(0);
    }
}
