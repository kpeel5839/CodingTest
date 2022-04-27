import sys
size = int(sys.stdin.readline())
num = list(map(int,sys.stdin.readline().split()))
dp = [0 for _ in range(size)]
tmp = [0 for _ in range(size)]
max = 0
#지까지는 그냥 원래대로 근데 지 오른쪽 부터는 감소하는 걸로 그 대신 자기 보다 큰 놈은 그냥 dp 다 0 넣어버려
if size == 1:
    max = 1
elif size == 2:
    if num[1] == num[0]:
        max = 1
    else:
        max = 2
else:
    for i in range(size):
        max_dp = 0
        for j in range(i + 1):
            max_dp = 0
            if j == 0:
                tmp[j] = 1
            if j == 1:
                if num[j] > num[j - 1]:
                    tmp[j] = 2
                else:
                    tmp[j] = 1
            for c in range(j):
                if num[j] > num[c]:
                    if max_dp < tmp[c]:
                        max_dp = tmp[c]
            tmp[j] = max_dp + 1
        for j in range(i + 1,size):
            max_dp = 0
            if num[i] <= num[j]:
                tmp[j] = 0
                continue
            if j == i + 1:
                tmp[j] = 1
            if j == i + 2:
                if num[j] < num[j - 1]:
                    tmp[j] = 2
                else:
                    tmp[j] = 1
            for c in range(i + 1, j):
                if num[j] < num[c]:
                    if max_dp < tmp[c]:
                        max_dp = tmp[c]
            tmp[j] = max_dp + 1
        max_dp = 0
        for j in range(i + 1 ,size):
            if max_dp < tmp[j]:
                max_dp = tmp[j]
        dp[i] = tmp[i] + max_dp

for i in range(size):
    if max < dp[i]:
        max = dp[i]
sys.stdout.write(str(max) + '\n')
        
            
                