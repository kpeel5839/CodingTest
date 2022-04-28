import java.util.*;
import java.io.*;

class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String s = br.readLine();
        System.out.println(Integer.parseInt(s));
        System.out.println(myAtoi(s));
    }
    public static int myAtoi(String s) {
        // 범위 안에 있으면, 그대로 두고,
        // 아니면, 넘어갔다고 처리를 하고, 그 범위에 남아있도록 해야함,
        // 즉 최대값에 남겨둔다.
        // 그리고 문자열을 만나면, 그만 읽고, 앞자리에 0을 만나게 되면 숫자로 처리하지 않는다.
        
        /*
        그리고 문자열을 만나게 되면, 즉시 읽기를 멈춰야 하는 것 같다.
        그렇기 떄문에, 문자열을 만나면 읽기를 멈춰야 한다.
        
        그래서 서순을 어떻게 해야할까?
        
        일단, 첫째, 공백은 무시한다, 공백을 만나게 되면 넘어가면 된다.(즉 무시한다)
        둘째, 부호를 만나는지 안만나는지 확인을 한다.
        셋째, 제일 처음 Stack 에 처음 담기는 애가, 0이라면? 계속 넘거야 한다.
        넷째, 그리고서 아닌 애들은, 계속 담는다.
        
        계속해서, 공백은 만나면 무시해야하고, 문자열을 만나게 되면 바로 제거해야 끝내야 한다.
        
        참고로 숫자 ascii code 48 ~ 57 번이다.
        그래서 이러한 예외 사항들을 넘어가서, 결국 Stack 에다가 담는다.
        그러면서, 계속해서 1 부터 시작해서, 곱을 계속 진행해가는데,
        만일 이 값이 Integer.MAX_VALUE (부호가 없었을 경우)
        혹은, Integer.MIN_VALUE (부호가 있었을 경우) 면, Integer.MIN_VALUE 혹은
        Integer.MAX_VALUE 로 고정한다.
        */

        // 일단 그러면 stack 을 선언하자.
        // 그리고서, 위의 조건대로만 진행하면 된다.

        // - == 45, + == 43, 공백 = 32
        Stack<Long> stack = new Stack<>();
        int sign = 0; // 부호를 저장할, 45 면 -, 0 or 43 이면 + 이다.
        boolean zeroMeet = false;

        for (int i = 0; i < s.length(); i++) {
            char character = s.charAt(i);
            if (character == 32) { // 공백이면 넘어감
                if (zeroMeet || sign != 0) {
                    break;
                }

                if (!stack.isEmpty()) {
                    break;
                }

                continue;
            } else if (character == 45 || character == 43) { // 이제, 부호를 만났을 경우
                if (zeroMeet) { // 만일 stack 이 비어있지 않는데, 만났으면?
                    sign = 100;
                    break;
                }

                // 지금 여기 있는 상황이, 이거임
                // Stack 이 차있는 상태에서 부호를 받게 되면 그냥 거기서 끝내면 됨
                if (!stack.isEmpty()) {
                    break;
                }

                sign += character;
                continue;
            } else if (!(48 <= character && character <= 57)) {
                break;
            } else if (stack.isEmpty() && character == 48) { // stack 가 비어있는데 0
                zeroMeet = true;
                continue;
            } else { // 위의 경우 다 아니면, just 문자임 그러면 그냥 담아줌            
                stack.push((long) (character - '0'));
            }
        }

        long res = 0;
        long mul = 1;

        if (sign == 45) { // 음수인 경우
            while (!stack.isEmpty()) {
                if (stack.size() >= 11) { // stack size 가 11 을 넘어가면 무조건 넘어감
                    res = Integer.MIN_VALUE;
                    break;
                }

                res += stack.pop() * mul; // stack 에서 하나씩 뺴주면서 곱해줌
                mul *= 10; // 곱하는 수 10씩 곱해줌

                if (Integer.MIN_VALUE > (res * -1)) {
                    res = Integer.MIN_VALUE;
                    break;
                }
            }
        } else if (sign == 43 || sign == 0) { // 양수인 경우
            while (!stack.isEmpty()) {
                if (stack.size() >= 11) { // stack size 가 11 을 넘어가면 무조건 넘어감
                    res = Integer.MAX_VALUE;
                    break;
                }

                res += stack.pop() * mul;
                mul *= 10;

                if (Integer.MAX_VALUE < res) {
                    res = Integer.MAX_VALUE;
                    break;
                }
            }
        }

        return (int) (res * (sign == 45 ? -1 : 1));
    }
}