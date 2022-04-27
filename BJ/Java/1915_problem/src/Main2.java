import java.util.*;
import java.io.*;

// 1915 : 가장 큰 정사각형
/*
-- 전제 조건
가장 큰 정사각형의 넓이를 출력하면 된다.
즉 , 그 말은 해당 정사각형이 존재한다면 , 그 셀의 개수가 넓이이다.

한번 그냥 되게 low level 하게 진행해보자.
1000 * 1000 * 1000 == 10 억이다.

생각을 잘 못해서 low level 하게 진행할 뻔
-- 틀 설계
일단 이분탐색 , 그리고 그냥 탐색 당연히 안된다.
이분탐색은 해보니까 시간초과가 나온다 , 아마 된다고 하더라도 그렇게 많이 시간이 많이 줄어들지는 않는 것 같다.

일단 정사각형임을 알 수 있는 방법이 있을까 생각해보자.
정사각형임을 알 수 있는 방법 , 그냥 다 확인해보는 바법이 있는데,
크게 분할 정복이 있다.

아니면 그냥 size 를 정해두고 하는 것이 아닌
한 지점에 도달하였을 때 , 이 지점에 들어올 수 있는 정사각형의 최대 크기를 알 수 있으면 괜찮지 않을까?

결국 해답을 보았음 이 식의 다이나믹 프로그래밍 풀이는 이러하다.
dp[i][j] = min(dp[i - 1][j] , dp[i][j - 1] , dp[i - 1][j - 1]) + 1;
나는 너무 이 사각형이 옳은 사각형인지 , 아닌 사각형인지에 너무 옭아매고 있었음

결국 그것이 아닌 , 정사각형의 넓이를 구하는 것인데 말이다.
한번 예제를 변경해서 다시 생각해보자.

0 1 1 1
1 1 1 1
0 1 1 1
1 0 0 0

1번째 지점부터 그냥 쭉 16번째 지점까지의 사각형의 크기를 구해보자.
1 = 0
2 = 1
3 = 1
4 = 1
5 = 1
6 = 1
7 여기서부터 신경을 써야한다.
dp[i - 1][j - 1] 의 넓이와 , 본인 위 , 왼쪽의 넓이 중 작은 것을 골라야 한다.
거기다가 + 1 을 해줘서 dp[i][j] 를 저장한다.

7 = 2 이다.
8 = 2 , 1 , 1 이 주변에 있지만 , 하지만 제일 작은 것을 선택해야 한다.
그래서 1을 선택하면 + 1 이 돼서
8 = 2
이다..
이렇게 쭉쭉 가다 보면 12 번째 인덱스에서 드디어 3이라는 숫자가 나오게 된다.

이것을 ans * ans 를 해서 결과값으로 찍으면 된다.

일단 왜 이렇게 하는지에 대해서 생각을 해보면 , dp[i - 1][j - 1] , dp[i - 1][j] , dp[i][j - 1] 모두
지금까지 그 지점에 형성된 , 그 지점을 끝점으로 하는 정사각형의 길이들이다.

예를 들어서 dp 배열을 형상화하면
0 1 1 1
0 1 2 2
0 1 2 3

이렇게 있다고 가정을 하자 , (1, 3) 은 왜 2가 될까?
옆에 있는 정사각형의 길이가 2인데 말이다.

그것은 옆에 있는 정사각형의 길이가 2이더라도 , dp[i][j] 를 정사각형의 반열에 포함하게 되었을 때
dp[i - 1][j] 도 , dp[i - 1][j - 1] 도 , 정사각형의 반열에 포함이 되기 때문이다 , 무조건 적으로 만약 값이 1이 아니라면
근데 1이려면 주변에 있는 애들이 0인 애들이 있어야 한다.

쨋든 그래서 주변에 있는 것들 중 큰 것을 고르게 되면
0 1 1 0
0 1 1 1
... 이러한 상황에서 (1, 3) 이 3이 되는 경우가 생길 수 있다 하지만
본인의 위에 있는 애는 0이고 , 왼쪽에 있는 애만 길이가 2인 정사각형이다.
그렇기 때문에 , 3 방향을 다 고려해야한다..

이 3가지를 왜 고려해야 하는지에 대해서 case 들을 통해서 이해해보자.

dp[i - 1][j] 를 고려해야 하는 이유
0 1 1 0
0 1 1 1

dp[i][j - 1] 를 고려해야 하는 이유
0 1 1 1
0 1 0 1

dp[i - 1][j - 1] 를 고려해야 하는 이유
0 1 0 1
0 1 1 0

이러한 3가지의 상황들로 인해서 3 지점을 다 고려해서 해야하는 것이다.

-- 해맸던 점
dp 배열의 크기와 , string 의 크기를 고려하지 않아서 , StringIndexOutOfBounds , ArrayIndexOutOfBounds exception 발생했었음
 */
public class Main2 {
    public static int H , W , ans = 0;
    public static int[][] map;
    public static int[][] dp;
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        map = new int[H + 1][W + 1];
        dp = new int[H + 1][W + 1];

        for(int i = 1; i <= H; i++){
            String string = input.readLine();
            for(int j = 1; j <= W; j++){
                map[i][j] = string.charAt(j - 1) - '0';
            }
        } // 주변을 0으로 만들어준다 , 그래서 dp[i - 1][j - 1] , dp[i - 1][j] , dp[i][j - 1]을 고려할 수 있도록 해준다.

        for(int i = 1; i <= H; i++){
            for(int j = 1; j <= W; j++){
                if(map[i][j] == 1){ // 정사각형의 반열에 포함될 수 있을 때
                    dp[i][j] = Math.min(dp[i - 1][j - 1] , Math.min(dp[i - 1][j] , dp[i][j - 1])) + 1;
                    ans = Math.max(ans , dp[i][j]);
                }
            }
        }

        System.out.println(ans * ans);

    }
}
