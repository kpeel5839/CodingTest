# 첫 번째는 개수 그 다음은 거기에 들어갈 숫자

def dfs(depth, sep , index):
    global num_list

    if len(sep) > 1 and sep[len(sep) - 1] < sep[len(sep) - 2]:
        return

    if depth == 6:
        print(' '.join(list(map(str, sep))))

    else:
        for i in num_list[index]:
            if i not in sep:
                sep.append(i)
                dfs(depth + 1, sep, index)
                sep.pop()
count = 0
num_list = [[]for _ in range(1000)]
while True:
    num_list[count] = list(map(int, input().split()))
    if num_list[count][0] == 0:
        break
    count += 1
for i in range(count):
    del num_list[i][0]

for i in range(count):
    dfs(0, [], i)
    print()