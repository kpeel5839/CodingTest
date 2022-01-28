import java.util.*;
import java.io.*;

// 14226 : 이모티콘
/*
-- 전제조건
영선이는 이모티콘을 S개 보내려고 한다.
이미 화면에 이모티콘 1개를 입력하였고 , 다음과 같은 3가지 연산만 사용해서 이모티콘을 S개 만들어 보려고 한다.
1. 화면에 있는 모든 이모티콘을 모두 복사해서 클립보드에 저장한다.
2. 클립보드에 있는 모든 이모티콘을 화면에 붙여넣기 한다.
3. 화면에 있는 이모티콘 중 하나를 삭제한다.
하나하나의 연산은 각각 1초가 걸린다 , 그리고 클립보드에 이모티콘을 복사하면 이전에 클립보드에 있던 내용들은
덮어쓰기가 된다. 일부만 복사 불가하고 , 비어있는 상태에서 붙혀넣기도 불가능 하다.
그냥 클립보드에 있는 것들 다 붙여넣기 해야하고 , 클립보드에 저장할 때에도 모든 이모티콘을 한번에 다 저장해야 한다.

영선이가 S개의 이모티콘을 화면에 만드는데 걸리는 시간의 최솟값을 구하는 프로그램을 작성하시오.

2가 주어지면
1번 연산(복사) -> 2번 연산(붙여넣기) ans : 2 이런식으로이다.

-- 틀 설계
확실한 것은 여기에서 연산은 3가지로 구분되지만
하지만 연산은 실제로 2가지라고 가정할 수가 있다
복붙이 즉 2배로 만드는 그 경우가 2초가 걸린다고 가정할 수가 있다
그래서 2배로 만드는 경우 , 그리고 -1 하는 연산들이 있다.

이것은 다이나믹 프로그래밍보다 , 다른 방법들이 더 생각난다.

일단 값을 2를 더해서 2배로 만드는 과정 , 그리고 거기서 -1 을 해서 + 1을 하는 과정
이것을 계속 반복해서 queue에다가 넣고 하다보면 , 무조건 답이 나올 것이다.

6 - 5 ? 6이 어떻게 5?
아 붙혀넣기를 여러번 가능하다
그러니까 3갈래이다.
6 - 2(2) -> 4(4) -> 6(5)

알고보니 붙혀넣기 하고서 그게 사라지는 것도 아니고 복사 한다음에 하나 뺐다가 하는 것도 가능하다
 */
