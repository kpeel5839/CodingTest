from copy import deepcopy
"""
일단 7로 남기자 , 시야 처리는
그리고 바로바로 맵 받으면서 1 , 2 , 3 , 4 , 5 를 받으면 cctv 위치를 기록해놓자.

그리고 dfs로 모든 조합을 만들어준다 (cctv 방향의 모든 경우)
그 다음 watch 함수를 만들어서 인자를 y, x , dir 넘겨주면 array에 표시해주는 함수를 만들어준다.
그리고 cctv number와 방향을 주면 watch 를 해당 cctv의 특징에 맞춰서 호출해주는 함수도 만들어준다.
"""

h , w = map(int , input().split())
dx = [0 , 1 , 0 , -1]
dy = [-1 , 0 , 1 , 0] # 위 , 오 , 아 , 왼
res = 1e10

array = [[0 for _ in range(h)] for _ in range(w)]

cctv = []

for i in range(h):
    array[i][0 : w] = map(int , input().split())
    for j in range(w):
        if array[i][j] in [1 , 2 , 3 , 4, 5]:
            cctv.append((i, j))

sequence = [0 for i in range(len(cctv))]
deepMap = deepcopy(array)

def dfs(depth):
    """
    sequence 배열에다가 방향들을 채워넣는다.
    그 다음에 depth == len(cctv) 즉 cctv 개수만큼의 sequence를 채웠으면 execute를 호출(시뮬레이션 시작)
    
    for문에다가는 그냥 depth = i 해주고 depth + 1 로 호출해주면 됨
    """
    if depth == len(cctv):
        execute()
        return
    for i in range(4):
        sequence[depth] = i
        dfs(depth + 1)

def watch(y , x , dir):
    """
    실제로 y , x , dir 이 주어지면 시야를 칠해줌 , 7로 칠할 것임
    deepMap 에다가 그냥 밖으로 나가거나 혹은 6을 만날때까지 칠하면 된다.
    근데 이제 6도 아니고 0도 아닌 것을 만나면 그대로 진행하되 7로 그려넣지는 않는다.
    
    첫 위치는 칠하지 않아도 됨(cctv니까)
    """
    global deepMap

    while True:
        y = y + dy[dir]
        x = x + dx[dir]
        if y < 0 or y >= h or x < 0 or x >= w or deepMap[y][x] == 6:
            break
        
        if deepMap[y][x] == 0:
            deepMap[y][x] = 7

    # print("next")
    # for i in range(h):
    #     print(deepMap[i])

def instruct(y , x , cctvNumber , dir):
    """
    cctvNumber 가 넘어오면 , 좌표와 , watch 에다가 실질적으로 지시하는 부분
    """
    if cctvNumber == 1:
        watch(y , x , dir)

    elif cctvNumber == 2:
        watch(y , x , dir)
        dir = (dir + 2) % 4
        watch(y , x , dir)

    elif cctvNumber == 3:
        watch(y , x , dir)
        dir = (dir + 1) % 4
        watch(y , x , dir)
    elif cctvNumber == 4:
        watch(y , x , dir)
        dir = (dir + 1) % 4
        watch(y , x , dir)
        dir = (dir + 1) % 4
        watch(y , x , dir)
    else:
        for i in range(4):
            watch(y , x , i)
def execute():
    """
    실제로 array를 deepCopy 해서 deepMap을 만들고
    그 다음에 sequence를 이용해서 해당 방향과 , cctv 위치와 , 번호를 묶어서 instruct에다가 던져주면 된다.
    """
    global res , deepMap

    deepMap = deepcopy(array)
    for i in range(len(sequence)):
        dir = sequence[i]
        y , x = cctv[i]
        cctvNumber = array[y][x]
        
        instruct(y , x , cctvNumber , dir)
    
    # print("next")
    # for i in range(h):
    #     print(deepMap[i])

    res = min(res , check())

def check():
    """
    하나하나씩 순회하면서 , deepMap 을 탐색해서 0의 개수를 센다.
    """
    global deepMap
    cnt = 0
    for i in range(h):
        for j in range(w):
            if deepMap[i][j] == 0:
                cnt += 1
    return cnt     

dfs(0)
print(res)