import java.util.*;
import java.io.*;

// 2666 : 벽장문의 이동

/*
-- 전제조건
순서대로 벽장문을 열어야 할 때 ,
어떤식으로 가장 최소로 벽장문을 옮기고 문을 전부 열 수 있는 경우의 수를 출력해라.
-- 틀설계
그냥 완전 전형적인 dfs + 브루트 포스 문제인 것 같다.
일단 N 개의 배열을 선언하고
그 다음에 들어오는 입력으로 able 배열에다가 true 를 집어넣는다.

그 다음에 순서대로 들어오는 순서들을 받은 다음에
그 순서대로 index 값을 주어서 가능하게 끔 진행해야 하는데
해당 지점에서 옆으로 벽장 문을 밀려고 하는데 옆에 있어서 밀리지 않으면 재귀적으로 오른쪽으로 이동하면서 밀면 된다.

그럼 일단 현재 위치한 벽장 문을 기준으로 진행하고
문을 열 때마다 , index 를 증가시키는 방향으로 해야할 것 같다.
그리고 문을 밀 때 , 해당 지점을 true 로 하고 밀어넣은 지점을 false 로 진행하는데
그 때 , 꼭 끝나고 나면 돌려줘야 한다.

이런식으로 하면 구할 수 있지 않을까?

역시 뭔가 이상하다고 생각이 들었는데 브루트 포스 + dp 였음
조금 더 생각을 해봐야할 것 같다.

이 문제는 두개의 벽장만이 계속 열려있다라는 것
이것이 포인트이다.
이것을 내가 잡지 못해서 조금 못했던 것 같다.

두개만 비어있는 경우라면 , 일단 비어있는 곳을 체크하면서,
내가 현재 진행해야 할 지점을 비우는 것이 중요하다.
그렇기 때문에 , 비어있는 곳으로 벽장문들을 밀어야 한다.

그 때 , 어떤식으로 밀 수 있냐면 ,
그냥 다 한칸 씩 밀수가 있다.

그 경우 벽을 민 횟수를 계산 하는 방법은
a , b == 비어있는 벽장 number
Math.abs(a - 현재 비어야 하는 벽장)
Math.abs(b - 현재 비어야 하는 벽장)

로 구할 수 있다.
그리고 이렇게 하고 나면 , 당연하게도 위의 경우는 a 는 벽장문이 생기게되고,
b 가 비어있는 상태 , 아랫꺼는 반대이고 , 공통적으로 현재 비워야했었던 벽장문을 열려있는 상태이다.

그러면 이 점을 이용해서 , 굉장히 쉽게 구할 수 있다.
저렇게 두개의 횟수와 , 위의 횟수를 더하는 경우는 b 와 sequence[i] 가 비어있는 것으로 넘기면 되고
그리고 반대는 반대로 하면 된다.

그래서 최종적으로 모든 문을 열었을 경우,
즉 sequence 의 모든 것을 진행한 경우는 return 0를 해주면
역으로 타고 올라가면서 , 최소의 경우들만 골라낼 수 있다.

-- 결론
처음에 했던 생각과 같이 , 해당 지점을 방문이 가능토록 만드는 것이 이문제의 핵심이였음
근데 , 그 방법이 내가 생각했던 방법과 달리 실질적으로 visited 배열을 만들어서 하는 것이 아닌,
열려있는 방문들 2개를 기억하고 , 거기다가 현재 방문해야 하는 벽장을 열기 위해서 밀어넣는 방식을 통해서 진행하였고
그 연산을 Math.abs(열려있는 문 - 현재 문) 을 통해서 횟수를 쉽게 구해서 연산속도를 높여줌
 */
public class Main {
    public static int N , M;
    public static int[] sequence;

    // idx = 현재 벽장 , a , b == 열려있는 벽장
    public static int dfs(int idx , int a , int b){

        // 모든 벽장문을 다 연 상태
        if(idx == sequence.length) return 0;

        // a 에 벽장문을 밀어넣는 경우
        int v1 = Math.abs(a - sequence[idx]);
        // b 에 벽장문을 밀어넣는 경우
        int v2 = Math.abs(b - sequence[idx]);

        return Math.min(v1 + dfs(idx + 1 , sequence[idx] , b) , v2 + dfs(idx + 1 , sequence[idx] , a));
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());

        st = new StringTokenizer(input.readLine());
        int[] able = new int[2];

        // a
        able[0] = Integer.parseInt(st.nextToken());
        // b
        able[1] = Integer.parseInt(st.nextToken());

        M = Integer.parseInt(input.readLine());
        sequence = new int[M];

        for(int i = 0; i < M; i++){
            sequence[i] = Integer.parseInt(input.readLine());
        }

        System.out.println(dfs(0 , able[0] , able[1]));
    }
}