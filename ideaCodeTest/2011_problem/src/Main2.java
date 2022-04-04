import java.util.*;
import java.io.*;

// 2011 : 암호코드
/*
-- 전제 조건
상근이와 선영이는 대화를 듣는 것을 방지하기 위해서 대화를 서로 암호화 하는데 ,
알파벳을 숫자화 하는 것이다.
그래서 사용할 수 있는 숫자는 각각 1 ~ 26 까지의 숫자이다.

이렇게 했을 때 , 어떠한 숫자로 암호가 주어졌을 때 , 그 암호의 해석이 몇가지가 나올 수 있는지 구하는 프로그램 작성
5000자리 이하의 암호가 주어진다.
-- 틀 설계
String 으로 그냥 쫘르륵 주어지고
암호의 개수를 구하면 될 것 같다. (답을 100만으로 나누어야 한다.)

일단 그냥 1학년 문제와 너무 비슷한 다이나믹 프로그래밍 문제이고
그런식으로 끝까지 암호를 작성하였을 때 , 이제 가짓수를 돌아오는 Top - down 방식으로 하면 될 것 같다.

그러기 위해서는 중복된 것들을 방지하기 위한 dp 배열을 만들어야 하는데,
어떻게 만들어야 할까..

일단 이전에 생각했던 것은 현재 index , 및 현재까지 글자 수 였는데
이 경우 , 현재까지 글자 수가 같더라도 , 각자 다른 해석이 가능하다.

몇 번째에서 , 2개로 읽고 , 몇 번째에서 1개로 읽었는지에 대한 정보가 , 확실하게는 아니더라도 간접적으로라도 필요할 듯 싶다.

아니면 방문처리를 해쉬로 하는 것은 어떨까?
해쉬로 알파벳들은 순서대로 저장한다음에
index + String 으로 해서

값을 저장하고 찾는 것임

일단 ascii 코드 1 ~ 26을 64를 더해야 한다.

결국 해쉬의 한계로 메모리 초과가 났음

생각해보니까 , 지금까지 알파벳 한게 무슨 상관이야
지금 현재 index 만이 중요한 거지
바보같이 생각했었다.

쓸데 없이 , 이전까지 정해온 코드들을 신경쓰느라 , 메모리 초과와 , 시간초과를 경험했다.
앞으로는 조금 더 생각을 많이 했으면 좋겠고

조금은 해맸던 점이 MOD 연산을 안하고 반환한 것이였음

그리고 이전 정한 알파벳들은 현재 부터의 알파벳을 정할 수 있는 가짓수에 전혀 영향을 끼치지 않는데 , 그 점 조차도 간과하고 있었음
 */
public class Main2 {
    public static final int MOD = 1000000;
    public static int[] dp;
    public static int[] problem;

    public static int dfs(int index){
        /*
        현재 index 와 , now == 지금까지 만든 알파벳을 해서
        index + now 를 해서 Hash 있으면 저장하고
        없으면 만들어서 더한다

        생각해보니까 , 지금 현재 여기서부터 몇개의 암호를 만들 수 있냐가 중요한 것이지
        그 전에 어떠한 알파벳을 선택했냐는 전혀 중요하지 않았음

        당연하게도 , 그러니까 현재 dp[index] 에다가 이후의 결과를 넣어서 더하기만 하면됨
         */

        if(index == problem.length) {
            return 1;
        }

        // 없으니까 0으로 만들어주고
        if(dp[index] != 0) return dp[index];

        if(problem[index] == 0) return 0;

        dp[index] += dfs(index + 1);
        if(index != problem.length - 1 && Integer.parseInt(problem[index] + "" + problem[index + 1]) <= 26) dp[index] += dfs(index + 2);

        return dp[index] % MOD;
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        String string = input.readLine();
        problem = new int[string.length()];
        dp = new int[string.length()];

        for(int i = 0; i < string.length(); i++){
            problem[i] = string.charAt(i) - '0';
        }

        System.out.println(dfs(0));
    }
}