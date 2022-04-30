import java.util.*;
import java.io.*;
import java.util.function.Function;

// 14725 : 개미굴
/**
 * -- 전제 조건
 * 로봇개미를 투입시켜서, 경로들이 주어지면,
 * 그 경로들을 가지고, 개미굴을 형상화하면 된다.
 * -- 틀 설계
 * 이거는 그냥, 일단 내가 처음에 생각했던 것처럼 하나의 개미굴 아래에 연결된 개미굴들은 동일한 이름의 개미굴은 주어지지 않는다고 가정해야지, 풀 수가 있다.
 * 그래서 나는 그렇게 할 예정이다.
 *
 * 일단, Node 를 만들어서 field 를 id, value, child 로 구성해야 할 것 같다.
 * id 는 dfs 를 위해서, unique 한 값을 부여하는 것이고, value 는 그 노드가 가지고 있는 이름 즉, 먹이 이름이다.
 * 그리고 child 는 HashMap으로 구성할 예정이다.
 *
 * root 노드가 필요하다, root 노드는 id (부여 안해도 될 듯, 짜피 child 로 연결해서, 부모한테 가는 방향은 연결 x) 값은 0 value = "root" (아무렇게나 설정해도 된다)
 * 그리고 child 는 이제, 주어지는 것으로 채워나갈 것이다.
 *
 * 하나의 라인이 주어지면 root 에서부터 시작해서 진행을 한다.
 * 어떤식으로 하냐면, HashMap에 주어진 것이 없다면? map.put("먹이이름", Node) 해주면 된다.
 * 그리고서, 게속 그런식으로 진행해주면 되는데, 만일 있다면 그냥 가면 되는 것이다.
 *
 * 그래서 이런식으로 트리를 구성하고, dfs 를 하게 될때에는, 그냥 EntrySet 반환 받아서, List 로 구성을 한다음에,
 * Collections 하고 그 다음에 순서대로 for 문을 이용하여서, 진행하면 될 듯 하다.
 *
 * Iterator it = map.keySet().iterator(); 이것을 통해서, map의 key 들을 다 받아내고,
 * 그것을 map.get(key) 를 해서, list 에다가 집어넣은다음 정렬해서, 순서대로 진행하면 된다.
 */
public class Main {
    public static int N;
    public static int K; // 각각 먹이의 길이
    public static String[] feed; // 각각의 먹이를 저장할 변수
    public static Node root = new Node("root"); // root 라는 value 를 넣어서 초기화, 이걸로 구분 가능 root 인지 아닌지
    public static StringBuilder sb = new StringBuilder(); // 결과값을 출력할 sb

    public static class Node {
        String value;
        HashMap<String, Node> child;
        public Node(String value) {
            this.value = value;
            this.child = new HashMap<>(); // 생성과 동시에 초기화, 솔직히 위에서 해주어도 됨
        }
    }

    public static void makeTree(int index, Node cur) {
        if (index == K) { // index 가 K 가 되었다는 얘기는 다 넣었다라는 얘기임, 끝내줌
            return;
        }

        // 일단, index 값으로 현재 먹이 값을 뽑아낸다.
        String nowFeed = feed[index];
        Node next;

        if (cur.child.containsKey(nowFeed)) { // 만약에 이미 이 먹이가 있다면 이미 생성된 루트이니까, 이 child 가 가진 노드를 반환
            next = cur.child.get(nowFeed);
        } else { // 만약에 없다면? 노드를 만들어서 추가해준 다음에, 그것을 저장한다음에, 넘어간다.
            next = new Node(nowFeed);
            cur.child.put(nowFeed, next); // 다음 노드로 추가해준다음에, child 에다가 실질적으로 추가해준다.
        }

        makeTree(index + 1, next);
    }

    public static void printTree(Node cur, int depth) {
        Iterator it = cur.child.keySet().iterator();
        List<String> childList = new ArrayList<>();

        while (it.hasNext()) {
            childList.add((String) it.next());
        } // key 를 Map 에서 빼내서, list 에다가 담는다.

        Collections.sort(childList); // 정렬

        for (String string : childList) {
            // 순서대로, 정렬된 childList 를 순서대로 진행하면서, map 에서 Node 들로 이동한다
            // 근데 이동하기전에, -- 와, 현재 노드의 값을 출력한다.
            Node next = cur.child.get(string); // next를 얻는다.

            // 짜피 자식이 없으면 여기까지 실행되지도 않으니까, 자식이 없는 경우는 신경쓰지 않는다.
            for (int i = 0; i < depth; i++) {
                sb.append("--");
            }

            sb.append(next.value + "\n");
            printTree(next, depth + 1);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        N = fun.apply(br.readLine());
        String[] input;

        for (int i = 0; i < N; i++) {
            input = br.readLine().split(" ");
            K = fun.apply(input[0]);
            feed = new String[K];

            for (int j = 0; j < K; j++) {
                feed[j] = input[j + 1];
            }

            makeTree(0, root); // 만들어진 feed 배열을 가지고 tree 를 만듦
        }

        printTree(root, 0);

        System.out.println(sb);
    }
}
