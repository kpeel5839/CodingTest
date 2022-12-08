import java.util.*;
import java.io.*;

// 5157 : Bailout Bonus

/**
 * Example
 * 2
 * 4 2 6 95
 * 1 4
 * 1 1000000
 * 3 25000
 * 3 102425
 * 4 567444
 * 1 150
 * 4 7000
 * 2 0 2 99
 *
 * 1 500000
 * 2 500000
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_5157_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int testCases = Integer.parseInt(br.readLine());

        for (int testCase = 1; testCase <= testCases; testCase++) {
            st = new StringTokenizer(br.readLine());

            int totalCompanies = Integer.parseInt(st.nextToken());
            int numberOfBailoutCompanies = Integer.parseInt(st.nextToken());
            int numberOfExecutive = Integer.parseInt(st.nextToken());
            int taxRate = Integer.parseInt(st.nextToken());
            int totalBonus = 0;

            st = new StringTokenizer(br.readLine());
            Set<Integer> bailoutCompanies = new HashSet<>();
            for (int i = 0; i < numberOfBailoutCompanies; i++) {
                bailoutCompanies.add(Integer.parseInt(st.nextToken()));
            }

            for (int i = 0; i < numberOfExecutive; i++) {
                st = new StringTokenizer(br.readLine());
                int company = Integer.parseInt(st.nextToken());
                int bonus = Integer.parseInt(st.nextToken());

                if (bailoutCompanies.contains(company)) {
                    totalBonus = totalBonus + ((bonus * taxRate) / 100);
                }
            }

            bw.write("Data Set " + testCase + ":\n" + totalBonus + "\n\n");
        }

        bw.flush();
        bw.close();
    }
}
