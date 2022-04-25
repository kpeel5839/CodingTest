import java.util.*;
import java.io.*;

class Test {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String s = br.readLine();
        int res = 0; // 답을 저장할 변수
        int j = 0; // 이전 노드를 탐색할 변수
        int preJ = 0;
        int count = 0;
        int preLength = 0;

        // i 는 1부터 시작함
        for (int i = 0; i < s.length(); i++) {
            if (i == j) { // i == j, 라는 것은 문자열 탐색을 다시 시작하거나, 처음임
                count++;
                preJ = j;
            } else if (s.charAt(i) == s.charAt(j)) {
                // 근데 이게 처음으로 같은 문자열을 만난 건지 그것이 중요함
                // 처음으로 같은 문자열을 만난 것이면, preLength 로 집어넣어놔야함
                if (preLength == 0) {
                    preLength = count; // preLength 로 저장
                    count = 0; // 얼마나 같은 문자열이 반복될지 이제 저장 해야함
                }

                count++;
                j++;

                if (count == preLength) { // 이전에 탐색했던 문자열과 같으면
                    res = Math.max(res, preLength);
                    j = i + 1; // 결과값을 도출하고, j를 i로 변경
                    preLength = 0;
                    count = 0;
                }

            } else { // 같은 문자열이 아닐 때
                // 여기서, 같은 문자열 진행되다가 온 경우는 특별하게 진행해주어야 함
                if (preLength != 0) {
                    count += preLength;
                }

                count++; // 생각해보니까, j 도 증가시키면 안됨
                j = preJ;
                preLength = 0; // preLength 0 으로 초기화 계속
            }
        }

        System.out.println(res);
    }
}