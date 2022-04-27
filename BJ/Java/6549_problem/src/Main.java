import java.util.*;
import java.io.*;

// 6549 : 히스토그램에서 가장 큰 직사각음
/*
-- 전제조건
이건 일전에 풀었던 문제이다.
히스토그램은 직사각형 여러개가 아래쪽으로 정렬되어 있는 도형이다.
직사각형은 너비는 같지만 높이가 다 다를 수 있다.

이 경우에서 순서대로 너비가 1인 직사각형들의 높이가 주어지면 , 가장 큰 직사각형의 넓이를 구하시오
-- 틀 설계
큐에다가 받는 높이랑
지금이 몇번째인지를 받는다.
그 다음에 stack의 size 가 1이상이면 stack의 지금 가장 peek에 있는 것을 비교한다.
그래서 지금 들어가는 것이 그것보다 작으면 게속 뺀다.
근데 뺄 때 지금 현재의 index - 그때의 index * value 값을 게속 ans와 비교한다.
그리고 만약 끝까지 게속 높은 숫자만 나올 수도 있다.
이제 입력의 끝에 다다르면 , 거꾸로부터 다 뺀다.
근데 이제 다 뺄때 끝에서부터 빼는 것이니까 끝 + 1 - 빼는 것의 index * height 로 게속 ans에다가 집어넣는다.
그러면 끝날 듯 그냥 data 객체 하나만 만들어주면 될 듯

-- 해맸던 점
그냥 개 해맸고 , 설계자체가 오류가 있었음
그래서 답을 보고 풀었음

-- 내가 이해한 내용
일단 기본 이념은 이것이다 , 현재 stack의 peek 값보다 낮은 값이 들어온다?
그 말은 즉슨 바로 이전에 들어온 사각형은 이제 더 이상 영역을 넓힐 수는 없다는 의미가 된다.
근데 여기서 바로 이전 사각형의 너비가 1이 되는 것이 아니다 , 왜냐하면 이전의 사각형의 이전 값이 더 클 수도 있기 때문이다.
예를 들어서 5 4 3 이렇게 들어왔다면 , 3보다 4가 작으니까 더 이상 영역을 넓힐 수 있지만 너비는 2이다. 왜냐하면 5보다 크기 때문이다.
그래서 여기서 구할 수 있는 것은 일단 5 4 3 이렇게 들어와서 stack에서 빼기 시작할 텐데 이전에도 그 행동을 반복했을 것이란 말이다.
그 의미는 즉슨 4보다 작은 값만이 존재한다라는 것이다 , 이전에 들어온 4라는 값이 지금 stack에서는 최대값이 될 거라는 말이다,
그 말은 즉슨 stack.peek는 항상 최대값이란느 의미가 된다. 그래서 4 3 이 될 것이다.
그러면 4의 너비는 2이다 이것을 어떻게 구하느냐 , 4를 빼면은 stack이 empty해진다. 그렇기 때문에
이것을 뺐을 때 stack.isEmpty이면 그냥 바로 그 사각형의 넓이를 곱해준다. 4의 인덱스 값은 1이니 (1 + 1) * 4 해주면 해당 사각형의 넓이를 구해줄 수가 있다.
다른 예를 들어보자 , 5 4 3 4 3 2 이렇게 들어왔다고 가정하자
5 4 여기서 5는 빠지고 5의 인덱스는 0 이니까 (0 + 1) * height 하면 5이다.
ans = 5;
4 3 여기서 4는 빠지고 stack이 empty 하니까 (1 + 1) * height 하면 8이다.
ans = 8;
3 4 그냥 집어넣어진다. 왜냐하면 삼각형이 그대로 이어질 수가 있기 때문이다.
3 4 3 여기서는 4와 3이 빠진다 , 동일한 값도 뺀다는 가정하에 이다.
4가 먼저 빠질 때 4의 너비는 1이다 그것을 어떻게 아냐 , stack.peek는 항상 최댓값이다 , stack.peek를 poll 하고 바로 이전 값의 인덱스를 보면 된다.
근데 바로 이전값인 3이 인덱스가 2이다 , 그렇기 때문에 4의 인덱스인 3에 3의 인덱스를 빼준다음 height 를 곱해주면 된다. 그래서 (3 - 2) * height = 4 이다.
그 다음에 3을 빼는데 3은 stack.isEmpty이니 인덱스 + 1 을 곱해준다. 그래서 (2 + 1) * 3 = 9이다.
ans = 9;
3 2 이다. 3이 더작다.
그래서 stack is empty니까 (4 + 1) * 3 = 15
ans = 15;
2만 남았고 , 입력의 마지막이다 , 그렇기 때문에 다 비워줘야 한다.
stack.isEmpty라면 이것이 최소값이다 , 그렇지 않으면 이것은 최댓값이다.
그렇기 때문에 stack.isEmpty() ? i : i - 1 - stack.peek().index;
가 너비이고 * height 즉 2를 하면 6 * 2 = 12 이다.
그래서 ans = 15가 되게 된다.

이게 같은 값도 비워내는 이유는 그것이다. 사각형의 넓이를 구할 때 앞으로 올 구간의 넓이까지는 구하지를 않는 것이다.
지금까지 쌓여온 사각형까지의 넓이까지만 계산을 하기 때문에 , 같은 값도 빼는 것이고 , 이전의 너비들을 구하기 위해서 지금 현재 높이 - 1 즉 , 지금 사각형을 넣기 전의 peek 값을 이용해서
이전 사각형들의 너비를 얻어내는것이다.

그래서 지금 현재 사각형보다 높이가 좁은 사각형이 여러개이면 , stack에 쌓인 사각형이 여러개이면 현재 높이의 바로 이전 높이 - 지금 현재 stack.peek() 값 * data.height 를 하는 것이다.
그리고 stack 이 비어있다면 지금 뽑은 data 값은 현재까지의 최소값이다. (지금 입력으로 들어온 높이를 제외한)
그렇기 때문에 그냥 i * height 를 하는 것이다.
참고로 i는 현재 0 부터 시작하기 때문에 값을 뺐는데 stack.isEmpty라면 그 값은 너비가 i인 것이다.
그렇기 때문에 i * height 를 해주어도 되는 것이고

i - 1 - stack.peek() 는 위에서도 설명했듯이 현재의 높이 바로 이전 것의 인덱스 - stack에서 바로 이전의 인덱스 즉 , 요놈보다 바로 작은 높이의 인덱스 * height 인 것이다.
이게 너비이니까

이렇게 해서 히스토그램의 직사각형의 넓이를 구할 수 있다.
 */
public class Main {
    public static class Data{
        long height;
        long index;
        public Data(long height , long index){
            this.height = height;
            this.index = index;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        while(true){
            st = new StringTokenizer(input.readLine());
            int n = Integer.parseInt(st.nextToken());
            long ans = 0;
            if(st.countTokens() == 0 && n == 0) break;
            Stack<Data> stack = new Stack<>();
            for(int i = 0; i < n; i++){
                int height = Integer.parseInt(st.nextToken());
                while(!stack.isEmpty() && stack.peek().height >= height){
                    Data data = stack.pop();
                    long width = stack.isEmpty() ? i : i - 1 - stack.peek().index;
                    ans = Math.max(ans , width * data.height);
                }
                stack.add(new Data(height , i));
            }
            while(!stack.isEmpty()){ //끝까지 남은 것들 다 빼내면서 출력
//                System.out.println(stack);
//                System.out.println((n - data.index) * data.height);
                Data data = stack.pop();
                long width = stack.isEmpty() ? n : n - 1 - stack.peek().index;
                ans = Math.max(ans , width * data.height);
            }
            output.write(ans + "\n");
        }

        output.flush();
        output.close();
    }
}
