import java.util.*;
import java.io.*;

// 1786 : 찾기
/*
-- 전체 설계
첫째 줄에 문자열 T가
둘째 줄에 문자열 P가 주어진다.

여기서 , 문자열이 일치하는 곳을 출력하고 ,
T 중간에 P가 몇개가 나타나는 지를 출력하라
-- 틀 설계
pi 가 kmp 알고리즘에서 가장 중요하다.
일단 ABABAB 로 pi 를 구하는 예를 들어보자.

일단 pi[0] 은 A일때
pi[1] AB
pi[2] ABA ...
pi[5] ABABAB 를 의미한다.

즉 pi[5] = 4 이것은 6번째 문자인 B에서 매칭이 실패했을 경우,
4번쨰 인덱스를 탐색하라는 것이다.

그래서 일단 A 는 접두사와 접미사가 존재 x

pi[0] = 0
pi[1] = 0
pi[2] = 1
pi[3] = 2
pi[4] = 3
pi[5] = 4
로 구해진다.

이것은 어떤식으로 구할 수 있는 것이냐면
이런식으로 구할 수 있다.

pi[0] = 0
  0 1 2 3 4 5
i A B A B A B
j   A B A B A B

이런식으로 되어 있는 것이다.
일단 , 한 글자일 때는 접두사와 접미사가 같을 수 없다 , 접미사가 정확히 어떠한 의미인지는 모르겠지만
확실한 건 여기서는 접두사와 접미사의 크기가 같을 떄의 최대 크기를 정하지만 그 최대크기가 n이 되어서는 안된다.
즉 ABABAB 는 접두사 접미사 모두 ABABAB 이지만 허용하지 않는다는 것이다.

그래서 이런식으로 구할 수 있는 것이다.

pi[1] = 0
  0 1 2 3 4 5
i A B A B A B
j   A B A B A B

1번째 인덱스가 같지 않다.
계속 지속적으로 0번째 인덱스를 탐색한다. (i) 는 순서대로 가면서 (j) 는 그대로

pi[2] = 1
  0 1 2 3 4 5
i A B A B A B
j     A B A B A B

이 경우는 드디어 맞는다 , 그렇다는 것은 ABA 일때 접두사와 접미사 즉 A가 겹친다는 의미가 된다.
그러니까 , i는 접미사 , j는 접두사를 의미한다고 생각하면 편하다.

pi[3] = 2
  0 1 2 3 4 5
i A B A B A B
j     A B A B A B

다음 단계를 진행해봤다. 3번째도 동일하다.
그럼 또 그대로 놥두고 진행한다.

pi[4] = 3
  0 1 2 3 4 5
i A B A B A B
j     A B A B A B

pi[5] = 4
  0 1 2 3 4 5
i A B A B A B
j     A B A B A B

쭉 진행해보면 4 , 5 번째도 다 동일하다.
그래서 ABABAB 의 앞의 ABAB 와 뒤의 ABAB 가 겹친다는 것을 알 수 있다.
즉 , 이렇게 맞지 않은 경우는 점프를 해주고, while(j > 0 && p[i] != p[j]) j = p[j - 1]
를 계속 진행해주면 , 접두사와 접미사를 계속 알맞게 찾을 수가 있다.

접두사와 접미사가 같은 경우의 최대 크기를 구해서 하는 이유는

즉 , 앞에서 시작했을 떄 , 뒤에서 시작했을 때의 어떠한 부분까지 똑같다라는 것이다.
즉 , 그 말은 ABABAC 가 되어서 여기서 C로 틀렸다.
그러면 여기서 앞의 ABAB 는 같다.
그리고 5번째 문자열 까지 오는데 당연히 p[0 .... 4] 까지는 동일했을 것이다.
그렇다는 것은 지금 5번째 문자열까지 왔을 때의 접두사 , 접미사가 동일한 최대크기를 이용해서 , 해당 p[최대크기] 를 탐색하면

앞의 동일하던 접미사를 제외하고 그 다음 번쨰 인덱스를 탐색하면 되는 것이다.
그렇기에 , 최대크기는 인덱스식으로 표현하는 것이 아닌 , 길이 형식으로 표현하는ㄱ ㅓㅅ이다.

이러한 점들을 이용해서 pi를 잘 만들고(전처리) 탐색을 진행하게 되면 금방 답을 찾아낼 수 있다.
 */
