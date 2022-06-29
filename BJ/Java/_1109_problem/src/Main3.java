import java.util.*;
import java.io.*;
import java.util.function.Function;

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