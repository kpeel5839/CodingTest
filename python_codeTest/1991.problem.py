#alphabet -65 gogo
size = int(input())
graph = [list(input().split()) for _ in range(size)]
for i in range(size):
    for j in range(3):
        if 65 <= ord(graph[i][j]) <= 90:
            graph[i][j] = ord(graph[i][j]) - 65
        else:
            graph[i][j] = -1 #이렇게 하고 찾아서 해야하는데 일단은 dfs로 간다고 생각
#다시 표현할 때에는 다시 65더해주고 chr로 바꿔서 레츠고
def dfs_low(root):
    root_row = 0
    for i in range(size):
        if graph[i][0] == root:
            root_row = i #0번째 인덱스 값이 root 와 동일하다면 ? 그 열이 자신의 열임
    left = graph[root_row][1]
    right = graph[root_row][2]

    print(chr(root + 65),end = "")
    if left != -1:
        dfs_low(left)
    if right != -1:
        dfs_low(right)

def dfs_middle(root):
    root_row = 0
    for i in range(size):
        if graph[i][0] == root:
            root_row = i #0번째 인덱스 값이 root 와 동일하다면 ? 그 열이 자신의 열임
    left = graph[root_row][1]
    right = graph[root_row][2]

    if left != -1:
        dfs_middle(left)
    print(chr(root + 65),end = "")
    if right != -1:
        dfs_middle(right)

def dfs_high(root):
    root_row = 0
    for i in range(size):
        if graph[i][0] == root:
            root_row = i #0번째 인덱스 값이 root 와 동일하다면 ? 그 열이 자신의 열임
    left = graph[root_row][1]
    right = graph[root_row][2]

    if left != -1:
        dfs_high(left)
    if right != -1:
        dfs_high(right)
    print(chr(root + 65),end = "")

dfs_low(0)
print()
dfs_middle(0)
print()
dfs_high(0)



