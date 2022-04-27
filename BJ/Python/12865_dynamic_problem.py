import sys
size , pivot_weight = map(int, sys.stdin.readline().split())
stuff = [[0 for _ in range(2)] for _ in range(size)]
dp = [[0 for _ in range(pivot_weight + 1)] for _ in range(size)]
for i in range(size):
    stuff[i][0],stuff[i][1] = map(int,sys.stdin.readline().split())
def max(num1, num2):
    if num1 >= num2:
        return num1
    else:
        return num2
for i in range(size):
    for j in range(1,pivot_weight + 1):
        if i == 0 and j >= stuff[i][0]:
            dp[i][j] = stuff[i][1]
            continue
        elif i == 0 and j < stuff[i][0]:
            dp[i][j] = 0
            continue
        if stuff[i][0] <= j:
            dp[i][j] = max(stuff[i][1] + dp[i - 1][j - stuff[i][0]], dp[i-1][j])
        else:
            dp[i][j] = dp[i-1][j]
sys.stdout.write(str(dp[size - 1][pivot_weight]))