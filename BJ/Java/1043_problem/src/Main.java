import java.util.*;
import java.io.*;

// 1043 : 거짓말
/*
-- 전제조건
지민이는 거짓과 , 진실을 섞어서 얘기함
지민이가 거짓말 쟁이로 소문나기는 싫음 , 그래서 진실을 알고 있는 사람 앞에서는 무조건 진실을 얘기해야함
근데 이게 예외 사항은 , 거짓이나 , 진실을 들은 사람들은 이것을 얘기를 한번 들은 사람은 무조건
처음 들었던 애기를 들어야 함 , 즉 진실을 알고 있던 사람은 무조건 진실을 들어야 하지만
이전에 아무것도 모르던 애들은 처음 들은 얘기가 걔내들의 진실이다.
이 경우에 모든 파티를 참여하였을 때 지민이가 거짓말쟁이가 되지 않으면서 가장 많은 거짓말을 할 수 있는 파티 개수의 최댓값을 구해야함

절대로 브루트 포스 , 즉 dfs는 안됨 , n , m 이 50까지 나올 수 있기 때문에
dfs로 하게 되면 2^50 이다 , 이 값은 1000조가 넘는다. 그렇기 때문에 이렇게 하면 컴퓨터 터진다.

여기서는 union , find 함수를 써야함을 카테고리에서 힌트로 얻을 수가 있다.
-- 틀 설계
일단 파티에 참석하는 순서? 중요하지 않다.
끝까지 갔을 때 한명이라도 거짓말을 들었냐 , 다른 이야기를 들었냐가 중요하다.
그렇다는 것은 순서대로 진행해도 아무문제가 없다는 의미가 된다.

해당 파티에 진실을 알고 있는 자가 없다면 무조건 거짓말을 한다는 가정하에 진행해보자.

예제 4
4 5
1 1
1 1
1 2
1 3
1 4
2 4 1

진실 알고 있는 사람 - 1
거짓을 알고 있는 사람 - null

첫번째 파티
1 진실을 얘기함

진실 알고 있는 사람 - 1
거짓을 알고 있는 사람 - null

두번째 파티
2 거짓을 얘기함

진실 알고 있는 사람 - 1
거짓을 알고 있는 사람 - 2

세번째 파티
3 거짓을 얘기함

진실 알고 있는 사람 - 1
거짓을 알고 있는 사람 - 2 3

네번째 파티
4 거짓을 애기함
진실 알고 있는 사람 - 1
거짓을 알고 있는 사람 - 2 3 4

다섯번 째 파티
4 1 두 사람이 아는 사실이 다름 , 4에게 거짓말을 할때 까지 올라가서 다시 거짓말을 하지 말아야함

진실 알고 있는 사람 - 1 4
거짓을 알고 있는 사람 - 2 3

최종 거짓말 종수 - 2번

다시 이렇게 생각해보자.
결국 힌트를 얻었는데 , 이런식으로 설계하면 될 것 같다.
진실을 아는자 , 진실을 알지 못하는자 이 두 집단으로 먼저 나눈다.
그 다음에 파티를 받으면서 진실을 아는자와 같은 집단에 있다면 ? 그 해당 진실을 아는자와 연결해준다.
그러니까 진실을 아는자와 같은 파티에 있다면 이 사람은 진실을 무조건 알게되는 자이다.

그 점을 이용하면 될 것 같다.
예를 들어서 1 2 번이 진실을 알고 있고 , 3 4번은 진실을 모른다.
그렇기 때문에 1 2 번이 있는 파티는 즉 무조건 진실을 들어야 하는 파티이다 , 왜냐하면 지민이는 거짓말 쟁이가 될 수가 없으니까 , 근데 만일 1 2 번과 한번이라도 3 4 번이 파티에 껴있다면
즉 얘내들도 진실을 아는자인 것이다 왜냐하면 1 2 번이 무조건 진실을 들어야하니까 , 내가 이거를 왜 생각을 못했지??

그럼 일단 입력을 받으면서 , 본인의 파티에 진실을 아는자가 있는 지 확인한다.
있다면 얘내들도 그냥 진실을 아는자로 여겨야 하기 때문이다.

근데 이러면 문제가 진실을 모르던 애들이 진실을 아는 아이들로 바뀌게 되면 거기서 이전 파티에서도 수정사항이 존재한다.
위에서 말한 예를 들어서 경우를 들어보면

3 4
1 2 3

이런식으로 오게 되면 3만 1 2 사이에 껴져 있어서 진실을 알지만
실질적으로 3은 위의 파티에서도 그러면 진실을 들어야하기 때문에 , 4도 결국 진실을 아는 자가 되어야 한다는 것이다.

이러면 이렇게 해도 되지 않을까?
이차원 배열로 만들어서 파티를 받을 때마다 , 이중 포문으로 같은 파티에 있는 애들을 relate[본인][파티원들 번호] = 1 로 다한다.
파티마다 , known[애들 인원] 으로 해서 , 진실을 아는 애들은 1로 바꿔준다.

그리고서 이따 파티 받을 때 known 한 애가 본인 파티에 껴있으면 , 다 돌면서 relate[본인][파티원들 번호] = 1 인 것을 찾아서 known[파티원들 번호] = 1로 바꿔주는데 여기서
재귀 적으로 진행 되어야 한다. known[파티원들 번호] = 0 -> 1로 바뀐애들은 다시 relate[파티원 번호][파티원들 번호] 로 1인 애를 찾을 때 마다 재귀적으로 다 1로 바꿔주면 된다.

한번 해보자.
relate , known 을 선언해주고, 일단은 입력을 받으면서 relate 들만 전처리를 해놓는다.
그 다음에 진실을 아는애들 받은 순서대로 find 에다가 보내준다.
그러면 재귀적으로 relate를 변경시키고
마지막으로 파티목록을 순서대로 돌면서 known 계속 검사하면서 가다가 known = 1인 애들이 하나도 없으면
ans++ 해주면 된다.

-- 해맸던 점
known 처리에서 , if(known[i] == 0) find(i) 를 호출하였는데
그 과정에서 known[i] = 1 을 이 find 호출하기 전에 해버리는 바보같은 실수를 함
근데 설게는 맞았고 코드 줄일려고 {} 안쓰다가 실수났음
 */
