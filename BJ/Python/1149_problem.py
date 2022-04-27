#일단 틀림 사이즈가 좀 큰것을 해서 한번 해보자
size = int(input())
house_list = [[0 for _ in range(3)] for _ in range(size)]
min_cost = [[0 for _ in range(3)] for _ in range(size)]
for i in range(size):
    house_list[i][0:3] = map(int,input().split())
for i in range(size):
    if i == 0:
        for j in range(3):
            min_cost[i][j] = house_list[i][j]
    else:
        for j in range(3):
            min = 10000001
            for c in range(3):
                if j == c:
                    continue
                else:
                    if min > min_cost[i-1][c]:
                        min = min_cost[i-1][c]
            min_cost[i][j] = house_list[i][j] + min
#             print("min_cost[{0}][{1}] = house_list[{0}][{1}] + min == {2} = {3} + {4}".format(i,j,min_cost[i][j],house_list[i][j],min))
# print("fucking result")
# for i in range(size):
#     for j in range(3):
#         print(min_cost[i][j], end =" ")
#     print()
min = 10000001
for i in range(3):
    if min > min_cost[size - 1][i]:
        min = min_cost[size - 1][i]
print(min)