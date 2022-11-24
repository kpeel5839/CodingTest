// 79 : Word Search
class Solution {
    int H;
    int W;
    String globalWord; // local to global
    boolean find = false;
    int[] dx = {0, 1, 0, -1};
    int[] dy = {-1, 0, 1, 0};
    char[][] globalBoard; // local to global
    boolean[][] visited;

    boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= H) || (x < 0 || x >= W)) {
            return true;
        }

        return false;
    }

    void dfs(int index, int y, int x) { // use globalBoard And Visited Array
        if (find) {
            return;
        }

        visited[y][x] = true; // 방문처리    
        if (index == globalWord.length()) {
            find = true;
            return;
        }

        char nowCharacterInWord = globalWord.charAt(index);

        for (int direction = 0; direction < 4; direction++) {
            int ny = y + dy[direction];
            int nx = x + dx[direction];
            if (outOfRange(ny, nx) || visited[ny][nx] || nowCharacterInWord != globalBoard[ny][nx]) {
                continue;
            }

            dfs(index + 1, ny, nx);
        }

        visited[y][x] = false;
    }

    public boolean exist(char[][] board, String word) {
        globalBoard = board;
        H = board.length;
        W = board[0].length;
        globalWord = word;
        visited = new boolean[H][W];
        char firstLetter = word.charAt(0);

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (board[i][j] == firstLetter) { // 같은 경우에만 실시                                
                    dfs(1, i, j);
                }
            }
        }

        return find;
    }
}