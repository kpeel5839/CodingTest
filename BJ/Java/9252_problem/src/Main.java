import java.util.*;
import java.io.*;

// 9252 : LCS 2
/*
-- 전제 조건
LCS(Longest Common Subsequence, 최장 공통 부분 수열)문제는 두 수열이 주어졌을 때,
모두의 부분 수열이 되는 수열 중 가장 긴 것을 찾는 문제이다.
예를 들어, ACAYKP와 CAPCAK의 LCS는 ACAK가 된다.
-- 틀 설계
일단 dp 연산 같은 경우는
대각으로 가는 경우는 즉 서로 문자가 추가되는 경우에 , 서로 끝문자가 같다면
부분 수열이 될 수 있는 같은 문자를 찾은 것이다.
근데 , 만일 없다면 위 , 왼쪽 중 하나를 찾아서 그대로 가져오면 된다.
왜냐하면 이번에 같은 문자가 없기 때문에 , 이전에 LCS 값중 가장 큰 값을 가져오면 되기 때문이다.

이제 솔직히 이 부분은 문제가 없는데 , 수열 중 가장 긴 것을 찾는 이 과정이 살짝 문제가 있다.
일단 내가 생각한 아이디어는 parent 배열을 똑같이 두어서, 이거는 근데 , 안될 것 같다.
그러면 그게 아니라 마지막 지점에서 시작해서 , 첫번째 지점까지 역 순으로 탐색하는 방법도 존재한다.(stack)

예를 들어서
ACAYKP
CAPCAK

를 배열로 표현하면 (첫번째 문자열 부터 , 두개가 동일할 경우 이전 대각선에 존재하는 최대 부분 수열의 개수를
가져오기 위해서 한라인을 더 추가해서 선언한다.
    0  1  2  3  4  5  6  (ACAYKP)

0   0  0  0  0  0  0  0

1   0  0  1  1  1  1  1

2   0  1  1  2  2  2  2

3   0  1  1  2  2  2  3

4   0  1  2  2  2  2  3

5   0  1  2  3  3  3  3

6   0  1  2  3  3  4  4

[0, 0, 0, 0, 0, 0, 0]
[0, 0, 1, 1, 1, 1, 1]
[0, 1, 1, 2, 2, 2, 2]
[0, 1, 1, 2, 2, 2, 3]
[0, 1, 2, 2, 2, 2, 3]
[0, 1, 2, 3, 3, 3, 3]
[0, 1, 2, 3, 3, 4, 4]
 */
public class Main{
    public static int[][] dp;
    public static String a , b;
    public static Stack<Character> stack = new Stack<>();
    public static void getDp(){
        /*
        여기서 dp를 선언하고 , dp 를 구해준다.
         */
        dp = new int[b.length()][a.length()];

        for(int i = 1; i < b.length(); i++){
            for(int j = 1; j < a.length(); j++){
                if(b.charAt(i) == a.charAt(j)){
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
                else{
                    dp[i][j] = Math.max(dp[i - 1][j] , dp[i][j - 1]);
                }
            }
        }

//        for(int i = 0; i < b.length(); i++) System.out.println(Arrays.toString(dp[i]));
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));

        a = " " + input.readLine();
        b = " " + input.readLine(); // 길이 하나씩 늘려주기

        getDp();
        int y = b.length() - 1;
        int x = a.length() - 1;
        int k = dp[y][x];

        // k는 현재 찾는 수

        while(stack.size() != dp[b.length() - 1][a.length() - 1]){
            if(b.charAt(y) == a.charAt(x) && dp[y - 1][x - 1] == k - 1){
                stack.add(b.charAt(y));
                k--;
                y--;
                x--;
            }

            else{
                if(dp[y - 1][x] == k){
                    y--;
                }
                else{
                    x--;
                }
            }
        }

        output.write(dp[b.length() - 1][a.length() - 1] + "\n");

        while(!stack.isEmpty()){
            output.write(stack.pop());
        }

        output.flush();
        output.close();
    }
}