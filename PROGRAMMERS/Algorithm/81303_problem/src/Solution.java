import java.util.*;
import java.io.*;

class Solution {
    /*
    결국 답을 보았는데
    아니 이게 맞다고라고 생각이 들정도로 단순했다.
    
    X 가 30만이니까
    30 * 20만은?
    600억번이다.
    
    이게 되는 게 솔직히 나는 의아하다.
    그래서, 이진 탐색을 진행해보았었지만
    잘 안되었다.
    
    분명히 좋은 아이디어였던 것 같은데
    그래서 굉장히 단순한 방법으로 풀어보려고 한다.
    
    Stack 과 Node class 를 이용할 것이다.
    그리고, pre, next 변수를 유지해서, 다음 요소와 이전 요소를 저장할 수 있도록 했다.
    
    답을 보고, 왜 다시 삭제되었다가 꺼낸 노드는 수정하지 않아도 되지라는 생각을 했는데,
    내가 생각했던 오류가 있었던 부분은 LIFO 의 형태르 꺼내지 않았을 경우에 발생하였음
    즉, 내가 제일 최근에 삭제한놈은 정상적으로 Link 가 연결되어 있음
    그렇기 떄문에, 최근에 삭제한놈을 가져오게 되면
    걔는 이미 Link 정보가 정상적인 것임
    그렇기 떄문에, 걔는 수정할 필요가 없음 하지만, 만약 순서에 맞지 않게 즉 LIFO 의 형태로 가져오지 않는다면?
    분명히 수정을 요할 것이지만, 그 경우 너무 복잡하다.
    
    쨋든 이렇게 풀게 되면 내가봤을 때, DOWN, UP 이 많이 없는 것 같다 만일 예외 테케로 
    DOWN, UP 만 30만번을 20만번 반복하는 입력이 있었다면?
    무조건 시간초과 날 수 밖에 없음
    
    600억번이기에 시간초과가 날 수밖에 없다.
    난 애초에 이 상황을 고려해서 그렇게 짰었던 건데
    애초에 그게 시간이 더 걸려버리니까 조금 의아하다.
    */
    static class Node {
        int pre;
        int cur;
        int next;

        Node(int pre, int cur, int next) {
            this.pre = pre;
            this.cur = cur;
            this.next = next;
        }
    }

    public String solution(int n, int k, String[] cmd) {
        int[] pre = new int[n];
        int[] next = new int[n];
        Stack<Node> stack = new Stack<>();
        StringBuilder sb = new StringBuilder("O".repeat(n));

        for (int i = 0; i < n; i++) {
            pre[i] = i - 1;
            next[i] = i + 1;
        }

        next[n - 1] = -1;

        for (int i = 0; i < cmd.length; i++) {
            String[] input = cmd[i].split(" ");

            if (input.length == 2) {
                String dir = input[0];
                int count = Integer.parseInt(input[1]);

                if (dir.equals("U")) {
                    while (count-- > 0) {
                        k = pre[k];
                    }
                } else {
                    while (count-- > 0) {
                        k = next[k];
                    }
                }
            } else {
                if (input[0].equals("C")) { // 삭제
                    stack.push(new Node(pre[k], k, next[k])); // 삭제되는 이전 노드와 다음노드를 저장
                    sb.setCharAt(k, 'X');

                    if (next[k] == -1) { // 맨 끝에 있는 경우
                        next[pre[k]] = -1;
                    } else if (pre[k] == -1) { // 앞에 있는 경우
                        pre[next[k]] = -1;
                    } else {
                        next[pre[k]] = next[k];
                        pre[next[k]] = pre[k];
                    }

                    if (next[k] == -1) { // 맨 끝에 있는 경우만 이전으로 가고
                        k = pre[k];
                    } else { // 아닌 경우는 뒤로 간다.
                        k = next[k];
                    }
                } else { // 복구
                    Node node = stack.pop();
                    sb.setCharAt(node.cur, 'O'); // 다시 살려버령

                    if (node.next == -1) { // 맨 끝
                        next[node.pre] = node.cur;
                    } else if (node.pre == -1) { // 맨 앞
                        pre[node.next] = node.cur;
                    } else { // 걍 중간
                        next[node.pre] = node.cur;
                        pre[node.next] = node.cur;
                    }
                }
            }
        }

        return sb.toString();
    }
}