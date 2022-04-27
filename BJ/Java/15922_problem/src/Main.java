import java.util.*;
import java.io.*;

// 15922 : 아우으 우아으이야!!
/*
-- 전제조건
1. 그냥 2차원 평면 위에 선이 주어진다.
2. 선들은 겹칠 수 있다.
3. 선들은 [x , y] 쌍으로 주어지고 x < y 이다. 그리고 x는 오름차순으로 주어진다
4. 그래서 선들의 겹친 부분은 제외하고 선의 길이의 총합을 구하라
-- 틀 설계
1. 주어지는 선의 개수를 입력 받는다.
2. 그 다음 for문에서 선의 x , y를 받는다.
3. 이것을 만일 이전의 선에 후에 받은 선이 포함이 되면 그것도 고려해서 바로바로 ans에다가 더한다.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(input.readLine());
        int ans = 0;

        int start = 0;
        int finish = 0;

        /*
        1. 일단 평범한 상황들은 이전의 y보다 x가 큰 경우이다.
        2. 이런 경우에는 이전의 ans += finish - start;를 해주고 start = 를 현재의 x 그리고 finish를 y로 바꿔준다.
        3. 그리고 만일 이전의 finish 가 x보다 큰 경우는 이제 두가지의 경우로 나뉘게 된다
        4. finish가 이번의 y보다 커서 그대로 유지 될 것인가 아니면 finish가 지금의 y보다 작아서 교체될 것인가
        5. if(finish > x) -> 경우에서 추가적으로 if(finish < y) 이 경우에만 finish 가 바뀌면 되고 아니면 그대로 가면된다.
        6. 이제 특별한 상황인 마지막 상황을 봐야하는데 마지막은 finish 가 x 보다 작으면 이전에 score += finish - start 를 해주고.
        7. 만일 크면 finish < y 보다 크냐 아니면 아니면 그냥 같거나 작냐 finish 가 이거면 finish = y 해주고 score += finish -start 해주면 된다. 마지막으로.
         */

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            if(i == 0){
                start = x;
                finish = y;
            }
            if(i == n - 1) {
                if(finish >= x){
                    if(finish < y){
                        finish = y;
                    }
                    ans += finish - start;
                }else{
                    ans += finish - start;
                    ans += y - x;
                }
            }else{
                if(finish >= x){
                    if(finish < y){
                        finish = y;
                    }
                }else{
                    ans += finish - start;
                    start = x;
                    finish = y;
                }
            }
        }
        System.out.println(ans);
    }
}