public class Main {
    public static class Emoticon{
        int count;
        int copy;
        int time;
        public Emoticon(int count , int copy , int time){
            this.count = count;
            this.copy = copy; // 현재 복사해놓은 이모티콘 개수
            this.time = time;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(input.readLine());

        Queue<Emoticon> queue = new LinkedList<>();
        int[] dp = new int[n * 2 + 1]; // 오히려 n을 넘어서 -1 하는 경우가 더 빠를 수도 있기 때문에 , n * 2 + 1로 넉넉하게 잡아놓음 , 사실 이정도는 필요 x
        dp[1] = 1;
        queue.add(new Emoticon(2 , 1 , 2));
        //copy가 0이면 더 이상 붙여넣기 못한다.
        while(!queue.isEmpty()){
            /*
            내가 짠 코드 다시 분석해보자. 일단 다시 그 곳에 돌아돌아 방문하였을 때 time 값이 dp[count] 값보다 높게되면 , queue에 추가하지 않는다는 가정이 아니라
            아얘 방문했던 곳은 다시 방문하지 않는다는 철저한 bfs적인 개념이였음
            이제 그러면 왜 그런지 6을 찾는 과정을 한번 탐구해봐야 할 듯하다.
            이제 그러면 대충 10의 배열의 크기를 갖는 배열에서 순서대로 queue에다가 넣어서 탐색하는 과정 , 탐색하면서 dp에 들어가는 값들을 한번 살펴보자.
            확실한 것은 아얘 방문햇던 곳을 재방문 하지 않는다는 점에서 , 어떻게단 다른 경로를 통해서 , 즉 복붙으로 바로바로 오는 것이 아닌, 돌아돌아 오는 방법은 copy가 더 큰 상태로 와봤자이다 라는 개념으로 접근할 수 있다,
            애초에 copy가 더 큰 경우도 발생되지 않을 가능성이 크다라고 본다.
            1에다가 0집어 넣고 , new Emoticon(2 , 1 , 2) 으로 queue 가 시작함 편의 상 2 , 1 , 2 라고 그냥 칭하겠음

            1 2 3 4 5 6 7 8 9 10
            0 2 0 0 0 0 0 0 0 0

            결과 이러한 결과가 나오게 됨, 그럼 2에서 3개로 분기가 될 것임
            1 1 3
            3 1 3
            2 2 3
            이런식으로 분기가 되게 됨 그러면 이것들을 순서대로 실행해보자

            일단 3개를 먼저 실행시키고 , 그에 따라서 나오는 9개의 분기들과 , 몇몇 제거되는 분기들은 제거하자 , 일단 3개를 먼저 처리하자
            1 1 3 - 사실 1로 가면 안됨 , 왜냐하면 초기에 1로 시작했기 때문에 1의 값은 0이고 , 여기는 가는 순간 처음 출발한 곳이기에 재방문이 되는 것 , 고로 이것은 무효
            3 1 3 - 첫 방문임 , 그렇기 때문에 3으로 추가 됨
            2 2 3 - copy가 2인 상태로 분기 되지만 , 값에 들어가지는 않음 , 하지만 얘가 죽는다? 얘가 분기되지 못하고 죽는다면 6까지 도달했을 때의 값이 제대로 구해지지 않게 됨 , 왜냐하면 3 1 3 , 4 1 4 , 5 1 5 인데 copy가 2인 경우는 2 2 3 , 4 2 4 , 6 2 5 이기 때문이다.
            그래서 2 2 3은 죽지는 않았지만 dp[count] 에 들어가지는 않음
            1 2 3 4 5 6 7 8 9 10
            0 2 3 4 0 0 0 0 0 0

            분기된 것들 9가지 중에 3개는 나가 떨어져서 나감 (백트래킹 , 근데 그럼에도 불구하고 중복되는 것이 있음) (3차원 배열 만들어서 똑같은 거 나오는 거 2 , 1 , 4 나오면 [2][1][4] 에다가 1을 집어넣어서 방문처리해도 될 듯
            2 1 4 - 값 못 들어감
            4 1 4 - 값 들 어감
            3 3 4 - 값 못 들어감
            2 1 4 - 값 못 들어감 (같은 값이니까 여기서는 전제 조건을 보는 것이니 같은 것이 나오면 백트래킹으로 제거한다고 하고서 가자)
            4 2 4 - 값 들 어감
            1 2 4 - 값 못 들어감

            1 2 3 4 5 6 7 8 9 10
            0 2 3 4 5 5 0 0 0 0

            1 1 5 - 값 못들어감 다음부터 이런 터무니 없고 , copy 값도 작은 것은 바로 제거하겠음 , 내 생각이니까
            3 1 5 - 값 못들어감
            2 2 5 - 값 못들어감
            5 1 5 - 값 들어감
            3 1 5 - 값 들어감
            4 4 5 - 못 들어감
            6 3 5 - 값 들어감 , 여기서 끝남 짜피
            2 3 5 - 값 들어감
            6 2 5 - 값 들어감
            4 4 5 - x
            3 2 5 - x
            3 2 5 - x
            2 2 5 - x

            이렇게 전부 분석해본 결과 , 일단 한 턴 , 즉 queue가 한 사이클 돌아갈 때마다 시간초가 1씩 증가하게 되고 , 그에따라서 이전 queue 에 emoticon 들에 의해서 다양한 값들로 분기 된다,
            근데 이 값들은 분명 6 3 5 , 6 2 5 와 같이 분명히 살아 있다 , 근데 이런 경우는 살아있음 왜냐하면 이전 것들 즉 2 1 4 , 4 1 4 .... 이러한 것들이 형성될 때 실제로 dp에 들어가지 않으면서
            6 3 5 , 6 2 5 가 queue에 add가 되었다 , 즉 얘내들은 6 3 5 가 먼저 들어갔기 때문에 queue에다가 집어넣으면서 dp 에다가 집어넣었으면 답이 나오지 않았다는 이야기이다.
            그렇기 때문에 이번 초에 , 지금 현재 최대로 방문한 지점보다만 더 count가 높은 것들만 queue에다가 담게 되는 것이다 , 여기에서 내가 이전에 품었던 의문들이 풀리게 된다
            왜냐하면 , copy가 더 큰 것이 나중에 도착해서 queue에 집어넣어지지 않는 경우? 불가능하다 , 왜냐 , 이미 내가 최대 방문한 지점보다 더 간 지점의 것들이 queue에 담아져 있다 만일 이 상태에서
            예를 들면 너무나도 말이 안되는 상황만 에가 들어지기에 그냥 말로써 이해하면 6 3 5로 , 5초에 6의 지점에 방문하였다 , 근데 만일 3보다 큰 값을 copy값으로 가진 값이 6에도 도달하였다면 ? 이것은 절대로
            6 3 5 보다 크게 나아갈 수 없다 , 왜냐하면 6초일 때 나중에야 6에 도달한 것들은 copy가 최대 4일 것이다 , 사실 최대 4도 못찍는다 , 절대로 , 근데 1초가 더 지나게 되면 6 3 5 는 6 6 5가 되게 된다
            애초에 여기서부터 copy값이 차이가 나게 되는 것이다. 그렇기 때문에 똑같은 지점에 도달하는 경우 , 더 높은 copy 값을 가진 값이 , 그 경우는 고려할 필요가 없는 것이 이 이유이다 , 더 큰 값을 가진 값이
            이미 6을 차지하고 있기 때문이다 , 하지만 copy 연산에서는 다르다 , copy 연산에서는 말 그대로 copy하는 경우이기 때문에 무조건 이 경우에는 dp[count]가 채워져 있을 수밖에 없다 , 하지만
            3가지로 분기하기 위해서는 무조건 필요한 연산이니 , dp[count]를 고려하지 않고 , (즉 이미 방문한 지점이여도 copy는 가능하도록) queue에다가 넣어주게 된다. 그러면 아까와 같은 4 4 5 , 6 3 5 이런 경우가
            가능하게 되는 것이다 , 그렇게 되면 9 3 6 , 8 4 6 이러한 다양한 값들이 존재할 수 있게 되며 , copy를 통한 time을 최저로 잡는 연산이 가능하게 된다

            애초에 근데 time이 1초씩 증가하고 , 거기에서 가능한 count들이 나오게 된다 , 그렇기 때문에 새로 방문하는 곳 , 그곳은 무조건 해당 time이 최저 값이 될 수밖에 없다.
            그 증거로 아래에 while 문 안에 emoticon.time 값을 찍어보면 2 3 3 4 4 4 5 5 5 5 5 6 6 6 6 6 6 6 6 7 7 7 7 이러한 값들이 나오게 된다,
            즉 , 처음 방문하는 count의 time 값 이것은 무조건 그 count에 접근하는 time의 최솟값인 것이다 , 그것을 보장하기 위해서 무조건 3가지 연산으로 분기가 가능해야 한다 , 모든 경우가 가능하도록 하기 위해서,
            즉 브루트 포스 , 너비 우선 알고리즘 이라고 생각할 수 있을 것 같다.

            결론적으로 copy는 dp[count]를 고려하지 않고(방문여부를 확인 x) 할 수 있게 하고 , count + copy 연산은 n * 2 의 범위를 넘지 않게 하고 , 또 count - 1 , count + copy 연산은 무조건 count 값이
            변하는 연산이기에 이 연산은 해당 count 에 방문하였을 때의(queue 에 넣는 time 값) 이 값이 해당 count의 time 최솟값인 것이다 , 그렇기에 count - 1 , count + copy 는 처음 방문한 경우에는
            무조건 dp[count]에 들어가게 된다 , 이게 무조건 첫 방문이라면 최솟값이니까 , 왜냐하면 time bfs 특성상 위에서 출력값과 같이 2 3 3 ..... 7 7 7 7 이런식으로 순서대로 진행되기에
            막 2 4 3 1 ... 이런식으로 진행되는 것이 아니라 오름차순으로 진행되어서 time 값이 , 첫 방문이 최소값인 것이다.

            한마디로 정리하면 time 값이 bfs 특성상 오름차순으로 진행되고 , 모든 3가지 연산을 백트래킹 연산과 , 적절한 조건문을 통해서 가능하도록 하고 count에 첫 방문이라면 queue에다가 집어넣고 , dp[count]에다가 값을
            집어넣도록 하여서 답을 도출하도록 하였음.
             */

//            Emoticon emoticon = queue.poll();
//            int count = emoticon.count , time = emoticon.time , copy = emoticon.copy;
//            if(count == n) {System.out.print(time); break;}
//            if(dp[count] == -1) dp[count] = time;
//            if(count - 1 != 0 && dp[count - 1] == -1) queue.add(new Emoticon(count - 1 , copy , time + 1)); // 무지성으로 일단 3가지 경우 다 때려박아보자.
//            if(count + copy <= n * 2 && dp[count + copy] == -1) queue.add(new Emoticon(count + copy , copy , time + 1));
//            if(count != copy) queue.add(new Emoticon(count , count , time + 1));

            //솔직히 그래서 아래와 같이 짜도 됨

            Emoticon emoticon = queue.poll();
            int count = emoticon.count , time = emoticon.time , copy = emoticon.copy;
            if(count == n) {System.out.print(time); break;}
            dp[count] = 1;
            if(count - 1 != 1 && dp[count - 1] == 0) queue.add(new Emoticon(count - 1 , copy , time + 1));
            if(count + copy <= n * 2 && dp[count + copy] == 0) queue.add(new Emoticon(count + copy , copy , time + 1));
            if(count != copy) queue.add(new Emoticon(count , count , time + 1));
        }
//        System.out.println(Arrays.toString(dp));
    }
}