import java.util.*;
import java.io.*;

// 2812 : 크게 만들기

/*
-- 전제 조건
N자리 숫자가 주어졌을 때,
여기서 숫자 K개를 지워서 얻을 수 있는 가장 큰 수를 구하는 프로그램을 작성하시오.
-- 틀 설계
일단 , 큐를 이용해서 , 가장 큰 수들만 남길 수 있도록 한다.
그래서 queue 에다가 숫자를 담는다.
일단 queue.isEmpty() 일 때에는 , 그냥 담고,
그렇지 않으면 peek 에 있는 숫자를 비교하여서 , 해당 숫자보다 , 작은 숫자들은 제거한다.
(이 행위는 앞쪽에는 항상 큰 숫자나 , 같은 숫자를 남기기 위함이다.)
그 과정에서 k 가 소진되면 , 그냥 진행하면 된다.

근데 , 예외 사항으로 , k가 남아있는 경우가 있다.
예를 들어서 어제 했던 4848111 같은 경우
에는 88이 최종인데

이 경우에는
4
4 8 작으니까 4 날리고 k--
8 4 8 4 날리고 k --
k == 3
8 8 1 1 1
이렇게 진행이 된다.

그럼 다 진행하였는데 , k 는 남아있다 이런 경우에는
그냥 k 개를 남기고 , 뽑으면 된다.

즉 queue.size == k 가 되었을 때 출력을 멈추면 된다.

-- 해맸던 점
last를 뽑고 , last 를 비교를 해야하는데 , 그것을 헷갈려서 잘 못했었음
근데 그 다음에는 잘 진행이 됨

-- 결론
결국 원리는 앞에 숫자가 제일 큰 수로 올 수 있게끔 걸러내는 것인데,
그 과정에서 LinkedList 자료구조와 , k의 개수를 고려하고,
그리고 다 진행했는데 , k 가 남아있다라는 것은 그 후에 더 작은 값이거나 같은 값이 지속적으로
들어왔다는 것이고 , 그렇기 때문에 , queue.size 가 k 가 될 때까지 앞에서부터 쫘르륵 뽑아서
출력해주면 답을 구할 수 있다.
 */
public class Main2 {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int N = Integer.parseInt(st.nextToken()) , k = Integer.parseInt(st.nextToken());
        LinkedList<Integer> queue = new LinkedList<>();

        char[] arr = input.readLine().toCharArray();

        for(int i = 0; i < N; i++){
             int a = arr[i];
             // 이렇게 받고 , queue 에 대한 연산을 진행한다.

//            System.out.println(queue);

            while(!queue.isEmpty() && k != 0 && queue.getLast() < a){
                queue.pollLast();
                k--;
            }

            queue.add(a);
        }

        while(queue.size() != k){
            output.write(queue.poll());
        }

        output.flush();
        output.close();
    }
}
