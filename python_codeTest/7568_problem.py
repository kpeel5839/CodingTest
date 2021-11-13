size = int(input())
body_list = [[0 for _ in range(2)] for _ in range(size)]
for i in range(size):
    body_list[i][0] , body_list[i][1] = map(int, input().split())
for i in range(size):
    rank = 1
    for j in range(size):
        if  j == i:
            continue
        if body_list[i][0] < body_list[j][0] and body_list[i][1] < body_list[j][1]:
            rank += 1
    print(rank, end = " ")        