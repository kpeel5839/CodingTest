import java.util.*;
import java.io.*;
import java.util.function.Function;

// 3954 : Brainf**k 인터프리터

/**
 * -- 전제조건
 * - 포인터가 가리키는 수를 1 감소시킨다. (modulo 28)
 * + 포인터가 가리키는 수를 1 증가시킨다. (modulo 28)
 * < 포인터를 왼쪽으로 한 칸 움직인다.
 * > 포인터를 오른쪽으로 한 칸 움직인다.
 * [ 만약 포인터가 가리키는 수가 0이라면, [ 와 짝을 이루는 ] 의 다음 명령으로 점프한다.
 * ] 만약 포인터가 가리키는 수가 0이 아니라면, ] 와 짝을 이루는 [ 의 다음 명령으로 점프한다.
 * . 포인터가 가리키는 수를 출력한다.
 * , 문자 하나를 읽고 포인터가 가리키는 곳에 저장한다. 입력의 마지막(EOF)인 경우에는 255를 저장한다.
 *
 * 이럴 때, 정상 종료가 가능하다면
 * Terminates 를 출력하고
 * 그렇지 않다면 Loops 가 발생한 부분에 대해서 출력하면 된다.
 * -- 틀 설계
 * 생각보다 이 문제는 고려할 사항이 많지 않았음
 * 그리고, 무엇보다 5000만 번을 실제로 실행해도 수행시간이 7초나 주어지기 때문에
 * 상관이 없엇음
 *
 * -- 해맸던 점
 * 일단 pointerPos 를 0으로 다시 초기화 해주지 않아서 해맸었음
 * 그거 외에는 답보고 풀었지만 굉장히 빠르게 풀었음
 * 생각보다, 쉬운 문제였음
 * 어려운 문제는 아니였던 것 같음
 * 근데 전제 조건들이 살짝 뭔가 이해하기 어려운 부분들이 있었어서 조금 헷갈렸던 것 같다.
 */
public class Main {
    static int T;
    static int sm;
    static int sc;
    static int si;
    static int pid;
    static int pointerPos;
    static int inputPos;
    static final int LIMIT = 50000000;
    static StringBuilder sb = new StringBuilder();
    static String programCode;
    static String inputCode;
    static int[] arr;
    static int[] bracket;

    static void doStep() {
        char c = programCode.charAt(pid);

        switch (c) {
            case '-' : // pointer 가 가르키는 수를 1 감소 시킨다. 최대 255
                arr[pointerPos] = (arr[pointerPos] - 1) < 0 ? 255 : (arr[pointerPos] - 1);
                break;
            case '+' :
                arr[pointerPos] = (arr[pointerPos] + 1) > 255 ? 0 : (arr[pointerPos] + 1);
                break;
            case '<' : // Pointer 를 왼쪽으로
                pointerPos = (pointerPos - 1) < 0 ? (sm - 1) : (pointerPos - 1);
                break;
            case '>' :
                pointerPos = (pointerPos + 1) >= sm ? 0 : (pointerPos + 1);
                break;
            case '[' :
                if (arr[pointerPos] == 0) { // PointerPos 가 가르키는 수가 0 이라면
                    pid = bracket[pid]; // 움직여 준다.
                }
                break;
            case ']' :
                if (arr[pointerPos] != 0) { // PointerPos 가 가르키는 수가 0 이 아니라면
                    pid = bracket[pid];
                }
                break;
            case '.' :
                break;
            case ',' :
                arr[pointerPos] = (inputPos == si) ? 255 : inputCode.charAt(inputPos++);
                break;
        }

        pid++;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        T = fun.apply(br.readLine());

        while (T-- > 0) {
            String[] input = br.readLine().split(" ");

            sm = fun.apply(input[0]); // 배열 크기
            sc = fun.apply(input[1]); // 프로그램 크기
            si = fun.apply(input[2]); // 입력 문자의 크기

            // 해주고 다 초기화 해준다.
            pid = 0;
            inputPos = 0;
            pointerPos = 0;

            programCode = br.readLine();
            inputCode = br.readLine();
            arr = new int[sm];
            bracket = new int[sc];
            Stack<Integer> stack = new Stack<>(); // 괄호를 연관지어주어야 함

            for (int i = 0; i < programCode.length(); i++) { // 괄호 연관 지어주기 완료
                char c = programCode.charAt(i);

                if (c == '[') { // 처음 나온 경우
                    stack.push(i);
                } else if (c == ']') { // 짝이 나온 경우, 맨 마지막에 나온 괄호와 연관지어져야 함
                    bracket[i] = stack.peek();
                    bracket[stack.pop()] = i;
                }
            }

            int count = 0;

            while (pid < sc && count < LIMIT) { // LIMIT 이상 실행되면 나가리
                count++;
                doStep();
            }

            if (pid == sc) {
                sb.append("Terminates").append("\n");
            } else { // 그 위치에서 다시 시작해줌, 무한루프로 끝난 위치의 괄호 처음과 끝을 알아야 하기 떄문에
                // 짜피 다른 거 건드릴 필요 자체가 없음, 왜냐하면 그냥 무한 루프로 끝난 거니까, 짜피 안건드려도 안끝남
                count = 0;
                int sta = Integer.MAX_VALUE;
                int des = Integer.MIN_VALUE;
                while (count < LIMIT) {
                    count++;
                    sta = Math.min(sta, pid);
                    des = Math.max(des, pid); // 괄호의 시작 위치와 끝을 알아낸다.
                    doStep();
                }

                // 근데 sta 는 괄호의 시작 바로 뒤를 나타낸다 그렇기에 -1 을 해준 값을 추가해준다.
                sb.append("Loops ").append(sta - 1)
                        .append(" ").append(des).append("\n");
            }
        }

        System.out.println(sb);
    }
}
