import sys
def dfs(visit,index):
    global sum
    global cloth_count_list
    add = 1
    if len(visit) > 1 and visit[len(visit) - 1] <= visit[len(visit) - 2]:
        return
    for i in visit:
        add *= cloth_count_list[index][i]
    if len(visit) >= 1:
        sum += add
    if len(visit) == len(cloth_count_list[index]):
        return
    for i in range(len(cloth_count_list[index])):
        if i not in visit:
            visit.append(i)
            dfs(visit,index)
            visit.pop()

size = int(sys.stdin.readline())
kind = []
cloth_count_list = [[] for _ in range(size)]
start = False
for i in range(size):
    kind = []
    cloth_count = int(sys.stdin.readline())
    for j in range(cloth_count):
        st = sys.stdin.readline()
        start = False
        kind_st = ''
        for c in range(len(st)):
            if st[c] == ' ':
                start = True
                continue
            if start == False:
                continue
            kind_st += st[c]
        if kind_st not in kind:
            kind.append(kind_st)
            cloth_count_list[i].append(1)
            continue
        for c in range(len(kind)):
            if kind_st == kind[c]:
                cloth_count_list[i][c] += 1
                break

for i in range(size):
    sum = 0
    dfs([], i)
    sys.stdout.write(str(sum) + '\n')
# for i in range(size):
#     m = 1
#     for j in cloth_count_list[i]:
#         m = m * (j + 1)
#     sys.stdout.write(str(m - 1) +'\n')    