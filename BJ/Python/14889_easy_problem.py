size = int(input())
num_list = list(map(int, input().split()))
oper = list(map(int, input().split()))
min = 1000000001
max = -1000000001

def dfs(result, depth):
    global min
    global max

    if depth == size - 1:
        if result > max:
            max = result
        if result < min:
            min = result
    
    else:
        for i in range(4):
            if oper[i] != 0:
                oper[i] = int(oper[i]) - 1
                if i == 0:
                    dfs(result + num_list[depth + 1] , depth + 1)
                elif i == 1:
                    dfs(result - num_list[depth + 1] , depth + 1)
                elif i == 2:
                    dfs(result * num_list[depth + 1] , depth + 1)
                elif i == 3:
                    if result < 0:
                        dfs(-1 * (abs(result) // num_list[depth + 1]),depth + 1)
                    else:
                        dfs(result // num_list[depth + 1] , depth + 1)
                oper[i] = oper[i] + 1

dfs(num_list[0], 0)
print(max)
print(min)