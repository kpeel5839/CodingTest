import sys
size = int(sys.stdin.readline())
line = [[0 for _ in range(2)] for _ in range(size)]
for i in range(size):
    line[i][0] , line[i][1] = map(int,sys.stdin.readline().split())

def mergesort(line,f_idx,l_idx):
    if f_idx == l_idx:
        return
    m_idx = (l_idx + f_idx) // 2
    mergesort(line,f_idx,m_idx)
    mergesort(line,m_idx + 1, l_idx)
    merge(line,f_idx,l_idx,m_idx)

def merge(line,f_idx,l_idx,m_idx):
    left_idx = f_idx
    right_idx = m_idx + 1
    tmp = [[0 for _ in range(2)] for _ in range(l_idx - f_idx + 1)]
    tmp_idx = 0
    while left_idx < m_idx + 1 and right_idx < l_idx + 1:
        if line[left_idx][0] > line[right_idx][0]:
            tmp[tmp_idx][0] = line[right_idx][0]
            tmp[tmp_idx][1] = line[right_idx][1]
            tmp_idx += 1
            right_idx += 1
        else:  
            tmp[tmp_idx][0] = line[left_idx][0]
            tmp[tmp_idx][1] = line[left_idx][1]
            tmp_idx += 1
            left_idx += 1
    while left_idx < m_idx + 1:
        tmp[tmp_idx][0] = line[left_idx][0]
        tmp[tmp_idx][1] = line[left_idx][1]
        tmp_idx += 1
        left_idx += 1
    while right_idx < l_idx + 1:
        tmp[tmp_idx][0] = line[right_idx][0]
        tmp[tmp_idx][1] = line[right_idx][1]
        tmp_idx += 1
        right_idx += 1
    tmp_idx = 0
    for i in range(f_idx, l_idx + 1):
        line[i][0] = tmp[tmp_idx][0]
        line[i][1] = tmp[tmp_idx][1]
        tmp_idx += 1

mergesort(line,0,size-1)
dp = [0 for _ in range(size)]
max = 0
if size == 1:
    max = 1
elif size == 2:
    if line[1][1] > line[0][1]:
        max = 2
    else:
        max = 1
else:
    dp[0] = 1
    if line[1][1] > line[0][1]:
        dp[1] = 2
    else:
        dp[1] = 1
    for i in range(2,size):
        max = 0
        for c in range(i):
            if line[i][1] > line[c][1]:
                if max < dp[c]:
                    max = dp[c]
        dp[i] = max + 1
if size > 2:
    max = 0
    for i in range(size):
        if max < dp[i]:
            max = dp[i]
sys.stdout.write(str(size - max))