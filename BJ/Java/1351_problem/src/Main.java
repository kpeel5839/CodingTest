import java.util.*;
import java.io.*;

// 1351 : 무한 수열
/*
-- 전제 조건
무한 수열 A는 다음과 같다.
A0 = 1
Ai = A[i/p] + A[i/q] 인데
0 <= n <= 10 ^ 12
2 <= P , Q <= 10 ^ 9 이다.

이때 An 을 구해라.
-- 틀 설계
절대로 size == n 하게 배열을 선언해서 하는 방법은 절대로 아니다,
overflow 난다.

방법은 하나다.
n을 어떻게든 나눠서 , 0까지 만들어서 , Ai를 차츰차츰 구해나가면서 , 진행하는 것이다.
그리고 내림 표시이다.

그래서 해당 n을 두 방향으로 나눠서 진행하면 될 것 같다.

dp 를 계속 생각해서 , 어떻게 할 까 생각했다.
배열을 생각하니 사이즈가 너무 컸다, 별로 들리는 곳이 많지 않을 것 같은데 , 말이다.
근데 계속 생각해보니 , Hash 에다가 등록해서 하면 될 것 같다.
 */
public class Main {
    public static long n , p , q;
    public static HashMap<Long , Long> dp = new HashMap<>();
    public static long div(long number){
        long result = 0;

        if(number == 0){
            return 1;
        }

        if(dp.containsKey(number)) return dp.get(number); // 이미 들린 곳이라면 , 그냥 dp에서 뽑아서 반환

        result += div((long)Math.floor(number / p));
        result += div((long)Math.floor(number / q));

        dp.put(number , result); // 지금 number 의 반환 값은 result 임

        return result; // 처음 방문한 곳이면 dp 에다가 저장 후 값 반환
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        n = Long.parseLong(st.nextToken());
        p = Long.parseLong(st.nextToken());
        q = Long.parseLong(st.nextToken());

        System.out.println();
        System.out.println(div(n));
    }
}
