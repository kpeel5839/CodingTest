import java.util.*;
import java.io.*;
import java.util.function.Function;

// 2568 : 전깃줄-2

/**
 * Example
 * 8
 * 1 8
 * 3 9
 * 2 2
 * 4 1
 * 6 4
 * 10 10
 * 9 7
 * 7 6
 */
public class Main {

    public static int upperBoundAndSet(List<Integer> increaseSequence, int value) {
        int left = 0;
        int right = increaseSequence.size() - 1;
        int setIndex = 0;

        while (left <= right) {
            int middle = (left + right) / 2;

            if (value <= increaseSequence.get(middle)) {
                setIndex = middle;
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }

        return setIndex;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2568_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int numberOfLine = Integer.parseInt(br.readLine());
        Integer[][] lines = new Integer[numberOfLine][2];

        for (int lineCount = 0; lineCount < numberOfLine; lineCount++) {
            st = new StringTokenizer(br.readLine());

            lines[lineCount][0] = Integer.parseInt(st.nextToken());
            lines[lineCount][1] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(lines, (o1, o2) -> o1[0] - o2[0]);
        List<Integer> increaseSequence = new ArrayList<>();
        increaseSequence.add(0); // 첫번째 요소
        int[] largeOrder = new int[numberOfLine];

        for (int lineCount = 0; lineCount < numberOfLine; lineCount++) {
            int value = lines[lineCount][1];

            if (increaseSequence.get(increaseSequence.size() - 1) < value) {
                increaseSequence.add(value);
                largeOrder[lineCount] = increaseSequence.size() - 1;
            } else {
                int setIndex = upperBoundAndSet(increaseSequence, value);
                largeOrder[lineCount] = setIndex;
                increaseSequence.set(setIndex, value);
            }
        }

        boolean[] notRemoveLine = new boolean[numberOfLine];
        int findIndex = increaseSequence.size() - 1;

        for (int index = numberOfLine - 1; index != -1; index--) {
            if (largeOrder[index] == findIndex) {
                findIndex--;
                notRemoveLine[index] = true;
            }
        }

        bw.write((numberOfLine - (increaseSequence.size() - 1)) + "\n");

        for (int index = 0; index < numberOfLine; index++) {
            if (!notRemoveLine[index]) {
                bw.write(lines[index][0] + "\n");
            }
        }

        bw.flush();
        bw.close();
    }
}
