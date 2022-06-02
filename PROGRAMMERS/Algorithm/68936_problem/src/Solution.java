class Solution {
    /*
    그냥 굉장히 간단한 분할정복 문제이다.
    그냥 처음에 크기는 n 으로 잡아놓고
    4칸으로 분할 하면 되는데
    시작 y, x 와 n 을 정하면 된다.
    분할은 이런식으로 하면 된다. (n 무조건 나눠지는 형태이다.)
    (y, x), (y, x + n / 2), (y + n / 2, x), (y + n / 2, x + n / 2); 이런형태로 나누면 쉽게 할 수 있다.
    그리고, 이 범위를 주고, 그냥 맞는지 확인한다음에 맞으면 끝 데스네
    */
    static int[] answer; // 답을 담을 배열

    static boolean check(int[][] arr, int y, int x, int n) { // true == 모두 1 이거나 0, false == 다름
        int target = arr[y][x];

        for (int i = y; i < y + n; i++) { // y + n 까지 범위를 탐색
            for (int j = x; j < x + n; j++) { // x + n 까지의 범위를 탐색
                if (target != arr[i][j]) {
                    return false;
                }
            }
        }

        answer[target]++; // 여기서 맞으면 target 을 ++ 해준다.
        return true;
    }

    static void recursive(int[][] arr, int y, int x, int n) {
        if (check(arr, y, x, n)) { // 처음에는 전부를 체크, 생각해보니 이거 크기가 1이여도 처리해주네
            return;
        }

        // 만일 위에서 안걸렸어? 그러면 분할해버렷
        int arg = n / 2;
        recursive(arr, y, x, arg);
        recursive(arr, y, x + arg, arg);
        recursive(arr, y + arg, x, arg);
        recursive(arr, y + arg, x + arg, arg);
    }

    public int[] solution(int[][] arr) {
        answer = new int[2]; // 0 == 0, 1 == 1;
        recursive(arr, 0, 0, arr.length);

        return answer;
    }
}