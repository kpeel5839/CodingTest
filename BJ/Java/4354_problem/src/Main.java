import java.util.*;
import java.io.*;

// 4354 : 문자열 제곱

/*
-- 전제 조건
여기서 말하는 곱은 문자열의 더하기를 의미한다.
그래서 , 어떠한 문자열에 제곱을 의미하는 것은 그냥 그 문자열을 여러번 더하는 것이다.
즉 , (ab)^3 이런 경우는 실질적으로는 (ab * ab) * ab 이런식으로 가면 abab * ab = aabaaabaabbbabbb 뭐 이런식으로 표현할 수도 있지 않을까
근데 , 역시나 어색한 표현 방식이다 , 그리고 제곱을 저런식으로 표현하게 되면 , 문제가 너무 미궁으로 빠질 것 같다.
그래서 (ab)^3 은 우리가 아는 for(int i = 0; i < 3; i++) string.concat(string , ab) 이런 식의 개념이라고 보면 된다.
ab + ab + ab 이렇게 될 수 있는 것이다.

그래서 이 문제에서는 s가 주어지면 어떠한 문자열 a로 s를 만드는 최대의 제곱 즉 s = a(어떠한 문자열)^n 의 n을 구해야 하는 것이다.

테스트 케이스는 1 부터 100만 까지의 길이로 주어진다.
그리고 각각의 테스트 케이스는 s만 주어진다. (난 a도 주어지는 줄 알고 , 머리만 엄청 싸맸음)
-- 틀 설계
그냥 다 받고 . 으로 들어오면 끝내면 되고,
부분 문자열이 있다 , 근데 부분 문자열로만 , 이루어져 있다라고 하면,
len % (s.length() - pi[s.length() - 1]) == 0 이다.
예를 들어서
ababab 라고 하였을 때 , pi[s.length() - 1] == 4 이다.
그렇다는 것은 abab abab 가 접두사 접미사가 같다라는 것이다.
그렇다는 것은 len - pi[len - 1] == 2라는 이야기이다.
그러면 len / (len - pi[len - 1]) == 3 이라는 결과를 도출 할 수 있게 되고 (ab)^3 으로 해결 할 수 있다.
그러면 n 의 최대 크기는 3이다.

근데 만약에 이런 경우를 한번 생각해보자.
abkkab 이런 경우를 생각해보면
pi[len - 1] == 2 이다.
len - 2 = 4 이고 , 이걸로 나눠지지 않는다 len 은 그래서 len 으로 나눠지지 않는 경우는 부분 문자열 (a)^n 으로 이루어질 수 없고 , 무조건 (a)^1 로만 가능하다.
그래서 답은 이렇게 하면 된다.

어떻게 보면 이런 답이 나올 수밖에 없다.
예를 들어보자.
ababab 가 있다고 가정해보자.
그러면 일단 , 이 문자열이 , 똑같은 부분 문자열들이 반복되는 문자열이라고 가정해보자 , 즉 a ^ n 으로 표현할 수 있는 문자열이라고 가정해봊다.
그러면 무조건 적으로
len - pi[len - 1] 을 하게 되면 해당 문자열의 최소 부분 문자열이 나올 수밖에 없다.
ababab 가 있으면
같은 부분이
abab
  abab 이다.
그러면 여기서 볼 수 있듯이 , 일단 , 둘이가 같다 , 겹치는 부분은 무조건 적으로 같다.
그러면 그 겹치는 부분을 뺀 , 나머지 부분이 반복되는 것이다.
그럴려면 len - pi[len - 1] 해당 문자열에서는 4라는 값을 이용해야 한다.
ababab 가 있으면
....ab 이 부분이 , 반복된다라는 것을 알 수 있다.
ababab
abab
  abab 여기서 알 수 있듯이 겹친다면
무조건 적으로 당연하게 가운데의 것은 겹친다 , 그러면 당연히 뒤에 ab가 최소 부분 문자열인 것이다.

그래서 나의 가정은 그렇다 , 애초에 부분수열로 표현할 수 있는 문자열은 len - pi[len - 1] 을 하게 되면
최소 부분 문자열이 나온다. 그러면 그것을 len / (len - pi[len - 1]) 을 하면 당연하게도 a ^ n 의 n을 구할 수 있는 것이다.(n의 최대 값 , a 가 최소 부분 문자열이니까)

그래서 또 하나의 예를 보면
abcabc
abc
   abc
이러면 pi[len - 1] = 3 이다.
이것도 절반이다.  그러면 n = 2 이다.

이렇게 보면 알 수 있듯이 , 무조건 n이 2 이상이 되려면 커도 절반 이하여야 한다.
그렇기 떄문에 , 겹치는 부분이 절반 이하인 것들은 당연하게도 제곱으로 표현할 수 없다.
그렇게 때문에 무조건 n == 1 이라는 결론이 도출이 된다.

그래서 결론적으로
len % (len - pi[len - 1) != 0 은 , 겹치는 부분이 절반 이하이기에 , 제곱으로 표현 불가능 하고
그렇지 않으면 최소 부분 수열 * x == 문자열의 길이  , x == 문자열의 길이 / 최소 부분 수열 이 되게 된다.

그리고 마지막으로 겹치는 부분이 절반 이상이여도 , 최소 부분 수열의 길이로 본래 문자열의 길이를 만들 수 없다면 , 그것도 n == 1 이다.
그 경우 , 이전 경우 두가지를 다 도출이 가능한 방법이 len % (len - pi[len - 1]) != 0 이다.

이 문제의 가정은 하나로 설명할 수 있다.
접두사 , 접미사가 같은 부분을 겹치는 부분은 빼고 , 최소 부분 문자열을 만들고 , 그 문자열로만 해당 문자열 s를 구성할 수 있다면 , 그 n값을 구할 수 있는 것이다.
 */
