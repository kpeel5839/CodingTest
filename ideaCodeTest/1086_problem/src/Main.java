import java.util.*;
import java.io.*;

// 1086 : 박성원
/*
-- 전제조건
서로 다른 정수로 이루어진 집합이 있다.
이 집합의 순열을 합치면 큰 정수를 하나를 만들 수 있다. 예를 들어, {5221 , 40 , 1 , 58 , 9}가 있으면
5221401589를 만들 수 있다.

합친 수가 정수 K로 나누어 떨어지는 순열을 구하는 프로그램을 작성하시오.

근데 박성원은 이 문제를 풀지 못했다.
그래서 그냥 순열 하나를 정답이라고 출력하려고 한다.

이 문제에는 정답이 여러 개 있을 수도 있고 , 박성원은 당연하게도 우연히 문제의 정답을 맞출 수도 있다.

박성원이 우연히 정답을 맞출 확률 , 즉 (순열로 만들 수 있는 조합중 k 로 나눌 수 있는 수 / 순열로 만들 수 있는 경우의 수) 를 출력하면 된다.

N이 주어지고 N <= 15이다. 둘째 줄에는 집합에 포함된 수가 주어지고 , 각 수의 길이는 길어야 50인 자연수이다.
마지막 줄에는 k(나누는 수) 주어진다 . k는 100보다 작거나 같은 자연수이다.

예제로 나온 n = 3 , 수열 = 3 2 1 , k = 2 경우로 생각하면
123 , 132 , 213 , 231 , 312 , 321 의 경우의 수가 있고
여기서 나누어지는 수는 132 , 312 이다 , 그렇기 때문에 2 / 6 이다 , 근데 기약분수로 표현해야 하기 때문에
1 / 3 이라고 표현이 가능하다.
-- 틀 설계
일단 gcd 함수가 필요하다 , (최대 공약수를 이용해서 기약분수를 만들기 위해서)
i는 비트마스킹을 의미한다.
dp[i][j] = 는 i가 나타내는 부분집합대로 선택하였을 때 나머지가 j 인 것들의 수를 의미한다.
그래서 여기서는 어떻게 해줄 것이냐면 , dp[i | 1 << l][next] += dp[i][j] 로 나타낼 수 있다.
이것은 이제 i 에서 추가적으로 l번째 수를 포함시켰을 때의 값을 추가하는 것이다.
next = (새로만들어진 수) % k (그래서 next는 이렇게 정의할 수 있다 , l번째 정수를 선택하면 새로운 수가 생길 것이고 , 그러면 나머지도 다르다.)
쨋든 그래서 next값을 구하면 dp[i][j] 에다가 그냥 l번만 추가하면 dp[i | 1 << l][next] 새로운 수에 next의 나머지를 가지니까
기존의 dp[i | 1 << l][next] 에 dp[i][j] 를 추가하면 되는 것이다 (i 의 부분집합에 l을 추가하는 것이니까)
근데 그럴려면 l을 포함하지 않은 것을 골라야한다. 그래서 l을 포함하지 않은 것을 찾기 위해서 이러한 조건문을 사용해야 한다.
if(i & 1 << l == 0) 이때만 적용해야 함 , 왜냐하면 l을 추가적으로 선택했을 때 그렇게 되는 것을 표현하는 것이기 때문이다.
= (원래 수 * pow( 10, len(l번째 수) ) + l번째 수) % k
= [ (원래 수 * pow( 10, len(l번째 수) ) ) % k  + l번째 수 % k ] % k
= [ (원래 수 % k  * pow( 10, len(l번째 수) ) % k ) %k + l번째 수 % k ] % k
= [ (j * pow( 10, len(l번째 수) ) % k ) % k +    l번째 수 % k ] % k

그래서 쨌든 이렇게 계속 비트마스킹으로 dp[0][0] = 1 에서 부터 , 아무것도 선택하지 않았을 때에 나머지가 당연히 1이니까
순서대로 모든 것을 다 구하는 것이다.

그 다음에 dp를 다 구했으면 , dp[(1 << n) - 1][0] 은 분자이고
n! 은 분모이다.
그래서 더 큰 것을 gcd의 a 변수로 넘겨서 최대 공약수를 받아서 분자와 분모를 연산하면 끝난다.

그리고 여기서 필요한 전처리는 일단 l번째 수의 length() 가 필요하다.
그리고 l의 크기 , 즉 진짜 크기가 필요하고 Math.pow(10 , n) 값, 그 다음에는 그냥 k만 알면된다.
근데 Math.pow(10 , n)의 크기는 n 이 최대 50이니 너무 크다.
 그래서 전처리가 필요하다 , ㅈ근데 그 과정은 10 * n 한다음에 % k 하면 된다.

-- 해맸던 점
dp 를 long으로 선언하지 않아서 범위가 초과되어서 틀렸었음

-- 결론 및 이해한 내용
일단 먼저 dp[i | 1 << l][next] += dp[i][j] 에 대해서 해석하자면 ,
이 상황에서 가장 작은 예인 처음에 3 2 1 이 들어온 경우를 예를 들어보자.
dp[1 << n][k] 크기로 선언하였다 , 즉 i 는 7까지 갈 수 있다
여기서 그냥 간단하게 i == 0 일 때의 경우를 보면 일단 i == 0일때 j < k 까지 즉 , 나머지가 0 ~ k - 1 인 경우를 계산하고 , l == 0 ~ n - 1; 까지 즉
나머지 0 ~ k - 1 까지 진행하는데 n - 1 번째까지 선택하는 것으로 들어가는 것이다.
그러면 일단 i == 0 이라는 것은 순열에서 하나도 선택하지 않은 경우이다.
그 경우에서 [0][0] 인 경우를 먼저 보면 [0][0] , l = 0 인 경우는 첫번째 수를 선택하는 것이다. 여기서는 1이다.
그 다음에 여기서 1을 선택했을 때 나머지를 보려면 next = (변경된 수) % k 로 나타낼 수 있다. (원래의 수에서 뒤에 1을 붙히는 것이다.)
그렇게 됐을 때 next = [(원래의 수) % k * (Math.pow(10 , len(l)) % k ] % k + a[i] % k] % k로 정의할 수 있다.
(원래의 수) % k 이것은 == j 로 정의할 수 있다.
그리고 Math.pow(len(l)) % k 이것은 ten을 정의해놨었다.
그래서 next = [[j * ten[l]] % k + a[l] % k] % k 로 정의가 가능한 것이다.
그래서 이렇게 next를 정의하게 되면 지금 현재의 i에서 l을 선택했으니까 dp[i | 1 << l][next] 로 정의 가능하다. 나머지인 next도 찾았고
l을 추가한 i 값도 찾았다. 그래서 dp[i | 1 <<l][next] += dp[i][j] 를 하면 되는 것이다. 왜냐하면 지금 dp[i][j] 에서 l 번째 정수를 추가했더니 나머지가 next가 되었으니
dp[i | 1 << l][next] 가 원래 가지고 있던 수에서 dp[i][j]를 더하면 되는 것이다 i 부분 집합에서 l을 추가한 것이니까
그래서 이렇게 하나하나씩 탐색해가면서 수열들의 조합 중 k이하인 것의 수들을 찾을 수가 있는 것이다.

그리고 이게 가능한 것은 순서대로만 조합하는 것이 아니라. 예를 들어서 101 에다가 010 을 더하는 경우
혹은 110 001 을 더하는 경우 , 혹은 011 에다가 100 을 더하는 경우 이 경우들은 다 수열들의 순서가 다르다 표현하면 312 , 321, 213 이라고 표현이 가능하다.
물론 110 , 101 , 011 이것들도 이전에 다른 순서로 들어온 것들이다 , 그렇기 때문에 원래의 수 -> l번째 정수를 추가한 수의 과정은 그냥 l번째 수를 뒤에다가 붙히면 되는 것이다.
그래서 뒤에다가 붙히는 과정이 당연히 l번째의 정수 길이만큼 뒤에 붙게 되니까 원래의 수 * Math.pow(10 , len(l)) 인 것이고 여기서 + l번째 수 를 한다음에 두개를 더한 것을
% k 로 나눠주면 되는 것이다 , 이 과정에서 수가 너무 커지는 것을 방지하기 위해서 모듈러 연산을 먼저 하는 과정에서 원래의 수 % k 는 지금 j 값이고 (dp[i][j] == i 대로 수열들을 선택하였을 때 j 의 나머지를 가지는 수의 개수이니까 (i대로 고른 원래의 수) % k == j)
그리고 Math.pow도 최대 길이 50이니 너무 커지니까 모듈러 연산을 미리 해놓은 (전처리를 해놓은) ten 배열을 이용해서 곱해준 것이다.

그래서 결론적으로 dp로 i == 0 ~ (1 << n 즉 n == 3일 때는 1000 == 8 Math.pow(2 , n)) 과 같음) 그리고 j == 0 ~ k 까지 l == 0 ~ n 까지 항상 모든 것들을 수열에다가 포함시키면서
dp[(1 << n) - 1][0] 값이 모든 수열들의 경우에서 0을 나머지로 가지는 경우의 수로 되는 것이다.
그래서 이것을 분자로 n! 을 분모로 (모든 경우의 수 , 수열로 만들 수 있는) 해서 gcd로 빠르게 최대 공약수(이걸로 나누면 서로 소가 되게끔 할 수 있는 수임 , 유클리드 호제법을 이용해서) 구한 뒤에 분모 , 분자르 나눠줘서 나타내면 끝난다.
 */
