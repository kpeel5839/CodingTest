import java.util.*;
import java.io.*;

// 14003 : 가장 긴 증가하는 부분 수열 5

/*
-- 전제조건
가장 긴 수열을 찾고 , 그 수열의 사이즈를 출력하며 , 그 수열을 출력하라
-- 틀 설계
1 3 2 4 5 6

arr index
list index
indexArr(실제 배열이 들어간 위치(list 상에서) 이 3개값을 표현할 것임

최종 : 1 2 4 5 6

index 1 2 3 4 5 6

arr   1 3 2 4 5 6
list  1 2 3 4 5
index 1 2 2 3 4 5

그러면 여기서 이제 stack에서 거꾸로 찾는 과정은
arrIndex = list.size() - 1;
index 배열을 이용해서 찾는다. 찾으면 index--를 해주면서 stack에다가 쌓고
그 다음에 stack을 출력한다.

-- 해맸던 점
int idx = list.size() - 1; 이렇게 했었어야 했는데
int idx = list.size() 이렇게 해버림 idx 를 list.size() - 1 로 해야하는 이유는
index에다가 실제로 list에 들어간 인덱스를 입력해놨기 때문에 list.size() 의 인덱스가 마지막이 아니라
list.size() - 1 즉 , 마지막 인덱스의 값이 마지막으로 들어간 배열의 인덱스 값이기 때문이다.

그리고 처음에 list에 초기에 넣는 값이 0 이 아닌 -10억 까지도 들어오니까 Integer.MIN_VALUE 가 들어가야했음
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int n = Integer.parseInt(input.readLine());
        int[] number = new int[n];
        int[] index = new int[n];
        List<Integer> list = new ArrayList<>();

        st = new StringTokenizer(input.readLine());
        for(int i = 0; i < n; i++) number[i] = Integer.parseInt(st.nextToken());

        list.add(Integer.MIN_VALUE);

        for(int i = 0; i < n; i++){
            int value = number[i];

            if(value > list.get(list.size() - 1)){
                list.add(value);
                index[i] = list.size() - 1; // 처음에는 0이라는 값이 들어가 있으니까 , 그리고 얘가 들어간 다음에 어떤 위치에 들어갔냐를 보는 것이기 때문에
                // 들어간다음에 얘의 위치를 얻어내야 한다.
            }else{
                /*
                value 가 크거나 작으니
                value 가 들어갈 적절한 위치를 찾아줘야함
                이 때 이제 중요한 것이 있는데
                같은 숫자를 목격하였을 때 어떻게 대처할 것이며 , 이분탐색을 언제 종료할 것이냐도 굉장히 관건이다.
                예를 들어서
                1 2 4 이 있음
                그러면 mid는 2야 , 근데 내가 가지고 있는 value 가 3이다.
                그러면 4자리에 들어가야 할 것이다.
                이 떄 어떻게 하면 4자리에 value가 들어갈 수 있을 까?
                1 2 4 , mid == 1 list.get(mid) == 2 < 3 이다.
                값이 value보다 더 작은 경우는 right 쪽으로 이동해야 한다.
                그러면 left = mid + 1; 을 해주면 된다.
                이제 반대이다. 1이 들어왔다고 가정해보자 , 아니 2가 들어왔다고 가정해보자.
                그러면 1 2 4 mid == 1 list.get(mid) == 2 == value 2 이다.
                그러면 여기서 left가 상승하거나 , right 가 상승하는 결정을 할 수가 있다.
                이 때 만일 left가 상승하는 결정을 한다?
                그러면 답은 left - 1 이 될 수도 있다. 근데 , 이런 경우에는 아까 3이 들어가는 경우에
                left는 똑같이 2가 될 것이다 근데 제대로된 위치에 안들어가게 된다.
                이런 경우에는 분명하게도 left <= right 로 진행을 해야할 것이다. while 문을
                근데 이제 더 쉽게 가는 방법은 같거나 크거나 작을 때 그냥 left = mid , 혹은 right = mid를 해주는 방법이다.
                이 문제에서 right = mid 방법을 쓰면
                같거나 value가 더 작은 경우
                즉 if(list.get(mid) >= value) right = mid;
                else left = mid + 1; 을 할수가 있다. 그런 다음에 while 문에서 벗어 나면
                index[i] = right 를 해줄 수가 있다.
                 */
                int left = 1;
                int right = list.size() - 1;
                while(left < right){
                    int mid = (left + right) / 2;
                    if(list.get(mid) >= value) right = mid;
                    else left = mid + 1;
                }
                list.set(right , value);
                index[i] = right;
            }
        }

        /*
        그 다음에 index 배열의 끝부터 탐색을 하고
        list의 사이즈부터 시작해서 해당 인덱스에 들어간 것으 index에서 찾아서 stack에다가 집어넣는다.
         */
        output.write(list.size() - 1 + "\n");
        Stack<Integer> stack = new Stack<>();
        int idx = list.size() - 1;
        for(int i = n - 1; i != -1; i--){
            if(idx == index[i]){ //이 경우에는 stack에다가 집어넣고 , idx를 -- 해준다 , 다음 것을 찾아야 하니까
                stack.push(number[i]);
                idx--;
            }
        }

        while(!stack.isEmpty()){
            output.write(stack.pop() + " ");
        }

        output.flush();
        output.close();
    }
}

