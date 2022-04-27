import java.util.*;
import java.io.*;

// 11812 : K진 트리

/*
-- 전제 조건
자식을 K개 가질 수 있는 트리를 K진 트리라고 하고 , 총 N개의 노드로 이루어져 있는 K진 트리가 주어진다.

트리는 그냥 순서대로 만들어진다 , 즉 이전 높이에 있는 트리가 전부 채워져야지만
다음 트리의 높이를 구성할 수 있다.

노드의 개수 N , K 가 주어졌을 때 , 두 노드 x 와 y 사이의 거리를 구하는 프로그램을 작성하시오.

첫째 줄에 N , K 가 주어진다.
그리고 거리를 구해야 하는 노드 쌍의 개수가 주어진다.

총 Q개의 결과를 출력하면 된다.
-- 틀 설계
당연하게도 N의 개수가 10 ^ 15 이기 때문에 실제로 tree 를 구성하는 것은 미친짓이다.
메모리 초과는 물론이고 , 시간초과가 날 것이다.

우리에게는 N , K 가 주어진다.
그리고 노드 쌍의 개수가 최대 10만개가 주어지니,
무조건 적으로 빠른 시간 내에 해당 노드 간의 거리를 구할 수 있어야 한다.

일단 tree 는 실제로 구성하는 것이 아닌,
수학적인 연산을 진행해야 할 것 같다.

깊이를 계산해야 한다.
그리고 , 공통 조상을 계산해내야 한다.
그래야지 일단 높이를 맞춰 놓은 다음에 , 공통 조상을 만날 때까지 올라가서 공통 조상을 만나면
해당 공통 조상까지의 거리 * 2 + 높이 맞출 때 올라간 높이 를 진행하면 구할 수 있다. (두 노드간의 거리)

일단 번호는 순서대로이다.
그리고 1번 부터 시작한다 , 해당 노드의 깊이를 먼저 계산할 수 있어야 한다.

번호가 주어졌을 때 , 어떤식으로 계산이 가능할 까?
좀더 트리를 구체적으로 더 작성해보자.

                   1
          2        3         4
       5  6  7  8  9  10  11 12 13
   14 15 16 ....

여기서 주어진 숫자의 depth 를 한번 정리해보자.
1 = 0
2 = 1
3 = 1
4 = 1
5 = 2
6 = 2
7 = 2
8 = 2
9 = 2
10 = 2
11 = 2
12 = 2
13 = 2
14 = 3
15 = 3
16 = 3

이런식으로 구할 수 있다.
일단 확실한것은 3의 제곱근 마다 끊긴다.
그렇다라는 것은 0 == 1
1 == 1 + 3
2 == 4 + 9
3 == 13 + 27

즉
1 < x <= 1 == 0
1 < x <= 4 == 1
4 < x <= 13 == 2
13 < x <= 40 == 3

이러한 식으로 정리가 가능하다.

이것을 재귀적으로 풀어내면 ? 굉장히 쉽게 계산이 가능할 것 같다.
현재 value 를 가지고 dfs 를 통해서 값을 계속 늘려나가면 빠르게 가능할 것 같다.

그럼 이제 조상을 찾는 방법이 중요하다.
높이는 이러한식으로 빠르게 구할 수 있다 , 하지만 조상을 찾는 것은?

depth 를 구하는 공식은 그냥 쉽게 작성하였다.
근데 이제 조상을 찾는 것이 중요하다.

                   1
          2        3         4
       5  6  7  8  9  10  11 12 13
   14 15 16 ....

이걸 인용해서 한번 봐보자.
일단 내가 생각한 방법은 이것이다 , 해당 depth 를 알면 해당 depth 에 노드가 몇개 있는지를 알아낼 수 있다.
그러면 본인이 몇번째 블럭인지도 알 수 있다.

즉 본인이 몇번째 블록인지 안다라는 것은 본인 부모의 depth 의 개수를 알아낸 다음에,
시작 번호 + 본인이 몇번쨰 블록인지 구했으니 + 몇번째 블록 이렇게 진행하면 된다.

그렇다라는 것은 일단 번호를 가지고 구할 수 있다.
그것을 구하려면 일단 배열로
해당 노드의 개수가 k 진 트리로 나타낼 때 , depth 가  어느 정도인지 확인하는 것이 중요하다.
그러면 해당 depth 를 확인하고 그 depth 까지 배열로 노드의 개수를 나타내면 쉬울 것 같다.
즉 , 해당 배열이 나타내는 것은 해당 depth 의 시작 노드 번호를 나타내면 되는 것이다.

-- 해맸던 점
K == 1 일 때 , 너무 오래 걸린다는 점이 있었다.
왜냐하면 arrSize 도 너무 크고 , findDepth 진행할 때에도 depth 가 너무 깊어서 잘 안되는 점이 있었다.

그래서 , K == 1 일 때에는
startNumber 라는 arr를 생성하지도 않고 , findDepth 를 진행하지도 않으며
서로간의 거리를 계산할 때에는 그냥 Math.abs(a - b) 로 진행하였음
 */
