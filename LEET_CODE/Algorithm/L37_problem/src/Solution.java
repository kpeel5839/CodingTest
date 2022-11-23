import java.util.*;

// 37 : Sudoku Solver

class Solution {
    int N = 9;
    boolean findResult = false;
    List<int[]> emptyCellsPosition = new LinkedList<>();
    char[][] globalBoard;
    char[][] resultBoard;

    boolean[] findCanUseNumbers(int row, int col) {
        // 여기서는 현재 인덱스에 들어갈 수 있는 숫자들을 확인해서 넘겨준다.
        boolean[] canUseNumbers = new boolean[10];
        Arrays.fill(canUseNumbers, true);

        for (int i = 0; i < N; i++) {
            // row
            if (globalBoard[row][i] != '.') {
                canUseNumbers[globalBoard[row][i] - '0'] = false;
            }

            // col
            if (globalBoard[i][col] != '.') {
                canUseNumbers[globalBoard[i][col] - '0'] = false;
            }
        }

        int subBoxesStartRow = (row / 3) * 3;
        int subBoxesStartCol = (col / 3) * 3;

        for (int i = subBoxesStartRow; i < subBoxesStartRow + 3; i++) {
            for (int j = subBoxesStartCol; j < subBoxesStartCol + 3; j++) {
                if (globalBoard[i][j] != '.') {
                    canUseNumbers[globalBoard[i][j] - '0'] = false;
                }
            }
        }

        return canUseNumbers;
    }

    void dfs(int nowIndex) {
        if (findResult) { // 이미 답을 찾은 경우 더 이상 답을 구하지 않는다.
            return;
        }

        if (nowIndex == emptyCellsPosition.size()) {
            resultBoard = new char[N][N];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    resultBoard[i][j] = globalBoard[i][j];
                }
            }

            findResult = true;
            return;
        }

        int row = emptyCellsPosition.get(nowIndex)[0];
        int col = emptyCellsPosition.get(nowIndex)[1];
        boolean[] canUseNumbers = findCanUseNumbers(row, col);

        for (int i = 1; i <= N; i++) {
            if (canUseNumbers[i]) { // 사용 가능한 것들만
                globalBoard[row][col] = (char) (i + (int) '0');
                dfs(nowIndex + 1);
                if (!findResult) {
                    globalBoard[row][col] = '.'; // 원상 복구
                }
            }
        }
    }

    public void solveSudoku(char[][] board) {
        globalBoard = new char[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                globalBoard[i][j] = board[i][j];
                if (board[i][j] == '.') {
                    emptyCellsPosition.add(new int[] {i, j});
                }
            }
        }

        dfs(0);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = resultBoard[i][j];
            }
        }
    }
}
