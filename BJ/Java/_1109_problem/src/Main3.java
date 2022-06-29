import java.util.*;
import java.io.*;
import java.util.function.Function;

/**
 * -- 해결한 점
 * 드디어 장정 한 어제 3시 부터 계속 고민했으니까
 * 거의 하루만에 푼 문제이다.
 *
 * 일단 내가 처음 생각했던 방법은
 * 어쩔 수 없이 어떤 섬 내부에 어떤 섬이 포함되어 있다면
 * 그 섬은 무조건 본인이 포함된 섬을 많이 만날 수 밖에 없다.
 *
 * 나는 솔직히 이 가정자체가 왜 틀린건지 진짜 예외 케이스를 보지 않는 이상 알지 못할 것 같다.
 * 왜냐하면 4 * 4 공간이 내부에 존재하는 섬이라고 해도
 * 즉, 안이 텅텅 비어있는 외곽만 존재하는 섬일 때에도
 * 무조건적으로 안에 있는 놈이 만나게 되는 해당 섬의 일부는 16개이다.
 *
 * 즉, 어떤 경우에도 내가 생각했을 때에는 이에 반하는 경우가 없는데
 * 근데 물론 내가 틀렸다라는 것은 분명히 예외가 존재한다라는 것이고, 분명히 실존한다라는 것이다.
 *
 * 그래서 더 짜증난다.
 *
 * 다른 사람들의 답을 볼려고 했는데 이 문제는 푼 사람도 별로 없어서
 * 어딜보고 배낄 수도 없다.
 *
 * 그래서 질문 게시판에서 가져와서 풀어봤다.
 *
 * 그래서 어느정도 수정을 거친 결과 답을 맞출 수 있었다.
 *
 * 해당 방법의 핵심은 이러함
 *
 * 1. 주변에 바다를 하나 더 두른다. (탈옥 문제와 굉장히 흡사한 방법)
 * 2. 섬번호를 매긴다 (이것까지는 똑같음)
 * 3. 0, 0 에서 시작해서 만나는 섬들을 HashMap<Integer, List<Point>> 로 잇는다.
 * 이 부분이 이해가 가지 않았었다.
 * 그래서 복사한다음 이 코드를 계속 수정하다가 완벽히 이해하게 되었다.
 * 즉, 가장 바깥에 있는 바다와 연결된 애들을 잇는 것이다.
 *
 * 11 10
 * xxxxxxxxxx
 * x........x
 * x.xxxxxx.x
 * x.x....x.x
 * x.x.xx.x.x
 * x.x....x.x
 * x.xxxxxx.x
 * x........x
 * x.x.xx.x.x
 * x........x
 * xxxxxxxxxx
 *
 * 해당 예시의 경우 외곽에 바다를 추가하게 되면 이러하다.
 *
 * ............
 * .xxxxxxxxxx.
 * .x........x.
 * .x.xxxxxx.x.
 * .x.x....x.x.
 * .x.x.xx.x.x.
 * .x.x....x.x.
 * .x.xxxxxx.x.
 * .x........x.
 * .x.x.xx.x.x.
 * .x........x.
 * .xxxxxxxxxx.
 * ............
 *
 * 그래서 이 상태에서 0, 0 부터 돌리는 것이다.
 * 그러면 가장 외곽에 있는놈이 node.put(0, 섬 번호 (여기에서는 1))
 * 될 것이다.
 *
 * 이제 그러면 또 본인과 연결되었던 애들에서 재귀적으로 다시 진행한다.
 *
 * 그러면 1번에서 시작하니까
 * node.put(1, 연결된 섬번호 (여기에서는 2, 4, 5, 6))
 * 번들이 또 연결이 된다.
 *
 * 그러고서 또 다시 거기에서 시작해주는 것이다.
 *
 * 이것을 가능케 하는 것은 일단 이 탐색을 할 때, 시작 위치와 섬 번호를 준다.
 * 그래서 탐색하는 곳이 x 인데 섬 번호가 본인 섬번호랑 같으면? 당연히 그냥 탐색하면 된다.
 *
 * 그냥 근데 솔직히 여기서 섬에 방문할 때 그냥 visited 처리하고 no 와 같으면 list 에다가 안담고
 * 같지 않으면 list 에다가 추가하면 될 것 같다.
 *
 * 쨌든 이런 식으로 본인 섬과 연결되어 있는
 * 즉, 바다를 두고 연결되어 있는 놈들을 계속해서 재귀적으로 실행하고
 *
 * nodes.get(no).add(i) 를 하면서 내가 지금 해당 no 로 돌았을 때, 연결되었었던 섬들과 연결을 시켜주고
 * getHeight 라는 함수에서
 * 재귀적으로 높이를 찾아주면 된다.
 *
 * 본인과 연결된 섬들을 끝까지 찾아가면 가장 안쪽의 섬이 나온다.
 * 그러면 걔는 height == 0 이다.
 *
 * 그리고, ret + 1 을 반환하면 된다.
 * 그러면 이전에 그 놈을 호출한 애는 ret + 1 을 받게 되고 또 다른놈을 호출해서 걔는 더 깊게 갔으면 height 가 망가지게 되니까
 * Math.max(ret, dfs(next)) 를 호출하면 된다.
 * 질문한 사람은 이것을 height[node] 로 했음 ret 으로 해야 하는데 그래서 틀렸었음
 *
 * 그래서 max 를 찾고
 * 그냥 탐색이 끝나고 height[ret]++ 를 해 최종적인 높이의 개수를 세주는 작업을 진행한다음에
 *
 * for (int i = 0; i <= max; i++) 까지 순서대로 출력해주면 정답을 받을 수 있다.
 *
 * 그리고 findSea 를 재귀적으로 호출할 때
 * !visited[p.y][p.x] 로 조건을 주고 시작하는데
 * 이게 가능한 이유는 얘가 만일 지 내부가 있는
 * 즉, 지가 포함하고 있는 애가 있는 놈이면
 *
 * 짜피 이전 놈이 지 주변 다 visited 로 방문처리 해놓고 갔어도 거기까지는 못 건드렸으니까
 * 더 깊게 탐색이 가능하다.
 *
 * 그리고 이렇게 하지 않으면 오히려 결과가 안나온다.
 *
 * 그래서 이런 이유로 그 지점까지 visited 를 처리하면 안되는 것이였다.
 * 즉, 같은 섬 번호는 방문처리를 하면 if (!visited[p.y][p.x]) 에서 거를 수 있고
 * 만일 다른 섬 번호까지 list 에다가 담으면서 방문처리해버리면, if (!visited[p.y][p.x]) 를 하지 못한다.
 * 왜냐? 이미 다른 섬이 방문처리해버렸으니까, 그래서 아얘 이거를 안하는 방법이 있는데, 이러면 해결이 되지만 시간 측면으로 좋지 않은 선택이다.
 *
 * 그렇기 때문에 이건 굉장히 잘 짠 코드라는 생각이 든다.
 *
 * 그래서 다시 최종적으로 정리를 이어가자면
 * 1. 주변에 바다를 하나 더 두른다. (탈옥 문제와 굉장히 흡사한 방법)
 * 2. 섬번호를 매긴다 (이것까지는 똑같음)
 * 3. 0, 0 에서 시작해서 만나는 섬들을 HashMap<Integer, List<Point>> 로 잇는다. (재귀적으로, 더 깊게 들어간다)
 * 4. 3번 과정에서 모든 섬들이 본인 내부에 있는 섬과와만 연결이 될 수 있도록, 섬의 바깥 부분은 다 visited 처리가 될 수 있도록 다른 섬이면 방문처리 해주지 않고, 그렇지 않으면 다 방문 처리한다.
 * 5. getHeight 함수로 해당 섬과 연결된 섬 모두를 (0번은 섬이 아니라 바다이다. 그러니 가장 외곽에 있는 섬들이 이때 호출된다 (node 0 에)) 재귀적으로 가장 깊은 섬까지 들어가서 ret + 1 을 순차적으로 반환하고 ret = Math.max(ret, dfs(next)) 를 해준다.
 * 6. 최종적으로 결정된 height 들 즉, ret 들을 가지고 height[ret]++ 를 해준다. (그러면서 ret 중 max 를 찾아냄)
 * 7. for (int i = 0; i <= max; i++) sb.append(height[ret] + " "); 를 통해서 결과를 출력해준다. (만일 섬이 하나도 없는 경우, islands.size() == 0 인 경우는 -1을 출력해준다.)
 */
