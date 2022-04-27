import java.util.*;
import java.io.*;

// 10775 : 공항
/*
-- 전제 조건
비행기가 순서대로 들어오고 , 박승원은 최대한 많은 비행기를 영구 도킹하려고 한다.
도킹은 gi 가 주어지는데 , 그 이하의 게이트에만 도킹이 가능하다.
만일 비행기가 들어왔는데 더 이상 도킹을 할 수 있는 위치가 아무것도 없다면 , 더 이상 비행기는 들어오지 못하고 끝난다.
비행기를 가장 많이 도킹을 하려면 어떻게 해야할까?
-- 틀 설계
바로 이전에 푼 친구 네트워크와 비슷한 문제였는데 , 시간이 없어서 그런지 문제 해결 방법이 떠오르지 않았다.
그래서 해결책을 보았는데 , 그리디한 선택과 , 분리집합을 이용하는 것이였다.

일단 그리디한 결정은 , 본인의 게이트가 주어졌을 때 , 가장 좋은 선택은 본인의 게이트를 선택하는 것이다.
그래야지 다음에 더 낮은 수가 들어왔을 때 , 게이트를 선택할 수 있으니까 예를 들어서 2 1 이렇게 들어왔는데
2번이 본인이 선택할 수 있는 게이트 중 가장 낮은 게이트를 선택하여 1 을 선택하게 되면 1이 들어왔을 때 ,비행기가 도킹을 못하게 된다.
그러면 안되니 그리디한 선택은 본인의 게이트 번호부터 순서대로 선택을 하여야 한다.

이 때 , 이 그리디한 결정을 내리기 위해서는 본인의 차선책을 알아야한다.
만일 2번이 들어왔는데 , 2번 게이트는 이미 들어와있다고 , 가정하자
그러면 1번게이트에 들어가야한다.
만일 이게 게이트 번호가 10만으로만 계속 들어온다면?
그러면 10만 * 10만 복잡도는 10만 ^ 2 가 될 것이고 ,그렇게 되면 시간초과가 날 것이다.

그래서 차선책은 n 을 선택했다면 바로 아래인 n - 1이라는 것을 알 수 있다.
근데 만약 n - 1 도 선택이 되었다면 n - 1 의 차선책인 n - 2 라는 것을 알 수 있고 , 이것이 계속 반복된다면?
아무런 대책없이 기계적으로 돌린다면 위와 같은 결론에 도달한다 , 그래서 사용할 방법이 분리집합이다.

해당 게이트가 비어있다면 넣고 , 아니라면 부모에다가 집어넣는다,
근데 만일 부모가 0 이라면 (0번 게이트는 존재하지 않음)
이제 더 이상 비행기를 받지 않으면 되고,

만일 부모가 0이 아니다 , 그러면 union(부모의 - 1 , 부모) 를 진행하여서
부모의 부모를 부모의 - 1 의 부모로 설정하면 된다.

이런식으로 답을 구할 수 있다.

-- 결론
혼자서 생각할 시간이 조금이라도 더 있었으면 생각해낼 수 있었을 것 같은데 조금 아쉽다.
그리고 되게 재밌는 문제였음
본인의 게이트 부터 탐색하는 그리디한 결정에서 비롯해서 차선책을 빨리 찾기위한 분리집합이라는 게 되게 재밌었다.
 */
public class Main {
    public static int[] parent;
    public static int G , P , ans = 0;
    public static int find(int a){
        if(a == parent[a]){
            return a;
        }
        return parent[a] = find(parent[a]);
    }
    public static void union(int a , int b){
        /*
        그냥 b의 부모로 a를 삼으면 된다.
         */
        parent[b] = a;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        G = Integer.parseInt(input.readLine());
        P = Integer.parseInt(input.readLine());

        parent = new int[G + 1];

        // parent 초기화
        for(int i = 0; i <= G; i++){
            parent[i] = i;
        }

        for(int i = 0; i < P; i++){
            // 순서대로 게이트를 받기 시작한다.
            int a = Integer.parseInt(input.readLine());

            a = find(a);
            // 0 이면 끝
            if(a == 0) break;

            // 0 아니면 짜피 find 는 parent[a] == a 인 것을 반환해주기에 그냥 else 로 진행
            else{
                union(find(a - 1) , a); // a - 1의 부모를 넘겨야함
                ans++;
            }
        }

        System.out.println(ans);
    }
}
