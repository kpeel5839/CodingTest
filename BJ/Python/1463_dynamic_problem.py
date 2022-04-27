import sys
number = int(sys.stdin.readline())
dp = [0 for _ in range(1000001)]
dp[2] = 1
dp[3] = 1
for i in range(4,number + 1):
    if i % 3 != 0 and i % 2 != 0: #다 안나눠지는 상황
        dp[i] = dp[i - 1] + 1
    elif i % 3 == 0 and i % 2 != 0 and dp[i // 3] <= dp[i - 1]: #3으로만 나누어지는 상황에서 3으로 나누는게 가장 작은 상황
        dp[i] = dp[i // 3] + 1
    elif i % 3 == 0 and i % 2 == 0 and dp[i // 3] <= dp[i // 2] and dp[i // 3] <= dp[i - 1]: #둘다 나눠지고 3으로 나누는게 가장 작은 상황
        dp[i] = dp[i // 3] + 1
    elif i % 3 != 0 and i % 2 == 0 and dp[i // 2] <= dp[i - 1]: #2로만 나누어지는 상황에서 2로 나누는게 더 작은 상황
        dp[i] = dp[i // 2] + 1
    elif i % 3 == 0 and i % 2 == 0 and dp[i // 2] <= dp[i // 3] and dp[i // 2] <= dp[i - 1]: #둘다 나눠지는 상황에서 2로 나누는게 가장 작은 상황
        dp[i] = dp[i // 2] + 1
    elif i % 3 == 0 and i % 2 == 0 and dp[i - 1] <= dp[i // 3] and dp[i - 1] <= dp[i // 2]:
        dp[i] = dp[i - 1] + 1
    elif i % 3 == 0 and i % 2 != 0 and dp[i - 1] <= dp[i // 3]:
        dp[i] = dp[i - 1] + 1
    elif i % 3 != 0 and i % 2 == 0 and dp[i - 1] <= dp[i // 2]:
        dp[i] = dp[i - 1] + 1
sys.stdout.write(str(dp[number]))