import java.util.*;
import java.io.*;

// 16198 : 에너지 모으기

/*
-- 전제 조건
N개의 에너지 구슬이 일렬로 놓여져 있고 , 에너지 구슬을 이용해서 에너지를 모으려고 한다.
i 번째 에너지 구슬의 무게는 Wi 이고 , 에너지를 모으는 방법은 다음과 같다.

1. 에너지 구슬을 하나를 고른다. 고른 에너지 구슬의 번호를 x라고 한다. (고를 때 첫번째 마지막 에너지 구슬은 고를 수 없음)
2. x번째 에너지 구슬을 제거한다.
3. W(x - 1) * W(x + 1) 즉 , 주변에 있는 에너지 구슬의 곱만 큼 모을 수 있음 , 그래서 첫번째이랑 마지막 구슬은 고를 수 없었던 듯
4. N을 1을 감소시키고 , 에너지 구슬을 1번부터 N번까지로 다시 번호를 매긴다 , 번호는 첫 구슬이 1번 ,.... N - 1 이렇게이다.

N과 에너지 구슬의 무게가 주어졌을 때 , 모을 수 있는 에너지 양의 최댓값을 구하는 프로그램을 작성

-- 틀 설계
4
1 2 3 4

일 때 , 3을 제거하고 , 2를 제거하면 , 12라는 에너지의 최댓값을 얻어낼 수 있다.

이 문제에서는 그냥 , 첫번째 구슬 , 마지막 구슬을 주의하고 , 이 구슬을 선택할지 안할지를 결정하면 될 것 같다.
근데 그 행위를 반복해야 한다.

-- 해맸던 점
sequence + 1 , sequence - 1 을 찾아야 하는데
둘다 모르고 -1 해서 찾아가지고 답이 이상하게 나왔었다 , 그 이외에는 괜찮음
 */
public class Main {
    public static int ans = 0 , N;
    public static int[] marble;
    public static boolean[] visited;
    public static void dfs(int sum , int remain){
        /*
        dfs 에서 확인해야 할 것 , 이게 양쪽에 구슬이 있는 구슬인지
        이 구슬이 이미 선택한 구슬인지 아닌지
        이 구슬을 지금 선택할 것인지 안할 것인지

        양쪽에 구슬이 있는지 알려면 , 이게 몇번째 구슬인지 알아야 하지 않을까?
        그리고 구슬의 남은 개수가 이제 2개 밖에 없으면 에너지를 ans와 비교해서 넣어야함

        이렇게 한번 해보자.
        처음에 입력이 들어오면 함수 내부에서 int 배열을 하나 만들어서(짜피 크기 10이라서 오버헤드 별로 없음)
        순서를 입력해주는 것이다. visited 여부로 따라서 remain 이 2이면 그냥 바로 리턴이라서 상관 x

        그리고서 , 1과 N이 아니면 선택하거나 선택하지 않거나로 하는데 ,
        선택하면 0번째부터 다시 탐색해야하고
        선택하지 않으면 그냥 continue를 해준다.

        선택한 구슬은 visited 처리를 한다.
         */

        if(remain == 2){
            ans = Math.max(sum , ans);
//            System.out.println(sum);
            return;
        }

        int[] sequence = new int[N];
        int count = 1;

        for(int i = 0; i < N; i++){
            if(!visited[i]) sequence[i] = count++; // 선택하지 않았으면 , sequence 에다가 넣어준다.
        }

        count--;

        for(int i = 0; i < sequence.length; i++){ // sequence length 만큼 진행해준다.
            if(sequence[i] == 1 || sequence[i] == count) continue;
            if(sequence[i] == 0) continue;
            visited[i] = true;
            dfs(sum + marble[findSequence(sequence , sequence[i] - 1)] * marble[findSequence(sequence , sequence[i] + 1)] , remain - 1); // 선택하지 않는 경우는 굳이 넘기지 않는다.
            visited[i] = false;
        }
    }

    public static int findSequence(int[] sequence , int find){
        for(int i = 0; i < N; i++){
            if(sequence[i] == find) return i;
        }
        return 0; // 없는 경우 없음
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());

        marble = new int[N];
        visited = new boolean[N];

        st = new StringTokenizer(input.readLine());

        for(int i = 0; i < N; i++){
            marble[i] = Integer.parseInt(st.nextToken());
        }

        dfs(0 , N);

        System.out.println(ans);
    }
}