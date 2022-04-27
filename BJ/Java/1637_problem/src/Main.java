import java.util.*;
import java.io.*;

// 1637 : 날카로운 눈
/*
-- 전체 설계
정수더미가 주어지는데
거기서 , 유일하게 홀수 개 존재하는 정수가 있다.
그 때 홀 수개 존재하는 정수를 찾아내고 , 그 홀 수개 존재하는 정수의 개수를 반환하라.
-- 틀 설계
이 문제는 실제로 해당 정수가 몇개가 존재하냐가 아니라,
해당 정수까지 몇개의 숫자가 존재하냐를 구하는 누적합을 구하여서 알아내는 문제이다,
이것은 어떠한 점을 이용해서 알 수 있냐면 , 한 정수만 홀 수개가 존재하고 , 나머지는 홀 수개가 존재하지 않는다는 점에
비롯해서 알 수 있는 내용이다 , 왜냐하면 홀 수개인 정수를 지나는 그것보다 크거나 같은 정수라면 누적합의 개수가 홀 수 일것이기 때문이다.
그래서 이 점을 이용해서 , 누적합을 구하고 , 누적합의 홀수가 시작되는 지점을 찾아낸다면?
그 시작되는 지점이 바로 홀수인 정수이다 , 그리고 그 정수의 개수는 해당 정수의 누적합 - 해당 정수 - 1의 누적합 으로 구할 수 있다.
그래서 , 더미가 주어졌을 때 , 여기에 어떤 숫자가 몇개가 들어가는지는 정확히 모르지만 , 하나의 정수 이하의 숫자가 몇개 들어가있는 지는 알 수 있다.
이렇게 해서 , count 라는 함수를 만들어서 , 누적합을 구할 수 있는 알고리즘을 짜놓으면 된다, 여기서 중요한 점은 heap[i][1] , number 를 비교해서 둘 중에
더 작은 것을 이용해서 heap[i][0] 과 구간을 구한 뒤 heap[i][2] 로 나눠야 하고 , 그리고 당연하게도 number 는 heap[i][0] 보다 크거나 같아야 한다.
왜냐하면 heap[i][0] 은 정수더미의 시작 숫자니까 이것보다 number 가 작으면 이 더미에는 해당 정수보다 큰 숫자들만 들어가있다는 말이니까 말이다.
그래서 이런식으로 누적합을 구하면서 , 홀수 시작점부터 오른쪽은 전부 다 홀수 , 왼쪽은 전부 짝수라는 점을 이용해서 이분탐색을 이용하면
홀수를 만나면 왼쪽으로 , 짝수를 만나면 오른쪽으로 가면서 , 맨 마지막에 왼쪽으로 간 지점 , 그것을 찾으면 된다.
맨 마지막에 홀 수를 만난 것이니까 말이다 , 그럴려면 조건문은 while(left <= right) 즉 left == right 인 지점도 검사를 해야지 정확하게 구할 수 있다.
그래서 홀 수인 숫자가 없다면 , 오른쪽으로만 갔기 때문에 answer 는 갱신되지 않았다 , 즉 answer 가 초기값과 같다면
그것은 홀 수인 숫자가 없다는 것을 의미하고 그렇지 않다면 결과가 구해졌다는 것이니 , answer , count(answer) - count(answer - 1) 로 해당 정수와
해당 정수의 개수를 구할 수 있는 것이다.
 */
public class Main {
    public static int[][] heap;
    public static int n , max = 0 , min = Integer.MAX_VALUE;
    public static long count(long number){
        /*
        heap 배열을 이용해서 누적합을 구한다.
        0번째부터 n - 1 번째 배열까지 돌면서
        sum += (Math.min(number , heap[i][1]) - heap[i][0]) / heap[i][2] + 1;
        을 해준다.
        여기서 number 와 heap[i][1] 중에서 하나를 작은 값을 고르는 이유는
        예를 들어서 number 가 4이고 , heap[i][1] 이 5라고 가정해보자 그리고
        heap[i][0]가 2 이고 , heap[i][2] == 1 이라고 가정하면
        이번 누적합에다가 더할 수는 실질적으로 2 3 4 이다 ,
        하지만 그냥 그런거 고려없이 무지성으로 heap[i][1] 을 해버리면 2 3 4 5 를 해서
        4개를 추가하게 된다. 그렇기 때문에 number 가 heap[i][1] 보다 작은 경우를 고려해서
        Math.min 으로 작은 것과의 구간합을 구해야한다.
        number 가 큰 경우도 ,  3 4 일댸 , 2 3 이여야 하는데 , 실질적으로 number 값을 따라서 2 3 4 이렇게 할 수도 있다
        애초에 이번 더미는 4는 못들어가는 데 말이다, 이러한 것을 신경써서 Math.min 을 사용한 것이다.
         */
        long sum = 0;
        for(int i = 0; i < n; i++){
            if(number >= heap[i][0]) sum += (Math.min(number , heap[i][1]) - heap[i][0]) / heap[i][2] + 1;
        }
        return sum;
    }
    public static boolean check(long number){
        /*
        해당 값의 누적합이 , 짝수이면 false , 홀수이면 true 를 반환한다.
         */
        long count = count(number);
        if(count % 2 == 0) return false;
        return true;
    }
    public static void binarySearch(){
        /*
        여기서 min , max 값으로 초기 left , right 값을 잡아주고,
        누적합을 이용하여서 , 현재 mid 값이 누적값이 홀수이다.
        왼쪽에다가 넣어주면서 mid - 1 해주고 누적값이 짝수이다. 그러면
        left = mid + 1 해주면서 넘어간다.
        최종적으로 answer 값이 초기값 그대로면 NOTHING 을 출력하면 되고
        그렇지 않으면 정수와 해당 정수의 개수를 누적합의 count(해당 정수) - count(해당 정수 - 1) 로 구하면 된다.
         */
        long left = min;
        long right = max;
        long answer = 0;

        while(left <= right){
            long mid = (left + right) / 2;
            if(check(mid)){ // 홀수이면
                answer = mid;
                right = mid - 1;
            }else{ // 짝수이면
                left = mid + 1;
            }
        }

        if(answer == 0){
            System.out.println("NOTHING");
        }else{
            System.out.println(answer + " " + (count(answer) - count(answer - 1)));
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(input.readLine());

        heap = new int[n][3];

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < 3; j++){
                heap[i][j] = Integer.parseInt(st.nextToken());
            }
            max = Math.max(max , heap[i][1]);
            min = Math.min(min , heap[i][0]);
        }

        binarySearch();
    }
}