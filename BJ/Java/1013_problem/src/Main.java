import java.util.*;
import java.io.*;
import java.util.function.Function;

// 1013 : Contact

/**
 * -- 전제 조건
 * 정형식 쓰는 문제이다. (주어진 패턴을 찾는 문제)
 * -- 틀 설계
 * 그냥 문제에서 주어진 거 정형식에다가 넣고서, matches 실행하면 됨
 * 완전 정형식 어떻게 하는지 보는 문제
 *
 * -- 해맸던 점
 * 정형식에 띄어쓰기가 있었으면 안됐었는데(그것까지 인식하는 것 같음), 띄어쓰기 해서 해맸고,
 * 띄어쓰기 없애니까 맞았음
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String wellFormed = "(100+1+|01)+";
        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            String problem = br.readLine();
            if (problem.matches(wellFormed)) {
                bw.write("YES" + "\n");
            } else {
                bw.write("NO" + "\n");
            }
        }

        bw.flush();
        bw.close();
    }
}
