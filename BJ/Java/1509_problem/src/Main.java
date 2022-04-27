import java.util.*;
import java.io.*;

// 1509 : 팰린드롬 분할

/*
-- 전제 조건
어떤 문자열이 주어지면
팰린드롬을 구하면 된다.
근데 , 해당 문자가 당연히 팰린드롬이 아닐 수도 있다.
그럴 떄 , 해당 문자열을 분할해서 , 최소의 개수로 팰린드롬을 만들어라
그 최소 개수를 출력하여라.

내가 문제 잘못 읽어서 자꾸 이상하게 생각하고 있었음,
나는 분할의 경우의 수들을 모두 구하라는 것인 줄 알았다.
근데 , 그것도 애매했던게 , aaaa 가 1이라는게 너무 이상했다.
그래서 설계 자체에 어려움이 있었는데 , 알고보니까 , 문자열을 분할해서 , 팰린드롬으로 만드는 경우를 구하고 , 그 중에서 최소의 개수로 분할한 경우를 구하는 것이였다.
그래서 설계를 할 수가 있었다.
-- 틀 설계
일단 어떠한 문자열 즉 p[i][j] - i ~ j 까지는 팰린드롬이다 즉 다르게 말하면 s.subString(i , j + 1); 은 팰린드롬이라는 것이다.
이것은 쉽게 구할 수 있다.
일단 p[i][i] 는 무조건 팰린드롬이다 한글자이니까
2글자인 경우는 p[i][i + 1] 즉 이 두문자가 같으면 팰린드롬이다.
3글자 경우부터 p[i][i + 2] 즉 i 와 i + 2가 같고 i + 1 이 팰린드롬이면 된다.
이렇게 3글자 이상부터는 양끝단의 문자가 같고 , 가운데에 있는 것이 팰린드롬이면 true 이다.

그리고서 , 이제 팰린드롬을 다 구하면 dp 일차원 배열을 만들어준다.
그 다음에 i , j 를 정해주고 dp = new int[s.length() + 1] 로 만들어준다.
그리고 i == 1 , j == 1 부터 시작하면서 ,
문자를 보고 , 해당 문자를 최소 몇개의 분할로 나누어서 팰린드롬으로 나타낼 수 있는지 나타낸다.
일단 abba 가 있다고 가정해보자.
a 만 보면 팰린드롬이다.
그래서 {a} , 그리고 ab 를 보자 , 일단 ab 팰린드롬이 아니다 , 다음 것으로 넘어가면 b 팰린드롬이다.
그래서 2번째까지는 {a} , {b} 로 나뉠 수가 있다.
다음 것으로 넘어가보자 abb 팰린드롬이 아니다.
bb 팰린드롬이다.
그렇다라는 것은 여기서 dp[i - 1] 이 나타내는 것 즉 {a} 와 팰린드롬이니까 + 1 을해주어서 {bb} 를 나타내주면 된다.
그러면 {a} , {bb} 로 나타낼 수 있따.
다음 것을 탐색해보자.
abba 팰린드롬이다 . 그러면 dp[i - 1] + 1 인데 여기서 new int[s.length() + 1] 로 해준 이유가 나온다.
dp[0] 을 0으로 유지하기 위해서이다. 그래서 첫번째 문자에서 dp[i - 1] 은 == 0 이니까 dp[i] = 0 + 1 가 된다.
그래서 , dp[i] = Math.min(dp[i] , dp[i - 1] + 1) 를 계속 해주면
답을 구할 수 있다 즉 dp[s.length()] 가 답이다.

-- 해맸던 점
일단 문자열의 인덱스를 고려하지 않아서 해맸었음 , 문자열의 인덱스도 0 부터 시작이기 떄문에 , 그래서 s =  " " + s; 로 해결하였고,
그리고 dp 값을 각각 최대로 가질 수 있는 수로 초기화를 해놨어야 했는데 그것도 안해서 조금 해맸었음

그리고 , 항상 구할 때 , dp[i] 까지의 최소분할의 개수를 구해얗 ㅏ기 때문에 dp[j][i] 까지 했었어야 했는데
dp[j][len] 을 해서 살짝해맸음 , 그것 말고는 쭉쭉 진행되었음
 */
public class Main {
    public static boolean[][] p;
    public static String s;
    public static int len;
    public static void getP(){
        /*
        팰린드롬을 구하는 함수
        일단 하나는 무조건 true 이고
        2개 부터 양끝단이 같은지 보고
        3개 이상부터는 그냥 양끝단 같은지 보고 가운데에 껴있는 것이 팰린드롬인지 확인한다.
         */
        for(int i = 1; i <= len; i++){
            p[i][i] = true;
        }

        for(int i = 1; i < len; i++){
            for(int j = 1; i + j <= len; j++){
                // j 는 현재 보려는 시작 인덱스 , i 는 길이
//                System.out.println("i : " + i + " j : " + j);
                if(i == 1){
                    if(s.charAt(j) == s.charAt(j + i)) p[j][j + i] = true;
                }
                else{
                    if(s.charAt(j) == s.charAt(j + i) && p[j + 1][j + i - 1]) p[j][j + i] = true;
                }
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        s = input.readLine();
        len = s.length();
        s = " " + s;
        p = new boolean[len + 1][len + 1];

        getP();

        int[] dp = new int[len + 1];
        for(int i = 0; i <= len; i++) dp[i] = i;

//        for(int i = 0; i <= len; i++) System.out.println(Arrays.toString(p[i]));

        for(int i = 1; i <= len; i++){
            for(int j = 1; j <= i; j++){
                // dp[i] 를 의미하는 i 와 dp[j][len] 이 팰린드롬인지 확인하는 j를 합친다.
                if(p[j][i]) dp[i] = Math.min(dp[i] , dp[j - 1] + 1);
            }
        }

        System.out.println(dp[len]);
    }
}