public class Main {
    public static int[][] relate , party;
    public static int[] known , knowTrueFriend;
    public static int n , m , ans = 0;
    public static void find(int number){
        /*
        여기서는 number로 넘겨 받으면 자신과 연관된 애가 있는지 확인한다.
        그리고서 연관된 애가 있다 그러면 얘가 known이 0인지 확인한다.
        known이 0이라면 재귀적으로 걔를 호출한다.
        만일 1이라면 굳이 호출하지 않는다 , 왜냐하면 이미 진실을 알고 있는 애고 , 얘는
        알아서 실행될 것이기 때문이다.

        그러면 한번 해보자.
        일단 반복문에서 relate가 1인지 확인해야한다.
        relate[number][i] == 1 이면 얘의 known을 검사해야한다.
        만일 0이라면 재귀적으로 number를 타고 들어간다.

        내가 딱 방문하였을 때 1이라면 , 걔는 이미 진실을 알고 있는 애임 , 새롭게 진실을 알게 된 애가 아니고
        만일 방문하였을 때 1인데 진실을 처음에는 모르고 있던 애라고 하더라도 이미 재귀적으로 호출되서 다시 호출 안해도 됨

        왜냐하면 내가 이미 진실을 알기 때문에 find에 호출되었다.
        그러고서 본인과 연관이 있는 , 파티를 같이 참석하는 사람이면 여기서 찾게 될 것이다.
        그것이 relate[number][i] == 1 이라는 조건이기 때문에, 근데
        이 사람이 이미 1이다? 내가 1로 바꾸기 전에도 1이다? 그 말은 즉 , 이미 이 사람은
        진실을 알고 있는 사람이라는 것, 그러면 knowTrueFriend 에서 호출되거나, 혹은 이미 호출이 되어서 본인과 연관 되어 있는 인원을 찾고 있을 것이다.

        그리고 (known[i] == 0) 일때만 호출하지 않으면 무한 재귀호출이 된다.

        그러니까 이미 처음에 입력받을 때부터 known이 1이였거나 , 아니면 재귀호출 하면서 known 이 1이 된 상황
        이 두가지의 상황만이 존재하니까 , 어떻게든 find로 호출이 되는 것이다.
        그렇다는 것은 본인과 연관된 파티 인원들 모두를 탐색해서 바꿀 수 있는 것이다.
          */
//        System.out.println(number);

        for(int i = 1; i <= n; i++){
            if(number == i) continue;
            if(relate[number][i] == 1){
                if(known[i] == 0) {
                    known[i] = 1;
                    find(i);
                }
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        n  = Integer.parseInt(st.nextToken());
        m  = Integer.parseInt(st.nextToken());

        relate = new int[n + 1][n + 1];
        party = new int[m][]; // 파티의 인원은 항상 달라서 나중에 붙혀줄 것임
        known = new int[n + 1];

        st = new StringTokenizer(input.readLine());
        int knowTrueFriendCount = Integer.parseInt(st.nextToken());
        knowTrueFriend = new int[knowTrueFriendCount];

        for(int i = 0; i < knowTrueFriendCount; i++){
            knowTrueFriend[i] = Integer.parseInt(st.nextToken());
            known[knowTrueFriend[i]] = 1; // 아는 친구들 known 에다가 처리
        } // 친구 까지 받아줌


        for(int i = 0; i < m; i ++){
            st = new StringTokenizer(input.readLine());
            int size = Integer.parseInt(st.nextToken());
            party[i] = new int[size];

            for(int j = 0; j < size; j++){
                party[i][j] = Integer.parseInt(st.nextToken());
            } // 연관된 애들 작업할 것임

            for(int j = 0; j < size; j++){
                for(int c = 0; c < size; c++){
                    if(j == c) continue;
                    relate[party[i][j]][party[i][c]] = 1; // 본인 파티에 있는 애들 다 연관
                }
            }
        }

        // 여기 까지 하고 연관관계 한번 출력해보자.
//        for(int i = 1; i <= n ;i++){
//            System.out.println(Arrays.toString(relate[i]));
//        }
        // 전처리 잘 되었음

        for(int i = 0; i < knowTrueFriendCount; i++){
//            System.out.println("call the find number: " + knowTrueFriend[i]);
            find(knowTrueFriend[i]);
        }

//        System.out.println(Arrays.toString(known));

        for(int i = 0; i < m; i++){
            boolean able = true;
            for(int j = 0; j < party[i].length; j++){
                if(known[party[i][j]] == 1){
                    able = false;
                    break;
                }
            }
            if(able) ans++;
        }

        System.out.println(ans);
    }
}
