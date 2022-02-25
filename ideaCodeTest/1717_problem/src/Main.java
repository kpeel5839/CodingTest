import java.util.*;
import java.io.*;

// 1717 : 집합의 표현

/*
-- 전제 조건
초기에 각각의 집합들을 이루고 있고,
0이며 같을 수도 있다.
그리고 0이 오게되면 union 하면 되고
1이 오게 되면 find 하면 된다.
그래서 출력 결과를 반환해라.
-- 틀 설계
union 하면 두개의 부모를 동일하게 하고
find 하면 부모를 반환하는데 , 거기서 부모가 같으면 Yes , 아니면 no를 출력하면 된다.
근데 여기서 가장 중요한 점은 , 시간을 빠르게 하기 위해서 , 꼭 find 시에 parent 를 가장 루트노드로 갱신해주어야 한다.
 */
public class Main {
    public static int[] parent;
    public static int find(int a){
        /*
        find 하면서 가장 중요한 것은
        마지막에 부모를 찾았을 때 return 하면서 , 다른 애들의 부모를
        직접적으로 루트에 연결시키는 것이 가장 중요하다.
         */
        if(parent[a] == a){
            return a;
        }else{
            parent[a] = find(parent[a]);
            return parent[a];
        }
    }
    public static void union(int a , int b){
        /*
        a , b 의 부모를 찾은 다음 하나로 합치면 되는데,
        원래 사이즈가 더 큰 것을 찾아서 합쳐야지만 , 완벽한 트리가 나오지만
        그냥 해도 될 것 같다.
         */
        int parent1 = find(a);
        int parent2 = find(b);

        if(parent1 != parent2){
            parent[parent2] = parent1;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        parent = new int[n + 1];

        for(int i = 0; i <= n; i++){
            parent[i] = i; // 본인의 부모를 본인 값으로 수정
        }
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(input.readLine());
            int oper = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if(oper == 0){ // union
                // union 을 호출하면 됨
                union(a, b);
            }else{ // find
                // 여기서 출력하면 됨
                if(find(a) == find(b)) output.write("YES" + "\n");
                else output.write("NO" + "\n");
            }
        }

        output.flush();
        output.close();
    }
}
