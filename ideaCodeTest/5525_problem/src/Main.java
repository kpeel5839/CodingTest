import java.util.*;
import java.io.*;

// 5525 : IOIOI
/*
-- 전체 설계
n + 1 개의 I 그리고 , n 개의 O 로 이루어진 것을
하나의 문장이 주어졌을 때 몇개의 개수가 있냐를 본다.
-- 틀 설계
일단 매칭할 문자열을 만들고 , n을 보고
그 다음에 13 - 2n + 1 을 해준다.
그 다음에 해당 숫자까지 반복문 돌리면서 equals 를 해주면서 , 맞으면 count ++ 를 해준다.

-- 해답
결국에 답을 봄 , 50점 짜리 답 이라서 , 생각보다 더 생각을 많이 해야하는 문제 였다.
일단 memo 로 현재 인덱스까지 , OI 가 얼마나 반복되었는지 셀 , arr 을 만들 것이다.
그 다음에 , char 배열은 어떠한 값들이 들어왔는지 저장할 , 변수이다.
그런 다음에 , memo 에 값들을 채워주면서 , memo[i + 1] >= n 보다 크면 ,
일단 OI 가 n 번 반복되었다는 얘기다 해당 자리는 , 그렇다는 것은
맨 앞에 I 만 오면 된다는 것이고 그럴려면 만일 n 이 1 이라고 할 때 , 2의 입장에서 , 가장 앞의 자리는
arr[0] 에 오게 된다. 그렇다는 것은 memo[i + 1 - 2 * n] 으로 정리할 수 있는 것이다.
그래서 memo[i + 1 - 2 * n] 이 I 라면 이것은 맞는 것이다 그 때 ans를 높여주면 된다.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        
        int n = Integer.parseInt(input.readLine());

        int m = Integer.parseInt(input.readLine());

        char[] character = input.readLine().toCharArray(); // 문제를 담아놓을 것

        int[] memo = new int[m];
        int ans = 0;

        for(int i = 1; i < m - 1; i++){ // 0 번째는 OI 반복되어봤자 , 짜피 못된다 , 그래서 1번부터 시작
            if(character[i] == 'O' && character[i + 1] == 'I'){
                memo[i + 1] = memo[i - 1] + 1;
            }

            if(memo[i + 1] >= n && character[i + 1 - 2 * n] == 'I'){
                ans++;
            }
        }

        System.out.println(ans);
    }
}