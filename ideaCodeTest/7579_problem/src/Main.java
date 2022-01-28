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
--틀설계
일단 예제를 먼저 보자
5 60
30 10 20 35 40
3 0 3 5 4
이다 , 여기서 60 바이트를 비워내야한다 , 어떻게? 다시 비활성화 할 때의 비용이 가장 적도록 해야한다.
여기서의 답은 6 이다 , 그럴려면 30 10 20 이렇게 비워내야한다.

일단 M값이 10000000 까지 있다 , 이 값인 1000만이며 , 이 값으로 dp를 접근해선 안될 듯 하다.
그럼 이것도 비활성화를 어떻게 하냐 이것이 관건이다...
일단 무지성으로다가 2차원 배열로 접근해보자.

일단 먼저 해당 바이트 이상의 메모리를 비워야 할 때의 최소 값을 구해보자.

10 - [10] - 0
20 - [20] , [10 20] ... - 3
30 - [10 20] , [10 30] , [30] - 3
40 - [10 30] - 3
50 - [10 40] - 4
60 - [10 20 30] - 6
70 - [10 40 20] , [10 40 30] -7
... 이렇게 하면 최대 135까지를 구할 수 있다라는 것이다. 일단 이 합을 넘어간다? 그러면 불가능 하다.
근데 M은 무조건 m1 + m2 .... + mn 이라서 무조건 안되는 값은 들어오지 않는다.

만일 근데 1000만에 100이 최대이니 , 최대 회수 10억이다 , 10억 번은 안된다.
그러니까 dp[m][n] 이런 형식으로는 죽어도 안될 것 같다.

연산 10억번 , 은근 1초는 안 걸리는 것 같다.
일단 10억번 연산으로 한번 접근해보자.

여기에서는 쉽게 10 ~ 60까지로 접근해보자

x      10  20  30  40  50  60

10(0)  0   x   x   x   x   x

20(3)  0   3   3   x   x   x

30(3)  0   3   3   3   6   6

40(4)  0   3   3   3   4   6

35(5)  0   3   3   3   4   6

이것을 보고 알 수 있는 사실
일단 List를 만들고 , 처음에는 value 에다가 집어넣고 , 그 다음에는
cost 에다가 집어넣는다.
그런 다음에 Comparable 을 implements 해가지고 정렬을 해준다.
그 다음에 1 2 3 4 .... 최대 1000만 까지 가는데 이 때
1 배열마다 모든 리스트의 값들을 접근해주는 것이다.

그래서 실제로는 저런 2차원 배열의 형태로 진행을 하지만 실제로는
그냥 1차원 배열로 진행할 수 있도록 해주는 것이다.
이런식으로

동전문제 처럼 모든 값들로 다 훑고 지나가면서 이 값들을 채워주는 것이다. 일단 처음에는 모든 배열들을 -1로 채워넣는다. (아직 채우지 못한 곳을 표시하기 위해서)
(x 는 -1 라고 생각하면 됨)

점화식은 일단 dp[list.value] 부터 시작해서 , max(list.cost + dp[i - list.value] , dp[i]) 이런식으로 가면 될 것 같다.
그리고 해당 숫자가 그 다음 숫자를 채울 수 없는 경우에 dp[i - list.value] = -1 이면 진행을 못한다 왜냐하면 아직 이 값은 채워지지가 않았으니까 이런 경우는 continue한다고 일단 생각해보자.

- 10을 진행할 때
0 10 20 30 40 50 60
0 0  x  x  x  x  x

- 20을 진행할 때
0 10 20 30 40 50 60
0 0  3  3  x  x  x

- 30을 진행할 때
0 10 20 30 40 50 60
0 0  3  3  3  6  6

- 40을 진행할 때
0 10 20 30 40 50 60
0 0  3  3  3  4  6

- 35을 진행할 때
0 10 20 30 40 50 60
0 0  3  3  3  4  6

자 그러면 이렇게 진행하는데
이제 점화식은 이렇게 세우면 된다.
max(list.cost + dp[i - list.value] , dp[i]) , 근데 여기서 중요한 것이 있음.
무조건 1 에서 부터 시작해야하는데 , 지금까지 현재 나온 list.value 들의 합까지 진행을 한다 에를 들어서 10 , 20 이 나왔다 그러면 20 이 진행할 때에는 30까지만 하는 것임 왜냐 , 거기까지 밖에 진행을 못하니까 지금까지 나온 합으로는
그래서 그렇게 까지만 진행을 하고 , list.value 가 현재 인덱스보다 크다 그 때에는 max(dp[i] , list.cost) 이런식으로 가다가 넘어가면 이제 그 때부터 그렇게 가야하는 것이다.

그래서 일단 n을 받아서 그만큼 2번을 진행한다.
그리고 처음에는 list.value로 list에다가 생성하고
그 다음에 돌 때에는 list.get(j) 해서 cost에다가 집어넣은 다음에 list를 정렬한다.

그 다음에 이제 반복문을 돌면서 위에서 말한 것과 같이
0은 무조건 0 이다. 비울 필요가 없으니까
for(int i = 0; i < list.size(); j++) for(int j = 1; j <= sum; j++) 이런식으로 진행하면서
게속 나오는 것들을 더해준다 그 다음에 거기까지 진행하는 것이기 때문에 , 그리고 if(list.value >= j) Math.max(dp[i] , list.cost); 로 진행해주고
else 인 경우에는 Math.max(list.cost + dp[i - list.value] , dp[i]) 를 해서 여기서 나온 값을 최종적으로 dp[j] 에다가 넣어주면 되는 것이다.
 */
public class Main {
    public static class Memory implements Comparable<Memory>{
        int value;
        int cost;
        public Memory(int value){
            this.value = value;
        }
        @Override
        public int compareTo(Memory other){
            if(this.cost > other.cost){
                return 1;
            }else{
                return -1;
            }
        }
        @Override
        public String toString(){
            return "value : " + value + " cost : " + cost;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        List<Memory> memoryList = new ArrayList<>();
        int sum = 0;

        for(int i = 0; i < 2; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < n; j++){
                if(i == 0){
                    int value = Integer.parseInt(st.nextToken());
                    sum += value;
                    memoryList.add(new Memory(value));
                }else{
                    memoryList.get(j).cost = Integer.parseInt(st.nextToken());
                }
            }
        }

        int[] dp = new int[sum + 1];
        Arrays.fill(dp , Integer.MAX_VALUE);
        Collections.sort(memoryList);

        sum = 0;

        for(int i = 0; i < memoryList.size(); i++){
            Memory memory = memoryList.get(i);
            sum += memory.value;
            for(int j = 1; j <= sum; j++){
                if(memory.value >= j){
                    dp[j] = Math.min(dp[j] , memory.cost);
                }else{
                    dp[j] = Math.min(dp[j] , memory.cost + dp[j - memory.value]);
                }
            }
        }

        System.out.println(dp[m]);
        int min = Integer.MAX_VALUE;
        for(int i = m; i <= sum; i++){
            min = Math.min(dp[i] , min);
        }
        System.out.println(min);
    }
}