public class Main {
    public static long gcd(long a , long b){
        if(b == 0) return a;
        else return gcd(b , a % b);
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine());

        String[] number = new String[n];
        int[] len = new int[n];
        for(int i = 0; i < n; i++){
            number[i] = input.readLine();
            len[i] = number[i].length();
        }

        int k = Integer.parseInt(input.readLine());
        int[] a = new int[n];
        int[] ten = new int[51]; // 10의 제곱 전처리가 필요함

        for(int i = 0; i < n; i++){
            for(int j = 0; j < len[i]; j++) {
                a[i] = a[i] * 10 + (number[i].charAt(j) - '0'); // 50자리 까지도 가기 때문에 이렇게 저장함
                a[i] %= k;
            }
        }

//        System.out.println(Arrays.toString(a));

        ten[0] = 1;
        for(int i = 1; i <= 50; i++){
            ten[i] = (ten[i - 1] * 10) % k;
        }

        long[][] dp = new long[1 << n][k];

        dp[0][0] = 1; // 이래야지 계산이 가능함
        for(int i = 0; i < (1 << n); i++){
            for(int j = 0; j < k; j++){
                for(int l = 0; l < n; l++){
                    if((i & (1 << l)) == 0){ //l을 선택하지 않은 경우를 고름
                        /*
                        [ (j * pow( 10, len(l번째 수) ) % k ) % k +    l번째 수 % k ] % k 이 식이 필요함
                         지금 수의 % k 가 == j 이고 , 나머지들은 그냥 k 로 나눈 수들이다 그래서 여기서 필요한 수는
                         j , ten , a[l] , len[l] 이다.
                         */
                        int next = (j * ten[len[l]]) % k;
                        next += a[l] % k;
                        next %= k;
                        dp[i | 1 << l][next] += dp[i][j];
                    }
                }
            }
        }
        /*
        p는 분모 , q는 분자로 나타내자.
        그러면 q는 그냥 n! 이고 , p는 dp[1 << n - 1][0] 이다
         */
//        System.out.println((1 << n) - 1);
        long q = dp[(1 << n) - 1][0];
        long p = 1;
        for(int i = 2; i <= n; i++) p *= i;
        long div = gcd(Math.max(p , q) , Math.min(p , q));
//        System.out.println("q : " + q + " p : " + p);
        q /= div;
        p /= div;

        System.out.println(q + "/" + p);
    }
}
