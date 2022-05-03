import java.util.*;
import java.io.*;
import java.util.function.Function;

// 12764 : 싸지방에 간 준하

/**
 * -- 전제 조건
 * 준하는 군인이다.
 * 그래서, 싸지방을 사용하는데, 컴퓨터를 맘 놓고 하고싶다.
 *
 * 근데, 특별한 것이 여기에 싸지방을 쓰는 사람들이 다 동일한 시각에 컴퓨터를 사용한다.
 * 그러할 때, 아무도 막힘 없이 컴퓨터를 하기 위해서는, 최소 몇대의 컴퓨터가 있으며,
 * 그 다음에, 해당 좌석을 쓰는 사람은 몇명인지를 출력해라.
 *
 * -- 틀 설계
 * 일단, 우선적으로 모든 인원들이 다 쓸 수 있는 컴퓨터의 수를 구하는 것이 중요할 듯하다.
 * 그것이 확정되면, 그냥 다시 loop 돌면서, 컴퓨터에 할당시키면 되니까
 *
 * 근데, 컴퓨터의 수를 어떻게 구할 수 있을까?
 * 일단, 큐를 이용해서, 사용자가 지금 어떤식으로 컴퓨터를 사용하고 있는지 본다.
 *
 * 5
 * 20 50
 * 10 100
 * 30 120
 * 60 110
 * 80 90
 *
 * 일단, 이 테스트 케이스를 한번 봐보자.
 * 시작 시간을 기준으로 정렬을 일단 해야 한다.
 *
 * 그래야하는 이유는, 예를 들어서 70 90, 20 80 이 주어졌다고 가정했을 때,
 * 70 이 시작해서, 90 까지 진행한다고 되어있고,
 * 20 입장에서, 이때 사용하는지 아닌지 알 수가 없기 때문이다.
 *
 * 그래서 바로 사용한다라고 하면, 그냥 한대만 있어도 된다라고 하는데
 * 둘은 겹치기 때문에, 사실 2대를 써야 한다.
 *
 * 그래서, 오름차순으로 정렬한 뒤에 테스트 케이스를 진행을 해보자.
 * 10 100
 * 20 50
 * 30 120
 * 60 110
 * 80 90
 *
 * PriorityQueue 는 오름차순으로 정렬하도록 한다.
 * 그러고서 peek 값과 현재 시작시간을 비교하는 것이다.
 *
 * 만일, 현재 값이 더 크다면?
 * queue 값을 빼고, 현재 끝내느 시각을 집어넣는다.
 *
 * 만일, 현재 값이 peek 값보다 크지 않다면?
 * 그냥 집어넣는다.
 *
 * 현재 지금 실행되고 있는 컴퓨터들은 다 차있는 것이니까
 * 그래서 count 값도 늘린다.
 *
 * 이런식으로 계속 진행해보자.
 *
 * 10 100 실행 1
 * 20 50 실행, 100 보다 작음 컴퓨터 추가 2
 * 30 120 실행, 50 보다 작음 컴퓨터 추가 3
 * 60 110 실행, 50 보다 큼, queue.poll 하고 110 집어넣음
 * 80 90 실행, 100 보다 작음 컴퓨터 추가 4
 *
 * 그래서, 이 뒤에 endTime 과, 같이 count 값, 즉 현재 지금 몇번째 컴퓨터인지 값까지
 * 집어넣는다? 그러면
 *
 * 자리 배열 10만개 만들어놓고, 그냥 그거 추가하면 됨
 *
 * 틀린 이유는...
 * 컴퓨터 좌석을 배치할 때,
 * 더 자리가 더 작은 좌석으로 배치를 해야한다.
 *
 * 그러면 count 를 한꺼번에 처리하는 것이 아닌
 * 컴퓨터의 개수를 정하고, 가장 적합한 자리로 배치해야 할까?
 *
 * computer queue 를 하나 놔서
 * queue is Empty 하면, 무조건 집어넣고,
 * 또, queue peek 에 있는 것보다 point[1] 이 작으면 집어넣는데, 그 경우에는 예외 사항이 있는 것이
 * 이미 빼놓은 computer 번호가 있으면 그걸로 대체를 한다. 즉 컴퓨터 개수가 추가 되지 않고
 *
 * 그리고, queue peek 에 있는 것보다 point[1] 이 더 큰 상태이면,
 * 더 큰 것이 나올때까지, 즉 이미 끝난 컴퓨터들은 다 빼고, 거기서 가장 앞에 있는 컴퓨터를 차지하고
 * 현재 컴퓨터를 차지했다고 queue 에다가 집어넣는다.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        Function<String, Integer> fun = Integer::parseInt;

        int N = fun.apply(br.readLine());
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]); // 2개짜리 배열의 뒤에가 endTime 을 집어넣고, o[0] 은 몇번째 컴퓨터인지 집어넣음, 즉 오름 차순
        PriorityQueue<Integer> computer = new PriorityQueue<>();
        int[][] arr = new int[N][2];
        int res = 0;
        int[] count = new int[N];

        for (int i = 0; i < N; i++) {
            String[] input = br.readLine().split(" ");
            arr[i][0] = fun.apply(input[0]); // 시작 시간
            arr[i][1] = fun.apply(input[1]); // 끝 시간
        }

        Arrays.sort(arr, (o1, o2) -> o1[0] - o2[0]); // 이게 되네, 2차원 배열인데

        /**
         * 큐에다가 일단, 집어넣고, 본인보다 낮은 거 다빼면서,
         * computer queue 에다가 집어넣는다.
         *
         * 일단, 그러면 queue 에다가 어떻게 넣어야 할까??
         * 아 그래서, 이렇게 해야하려나?
         *
         * computer 가 isEmpty 면, 컴퓨터 번호를 부여할 수 없다.
         */
        for (int i = 0; i < N; i++) {
            // queue 가 isEmpty 일 때에는 집어넣고, res 값이 컴퓨터 번호이다. 그리고 count[res]++ 해준다.
            // 그리고, 만일 peek 값보다 커서, 집어 넣는 경우에는 해당 뺀 peek 의 컴퓨터 번호를 count++ 해준다.
            // 그리고, 만일 peek 값보다 작은 경우는 res 를 또 ++ 해주면서, count[res]++ 해준다.
            int[] point = arr[i];

            if (queue.isEmpty()) { // 처음인 경우
                count[res]++;
                queue.add(new int[] {res++, point[1]});
            } else if (queue.peek()[1] <= point[0]){
                while (!queue.isEmpty() && (queue.peek()[1] <= point[0])) { // 맨 위에 있는 게, point[0] 보다 클 때까지 반복
                    computer.add(queue.poll()[0]);
                }
                int computerNumber = computer.poll();
                count[computerNumber]++;
                queue.add(new int[] {computerNumber, point[1]});
            } else { // 이제, peek 값보다 작은 경우만
                if (!computer.isEmpty()) { // 이미 빼놓은 computer 번호 있으면
                    int computerNumber = computer.poll();
                    count[computerNumber]++;
                    queue.add(new int[] {computerNumber, point[1]});
                } else {
                    count[res]++;
                    queue.add(new int[] {res++, point[1]});
                }
            }
        }

        bw.write(res + "\n");
        for (int i = 0; i < res; i++) {
            bw.write(count[i] + " ");
        }

        bw.flush();
        bw.close();
    }
}
