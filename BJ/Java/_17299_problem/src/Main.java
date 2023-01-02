import java.util.*;
import java.io.*;

// 17299 : 오등큰수

/**
 * 7
 * 1 1 2 3 4 2 1
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_17299_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        Map<Integer, Integer> frequency = new HashMap<>();

        int N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int[] number = new int[N];
        int[] answer = new int[N];

        for (int i = 0; i < N; i++) {
            int addNumber = Integer.parseInt(st.nextToken());
            number[i] = addNumber;
            frequency.put(addNumber, frequency.getOrDefault(addNumber, 0) + 1);
        }

        Stack<Integer> index = new Stack<>();
        // 모노톤 스택으로 내림차순으로 유지하면됨
        for (int i = 0; i < N; i++) {
            while (!index.isEmpty() && frequency.get(number[index.peek()]) < frequency.get(number[i])) {
                int fillIndex = index.pop();
                answer[fillIndex] = number[i];
            }

            index.push(i);
        }

        while (!index.isEmpty()) {
            answer[index.pop()] = -1;
        }

        for (int i = 0; i < N; i++) {
            bw.write(answer[i] + " ");
        }

        bw.flush();
        bw.close();
    }
}
