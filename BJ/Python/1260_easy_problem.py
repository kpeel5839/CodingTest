node_count , line_count , root_node = map(int, input().split())
graph = [[0 for _ in range(node_count)] for _ in range(node_count)]
for i in range(line_count):
    start , des = map(int, input().split())
    graph[start-1][des - 1] = 1
    graph[des - 1][start - 1] = 1

visited = []

def dfs(root_node):
    visited.append(root_node)
    print(root_node , end = " ")
    for i in range(node_count):
        if graph[root_node - 1][i] != 0 and (i + 1) not in visited:
            dfs(i+1)
            
def bfs(root_node):
    queue = []
    queue.append(root_node)
    visited = []

    while queue:
        node = queue[0]
        del queue[0]
        if node not in visited:
            visited.append(node)
            print(node, end = " ")
            for i in range(node_count):
                if graph[node - 1][i] != 0 and (i + 1) not in visited:
                    queue.append(i + 1)
dfs(root_node)
print(end = "\n")
bfs(root_node)