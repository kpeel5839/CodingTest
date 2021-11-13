from collections import deque
node_count , line_count , root_node = map(int, input().split())
graph = [[0 for _ in range(node_count)] for _ in range(node_count)]
vertex_list =[]
for i in range(line_count):
    start , des = map(int, input().split())
    graph[start-1][des - 1] = 1
    graph[des - 1][start - 1] = 1
    if start not in vertex_list:
        vertex_list.append(start)
    if des not in vertex_list:
        vertex_list.append(des)
    
vertex_list.sort()

visited = []

def dfs(root_node,vertex_list):
    visited.append(root_node)
    print(root_node , end = " ")
    for i in range(node_count):
        if graph[root_node - 1][i] != 0 and (i + 1) not in visited:
            dfs(i+1,vertex_list)
    for i in range(len(vertex_list)):
        if vertex_list[i] not in visited:
            dfs(vertex_list[i], vertex_list)
        
            
def bfs(root_node,vertex_list):
    queue = []
    queue.append(root_node)
    visited = []

    while queue or visited != vertex_list:
        if not queue:
            for i in range(len(vertex_list)):
                if vertex_list[i] not in visited:
                    queue.append(vertex_list[i])
                    break
        node = queue[0]
        del queue[0]
        if node not in visited:
            visited.append(node)
            print(node, end = " ")
            for i in range(node_count):
                if graph[node - 1][i] != 0 and (i + 1) not in visited:
                    queue.append(i + 1)
        visited.sort()
dfs(root_node,vertex_list)
print(end = "\n")
bfs(root_node,vertex_list)
    