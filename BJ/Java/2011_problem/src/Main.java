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
 */
public class Main {
    public static final int MOD = 1000000;
    public static HashMap<String , Integer> dp = new HashMap<>();
    public static int[] problem;

    public static int dfs(int index ,String now){
        /*
        현재 index 와 , now == 지금까지 만든 알파벳을 해서
        index + now 를 해서 Hash 있으면 저장하고
        없으면 만들어서 더한다
         */

        if(index == problem.length) {
//            System.out.println(now);
            return 1;
        }

        String nowValue = index + now;
        if(dp.containsKey(nowValue)) return dp.get(nowValue);

        // 없으니까 0으로 만들어주고
        if(!dp.containsKey(nowValue)) dp.put(nowValue , 0);

        if(problem[index] != 0) dp.put(nowValue , dp.get(nowValue) + dfs(index + 1 , now + (char)(problem[index] + 64)));
        if(index != problem.length - 1 && problem[index] != 0 && Integer.parseInt(problem[index] + "" + problem[index + 1]) <= 26)
            dp.put(nowValue , dp.get(nowValue) + dfs(index + 2 , now + (char)(Integer.parseInt(problem[index] + "" + problem[index + 1]) + 64)));

        return dp.get(nowValue) % MOD;
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        String string = input.readLine();
        problem = new int[string.length()];

        for(int i = 0; i < string.length(); i++){
            problem[i] = string.charAt(i) - '0';
        }

        System.out.println(dfs(0 , ""));
    }
}
