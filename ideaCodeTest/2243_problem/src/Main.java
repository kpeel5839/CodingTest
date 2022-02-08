import java.util.*;
import java.io.*;

// 2243 : 사탕 상자

/*
-- 전제조건
사탕의 맛이 1 ~ 1000000 까지 표현이 된다.
여기서 사탕을 빼는 연산과
사탕을 집어넣는 연산이 주어졌을 때
꺼내지는 사탕을 출력해라
-- 틀 설계
세그먼트 트리를 이용하고,
숫자의 맛에 따라 리프노드에다가 어떠한 값을 넣을지가 정해진다.
예를 들어서 1번 맛을 집어넣는다고 하면 start == end == 1 이 되는 지점에서 사탕의 개수를 추가해주고
그리고 여기까지 오는데의 tree[node] 값들도 수정해준다.
세그먼트의 합들은 사탕의 개수들을 나타낸다.
근데 왼쪽 , 오른쪽 중 더 맛있는 사탕은 왼쪽에 있으니까 그것을 고려해서 짜야한다.
 */
public class Main {
    public static int size = 1000000;
    public static long[] tree;
    public static void update(int node , int idx , int start , int end , int diff){
        /*
        update 할 때에는 들어온 사탕의 경로대로 추가해주어야 한다.
        그러면 두가지 선택방안이 있는데 update를 호출하면서 리프노드를 수정하냐
        아니면 리프노드를 update 마지막에 수정해주냐이다.

        나는 후자를 선택하겠다.
        update 할 때에는 그냥 idx 범위 내에 있냐가 전부이다.
        idx 범위 내에 있으면 계속 연산을 진행해주면 된다.
         */
        if(idx < start || end < idx) return; //범위를 벗어나는 경우
        if(start == end){ // start == end , 즉 리프노드에 도달하면 종료해준다.
            tree[node] += diff;
            return;
        }
        tree[node] += diff;
        update(node * 2 , idx , start , (start + end) / 2 , diff);
        update(node * 2 + 1 , idx , (start + end ) / 2 + 1, end , diff);
    }
    public static long search(int node , int start , int end , long find){
        /*
        search를 할 때에는 ㄷ내가 지금 탐색하는 find 의 값이 왼쪽 자식보다 크면
        오른쪽 자식으로 간다 , 근데 오른쪽 자식으로 가면 거기애들의 합이 있기 때문에
        왼쪽 애들까지 더한 게 아니다.
        예를 들어서 4번째 사탕을 찾고 싶고 , 1 번과 3번 사탕이 있다고 가정한다음
        1번은 왼쪽에 존재 , 3번은 오른쪽에 존재한다고 가정해보자.
        그러면 1 ~ 2 , 3 ~ 4 이렇게 가정할 수가 있다.
        그러면 1 ~ 2 = 2 , 3 ~ 4 = 2 이다 근데 내가 찾으려는 값은 4번째이다.
        여기서 오른쪽 트리가 무조건 더 맛없는 사탕이라는 것을 이용할 수가 있다.
        그래서 왼쪽 값의 범위에 포함되지 못하면 , 즉 오른쪽으로 가게 되면
        왼쪽 사탕들은 이제 다 배제 해야 하니까 find - 1 ~ 2 (왼쪽자식) 이런식으로 탐색할 수가 있다.
        그리고 리프노드에 도달하면 update(1 , start , 1 , size , -1) 를 호출하면 된다.
        그리고 리프노드의 start 값을 반환한다. (start ~ end) 는 사탕의 범위를 나타내기 때문에
        start == end 가 되었을 때 즉 start , end는 사탕의 맛을 나타낼 수 있음
        3 ~ 4 에서 왼쪽은 3번사탕 오른쪽은 4번사탕인 것처럼
         */
        if(start == end){
            update(1 , start , 1 , size , -1);
            return start;
        }

        if(tree[node * 2] >= find){ // 내가 찾으려는 순위의 사탕이 왼쪽 자식의 값보다 낮다면 거기 있는 것
            return search(node * 2 , start , (start + end) / 2 , find);
        }else{ // 내가 찾으려는 값이 왼쪽에 없다면 오른쪽으로 가면서 왼쪽 자식들을 배제하면서 진행
            return search(node * 2 + 1 , (start + end) / 2 + 1 , end , find - tree[node * 2]);
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output= new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int n = Integer.parseInt(input.readLine());
        tree = new long[1 << ((int)(Math.ceil(Math.log(size) / Math.log(2))) + 1)];

//        System.out.println(tree.length);

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            int oper = Integer.parseInt(st.nextToken());
            if(oper == 1){ //사탕을 빼는 연산
                int b = Integer.parseInt(st.nextToken());
                output.write(search(1 , 1 , size , b) + "\n");
            }else{ // 사탕을 집어넣는 연산
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                update(1 , b , 1 , size , c);
            }
        }

        output.flush();
        output.close();
    }
}
