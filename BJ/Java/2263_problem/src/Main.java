import java.util.*;
import java.io.*;

// 2263 : 트리의 순회
/*
--전제조건
입력으로 인오더 , 포스트 오더가 주어질때 프리오더를 구하는 프로그램을 작성하시오
인오더 -> 중위 순회
포스트 오더 -> 후위 순회
이렇게 입력이 주어졌을 때 트리를 만든다.
첫째 줄에 프리오더를 출력하면 된다.(프리오더 -> 전위 순회)
--틀 설계
일단 post order 는 맨 끝에 있는 노드가 root Node라는 것을 의미하는 것을 알 수 있다.
예를 들어서 이런식으로 트리가 주어진다고 가정해보자
이러한 트리가 있다고 해보자
         1
     2       3
  4     5       6
     7     8  9
이런식의 트리가 있다고 가정해보자 그러면 이런식의 순회가 나오게 된다.
in - 4 2 7 5 8 1 3 9 6
post - 4 7 8 5 2 9 6 3 1
pre - 1 2 4 5 7 8 3 6 9
그러면 in , post order로 post order의 가장 마지막이 root node라는 것과 in order의 root order 오른쪽 왼쪽은 구분이 된다는 것을 이용하면 된다.
설게는 실제로 트리를 만드는 것보다는 그냥 재귀적으로 분할해서 post의 마지막번째 것을 출력하고 또 재귀적으로 진행하게 하면 된다.
그러니까 System.out.print(post[last] + " "); 이렇게 하고 재귀호출 왼쪽 , 오른쪽을 해주면 되는 것이다.
1번째
in - 4 2 7 5 8 / 1 / 3 9 6
post - 4 7 8 5 2 / 9 6 3 / 1
print - 1
post의 가장 마지막에 있는 1을 출력해주고 in에서 post[last]와 같은 숫자의 정점을 찾아서 갈라주면서 두개의 사이즈를 체크 해주면 된다.
그 다음에 post에서 왼쪽 사이즈 /  오른쪽 사이즈 만큼 해주면 된다.
2번째
in - 4 / 2 / 7 5 8
post - 4 / 7 8 5 / 2
print - 1 2
3번째
in - 4
post - 4
print - 1 2 4
이런식으로 사이즈가 하나가 되면 바로 return을 해준다.
다시 그러면 2번쨰의 오른쪽 것으로 거슬러 올라가야한다.
4번째
in - 7 / 5 / 8
post - 7 / 8 / 5
print - 1 2 4 5
5번째
in - 7
post - 7
print - 1 2 4 5 7
6번째
in - 8
post - 8
print - 1 2 4 5 7 8
7번째
in - 3 / 9 6
post - 9 6 / 3
print - 1 2 4 5 7 8 3
여기서 중요한 점은 에외로 post[last] 가 in 에서 가장 첫번째나 가장 마지막에 있으면 첫번째면 left를 호출 x , 만약 마지막에 있으면 right를 호출 x이다
8번째
in - 9 / 6
post - 9 / 6
print - 1 2 4 5 7 8 3 6
9번째
in - 9
post - 9
print - 1 2 4 5 7 8 3 6 9
이런식으로 또 하나만 남았을 때 하나를 출력하면 된다.

그러면 이제 실제적으로 들어가면 딱 주어졌을 때 일단 post , in order 배열에다가 담아 놓는다.
그 다음에 post[n - 1]을 in order에서 찾아서 0 index - 1 , index + 1 , last 이렇게 해서
size를 구해야 하니까 index - 1 - 0 + 1 그리고 위에는 post 사이즈는 last - index + 1 + 1 이렇게 해서 size를 구해서
post는 start ~ leftSize , leftSize + 1 ~ last로 나누어서 처리를 계속 지속적으로 하면된다.
이것은 dfs로 해결하는 걸로 하자

-- 해맸던 점
rightSize 구할 때 inLast - (rootIndex + 1) + 1; 이렇게 했었어야 했는데
rightSize = inLast - rootIndex + 1 + 1 이렇게 해서 잘 못 나왔었음
 */
public class Main {
    public static BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int n;
    public static int[] post , in;
    public static void dfs(int inStart , int inLast , int postStart , int postLast) throws IOException{
        /*
        그냥 일단 오른쪽만 있는 상황
        왼쪽만 갈 수 있는 상황으로 나뉘어야 함
        그 다음에 if(inStart == intLast) 같은 경우에는 return을 해주어야함
        in - 4 2 7 5 8 / 1 / 3 9 6
        post - 4 7 8 5 2 / 9 6 3 / 1
        그 다음에 이런식의 구현을 해주어하는데
        일단 post[postLast]를 얻어주고
        그 다음에 rootIndex를 찾아준다.
        그 다음에 leftSize , rightSize 를 찾아준다
        위에 같은 경우에는 rootIndex - 1 - start + 1 으로 할 수 있다
        그리고 last - rootIndex + 1 + 1 이렇게 rightSize도 구해줄 수가 있다.
        이제 만일 rootIndex가 0이다 그러면 leftSize가 0이 나올 것이다
        만일 rootIndex가 n - 1이다 그러면 n - 1 - n - 1 + 1 + 1 이니까 이것도 0이 나올 것이다
        그러니까 leftSize , rightSize를 구하면은 왼쪽을 호출을 하지 않을 것인지
        오른쪽을 호출을 하지 않을 것인지가 결정된다.
         */
        output.write(post[postLast] + " ");
        int rootIndex = 0;
        if(inStart == inLast){
            return;
        }
        for(int i = inStart; i <= inLast; i++){
            if(post[postLast] == in[i]){rootIndex = i; break;}
        }
        int leftSize = rootIndex - inStart;
        int rightSize = inLast - (rootIndex + 1) + 1;
        if(rightSize == 0){ //왼쪽만 호출하는 상황
            /*
            in - 9 6 / 3
            post - 6 9 / 3
             */
            dfs(inStart , rootIndex - 1 , postStart , postStart + leftSize - 1);
        } else if(leftSize == 0){ //오른쪽만 호출하는 상황
            /*
            in - 3 / 9 6
            post - 9 6 / 3
             */
            dfs(rootIndex + 1 , inLast , postStart , postStart + rightSize - 1);
        }else{ // 둘다 호출하는 상황 이 경우에도 무조건 왼쪽을 먼저 호출 하여야 함
            /*
            in - 4 2 7 5 8 / 1 / 3 9 6
            post - 4 7 8 5 2 / 9 6 3 / 1
            여기서는 둘을 나누는 상황이니까 잘 지켜야함
             */
            dfs(inStart , rootIndex - 1 , postStart , postStart + leftSize - 1);
            dfs(rootIndex + 1 , inLast , postStart + leftSize, postLast - 1);
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(input.readLine());
        in = new int[n];
        post = new int[n];

        for(int i = 0; i < 2; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < n; j++){
                if(i == 0){
                    in[j] = Integer.parseInt(st.nextToken());
                }else{
                    post[j] = Integer.parseInt(st.nextToken());
                }
            }
        }
        dfs(0 , n - 1 , 0 , n - 1);
        output.flush();
        output.close();
    }
}
