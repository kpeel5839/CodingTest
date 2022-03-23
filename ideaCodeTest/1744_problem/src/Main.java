import java.util.*;
import java.io.*;

// 1744 : 수 묶기

/*
-- 전제 조건
길이가 N인 수열이 주어졌을 때 , 그 수열의 합을 구하려고 한다.
어떤 수열이 주어졌을 때 ,
이 수열의 합을 구하면 그냥 구할 수 있지만
어떠한 수들끼리 묶기 시작하면 더 큰 값을 구할 수가 있다.

수열의 모든 수는 단 한번만 묶거나 아니면 묶지 않아야한다.

수열이 주어졌을 때 , 수열의 각 수를 적절히 묶었을 때 , 합이 최대가 되게 하는 프로그램을 작성하시오.
-- 틀 설계
일단 내 생각은 양수 , 음수로 나누고
그 다음에 음수는 0까지 포함을 시킨다.

그 다음에 priorityQueue 로 음수는 오름차순
그리고 양수는 내림차순으로 진행해준다.

그 다음에 음수를 먼저 다 빼면서
queue.size < 2 가 되면 그냥 queue 에서 빼면서 다 더해주고

아니면 그냥 2개씩 빼면서 곱해준다.

그러니까 0과 곱해지거나 아니면 음수끼리 곱해져서 +가 된다.

그리고 양수는 내림차순으로 진행하는데
이것도 queue.size < 2 가 되면 그냥 queue 에서 빼면서 더해주고 , 만일
빼는데 1이 포함이 되어있다면 이것들은 곱하면 안되고 , 더해주면 된다.
1 과 곱하면 더 작아지기 때문이다.
1 * 2 = 2
1 + 2 = 3
이런식으로 1이 포함이 되어있다면 더하는 것이 났다.
근데 곱이고 1이 포함되어 있지 않다면?

무조건 곱하는 게 낫다.
양수이니까

0은 음수에 포함시켜서 괜찮다.

-- 결론
너무 이상하다.
시간을 2초나 주길래
내가 생각한 방법이 틀린 방법일 까
생각을 했었다.
근데 아무리 생각해도 반례를 생각하려고 해봐도
맞다라는 결과밖에 나오지 않는 상태에서
혹시나 하는 상태에서 코드를 짰다.
근데 그냥 맞았다. 왜 2초나 준것일까?
 */
public class Main {
    public static int N , res = 0;
    // o2 - o1 은 o1 이 this , o2 가 other 이다. o1 이 더 큰 경우에는 음수값이 나와야 하니까
    // o2 - o1 일 때 o1 이 더 크면 음수이고 더 작으면 양수가 반환이 된다.
    public static PriorityQueue<Integer> negative = new PriorityQueue<>() , positive = new PriorityQueue<>((o1 , o2) -> o2 - o1);
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(input.readLine());

        for(int i = 0; i < N; i++){
            int number = Integer.parseInt(input.readLine());

            if(number > 0) positive.add(number);
            else negative.add(number);
        }

        while(true){ // 음수를 먼저 빼주면서 더 해줄 것이다.
            // 2개씩 빼주는데 negative.size < 2 이면 그냥 빼주고 끝낸다.

            if(negative.size() < 2){
                if (!negative.isEmpty()){
                    res += negative.poll();
                }
                break;
            }

            else{
                res += negative.poll() * negative.poll();
            }
        }

        while(true){ // 양수를 빼줄 것이다.
            if (positive.size() < 2) {
                if(!positive.isEmpty()){
                    res += positive.poll();
                }
                break;
            }

            else{
                int a = positive.poll();
                int b = positive.poll();

                if(a == 1 || b == 1){ // 둘 중 하나라도 1 이 포함되어 있으면 그냥 더함
                    res += a + b;
                }
                else{ // 1이 포함되어 있지 않으면 곱한다음 더함
                    res += a * b;
                }
            }
        }

        System.out.println(res);
    }
}
