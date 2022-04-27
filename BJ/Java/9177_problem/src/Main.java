import java.util.*;
import java.io.*;

// 9177 : 단어 섞기
/*
-- 전제 조건
a , b 단어가 주어지고 , 주어진 순서대로 섞어도 된다.
그럴때 c 를 완성시킬 수 있으면 yes
아니면 no 를 출력해라

Data set n : yes or no 로 출력하면 된다.
-- 틀 설계
일단 가장 간단한 접근 방법은 당연하게도 queue 를 이용해서 두개의 포인터를 지정하고,
첫번째부터 지정을 해서 탐색을 해 나가면 된다.
근데 , 문제점은
a = tv
b = tc

c = tvtc

라고 가정하자 이건 근데 , 되는 단어이다.
하지만

a queue = t v
b queue = t c

t 여기서 만일 b queue 를 제거한다고 가정하면
앞으로 한칸 가게 된다.

그러면 v를 탐색할 차례인데 , 지금 포인터는 c 와 t 를 가르키고 있다.
그래서 여기서는 불가능이라고 나오게 되지만 , 실은 그게 아니다.

그래서 만일 같은 문자가 나왔을 떄 , 다른 문자이면 솔직히 상관할 바가 아니다 원래대로 진행하면 된다.
하지만 예외 사항인 같은 문자가 나왔을 떄를 어떻게 처리해야 할지 그게 관건이다.

그럼 같은 문자가 나왔을 때 두개의 방향으로 분기하면 되지 않을까?
그러고서 실패하면 이전에 두개의 방향으로 분기했던 그곳으로 돌아가는 것이다.
근데 바로 이전에 것으로 돌아가야 한다.

한번 해보자.
queue 가 아닌 그냥 재귀함수로 진행을 해보자.

-- 결과
역시나 다이나믹 프로그래밍 , 즉 방문처리를 해주니까 시간초과가 안났음
해당 문자를 이전에 탐색한적이 있나를 확인하고, 있다면 그냥 바로 리턴을 해주었음
왜냐하면 그 경우가 이미 끝까지 도달했었다라면 이미 success 가 되었을 것이고
아니라면 짜피 실패하는 경우니까 더 탐색할 이유가 없다.
 */
public class Main{
    public static int N;
    public static String a , b , c;
    public static boolean success;
    public static boolean[][] dp;
    public static void dfs(int aIdx , int bIdx , int cIdx){
        if(c.length() == cIdx){
            success = true;
            return;
        }

        if(dp[aIdx][bIdx]){
            return;
        }

        dp[aIdx][bIdx] = true;

        // 두 문자 다 동일하지 않을 경우
        // 그냥 공백을 하나 추가해줘서 a , b 뒤에다가 더 이상의 idx 가 진행되지 못하게 하였음
        if(a.charAt(aIdx) != c.charAt(cIdx) && b.charAt(bIdx) != c.charAt(cIdx)) return;

        // 이제 두 문자 다 동일하거나 , 한 문자라도 동일한 경우임
        if(a.charAt(aIdx) == b.charAt(bIdx)){ // 두개가 같은 경우에는 같은지 아닌지 살펴본다.
            dfs(aIdx + 1 , bIdx , cIdx + 1);
            dfs(aIdx , bIdx + 1 , cIdx + 1); // 두가지의 경우를 다 시도해준다.
        }
        else{
            if(a.charAt(aIdx) == c.charAt(cIdx)) dfs(aIdx + 1 , bIdx , cIdx + 1);
            else dfs(aIdx , bIdx + 1 , cIdx + 1);
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());

        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(input.readLine());

            success = false;
            a = st.nextToken() + " ";
            b = st.nextToken() + " ";
            // 공백문자를 추가해주면서 , StringOutOfIndex exception 에 대한 처리를 해주었음, 이러면 짜피 공백문자와 c는 일치하지 않으니까 , 사이즈에 대한 처리를 해줄 필요가 없음
            c = st.nextToken();

            dp = new boolean[a.length()][b.length()];

            dfs(0 , 0 , 0);

            if(success) output.write("Data set " + i + ": yes" + "\n");
            else output.write("Data set " + i + ": no" + "\n");
        }
        output.flush();
        output.close();
    }
}