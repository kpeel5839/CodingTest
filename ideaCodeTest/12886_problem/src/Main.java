import java.util.*;
import java.io.*;

// 12886 : 돌 그룹
/*
-- 전체 설계
A , B , C 그룹의 돌 개수가 주어진다.
작은 그룹 , 큰 그룹을 선택하여서 , 작은 그룹은 X , 큰 그룹은 Y라고 칭한다.
그래서 여기서 X = X + X 즉 2배 , 그리고 Y = Y - X 를 해주어서
A B C 를 모든 같은 개수로 만들 수 있으면 1 아니면 0 을 출력하라.
-- 틀 설계
bfs 로 주변에 있는 돌과 , 연관시켜서 , 하나의 돌을 선택해서,
높은 돌 , 낮은 돌 구분을 짓고,
돌의 개수를 변환시켜서 큐에다가 넣어준다 (돌 3개의 값들을)
근데 , 만일 memo[a][b][c] 인데 일단 확실한 것은
큐에 이미 이전에 지났던 a b c 가 또 올 필요가 없다
그렇다는 것은 즉 bfs 과정에서 queue 에서 꺼낼 때 , memo[a][b][c] 에 기록이 되어있다면
그것은 그냥 넘어갈 수 있다는 것이다.

여기서 간과 한 것이 있다.
1 2 2 , 2 2 1 이것들은 공통점이 있다.
즉 2가 2개 , 1이 하나라는 점이 있다.
이걸 어떻게 배열로 표현 할 수 있을까?

사실 돌의 그룹 순서는 중요치않다.
즉 돌의 구성요소로 방문처리를 할 수 있다면?

그럼 이렇게 하는 거 어떨까?
사이즈를 memo[000000000] = 저 자리수가 되게끔 하는 거지
그럴려면 memo = new int[1000000000] 를 하고,
rock.a , rock.b , rock.c 를 순서대로 오름차순으로 정렬한 뒤에
순서대로 문자열로 조합해서 , 방문처리를 하는 것이다.

-- 결과
결국 해답을 보았음 , 내가 생각했을 때에는 완전히 , a , b , c 그룹의 방문처리를 하려면 3차원 혹은
순서는 상관이 없으니 , string 으로 이어 붙혀서 하는 메모제이션 이게 전부라고 생각했음
근데 , 2차원 리스트로 해결이 되는 부분이였고,
maxSize 도 1001 로 되는 것처럼 보이지만 , 어떠한 경우로 인해서 , 1000을 넘어가는 경우가 생기게 된다.
그렇기 때문에 낭낭하게 1501 로 선언해주어야 한다.

솔직히 이번문제는 왜 2차원으로 선언했을 때도 가능한지는 정확히 이해가 가지 않는다.
왜냐하면 10 15 35 이 경우에서 10 과 15 를 바꾸는 것 15 와 10을 바꾸는 것
이것은 분명히 같은 행위이다. 그래서 이 부분에 대한 처리를 해주는 것이 당연하긴 하지만
만일 10 15 35 에서 10 15 를 바꾸는 경우가 있지만
나중에 어떻게 진행하다보면 10 15 25 인 상태에서 10 15 를 바꾸는 경우가 생기지 않을까?
이 경우에는 그러면 예외를 할 수 없는 경우 아닌건가? 싶은 생각이 내 생각이다.
분명히 이것이 되는 이유는 저렇게 시작하였을 때 , 어떻게든 저런 상황이 오지 않을 것이 분명하다.
하지만 이해가 정확히 가지는 않는다.

내가 진짜 너무 잘못생각하고 있었다.
X + X , Y - X 하면 결국 돌의 총합은 무조건 같다.
즉 , 그말은 즉슨 두 숫자가 결정이 되면 , 나머지 숫자는 무조건 한가지의 경우의 수밖에 안되는 것이다.
그래서 memo 를 2차원으로 유지할 수 있는 것이다.
괜히 걸어오면서 잘 못 생각해가지고 못맞췄다.
엄청 쉬운 문제 였는데 ...
그리고 memo가 1501 까지 되는 이유도 여기서 풀리게 된다.
돌의 총합이 1500 이다 최대의 경우
즉 손실이 없고 어떠한 경우까지 가게되면 최대 750 749 1 까지 갈 수 있을 것이다.
그렇게 되면 결국 1498까지 갈 수 있게 될 것이다.
그래서 1501 로 한 것이다.
실질적으로는 1498 까지만 가능할 터이니 , 1500 으로 선언해도 될 것 같다.
한번 실험으로 1499 로 해보아도 괜찮을 것 같다.

실험으로 해보았는데 1499도 가능하다 왜냐하면 최대값은 1498 까지만 가능하니까 당연한 결과이다.

다음에는 조금 더 꼼꼼하게 생각해보자 , 총합이 유지된다는 사실을 조금만 더 생각했으면 알았을텐데 아쉽다.
애초에 X + X , Y - X 인데 왜몰랐지 X를 + 하고 X를 - 하는데 그럼 X - X 이니 변경된 총합은 0이니 , 그대로 유지되는 것이 당연한 것이였는데
15 + 35 = 50
30 + 20 = 50 이걸  왜 머릿속으로 계산했을 때 , 안됐는지 이해가 되지 않는다 너무 당연한건데
 */
