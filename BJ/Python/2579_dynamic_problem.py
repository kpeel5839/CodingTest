#아애 올라가면서 그떄까지의 최대 값들을 찾는 것을 한번 시도해보자
#dp로 표현하는데 얘가 이전에 무엇을 골랐는지 어떤 것을 select를 했는지 기억하는 배열도 한번 만들어보자
import sys
size = int(sys.stdin.readline())
stair = [0 for _ in range(size + 1)]
for i in range(1, size + 1):
    stair[i] = int(sys.stdin.readline())
dp = [0 for _ in range(size + 1)]
if size == 0:
    sys.stdout.write(str(0))
elif size == 1:
    dp[1] = stair[1]
elif size == 2:
    dp[2] = stair[1] + stair[2]
elif size == 3:
    if stair[1] + stair[3] > stair[2] + stair[3]:
        dp[3] = stair[1] + stair[3]
    else:
        dp[3] = stair[2] + stair[3]
else:
    dp[1] = stair[1]
    dp[2] = stair[1] + stair[2]
    for i in range(3, size + 1):
        if dp[i - 3] + stair[i - 1] + stair[i] > dp[i - 2] + stair[i]: 
            #자신과 자신 이전의 것이 연속으로 오는 경우와 그리고 한칸 띄어서 온 경우를 비교 함 왜냐하면 일어나는 상황은 이 두가지 밖에 없으니까
            dp[i] = dp[i - 3] + stair[i - 1] + stair[i]
        else:
            dp[i] = dp[i - 2] + stair[i]
if size != 0:
    sys.stdout.write(str(dp[size]))