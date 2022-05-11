import java.util.*;
import java.io.*;
import java.util.function.Function;

// 정수 삼각형

class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        int H = fun.apply(br.readLine());
        int[][] triangle = new int[H][];

        for (int i = 0; i < H; i++) {
            String[] input = br.readLine().split(" ");
            triangle[i] = new int[input.length];
            for (int j = 0; j < input.length; j++) {
                triangle[i][j] = fun.apply(input[j]);
            }
        }

        System.out.println(solution(triangle));
    }

    public static int solution(int[][] triangle) {
        /*
        걍 dp 임
        1번째 행부터 시작해서
        
        맨 끝에 있는 애랑
        맨 앞에 있는 애만 인덱스 에러 안나게 신경써주면 됨
        
        본인 인덱스 - 1 와 본인 인덱스
        Math.max(map[i - 1][j - 1], map[i - 1][j]) + map[i][j]
        이러면 다 해결되는 문제임
        
        생각보다 너무 쉬운 문제였음
        조금 해맸던 점은 dp[0][0] 을 초기화 안해줬었음
        */
        int answer = 0;
        int H = triangle.length;
        int[][] dp = new int[H][triangle[H - 1].length];
        dp[0][0] = triangle[0][0];

        for (int i = 1; i < H; i++) {
            for (int j = 0; j < triangle[i].length; j++) { // triangle[i] 크기 만큼만 돌ㄹ음
                int max;
                if (j == 0) { // 처음일 때
                    max = dp[i - 1][j];
                } else if (j == triangle[i].length - 1) { // 맨 마지막일 때
                    max = dp[i - 1][j - 1];
                } else {
                    max = Math.max(dp[i - 1][j - 1], dp[i - 1][j]);
                }

                dp[i][j] = max + triangle[i][j];
            }
        }

        for (int i = 0; i < triangle[H - 1].length; i++) {
            answer = Math.max(answer, dp[H - 1][i]);
        } // 답 구하기

        return answer;
    }
}