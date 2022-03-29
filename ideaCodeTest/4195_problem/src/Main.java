import java.util.*;
import java.io.*;

// 4195 : 친구 네트워크
/*
-- 전제 조건
그냥 민혁이는 소셜 네트워크 사이트에서 친구를 모으는 것이 취미옥,
어떤 사이트의 친구 관계가 생긴 순서대로 주어졌을 때 ,
두 사람의 친구 네트워크에 몇 명이 있는지 구하는 프로그램을 작성
-- 틀 설계
Hash 를 이용한 index 지정과, 그리고 parent 를 이용한 find , union 을 통해서
parent , count 를 이용하면 될 것 같다.

근데 이제 친구 관계가 순서대로 주어졌을 떄 , 얘내들은 무조건 친구이다.
그렇기 때문에 union 을 진행하면서 , 그에 맞게 나온 집합의 개수를 출력하고

만일 이미 친구면은 , 그냥 아무거나 parent 로 올라가서 집합의 개수를 출력하면 된다.
각 그냥 parent , size 다 초기화하고 , 부모 사이즈만 계속 갱신하면 된다.

일단 계속 친구를 받으면서 map 에 존재하지 않다면 , name , map.size() + 1 꼴로 만들어주고
그 다음에 서로의 부모가 같은지 보고 같지 않다면 union 진행하면서 서로 count 값도 갱신해주고
만일 같다면 그냥 아무 부모까지 올라가서 count 출력하고

그러면 될 것 같다.

생각보다 너무 쉽게 맞음
 */
public class Main {
    public static HashMap<String , Integer> map = new HashMap<>();
    public static int T , N;
    public static final int MAXSIZE = 200001;
    public static int[] parent , count;
    public static int find(int a){
        /*
        그냥 원래 하듯이 부모를 찾으면 된다.
         */
        if(a == parent[a]){
            return a;
        }

        return parent[a] = find(parent[a]); // 본인의 부모로 재귀 호출을 진행하면서 부모를 찾아나가고
        // 결국 반환되는 부모를 본인의 부모로 다 갱신한다 , 이렇게 되면 , 해당 경로에 불렸던 애들의 부모들이 다 루트 노드로 갱신된다.
    }

    public static void union(int a , int b){
        /*
        그냥 원래 하듯이 집합을 합치는데 , 여기서는 특별하게
        합치면서 합친 곳에 본인의 집합의 크기도 추가해준다.

        근데 솔직히 그냥 따까리들 집합의 개수는 상관이 없어서 , 다 같이 집합 개수로
        갱신해줄 필요는 없는 것 같다 , 분명히 메모리를 더 줄일 방법이 있을 것 같은데
        지금은 그냥 그대로 하자.

        근데 이미 find 를 통해서 둘의 부모가 같은지 다른지를 확인을 한다.
        그러고서 같지 않다면 union 을 부를 것이다.
        같다면 union 을 부르지 않는다.

        그러면 그냥 찾은 부모로 a , b 넘기면 되지 않을까?
        그러면 굳이 이 메소드에서 부모를 다시 찾을 필요가 없을 것 같다.
         */

        count[a] += count[b]; // 부모들의 집합의 개수들을 더해서 루트 노드에 저장하고
        parent[b] = a; // 루트 노드를 a로 변경
    }
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        T = Integer.parseInt(input.readLine());

        int index = 0;

        while(index++ < T){
            N = Integer.parseInt(input.readLine());

            count = new int[MAXSIZE];
            parent = new int[MAXSIZE];

            for(int i = 0; i < MAXSIZE; i++){
                parent[i] = i;
                count[i] = 1; // 다 본인이 존재하니 집합의 개수는 1에서 시작이다.
            }

            for(int i = 0; i < N; i++){
                st = new StringTokenizer(input.readLine());

                String first = st.nextToken();
                String second = st.nextToken();

                // a 가 현재 존재하지 않는다면 자리를 배정
                if(!map.containsKey(first)) map.put(first , map.size() + 1);
                if(!map.containsKey(second)) map.put(second , map.size() + 1);

                // 이제 배당이 되었으니까 find , union 연산을 진행할 것임 일단 , 부모를 찾자
                int a = find(map.get(first));
                int b = find(map.get(second));

                // 찾았다 만일 같다면?
                if(a == b) output.write(count[a] + "\n");

                // 다르다면 union 을 진행하고 , a 에 합쳐졌으니까 a를 출력
                else{
                    union(a , b);
                    output.write(count[a] + "\n");
                }
            }
        }
        output.flush();
        output.close();
    }
}
