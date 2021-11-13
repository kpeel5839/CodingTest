import sys
size = int(sys.stdin.readline())
drink = [int(sys.stdin.readline()) for _ in range(size)]
dp = [0 for _ in range(size + 1)]
max = 0
if size == 1:
    max = drink[0]
elif size == 2:
    max = drink[0] + drink[1]
else:
    dp[1] = drink[0]
    dp[2] = drink[0] + drink[1]
    for i in range(3, size + 1):
        first_max_dp = 0 #이게 두개 연속으로 오는 경우에서의 dp
        second_max_dp = 0 #이거는 그냥 dp
        for j in range(i - 2,0,-1):
            if j == i - 2:
                second_max_dp = dp[j]
                continue
            if first_max_dp < dp[j]:
                first_max_dp = dp[j]
            if second_max_dp < dp[j]:
                second_max_dp = dp[j]
        if first_max_dp + drink[i - 2] + drink[i - 1] > second_max_dp + drink[i - 1]:
            dp[i] = first_max_dp + drink[i - 2] + drink[i - 1]
        else:
            dp[i] = second_max_dp + drink[i - 1]
if size >= 3:
    for i in range(size - 3, size + 1):
        if max < dp[i]:
            max = dp[i]
sys.stdout.write(str(max))