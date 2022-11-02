import java.util.*;
import java.io.*;
import java.util.function.Function;

// 10836 : 여왕벌

/**
 * 예제
 * 4 2
 * 2 3 2
 * 0 6 1
 */
public class Main {
    static int N;
    static int M;
    static int[] add;
    static int[][] larva;

    static void growParsing (int aRange, int bRange, int cRange) {
        int point = add.length - 1;
//        System.out.println(point + " " + aRange + " " + bRange + " " + cRange);

        if (cRange != -1) {
            point -= cRange;

            if (bRange != - 1) {
                add[point]++;
            } else {
                add[point] += 2;
            }

            point--;
        }

        if (bRange != -1) {
            point -= bRange;
            add[point]++;
            point--;
        }

        // aRange 는 사실 필요없는 값
//        System.out.println(Arrays.toString(add));
    }

    static void realGrow () {
        // add 를 기반으로
        // 일단 위로, 오른쪽으로 순서대로 증가시키고 최후에 맨 윗 행부터 쫙 내리면서 값 복사
        int addValue = 0;
        int idx = 0;

        for (int i = M - 1; i != 0; i--) { // 위쪽으로
            addValue += add[idx++];

            larva[i][0] = 1 + addValue;
        }

        for (int i = 0; i < M; i++) { // 오른쪽으로
            addValue += add[idx++];

            larva[0][i] = 1 + addValue;
        }

        for (int i = 1; i < M; i++) {
            int copyValue = larva[0][i];
            for (int j = 1; j < M; j++) {
                larva[j][i] = copyValue;
            }
        }
    }

    static StringBuilder getStringBuilderOfResult() {
        // 여기서는 실제로 map 을 출력하고, 그것을 StringBuilder 로 변환해서 넘겨줄 것임
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                sb.append(larva[i][j] + " ");
            }
            sb.append("\n");
        }

        return sb;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_10836_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken()); // 가로, 세로 크기
        N = Integer.parseInt(st.nextToken());
        add = new int[M * 2 - 1];
        larva = new int[M][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            // 자 이제 3개의 입력을 파싱할 차례임, 여기서 실제로 키워줄 것임
            growParsing(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1);
        }

        realGrow(); // 여기서 실제로 키워줌, add 를 기반으로
        System.out.println(getStringBuilderOfResult().toString());
    }
}