public class Main3 {
    static int H;
    static int W;
    static int max = 0;
    static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
    static int[] heights;
    static char[][] map;
    static int[][] island;
    static boolean[][] visited;
    static Map<Integer, List<Integer>> nodes = new HashMap<>();

    static class Point {
        int y;
        int x;
        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static boolean outOfRange(int y, int x) {
        if (y < 0 || y >= H || x < 0 || x >= W) {
            return true;
        } else {
            return false;
        }
    }

    static void setIsland(Point start, int no) {
        Queue<Point> q = new LinkedList<>();
        q.add(start);
        island[start.y][start.x] = no;

        while (!q.isEmpty()) {
            Point now = q.poll();
            for (int i = 0; i < 8; i++) {
                int ny = now.y + dy[i];
                int nx = now.x + dx[i];

                if (outOfRange(ny, nx) || map[ny][nx] == '.' || island[ny][nx] != 0) { // island 로 방문처리
                    continue;
                }

                island[ny][nx] = no;
                q.add(new Point(ny, nx));
            }
        }
    }

    static void findSea(Point start, int no) {
        Queue<Point> q = new LinkedList<>();
        Map<Integer, List<Point>> islands = new HashMap<>(); // 현재 0 번 섬에서 어디 섬을 방문하는지 확인
        q.add(start);
        visited[start.y][start.x] = true; // 방문처리

        while (!q.isEmpty()) {
            Point now = q.poll();
            for(int i = 0; i < 8; i += 2) {
                int ny = now.y + dy[i];
                int nx = now.x + dx[i];

                if (outOfRange(ny, nx) || visited[ny][nx]) {
                    continue;
                }

                if (map[ny][nx] == '.') { // 바다라면 Keep going
                    visited[ny][nx] = true;
                    q.add(new Point(ny, nx));
                    continue;
                }

                if (island[ny][nx] == no) { // 현재 도는 거랑 같은 섬이면 방문을 이어감
                    visited[ny][nx] = true;
                    q.add(new Point(ny, nx));
                } else { // 다른 섬이면 다음에 방문할 노드로 추가, 방문처리는 아직 안함 왜냐하면 재귀적으로 다시 실행할 때 방문처리해서
                    List<Point> list = (islands.containsKey(island[ny][nx])) ? islands.get(island[ny][nx]) : new ArrayList<>(); // 그 섬의 list 를 얻음
                    list.add(new Point(ny, nx)); // 그 섬에 point 를 추가
                    islands.put(island[ny][nx], list); // 다시 넣음
                }
            }
        }

        for (Integer i : islands.keySet()) { // 처음에는 0 번과 연결된 애들을 다 호출해서, nodes 0 번에다가 다 집어넣음 (해당 섬의 번호를)
            nodes.get(no).add(i);
            for (Point p : islands.get(i)) {
                if(!visited[p.y][p.x]) {
                    findSea(p, i); // 다시 i 번째 섬에서 시작해서, i 번과 연결된 섬을 찾음
                }
            }
        }
    }

