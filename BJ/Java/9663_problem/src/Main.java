import java.util.*;
import java.io.*;

// 9663 : N-Queen
/*
-- 전제조건
N * N 인 체스판 위에 퀸 N개를 서로 공격할 수 없게 놓는 문제이다.
N이 주어졌을 때 퀸을 놓는 방법의 수를 구하는 프로그램을 작성하시오.
-- 틀 설계
일단 백트래킹을 이용하여서 , 말을 놓고서 , 그 다음 말을 놓을 때부터 , 적절하게 제거하는 것이다.
일단 확실한 것은 한 줄에 하나씩 말을 놓아야한다.
그래서 무조건 depth 하나에 넣었다며 무조건 다음 depth로 들어가야 하는 것이다.
근데 이제 depth 가 1이다 , 그렇다면 이전 pos[i]들을 검사해야한다.

그럼 이제 솔직히 가로 세로는 너무나도 쉽다 , 하지만 퀸은 대각의 방향으로 움직일 수 있는 방향을 가지고 있다.
그렇기 때문에 , 대각을 생각해야 한다.
간단하게 체스판을 그려서 한번 진행해보자
그냥 간단하게 예측 할 수 있는데 pos[i] = value 이 것은 i는 row 를 의미 value 는 col 을 의미한다.
그러면 abs(row - i) == abs(col - value) 이래야한다.
0 1 2 3 4
1 x x o x
2 x o x x
3 o x x x
4 x x x o

이런식으로 있다고 가정하자 그러면 각각의 i는
pos[1] = 3
pos[2] = 2
pos[3] = 1
pos[4] = 4
라고 할 수가 있다
그러면 여기서 2 와 4 는 대각으로서 겹친다.
이 경우에는 abs(4 - 2) == abs(4 - 2) 이니 겹친다
이제 1과 2 3 이 겹친다
일단 먼저 2를 보면 abs(2 - 1) == abs(2 - 3)이다
그래서 결국은 dfs로 해결하는데 이런식의 조건문들로 제거하면 된다.
 */
public class Main {
    public static int n , ans = 0;
    public static int[] pos;
    public static void dfs(int depth){
        /*
        depth 는 row를 의미하고 pos[depth] 에 넣는 값은 그냥 col을 의미한다.
        그래서 일단 depth == 0 은 그냥 다 넣는다.
        그리고 나머지는 백트래킹으로 제거할 수 있는 것들은 제거 한다.
        그리고 짜피 이전에 것들만 조사하기 때문에 다시 원래상태로 복구 시킨다거나 그런일은 필요 없다.
        그리고 depth == n 이 되면 무조건 성공한 것이니까 ans ++ 를 해주면 된다.
        일단 for(int i = 0; i < n; i++) 이런식으로 pos 에 값을 넣어주면서 진행하면서
        depth == 0 일 때에는 그냥 조건문 필요 없이 진행한다.
        그 다음에 else 인 경우에는 for(int j = depth - 1; j != -1; j--) 를 검사하면서
        pos[j] == i 이거나 혹은 depth - j == abs(i - pos[j]) 이런식으로 진행하게 되면
        불필요한 경우들을 제거 할 수가 있다.
         */

        if(depth == n){
            ans++;
            return;
        }

        Loop:
        for(int i = 0; i < n; i++){
            if(depth == 0){
                pos[depth] = i;
                dfs(depth + 1);
            }else{
                for(int j = depth - 1; j != -1; j--){
                    if(pos[j] == i || depth - j == Math.abs(i - pos[j])) continue Loop;
                }
                pos[depth] = i;
                dfs(depth + 1);
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(input.readLine());
        pos = new int[n];

        dfs(0);

        System.out.println(ans);
    }
}