public class Main {
    public static int[] pi;
    public static String s;

    public static void getPi(){

        /*
        0 은 그냥 무조건 1이고 그 다음부터 pi[i] 와 pi[j] 를 비교하기 시작한다.
        j 가 비교하는 문자열이고 , i 가 pi[i] 에 넣을 값이다.
         */

        pi = new int[s.length()];

        for(int i = 1 , j = 0; i < pi.length; i++){
            while(j > 0 && s.charAt(i) != s.charAt(j)) j = pi[j - 1];

            if(s.charAt(i) == s.charAt(j)){
                pi[i] = ++j;
                // 맞으면 , ++ 한 j 값을 넣음 왜냐하면 1개를 맞았다라는 것이고 , 곧 그 개수가 어디까지 맞았다는 것이므로 여기서 틀리면 그 위치로 돌아갈 수 있게 되는 것이다.
                // 지금까지 맞는 길이를 입력하면서 , 자연스럽게 여기까지 맞았을 때의 다음 탐색 위치를 나타내줌
                // 예를 들어서 aa... 가 있다라고 했을 때 , pi[1] == 1 이기 때문에
                // 2번째 문자열에서 틀리게 되면 aa까지는 맞았다라는 의미임으로 pi[1] 부터 탐색을 하면된다.
                /*
                aabc
                aa.. 틀렸다고 가정하자

                aabc
                 aa... 이렇게 가는 것이다.
                이런식으로 접두사 접미사의 개념으로 돌아갈 수 있는 위치를 지정할 수 있다.
                 */
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));

        while(true){
            s = input.readLine();

            if(s.equals(".")) break;

            getPi();

//            System.out.println(Arrays.toString(pi));

            int len = s.length();

            if(len % (len - pi[len - 1]) != 0){
                output.write(1 + "\n");
            }else{
                output.write(len / (len - pi[len - 1]) + "\n");
            }
        }

        output.flush();
        output.close();
    }
}