import java.util.*;
import java.io.*;

// 13549 : 숨바꼭질 3
/*
-- 전제 조건
1. 수빈이가 동생을 찾으러 간다.
2. 자신의 2배가 되는 위치로 이동하게 되면 가중치가 0이고
3. 자신의 +1 이거나 -1 인 경우에는 가중치가 +1이다
4. 그 경우 가장 낮은 가중치로 동생을 찾을 때의 값을 구하자 (가중치 == 시간)
-- 틀 설계
1. 일단 check 배열을 만들어서 현재 이동하지 않은 곳은 -1로 표시
2. 이동한 곳은 거기까지 이동한 비용으로 표시한다.
3. 시작 위치인 n에다가 0을 집어넣어주고 자신의 2배되는 위치로 가는 *2를 우선시로 처리한다.
4. 이 경우 Queue 를 쓰면 안되고 LinkedList를 써야한다.
5. 이렇게 되면 순간이동이 우선적으로 처리가 되게 된다 , 즉 가중치가 0인 경우가 우선적으로 처리되고
6. 더 이상 가중치가 0이 되는 것이 없을 때 1을 처리하는 것이다.
7. 이 경우에는 0 , 1 즉 두개의 값만 존재할 때 0이 최소 가중치 , 1이 최대 가중치이기에
8. 0의 가중치를 가질 때에는 무조건적으로 최하의 비용이라는 것을 감안할 수 있어서 가능한 해결방안이다.
9. 그래서 이 문제는 0 - 1 bfs를 아주 잘 보여주는 문제이다.
 */
public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int limit = 100000;
        int[] check = new int[limit + 1];
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(n);
        Arrays.fill(check , -1);
        check[n] = 0;

        while(!queue.isEmpty()){
            Integer position = queue.poll();

            if(position == k){
                break;
            }

            if(position * 2 <= limit && check[position * 2] == -1){
                queue.addFirst(position * 2);
                check[position * 2] = check[position];
            }

            if(position != 0 && check[position - 1] == -1){
                queue.add(position - 1);
                check[position - 1] = check[position] + 1;
            }

            if(position != limit && check[position + 1] == -1){
                queue.add(position + 1);
                check[position + 1] = check[position] + 1;
            }
        }
//        System.out.println(Arrays.toString(check));
        System.out.println(check[k]);
    }
}