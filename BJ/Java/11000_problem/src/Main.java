import java.util.*;
import java.io.*;

// 11000 : 강의실 배정

/*
-- 전제 조건
Si 에 시작해서 Ti에 끝나는 N개의 수업이 존재한다.
최소한의 강의실을 사용해서 , 모든 수업을 가능하게 해야 한다.

모든 수업은 수업이 끝난 직후에 할 수 있다.
최소한의 강의실을 출력하시오.
-- 틀 설계
일단 정렬을 한다.
그리고 시작시간을 기준으로 정렬하지만
시작시간이 만일 같다면 종료시간을 빠른 것을 선택하여서 강의의 길이가 짧은 것을 선택한다.

그냥 상식적으로 생각하면 강의 사이사이에 빈공간을 줄이면 된다.
그렇다라는 건 선택된 과목이 있다면 다른 강의실을 선택해야 한다.

그러면 이렇게 진행하면 될 듯하다.

일단 시작시간 및 종료시간으로 정렬을 진행한 다음에
순서대로 진행하면서 , 종료시간을 기록한다.

그리고 그 다음 수업이 종료시간에 부합하지 않을 때 ans ++ 를 해주고
현재의 종료시간과 그 강의의 종료시간을 비교하여서 , 종료시간을 바꿔준다.

계속 그렇게 진행하면서 , 종료시간에 부합하지 않으면 ans++ 를 해주는 방향으로 진행한다.

이렇게 되면 종료시간을 제일 짧은 것으로 해놨기 때문에 당연하게도 해당 종료시간 보다 강의시간이 빠르면 강의실을 하나 더 써야 한다.
왜냐하면 지금 진행되고 있는 강의실이 없거든

근데 만일 종료시간 이후로 성공하면 이전 나가리된 강의실에 수업 종료시간을 신경써야 하니까
그러면 이렇게 하면 되겠다.

일단 수업을 시작한다.
그 다음에 종료시간을 priorityQueue 에다가 넣어놓는다.
그 다음 수업을 진행한다. 만일 내 종료시간보다 작으면 시작 시간이
그러면 ans++ 를 하고 , 그 다음에 지금 현재 종료시간을 바꿔준다.

그 다음에 만일 종료시간이 더 크게 되면 priorityQueue 에다가 넣어준다.
그 다음에 또 현재 종료시간에 만족하는 시작시간이 존재한다면
해당 강의의 종료시간을 priorityQueue 에다가 넣어준다.

그런 다음에 현재 종료시간과 비교해서 바꿔준다.

즉 , 결론적으로 종료시간을 priorityQueue 에다가 관리를 하면서
priorityQueue 에다가 집어넣은 값이 현재의 endTime 보다 작으면 queue에서 빼서 바꾸고
아니면 queue 에다가 냅둔다.

새로운 강의가 들어올 때마다 그것을 진행하고,
일단 새로운 강의가 들어오면 지금 가지고 있던 종료시간은 버려야 한다.

-- 해맸던 점
강의실을 더 쓰는 경우에
그래도 이전에 쓰던 강의실을 살려놨었어야 했는데,
그냥 버려버림

5
1 7
2 3
3 4
4 8
7 10

그럼 이 경우에서도
7 이 end 로 유지되고 ,
그 다음에 3 , 4 이렇게 들어왔다 (강의실을 더 써야하는 경우)

그런 다음에 , 4 8 이 들어왔는데
여기서 이제 7은 이미 버려졌었으니까 8 이 end time 으로 들어온다.
그러면 7 10 이 들어왔을 때 강의실을 하나 더 써야 하는 경우가 생긴다. 1 7 이 있었는데 말이다.

그래서 강의실을 더 쓰는 경우는 이전 강의실의 기록을 남겨놔야 하기 때문에
다시 넣는다 현재의 end를

그러면 다 기억할 수 있다.

강의실을 그냥 이어서 쓰는 경우는 고려할 필요가 없다 , 현재 쓰던 강의실을 쓰는 것이니까
근데 이전에 쓰던 강의실을 그냥 버리는 것은 안된다라는 개념으로 접근하면 된다.(그게 강의실을 하나 더 쓸때 queue에다가 넣는 행위)
 */
public class Main {
    public static int N ,ans = 0;
    public static PriorityQueue<Integer> queue = new PriorityQueue<>(); // default 가 ascending 임
    public static Lecture[] lectures;

    public static class Lecture implements Comparable<Lecture>{
        int start;
        int end;
        public Lecture(int start , int end){
            this.start = start;
            this.end = end;
        }
        @Override
        public int compareTo(Lecture o){
            if(this.start > o.start){
                return 1;
            }else if(this.start == o.start){
                return this.end - o.end;
            }
            return -1;
        }
        @Override
        public String toString(){
            return "start : " + start + " end : " + end;
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());
        lectures = new Lecture[N];

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(input.readLine());

            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            lectures[i] = new Lecture(start , end);
        }

        Arrays.sort(lectures); // sort 잘 됨

        int end = lectures[0].end;
        ans++; // 일단 첫번째 강의실은 무조건 차지함

        for(int i = 1; i < N; i++){
            /*
            PriorityQueue 에다가 집어넣는다.
            근데 해당 종료시간 보다 더 높은 값이 들어오면 무조건 queue 에서 뽑아다가 쓰고
            그렇지 않으면 queue 에다가 집어넣은 다음에
            현재 end time 과 비교해서 집어넣는다.
             */

            int nowStart = lectures[i].start;
            int nowEnd = lectures[i].end;

            queue.add(nowEnd); // nowEnd를 일단 집어넣고

            // 비교를 진행한다.
            if(end <= nowStart){ // 강의실을 더 안써도 되는 경우이면
                end = queue.poll();
            }else{ // 강의실을 더 써야 하는 경우이면
                ans++;

                if(end > queue.peek()){
                    queue.add(end);
                    end = queue.poll();
                }
            }
        }

        System.out.println(ans);
    }
}
