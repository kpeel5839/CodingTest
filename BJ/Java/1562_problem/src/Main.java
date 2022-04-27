import java.util.*;
import java.io.*;

// 1562 : 계단 수

/*
-- 전제 조건
n 이 주어질 때 길이가 n 이면서 계단 수인 것들을 전부다 구한다.
정답은 1,000,000,000 (10억) 으로 나눈 값을 출력한다.
-- 틀 설계
어떻게 설계를 할 수가 있을까?
일단 확실한 것은 계단수란 인접한 모든 숫자가 차이가 1이 나는 상황이다.
그리고 길이가 N이면서 , 0 부터 9까지 모든 숫자가 등장하는 게단수를 구해야하는 것이다.

그렇다라는 것은 일단 길이가 N <= 9 이다? 그러면 무조건 0이다. 일단 0 ~ 9 까지 숫자가 모두 등장해야 하는데,
그럴 수가 없기 때문이다.

일단 그러면 dp[10] 부터 생각해본다고 해보자.
9876543210 왜냐하면 , 0으로 시작하는 수는 정수가 아닐 뿐더러 여기서도 명시하고 있듯 0으로 시작하는 수는 계단수가 아니라고 명확하게 정의 내렸기 때문이다.

한 자리에는 10개의 숫자가 들어올 수 있다.(10진법)
해당 숫자를 탐색하는 것도 아니다 , 왜냐하면 주어진 수가 없기 때문이다 , 이 수 안에 계단 수가 몇개가 들어있냐가 아닌,
총 그냥 숫자중에 N자리 숫자에서 , 계단수가 몇개인가를 묻는 것이기 때문이다.

일단 항상 그렇듯 이 수가 계단 수인지 , 해당 dp[i] 가 계단 수가 몇개인지를 보았을 때 ,
다음으로 될 수 있는 계단 수는 몇개가 있을 지를 구할 수 있을 까 ? 그러니 dp[i] 를 알 때 , dp[i + 1] 을 알 수 있을까?

그리고 힌트로 N 이 1일 때 부터 N 이 40일 떄까지 모두 더한 값을 알려준 것도
해당 자리수를 설정하고 거기서 탐색하는 형식의 풀이 방법이 아닌 , bottom - up 방식으로 접근하는 풀이일 것 같다.

일단 그러면 우리는 dp[10] == 1 이라는 사실을 안다.
그렇다라는 것은 한자리만 추가가 되었을 때를 경우를 한번 구해보자.

일단 , 추가될 수 있는 자리?

dp[10] 이 가르키는 것은 해당 경우의 수는 계단의 수의 조건을 만족한다라는 것이다.
그렇다라는 것은 dp[11] 은 앞에 혹은 뒤에다가 경우의 수를 추가할 수 있다.

그러면
9876543210 여기에다가 뒤나 혹은 앞에다가 추가할 수 있는 것이다.

생각 해보니까 , 뒤에다가 숫자 그냥 3개를 집어넣을 수가 있구나,
98...
99....
이렇게도 되고
8이면
89... 는 안되고
88 , 87... 은 된다.
연속된 숫자가 반복되는 경우에는 3가지의 경우
만일 나와 1차이가 나는 경우의 수가 반복되는 경우는 2가지의 경우가 존재한다.

솔직히 감이 전혀 안잡히기 때문에 , 답을 한번 봐야 할 것 같다.

또 개같은 문제를 잘 못 읽었었다.
0 차이나면 안되고 , 무조건 1이 차이가 나야 한다.

결국 해답을 보았음 , 근데 차라리 빨리 본게 낫다 , 나 혼자서는 절대 생각못해낼 해법이다.
일단 비트마스킹의 문제는 항상 그렇듯 , 체크하는 , 내가 어떤 수를 거쳤는지 체크를 하는 것이 필요하다 ,
그것은 바로 dp[i][j][k] 의 k 부분이다 , 이 k 부분은 0 ~ 9 까지의 숫자를 몇개를 포함하고 있냐를 구하는 것이다.

그리고 i == 현재 자리수를 의미하고 , j 는 맨 끝의 수이다.
왜 끝의 수만 신경을 쓰냐면 , 짜피 1번째 자리부터 경우의 수를 구하기 시작한다 , 즉 , 맨 마지막 자리에 의해서 , 이 수가 어떤 수라면 첫번째 자리는 정해지기 마련이고 ,
마지막 수를 정의하면 , 첫 번째 수까지 신경쓰게 되는 것이다.
그래서 마지막 수를 고려해서 , 해당 수를 추가하였을 때 , 어떻게 되는지 확인을 해야한다.

전제 자체는 간단하다 , 일단 dp[i][j][k] 를 해서 k == 0 (아무것도 포함하지 않은 상태) k == 1 << 10 - 1 이 상태(모두 다 포함하고 있는 상태 , 즉 우리가 구하려는 상태이다.)
를 구하는 것이다.

그러면 일단 for(int i = 1; i < 10; i++) dp[1][i][1 << i] = 1 를 해준다 이것이 의마하는 것은 0으로 시작하는 수는 아얘 존재하지 않으니까 dp[1][0][1] 은 경우의 수를 갱신해주지 않는다 짜피 0이니까
그리고 나머지들 끝자리수가 i 이면서 1자리 수 이니깐 i를 포함하는 경우의 수는 당연하게도 1개이다 , 그래서 dp[1][i][1 << i] 라는 식이 탄생하게 되는 것이다.

이제
for(int i = 2; i <= N; i++) // 첫 째 자리는 이미 구했으니까 , 짜피 1개임 다
for(int j = 0; j < 10; j++) // 끝의 자리가 0 ~ 9 일 때의 경우들을 구한다.
for(int k = 0; k < 1 << 10; k++) // k 는 아무 수를 포함하지 않을 때 부터 , 다 포함하는 경우까지 다 구한다 , 근데 솔직히 내 생각은 0은 할 필요가 없어 보인다 , i 가 2라는 것은 적어도 1 부터는 시작해야 한다.
당연하게도 1부터 시작해서 맞았음 , 2 이상 부터는 안된다.
그러면 애초에 0을 포함시키는 경우를 구할 수가 없다. (0만 포함시키는 경우)

그래서 이런식으로 for 문을 구성하고 , 이 dp[i][j][k] 의 상황에서 구할 수 있는 경우의 수는 당연하게도 여기서 하나의 수를 더 포함시킨 경우이다.
그래서 여기서 구할 수 있는 것은 하나의 수를 더 포함시키고 , 지금 내 끝자리가 j 이니까 , 그 더하려는 수의 j는 당연히 나보다 , 1이 크거나 작아야 한다.
근데 여기서 예외 사항은 j == 0 , j == 9 일 때에는 j + 1  , j - 1 만 올 수 있다 이 예외 사항들만 처리해주고 ,
int bit = k | (1 << j); 를 해줘서 , bit == 즉 여기서 j를 더 포함하고 있는 경우를 만든다.
근데 여기서 오해하면 안될 것이 dp[i][j][k] 를 이용해서 만드는 것이 아닌 dp[i - 1][j +- 1][k] 를 이용해서 만드는 것이기에 , dp[i][j][k] 에 있는 것이 아니다.
즉 for 문으로 구성해준 것은 dp[i][j][k | 1 << j] 를 구하기 위해서 구성한 것이다.
그래서 i == 2 부터 시작하는 것이다.
그래서 이런시긍로 계속 j 를 추가적으로 선택해주는 형ㅅ아을 띄면서 , dp[i - 1][j - 1][k] , dp[i - 1][j + 1][k] 를 이용해서 값을 점점 구해나가는 것이다.

그래서 내가 처음에 생각했던 점은 , 그냥 계단 수의 경우만 구하자 , 0 ~ 9 를 포함한 , 근데 그게 아니라
모든 계단 수를 구하고 , 거기서 해당 계단 수가 몇개의 숫자를 포함하고 있는지 체크하면서 푸는 문제였다.
이게 키워드 였던 것 같다.
짜피 0 ~ 9 만 포함된 숫자만 구하지 않더라도 마지막에 dp[N][i][1023] 만 다 구하면 0 ~ 9 로 끝나는 숫자들 중
0 ~ 9 를 포함한 N 자리의 계단 수들을 다 구할 수 있는 것이다.

아직 난 멀은 것 같다. 계속 더 해서 생각하는 방법을 기르자.

-- 해맸던 점
처음에 dp를 long 으로 선언을 안했고,
다 맞았는데 , 이상하게 다 틀렸습니다 나와서 혹시 몰라서
result += dp[N][i][1023] % MOD 를 -> result = (result + dp[N][i][1023]) % MOD 로 바꾸었 더니 맞았음 , 연산 순서때문에 틀렸었던 것 같고
그 다음에 dp 연산을 진행할 때 MOD를 지웠었는데 , 그것 때문에 , 또 틀렸었다 , 이외에 것들은 맞음
 */
