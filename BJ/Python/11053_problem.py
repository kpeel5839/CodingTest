import sys
size = int(sys.stdin.readline())
num_list = list(map(int, sys.stdin.readline().split()))
max = 0
result_list = []
def dfs(depth, visit):
    global max
    global num_list
    global result_list
    if len(visit) > 1 and visit[len(visit) - 1] < visit[len(visit) - 2]:
        return
    if depth >= size:
        value_list = []
        for i in visit:
            value_list.append(num_list[i])
        sys.stdout.write("value list: "+ str(value_list) +'\n')
        sys.stdout.write("visit list: " + str(visit) + '\n')
        sys.stdout.write("depth : " + str(depth) + '\n')
        count = len(visit)
        if max < count:
            max = count
            result_list = value_list.copy()
        return
    else:
        for i in range(size):
            if depth >= i + 1:
                continue
            if len(visit) == 0:
                visit.append(i)
                jump = i + 1 - depth
                dfs(depth + jump,visit)
                visit.pop()
                continue
            if num_list[i] > num_list[visit[len(visit) - 1]]:
                visit.append(i)
                jump = i + 1 - depth
                dfs(depth + jump,visit)
                visit.pop()
            elif i == size - 1 and num_list[i] <= num_list[visit[len(visit) - 1]]:
                dfs(depth + 2, visit)
dfs(0, [])
sys.stdout.write(str(result_list) + '\n')
sys.stdout.write(str(max) + '\n')