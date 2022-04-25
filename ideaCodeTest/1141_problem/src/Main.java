import java.util.*;
import java.io.*;
import java.util.function.Function;

// 1141 : 접두사 x
/**
 * -- 전제 조건
 * 서로의 문자열의 접두사가 되지 않는 가장 큰 부분집합의 크기를 출력하시오.
 *
 * -- 틀 설계
 * 이건 그냥 빠르게 풀려고 답을 봤는데, 생각보다 어려운 문제였음
 * 일단, 정렬이라는 키워드를 보고, 왜 정렬을 하지 하면서 도대체 이해가 가지 않았음.
 * 처음에 딱 보자말자, 길이별로 정렬하면 괜찮겠다. 왜냐하면, 접두사로 가능한지 안한지를 봐야 하니까,
 * 비교하려는 문자열보다 본인의 길이가 더 크다면? 절대로, 그 문자의 접두사는 본인이 될 수가 없으니까,
 *
 * 근데, 이거를 했었어야 했는데, 스트림도 배우고, 람다식도 배워놓고서, 길이로 정렬할 생각을 하지를 못했음
 * 그래서, 길이로 내림차순으로 정렬을 진행하였음
 * 그 이유는, 내림차순으로 정렬하게 되면, 일단 첫번째, 단어는 무조건 적으로 Set 에 포함 시킬 수 있다라는 것을 알 수가 있음
 * 왜냐하면, 첫번째 글자는 가장 길이가 긴 문자열이고, 혹은 제일 길지 않고 본인과 같은 길이의 문자열이 존재한다고 하더라도, 이미 본인이 포함이 되었다면?
 * 그러면, 그 문자들은 포함이 될 수가 없으니까 말이다.
 *
 * 그리고, 무조건 많은 숫자의 부분집합을 찾아내기 위해서는, 일단 가장 긴 길이의 문자열을 먼저 넣고 보는 것은 어찌보면 당연한 생각이다.
 * 예를 들어서, abcd, a, a, a, a 이렇게 있다고 하더라도
 * 어짜피, abcd 는 포함되는 것이 무조건 적으로 이득이다, 그리고 abcd, abc, ab, a 이런식으로 되어있다고 하더라도, 첫번쨰 글자가 포함도리 수도 있겠지만
 * 조금 더 유하게 생각하기 위해서는 가장 긴 길이의 문자열을 먼저 넣는 것이 이득이다.
 *
 * 그리고, 무엇보다, 비교에 능하다 Set 에다가 넣어놓고서, 이제 다음 String 볼 때마다, 길이 조건을 딱히 명시하지 않아도, 이미 set에 들어있는애는 나보다 길이가 길거나 같으니까
 * 길이를 비교하지 않고, 비교가 가능하다, 그래서, String.indexOf method 를 이용하여서, 접두사로 포함이 되는지에 대해서, 판단을 진행하였음
 *
 * 그래서 결국, 이런식으로 진행하였고, 단 저렇게 두가지의 조건만으로 문제를 해결할 수 있었다.
 * 그리고 Set을 쓸 필요가 없어보이는데, 왜 Set 을 쓰나해서, List를 사용해보았는데, 역시나 잘 된다.
 *
 * 첫번째, 문자열 길이를 기준으로 내림차순 정렬
 * 두번째, 이미 들어간 문자열과 현재 들어가야 할 문자열을 비교하여서, 이미 존재하는 문자열과 접두사가 존재하는지, 본다.
 *
 * 결론적으로, 약간 그리디한 해결방법이였다고 볼 수 있다.
 * 길이가 긴 것들로 부터 우선적으로 넣은 다음에, 그 다음에 들어오는 것들이 들어올 수 있냐 아니냐를 판단하여서, 최대 부분집합의 개수를 가려내는 것이다.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        int N = fun.apply(br.readLine());
        String[] arr = new String[N];

        for (int i = 0; i < N; i++) {
            arr[i] = br.readLine();
        }

        Arrays.sort(arr , (o1, o2) -> o2.length() - o1.length()); // 문자열 길이를 이용한 정렬
        List <String> list = new ArrayList<>();

        for (String string1 : arr) {
            if (list.size() == 0) {
                list.add(string1);
                continue;
            }

            boolean able = true;

            for (String string2 : list) {
                if (string2.indexOf(string1) == 0) { // string2의 접두사로 string1 이 들어가지 않으면
                    able = false;
                    break;
                }
            }

            if (able) {
                list.add(string1);
            }
        }

        System.out.println(list.size());
    }
}
