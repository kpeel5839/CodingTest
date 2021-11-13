import sys
size = int(sys.stdin.readline())
drink = [int(sys.stdin.readline()) for _ in range(size)]
max = 0
result_list = []

#visit 에는 index 값을 넣자.
def dfs(depth ,visit):
    global max
    global size
    global result_list

    if len(visit) > 1 and visit[len(visit) - 1] <= visit[len(visit) - 2]:
        return
    if len(visit) > 2 and visit[len(visit) - 1] - visit[len(visit) - 2] == 1 and visit[len(visit) - 2] - visit[len(visit) - 3] == 1:
        return
    if depth >= size:
        value_list = []
        score = 0
        for i in visit:
            value_list.append(drink[i])
            score += drink[i]
        # sys.stdout.write("visit list :" + str(value_list) + '\n')
        if max < score:
            max = score
            result_list = value_list.copy()
        return
    else:
        for i in range(size):
            if i == size - 1 and len(visit) >= 2 and i - visit[len(visit)-1] == 1 and visit[len(visit) - 1] - visit[len(visit) - 2] == 1:
                dfs(depth + 2, visit)
                break
            visit.append(i)
            jump = i + 1 - depth
            dfs(depth + jump, visit)
            visit.pop()
        
dfs(0,[])
sys.stdout.write(str(result_list) + '\n')
sys.stdout.write(str(max))