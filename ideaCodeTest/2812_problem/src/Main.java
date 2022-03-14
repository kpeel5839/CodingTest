import java.util.*;
import java.io.*;

// 2812 : 크게 만들기
/*
-- 전제 조건
N 자리 숫자가 주어졌을 때 , 여기서 숫자 K개를 지워서 얻을 수 있는 가장 큰 수를 구하여라
N은 50만 까지 가능하다 , 그리고 이 수는 0으로 시작하지 않는다.

-- 틀 설계
일단 확실한 것은 자리수를 하나를 제거 할 때마다 가치는 10 / 1이된다.
그리고 맨 앞자리는 0이 될 수 없다.

그렇다는 것은 앞자리는 무조건 올 수 있는 가장 큰 수가 오는 것이 좋다.
되는 한 앞자리가 가장 큰 수가 와야 한다라는 것이다.

그 다음에 앞자리가 확정 되면 , 즉 더 큰 수로 만들지 못한다?
그러면 다음 자리로 이동하면 된다.

그러면 이렇게 하면 된다.
9를 찾는다? 그러면 그냥 넘어가고
그렇지 않다면? 계속 찾는다.

그럼 여기서 더 조금 더 다가가보면 9가 아닌 주어진 N을 이루고 있는 수중 가장 큰 값에 도달하면 이제 다음부터 찾는 것이
어떨까?
예를 들어서

4848111
5개를 빼라고 했다.
81 이 될 것이다 가장 큰 수를 빼지 않는다면
근데 그렇지 않고 가장 큰 수에 도달하면 88 이 될것이다.

근데 뭔가 더 다른 개념으로 접근 가능할 것 같다.

이렇게 하는 거 어떨까?
값으로 주어지는 것들을 전부다. arr 에다가 저장한다음에

k값만큼 priorityQueue 에다가 담는다.
그 다음에 , priorityQueue 에 cost 내림 차순에다가 cost 같은 경우에는
index 가 낮은 순으로 정렬한뒤 출력한다.
그러고서 , k - index 를 해준다.
그 다음에 k == 0 이 되면 그냥 다 출력해준다.

정리하면 , 일단 PriorityQueue 를 cost 값 순으로 정렬을 시키고 , cost 값이 같으면 index 가 낮은 순으로 정렬이 되게 한다.
일단 priorityQueue 에다가 k 개 만큼 집어넣는다.

그 다음에 그 다음에 k만큼 집어넣은 다음에 거기서 하나를 뽑는다.
그러면 하나가 나오고 , 그 index 숫자를 하나를 출력한다.
그 다음에 해당 index 수만큼 다시 담는다.

처음에 k개만큼 담았으니까
그 다음에 또 하나를 뽑고 , 이 번에는 이번에 뽑은 인덱스 - 이전 인덱스 - 1 만큼 다시 뽑는다.
그래서 결국 더 뽑을 것이 없으면 출력을 전부 다 해준다.... 아닌 것 같다.

항상 k + 1 개만큼 뽑으면 어떨까? 짜피 k개 뻈을 때 , k + 1 번째의 숫자가 가장 커야 하는 거니까

1924
의 경우로 해보자.
1 9 2 까지 담음
9 가 가장 크면서 앞에 있음 , 1만 뺌
하나 더 담음
1 2 4  , 4가 나옴 출력
94

k == 3
1231234

1 2 3 1 까지 뽑음
3 뽑음 2개 더 뽑음
k - 2 = 1

1 2 1 2 3
3 뽑으면 k가 안됨
2 뽑음 현재 index 보다도 작음
버림 1 뽑으면 또 버림
1 2 만 남았음
2 뽑는다. 그러면 현재 해당 인덱스 4 이전에 뽑은 거 2였음
그래서 k == 1 이라서 가능함
2 출력
k - 1 = 0

1 만 남고 , k == 0 이 되었으니까 다 출력
3234
이렇게 된다.

k == 5
4848111 가장 걱정인 이것을 해보자.

4 8 4 8 1 뽑음
8 나옴 k -= 1 했음
8 출력
하나 더 뽑음
4 4 8 1 1
8 뽑음

이전 인덱스 1 에서 지금 뽑은 거 3
당연히 가능함
한개 더 뽑음

4 4 1 1 1
전부 다 뽑았음 지금

4 4 뽑았더니 index 이전임
1 1 1
priorityQueue size == 3 == k 끝
출력 x

현재 출력된 거랑 , N - K 랑 같으면 끝내는 방법도 나쁘지 않은 듯 일단 계속
PriorityQueue 로 뽑음서

이건 솔직히 틀릴 것 같긴했다.
최선을 다했으니까 답을 보자.
 */
public class Main{
    public static int N , K;
    public static char[] arr;

    public static class Number implements Comparable<Number>{
        int value;
        int index;
        public Number(int value , int index){
            this.value = value;
            this.index = index;
        }
        @Override
        public int compareTo(Number o){
            if(this.value < o.value){
                return 1;
            }
            else if(this.value == o.value){
                return this.index - o.index;
            }
            return -1;
        }
        @Override
        public String toString(){
            return "value : " + value + " index : " + index;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(input.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        arr = input.readLine().toCharArray();
        PriorityQueue<Number> queue = new PriorityQueue<>();

        int count = 0; // 현재까지 뽑은 숫자의 개수를 세는 것
        int pullIndex = -1; // 내가 뽑은 것이 k에 부합하는지 확인하기 위해서
        int index = 0;
        int more = K + 1;
        int remainK = K;

        Loop:
        while(true){
            if(count == N - K) break; // end 조건
            if(remainK == 0){
                for(int i = pullIndex + 1; i < N; i++) output.write(arr[i]);
                break;
            }
            if(index >= N) break;

            for(int i = 0; i < more; i++){ // 일단 k + 1 만큼 queue에다가 담아준다.
                queue.add(new Number(arr[index] , index++));
                if(index >= N) break Loop;
            }

            if(queue.isEmpty()) break;

            while(!queue.isEmpty()){ // 비어 있지 않은 이상 뽑는다.
                Number number = queue.poll();
                // 이게 k 개에 부합한지 안한지를 확인해야 한다.
                if(pullIndex == -1){ // 아직 뽑지 않은 상태라면?
                    if(number.index >= more) continue;
                    output.write(number.value);
                    count++;
                    pullIndex = number.index;
                    more = pullIndex;
                    remainK -= pullIndex;
                    break;
                }else{ // 처음 뽑는게 아닐 때에는?
                    if(number.index < pullIndex) continue;
                    if(number.index - pullIndex - 1 > remainK) continue;
                    output.write(number.value);
                    count++;
                    more = number.index - pullIndex - 1;
                    pullIndex = number.index;
                    remainK -= more;
                    break;
                }
            }
        }

        output.flush();
        output.close();
    }
}