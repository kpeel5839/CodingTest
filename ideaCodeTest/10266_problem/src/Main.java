import java.util.*;
import java.io.*;

// 10266 : 시계 사진들
/*
-- 전제 조건
동일한 목적 , 길이를 가진 침들이 (즉 들어온 순서는 상관이 없다라는 것)
n 개 주어질 때 , 다른 각도로 보았을 때 , 두개가 같은 시각을 보게끔 만들 수 있는지 출력하라.
-- 틀 설계
일단 정말 간단한 생각은 360000만 번 진행을 하면서
이게 같은지 안같은지 , 계속 확인해보는 것이다.
하나만 변경하면서

일단 이 방법은 절대로 아니다.
총 연산횟수 720 억 번이다. 그냥하게 되면(최대)

그리고 심지어 계속 값이 바뀌니까 정렬도 계속해줘야 함
말이 안됨
다른 방법을 모색해야 한다.

일단 두개가 같은 경우는 앞뒤의 차이
가 같다라는 것임 즉 0 , 270000은 270000 혹 90000 차이가나고
180000 , 270000 도 똑같다.
시계 방향으로 차이든 , 시계 반대 방향으로 차이든 가능한 경우는 이것이 똑같다.

[1, 2, 3, 4, 5, 6]
[1, 3, 4, 5, 6, 7]

[100, 105, 110, 120, 125, 130, 140]
[200, 205, 215, 220, 225, 235, 240]

[0, 270000]
[180000, 270000]

근데 당장 Arrays.sort 로 작은 값 순으로 나열했다고 하더라도,
답을 구할 수 없음 , 왜냐하면 어느 침과 어느 침이 각도를 회전시켰을 때 매칭이 되는지를 모르니

그렇다고 하나하나씩 맞춰가는 것도 말이 안됨
하나가 증가되면 하나가 증가되어야 하니까

진짜 각도를 돌리는 것처럼 , 논리 구조를 한번 대입해보자.

결국 해답을 봤다.
역시 아직 나는 멀은 것 같다.

근데 코드는 보지 않고 , 방안만 봤다.
방안은 이러하다 , 들어온 시계침을 기준으로 각도를 기록한다.
근데 , 각도를 기록하는데 , arr1 의 각도는 배열의 크기도 2배로 선언을 하고 , SIZE 만큼 더해서도 1을 추가한다(1은 지금 현재 이 각도가 존재한다라는 것이다.)

조금 더 쉽게 , 6개 짜리 배열로 보자
즉 1 3 이 있고 arr1 은
arr2 는 0 4 가 들어왔다고 가정해보자.

그러면 6까지 존재한다라고 하였을 때 , 1 3 이 가르키는 것은 120 도의 각을 이루고 있다.
그리고 0 4도? 120도의 각을 이루고 있다.

그럼 이거는 가르키는 것이 돌리다 보면 같을 수 있다 인간의 눈으로 써 다가가면 각각 3씩을 더해주면 된다.
그러면 1 3 이 된다.

그럼 이 것을 어떻게 찾느냐
1 3 을 그냥 2진 배열로 바꾸면
size = 6 이니까
0 1 2 3 4 5
0 1 0 1 0 0

그리고 0 4 는
1 0 0 0 1 0 이다.
이걸 각 3씩 증가하게 되면

0 1 0 1 0 0
      1 0 0 0 1 0
이렇게 되게 된다.

하나를 맞추면 범위를 넘어가게 된다.

근데 가만히 생각해보면 , 시계는 원이기에 반복이 된다.
그렇다라는 것은 0 1 0 1 0 0 0 1 0 1 0 0 0 1 0 1 0 0 .... 반복이 될 수 있다.

그래서 이것을 이용하여서 arr1 은 2배로 선언할 수 있게 된다.
2배로 선언한 이유는 ? 짜피 arr1 을 2배로 늘리고 , 그 사이에서 arr2 와 같은 패턴을 찾지 못하게 되면
짜피 arr1 이 반복되는 것이기에 똑같은 상황이 계속 이어진다.
그렇기 때문에 arr1를 2배만 늘리면 된다.

그리고 이렇게 하게 되면 ? 이게 몇시를 가르키는 것이 중요한 것이 아니라
이 사이의 거리가 중요한 것이였다.
그렇기 때문에 1이 각각 일치한다면 일치한 것들은 동일한 거리가 떨어져있다라는 것 , 즉 돌려보면 똑같다라는 의미를 가지게 된다.
그래서 위의 경우를 해답을 구하면
0 1 0 1 0 0 0 1 0 1 0 0
      1 0 0 0 1 0

즉 완벽하게 맞게 된다.
그렇게 되면 서로 같다라고 표현할 수 있다.

이해는 되지만 혼자 생각해내기에는 너무 어려울 것 같다.

결론적으로 이 문제는 모든 각도들을 기록하고 , 짜피 시계는 360000 개의 배열이 계속 반복될 수 밖에 없는 형태이다.
원은 계속 돌면 무한적으로 계속 돌 수 있으니까 , 즉 끝이 없다 , 하지만 2번 반복된 arr1 을 탐색하였을 때 ,
못 찾는다면 짜피 다시 똑같은 것들을 돌기 때문에 결과는 같다.
즉 SIZE * 2 짜리의 배열을 만들어서 , 반복되는 시계의 특징을 이용하여 arr[num] 에 넣고 (1 , 0 이 지점에 침이 존재한다는 의미)
arr[num + SIZE] 에다가 넣는 것이다.
그 다음에 만일 완전 일치하는 부분을 찾는다면 그것은 모든 침들이 동일한 거리를 두고 있다라는 것을 의미하게 되고
그렇다라는 것은 내가 위에서 세웠던 가정과 같이 동일한 시각을 가르킨다라고 정의할 수 있음

-- 해맸던 점
배열 크기 , 다 SIZE 로 선언했어야 했는데,
왜냐하면 각도가 SIZE 만큼 존재하고 , 거기에서 pi 를 구해서 arr1 에서 arr2 전체 즉
SIZE 크기의 arr2 를 찾아내야 하기 떄문에
그래서 SIZE 로 선언을 다 하고 , 반복문도 SIZE 까지 돌아야 했는데 , 그것을 안해서 살짝 해맸음
 */
