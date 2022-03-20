import java.util.*;
import java.io.*;

// 1806 : 부분합

/**
 * -- 전제 조건
 * 순서대로 수를 선택하였을 때 , 합이 S 이상인 것 중에서
 * 가장 길이가 짧은 것을 출력하시오.
 * -- 틀 설계
 * 일단 배열을 받고서 , 해당 배열을 i , j 포인터로 가르킨다.
 * 그러면서 i는 계속 증가시키면서 , sum 을 저장한다.
 *
 * 그런 다음에 sum >= S 가 되면 뺄 수 있는 것들을 다 빼준다.
 * 근데 그것은 j 로 지정한다,
 *
 * 그래서 뺄 수 있는 것을 다 빼고 나서 i - j + 1 을 ans 와 Math.min 으로 비교해서 저장한다.
 * ans = Integer.MAX_VALUE 로 초기화 해놓고서 , 아얘 모든 지점을 돌았는데 ,
 * ans 가 Integer.MAX_VALUE 이다.
 *
 * 그러면 0을 출력하면 된다.
 * 그리고 j를 변동시키는 것은 그냥
 *
 * while(sum - arr[j] >= S) sum = sum - arr[j++];
 * 로 하면 될 것 같다.
 * 위의 식이 의마하는 것은 현재 합에서 arr[j] 를 빼도 s >= 이상이냐
 * 만약에 뺐을 때 s 이상이 아니면 빼면 안된다.
 *
 * 그리고서 이제 다 빼고 난 다음에 ,
 * if(sum >= s) 이면 ans = Math.min(ans , i - j + 1); 을 해주면 된다.
 *
 * 진짜 금방 맞았음
 */
public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int N = Integer.parseInt(st.nextToken()) , S = Integer.parseInt(st.nextToken());
        int ans = Integer.MAX_VALUE;

        int[] arr = new int[N];

        st = new StringTokenizer(input.readLine());
        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        long sum = 0;

        for(int i = 0 , j = 0; i < N; i++){
            sum += arr[i];

            // 아얘 숫자를 포함하지 않는 경우는 제외
            while(i != j && sum - arr[j] >= S) sum -= arr[j++];

            if(sum >= S) ans = Math.min(ans , i - j + 1);
        }

        System.out.println(ans == Integer.MAX_VALUE ? 0 : ans);
    }
}