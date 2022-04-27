import sys
def mergesort(stuff,f_idx,l_idx):
    if f_idx == l_idx:
        return
    m_idx = (l_idx + f_idx) // 2
    mergesort(stuff,f_idx, m_idx)
    mergesort(stuff,m_idx + 1, l_idx)
    merge(stuff, f_idx, l_idx, m_idx)
def merge(stuff,f_idx,l_idx,m_idx):
    tmp = [[0 for _ in range(2)] for _ in range(l_idx - f_idx + 1)]
    tmp_idx = 0
    left_idx = f_idx
    right_idx = m_idx + 1
    while left_idx < m_idx + 1 and right_idx < l_idx + 1:
        if stuff[left_idx][1] <= stuff[right_idx][1]:
            if stuff[left_idx][1] == stuff[right_idx][1]:
                if stuff[left_idx][0] > stuff[right_idx][0]:
                    tmp[tmp_idx][0] = stuff[left_idx][0]
                    tmp[tmp_idx][1] = stuff[left_idx][1]
                    tmp_idx += 1
                    left_idx += 1
                else:
                    tmp[tmp_idx][0] = stuff[right_idx][0]
                    tmp[tmp_idx][1] = stuff[right_idx][1]
                    tmp_idx += 1
                    right_idx += 1
            else:
                tmp[tmp_idx][0] = stuff[left_idx][0]
                tmp[tmp_idx][1] = stuff[left_idx][1]
                tmp_idx += 1
                left_idx += 1
        else:
            tmp[tmp_idx][0] = stuff[right_idx][0]
            tmp[tmp_idx][1] = stuff[right_idx][1]
            tmp_idx += 1
            right_idx += 1
    while left_idx < m_idx + 1:
        tmp[tmp_idx][0] = stuff[left_idx][0]
        tmp[tmp_idx][1] = stuff[left_idx][1]
        tmp_idx += 1
        left_idx += 1
    while right_idx < l_idx + 1:
        tmp[tmp_idx][0] = stuff[right_idx][0]
        tmp[tmp_idx][1] = stuff[right_idx][1]
        tmp_idx += 1
        right_idx += 1
    tmp_idx = 0
    for i in range(f_idx , l_idx + 1):
        stuff[i][0] = tmp[tmp_idx][0]
        stuff[i][1] = tmp[tmp_idx][1]
        tmp_idx += 1

size , weight = map(int, sys.stdin.readline().split()) #weight is pivot
stuff = [[0 for _ in range(2)] for _ in range(size)]
for i in range(size): #stuff[i][0] = weight , stuff[i][1] = value
    stuff[i][0] , stuff[i][1] = map(int, sys.stdin.readline().split())
dp = [[0 for _ in range(2)] for _ in range(size)]
# mergesort(stuff,0,size - 1)
sys.stdout.write("sorted " + '\n')
for i in range(size):
    for j in range(2):
        sys.stdout.write(str(stuff[i][j])+' ')
    sys.stdout.write('\n')
if size == 1:
    if weight <= stuff[0][0]:
        max = stuff[0][1] 
    else:
        max = 0
else:
    if weight >= stuff[0][0]:
        dp[0][0] = stuff[0][0]
        dp[0][1] = stuff[0][1]
    else:
        dp[0][0] = 0
        dp[0][1] = 0
    for i in range(1,size):
        dp_max = 0
        dp_weight = 0
        if weight < stuff[i][0]:
            dp_default_weight = 0
            stuff_max = 0
            stuff_weight = 0
        else:
            dp_default_weight = stuff[i][0]
            stuff_max = stuff[i][1]
            stuff_weight = stuff[i][0]
        
        for j in range(i - 1, -1 , -1):
            if stuff_weight + stuff[j][0] <= weight:
                stuff_weight += stuff[j][0]
                stuff_max += stuff[j][1]
        for j in range(i - 1, -1, -1):
            if dp_default_weight + dp[j][0] <= weight:
                if dp_max < dp[j][1]:
                    dp_max = dp[j][1]
                    dp_weight = dp[j][0]
        sys.stdout.write("now index " + str(i) + '\n')
        sys.stdout.write("stuff weight : " + str(stuff_weight) + '\n')
        sys.stdout.write("stuff max : " + str(stuff_max) + '\n')
        sys.stdout.write("dp weight : " + str(dp_weight) + '\n')
        sys.stdout.write("dp max : " + str(dp_max) + '\n')
        if dp_max > stuff_max:
            sys.stdout.write("choose dp" + '\n')
            sys.stdout.write("dp[{0}][0] = {1} , dp[{0}][1] = {2} ".format(i,stuff[i][0] + dp_weight,stuff[i][1] + dp_max) + '\n')
            dp[i][0] = stuff[i][0] + dp_weight
            dp[i][1] = stuff[i][1] + dp_max
        else:
            sys.stdout.write("choose stuff" + '\n')
            sys.stdout.write("dp[{0}][0] = {1} , dp[{0}][1] = {2} ".format(i,stuff_weight,stuff_max) + '\n')
            dp[i][0] = stuff_weight
            dp[i][1] = stuff_max
if size > 1:
    max = 0
    for i in range(size):
        if max < dp[i][1]:
            max = dp[i][1]
sys.stdout.write("dp value" +'\n')
for i in range(size):
    for j in range(2):
        sys.stdout.write(str(dp[i][j]) + ' ')
    sys.stdout.write('\n')
sys.stdout.write(str(max))
                    