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
 */
public class Main2 {
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
            if(this.cost > o.cost){
                return -1;
            }
            else if(this.cost == o.cost){
                return o.time - this.time; // cost 가 낮은 것이 뒤에 가야 함 음수가 앞쪽이니까 , this.cost 가 더 크면 음수가 되어서 this가 앞쪽으로 올 것
            }
            else{
                return 1;
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
        boolean[] visited = new boolean[max + 1];

        while (!queue.isEmpty()) {
            Lecture lecture = queue.poll();
            for(int i = lecture.time; i != 0; i--){
                if(!visited[i]){
                    visited[i] = true;
                    ans += lecture.cost;
                    break;
                }
            }
        }

        System.out.println(ans);
    }
}
