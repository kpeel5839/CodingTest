import sys
size = int(sys.stdin.readline())
tmp_house = [sys.stdin.readline() for _ in range(size)]
house = [[0 for _ in range(size)] for _ in range(size)]
visited = [[0 for _ in range(size)] for _ in range(size)]
house_count = 0
for i in range(size):
    for j in range(size):
        house[i][j] = int(tmp_house[i][j])

def dfs(row , col):
    global house_count
    global visited
    
    visited[row][col] = 1
    house_count += 1
    
    if row != 0 and visited[row-1][col] == 0 and house[row - 1][col] == 1: #위쪽
        dfs(row-1,col)
    if col != 0 and visited[row][col - 1] == 0 and house[row][col - 1] == 1: #왼쪽
        dfs(row, col - 1)
    if row != size - 1 and visited[row + 1][col] == 0 and house[row + 1][col] == 1: #아래쪽
        dfs(row + 1, col)
    if col != size - 1 and visited[row][col + 1] == 0 and house[row][col + 1] == 1: #오른쪽
        dfs(row, col + 1)

apart_count = 0
result = []
for row in range(size):
    for col in range(size):
        if house[row][col] == 1 and visited[row][col] == 0:
            house_count = 0
            apart_count += 1
            dfs(row, col)
            result.append(house_count)
result.sort()
sys.stdout.write(str(apart_count) + '\n')
for i in range(len(result)):
    sys.stdout.write(str(result[i]) + '\n')
