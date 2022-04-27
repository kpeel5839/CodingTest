import java.util.*;
import java.io.*;

// 1918 : 후위 표기식

/*
-- 전제조건
전위 표기식으로 주어진 후위표기식으로 변경해서 출력한다.
-- 틀 설계
+ , - 는 1이라는 연산자 우선순위를 주고 , * , / 는 2라는 연산자 우선순위를 준다.
그리고서 이제 순서대로 진행하는데 알파벳을 만나면 바로 출력하고 , '(' 를 만나면 넘어간다.
그리고 이제 본인의 연산자보다 , 우선순위가 높거나 , 같은애를 만나면 바로 stack에 있는 것을 비우면서
지금 연산자 우선순위보다 낮은애까지 간다 , 무조건 낮아야 한다 같아도 출력
그 다음에 낮은애까지 가서 다시 읽기 시작하는 것이다. ')' 를 만나면 지금 queue에 있는 것을 출력한다.
다 출력하지 말고 , 그 다음 연산자까지 읽어서 우선순위를 본 다음에 출력한다.

아 괄호도 집어넣고 , 괄호가 빠질때까지 빼면 된다.

내가 생각한 그래서 후위표기식은

a + b * c * d
abc*d*+

a + b + c
ab+c+

a * (b + c)
abc+*

a * (b + c) * d
abc+*d*

(a + b) * (c + d)
ab+cd+*

결국 , 괄호에 들어가면 밖에 있는 것들은 일단 괄호 안보다는 우선순위가 모두 낮게 된다.
그렇기 때문에 , stack에서 빼다가 ( 를 만나게 되면 )를 만나서 뺀게 아닌 이상 빼지 않는다.

결론은 , 해당 연산자보다 우선순위가 낮은 아이를 만나면 stack에서 빼지 않는 것이다,
왜냐하면 stack에서 먼저 뺀다는 것은 앞에 위치하게 되는 것이고 , 그 말은 즉 우선순위가 높다는 의미이기 때문이다.

 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        Stack<Character> stack = new Stack<>();

        String pro = input.readLine();

        /*
        stack 에서 빼는 것은 다 우선순위가 높은 것들이다.
        고로 ) 를 만나게 되면 , 무조건 (까지 빼고
        + , - 를 만나면 모든 것을 다 빼고 ,( 를 만나기 전까지
        * , / 를 만나면 + , - , ( 를 만날 때까지 다뺀다.
        그러면서 문자는 바로 출력하고
        문자가 아닌 것들만 해당 처리를 해준다.
         */
        for(int i = 0; i < pro.length(); i++){
            char character = pro.charAt(i);
//            System.out.println(stack);
            if(character == '(') {
                stack.push(character);
                continue;
            }
            if(character == '+' || character == '-'){
                while(!stack.isEmpty() && stack.peek() != '('){
                    output.write(stack.pop());
                }
            }
            else if(character == '*' || character == '/'){
                while(!stack.isEmpty() && stack.peek() != '(' && stack.peek() != '+' && stack.peek() != '-'){
                    output.write(stack.pop());
                }
            }
            else if(character == ')'){
                while(true){
                    if(stack.peek() == '('){
                        stack.pop();
                        break;
                    }
                    output.write(stack.pop());
                }
                continue;
            }else{
                output.write(character);
                continue;
            }
            stack.push(character);
        }

        while(!stack.isEmpty()){
            output.write(stack.pop());
        }

        output.flush();
        output.close();
    }
}
