import sys
size = int(sys.stdin.readline())
result_list = [-1 for _ in range(size)]
num_list = list(map(int,sys.stdin.readline().split()))
stack_value = []
stack_index = []
for i in range(size):
    if i == size - 1:
        result_list[i] = -1
        break
    if i != size - 1 and num_list[i] < num_list[i+1]:
        result_list[i] = num_list[i+1]
        if len(stack_value) > 0:
            for j in range(len(stack_value) - 1 ,-1,-1):
                if stack_value[j] < num_list[i+1]:
                    result_list[stack_index[j]] = num_list[i+1]
                    del stack_value[j]
                    del stack_index[j]
                else:
                    break
        continue
    if i != size - 1 and num_list[i] >= num_list[i+1]:
        stack_value.append(num_list[i])
        stack_index.append(i)
        continue
for i in range(size):
    sys.stdout.write(str(result_list[i]) + ' ')