public class Main{
    public static int[] arr1, arr2 , pi;
    public static int N;
    public static final int SIZE = 360000;
    public static String ans = "impossible";
    public static void getPi(){
        /*
        arr2 의 pi를 구해주면 된다.
        접두사와 접미사의 개념을 완벽히 이해하고,
        그에 맞게 끔 설계해주면 된다.
         */

        pi = new int[SIZE];

        // 0 번째에서 틀리면 갈 곳이 없기에 i == 1 부터 시작한다.
        for(int i = 1 , j = 0; i < SIZE; i++){
            while(j > 0 && arr2[i] != arr2[j]) j = pi[j - 1];
            if(arr2[i] == arr2[j]) pi[i] = ++j;
            // 접두사와 접미사가 동일한 길이를 pi[i] 에 입력해주어서 , 나중에 i + 1 에서 틀렸을 경우 i 까지는 맞은 것이니 돌아갈 수 있는 위치를 제공한다.
        }
    }
    public static void kmp(){
        /*
        끝까지 탐색하는 과정 중에 찾으면 ans 를 바꿔주고 아니면 그냥 진행
        arr1 과 arr2 를 비교
         */

        for(int i = 0 , j = 0; i < SIZE * 2; i++){
            while(j > 0 && arr1[i] != arr2[j]) j = pi[j - 1];
            if(arr1[i] == arr2[j]){
                // 두개가 같은 경우
                if(j == SIZE - 1){ // 현재 arr1[i] == arr2[j] 가 같은데 j 가 끝 문자이다. 그러면 성공이다.
                    ans = "possible";
                    return;
                }
                else{ // 끝 문자가 아니면 그냥 다음 문자를 탐색할 수 있도록 j++ 을 해준다.
                    j++;
                }
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());
        arr1 = new int[SIZE * 2];
        arr2 = new int[SIZE];

        for(int i = 0; i < 2; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < N; j++){
                int num = Integer.parseInt(st.nextToken());
                if(i == 0) {
                    arr1[num] = 1;
                    arr1[num + SIZE] = 1;
                }
                else arr2[num] = 1;
            }
        }

        getPi();
        kmp();

        System.out.println(ans);
    }
}