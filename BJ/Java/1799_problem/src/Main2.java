import java.util.*;
import java.io.*;

// 1799 : 비숍

/**
 * -- 전제조건
 * 비숍을 놓을 수 없는 칸 놓을 수 있는 칸이 주어졌을 때 ,
 * 체스 규칙에 의거하여 , 최대의 말의 개수를 놓는 경우의 수를 구하여라
 * 최대 n은 10이고 일단 간단한 백트래킹을 이용해서 한번해보자.
 *
 * -- 틀 설계
 * 일단 내가 하던 방법은 살짝 버리고 조합쪽으로 접근해보자.
 * 선택하고 안하고가 아니라 , 그냥 순서대로 선택하는데 모든 조합을 진행한다고 생각하고 진행해보자.
 * 근데 신경써야 할 부분이 있다 수행시간을 획기적으로 줄이기 위해서 black , white 를 나눠서 진행해야 하낟.
 * 그래서 color boolean 배열을 만들어서 black == true , white == false 로 진행한다음 할 것이다.
 * 그리고 chess 를 이용해서 말을 놓은 곳은 true 로 만들것이다.
 * 그래서 check 함수를 통해서 해당 지점에 말을 놓을 수 있는 지 확인할 것이다.
 *
 * 조합론 적으로 봤을 때에 , 무조건 현재 말을 놓는 공간 아래에는 말이 존재하지 않는다
 * 그렇다라는 것은 위만 조사하면 된다.
 * 그래서 dx = {-1 , 1} , dy ={-1 , -1} 로 해놨다.
 *
 * 그래서 말을 놓을 때 , 해당 지점이 내가 지금 놓으려는 색이 맞는지 혹은 ,
 * 말을 놓을 수 있는 공간인지 , 그리고 해당 지점이 able 배열에 1로 등록이 되어있는지 등을 확인한다.
 * 해당 조건들이 다 맞으면 진행을 하면 된다.
 *
 * 생각보다 시간이 얼마 안걸려서 놀랐고,
 * 생각을 더 키워야 한다는 생각이 많이 들었다.
 *
 * 그리고 프로그래밍시에 조금 더더 생각하고 하는 습관이 중요하다고 다시 한번 느꼈다.
 */
public class Main2 {
    public static int N;
    public static int[][] able;
    public static boolean[][] chess , color;
    public static int[] res = new int[2] , dx = {-1 , 1} , dy ={-1 , -1};
    public static void dfs(int idx , int count , boolean black){
        /**
         * index 순으로 진행하고 , 해당 idx 는 이미 놓은 상태로 넘어 온 것이니까
         * index + 1 부터 시작하면 된다.
         * 그리고 , 놓고서 다시 돌아왔을 때에는 꼭 chess[i] = false 를 해주어야 한다.
         * 왜냐하면 순서대로 진행되긴 하지만 지워줘야 다른 결과에 피해를 안끼친다 , 왜냐하면 이게 끝나면
         * 다음 턴으로 진행이 되고 , 그러면 내가 원하는 결과가 나오지 않는다.
         *
         * 그래서 idx + 1 부터 시작하고 N * N 까지 진행한다.
         * 그리고서 y = i / N 으로 파싱하면 되고
         * x = i % N 으로 하면 된다.
         *
         * 그러고서 해당 지점에 놓을 수 있나 못 놓나를 진행하면
         * 결과가 나오게 되고 , 거기서 안걸러지면 놓는다.
         * 일단 놓는 게 좋다.
         * 짜피 모든 경우를 실행할 것이다 , 놓을 수 있다면
         */
        for(int i = idx + 1; i < N * N; i++){
            int y = i / N;
            int x = i % N;

            // black == true 이면 black 을 탐색하는 것이다 , 근데 color[y][x] 도 true 이면 black 이다.
            // 그래서 넘어가는 경우는 color[y][x] 가 black 과 일치하지 않을 때이다.
            // black == false 이면 white 를 찾아야 하는데 color[y][x] == false 가 white 이다. 근데 true 인 경우를 거르면 != black 이다
            // black 을 찾는 로직도 같다.
            if(chess[y][x] || check(y , x) || able[y][x] == 0 || color[y][x] != black) continue;

            /**
             * chess 말을 놓고
             * 다시 재귀가 끝나고 돌아오면 다시 뺀다.
             */
            chess[y][x] = true;
            dfs(i , count + 1 , black);
            chess[y][x] = false;
        }

        /**
         * 해당 색깔에 맞는 ans 를 선택해야 한다.
         * 그래서 black은 0번째 white 를 2번째로 진행할 것이다.
         */

        res[black ? 0 : 1] = Math.max(count , res[black ? 0 : 1]);
    }

