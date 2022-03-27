import java.util.*;
import java.io.*;

// 2342 : Dance Dance revolution

/*
-- 전제 조건
DDR 을 하는 것을 좋아한다.
여기서 특이한 점은 , 시작을 제외하고 , 2 발이 한 지점에 있으면 안되는 것이다.
그리고 가운데에서 상하좌우로 이동하는 경우에는 2의 비용이들고 ,
오른쪽에서 위 , 아래와 같은 가운데가 아닌 곳에 위치해 있다가 , 인접한 곳으로 발을 옮길 때에는
3의 비용이 든다 , 그리고 같은 곳을 연속으로 누를 때에는
1의 비용이들고 , 반대편으로 발을 옮길 때에는 4의 비용이 든다.
이렇게 되었을 때 , 쳐야하는 발판의 리스트가 주어졌을 때 , 최소 비용을 출력하라.
-- 틀 설계
처음에는 3차원 배열을 만들어서 현재 발 위치와 , 이동해야 할 위치가 주어진다면
[i][j] 현재 발 위치 왼쪽발 부터 순서대로 , 그리고 [c] 는 c 로 이동할 때 , 최소비용을 저장하려고 했었다.
그러니까 dp[i][j][c] 의 경우는 (i , j) -> c 로 이동할 떄 , 최소비용이다.
근데 , 이런 경우에 선택지가 생긴다. c로 옮길 때 , 만일 i , j 의 발 위치가 2 , 4 이고
이동해야 할 위치가 3인 경우 둘다 (c == 3 , i == 2 , j == 4) 인 경우 둘의 비용이 다 3이다.
그러면 두 발이 다 움직일 수 있는 것이다.
이 경우를 해결해야 한다.

그리고 혹은 분명히 이런 경우도 있을 것이다.
현재 해당 발이 움직였는데 , 그 다음 위치가 해당 발이 있던 위치인 경우,
이런 경우는 1의 비용으로 해결 할 수 있던 상황을 놓친 것이다.

그냥 그리디하게 놓는 경우를 생각해보자.
숫자가 1 , 2 , 3 , 2
이렇게 주어진다고 가정했을 떄
(1 , 2)인 상태에서
2 가 3과 가까우니까 1 -> 3 이 아닌 2 -> 3 이 되어서
(1 , 3) 이 될 것이다.
그 다음에 2 를 했을 때 3
그래서 결론적으로 2 + 2 + 3 + 3 == 10 이된다.

근데 만일 1 -> 3 으로 갔었다면?
2 + 2 + 4 + 1 = 9 이다.
즉 , 같은 곳을 누르게 되는 경우 이 경우가 올 수 있기 때문에
그리디한 선택은 안된다.

그렇다고 배열을 2차원 배열로 만들어서 관리하는 배낭문제 식으로 풀면?
그것도 안될 것 같다
왜냐하면 10만 * 10만 은
10,000,000,000 즉 100억이다.
이것은 될리가 없음

애초에 배열 만들 때에도 Stack Overflow 난다.
그렇다라는 것은 내 생각에는 1차원 배열로 해결하는 방법밖에 없다.

1차원 배열로 어떻게 해결 할 수 있을 까
1 2 3 2 가 있다고 했을 때
두가지의 경우가 존재한다라는 것은 이미 위에서 증명해냈다.

그러면 그냥 한번 최소 비용으로만 정리해보자 (배열의 값을)

1 2 3 2
2 4 7 9

이런식으로 정리된다 , 3으로 갈 때에는 당연하게 2 -> 3 으로 가는 것이 현재로서의 비용은 우세이다.
하지만 2로 진행할 때에는 1 -> 3 , 2 -> 2 이 상황이 우세이다.
그래서 배열로 정리하면 저렇게 되게 된다.

결국 해답을 보았다 , 이것은 진짜 내가 풀 수 없었던 문제였던 것일까?
생각해보면 아닌 것 같다.
나는 어느정도 결과에 이르렀었다 , 하지만 지금 현재 내 상태가 안좋기 때문에 현실과 타협했던 것이 아닐까
이 문제를 풀고나서 카페에가서 , 조금 더 경건한 마음가짐으로 진행해야 할 것 같다.

내가 생각했던 것과 비슷하다,
dfs 함수를 만들고 , 거기에서는 현재 dp[i][j][c] , 여기서 cnt는 현재 진행 번째임 , 주어진 수열에서 몇번쨰를 진행하고 있냐이다.
그래서 dp[i][j][c] 를 모두 -1 로 만들어서 , 방문처리가 필요 없도록 하고,
dp[i][j][c] 가 != -1 이라면 이미 방문한적이 있고 , 해당 지점은 이미 최소값으로 갱신이 된 상태이기에,
이 값을 return 하면 된다.

그래서 이런식으로 계속해서 dp[i][j][c] = Math.min(dfs(move(왼쪽 발 , 갈 위치) , 오른쪽 발 , 수열 인덱스 + 1) ...) 이런식으로 게속 진행하면서
현재 dp[i][j][c] 를 이전 시작들에게 넘겨주면 된다 , 그러면 결국 dfs 호출 결과가 정답이 될 것이다.

결국 맞았고 , 나는 해답 코드를 보고 해석하는데 1분 정도밖에 걸리지 않았다 , 그렇다라는 것은
나도 풀 수 있었다라는 것이였다.

주말에는 너무 하기가 싫어서 그런 것일까
실제로 하면 괜찮은데 , 가끔 생각이 잘 안될때가 확실히 있는 것 같다. 
 */
