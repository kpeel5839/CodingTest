import java.util.*;
import java.io.*;

// 6064 : 카잉 달력

/*
-- 전제 조건
y 와 x 가 주어지고 , 카잉 달력은 <x:y> 로 출력한다.
카잉제국 사람은 <M:N> 이 세상의 종말이라고 생각한다. 세상의 종말의 날이라고 말이다.
근데 x < M 이면 x' = x + 1 이고 이 외의 경우 , 즉 x가 M과 같으면 , x = 1 로 돌아간다.
y도 동일한다 하지만 y는 비교대상이 N이다.
-- 틀 설계
당연히 반복문을 돌려서 보면 쉽게 되겠지만 , 그렇게 하면 테스트 케이스도 많고 , M , N 도 40000이라는 큰 값이기에
수학적인 연산을 사용해야 할 것 같다.

일단 , 간단하게 한번 해보자.
어떤식으로 이동하는지

이게 되는지 안되는지 보다,
답을 어떻게 구하는지 알게되면 , 되는지 안되는지에 대한 판단도 가능할 것 같다.


 */
public class Main {
    public static int T , N , M;
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        T = Integer.parseInt(input.readLine());
        int count = 0;
        while(count != T){
            st = new StringTokenizer(input.readLine());

            M = Integer.parseInt(st.nextToken());
            N = Integer.parseInt(st.nextToken());
            int goalX = Integer.parseInt(st.nextToken());
            int goalY = Integer.parseInt(st.nextToken());

            int ans = 0;
            int y = 0;
            int x = 0;

            // test case 를 통해서 , 마지막 해에 도달했을 때의 번째수를 나타내는 것이 아닌
            // 주어지는 X , Y에 도달하는 경우를 의미하는 것이고
            // y , x 는 0 으로 시작한다.

            // 분할 정복으로 풀 수 있을 것 같다.
            // 하나를 고정치로 잡고 구하면 구할 수 있지 않을까?
            // 예를 들어서 0 같은 경우는 일단 9까지 더해놓고서
            // 계속 이제 거기서부터 N을 더하는 것이다.
            // 그러면 다음 숫자는 (9 + N) % M 이 될 것이다.
            // 이렇게 계속 탐색하면서 , 안되는 경우를 생각해야 한다.

//            while(!(x == goalX && y == goalY)){
//                y = y < N ? y + 1 : 1;
//                x = x < M ? x + 1 : 1;
//                System.out.println("x : " + x + " y : " + y);
//            }

            boolean[] visited = new boolean[M + 1];

//            y = goalY;
            x = goalY % M;
            ans += goalY;
            visited[x] = true;
            boolean find = true;
//            System.out.println("x : " + x + " y: " + y);

            while(!(goalX == x) && !(M == goalX && x == 0)){
                x = (x + N) % M;

//                System.out.println("x : " + x + " y: " + y);

                ans += N;

//                if(M == goalX && x == 0) break;

                if(visited[x]){
                    find = false;
                    break;
                }

                visited[x] = true; // 처음 방문이면 방문처리를 해준다.
            }

            // 하나의 숫자를 고정하고서 진행하기 때문에 , 동일한 x 가 오면 동일한 상황이고 , 그렇다는 것은 해가 없다라는 것이다.
            // x == M , y == N 일 떄 오류가 있어
            // 이 오류는 내가 간과한 점이 있었어서 발생했었다 , x == M 일 때에 , 당연하게도 % M 으로 하게 되면
            // x 값으로 10은 오지 못하게 되고 , 바로 0으로 갱신된다 , 하지만 0 == M 이랑 다름 없는 논리구조를 가진다 (이 문제는)
            // 그래서 x == 0 일 때에는 10으로 하고 , 나머지는 동일하게 했다 만일 여기서 11이 오면 그냥 x == 1 이니까
            // x >= M 일 때에는 x == 1 로 바꾸니까 그렇다 , 그러니 M과 동일할 때만 제외하면 modular 연산을 그냥 진행하면 된다.
            // 근데 다른 테스트 케이스가 이상하게 나와서 , 그냥 M == goalX 일 때만 예외 처리해주었음
            // 간과 했던 점이 또 있는 것이 , 처음 부터 답이 맞는 경우를 생각안함
            // 그러니까 , 전처리 과정에서 맞는 경우를 생각을 안했음 , 그래서 while 문에다가 조건을 넣어줘서 해결하였음
            if(!find) ans = -1;
            output.write(ans + "\n");
            count++;
        }

        output.flush();
        output.close();
    }
}