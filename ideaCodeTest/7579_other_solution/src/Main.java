import java.util.*;
import java.io.*;

// 7579 : 앱
/*
--전제조건
우리는 앱을 사용하면서 화면에 보이는 실행 중인 앱은 하나뿐이지만 보이지 않는 상태로 많은 앱들이 활성화 되어있다.
앱들이 활성화가 되어있다는 것은 화면에 보이지 않더라도 메인 메모리에 직전의 상태가 로드 되어있다는 것을 의미한다.
현재 실행 중이 아니더라도 이렇게 메모리에 남겨두는 이유는 나중에 사용자가 다시 이 앱을 불러올 때 직전의 상태를 메인 메모리로부터 읽어 들여오기 위해서이다.
근데 이제 스마트폰의 한계가 있다보니 너무 많은 앱들이 활성화 되어있으면 메모리가 부족하다
그렇기 때문에 임의로 정해서 앱들을 비활성화 시킨다.(메모리에서 삭제)
메모리 부족상황에서 활성화 되어 있는 앱들을 무작위로 필요한 메모리만큼 비활성화 하는 것은 좋은 방법이 아니다.
그래서 효율적으로 메모리에서 제거하여서 비활성화로 만들어놓아야한다.
현재 n개의 앱 A1 .... An 이 활성화 되어있다고 가정했을 때 이들 앱 Ai는 각각 Mi 바이트만큼의 메모리를 사용하고 있다.
또 앱 Ai를 비활성화한 후에 다시 실행하고자 할 경우 추가적으로 들어가는 비용을 수치화 한 것을 Ci라고 하자
그러니까 Ai 는 Mi , Ci를 가진 것이다 Mi = 메모리를 차지하는 용량 , Ci = 다시 활성화 시킬 때의 오버헤드
이러한 상황에서 사용자가 새로운 앱 B를 실행하고자 하여 , 추가로 M 바이트의 메모리가 필요하다고 했을 때
현재 활성화 되어 있는 앱 A1 .... An 중에서 몇개를 비활성화 하여 M 바이트 이상의 메모리를 추가로 확보해야 하는 것
이제 이 경우에서 M바이트를 확보하면서 가장 적은 Ci 의 합을 가지는 방법을 찾아야한다.
M 은 무조건 총합보다 작거나 같다 (그래야지 확보가 가능하니까) 근데 같을 수도 있다는 것은 모두를 비활성화 할 수도 있다는 것
그래서 결국 n이 주어지고 m이 주어졌을 때 m만큼 공간을 확보하면서 , Ci 의 합이 가장 적은 방법의 Ci의 합을 출력하라
-- 틀 설계
dp[i][j] 를 선언한다.
i 번째 앱까지 사용하였을 때 j 비용으로 확보할 수 있는 최대 메모리를 의미한다.
그렇기 때문에 이것의 값을 정할 때에는 dp[i][j] = dp[i - 1][j - cost] + memory.value , dp[i - 1][j] 이 경우 중에서 선택하는 것이다.
그래서 앱을 0번째까지 사용했을 때 부터 끝까지 사용하였을 때 최대 값을 구할 수 있는 것이다.
답은 그냥 끝 라인 즉 dp[n - 1][j] 들에서 찾을 수 있겠지만 그냥 바로바로 m 을 넘으면 결과값으로 저장하면서 진행한다.

dp[i][j] 의 전제의 이유는 일단 i번째 입력의 memory 까지 썼을 때 j 비용으로 낼 수 있는 최대의 메모리 비용이다.
그러니까 cost 별로 최대 메모리를 점할 수 있는 것이다.
그 최대 메모리 값을 구하기 위해서는 dp[0][0 번째 입력의 코스트 값 이전 것들] = memory 이런식으로 가야함

일단 가정 자체를 dp[i][j] i 번째를 돌 때 cost[i] 의 값보다 큰 j 값들은 무조건 넣어주는 것이다. 왜냐하면 최소 j 비용으로 확보 가능한 메모리 용량을 나타내기 때문이다 이렇게 가정하고 들어가게 되면
dp[0][3] 부터는 30 으로 이어지고
dp[1][0] 부터 dp[1][2] 까지 10으로 가게 되고 , 이제 dp[1][3] 부터 비교를 하게 되는데 dp[0][3] 의 값이 존재하니 dp[0][3 - cost(0) == 3] + memory , dp[0][3]) 이냐 를 판가름 하게됨
근데 dp[0][3 - 0] + memory , dp[0][3] 은 비교 필요도 없을 정도로 전자가 더 크다. 이런식으로 계속 이어서
dp[1][4] = dp[1][4 - cost(3)] + memory , dp[1][4] 이런식으로 계속 가게되고 , 이것도 또한 costSum 까지 가게 된다.
이거를 모든 n 을 반복하게 되면 dp[n - 1][j] 중에서 m 보다 큰 것 중에 j 가 가장 작은 것이 답이다.

원래 같으면 dp[0][0 번째 입력의 cost] 만 memory 값을 넣는게 맞다. 왜냐하면 dp[0][0번 째 입력의 cost] 이것만이 얘가 해당 비용으로 구성할 수 있는 memory니깐
하지만 조금 더 넓혀서 생각해보면 , 그냥 비용을 어거지로 아무것도 없이 더 한다고 하더라도 dp[0][0번 째 입력의 cost 이후] 가 memory 까지는 비용을 구성할 수 있다는 사실은 변하지 않는다.
근데 물론 이대로 그냥 가게되면 당연히 틀린다 , 하지만 이렇게 넓혀서 생각한 이유는 다음 입력의 dp[1][1번 째 입력의 cost 이후] 에서도 그 비용을 처리하기 위해서 더 넓혀서 생각하게 된 것이다.
왜냐하면 만일 dp[0][4] = 30 이고 1번 째 입력이 cost가 0이고 , 메모리는 10이라고 가정하자 , 그러면 dp[0][4 - 0] + 10 , dp[i - 1][j] 이 둘 중에 하나를 선태개향 할 것이다.
본인을 추가하고 , 본인의 cost 만큼 뺀 비용에서의 최대 메모리양을 찾아서 더하는 것이다, 혹은 이 경우 , 즉 본인이 추가 되는 경우보다 본인이 추가되지 않는 경우가 , 더 나은 경우가 존재한다 , 그 경우에는 dp[i - 1][j] 를 선택하게 되는 것이다.
그리고 해당 i 번째 입력의 cost가 현재 j 인덱스보다 크다 , 그러면 이 memory는 dp[i][i 번째 입력의 cost보다 작은 값들] 여기에 죽어도 추가 될 수가 없는 것이다.
그러면 이전에 최대 값으로 찾아놓은 dp[i - 1][j] 들을 그냥 dp[i][j] 에다가 집어넣는 것이다.
그래서 이러한 점화식이 나오게 되는 것이다 j >= cost[i] 이 경우에만 dp[i][j] = Math.max(dp[i - 1][j - cost[i]] + memory[i] , dp[i - 1][j]) 이렇게 이제 본인을 낄 수 있게 되었을 때 이러한 점화식을 적용하고
본인이 끼지 못하는 상황인 j < cost[i] 즉 cost[i] 가 더 큰 상황에는 dp[i][j] = dp[i - 1][j] 이전의 최대 값을 가져와야 하는 것이다.

그리고 dp[0]번 째 인덱스는 전처리 과정이 전혀 없으니 if(j >= cost[i]) 이 경우에 dp[i][j] = memory[i] 이렇게 해주면 되는 것이다, 처음에는 그냥 무조건 본인이 포함 된 것이 최대값이니까 비교할 필요도 없는 것이다.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int ans = Integer.MAX_VALUE;
        int[][] dp;
        int[] cost = new int[n];
        int[] memory = new int[n];
        int costSum = 0;

        for(int i = 0; i < 2; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < n; j++){
                if(i == 0){
                    memory[j] = Integer.parseInt(st.nextToken());
                }else{
                    cost[j] = Integer.parseInt(st.nextToken());
                    costSum += cost[j];
                }
            }
        }

        dp = new int[n][costSum + 1];

        for(int i = 0; i < n; i++){
            int co = cost[i];
            int me = memory[i];
            for(int j = 0; j < costSum + 1; j++){
                if(i == 0) {
                    if(j >= co) dp[i][j] = me;
                }
                else{
                    if(j >= co) dp[i][j] = Math.max(dp[i - 1][j - co] + me , dp[i - 1][j]);
                    else dp[i][j] = dp[i - 1][j];
                }

                if(dp[i][j] >= m) ans = Math.min(j , ans);
            }
        }

//        for(int i = 0; i < costSum; i++){
//            if(dp[n - 1][i] >= m) {
//                System.out.println(i);
//                break;
//            }
//        }
        System.out.println(ans);
    }
}
