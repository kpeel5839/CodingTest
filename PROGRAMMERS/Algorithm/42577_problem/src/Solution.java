import java.util.*;
import java.io.*;

/*
길이 별로 정렬하게 되면, 무조건 적으로 앞의 문자열은 뒤의 문자열의 접두사가 도리 수 있다.
근데 여기서의 문제점은 앞의 문자열이 바로 뒤의 문자열에만 접두사가 될 수 있는 것이 아닌
더 뒤에 있는 애들의 접두사가 될 수 있다.

일단 개 바보 같이 n ^ 2 로도 가능하다.
근데, 그렇게하면 절대 통과 못함

다른 효과적인 방법을 통해서 비교해야 한다.

오마이갓 해시라는 것에서 힌트를 얻어버렸음
괜히 봤다.
근데 생각못했을 것 같음, 해쉬라는 것은

일단 최소 길이의 문자열을 찾는다
그 다음에, 모든 문자들을 hash 에다가 길이별로 등록을 한다.
그냥 subString 으로 계속 등로갷도 될 것 같고
뭐 String.charAt 로 등록해도 될 것 같고

효율 중시하지 않는다라고 하면 그냥 한글자 한글자 추가하면서 진행하고
그 다음에 그냥 통쨰로 넣으면서 있는지 없는지 확인하는 것이다.
*/
class Solution {
    public boolean solution(String[] phone_book) {
        boolean answer = true;
        HashSet<String> hashSet = new HashSet<>();

        for (int i = 0; i < phone_book.length; i++) {
            String string = phone_book[i];
            String add = "";
            for (int j = 0; j < (string.length() - 1); j++) { // 본인이 본인을 완벽히 저장해버린다면? 같은 번호는 오지 않는 다고 했으니까, 본인이 본인을 찾을 수밖에 없음, 그래서 무조건 답이 false 가 됨, 그래서 본인 보다 한글자 적은 것 까지만 진행
                add += string.charAt(j);
                hashSet.add(add);
            }
        }

        for (int i = 0; i < phone_book.length; i++) {
            if (hashSet.contains(phone_book[i])) {
                answer = false;
                break;
            }
        }

        return answer;
    }
}