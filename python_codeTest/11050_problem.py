import sys
row , col = map(int ,sys.stdin.readline().split())
arr = [[1 for _ in range(row + 1)] for _ in range(row + 1)]
for i in range(1,row + 1):
    for j in range(i + 1):
        if j == 0 or j == i:
            arr[i][j] = 1
            continue
        arr[i][j] = arr[i-1][j-1] + arr[i-1][j]
for i in range(row + 1):
    for j in range(i + 1):
        sys.stdout.write(str(arr[i][j]) + ' ')
    sys.stdout.write('\n')
sys.stdout.write(str(arr[row][col]))
