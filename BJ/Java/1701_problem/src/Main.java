import java.util.*;
import java.io.*;
import java.util.function.Function;

// 1701 : Cubeditor

/**
 * -- 전제조건
 * 문자열이 주어지면, 거기서 두번 이상 나오는 가장 긴 문자열을 찾으면 된다.
 * 그래서, 그것을 찾고 길이를 출력하시오.
 * -- 틀설계
 * 그냥, 문자열이 주어지면, 문자열 길이 -1 의 길이로 부터, 짜른다.
 * for 문으로, 그 다음에 getPi 함수를 만들어서, pi 를 만들어주고,
 * 그 다음에 kmp 알고리즘을 만들어서, 최종 해당 문자열이 몇번 반복되는지 반환한다.
 *
 * 5000 * 5000 혹은 그 이하인 연산수라서, 0.5초에 빠듯하게 들어올 수 있을 것 같긴하다.
 *
 * 역시나 안되서, 답안을 봤음
 * 근데, 너무 의외의 풀이 방법이였음
 *
 * 일단, 3번 반복되는 것 따위는 생각하지 않았음
 * 왜냐하면, 2번 반복되는 것이, 3번 반복되는 것보다 무조건 길이가 길기 때문임
 * 그래서, 어떠한 방법을 쓸 수 있냐면, getPi 과정에서, 그것을 할 수 있었음
 * getPi 과정 자체가, 그때 그때 문자열을 잘라서, 접두사와 접미사가 얼마나 동일하냐를 보는 것임
 * 즉, 동일한 문자열의 길이를 앞 뒤로 보는 것임
 *
 * 근데, 여기서 그러면 앞뒤만 보는 것이 아니냐 볼 수 있지만,
 * KMP 알고리즘 자체가, ABAB 이렇게 있다고 했을 때
 * ABAB
 * AB
 *   AB
 * 이런식으로 동일하다고 할 수도 있고, 겹쳐서도 동일하다고 볼 수 있기때문에, 그냥 pi를 구해주면서 max 를 구해주면 되고,
 * subString 은 그냥 0 부터 s.length 까지 문자열을 잘라가면서 하면 된다.
 * 이것도 되는 이유가, 일단 부분 문자열에 대해서는 당연히 0 부터 s.length - 1 에서 시작하는 애들까지 다 진행해주어야 한다.
 * 그리고, 어디까지 진행하는지? 그거에 대해서는 전혀 중요하지 않은 문제였음
 *
 * 왜냐하면, 짜피 getPi 에서 위에서도 말했듯, 그때그때 문자열 길이까지만 검사함
 * 예를 들어서
 * ABAB 를 또 예로 들면
 * ABAB
 * AB
 * ABA
 * ABAB 이런식으로 순서대로 비교하면서
 *
 * 처음에는 겹치는거 0
 * 그 다음 1
 * 그다음 2 이런식으로 접두사와 접미사의 문자열이 얼마나 겹치는지를 구할 수 있는 것임
 *
 * 그래서 사실상 시작 지점만 움직여주면?
 * 모든 subString 에 관해서 (내가 답안을 보기전에 했던 방법)
 * 가능한 것이다.
 * */
public class Main {
    public static int[] pi;
    public static String s; // 주어진 문자열
    public static String p; // 찾는 문자열

    public static void getPi() {
        pi = new int[p.length()]; // p.length 만큼의 크기의 배열을 선언

        // 이제 여기서부터, ABC 가 있다고 하면
        /**
         * ABC
         *  ABC
         * 이런식으로 비교해주면서, 맞으면, ++j 를 넣어주어야 함
         * 만약에 틀리면, 이전 문자열까지 맞은 것이니까, 이전 문자열 pi[j - 1] 로 돌아감
         * 그리고, 위의 저 가정은 다, i 길이만큼의 문자열만이 존재한다고 가정하고 진행하는 것임 (그때그때)
         */
        int j = 0;

        for (int i = 1; i < p.length(); i++) {
            while (j > 0 && p.charAt(i) != p.charAt(j)) { // j 가 0 보다 큰데, 두개의 문자열이 같지 않으면?
                j = pi[j - 1]; // 그러면 j - 1 까지는 맞았으니까, 그것을 이용해서 이동
            }

            // 여기서는 그냥 문자열이 맞으면, ++j 넣어주면 됨, 만일 접두사가 같은게 없다, 그러면 update 안해주면, 짜피 0임
            if (p.charAt(i) == p.charAt(j)) {
                pi[i] = ++j;
            }
        }
    }

    public static boolean kmp() {
        // s, p 를 비교해주면 된다.
        int j = 0;
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            while (j > 0 && s.charAt(i) != p.charAt(j)) {
                j = pi[j - 1]; // 돌아갈 수 있는 가장 효율적인 위치로 돌아감
            }
            if (s.charAt(i) == p.charAt(j)) {
                if (j == p.length() - 1) { // 즉, 마지막 문자열에서 맞았을 때
                    count++; // count ++ 해주고
                    j = pi[j]; // 마지막 문자열에서 맞았으니까, 이 위치에서 움직일 수 있는 가장 효율적인 위치로 움직인 뒤에, 다음 탐색을 시작함
                } else {
                    j++;
                }
            }
        }

        return count >= 2 ? true : false; // count 가 2 이상이면, true 아니면 false 를 반환
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int res = 0;
        s = br.readLine();

        for (int i = s.length() - 1; i != 0; i--) { // 문자열의 길이를 이런식으로 설정하고
            for (int j = 0; j + i <= s.length(); j++) {
                p = s.substring(j, j + i); // i, j 를 이용해서 문자열을 잘라낸다.
                getPi();
                if (kmp()) {
                    res = Math.max(res , p.length());
                }
            }
        }

        System.out.println(res);
    }
}
