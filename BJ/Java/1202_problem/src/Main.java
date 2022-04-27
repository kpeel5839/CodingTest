import java.util.*;
import java.io.*;

// 1202 : 보석도둑
/*
-- 전체 설계
보석이 n 개 주어지고 , 가방이 k 개 주어진다.
가방에는 각 하나의 보석만 들어갈 수 있고,
보석의 무게 , 가치가 주어진다.
그리고 가방의 최대 중량이 주어진다.

문제가 주어졌을 때 , 챙길 수 있는 보석의 최대 값을 구하시오.
-- 틀 설계
일단 보석을 클래스로 받고 , 오름차순으로 정렬해준다.
그 다음에 , 가방도 오름차순으로 정렬해준다.

그리고서 , for(int i = 0; i < n; i++) 을 하면서,
가방에다가 보석을 차례대로 넣는다.

근데 여기서 priorityQueue 를 Collections.reversOrder 를 사용해서 ,
내림차순으로 정렬하여서,
가능한 보석 중 , 가장 값어치가 큰 것들로 계속해서 , 집어넣는다.
그러면 가능한 것들 중 가장 값어치가 많이 나가는 것들을 차례대로 담을 수가 있다.
즉 , 가능한 경우에서 , 가장 최대의 선택을 계속해서 한다는 것이다.

-- 결과
솔직히 해답 볼 때 , 아무리 생각해도 , 같은 경우에 내림차순으로 정렬해야 하는 이유를 몰랐는데
역시나 그럴 필요가 없었다. 그냥 정렬해도 당연히 답이 나온다.
왜냐하면 , 가능한 무게일 때 짜피 priorityQueue에 다 들어가니까 , 그렇다는 것은
정렬에 시간이 더 걸리지만 않는다면 굳이 그럴 필요가 없다

하지만 , 그렇게 정렬하는 이유가 , PriorityQueue 에다가 집어넣었을 때 , logN 의
속도때문에 , 시간이 더 걸릴 줄 알았는데 그것도 아니였음 , 즉 그럴필요가 없었다.

-- 해맸던 점
결과값을 long 으로 했었어야 했는데 , 그렇지 않았다.
 */
public class Main {
    public static class Jewel implements Comparable<Jewel>{ // List 정렬은 , Comparator , 객체 배열 정렬은 Comparable
        int value;
        int weight;

        public Jewel(int weight , int value){
            this.value = value;
            this.weight = weight;
        }

        @Override
        public int compareTo(Jewel o){
            if(this.weight > o.weight){
                return 1;
            }
//            else if(this.weight == o.weight){
//                return o.value - this.value;
//            }
            return -1;
        }
    }
    public static PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.reverseOrder());
    public static int N , K;
    public static long ans = 0;
    public static int[] bag;
    public static Jewel[] jewel;
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        jewel = new Jewel[N];
        bag = new int[K];

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(input.readLine());
            jewel[i] = new Jewel(Integer.parseInt(st.nextToken()) , Integer.parseInt(st.nextToken()));
        }

        for(int i = 0; i < K; i++){
            bag[i] = Integer.parseInt(input.readLine());
        }

        Arrays.sort(jewel);
        Arrays.sort(bag);

//        for(int i = 0; i < N; i++){
//            System.out.println(jewel[i].weight + " " +  jewel[i].value);
//        }

        for(int i = 0 , j = 0; i < K; i++){
            // j는 보석을 나타내니 N 까지 i 는 가방을 나타내니까 K까지
            while(j < N && bag[i] >= jewel[j].weight) queue.add(jewel[j++].value);

            if(!queue.isEmpty()){
                ans += queue.poll();
            }
        }

        System.out.println(ans);
    }
}