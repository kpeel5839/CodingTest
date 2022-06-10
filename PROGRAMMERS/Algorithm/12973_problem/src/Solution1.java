import java.util.*;
import java.io.*;

class Solution1 {
    // 너무 단순하게 생각헀나보다, 효율성에서 떨어짐
    // 조금 더 생각을 해야할 것 같음
    public int solution(String s) {
        int answer = 0;
        StringBuilder sb = new StringBuilder(s);
        // 그냥 엄청 단순한 문제인듯 StringBuilder 를 가지고 연산을 진행할 것임
        // 그냥 겹치면, 삭제 해준다 StringBuilder 의 Delete 메소드를 이용할 것이며, 범위는 substring 과 동일하게 동작한다.

        while (true) {
            if (sb.length() == 0) { // 모두 삭제했으면
                answer = 1;
                break;
            }

            boolean delete = false;
            char prev = sb.charAt(0); // 0번째 문자를 빼옴

            for (int i = 1; i < sb.length(); i++) {
                char now = sb.charAt(i);

                if (prev == now) { // 같으면
                    sb.delete(i - 1, i + 1); // 해당하는 범위를 삭제
                    delete = true;
                    break;
                } else {
                    prev = now; // prev 에다가 now 를 넣어준다.
                }
            }

            if (!delete) { // delete 하지 못한 경우는 바로 끝냄
                answer = 0;
                break;
            }
        }

        return answer;
    }
}