from collections import deque
from itertools import permutations
import copy

'''

1. dq에 값을 저장해놓는다. 순열을 이용하여 순서쌍을 넣어놓는다.
1~6까지 들어있는값들을

2. dq에 저장되어있는것들을 한번씩 하여 수행한다. 

3. 이때 matrix를 새로 만든다음에 cctv가 역할을 수행하였을때 남는 0의 개수를 센다
4. 0의 개수를 세서 반환을 한뒤에 앞으로 이 값을 비교하여 최소가 될때를 구한다. 

'''

n, m = map(int, input().split())
matrix,graph = [],[] # graph 가 맵인 것 같다.
arr = [] # cctv를 넣어놓는 배열 (arr 에다가는 cctv 위치를 저장함)

for i in range(n):
    temp = list(map(int , input().split()))
    for j in range(len(temp)):
        if temp[j] in [1, 2, 3, 4, 5]:
            arr.append((i, j)) # cctv 의 위치를 저장함
    graph.append(temp)

print("graph and arr")
print(arr)

for i in range(len(graph)):
    print(graph[i])

dx = [-1, 0, 1, 0]
dy = [0, 1, 0, -1] # 방향

# 1번이라고 가정하자

# def foward(x, y, dir):
#     dq = deque() # queue 선언
#     dq.append((x, y)) # queue에다가 x , y 를 저장
#     cnt = 0
#     while dq:
#         x, y = dq.popleft() # 처음 들어간 것을 빼는 연산
#         nx, ny = x + dx[dir], y + dy[dir]
#         if 0>nx or n<=nx or 0>ny or m<=ny:
#             continue
#         if matrix[nx][ny] == 0 and 0 <= nx < n and 0 <= ny < m:
#             dq.append((nx, ny))
#             cnt += 1
#     return cnt # 감시를 실질적으로 실행하는데 함수 , 몇칸을 감시했냐를 반환하는 듯

def express(x, y, dir):
    global matrix
    global n , m

    # dq = deque()
    # dq.append((x, y))
    # while dq:
    #     x, y = dq.popleft()
    #     nx, ny = x + dx[dir], y + dy[dir]
    #     print("y : " + str(y) + " x: " + str(x))
    #     print("nx : " + str(nx) + " ny : " + str(ny))
    #     if nx < 0 or nx >= n or ny < 0 or ny >= m or matrix[nx][ny] == 6: #범위를 벗어나면 나가리 , 혹은 벽을 만나면 나가리 데스네
    #         continue
    #         # break # 수정
    #     if matrix[nx][ny] == 0: # 0일 때에만 찍고
    #         matrix[nx][ny] = '#'
    #     dq.append((nx, ny)) # 다음 위치로 집어넣음
    #     # 이렇게 함으로써 가는길에 벽이 막고 있는 거 아니면 그대로 지나갈 수 있음
    
    while True:
        x = x + dx[dir]
        y = y + dy[dir]
        # print("x : " + str(x) + " y: " + str(y))
        # print("n : " + str(n) + " m: " + str(m))
        # print("matrix row size : " + str(len(matrix)) + " matrix col size : " + str(len(matrix[0])))
        if x < 0 or x >= n or y < 0 or y >= m or matrix[x][y] == 6:
            break
        if matrix[x][y] == 0:
            matrix[x][y] = '#'
        

def two(x,y,dir): # 위 , 오른쪽 , 아래 , 왼쪽 순서
    # 만일 dir == 2 라면 2와 반대인 위를 바라봐야 함
    express(x,y,dir) 
    dir=(dir + 2) % 4
    express(x,y,dir)

def three(x,y,dir):
    # 만일 dir == 2 라면 3을 바라봐야함
    express(x,y,dir)
    dir=(dir + 1) % 4
    express(x,y,dir)

def four(x,y,dir):
    # 만일 dir == 2 라면 3 과 0 을 바라봐야함
    express(x,y,dir)
    dir=(dir + 1) % 4
    express(x,y,dir)
    dir=(dir + 1) % 4
    express(x,y,dir)

def check():
    global matrix
    cnt = 0
    for i in range(n):
        for j in range(m):
            if matrix[i][j] == 0:
                cnt += 1
    return cnt

# matrix = copy.deepcopy(graph)

# express(0, 0 , 2)
# for i in range(len(matrix)):
#     print(matrix[i])

sequence = [0 for i in range(len(arr))]

def dfs(depth):
    if(depth == len(arr)):
        instruct()
        return
    for i in range(4):
        sequence[depth] = i
        dfs(depth + 1)

final_res=int(1e9)

def instruct(): # 여기까지는 좋음 , cctv들의 조합들이 존재함
    global sequence , final_res
    matrix = [[0 for i in range(n)] for j in range(m)]
    for i in range(n):
        for j in range(m):
            matrix[i][j] = graph[i][j]
    cctvIndex = 0
    for dir in sequence:
        i , j = arr[cctvIndex]
        no=matrix[i][j]
        print(no)
        if no==5:
            for k in range(4):
                express(i , j , k)
            continue
        elif no==1:
            express(i, j, dir)
        elif no==2:
            two(i , j , dir)
        elif no==3:
            three(i , j , dir)
        elif no==4:
            four(i , j , dir)

        print("next")
        for s in range(len(matrix)):
            print(matrix[s])
        cctvIndex += 1

    res = check()

    final_res=min(final_res,res)

dfs(0)
print(final_res)

