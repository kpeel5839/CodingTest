import java.util.*;
import java.io.*;

// 2042 : 구간 합 구하기

/*
-- 전제조건
n 수의 개수가 주어지고 m , k가 주어진다. m은 수의 변경이 일어나는 횟수이고
k 는 구간의 합을 구하는 횟수이다.
둘째 줄부터는 n개의 수가 주어진다.
그 다음부터는 3개의 수가 주어지는데 1인 경우에는 b 번째 수를 c로 바꾸는 행위고,
2인 경우에는 b , c 수까지 구간합을 구하는 것이다.
-- 틀 설계
세그먼트 트리를 이용해서 , 풀 것임
세그먼트 트리는 start ~ end 까지의 합을 읿부 저장해놓고 있는 트리의 형태이다.
그리고 부모노드로부터 반으로 나누어져서 0 ~ 9 가 있다면 자식의 합의 범위는 0 ~ 5 , 6 ~ 9 이렇게 나뉘게 된다
그래서 계속 이렇게 나뉘다가 , 리프노드로는 실제로 0 , 1 , 2 ... n 번째의 인덱스들이 있는 것이다.
그래서 원래 쉽게 구간합을 계속 변경하려면 계속 바꾼 다음에 다 돌면서 더해보고 , 아니면 s[i] 배열을 만들어서
구간 합들을 다 저장해놓은 다음에 빼는 방법이 있다 , 예를 들어서 , s[i] 까지가 0 ~ i 까지 더한 합이면
i ~ j 까지의 합을 구하려면 result = s[j] - s[i - 1] 을 하면 된다.
이런식으로 구할 수가 있는데 이러면 시간이 오래걸린다 , 하지만 합을 트리로 유지해놓고서 logN의 속도로 합을 구하고 , 수정을 반복하면?
훨씬 빠른시간내에 해결이 가능한 것이다.

그래서 세그먼트 트리를 사용한다.
-- 해맸던 점
update에서 실질적으로 value[] 부분을 바꾸지 않았었다.
그리고 입력값 받을 때에도 Long.parseLong 으로 받아야하는데 Integer.parseInt로 받았었다.
 */
public class Main {
    public static int n , m , k;
    public static long[] tree;
    public static long[] value;
    public static long init(int node , int start , int end){
        /*
        start == end 라는 것은 리프노드에 다다랐다는 것이고 그때 그냥 a[node] 를 반환해주면 된다.
        그리고 이전에는 계속 init 함수를 재귀 호출해서 , 그것을 계속 tree[node] = init(..) + init(..) 해주는 형식으로 해준다.
         */
//        System.out.println("node : " + node + " start : " + start + " end : " + end );
        if(start == end){
            return tree[node] = value[start];
        }
        else{
            return tree[node] = init(node * 2 , start , (start + end) / 2) + init(node * 2 + 1 , (start + end) / 2 + 1 , end);
        }
    }
    public static void update(long index , long diff , int node , int start , int end){
        /*
        update에는 2가지의 경우가 존재한다.
        인덱스에 포함되서 바뀌는 경우 , 혹은 바뀌지 않는 경우
        바뀌지 않는 경우는 그냥 return 해버리면 되고,
        바뀌는 경우는 분기한다.

        근데 이게 start != end 일때까지만 반복한다.
         */
        if(index > end || start > index) return;
        tree[node] = tree[node] + diff;
        if(start != end){ // 겹치지 않는 경우 , 이 경우에는 아직 더 갈수 있는 것임
            update(index , diff , node * 2 , start , (start + end) / 2);
            update(index , diff , node * 2 + 1 , (start + end) / 2 + 1 , end);
        }
    }
    public static long sum(int node , long left , long right , int start , int end){
        /*
        이 구간은 4가지의 경우가 존재함
        left , right 즉 내가 구하려는 구간합이 start , end 보다 크거나
        아니면 start, end 가 더 크거나
        아니면 아얘 나가리거나
        아니면 겹치는 경우
        예외처리를 해주는 경우는 두가지의 경우이다.
        아얘 나가리인 경우에는 return 0 를 해주면 되고
        start , end 가 left ,right 보다 작은 경우는 더 호출하는 것은 비효율 적이기에 tree[node]를 바로 리턴해준다.
        그리고서 return 하면서 재귀적으로 진행하면 된다.
         */
        if(start > right || end < left) return 0;
        if(left <= start && end <= right) return tree[node];
        return sum(node * 2 , left , right , start , (start + end) / 2) + sum(node * 2 + 1 , left , right , (start + end) / 2 + 1 , end);
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(input.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        long h = (1 << ((int)Math.ceil((Math.log10(n) / Math.log10(2))) + 1)); // 첫번째 노드를 1로 가정하고 2^logn 사이즈로 함
        value = new long[n + 1];
        tree = new long[(int)h];

        for(int i = 1; i <= n; i++){
            value[i] = Long.parseLong(input.readLine());
        }

        init(1 , 1 , n);

        for(int i = 0; i < m + k; i++){
            st = new StringTokenizer(input.readLine());
            int judge = Integer.parseInt(st.nextToken());
            long b = Long.parseLong(st.nextToken());
            long c = Long.parseLong(st.nextToken());
            if(judge == 1){ // 변경
                long diff = c - value[(int)b];
                value[(int)b] = c;
                update(b , diff , 1 , 1 , n);
            }
            else{ // 구간합 구하기
                output.write(sum(1 , b , c , 1 , n) + "\n");
            }
        }

        output.flush();
        output.close();
    }
}
