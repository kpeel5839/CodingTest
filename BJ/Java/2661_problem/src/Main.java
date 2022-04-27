import java.util.*;
import java.io.*;

// 2661 : 좋은 수열
/*
-- 전제 조건
숫자 1 , 2 , 3 으로만 이루어진 수열들이 주어지고 , 임의의 길이의 인접한 두 개의 부분 수열이 동일한 것이 있다면
그 수열은 나쁜 수열이라고 부른다 , 만일 그렇지 않다면 , 좋은 수열이다.

나쁜 수열의 예는
- 33
- 32121323
- 123123213

좋은 수열의 예는
- 2
- 32
- 32123
- 1232123
이 있다.

길이가 N인 좋은 수열들을 N자리의 정수로 보아 그 중 가장 작은 수를 나타내는 수열을 구하는 프로그램을 작성하시오
1213121 , 2123212 두개 다 좋은 수열이지만 출력해야 하는 수열은 1213121 이다.

-- 틀 설계
가장 작은 수열을 출력해야 한다라는 것은
일단 가장 작은 수부터 차곡차곡 쌓아 올려서 , 이 수가 좋은 수열이 아니라면 , 다시 back 해서 그런식으로
순서대로 진행하는 것으로 보인다.

그렇다라는 것은 일단 규칙은 같은 숫자가 절대로 반복되어서는 안된다.
(이전 숫자랑 비교했을 때 , 그 수가 반복되면 안된다라는 의미)

그리고 추가되는 숫자로 인해서 발생하는 반복 수열을 찾아야 한다.
어떻게 할까
한번 나쁜 수열의 예를 하나하나씩 쌓아올려가면서 그 규칙에 대해서 찾아보자.

33 - 이건 당연히 같은 숫자가 반복되었으니 , 이런 상황을 만들지도 않을 것이다.

32121323 - 한번 가보자.

3 - 괜찮음
32 - 괜찮음 (이전과 같지 않다)
321 - 괜찮다.(이전과 같지 않음)
3212 - 괜찮다.(이전과 같지 않음)
32121 - 안 괜찮다(이전과 같음)
이것을 어떻게 검사할 수 있을까

일단 주의할 수 있는 점은 길이가 80밖에 되지 않는다.
그래서 for문으로 순차적으로 검사해도 되지 않을까?

for 문으로 검사할 길이를 선택한다. (80이라고 했을 때 , 최대 부분 수열의 길이는 40이다.)
그러면 for(int i = 1; i <= legnth() / 2; i++) 로 진행하면 될 것 같다.
왜냐하면 80이라고 가정했을 때 , 최대 길이가 40이고
79 라고 가정했을 때 , 최대 부분 수열의 길이는 39이다.
(부분 수열이니까 무조건 길이가 같아야하고)

그리고 길이만큼 맨 끝에서부터 (이 숫자가 추가가 되었기 때문에 , 검사를 진행하는 것 ,이전에 짤릴 것들은 다 짤려서 , 맨 마지막 숫자를 무조건 포함시켜야 함)

해당 길이만 큼 반복하는데 , for(int j = 0; j < i; j++) 이렇게 하고 , 각 위치는
현재 length() - 1 - (i - 1);
그리고 또 인접한 부분수열의 시작 위치는 1 이면 1칸 밀려나고 , 2이면 3칸 밀려나야 한다.
length() - 2 * i 이렇게 가면 될 것 같다.

한번 진행해보자.

너무 이상하리만큼 완벽하게 맞았다.
로직 짜느라 너무 스트레스 받았는데 개꿀딱
 */
public class Main {
    public static int N;
    public static int[] res;
    public static boolean finish = false;
    public static void dfs(int length){
        if(finish) return;

//        System.out.println(Arrays.toString(res));

        if(length == N){
            for(int i = 0; i < N; i++){
                System.out.print(res[i]);
            }
            finish = true;
            return;
        }

        if(length == 0){ // 길이가 0일 때에는 1 ~ 3 중 아무 숫자나 집어넣는다.
            for(int i = 1; i <= 3; i++){
                res[0] = i;
                dfs(length + 1);
            }
        }

        for(int i = 1; i <= 3; i++) { // 3 까지 숫자를 넣어보는 시뮬레이션을 함
            // 1 ~ 3 들어갈 수 있는 숫자인지 판단한다.
            res[length] = i;
            boolean able = false; // 현재 추가되는 문자가 들어가도 괜찮은지 판단한다.

            for (int j = 1; j <= (length + 1) / 2; j++) {
                able = false; // 계속 해당 사이즈의 문자열들을 검사할 때마다 갱신해준다.

                int a = (length + 1) - 1 - (j - 1);
                int b = (length + 1) - 2 * j; // 탐색을 시작할 위치를 지정한다.

                for (int c = 0; c < j; c++) {
                    if(res[a++] != res[b++]) {
                        // 같지 않으면 가능한 것이다
                        able = true;
                        break;
                    }
                }

                if(!able) break; // 만일 같은 것을 발견하면 나간다.
            }

            if(!able) res[length] = 0; // 다시 돌려놓는다 (안되니까)
            else dfs(length + 1); // 되면 다음 인덱스로 넘어간다
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(input.readLine());

        res = new int[N];

        dfs(0);
    }
}
