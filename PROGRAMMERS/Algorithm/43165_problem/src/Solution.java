class Solution {
    /**
     그냥 굉장히 단순한 bfs 문제임
     */
    static int count;

    static void dfs(int depth, int sum, int target, int[] numbers) { // 여기서 하나하나씩 숫자를 더하거나 빼며 진행할 것임 target Number 와 동일한지 확인해줌
        if (depth == numbers.length) {
            if (sum == target) { // target 과 같은 경우는 count 를 ++ 해준다.
                count++;
            }

            return;
        }

        dfs(depth + 1, sum + numbers[depth], target, numbers);
        dfs(depth + 1, sum - numbers[depth], target, numbers);
    }

    public int solution(int[] numbers, int target) {
        count = 0;
        dfs(0, 0, target, numbers);

        return count;
    }
}