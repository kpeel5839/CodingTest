import java.util.*;
import java.io.*;

// 15649 : N과 M (1)
/*
-- 전제조건
자연수 N과 M이 주어졌을 때 , 아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성
1부터 N까지 자연수 중에서 중복 없이 M개를 고른 수열
-- 틀 설계
dfs 함수를 만든다.
M크기의 전역변수 배열을 만든다.
depth == M이 되면 출력한다
 */
public class Main {
    public static int[] number;
    public static int[] visited;
    public static int n , m;
    public static BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
    public static void dfs(int depth) throws IOException{
        /*
        if(depth == m) 이면 출력하고
        for(int i = 1; i <= n; i++)  까지 집어넣는다.
        그리고 중복된 숫자를 빼기 위해서 visited[i] = 1 를 해서 i 숫자를 쓰면 뒤에서는 못쓰게 한다.
         */
        if(depth == m) {
            for(int i = 0; i < m; i++){
                output.write(number[i] + " ");
            }
            output.write("\n");
            return;
        }

        for(int i = 1; i <= n; i++){
            if(visited[i] != 1) {
                visited[i] = 1;
                number[depth] = i;
                dfs(depth + 1);
                visited[i] = 0;
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        number = new int[m];
        visited = new int[n + 1];

        dfs(0);
        output.flush();
        output.close();
    }
}