import sys
st1 = sys.stdin.readline()
st2 = sys.stdin.readline()
width = len(st1)
height = len(st2)
dp = [[0 for _ in range(width)] for _ in range(height)]
def max(num1, num2):
    if num1 >= num2:
        return num1
    else:
        return num2
for row in range(1,height):
    for col in range(1,width):
        if st1[col - 1] == st2[row - 1]:
            dp[row][col] = dp[row - 1][col - 1] + 1
        else:
            dp[row][col] = max(dp[row - 1][col], dp[row][col - 1])
sys.stdout.write(str(dp[height - 1][width - 1]))