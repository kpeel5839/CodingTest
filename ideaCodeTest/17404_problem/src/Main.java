import java.util.*;
import java.io.*;

// 17404 : RGB 거리 2
/*
-- 전체 설계
RGB 거리에는 집이 N개 있고 , 거리는 선분으로 나타낼 수 있다.
여기서 지켜야 할 규칙은 단 하나ㄴㅇㄴㅁㅇ란모이로넘일, 앞 집과 , 뒷 집의 색깔이 겹쳐서는 안되는 것
이럴 때 , 최소비용을 출력해보아라
-- 틀 설계
시작점을 중요하게 생각하면서 , 선택한 첫번째 지점도 굉장히 중요하다.
일단 그냥 첫번째 지점만을 생각하고 , 중간에 애들은 아무것도 신경안쓰고 골라보자. (그리디 하게)

3
26 40 83
49 60 57
13 89 99

26 40 83
89(2) 86(1) 83(1)
13 여기서 선택할 수 있는 것이 없다. 여기서 할 수 있는 선택 57 이 2번을 선택하는 것 그렇게하게 되면

89(2) 86(1) 97(2)
110(2) .... 이렇게 갈 수가 있다.

결국 dp 구조가 3차원 으로 선언을 해야지 가능할 것 같다라는 생각이 든다.

dp 구조를 3차원으로 선언해보자.
dp[N][3][3] 으로 선언을 하고,
dp[N][0] == R
dp[N][1] == G
dp[N][2] == B

dp[N][value][0] == 처음에 1번째를 선택한 경우
dp[N][value][1] == 처음에 2번째를 선택한 경우
dp[N][value][2] == 처음에 3번째를 선택한 경우

그래서 이런식으로 dp 를 갱신해나가면서 ,
처음에 모든 dp를 INF 로 초기화 시켜준다. 이것이 의미하는 것은 이렇게 도달하지 못한다는 것을 의미한다.
1000 집 1000 보다 작거나 같은 자연수 이니 , INF == 1000 * 1000 보다 높은 수로 하면 된다.
INF = 2000000 로 하면 된다.

그리고 처음에는 그냥 dp[0][i][i] = dp[0][i] 로 초기화 해주면 된다.
그 다음에 다음부터는 첫번째 선택한 것들을 0 부터 시작한다.
dp[1][0][0] 부터 하는 것이다.
그래서 이 경우에는 본인의 0번 째 인덱스가 아닌 것들 중 [0] 을 해서 거기서 가장 작은 것을 골라내면 된다.
이 것을 끝 전까지 한다음에
dp[N - 1] 일 떄 여기서는 dp[N - 1][i][j] i 와 같은 것들은 제거
그리고 j 는 본인과 같지 않은 것 중에서 찾아준다.
그래서 여기서는 dp 에다가 값을 넣지 않고 , ans 와 비교해줘서
결과값을 뽑아내는 형식으로 가야할 것 같다.

0 == INF (선택하지 못하는 경우)

0 번째를 선택하는 경우(처음) - dp[i][j][0]
26 0 0
0 86 83
0 172 185

1 번째를 선택하는 경우(처음)
0 40 0
89 0 97
110 0 198

여기서부터 답이 나왔다
실제로는 2번째도 진행할테지만 , 이미 답이 나왔으니까 더 해보지는 않을 것이다.

지금도 고민이지만 마지막을 어떻게 진행해야 될지 모르곘다.

그냥 마지막 전까지 구한다음 마지막은 따로 하는 것도 괜찮을 것 같다.
 */
public class Main{
    public static final int INF = 200000;
    public static int n , ans = INF;
    public static int[][][] dp;
    public static int[][] map;

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(input.readLine());

        dp = new int[n][3][3];
        map = new int[n][3];

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < 3; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        } // map 에다가 값들을 얻어 놓는다.

        for(int i = 0; i < n; i++){
            for(int j = 0; j < 3; j++){
                Arrays.fill(dp[i][j] , INF);
            }
        }

        for(int i = 0; i < 3; i++){
            dp[0][i][i] = map[0][i];
        } // 값들 초기화

        // 일단 여기서 dp[n - 1] 전까지는 갱신들을 시켜 놔야함
        // 순서는 dp[i][j][k] (0 <= k <= 2) 이고 , dp[i][0][0] , dp[i][0][1] , dp[i][0][2] , dp[i][1][0] ... 이렇게 가야할 것 같음
        // 여기서 볼때 , 위의 순서대로 쫙 구하는데 , 여기서 구하는 과정에서는 dp[i][1][0] j 를 옮겨 가면서 봐야한다.
        for(int i = 1; i < n - 1; i++){
            for(int j = 0; j < 3; j++){
                for(int c = 0; c < 3; c++){
                    int min = INF;

                    for(int k = 0; k < 3; k++){ // 여기서부터 하나하나씩 돌려가면서 구해야 한다.
                        if(j == k) continue;
                        if(dp[i - 1][k][c] == INF) continue; // 값이 없으면 넘김
                        if(min > dp[i - 1][k][c]) min = dp[i - 1][k][c]; // 더 최소값을 찾으면 구함
                    }

                    if(min != INF){
                        dp[i][j][c] = map[i][j] + min;
                    }
                }
            }
        }

        // 이제 마지막 값을 구하면 된다.
        // 마지막 값은 넣는게 아니라 , 두 가지의 조건을 검사해야한다.
        // 첫째 , dp[i][j][c] 라고 했을 때 , j 값이 겹치면 넘기는 것
        // 둘째 , c 값을 본인과 겹치지 않게 하기 , 즉 , 본인의 j 와 c 가 겹치면 안된다.
        // 정리하면 j 값은 겹치면 안되고 , j 와 c 가 겹치면 안된다.
        // 즉 dp[i][j] 값을 구해야 한다. 정확히 말하면 dp[n - 1][j] 값들을 구해서
        // 이 중 가장 작은 값을 선택해야 한다.
        // 그러면 dp[n - 1][i] 이것이 현재 구해야 할 값이ㅏㄷ.
        // 그러면 dp[n - 2][j][c] 로 구해야 한다.
        // i == j 이면 나가리 그리고 i == c 이면 나가리이다.
        // 그리고 dp[n - 2][j][c] == INF 여도 나가리이다.
        for(int i = 0; i < 3; i++){
            int min = INF;
            for(int j = 0; j < 3; j++){
                if(i == j) continue;
                for(int c = 0; c < 3; c++){
                    if(i == c) continue;
                    if(dp[n - 2][j][c] == INF) continue;
                    if(min > dp[n - 2][j][c]) min = dp[n - 2][j][c];
                }
            }
            if(min != INF) {
                ans = Math.min(ans , map[n - 1][i] + min);
            }
        }

        System.out.println(ans);
    }
    public static void dpPrint(){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < 3; j++){
                for(int c = 0; c < 3; c++){
                    System.out.print(dp[j][c][i] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
