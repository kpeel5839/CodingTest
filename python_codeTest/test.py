
from collections import deque
from itertools import permutations
import copy

n, m = map(int, input().split())
matrix,graph = [],[]
arr = []

for i in range(n):
    temp = list(map(int, input().split()))
    for j in range(len(temp)):
        if temp[j] in [1,2,3,4,5]:
            arr.append((i, j))
    graph.append(temp)


dx = [-1, 0, 1, 0]
dy = [0, 1, 0, -1]

# 1번이라고 가정하자

def foward(x, y, dir):
    dq = deque()
    dq.append((x, y))
    cnt = 0
    while dq:
        x, y = dq.popleft()
        nx, ny = x + dx[dir], y + dy[dir]
        if 0>nx or n<=nx or 0>ny or m<=ny:
            continue
        if matrix[nx][ny] == 0 and 0 <= nx < n and 0 <= ny < m:
            dq.append((nx, ny))
            cnt += 1
    return cnt

def express(x, y, dir):
    dq = deque()
    dq.append((x, y))
    cnt = 0
    while dq:
        x, y = dq.popleft()
        nx, ny = x + dx[dir], y + dy[dir]
        if 0>nx or n<=nx or 0>ny or m<=ny:
            continue
        if matrix[nx][ny] == 0 and 0 <= nx < n and 0 <= ny < m:
            matrix[nx][ny]="#"
            dq.append((nx, ny))

def two(x,y,dir):
    cnt=0
    cnt+=foward(x,y,dir)
    dir=(dir + 2) % 4
    cnt+=foward(x,y,dir)
    return cnt

def three(x,y,dir):
    cnt=0
    cnt+=foward(x,y,dir)
    dir=(dir + 1) % 4
    cnt+=foward(x,y,dir)

def four(x,y,dir):
    cnt=0
    cnt+=foward(x,y,dir)
    dir=(dir + 1) % 4
    cnt+=foward(x,y,dir)
    dir=(dir + 1) % 4
    cnt+=foward(x,y,dir)

n = len(arr)
final_res=int(1e9)
for c in permutations(arr, n):
    matrix=copy.deepcopy(graph)
    dq=deque(c)
    while dq:
        i,j=dq.popleft()
        if matrix[i][j] not in [1,2,3,4,5]:
            continue
        x,y,max_dir=0,0,0
        cnt,res=0,0
        no=matrix[i][j]
        if no==5:
            for k in range(4):
                express(i,j,k)
            continue
        for k in range(4):
            if no==1:
                cnt = foward(i, j, k)
                if res < cnt:
                    res = cnt
                    x, y, max_dir = i, j, k
            elif no==2:
                cnt = two(i, j, k)
                if res < cnt:
                    res = cnt
                    x, y, max_dir = i, j, k
            elif no==3:
                cnt = three(i, j, k)
                if res < cnt:
                    res = cnt
                    x, y, max_dir = i, j, k
        if no==1:
            express(x,y,max_dir)
        elif no==2:
            express(x,y,max_dir)
            max_dir = (max_dir + 2) % 4
            express(x,y,max_dir)
        elif no==3:
            express(x,y,max_dir)
            max_dir = (max_dir + 1) % 4
            express(x,y,max_dir)
        elif no==4:
            express(x,y,max_dir)
            max_dir = (max_dir + 1) % 4
            express(x,y,max_dir)
            max_dir = (max_dir + 1) % 4
            express(x,y,max_dir)

        for s in matrix:
            print(s)

    res=0
    for i in range(n):
        for j in  range(m):
            if matrix[i][j]==0:
                res+=1
    final_res=min(final_res,res)

print(final_res)

