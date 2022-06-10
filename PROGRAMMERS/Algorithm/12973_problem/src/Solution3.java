import java.util.*;
import java.io.*;

class Solution3 {
    // List 로 바꿔봤는데, StringBuilder 가 느린가 해서, 그것도 아니였음
    public int solution(String s) {
        int answer = 0;
        List<Character> sb = new ArrayList<>();
        // 생각을 다시 해보니까, 굳이 그렇게 할 필요가없다.
        // 연속된 수를 짝지어 제거하는 것이기 때문에, 바로 이전으로 돌아가기만 하면 된다.
        // 주의해야 할 점은 단 두가지이다. 맨 마지막 - 1 에서 맞는 경우와
        // 첫번째에서 맞는 경우
        // 그 외의 경우에는 문자열을 삭제한 뒤에 바로 이전 인덱스로 돌아가면 된다.
        // 왜 시간 엄청 획기적으로 줄였는데, 이전에 1500ms 걸리던 것도 엄청 줄였는데 왜 시간초과일까 도대체 어떻게 해야하는 것일까 그러면
        int index = 0;
        for (int i = 0; i < s.length(); i++) {
            sb.add(s.charAt(i));
        }

        while (true) {
            if (index == sb.size() - 1) { // 현재 pointer 가 sb.length - 1 을 가르키고 있다면, 즉 가장 마지막이라면
                break;
            }

            if (sb.size() == 0) { // 아얘 끝난 경우도 레츠 고도리맨
                break;
            }

            char a = sb.get(index);
            char b = sb.get(index + 1);

            if (a == b) { // 두개가 일치한 경우
                for (int i = 0; i < 2; i++) {
                    sb.remove(index);
                }

                if (index != 0) { // index == 0 인 경우에 맞아버리면 그냥 넘어가야함
                    index--; // 이전 인덱스로 돌아간다, 왜냐하면 현재 위치는 삭제했기 때문에, 하지만 0이라면 뒤로 가면 안됨
                }
            } else { // 일치하지 않은 경우
                index++;
            }
        }

        if (sb.size() == 0) { // 다 삭제한 경우
            answer = 1;
        } else { // 그렇지 않은 경우
            answer = 0;
        }

        return answer;
    }
}
