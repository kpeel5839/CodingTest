import java.util.*;
import java.io.*;

// 14002 : 가장 긴 증가하는 부분 수열 4

/*
-- 전제 조건
그냥 가장 긴 수열을 찾고 , 가장 긴 수열의 크기와 , 그것을 출력하시오
-- 틀 설계
사이즈가 1000 밖에 안되니까 , 그냥 dp 연산에다가 , 배열에다가 담아서 , 본인 값보다 작고 , 본인 인덱스보다 작으면 끝
 */
public class Main {
    public static class Data{
        int count;
        int value;
        public Data(int count , int value){
            this.count = count;
            this.value = value;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int N = Integer.parseInt(input.readLine());
        int[] numberList = new int[N];
        int[] dp = new int[N];

        st = new StringTokenizer(input.readLine());

        for (int i = 0; i < N; i++) {
            numberList[i] = Integer.parseInt(st.nextToken());
        }

        // 이제 0번째 요소에는 그냥 1을 넣고 , 1번째 부터 탐색을 한다.
        // 본인 이전의 것을 탐색하면서 , 본인보다 작은 수 중 가장 값이 큰 것을 저장한다.
        dp[0] = 1;
        int max;

        for (int i = 1; i < N; i++) {
            max = 0;
            for (int j = i - 1; j != -1; j--) { // 이 과정에서 가장 큰 값을 찾아야함
                if (numberList[j] < numberList[i]) { // 이게 더 큰 경우에만 max를 갱신함
                    max = Math.max(max, dp[j]);
                }
            }
            dp[i] = max + 1; // 그래서 여기서 이제 찾은 맥스값을 가지고 거기에다가 본인을 추가한 +1 을 해줘서 값을 집어 넣는다.
        }

        // 이제 dp 값 중 가장 큰 값을 찾고!
        // 그 값으로 result 배열을 만든다음에
        // result[result 크기 - 1] = maxNumber; 을 넣어준다.
        // 그 다음에 이제 dp 에서 순차대로 내려오면서 j와 dp 값이 같으면서 result[j + 1] > number 한 것들을 찾아서 순차대로 배열에다가 담은 다음 출력한다.
        // 근데 이거 너무 귀찮으니까 , 그냥 stack 으로 객체 만들어서하자.

        max = 0;
        int maxIndex = 0;
        for(int i = 0; i < N; i++){
            if(max < dp[i]){
                max = dp[i];
                maxIndex = i;
            }
        }

        Stack<Data> stack = new Stack<>();
        stack.push(new Data(max , numberList[maxIndex])); // 값과 카운트를 집어넣었음
        output.write(max + "\n");

        for(int i = maxIndex - 1; i != -1; i--){ // maxIndex 바로 아래부터 시작해서 , -1이 되지 않을 때까지 진행하면서 stack 에다가 담는다.
            // stack.peek().count 값보다 1만 작으면서 , dp 값이 그리고 stack.peek().value 보다 numberList[i] 가 작은 애들만 고른다.
            if(stack.peek().count - 1 == dp[i] && stack.peek().value > numberList[i]){
                stack.push(new Data(dp[i] , numberList[i]));
            }
        }

        while(!stack.isEmpty()){
            output.write(stack.pop().value + " ");
        }

        output.flush();
        output.close();
    }
}