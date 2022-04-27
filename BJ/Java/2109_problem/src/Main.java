import java.util.*;
import java.io.*;

// 2109 : 순회강연
/*
-- 전제 조건
강연이 들어오는 데 , 하루에는 하나만 할 수 있다.
그래서 , 각 대학에서 해당 일 안에 와서 해주면 , 해당 강연료를 지불한다고 했을 때 ,
가장 돈을 많이 받을 수 있는 경우를 구하여라
-- 틀 설계
이 경우는 그냥 최대 일 수를 기억하고 ,
해당 time 으로 정렬을 해주고
해당 time 까지 받는다 , PriorityQueue 로
그 다음에 또 그렇게 받고 , 일 수가 다 소진 될때까지 계속 이 행위를 반복한다.

그렇게 하면 된다.

-- 해답
솔직히 이거 맞추기 까지도 , 내가 했던 방법이 절대 틀린 것 같지 않은데 왜 틀린지 아직도 모르곘음.
4
20 2
30 2
40 3
40 3

반례를 찾아냈음 , 이 경우에는 그냥 40 , 40 을 진행하고 30하나를 진행하는 게 더 좋음
근데 내것은 그렇게 안함.

다른 여느 방법들 처럼
코스트가 큰 순서대로 내림차순 정렬한 뒤에,
코스트가 같은 경우에는 time도 내림차순으로 정렬하여서
해당 날짜에 이미 했는지 안했는지 , 했다면 , 가능한 가용범위 내에서 , 실행을 해야함

20 2
30 2
40 3
40 3

같은 경우도 이렇게 정렬이 될 것이다.
40 3
40 3
30 2
20 2

그러면 40 3 두개를 신청하여서
3일 2일을 할당할 것이다.
그 다음에 , 30일 , 20일을 진행하는데 30일은 2일은 이미 할당 되어 있고,
그래서 1일을 할 당한다.
근데 이제 20이 들어갈 데가 없어서 20을 할당하지 못한다.
그래서 , 답은 == 110 으로 구할수가 있다.

이 풀이 방법 같은 경우는 , 지금 나온 것은 cost가 가장 큰 것이다.
결국은 , 그러니까 당연하게 무조건 적으로 할당을 먼저 해야한다.
근데 가용범위 내에서 해야한다.
그렇기 때문에 , cost 가 같은 경우에는 time 이 높은 순으로 먼저 실행이 되어서
다음 같은 cost를 가진 놈에게 영향을 주지 않도록 한다. 만일 영향을 주더라도 그게 최선이다.

그래서 , 스케줄을 적절하게 배치하는 것 , cost 순으로 정렬후에 , 스케줄을 적절히 배양하기 위해서
cost 가 같은 경우에는 time 으로 내림차순 정렬을 하여서 , 스케줄을 나중 시간부터 할 수 있도록,
다음에 실행 되는 같은 cost 가 할당하는데 영향을 덜 끼치도록 하여야 한다.

그래서 결론은 제일 비싼 강연부터 고르면서 , 가능한 일을 꽉꽉 채우는 것이 이 문제의 쟁점이다.
(앞서 뽑힌 애들은 cost가 높은 애들이니까 무조건 적으로 실행되어야 하는 게 맞고 , 해당 일 수로부터 1일까지 빈 스케줄이 없다라면,
이미 더 좋은애들이 많이 선택된 것이니 어쩔 수 없이 나가리가 되어야 한다.)

결국은 가장 코스트가 큰 애들의 범위 내에서 코스트 큰 애들이 최대한 많이 차지한다 , 이 이념하나이다. 그냥
 */
public class Main{
    public static int N , ans , max = 0;
    public static class Lecture implements Comparable<Lecture> {
        int cost;
        int time;
        public Lecture(int cost , int time){
            this.cost = cost;
            this.time = time;
        }

        @Override
        public int compareTo(Lecture o){
            if(this.cost < o.cost){
                return 1;
            }
            else if(this.cost == o.cost){
                return o.time - this.time; // cost 가 낮은 것이 뒤에 가야 함 음수가 앞쪽이니까 , this.cost 가 더 크면 음수가 되어서 this가 앞쪽으로 올 것
            }
            else{
                return -1;
            }
        }

        @Override
        public String toString(){
            return "cost : " + this.cost + " time : " + this.time;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());
        PriorityQueue<Lecture> queue = new PriorityQueue<>(); // default 가 오름차순임

        for(int i = 0; i < N; i++){
            // time 의 가장 max 값까지 간다음에 그 다음에 이 값으로 강의를 진행할 것임
            st = new StringTokenizer(input.readLine());

            int cost = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());

            max = Math.max(time , max);

            queue.add(new Lecture(cost , time));
        }

        /*
        어떻게 해야 할까..
        생각하던 게 틀린다. 다시 생각해보니까 그렇게 될리가 없다.
        일단 첫번째 조건 하루에 하나라도 무조건 하는 것이 낫다.
        즉 , 여유가 있는 것들은 조금 나중에 해도 된다. 값이 낮더라도 지금 되는 거를 하는게 좋다.

        이렇게 하면 될 것 같다.
        time 순으로 정렬하되 time이 같으면 높은 cost 순으로 정렬하면 될 것 같다. 그리고 애초에 lectures[i] 에다가 넣는 것이 아닌
        priorityQueue 에다가 바로 집어넣으면 될 것 같다.
         */

//        int index = 0;
//        while (!queue.isEmpty()) {
//            System.out.println(queue.poll());
//        }

        boolean[] visited = new boolean[max + 1]; // 0을 제외하고 , 본인의 스케줄을 기록할 배열
        while (!queue.isEmpty()) {
            Lecture lecture = queue.poll();
            for(int i = lecture.time; i != 0; i--){
                if(!visited[i]){ // 아직 스케줄 없을 때
                    visited[i] = true;
                    ans += lecture.cost;
                    break;
                }
            }
        }

        System.out.println(ans);
    }
}
