import java.util.*;
import java.io.*;

// 1915 : 가장 큰 정사각형
/*
-- 전제 조건
가장 큰 정사각형의 넓이를 출력하면 된다.
즉 , 그 말은 해당 정사각형이 존재한다면 , 그 셀의 개수가 넓이이다.

한번 그냥 되게 low level 하게 진행해보자.
1000 * 1000 * 1000 == 10 억이다.

생각을 잘 못해서 low level 하게 진행할 뻔
-- 틀 설계
가장 기본적인 생각은 그냥 1 ~ 1000 까지 진행해보는 것이다
정사각형이 만들어지나
근데 , 이 방법은 1000 * 1000 * 1000 이니 10억번을 진행해야 한다.
이 맨 끝에 1000을 획기적으로 줄인다면 많아봐야 100만번 혹은 100 만 * 상수이다.
이렇게 되면 굉장히 괜찮은데 , 1000을 줄이기 위해서는 해당 정사각형을 어떻게 빨리 걸러내느냐가 중요하다.

일단 가장 바로 보이는 것은 0이 껴있으면 , 정사각형이 될 수 없다.
근데 0도 안껴있고 , 중간에 맨끝에가서야 걸린다면?

아니면 이러면 되지 않나
가장 끝에서 시작하는 것이다 , 아니다 이러면 만약 제일 작은 사이즈면 그때까지 짜피 다 검사해야한다.
만일 중간에 있다고 하더라도

아니면 이분탐색으로 찾는 방법은 어떨까?
해당 사이즈가 존재한다면 크게 아니면 작게 진행하는데
존재할 때에 ans 에다가 집어넣으면 된다.

알고보니까 이게 1000 * 1000 * 1000 * 1000 이였다
개 큼
 */
public class Main {
    public static int H , W;
    public static int[][] map;
    public static boolean findSquare(int size){
        for(int i = 0; i < H - size + 1; i++){
            for(int j = 0; j < W - size + 1; j++){
                boolean find = true;
                for(int c = 0; c < size; c++){
                    for(int v = 0; v < size; v++){
                        if(map[i + c][j + v] == 0) find = false;
                    }
                }
                if(find) return true;
            }
        }

        return false;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        map = new int[H][W];

        for(int i = 0; i < H; i++){
            String string = input.readLine();
            for(int j = 0; j < W; j++){
                map[i][j] = string.charAt(j) - '0';
            }
        }

        int left = 0;
        int right = Math.min(H ,W);
        int ans = 0;

        while(left <= right){
            int mid = (left + right) / 2;

            if(findSquare(mid)){ // 찾으면 무조건 올림
                ans = mid * 2;
                left = mid + 1;
            }
            else{
                right = mid - 1;
            }
        }

        System.out.println(ans);
    }
}
