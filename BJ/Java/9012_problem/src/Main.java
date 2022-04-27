import java.util.*;
import java.io.*;

// 9012 : 괄호
/*
-- 전제조건
괄호가 주어진다.
이것이 맞는 괄호 양식인지 맞는지 아닌지 테스트 케이스마다
문구를 출력한다.
-- 틀 설계
stack 에다가 '(' 문자열이 들어오면 push 한다.
')' 이 들어오면 pop 을 한다.
만일 pop을 하기 이전에 stack에 더이상 '('가 들어왔다는 게 없다면 No를 바로 출력한다.
혹은 끝까지 실행했는데 stack.size() 가 0이 아니면 No를 출력한다.
그래서 결국 위에 조건에 다 들지 않으면 YES를 출력한다.
그 다음에 케이스를 실행할 때에는 Stack을 clear시켜준 다음에 실행시켜준다.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        Stack<Integer> stack = new Stack<>();

        int n = Integer.parseInt(input.readLine());

        for(int i = 0; i < n; i++){
            stack.clear();
            String testCase = input.readLine();
            for(int j = 0; j < testCase.length(); j++){
                char letter = testCase.charAt(j);
                if(letter == '('){
                    stack.push(1);
                }else{
                    if(stack.size() == 0) {
                        System.out.println("NO");
                        break;
                    }
                    stack.pop();
                }
                if(j == testCase.length() - 1){
                    if(stack.size() != 0) {
                        System.out.println("NO");
                    }else{
                        System.out.println("YES");
                    }
                }
            }
        }
    }
}