public class Main {
    public static long N , K , Q;
    public static long[] startNumber;
    public static long calDistance(long a , long b){
        /*
        해당 함수는
        findDepth 를 이용해서 , 둘 사이의 depth 차이를 구하고 ,
        해당 depth 만큼 올라간다 , 근데 그것은 Math.pow 를 이용해서 2 ^ n 만큼 점프하면서 올라간다.
        그래야지 시간을 단축시킬 수 있다.

        일단 a , b 의 서로의 높이를 구한다.

        일단 서로의 높이를 구한다
        그 다음에 더 높이 있는 아이의 높이는 그대로 두고 (즉 depth 가 높은애를 바꿔야함)
        낮게 있는 애들을 올려준다.

        그 다음에 같은 높이까지 올려줬으면
        이제 서로 parent가 같아질 때까지 계속 올린다.

        k == 1 일때 너무 오래 걸린다.
        이 부분을 어떻게 극복할 수 있을까
        findParent 부분을 수정해야한다.
        그래야지 k == 1 일 때에도 , 빠르게 접근이 가능하다.

         */

        int depthA = findDepth(0 , a , 0);
        int depthB = findDepth(0 , b , 0);

        int res = 0;

        if(depthA > depthB){ // a 를 올려야한다.
            while(depthA != depthB){ // 같지 않을 때까지 진행한다.
                a = findParent(a , depthA--);
                res++;
            }
        }

        if(depthB > depthA){ // b 를 올려야한다.
            while(depthB != depthA){
                b = findParent(b , depthB--);
                res++;
            }
        }

        if(a == b) return res;

        while(a != b){
            a = findParent(a , depthA--);
            b = findParent(b , depthB--);
            res += 2;
        }

        return res;
    }
    public static long findParent(long number ,int depth){
        /*
        findParent 는 number 와 depth 를 입력하면 해당 number의 부모가 무엇인지 반환해준다.
        그리고 그냥 순서대로 올라가면 될 것 같다.
        짜피 완전 K진 트리라서 많아봤자 40번 이다.
        그냥 순서대로 찾으면서 올라가도
        10만 * 40 이니까 400만 혹은 천만 정도까지밖에 안 갈 것이다.

        그러니까 해당 정보를 가지고 다음 number의 부모 노드 번호를 알아내면 된다.
        일단 넘어온 노드의 시작 노드를 알아야한다.
        예를 들어서 K = 3 인 경우에 9번이 들어왔다고 가정해보자.
        그러면 일단 depth 도 같이 넘어오니까
        number - startNumber[depth] 를 하여서 완전 날것의 number를 만들어준다.
        그 다음에 해당 결과를 / K 를 진행하여서 해당 노드가 몇번째 블럭인지 알아낸다.

        그 다음에 그 블럭을 가지고 startNumber[depth - 1] 에다가 더해주면 해당 노드의 부모 노드를 알 수 있다.
        그렇게 하면 한 식으로 정리가 가능하다.
         */
        return startNumber[depth - 1] + (number - startNumber[depth]) / K;
    }
    public static int findDepth(long sum , long number , int depth){
        /*
        일단 sum을 처음에는 0으로 넘겨준다.
        그 다음에 sum < number <= sum + Math.pow(K , depth) 로 계산하면 된다.
        Math.pow 는 계산해놓으면 좋을 듯하다.
        그 다음에 범위내에 맞으면 그냥 바로 반환하고 아니면
        다음으로 넘긴다.
        depth + 1 , 과 sum + Math.pow 로 말이다.
         */
        long now = (long)Math.pow(K , depth);
        if(sum < number && number <= sum + now) return depth;
        return findDepth(sum + now , number , depth + 1);
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(input.readLine());

        N = Long.parseLong(st.nextToken());
        K = Long.parseLong(st.nextToken());
        Q = Long.parseLong(st.nextToken());

        long maxNode = (long)Math.pow(10 , 15);
        int arrSize = 1;
        if(K != 1) arrSize = findDepth(0 , maxNode , 0) + 1;

        startNumber = new long[arrSize];

        // 여기서부터는 startNumber 배열의 0번째 인덱스부터 차례대로 찾아 나가는데,
        // 계속 long sum 으로 계산해가면서 진행하면 된다.
        // K를 이용해서 long sum 을 더해가면서 진행하면 된다.

        startNumber[0] = 1;
        long sum = 1;
        if(K != 1) {
            for (int i = 1; i < startNumber.length; i++) {
                sum = sum + (long) Math.pow(K, i - 1);
                startNumber[i] = sum;
            }
        }

        for(int i = 0; i < Q; i++){
            st = new StringTokenizer(input.readLine());
            long a = Long.parseLong(st.nextToken());
            long b = Long.parseLong(st.nextToken());

            if(K == 1) output.write(Math.abs(a - b) + "\n");
            else output.write(calDistance(a , b) + "\n");
        }

        output.flush();
        output.close();
    }
}
