import java.util.*;
import java.io.*;

// 1339 : 단어 수학

/*
-- 전제 조건
단어들은 알파벳으로 이루어져 있고,
각 알파벳 대문자를 0부터 9까지의 숫자 중 하나로 바꿔서 N개의 수를 합하는 문제이다.

같은 알파벳들은 같은 숫자로 변겨오디어야 하고 , 두 개 이상의 알파벳이 같은 숫자로 변환되어서는 안된다.

N개의 단어들이 주어졌을 때, 그 수의 합을 최대로 만드는 프로그램을 작성하시오.
-- 틀 설계
일단 각 자리수가 높은 것들을 높은 숫자를 선택해야 한다.
8자리가 최대이다.
int 의 최대 범위는 21 억이다.

여기서 8자리는 얘는 가치가,
10000000 이다.

각각의 단어들을 받으면 각각의 가치를 보면 될 것 같은데
일단 , 자리수에 따라서 , 더해주는 것이 좋을 것 같다.

AB
BA

이것은 둘의 가치가 같다.

확실한 것은 위에서부터의 숫자부터 순서대로 사용하면 된다.

그래서 쨌든 가치를 구하면
10 + 1
10 + 1 이다.

좀더 직관적으로 볼 수 있는 것을 찾아보자

CAB
BCA

100 + 10 = 110
100 + 1 = 101
10 + 1 = 11 그래서
순서대로 c = 9 , b = 8 , a = 7 로 지정하면 된다.

그러면 978 + 897 이 된다.
그러면 1975 이다.

이런식으로 구하면 된다.
근데 이제 아마 10자리에서 문자가 발생하게 될 것이다.

예를 들어서 C가 자리 수가 가장 가치가 낮다고 해보자.
그러면 0이 들어가야 할텐데
0이 제일 앞에 있으면 숫자가 성립되지 않는다.

그러면 일단 자리수의 가치들을 계산해서 정렬을 진행하고
그 값들로 bfs를 돌리면 될 것 같다.

일단 자릿수의 가치에 대해서 어떻게 정의할 수 있을까?
hash 를 쓰는 방법이 있을 것 같다.

그러면 hash 를 썼을 때 정렬은 어떻게 해야할까?
일단 이렇게 한번 해보자.
자릿수가 어떻게 되냐에 따라서
그 숫자가 나올 때마다 , 처음 나오면 집어넣으면서 값을 초기화 한다.
그리고 이미 있다면 자릿수를 계산해서 , 집어넣는다.

배열에다가 객체를 담는다 , 그리고 정렬을 해준다.
그러고서 단어들을 하나하나씩 돌면서 , 계산을 해준다.

뭐야 그냥 맞았네 , bfs 쓰지도 않았는데..
역시나 그냥 각 가치를 저장해놓고서 , 순서대로 집어넣는 게 맞았음
그리고 끝이 0이여도 괜찮음 짜피 그 자리에는 누군가 0이 와야 하고,
거기 있어도 0이였다는 것은 다른 곳에 0이 들어가면 더 값들이 낮아지거나 , 혹은 다른데에 0이 들어가도 결과값이 똑같을 것이다.
 */
public class Main {
    public static int N , ans = 0;
    public static String[] wordList;
    public static Letter[] letterList;
    public static HashMap<Character , Integer> map = new HashMap<>();
    public static class Letter{
        char letter;
        int value;
        public Letter(char letter , int value){
            this.letter = letter;
            this.value = value;
        }
    }

    public static void bfs(){
        /*
        여기서는 이제 단어를 숫자들로 변환해서 계산을 진행해야함
         */
        int result = 0;

        for(int i = 0; i < N; i++){
            int sum = 0;
            int value = 1;
            for(int j = wordList[i].length() - 1; j != -1; j--){
                sum += map.get(wordList[i].charAt(j)) * value;
                value *= 10;
            }
            result += sum;
        }

        ans = Math.max(result , ans);
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(input.readLine());
        wordList = new String[N];

        for(int i = 0; i < N; i++){
            String word = input.readLine();
            int value = 1;
            wordList[i] = word;
            for(int j = word.length() - 1; j != -1; j--){
                char character = word.charAt(j);
                if(map.containsKey(character)){ // 이미 존재하면
                    map.put(character , map.get(character) + value);
                }else{ // 존재하지 않으면
                    map.put(character , value);
                }
                value *= 10;
            }
        }

        Iterator<Character> iterator = map.keySet().iterator();
        letterList = new Letter[map.size()];
        int index = 0;

        while(iterator.hasNext()){
            char key = iterator.next();
            letterList[index++] = new Letter(key , map.get(key));
        }

        Arrays.sort(letterList , (o1 , o2) -> Integer.compare(o1.value , o2.value));

        map = new HashMap<>();

        int value = 9;

        for(int i = letterList.length - 1; i != -1; i--){
            map.put(letterList[i].letter , value--);
        }

        bfs();

        System.out.println(ans);
    }
}