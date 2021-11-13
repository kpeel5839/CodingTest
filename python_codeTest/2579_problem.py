import sys
size = int(sys.stdin.readline())
stair = [int(sys.stdin.readline()) for _ in range(size)]
max = 0
max_list = [] 
def dfs(result,visit,index):
    sys.stdout.write("function({0},{1})" .format(result,index) + '\n')
    global max
    global stair
    global max_list
    # if len(visit) >= 3 and (int(visit[len(visit) - 1]) - int(visit[len(visit) - 2])) == 1 and (int(visit[len(visit) - 2]) - int(visit[len(visit) - 3])) == 1:
    #     return
    if len(visit) > 0 and visit[len(visit) - 1] == len(stair) - 1:
        # sys.stdout.write("visit list : " + str(visit) + '\n')
        # sys.stdout.write("result : " + str(result) + '\n')
        if max < result:
            max = result
            max_list = visit.copy()
            
    elif len(visit) > 0 and visit[len(visit) - 1] > len(stair) - 1:
        return

    else:
        for i in range(index + 1, index + 3):
            if i < len(stair):
                if len(visit) > 1 and (i - visit[len(visit) - 1] == 1 and visit[len(visit) - 1] - visit[len(visit) - 2] == 1):
                    continue
                visit.append(int(i))
                dfs(result + stair[i], visit, i)
                visit.pop()
dfs(0,[],-1)
sys.stdout.write("max_list : " + str(max_list) + '\n')
sys.stdout.write("max_list_result : " + str(max))
