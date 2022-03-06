import java.util.*;
import java.io.*;

// 1208 : 부분수열의 합 2
/*
-- 전체 설계
부분수열의 합을 구하는데 , 합이 S가 되는 경우의 수를 구하면 된다.
-- 틀 설계
결국 답을 보았는데 ,
이분탐색은 없었다.
그냥 2 ^ 40 은 너무 크다.
그래서 , 이것을 일단 반으로 나눠서 부분합을 구할 것이다.

그렇게 되면 2 * (2 ^ 20) 이 된다.
그렇다는 것은 200만 개의 부분수열이 나오게 된다.
그런 다음에 List 를 각각 정렬하고 , 하나는 왼쪽에서 부터
하나는 오른쪽에서부터 시작하면 된다.

그러면 왼쪽에서 시작하는 것의 합과 , 오른쪽에서 시작하는 합이 같은 경우를 찾으면 되는데
만약 합이 s보다 작다 , 그렇다면 왼쪽에서 시작하는 것을 증가시켜서 , 더 큰 값을 만들고,
더 크다 그러면 오른쪽에서 시작하는 것을 감소시켜서 더 작은 값을 만든다.

그 다음에 , 만일 합이 같은 것이 나오게 되면 , 그것과 같지 않을 때까지 왼쪽에서 시작하는 것의
인덱스를 높이고 , 오른쪽은 낮춘다.
그러면서 카운트를 센 것을 , 서로 곱해주면 된다 , 왜냐하면 합이 되는 경우가 왼쪽에서 시작하는 것은 1개
오른쪽에서 시작하는 것은 2개라고 했을 떄 , 총 해당 숫자로는 해당 합을 만드는 경우는 1 * 2 즉 , 2가지 인 것이다.

그래서 그렇게 하면 되고 , 이제 특별한 경우는 합이 S == 0 인 경우는
둘다 아무것도 선택하지 않은 경우도 선택되게 되니 , ans -- 를 해서 출력해준다.

-- 해맸던 점
leftCnt , rightCnt 를 long 으로 선언하지 않아서,
해당 leftCnt * rightCnt 가 long 의 범위를 넘을 때를 , 고려하지 못해서
값이 잘못 나왔었다. 고치니까 바로 맞았다.
 */
public class Main {
    public static int[] arr;
    public static int N , S;
    public static long ans = 0;
    public static List<Integer> leftList = new ArrayList<>() , rightList = new ArrayList<>();
    public static void dfs(int sum , int idx , int end , List<Integer> list){
        /*
        end 가 실질적으로 더해야하는 , leftArr 의 인덱스보다 하나 더 해야 한다.
        왜냐하면 , 맨 마지막 요소까지도 sum에 더해서 , 추가해봐야하기ㄷ 때문이다.
         */
        if(idx == end){
            list.add(sum);
            return;
        }

        dfs(sum , idx + 1 , end , list); // 선택하지 않는 경우
        dfs(sum + arr[idx] , idx + 1 , end , list); // 현재의 idx를 선택하는 경우

    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        N = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());

        arr = new int[N];
        st = new StringTokenizer(input.readLine());
        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // N == 40 이라고 가정했을 때 , idx를 나타내면
        dfs(0 , 0, N / 2, leftList); // 0 ~ 19
        dfs(0, N / 2 , N, rightList); // 20 ~ 39 모든 숫자들을 다 돌아봐야 하기 때문에 , 실질적으로 끝나야 하는 곳보다 하나 더 더해서 인덱스를 넣어준다.

        Collections.sort(leftList);
        Collections.sort(rightList);

        int left = 0;
        int right = rightList.size() - 1;

        while(true){
            if(left >= leftList.size() || 0 > right) break; // left 가 사이즈를 넘어가거나 , 혹은 right 가 음수가 되었을 떄

            int leftValue = leftList.get(left);
            int rightValue = rightList.get(right);

            if(leftValue + rightValue == S){
                long leftCnt = 0;
                while(left < leftList.size() && leftValue == leftList.get(left)){
                    leftCnt++;
                    left++;
                }
                long rightCnt = 0;
                while(right >= 0 && rightValue == rightList.get(right)){
                    rightCnt++;
                    right--;
                }
                ans += leftCnt * rightCnt;
            }
            else{
                if(leftValue + rightValue < S){ // S 보다 작을 떄에는 left를 올려줘서 leftValue 를 올려주어야 한다.
                    left++;
                }
                else{ // S 보다 클 때에는 right를 줄여줘야 한다.
                    right--;
                }
            }
        }

        if(S == 0) ans--;
        System.out.println(ans);
    }
}