import java.util.*;

// 36 : Valid Sudoku

class Solution {
    Set<Character> duplicateChecker = new HashSet<>();

    boolean canFindDuplicateInSubBoxes(char[][] board, int startIndexOfRow, int startIndexOfCol) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (duplicateChecker.contains(board[startIndexOfRow + i][startIndexOfCol + j])) { // 찾음
                    return true;
                }

                if (board[startIndexOfRow + i][startIndexOfCol + j] != '.') {
                    duplicateChecker.add(board[startIndexOfRow + i][startIndexOfCol + j]);
                }
            }
        }

        return false;
    }

    public boolean isValidSudoku(char[][] board) {
        // 그냥 row, col, sub-boxes check
        for (int i = 0; i < board.length; i++) {
            // row check
            for (int j = 0; j < board.length; j++) {
                if (duplicateChecker.contains(board[i][j])) {
                    return false;
                }

                if (board[i][j] != '.') {
                    duplicateChecker.add(board[i][j]);
                }
            }
            duplicateChecker.clear();

            // col check
            for (int j = 0; j < board.length; j++) {
                if (duplicateChecker.contains(board[j][i])) {
                    return false;
                }

                if (board[j][i] != '.') {
                    duplicateChecker.add(board[j][i]);
                }
            }
            duplicateChecker.clear();
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (canFindDuplicateInSubBoxes(board, i * 3, j * 3)) {
                    return false;
                }

                duplicateChecker.clear();
            }
        }

        return true;
    }
}