public class Main {
    public static int size;
    public static int[] arr;
    public static int[][][] dp;
    public static int move(int sta , int des){
        /*
        현재 발과 , 목표 위치가 주어지면
        값을 구할 수 있다.

        일단 Math.abs(sta - des); 값을 구하고
        해당 값이 0 이면 return 1
        1 || 3 이면 return 3
        이 값이 2 이면 return 4
        만일 sta 가 0 이면 그냥 return 2로 바로 해주면 된다.
         */
        if(sta == 0) return 2;
        int number = Math.abs(sta - des);
        if(number == 0) return 1;
        if(number == 1 || number == 3) return 3;
        return 4;
    }
    public static int dfs(int left , int right , int index){
        /*
        왼쪽 발 , 오른쪽 발이 주어지면
        해당 left 혹은 right 를 넘기면서 dp처리를 해주면 되는데,
        그것은
        dp[left][right][index] = Math.min(dfs(arr[index] , right , index + 1) + move(left , arr[index])
        , dfs(left , arr[index] , index + 1) + move(right , arr[index]);
        이렇게 할 수 있다 그리고 size == index 라면 return 0 을 해주고
        그 다음에 , 이렇게 dp[left][right][index] 를 값을 결국 구하게 되면 반환하면 된다.

        그리고 당연하게도 , 여기서만 보더라도 2가지 방향으로 분기된다
        그렇기 때문에 , dp[left][right][index] 가 많이 반복될 수밖에 없다
        그래서 if(dp[left][right][index] != -1) 을 통해서 방문처리도 가능하다.
         */
        if(index == size) return 0;

        if(dp[left][right][index] != -1) return dp[left][right][index]; // 이미 이런식의 방문을 진행한 상황

        dp[left][right][index] = Math.min(dfs(arr[index] , right , index + 1) + move(left , arr[index]) , dfs(left , arr[index] , index + 1) + move(right , arr[index]));

        return dp[left][right][index];
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        size = st.countTokens() - 1;
        arr = new int[size];
        dp = new int[5][5][size];
        for(int i = 0; i < size; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                Arrays.fill(dp[i][j] , -1);
            }
        }

        System.out.println(dfs(0 , 0 , 0));
    }
}

