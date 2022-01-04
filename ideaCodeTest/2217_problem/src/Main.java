import java.util.*;
import java.io.*;

// 2217 : 로프
/*
-- 전제조건
1. 최대한 많은 중량을 들어올려야한다.
2. k개의 로프로 w의 중량을 들어 올리면 각 로프에 걸리는 중량은 k / w 이다.
3. k개의 로프로 중량을 들어올렸을 때 가장 많이 들어올릴 수 있는 중량을 구하라
-- 틀 설계
1. 입력을 받는다.
2. 2중 포문으로 자신이 견딜 수 있는 무게보다 더 크거나 같은 무게를 견딜 수 있는 로프수를 구한다.
3. 본인의 무게 * 로프수 = 견딜 수 있는 무게 (지금 본인이 최저 중량을 견딜 수 있는 로프라고 하였을 때 견딜 수 있는 중량)
4. 10000을 넘지 않는 자연수이니까 10000의 배열에다가 저장을 해놓고서 10001 정확히하면 그리고 거기다가 개수를 저장한 뒤에
5. 본인이 1이면 count 를 1부터 10001까지 다 더한다. (자신의 숫자 이상부터 count에 더하는 것)
 */

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine());
        int max = 0;
        int[] rope = new int[n];
        int[] ropeCount = new int[10001];
        for(int i = 0; i < n; i++){
            rope[i] = Integer.parseInt(input.readLine());
            ropeCount[rope[i]]++;
        }
        for(int i = 0; i < n; i++){
            int value = rope[i];
            int count = 0;
            for(int j = value; j <= 10000; j++){
                count += ropeCount[j];
            }
            value *= count;
            if(max < value) max = value;
        }
        System.out.println(max);
    }
}
