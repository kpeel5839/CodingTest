import java.util.*;
import java.io.*;

// 16566 : 카드 게임

/**
 * 예제
 * 10 7 5
 * 2 5 3 7 8 4 9
 * 4 1 1 3 8
 */
public class Main {
    static int N;
    static int M;
    static int K;
    static int[] arr;
    static boolean[] isExist;
    static int[] parent; // 본인보다 큰 바로 위의 부모를 연결하는, 카드를 버리는 경우 find 을 통해서 갱신

    static int find(int vertex) { // 이분 탐색으로 찾고, find 로 결국 이 수보다 가장 큰 숫자를 찾음
        if (vertex == parent[vertex]) { // 본인이 부모이면
            return vertex;
        } else {
            return parent[vertex] = find(parent[vertex]); // 본인의 부모를 부르면서 반환 받는 최종 부모를 본인의 부모로 임명
        }
    }

    static void makeSortArr() { // Sort 한 Arr 를 만들어줌
        arr = new int[M];
        int index = 0;

        for (int i = 1; i <= N; i++) {
            if (isExist[i]) {
                arr[index++] = i;
            }
        }
    }

    static int getNextAboveCard(int findCardNumber) { // upperBound 로 찾아야함.
        // 여기서 해야할 것 일단 upperBound 를 이분탐색으로 찾음
        // 그리고 찾은 수의 index 를 가지고 find 를 통해서 받음, 이때 돌려받는 값도 인덱스임
        // 그리고 이것을 return 하기로 하고, 그 이전에 해당 인덱스의 부모를 바로 뒤 인덱스로 함, 근데 범위를 넘어가는 경우, 즉 가장 끝의 원소인 경우에는 예외
        int left = 0;
        int right = M - 1; // M - 1
        int indexOfAnswer = 0;

        while (left <= right) { // 두개의 순서가 뒤바뀌지 않을 때까지
            int middle = (left + right) / 2;

            if (findCardNumber <= arr[middle]) {
                indexOfAnswer = middle;
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }

        indexOfAnswer = find(indexOfAnswer);
        if (indexOfAnswer != M - 1) { // 가장 끝 인덱스가 아닌 경우에만
            parent[indexOfAnswer] = indexOfAnswer + 1; // 다음 요소를 부모로 가르킬 수 있도록
        }

        return arr[indexOfAnswer];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_16566_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken()); // 최대 10000 임
        isExist = new boolean[N + 1]; // at most 400만

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            isExist[Integer.parseInt(st.nextToken())] = true;
        }

        makeSortArr(); // Arr 만들어줌
        parent = new int[M];
        for (int i = 0; i < M; i++) { // 각각 처음에는 본인을 부모로 지정
            parent[i] = i;
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
            bw.write(getNextAboveCard(Integer.parseInt(st.nextToken()) + 1) + "\n"); // 더 큰 것을 찾아야하니까 + 1 을 해서 보냄
        }

        bw.flush();
        bw.close();
    }
}