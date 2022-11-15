import java.util.*;
import java.io.*;

// 1269 : 대칭 차집합

/**
 * 예제
 * 3 5
 * 1 2 4
 * 2 3 4 5 6
 */
public class Main {
    static Set<Integer> aSet = new HashSet<>();
    static Set<Integer> bSet = new HashSet<>();

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1269_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int sizeOfA = Integer.parseInt(st.nextToken());
        int sizeOfB = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < sizeOfA; i++) {
            aSet.add(Integer.parseInt(st.nextToken()));
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < sizeOfB; i++) {
            bSet.add(Integer.parseInt(st.nextToken()));
        }

        int aInstanceNotExistCountInB = 0;
        int bInstanceNotExistCountInA = 0;

        for (Integer instance : aSet) {
            if (!bSet.contains(instance)) {
                aInstanceNotExistCountInB++;
            }
        }

        for (Integer instance : bSet) {
            if (!aSet.contains(instance)) {
                bInstanceNotExistCountInA++;
            }
        }

        System.out.println(aInstanceNotExistCountInB + bInstanceNotExistCountInA);
    }
}
