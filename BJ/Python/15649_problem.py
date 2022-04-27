N , M = map(int, input().split())

def dfs(level , sep):
    if level == M:
        print(' '.join(list(map(str,sep))))
        return
    for i in range(1, N+1):
        if i not in sep:
            temp = sep.copy()
            temp.append(i)
            dfs(level + 1,temp)
dfs(0, [])