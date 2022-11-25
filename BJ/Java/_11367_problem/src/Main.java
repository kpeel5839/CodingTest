import java.util.*;
import java.io.*;

// 11367 : Report Card Time

/**
 * Example
 * 5
 * Bilbo 13
 * Sam 90
 * Pippin 78
 * Frodo 97
 * Merry 70
 */
public class Main {
    /**
     * Standard is
     * A+: 97-100
     * A: 90-96
     * B+: 87-89
     * B: 80-86
     * C+: 77-79
     * C: 70-76
     * D+: 67-69
     * D: 60-66
     * F: 0-59
     */
    static String[] letterOfGrade = {"A+", "A", "B+", "B", "C+", "C", "D+", "D", "F"};
    static String[] rangeOfGrade = {"97 100", "90 96", "87 89", "80 86", "77 79", "70 76", "67 69", "60 66", "0 59"};
    static Map<String, int[]> numberMapGrade = new HashMap<>();

    static void initNumberMapGrade() {
        for (int i = 0; i < letterOfGrade.length; i++) {
            String[] numberRange = rangeOfGrade[i].split(" ");
            numberMapGrade.put(letterOfGrade[i], new int[] {Integer.parseInt(numberRange[0]), Integer.parseInt(numberRange[1])});
        }
    }

    static String getGrade(int score) {
        String resultOfGrade = "";

        for (String key : numberMapGrade.keySet()) {
            int[] range = numberMapGrade.get(key);

            if (range[0] <= score && score <= range[1]) {
                resultOfGrade = key;
                break;
            }
        }

        return resultOfGrade;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_11367_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int numberOfTestCases = Integer.parseInt(br.readLine());
        initNumberMapGrade();

        for (int testCase = 0; testCase < numberOfTestCases; testCase++) {
            st = new StringTokenizer(br.readLine());

            String nameOfHobbit = st.nextToken();
            int scoreOfHobbit = Integer.parseInt(st.nextToken());
            bw.write(nameOfHobbit + " " + getGrade(scoreOfHobbit) + "\n");
        }

        bw.flush();
        bw.close();
    }
}
