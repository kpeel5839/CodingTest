import java.util.*;
import java.io.*;

// 1981 : 배열에서 이동
/*
-- 전제조건
n * n짜리의 배열에서 1 , 1에서 n , n 까지 이동하려고 한다.
이동할 때에는 상 하 좌 우의 네 인접한 칸으로만 이동가능하다.

이와 같이 이동하다보면 , 배열에서 몇 개의 수를 거쳐서 이동하게 된다. 이동하기 위해 거쳐 간 수들 중 최댓값과
최솟값의 차이가 가장 작아지는 경우를 구하는 프로그램을 작성 , 즉 (1 , 1)부터 (n , n)까지 이동하는 데 거쳐간
숫자들의 모임에서 최댓값 - 최솟값이 가장 작아지는 경우로 가야하는 것이다 , 그리고서 값이 나오면 그 값을 출력한다.

n 이 100까지도 가능하고 , 배열의 각 값들은 200까지 가능하다.
-- 틀 설계
1 1 3 6 8
1 2 2 5 5
4 4 0 3 3
8 0 2 3 4
4 3 0 2 1

여기서는 1 , 1 , 2 , 2 , 0 , 2 , 0 , 2 , 1의 경우에서 가능하다.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
    }
}

