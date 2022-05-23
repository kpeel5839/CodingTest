import java.util.*;
import java.io.*;

public class Main {
    static int N;
    static StringBuilder sb = new StringBuilder();
    static void recursive(int depth) {
        if (depth == (N - 1)) {
            addUnderBar(depth);
            sb.append("\"재귀함수가 뭔가요?\"\n");

            addUnderBar(depth);
            sb.append("\"재귀함수는 자기 자신을 호출하는 함수라네\"\n");

            addUnderBar(depth);
            sb.append("라고 답변하였지.\n");
            return;
        }

        addUnderBar(depth);
        sb.append("\"재귀함수가 뭔가요?\"\n");

        addUnderBar(depth);
        sb.append("\"잘 들어보게. 옛날옛날 한 산 꼭대기에 이세상 모든 지식을 통달한 선인이 있었어.\n");

        addUnderBar(depth);
        sb.append("마을 사람들은 모두 그 선인에게 수많은 질문을 했고, 모두 지혜롭게 대답해 주었지.\n");

        addUnderBar(depth);
        sb.append("그의 답은 대부분 옳았다고 하네. 그런데 어느 날, 그 선인에게 한 선비가 찾아와서 물었어.\"\n");

        recursive(depth + 1);

        addUnderBar(depth);
        sb.append("라고 답변하였지.\n");
    }

    static void addUnderBar(int n) {
        for (int i = 0; i <= n; i++) {
            sb.append("____");
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        sb.append("어느 한 컴퓨터공학과 학생이 유명한 교수님을 찾아가 물었다.\n"
                + "\"재귀함수가 뭔가요?\"\n"
                + "\"잘 들어보게. 옛날옛날 한 산 꼭대기에 이세상 모든 지식을 통달한 선인이 있었어.\n"
                + "마을 사람들은 모두 그 선인에게 수많은 질문을 했고, 모두 지혜롭게 대답해 주었지.\n"
                + "그의 답은 대부분 옳았다고 하네. 그런데 어느 날, 그 선인에게 한 선비가 찾아와서 물었어.\"\n");

        recursive(0);

        sb.append("라고 답변하였지.");

        System.out.println(sb);
    }
}
