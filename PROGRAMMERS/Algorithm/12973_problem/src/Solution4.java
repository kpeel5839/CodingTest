import java.util.*;
import java.io.*;

class Solution {
    // 하도 생각안나서 답 봤는데 와... 생각보다 너무 단순했음
    // stack 이 확실히 생각이 안나면 어려운 것 같다, 애초에 stack 문제인지 생각도 못했음
    public int solution(String s) {
        int answer = 0;

        Stack<Character> stack = new Stack<>(); // 답을 봤는데 stack 으로 푸는 문제였네? 나는 엄청 이상하게 하고 있었음

        for (char c : s.toCharArray()) { // 일단 넣고, stack.peek 이 현재랑 같으면 넣지 않고 빼고, 그렇지 않으면 그냥 넣는다.
            if (!stack.isEmpty() && stack.peek() == c) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        if (stack.isEmpty()) {
            answer = 1;
        } else {
            answer = 0;
        }

        return answer;
    }
}