    public static boolean check(int y, int x){
        /**
         * 일단 체크는 해당 지점에다가 놓을 수 있냐임
         * 그래서 그냥 윗 대각으로 두 방향 탐색해서 비숍 발견하면 바로 return true(놓을 수 없다.)
         * 그리고 그냥 끝까지 못 찾으면 return false 하면 된다.
         *
         * 그래서 해당 outOfRange 를 사용해서 범위를 벗어날 때까지 진행을 한다음에
         * 찾으면 true 를 반환하고 , 찾지 못하면 그냥 false 를 반환하는 그런 형태의 식을 만들면 된다.
         */

        for(int i = 0; i < 2; i++){
            int ny = y;
            int nx = x;

            while(!outOfRange(ny = ny + dy[i] , nx = nx + dx[i])){ // 지점을 나가면 바로 아웃 데스네
                if(chess[ny][nx]) return true;
            }
        }

        return false;
    }

    public static boolean outOfRange(int y, int x){
        if(y < 0 || y >= N || x < 0 || x >= N) return true;
        return false;
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());

        able = new int[N][N];
        chess = new boolean[N][N];
        color = new boolean[N][N];

        for(int i = 0; i < N; i++){
           st = new StringTokenizer(input.readLine());
           for(int j = 0; j < N; j++){
               able[i][j] = Integer.parseInt(st.nextToken());

               /**
                * black and white 로 나눠야 함 , 서로 영역을 침범하지 않아서 , 따로 진행하면
                * 2^100 - 알파 였던 것이 최대의 경우 (2 ^ 50 - 알파) * 2 로 바뀌게 된다.
                * 근데 뒤의 * 2 는 상수 값이나 다름 없기에 의미가 있지 않다 확실한 것은 이것은 수행시간을 줄이기 위해서는 획기적이다.
                * 그렇게 하려면 일단 black 과 white 의 기준을 알아야한다.
                *
                * 그림으로 한번 표현해보자.
                *
                * 1 0 1 0
                * 0 1 0 1
                * 1 0 1 0
                * 0 1 0 1
                *
                * 이런식으로 표현이 가능하다 , 1 == true , 0 == false 로 보면 된다.
                * 볼 수 있으면 알 수 있는 것이 홀수이면 홀 수 칸에
                * 짝수 이면 짝수 칸에 들어간다.
                *
                * 식으로 표현하면
                * i % 2 == 0 && j % 2 == 0 이러면 black 혹은
                * i % 2 != 0 && j % 2 != 0 이러면 black 이다.
                * 이 조건 둘다 틀리다? 그러면 white 라고 할 수 있다.
                *
                * 한번 진행해보자.
                */

               color[i][j] = (i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0); // 식 한줄로 표현이 가능하다.
           }
        }

//        for(int i = 0; i < N; i++){
//            for(int j = 0; j < N; j++){
//                System.out.print((color[i][j] ? 1 : 0) + " ");
//            }
//            System.out.println();
//        }

        dfs(-1 , 0 , true); //black 조사
        dfs(-1 , 0 , false); //white 조사

        System.out.println(res[0] +  res[1]);
    }

}
