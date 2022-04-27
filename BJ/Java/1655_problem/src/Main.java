import java.util.*;
import java.io.*;

// 1655 : 가운데를 말해요
/*
-- 전제 조건
정수를 외칠 때 마다 지금까지 외친 정수들 중 가운데 값을 외쳐야한다.
만일 정수들이 짝수 개일 경우에는 가운데 2개중 작은 값을 선택하면 된다.
1 , 5 , 2 , 10 , -99 , 7 , 5를 순서대로 외쳤다고 하면 1, 1, 2, 2, 2, 2 , 5를 외치는 순이다.

입력으로는 총개수가 주어지고 순서대로 외치는 값들이 주어진다.
순서대로 가운데 값을 출력하라
0.1초 시간제한이기에 효율적으로 구성해야 한다.

-- 틀설계
자 일단 순서대로 한번 수를 정리해보자

들어온 순서대로
1 - 1
1 5 - 1
1 5 2 - 2
1 5 2 10 - 2
1 5 2 10 -99 - 2
1 5 2 10 -99 7 - 2
1 5 2 10 -99 7 5 - 5

5 7 8 13 15 20 25

정렬 하면서 받아보자
1
1 5
1 2 5
1 2 5 10
-99 1 2 5 10
-99 1 2 5 7 10
-99 1 2 5 5 7 10

결국 모르겠어서 답을 보았음..
min , max heap을 따로 comparable 을 오버라이딩 하면 된다. (o1 , o2) -> o1 - o2 이렇게 되면 o1 이 더 크면 뒤로 가게 되는 것이다.
쨋든 왜 min , max heap을 따로 두어서 max 의 top에 있는 것이 왜 중간값인지 탐구해보자.
왼쪽이 max , 오른쪽이 min이라고 가정하고 해보자.
가정은 이러하다.
두개의 사이즈가 같다면 max heap에다가 집어넣는다.(이 경우 min의 최소 값보다 들어간 값이 크다면 넣은 다음에 상위 숫자들을 swap 해주어야함)
두개의 사이즈가 다르다면 , 당연히 사이즈가 다르면 무조건 min 이 사이즈가 작으니 min에다가 집어넣는다.(이 경우에도 max의 최대값보다 작다면 max에다가 집어넣는다.) - 이 두개의 부분이 max의 top 부분의 숫자를 중간값으로 만드는 keyword임
이러면서 계속 반복하면서 들어간다

1입력 - [1] [] - 1
5입력 - [1] [5] - 1
2입력 - [1 2] [5] - 2
10입력 - [1 2] [5 10] - 2
-99입력 - [1 2 -99] [5 10] - 2
7입력 - [1 2 -99] [5 7 10] - 2
5입력 - [1 2 -99 5] [5 7 10] - 5

여기서 포인트는 번갈아가면서 집어넣는데 , max에 들어가는 값이 min 의 최솟값보다 크면 swap 하는 것이다 , 그러니까 min에 max heap 보다 큰 숫자가 없도록 만드는 것임
그리고 min도 max보다 큰 숫자들을 유지해야한다 , 그래서 max 의 최댓값보다 작은 값이 들어오게 되면 넣은다음 swap하는 것이다. max의 상위 값과

예를 들어서 그러면 10 8 5 3 5 -1 이 들어왔다고 가정해보자
10 - [10] [] - 10
8 - [8] [10] - 8
5 - [5 8] [10] - 8
3 - [3 5] [8 10] - 5
5 - [3 5 5] [8 10] - 5
-1 - [-1 3 5] [5 8 10] - 5

여기서 역시 포인트는 두개의 힙을 번갈아가면서 넣는 것이다 값을 그렇게 번갈아가면서 넣게 되면 두개의 사이즈는 1씩밖에 차이가 나지 않게 된다.
그래서 이 경우에 min heap 을 max heap보다 큰 값을 넣을 수 밖에 없게 된다면?
당연히 max heap의 최고 값이 가운데 값이 될 수밖에 없는 것이다. 직관적이게 표현하자면
max heap 사이즈가 2이고 (max heap size가 무조건 클 수밖에 없게 만들어야함 그래야지 홀 수개일 때도 , 짝수 개일 때에도 가운데 값이 top으로 유지가 됨
그래서 max heap size = 2 min heap size = 1 이라고 했을 때 [2개][1개] 이다 근데 무조건 max heap에 min heap 에 존재하는 수보다 작은 값을 유지하도록 하였음
그래서 max heap의 1 2 가 들어있다고 가정하고 min heap에 3이 들어있다고 가정했을 떄 (쉽게) 1 2 3 이라는 배열이 되서 max heap의 peek 가 가운데 값이 될 수밖에 없는 것이다.
이제 두개의 사이즈가 똑같을 때를 가정해보자 max heap size = 2 min heap size = 2 라고 가정했을 떄 max heap = 1 2 , min heap = 3 4 라고 가정해보자 이럴 때 두개의 사이즈가 같을 때에도
1 2 3 4 가 유지되게 되는데 여기서 더 작은 값을 뽑는 것이니 max head의 peek가 답임에는 변함이 없다 만약에 min heap이 먼저 추가가 되게 된다면? 그러면 이제 두개의 힙의 사이즈가 같을 때에는 상관이 없지만
두개의 힙의 사이즈가 다를 때 문제가 된다.
마지막으로 예를 들고 설계를 시작하자면 max heap size = 1 , min heap size = 2 , 1 2 3 이런 배열이 되는 건 똑같지만 max heap peek는 1 이기에 이것은 중간값이 아니게 된다.
그러니 이 알고리즘의 핵심은 max heap size 가 항상 앞서가거나 같으며 , min heap 에 있는 숫자들 보다 max heap에 있는 숫자들이 모두 작도록 하여서 peek를 중간값으로 유지하는 것이다.

n을 받고 loop를 돈다.
i == 0 일 때에는 무조건 max에다가 집어넣는다.
그 다음부터는 이제 두개의 사이즈를 보는 것이다.  max.size > min.size 면 min 에다가 집어넣고
max.size == min.size 이면 max에다가 집어넣는다. 근데 집어넣으면서 확인해야할 것이
max.size > min.size 에서는 지금 받은 숫자가 max.peek 보다 작은지를 확인한다. 그러면 바로 max.poll 하고 그 숫자 min에다가 집어넣고 max 에다가 입력 받은 숫자를 집어넣는다.
max.size == min.size 에서는 지금 받은 숫자가 min.peek 보다 큰지 확인한다. 만약 크다면 바로 min.poll 하고 그 숫자를 max에다가 집어넣고 min 에다가 입력 받은 숫자를 집어넣는다. 이 예외 상황에 벗어나지 않는다면 그냥 집어넣는다.
그러고서 max.peek()로 출력을 해주면 된다.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));

        PriorityQueue<Integer> max = new PriorityQueue<>((o1 , o2) -> o2 - o1); // o1 이 더 크면 , (o1 == this) 앞에다가 배치 , 즉 오름차순
        PriorityQueue<Integer> min = new PriorityQueue<>((o1 , o2) -> o1 - o2); // o1 이 더 크면 , (o1 == this) 뒤에다가 배치 , 즉 내림차순 PriorityQueue 개념에서

        int n = Integer.parseInt(input.readLine());

        for(int i = 0; i < n; i++){
            int inputNumber = Integer.parseInt(input.readLine());
            if(i == 0){
                max.offer(inputNumber);
            }else{
                if(min.size() == max.size()){ // max에다가 집어넣는 상황
                    if(min.peek() < inputNumber){
                        max.offer(min.poll());
                        min.offer(inputNumber);
                    }else{
                        max.offer(inputNumber);
                    }
                }
                else{ // min 에다가 집어넣는 상황
                    if(max.peek() > inputNumber){
                        min.offer(max.poll());
                        max.offer(inputNumber);
                    }else{
                        min.offer(inputNumber);
                    }
                }
            }
            output.write(max.peek() + " ");
        }

        output.flush();
        output.close();
    }
}
