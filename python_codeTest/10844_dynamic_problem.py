import sys
number = int(sys.stdin.readline())
dp = [[0 for i in range(10)] for _ in range(number)]
for i in range(0, 10):
    dp[0][i] = 1
if number > 1:
    for i in range(1, number):
        for j in range(10):
            if j == 0:
                dp[i][j] = dp[i - 1][j + 1]
                continue
            if j == 9:
                dp[i][j] = dp[i-1][j-1]
                continue
            dp[i][j] = dp[i-1][j-1] + dp[i-1][j+1]
result = 0
for i in range(1,10):
    result += dp[number - 1][i]
result = result % 1000000000
sys.stdout.write(str(result))