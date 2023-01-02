import java.util.*;

class Main {
    public int height;
    public int width;
    public int[] dx = {0, 1, 0, -1};
    public int[] dy = {-1, 0, 1, 0};
    public int[] startPosition;
    public int[] destinationPosition;
    public int[][] globalGrid;
    public int[][][] dp;

    public boolean isOutOfRange(int y, int x) {
        return (y < 0 || y >= height) || (x < 0 || x >= width);
    }

    public int convertIndex(int y, int x) {
        return (y * width + x) + 1;
    }

    public int dfs(int bit, int y, int x) {
        if (bit != ((int) Math.pow(2, height * width) - 1) && (bit & (1 << convertIndex(destinationPosition[0], destinationPosition[1]))) != 0) {
            return 0;
        }

        if (bit == (Math.pow(2, height * width) - 1)) {
            return 1;
        }

        if (dp[bit][y][x] != -1) {
            return dp[bit][y][x];
        }

        dp[bit][y][x] = 0;
        for (int i = 0; i < 4; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (isOutOfRange(ny, nx) || globalGrid[ny][nx] == -1) {
                continue;
            }

            dp[bit][y][x] += dfs(bit | (1 << convertIndex(ny, nx)), ny, nx);
        }

        return dp[bit][y][x];
    }

    public int uniquePathsIII(int[][] grid) {
        height = grid.length;
        width = grid.length;
        dp = new int[(int) Math.pow(2, height * width)][height][width];
        globalGrid = grid;

        for (int i = 0; i < (int) Math.pow(2, height * width); i++) {
            for (int j = 0; j < height; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == 1) {
                    startPosition = new int[] {i, j};
                }

                if (grid[i][j] == 2) {
                    destinationPosition = new int[] {i, j};
                }
            }
        }

        return dfs(1 << convertIndex(startPosition[0], startPosition[1]), startPosition[0], startPosition[1]);
    }
}