size = int(input())
tri_list = [[0 for _ in range(size)]for _ in range(size)]
if size == 1:
    tri_list[0][0] = int(input())
    max = tri_list[0][0]
else:
    for i in range(size):
        j = i + 1
        tri_list[i][0:j] = map(int,input().split())
    tri_list[1][0] = tri_list[0][0] + tri_list[1][0]
    tri_list[1][1] = tri_list[0][0] + tri_list[1][1]

    for i in range(2,size):
        for j in range(i+1):
            if j == 0:
                tri_list[i][j] = tri_list[i][j] + tri_list[i-1][0]
                continue
            if j == i:
                tri_list[i][j] = tri_list[i][j] + tri_list[i-1][j-1]
                continue
            if tri_list[i-1][j-1] > tri_list[i-1][j]:
                tri_list[i][j] = tri_list[i][j] + tri_list[i-1][j - 1]
            else:
                tri_list[i][j] = tri_list[i][j] + tri_list[i-1][j]
    max = -1
    for i in range(size):
        if max < tri_list[size - 1][i]:
            max = tri_list[size - 1][i]
print(max)
    
                