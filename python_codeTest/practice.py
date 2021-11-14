row , col= map(int , input().split())
stringMap = [input() for _ in range(row)]
map = []
for i in range(row):
    map += [list(stringMap[i])]
for i in range(row):
    for j in range(col):
        print(map[i][j] , end = " ")
    print()