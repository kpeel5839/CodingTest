import java.util.*;
import java.io.*;

// 5582 : 공통된 부분 문자열

/*
-- 전제조건
서로 다른 문자열 두개에서
부분 문자열의 크기가 가장 큰 것의 길이를 출력하시오.
-- 틀설계
그냥 서로 맞으면 + 1 하면 될 것 같은데
2차원 배열로 진행하고
서로 맞지 않으면 0으로 진행하고
LCS 같은 경우는 위 , 왼쪽에서 가져오는 연산을 진행하는데
이 문제 같은 경우는 같은 문자열이 연속으로 반복되지 않으면 초기화 되는 것이니까 그렇게 하면 될 듯하다.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        String a = " " + input.readLine();
        String b = " " + input.readLine();

        int[][] arr = new int[a.length()][b.length()];
        int res = 0;
        for(int i = 1; i < a.length(); i++){
            for(int j =1; j < b.length(); j++){
                if(a.charAt(i) == b.charAt(j)) arr[i][j] = arr[i - 1][j - 1] + 1;
                else arr[i][j] = 0;
                res = Math.max(res , arr[i][j]);
            }
        }

        System.out.println(res);
    }
}

