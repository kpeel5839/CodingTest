import sys
size = int(sys.stdin.readline())
num_List = list(map(int, sys.stdin.readline().split()))
dp = [0 for _ in range(size)]
max = 0
if size == 1:
    max = num_List[0]
elif size == 2:
    dp[0] = num_List[0]
    if num_List[0] < 0:
        dp[1] = num_List[1]
    else:
        dp[1] = num_List[0] + num_List[1]
else:
    dp[0] = num_List[0]
    if num_List[0] < 0:
        dp[1] = num_List[1]
    else:
        dp[1] = num_List[0] + num_List[1]
    for i in range(2, size):
        if dp[i - 1] < 0:
            dp[i] = num_List[i]
        else:
            dp[i] = num_List[i] + dp[i - 1]
if size > 1:
    max = -100000001
    for i in range(size):
        if max < dp[i]:
            max = dp[i]
sys.stdout.write(str(max))
    