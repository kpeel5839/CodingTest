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
        /*
        queue 를 선언하고
        이전에 내가 무조건 2배 늘리는데에 2초가 걸린 다는 전제가 잘 못 되었었음
        그렇기 떄문에 3가지 연산으로 분기해야 한다.
        일단 처음에 copy 하는 부분
        그리고 copy의 개수가 존재한다면 붙혀넣기 하는 부분
        그리고 그리고 하나를 빼는 time 부분을 생각해야한다.
        copy하는데에는 시간이 1초 걸리지만 실제로 값이 변하지 않는다.
        그래서 단편적으로 보았을 때에는 손해처럼 보인다 , 이 부분을 해결해야 할 것 같다.
         */
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(input.readLine());

        Queue<Emoticon> queue = new LinkedList<>();
        int[] dp = new int[n * 2 + 1]; // 오히려 n을 넘어서 -1 하는 경우가 더 빠를 수도 있기 때문에 , n * 2 + 1로 넉넉하게 잡아놓음 , 사실 이정도는 필요 x
        Arrays.fill(dp , Integer.MAX_VALUE);

        dp[1] = 0;
        queue.add(new Emoticon(2 , 1 , 2));
        //copy가 0이면 더 이상 붙여넣기 못한다.
        while(!queue.isEmpty()){
            /*
            일단은 -1 , 그리고 붙혀넣기 이 과정에서 queue에 추가하기전에 이 값이 그 count 값으로 이동했을 때 , 이전에 이 count 값에 방문했었던 time보다 time이 크거나 같다면? 방문할 이유가 없는 것임
            근데 여기서 살짝 애매한게 , copy값이 다를 경우에 만일 이전에 여기에 방문한 놈의 time 과 지금 도착시간이 같은데 지금 copy 값이 더 크다면??? 근데 그게 가능할까?
            확실한 건 만일 가능하다면 더 큰 값에 더 빠르게 도달 할 수 있을 것이다. 왜냐하면 계속 붙혀넣기를 하면 이미 내가 지금 더 크기 때문에 큰 값에 도달을 빨리 하니까 , 이 경우에
            그렇게 된다면 , 공배수에 값에 도달하게 될 때 예를들어서 4 와 3 의 copy값이 있다면 12 있다가 도달하게 될 때 4의 copy값을 가졌던 애가 time 1이 더 앞서게 된다 , 근데 이렇게 되면 상관 없는데
            내가 이미 4를 죽여놨기 때문에 그렇게 될 수가 없다 , 근데 이 코드는 어떻게 통과한 거지?
            일단 copy 부분에서는 count == copy 라면 이미 copy를 진행했다는 반증 , 혹은 갔다가 돌아와서 여기서 copy해봤자 상관이 없다는 전제이다 , 그리고 여기서 dp[count] > time + 1을 하지 않은 이유는 이거는
            copy가 안되기 때문이다 , 그러면 계속 copy = 1을 가지고 가야하기 때문에 이러한 조건을 넣었다.

            일단 백트래킹을 이용하여서 한 지점에서 3가지의 방법으로 분기하지만 , 적절한 조건문 처리를 이용해서 시간을 줄였다.

            이 코드가 맞기 위해서는 무조건 다른 copy 값을 가진 emoticon 객체가 같은 시간에 같은 count 값으로 올 수가 없어야함 , 그렇지 않고서는 말이 안됨 , 내가 풀고서도 이게 왜 맞았는지 의아하다.
             */
            Emoticon emoticon = queue.poll();
            int count = emoticon.count , time = emoticon.time , copy = emoticon.copy;
            if(count == n) {System.out.print(time); break;}
            if(dp[count] > time) dp[count] = time;
            if(count - 1 != 0 && dp[count - 1] > time + 1) queue.add(new Emoticon(count - 1 , copy , time + 1)); // 무지성으로 일단 3가지 경우 다 때려박아보자.
            if(count + copy <= n * 2 && dp[count + copy] > time + 1) queue.add(new Emoticon(count + copy , copy , time + 1));
            if(count != copy) queue.add(new Emoticon(count , count , time + 1));
        }
    }
}
