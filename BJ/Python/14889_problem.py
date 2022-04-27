def dfs(depth,sum,oper):
    global result
    global promising

    if depth == end_depth:
        oper.sort()

        if oper == promising:
            result.append(sum)
    else:
        tmp_promising = is_promising(oper)
        for operator in tmp_promising:
            tmp_oper = oper.copy()
            tmp_oper.append(operator)
            tmp_sum = sum
            if len(tmp_oper) == 1:
                if operator == '+':
                    tmp_sum = num_list[0] + num_list[1]
                elif operator == '*':
                    tmp_sum = num_list[0] * num_list[1]
                elif operator == '-':
                    tmp_sum = num_list[0] - num_list[1]
                elif operator == '/':
                    tmp_sum = num_list[0] // num_list[1]
            else:
                if operator == '+':
                    tmp_sum = tmp_sum + num_list[depth + 1]
                elif operator == '*':
                    tmp_sum = tmp_sum * num_list[depth + 1]
                elif operator == '-':
                    tmp_sum = tmp_sum - num_list[depth + 1]
                elif operator == '/':
                    if tmp_sum < 0:
                        tmp_sum = -1 * (abs(tmp_sum) // num_list[depth + 1])
                    tmp_sum = tmp_sum // num_list[depth + 1]
            dfs(depth + 1, tmp_sum , tmp_oper)

def is_promising(oper):
    global promising
    tmp_promising = promising.copy()

    for i in range(len(oper)):
        if oper[i] in tmp_promising:
            tmp_promising.remove(oper[i])
    return tmp_promising

size = int(input())
num_list = list(map(int, input().split()))
end_depth = size - 1
add, minus , product , division = map(int , input().split())
promising = (['+'] * add) + (['-'] * minus) + (['*'] * product) + (['/'] * division)
promising.sort()
max = -1000000001 
min = 1000000001 
result = []
dfs(0, 0,[])
for i in range(len(result)): 
    if max < result[i]: 
        max = result[i]
    if min > result[i]:
        min = result[i]
print(max)
print(min)