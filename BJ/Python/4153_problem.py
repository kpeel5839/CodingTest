import math

try_list = []
index = 0
while True:
    x , y , z = map(int,input().split())
    if x == 0 and y == 0 and z == 0:
        index -= 1
        break
    try_list.append([])
    try_list[index].append(x)
    try_list[index].append(y)
    try_list[index].append(z)
    index += 1
for i in range(index+1):
    sum = 0
    max = 0
    for j in range(3):
        if max < try_list[i][j]:
            max = try_list[i][j]
            max_index = j
    for c in range(3):
        if c == max_index:
            pass
        else:
            sum += math.pow(try_list[i][c],2)
        if c == 2:
            if sum == math.pow(try_list[i][max_index],2):
                print("right")
            else:
                print("wrong")
        