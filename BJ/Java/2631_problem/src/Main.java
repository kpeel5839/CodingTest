import java.util.*;
import java.io.*;

// 2631 : 줄 세우기
/*
--전제 조건
줄을 바꿔서 순서대로 줄을 세우는 경우 ,
이 경우 가장 최소 횟수를 구해라.
--틀 설계
생각해보니까 , 오름 차순으로 정렬이 되어 있는 애들은 그대로 두고 , 나머지 애들을 옮기면 된다.
이 값이 최소가 되려면 가장 긴 오름 차순을 찾으면 된다.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        int size = Integer.parseInt(input.readLine());
        int[] child = new int[size];
        int[] dp = new int[size];
        int max = 0;

        for(int i = 0; i < size; i++){
            child[i] = Integer.parseInt(input.readLine());
        }

        for(int i = 0; i < size; i++){
            int maxVal = 0;
            for(int j = i; j != -1; j--){
                if(child[j] < child[i] && maxVal < dp[j]) maxVal = dp[j];
            }
            dp[i] = maxVal + 1;
        }

        for(int i = 0; i < size; i++){
            if(max < dp[i]) max = dp[i];
        }

        System.out.println(size - max);
    }
}