public class Main {
    public static int initA , initB , initC , maxSize = 1499;
    public static int ans = 0;
    public static int[][] memo = new int[maxSize][maxSize];
    public static void bfs(){
        /*
        여기서 Rock 을 이용해서 ,
        처음에는 queue.add(new Rock(initA , initB , initC)) 를 해준다.
        그 다음에 , 이제 for 문으로 여러 시도를 해보는 것이다.
        근데 , 이제 되는 경우에 바로 break 즉 a == b && b == c 다? 그러면
        바로 ans = 1 로 바꾸고 바로 break;
        근데 그게 안되면 0 그대로 출력이다.
         */

        Queue<Rock> queue = new LinkedList<>();
        queue.add(new Rock(initA , initB , initC));

        while(!queue.isEmpty()){
            Rock rock = queue.poll();
            // 순서대로 스트링에 붙히면 됨 이제
            if(rock.a == rock.b && rock.b == rock.c){
                ans = 1;
                return;
            }
            int[] rockList = {rock.a , rock.b , rock.c};

            // 여기서 하나를 주체로 정하고 , 주변에 있는 것들을 정해서 , 해야함
            // 그럴려면 일단 2중 루프로 이어지면서 , i == 0 == a, 1 == b, 2 == c 이렇게 의미하고
            // j 도 똑같이 하지만 i == j 일 때에는 , 나가리이다 , 그렇게 하면 된다.
            for(int i = 0; i < 3; i++){
                int small = 0;
                int big = 0;
                for(int j = 0; j < 3; j++){
                    if(i == j) continue;
                    if(rockList[i] == rockList[j]) continue;

                    // rockList[i] == rockList[j] 중 작은 것을 골라내야 한다.
                    int[] innerRockList = rockList.clone(); // deepCopy
                    if(innerRockList[i] > innerRockList[j]) {
                        small = j; // j 가 더작음
                        big = i;
                    }
                    else {
                        small = i; // i 가 더작음
                        big = j;
                    }

                    // 작은 것을 골라냈으면 , 작은 것은 2배로 변경하고 , innerRock 갱신 , 그리고 큰 것은 작은 것 만큼 빼내고 innerRock 갱신
                    innerRockList[big] = innerRockList[big] - innerRockList[small];
                    innerRockList[small] = innerRockList[small] + innerRockList[small];

                    if(memo[innerRockList[big]][innerRockList[small]] != 1) { // 이전에 방문하지 않았을 때
                        queue.add(new Rock(innerRockList[0], innerRockList[1], innerRockList[2]));
                        memo[innerRockList[big]][innerRockList[small]] = 1;
                    }
                }
            }
        }
    }
    public static class Rock{
        int a;
        int b;
        int c;
        public Rock(int a , int b, int c){
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());


        initA = Integer.parseInt(st.nextToken());
        initB = Integer.parseInt(st.nextToken());
        initC = Integer.parseInt(st.nextToken());

        bfs();

        System.out.println(ans);
    }
}