    static int getHeight(int node) {
        int ret = 0;

        for (Integer next : nodes.get(node)) {
            ret = Math.max(ret, getHeight(next));
        }

        if (node != 0) {
            heights[ret]++;
            max = Math.max(max, ret);
        }

        return ret + 1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        H = fun.apply(input[0]) + 2;
        W = fun.apply(input[1]) + 2;
        map = new char[H][W];
        island = new int[H][W];
        visited = new boolean[H][W];
        List<Point> islands = new ArrayList<>();

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                map[i][j] = '.';
            }
        }

        for (int i = 1; i < H - 1; i++) {
            String string = br.readLine();

            for (int j = 1; j < W - 1; j++) {
                map[i][j] = string.charAt(j - 1);
                if (map[i][j] == 'x') {
                    islands.add(new Point(i, j));
                }
            }
        }

        if (islands.size() == 0) {
            sb.append(-1);
        } else {
            // 1. Set Island No
            int islandNo = 0;
            for (Point p : islands) {
                if (island[p.y][p.x] == 0) { // island 번호가 아직 안매겨져 있으면
                    setIsland(p, ++islandNo);
                }
            } // island 번호 다 매김

            for (int i = 0; i <= islandNo; i++) { // island
                nodes.put(i, new ArrayList<>());
            } // 0 번 부터 island number 까지 array list setting

            // 2. Sea
            findSea(new Point(0, 0), 0); // sea 탐색
            heights = new int[islandNo + 1];

            // 3. Get Height
            for (Integer node : nodes.get(0)) { // 0 번에서 시작해서 탐색
                getHeight(node);
            }

            for (int i = 0; i <= max; i++) {
                sb.append(heights[i] + " ");
            }
        }

        System.out.println(sb);
    }
}