public class Main {
    public static String p , t;
    public static int[] pi;
    public static List<Integer> ansList = new ArrayList<>();
    public static void getPi(){
        /*
        pi 는 접두사와 접미사를 이용해서 해당 문자열에서 매칭이 실패했을 때 , 몇 번째 문자열부터 다시 탐색할 것인지
        그것을 정하는 것이다.

        그러면 일단 pi를 p 의 크기만큼을 선언한다.
        그리고 , 0으로는 채워져있으니까 넘어가고
        pi[1] 부터 채울 생각을 해야한다.
        j = 0; 으로 시작한다. (j) 는 p를 탐색할 위치

        그래서 같으면 j++ 를 해주고 그 값을 pi[i] 에다가 넣어준다.
        그 행위를 반복하면 된다.

        근데 만약에 같지 않은 경우
        그럴 때에는 또 접두사의 최대크기를 찾아야 한다.
        근데 당연하게도 이전에 했던 방법을 이용하면
        빠르게 탐색이 가능하다.

         */
        int j = 0;
        for(int i = 1; i < p.length(); i++){
            while(j > 0 && p.charAt(i) != p.charAt(j)) j = pi[j - 1]; // 지금 현재 j 번째 인덱스와 i 번째 인덱스가 맞지 않는 상황임
            // 그 의미는 즉슨 , j - 1 번째까지는 동일했다라는 의미가 된다.
            // 그렇다는 것은 두개의 접두사 , 접미사의 최대 크기가 결정되었다는 것이다.
            // 그렇다는 것은 그 최대크기만큼은 일치한다라는 의미가 된다.
            // 그렇다는 것은 접두사에서 최대 크기만큼 뒤로 간ㄷ , 즉 p[접두사 , 접미사가 동일한 최대크기] 가 다음 탐색 대상인 것이다.
            /*
            그림으로 표현하면 이렇다.
            ABABABC
            ABAB   접두사
              ABAB 접미사
            이 이렇게 최대 크기라고 할 수 있다.
            근데 만일 C에서 탐색이 실패했다.
            그렇다라는 것은 탐색을 실패한 이전 위치로 돌아가서 본다면
            ABAB 가 겹친다.
            즉 , ABAB = 4 의 길이만큼 다음 것을 탐색해보면 되는 것이다.
            즉 어떻게 되냐면

            ABABABD
            ABABABC
            이렇게 왔다. 근데 , C에서 틀렸네 그러면 이전 것을 본다.
            ABABAB 이 두개를 보면 겹치는 부분의 최대 크기는 ABAB 이다.
            그러면 p[4] 를 다음 탐색 위치로 보면 되는 것이다.
            그렇게 되면
            ABABABD
              ABABABC 또 틀리게 된다.
            그러면 또 점프를 해준다.
            ABAB 에서 겹치는 부분은 AB 이다. 그렇다는 것은 또 p[2] 를 탐색하면 된다.
            ABABABD
                ABABABC 또 틀린다.
            그러면 또 점프를 해준다.

            ABABABD
                  ABABABC
            이제는 탐색하는 위치가, 즉 j 가 0이 되었다. 틀리면 이제 i를 증가시키는 것이다.
            이런식으로 계속 이어가면서 탐색하게 되면 굉장히 빠르게 구할 수 있다.
             */
            if(p.charAt(i) == p.charAt(j)){
                pi[i] = ++j;
            }
        }
    }
    public static void kmp(){
        /*
        kmp 알고리즘은 일단 , pi 를 이용해서 구한다.
        pi 는 접두사와 , 접미사를 이용한 , 해당 문자에서 , 틀렸을 경우 또 어디로 가야하는 지 , 즉
        문자열을 틀렸을 경우 다시 반복하는 것이 아닌 , 기억이 가능하게 끔 해주는 배열이다.

        그래서 이 점을 이용해서 , jump 를 적절하게 하면서 , 문자열을 탐색해주면 되게 쉽게 할 수 있다.
        즉 pi를 구하는 것 , 그게 관건이ㅏㄷ.
         */
        getPi();
        int j = 0;
        for(int i = 0; i < t.length(); i++){
            while(j > 0 && t.charAt(i) != p.charAt(j)) j = pi[j - 1];
            if(t.charAt(i) == p.charAt(j)){
                if(j == p.length() - 1){ // 이거 또 맞으면 이번에는 추가임
                    ansList.add(i - j + 1); // 원하는 출력값은 인덱스가 아니기에 + 1 해준다.
                    j = pi[j];
                }
                else{
                    j++;
                }
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        t = input.readLine();
        p = input.readLine();

        pi = new int[p.length()];

        kmp();

        output.write(ansList.size() + "\n");
        for(Integer number : ansList){
            output.write(number + " ");
        }

        output.flush();
        output.close();
    }
}