import java.util.*;
import java.io.*;
/*
두가지의 케이스가 존재한다.
케이스 1 : 단어의 사이사이에 지정한 문자가 들어오는 경우
케이스 2 : 단어의 앞, 뒤에 지정한 문자가 들어오는 경우

그리고, 한 단어에 같은 규칙이 두 번 적용될 수 없다.
하지만, 한 단어에 두 규칙이 다 적용될 수는 있다.

그리고 같은 기호는 사용될 수 없다.
구분자로 사용된 소문자 혹은 기호는 다시 사용될 수가 없다.

일단, 무조건 본문은 대문자라고 가정하자. 그렇지 않으면 못 풀 것 같음
일단, 이 알파벳이 이미 쓰였는지 확인하기 위해서, ascii 코드로 관리해야 할 것 같다.
혹은 HashMap 으로 관리하면 될 듯 하다.
하지만, 그냥 126 까지의 인덱스를 가진 배열을 선언해서 visited 처리 하는 것이 훨 나을 듯하다.

그러면 같은 구분자가 들어오는 것은 막을 수 있다.
그리고서, 이제 처리 해야 할 부분은 규칙 1 인지, 규칙 2인지 확인을 하는 것이다.

일단, 무조건 적으로 단어들을 구분하여서, List 에다가 순서대로 넣고서,
마지막에 answer 에 순서대로 채워넣으면 될 것 같다.

그럼 이제 특수 기호가 즉, 대문자가 아닌 것이 연속으로 2개가 왔을 때의 상황들을 정리해보자.
일단 확실하게 aHbEbLbLbOa cWdOdRdLdDc 이 예시가 있음 ac 이런식으로 바로 오지만
invalid 하지 않다.

그러면 어떻게 해야할까?
일단, 처음에 a 로 진입할 때, 규칙 1 이 적용될 수 있다. (단어의 첫 시작이 소문자이니까)
그 다음에, 여기서 또 소문자를 만나게 되면? 그때는 끝이다. 이거는 확실히고
여기에서, 대문자를 만나고, 또 소문자를 만난다? 그러면 두가지의 양상으로 나뉜다.
같은 문자라면? 단어의 끝이고,
다른 문자라면? 다른 규칙의 적용을 의심해볼 수 있다.
그래서 또 second 규칙이 적용되었다고 넣고 그 다음 문자를 탐색한다.
계속 그렇게 하다가, 다시 규칙 1 과 동일한 문자를 만나면? 그러면 현재까지 저장했던 대문자를 저장하면 된다.

그 다음에, 단어를 끝냈다고 하고, 다시 시작하면 된다.
이거는 어렵지 않다.

근데, 이제 구분자로 싸여있지 않은 단어가 문제이다.
SpIpGpOpNpGJqOqA 이 문자 같은 경우는 규칙 2로 시작한다.

일단, 단어의 첫 시작이 대문자이다. 그러면 second 규칙 적용이다.
second 규칙이 적용되었으니까, 대문자를 두번 만나게 되었을 때, 그때 단어의 끝이라고 볼 수 있다.

그리고, 다시 단어의 시작이고 대문자 만나고, 소문자 만나니까 쭉쭉 읽어가면 된다.
근데, 이제 q O q 여기서 이것을 규칙1을 우선으로 본다면?
J O A 가 된다.
각각의 단어들로 이러한 부분들을 어떻게 처리할 수 있을까?

AxAxAxAoBoBoB 이 테스트 케이스도 한번 봐보자.
규칙 2 적용이다.
AxAxAxA 여기까지는 문제가 없음 단어로 집어넣는다.
그 다음에 바로 소문자 만나니까 규칙 1 적용이다.
그 B 쭉쭉 읽는데, 규칙 2도 만났다.
그럼 규칙 1과 규칙 2가 만났는데, 둘이 같은 문자이다. 그러면 또 틀렸다고 볼 수 있다.

이런식으로, 굳이 모든 경우를 고려하지 않고 할 수 있을 것 같은데..

사실 그리고 규칙 1 = 사이사이
규칙 2 = 앞 뒤 이거임, 설계에서는 다르게 말함

결론
걍 해답 봄.. 개 노답 문제였음 이게 왜 레벨 3이지

-- 결론
굉장히 좆같은 문제였음...
일단 규칙 1, 규칙 2에 대한 예외 처리들을 모두 해줘야 하는 문제였음

일단, 첫번째로 소문자들을 다 읽어온다.
그 다음에, 소문자들을 읽어올 때, 같은 소문자들끼리 묶을 수 있도록 List로 관리하게 되는데,
어떤 알파벳이 먼저 들어왔냐에 대한 정보를 알아야 하기 때문에, 순서를 유지 못하는 HashMap 을 쓰는 것이 아닌
LinkedHashMap을 사용해서 순서를 유지할 수 있도록 함

그래서, 순서대로 리스트들을 만들고,
lastStartWord, lastEndWord 를 계속 기억하는 것이다.

즉, 먼저 나오는 알파벳들로 단어를 구분하는데
예를 들어서, 규칙 1, 규칙 2가 섞여있다라고 가정하면, 규칙 2 안에 규칙 1이 들어갈 수 있다.
그래서, 그것도 고려해야 한다.

일단, 그래서 해당 소문자가 1개 혹은 3개 이상인 경우는 rule 1 인 경우인데, 이 경우 문자가 2칸씩 떨어져서 있지 않으면 invalid 하다.
그리고, count == 2 인 경우가 있는데, 이때 당연하게 rule 1 일 수도 있다. 그래서, distance == 2 이고, 이전에 탐색한 단어 사이에 있으면, rule 1 이라고 하였음
그리고 그게 아니라 distance >= 2 이면, rule 2 만일 distance == 1 인 경우는 invalid 한 경우로 두었음

그리고 rule 이 정해진 다음에, 이제 make word 를 할 수 있어야 하기 때문에 rule = 1 인 경우는 startWord = startChar - 1, endWord = endChar + 1 을 해주었음
그 다음에, 만일 이전 단어에 rule 1 이 포함되어 있는 경우에는, 당연하게 continue 되어야 한다, 왜냐하면 단어가 또 포함되기 떄문이다 이 예외 처리를 하지 않으면
그래서, lastStartChar < startChar && endChar < lastEndChar 를 해주고, 그 안에서 Math.abs(lastStartChar - startChar) == 2 && Math.abs(lastEndChar - endChar) == 2 를 해주면 된다.
즉, 2자리 차이나면 continue 이고, 만약에 그렇지 않으면 invalid 한 값이다.

그 다음에 이렇게 다 처리를 하고 나면, 이제 단어를 포함시킬 차례인데
만약 lastEndWord >= startWord 이전 단어에 현재 단어가 하나라도 포함되어 있으면?
return invalid 를 해준다.

그 다음에, 소문자를 만나기 전에 진행 된 애들도 있을 것이기 때문에
if (stringIdx < startWord) 를 해준 다음에 answer.append(make(sentence, stringIdx, startWord - 1) 를 해준다
그 다음에 startWord ~ endWord 까지 단어를 넣어주고
그리고 lastWord, lastChar 를 갱신해준 뒤에 stringIdx = endWord + 1; 즉, stringIdx 를 마지막 문자 뒤로 겹치지 않게 설정해준다.

그 다음에 catch 문에 걸린 것들은 다 예외 사항이므로, invalid 한 값들이고, 이 조건문들을 빠져나왔으면 정상적으로 답을 다 구한 것이니
return answer.toString.trim() 을 해줌으로서 마지막 문자를 제외하고 내보낸다.

-- 해맸던 점
endWord = endChar + 1; 을 해야 하는데, endChar - 1 을 했었음
그리고 size - 1 했었어야 했는데, 그냥 size 해서 index 에러 나서 invalid 자꾸 났었음
*/
class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(solution(br.readLine()));
    }
    public static String solution(String sentence) {
        StringBuilder answer = new StringBuilder();
        LinkedHashMap<Character, ArrayList<Integer>> lowCharacter = new LinkedHashMap<>();
        String invalid = "invalid"; // 에러 나면 중간에 던져버리려고

        try { // 그냥 중간에 에러 나면 invalid 로 던져버림
            int size = sentence.length();

            for (int i = 0; i < sentence.length(); i++) { // 소문자들 수집
                char character = sentence.charAt(i);

                if (Character.isLowerCase(character)) {
                    if (!lowCharacter.containsKey(character)) {
                        lowCharacter.put(character, new ArrayList<Integer>());
                    }

                    lowCharacter.get(character).add(i); // 소문자의 위치들을 저장
                }
            }

            int stringIdx = 0;
            int startChar;
            int endChar;
            int lastStartChar = -1;
            int lastEndChar = -1;
            int startWord = 0;
            int endWord = 0;
            int lastStartWord = -1;
            int lastEndWord = -1;
            int count;
            int distance;
            int rule = 0;

            ArrayList<Integer> temp;
            for (Character c : lowCharacter.keySet()) {
                temp = lowCharacter.get(c);
                count = temp.size();
                startChar = temp.get(0); // 시작 위치
                endChar = temp.get(count - 1); // 끝 위치

                // 이제 count == 1 || count >= 3 (규칙 1) 과, count == 2 (규칙 2) 를 구분해서 처리
                if (count == 1 || count >= 3) {
                    // 규칙 1인 경우에는, 무조건 distance 가 2 여야함
                    for (int i = 1; i < count; i++) { // count == 1 은 그냥 넘어감
                        if (temp.get(i) - temp.get(i - 1) != 2) {
                            return invalid; // 규칙 1인 경우에는 무조건 적으로, 2가 차이나야함
                        }
                    }
                    rule = 1; // 확실한 것은 이러면 rule 1임, 문자가 하나이니까
                } else if (count == 2) {
                    // 문자가 두개여도, rule 1 일 수도 rule 2 일 수도 있음
                    distance = endChar - startChar;

                    // 근데, rule 1 이 되려면, 무조건적으로 이전 단어의 사이에 있어야 함
                    if (distance == 2 &&
                            ((lastStartChar < startChar) && (endChar < lastEndChar))) {
                        rule = 1; // 이렇게, 이전 단어 즉, lastStartChar < x < lastEndChar 라면 rule 1으로 칠 수 있음
                    } else if (distance >= 2) {
                        rule = 2;
                    } else { // distance 가 1 차이라는 것은 연속되었다라는 것임, 그렇기 때문에, invalid 함
                        return invalid;
                    }
                }

                // 각 적용된 규칙을 찾았음, 이제 이 룰에 대해서 처리를 진행해주면 됨
                if (rule == 1) {
                    // rule 1 은 일단, startWordChar, startEndChar 를, 앞 뒤로 해줌
                    startWord = startChar - 1;
                    endWord = endChar + 1;

                    // 그리고, 이게 만약에 사이에 있던 단어이면, 다시 포함 시키면 안되니까, continue 해줌
                    if ((lastStartWord < startWord) && (endWord < lastEndWord)) {
                        // 규칙 2이면 continue, 규칙 1이면 invalid 임
                        if ((startChar - lastStartChar == 2)
                                && (lastEndChar - endChar == 2)) {
                            continue;
                        } else {
                            return invalid;
                        }
                    }
                } else if (rule == 2) {
                    startWord = startChar;
                    endWord = endChar;

                    // rule 2 는 어떠한 거에도 싸여있으면 안됨, rule 2 는 바깥에 나와있어야 함
                    if ((lastStartWord < startWord) && (endWord < lastEndWord)) {
                        return invalid;
                    }
                }

                if (lastEndWord >= startWord) { // 이전 단어에 포함되어 있으면 안된다.
                    return invalid;
                }

                if (stringIdx < startWord) {
                    answer.append(make(sentence, stringIdx, startWord - 1));
                    answer.append(" ");
                }

                answer.append(make(sentence, startWord, endWord));
                answer.append(" ");
                lastStartWord = startWord;
                lastEndWord = endWord;
                lastStartChar = startChar;
                lastEndChar = endChar;
                stringIdx = endWord + 1;
            }
            if (stringIdx < size) {
                answer.append(make(sentence, stringIdx, size - 1));
            }
        }  catch (Exception e) {
            return invalid;
        }

        return answer.toString().trim(); // 마지막 공백문자 제거
    }

    public static String make(String sentence, int start, int end) {
        String word = sentence.substring(start, end+1);
        return word.replaceAll("[a-z]","");
    }
}