public class Main {
    public static int N;
    public static final int MOD = 1_000_000_000;
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(input.readLine());

        long[][][]  dp = new long[N + 1][10][1 << 10]; // 10 자리 수를 비트마스킹을 통해서 , 표현할 것이기 때문에 , 1 << 10 으로 크기를 맞춰줌

        for(int i = 1; i < 10; i++){ // 0 으로 시작하는 수는 없으니까 , 0으로 시작하는 것은 없애기위해서 i == 1 부터 시작함
            dp[1][i][1 << i] = 1; // 1자리 수 면서 i 로 끝나는 수이고 , 비트마스킹 1 << i 를 포함하고 있는 경우의 수는 1자리에서는 당연히 1이다.
        }

//        System.out.println(1 << 10);

        for(int i = 2; i <= N; i++){
            for(int j = 0; j < 10; j++){
                for(int k = 1; k < 1 << 10; k++){
                    int bit = k | (1 << j); // j 가 포함된 수를 구하자 , 결국 dp[i][j][k] 는 dp[i][j][bit] 를 구하려는 수단임
                    // k == bit continue 가 안되는 이유는 j 를 이미 포함하고 있던 애도 데려와서 더해야 함 , 그래서 넘기면 안되고 더해주어야 함
                    if(j == 0){ // 끝자리가 9인 경우나 0 인 경우는 하나 밖에 더 못들어옴
                        dp[i][j][bit] = (dp[i][j][bit] + dp[i - 1][j + 1][k]) % MOD; // 뒤에 수가 j 이니 , i - 1 에서는 j를 제외하고 포함하고 있거나 j도 포함한 수를 가져와서
                        // 연산을 진행해준다. 즉 여기서 중요한 점은 j를 포함하고 있거나 , j를 포함하지 않고 있던 애들을 데려와서 , 끝자리가 내 지금 끝자리인 j와 +- 1의 차이가 나냐를 계산해서 가져올 수 있으면 가져오는 것이고
                        // j == 0 , j == 9 인 경우는 각각 j + 1 , j - 1 밖에 못가져오니까 , 따로 예외 사항을 두어서 진행한 것이다.
                        // 그러니까 , 이 연산의 핵심은 끝자리가 j라는 가정하에 j를 포함하고 있고 bit 와 같은 포함 관계를 가진 수 , 혹은 j를 포함하고 있지 않던 수 , 근데 j를 포함관계가 나와 같아지는 수들을 더해서 결과를 도출해내는 것이다.
                    } else if(j == 9){
                        dp[i][j][bit] = (dp[i][j][bit] + dp[i - 1][j - 1][k]) % MOD;
                    } else{
                        dp[i][j][bit] = (dp[i][j][bit] + dp[i - 1][j - 1][k] + dp[i - 1][j + 1][k]) % MOD;
                    }
                }
            }
        }

        long result = 0;
//        long check = 0;
//        for(int i = 1; i <= 40; i++){
//            for(int j = 0; j < 10; j++){
//                check += dp[i][j][1023];
//            }
//        }
        for(int i = 0; i < 10; i++){
            result = (result + dp[N][i][1023]) % MOD;
        }
//        System.out.println(check);
        System.out.println(result);
    }
}