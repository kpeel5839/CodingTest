import java.util.*;
import java.io.*;

// 2293 : 동전 1
/*
--전제조건
n 가지 종류의 동전이 있다. 각각의 동전이 나타내는 가치는 다르다.
이 동전을 적당히 사용해서 , 그 가치의 합이 k원이 되도록 하고 싶다. 그 경우의 수를 구하시오. 각각의 동전은 몇개라도 사용할 수 있음
사용한 동전의 구성이 같은데 , 순서만 다른 것은 같은 경우이다.

처음에 n , k 가 주어지고
다음 줄부터 n개의 줄이 주어지는데 각각의 동전의 가치가 나온다.
3 10 \n 1 \n 2 \n 5 같은 경우에는
뭐 [1 ..... 1] , [1 ..... 2] .... [5, 5] 이런식으로 경우가 있을 것이고 답은 10이 나오는 것이다.
순서만 다르고 구성이 같으면 두개는 같은 것으로 따져진다.
동전은 몇번이고 사용이 가능하다. 그냥 몇개쓰든 상관 없고 k 를 만드는 경우를 구하면 되는 것이다.
--틀 설계
일단 0 ~ n 까지 dp의 배열을 채워넣는다.
일단 첫번째로 가장 작은 단위로부터 값을 채워넣는다.
이 경우에 해당 단위의 코인으로서만 채울 수 있는 경우에 +1을 해주어야 한다.
예를 들어서 1 2 5 가 주어지고 3 5라고 해보자
0 1 2 3 4 5
0 1 1 1 1 1 이렇게 될 것이다 처음에는 왜냐하면 1로 5까지 다 하나의 경우는 만들 수가 있으니까
이제 2로 만들 수 있는 경우를 구해서 추가하면
0 1 2 3 4 5
0 1 2 1 3 2
3과 5의 경우를 보자
3 - [1 1 1] [2 1] 인 경우이고
5 - [1 1 1 1 1] [2 1 1 1] [2 2 1] 이다 이 경우 2로만 구성할 수는 없으나
이전의 3에다가 2를 하나 추가해서 구성하는 것은 가능하다 그렇기 때문에 현재의 3에서 2 하나만 추가하면 모든 구성이 가능하니 dp[3] + dp[5] 를 해주면 되는 것이다.
그럼 이제 2와 4의 경우를 보자
2 - [1 1] [2]
4 - [1 1 1 1] [2 1 1] [2 2] 가 가능하다 이것도 역시 2의 경우에서 2를 하나만 추가해도 가능하니까 본인의 1개에다가 2의 [1 1] [2] 에다가 2 하나씩 추가하면 되니까 1 + 2 인 것이다.
이렇게 그냥 들어온 순서대로 dp[inputNumber]++ 해주고 그 다음에 그냥 dp로 싹 돌리면서 dp[i] + dp[i - inputNumber] 를 해주면 다 구할수가 있는 것이다.
for문으로 사이즈만큼 돌면서 구할 수 있다
그래서 결론은 for문으로 돌아가면서 일단 dp[inputNumber]++ 를 해주고 dp[i] + dp[i - inputNumber]를 해준다.
그 다음에 0 + inputNumber에서 부터 시작해준다.

-- 결과
이 문제의 전제조건 자체가 동전 하나하나씩 풀어나가는 것이다.
일단 처음에는 들어오는 동전의 가치로만 채울 수 있는 것들을 만들어 놓는다.
예를들어서 1 2 5 가 들어오고 3 5 일 때의 경우로 설명을 하면
1만 만들었을 때
1 - [1]
2 - [1 1]
3 - [1 1 1]
4 - [1 1 1 1]
5 - [1 1 1 1 1] 이런식으로 만들 수가 있다 이 경우에는 어떤식으로 하면 되냐면
일단 inputNumber로 1이 들어오게 되면 dp[inputNumber]++ 를 해준다 즉 1을 선택해서 [1]의 경우를 만들어주는 것이다.
그 다음 부터는 무조건 dp[i] + dp[i - inputNumber] 로 이루어진다. 이 의미는 현재 숫자에서 지금 inputNumber 만큼 빼주면은 무조건 본인이 가지고 있는 경우의 수 + [dp[i - inputNumber].list + inputNumber]의 형식이 되기 때문이다.
예를 들어서 2를 보면 1 - [1] 인 것을 봐서 여기에다가 1만을 추가하면 되니까 2 - [1 1] 3은 2의 [1 1]에서 1을 하나 더 추가하니까 3 - [1 1 1] ... 이런식으로 쭉 가는 것이다.
이제 2를 투입하면
1 - [1]
2 - [1 1] [2] - 2를 추가
3 - [1 1 1] [1 2]
4 - [1 1 1 1] [1 1 2] [2 2]
5 - [1 1 1 1 1] [1 1 1 2] [1 2 2] 이런식으로 이루어질 수가 있음
그러면 이 경우에서도 순서대로 설명을 하면 일단 dp[inputNumber == 2]++를 해주면서 [2] 라는 경우를 추가해서 [1 1] [2] 가 되었음 이제 3부터 살펴보자
3은 [1 1 1]로만 이루어져 있다 여기에서 2를 추가해서 얻을 수 있는 방법은? 당연히 1을 선택하는 것이 최선이다. 그래서 [1 1 1] [1 2] 가 되는 것이다.
4는 ? [1 1 1 1]로만 이루어져 있다 , 2를 선택하면 [1 1 2] [1 2]가 추가되게 된다 그러면 [1 1 1 1] [1 1 2] [2 2]가 되게된다.
5는 ? [1 1 1 1 1]로만 이루어져있다. 3을 선택하면 [1 1 1 2] [1 2 2] 가 추가되게 된다. 그러면 [1 1 1 1 1] [1 1 1 2] [1 2 2] 가 되는 것이다.
이런식으로 5도 진행되기 때문에 이런식으로 구할 수 있는 것이다.
5도 구해보면
1 - [1]
2 - [1 1] [2]
3 - [1 1 1] [1 2]
4 - [1 1 1 1] [1 1 2] [2 2]
5 - [1 1 1 1 1] [1 1 1 2] [1 2 2] [5] 가 된다. dp[inputNumber == 5]++ 를 해서 [5]가 추가된 것이다.
이런식으로 inputNumber로만 이룰 수 있는 가장 작은 숫자에다가 하나의 경우의 수를 추가해주고 나머지는 본인이 선택할 수 있는 최고의 경우의 수인 dp[i - inputNumber]를 선택함으로써 모든 경우의 수를 구할 수 있는 것이다.

그리고 마지막으로 설명하면 dp[InputNumber]++ 만 해주는 이유는 이거를 해주면 짜피 그 다음 inputNumber로만 구성 가능한 것들은 인덱스 inputNumber가 가지고 있는 것에다가 inputNumber 를 추가하면
inputNumber 로만 구성된 경우가 생기기 때문에 dp[inputNumber]만 ++ 해준 것이다
-- 해맸던 점
inputNumber가 k 보다 큰 경우를 생각안했고
그리고 반복문에서 i , j를 또 잘못썼었음
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[] dp = new int[k + 1];

        for(int i = 0; i < n; i++){
            int inputNumber = Integer.parseInt(input.readLine());
            if(inputNumber > k){
                continue;
            }
            dp[inputNumber]++;
            for(int j = inputNumber + 1; j <= k; j++){
                dp[j] = dp[j] + dp[j - inputNumber];
            }
        }
        System.out.println(dp[k]);
    }
}