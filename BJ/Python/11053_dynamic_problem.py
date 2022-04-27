import sys
size = int(sys.stdin.readline())
num_list = list(map(int, sys.stdin.readline().split()))
dp = [0 for _ in range(size)]
max = 0
if size == 1:
    max = 1
elif size == 2:
    if num_list[1] > num_list[0]:
        max = 2
    else:
        max = 1
else:
    dp[0] = 1
    if num_list[1] > num_list[0]:
        dp[1] = 2
    else:
        dp[1] = 1
    for i in range(2,size):
        max_dp = 0
        for j in range(i - 1,-1,-1):
            if num_list[i] > num_list[j]:
                if max_dp < dp[j]:
                    max_dp = dp[j]
        dp[i] = max_dp + 1
if size > 2:
    for i in range(size):
        if max < dp[i]:
            max = dp[i]
for i in range(size):
    sys.stdout.write(str(dp[i]) + " ")
sys.stdout.write('\n')
sys.stdout.write(str(max))
    