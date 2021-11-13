import sys
size = int(sys.stdin.readline())
num = [[0 for _ in range(2)] for _ in range(size)]
for i in range(size):
    num[i][0],num[i][1] = map(int, sys.stdin.readline().split())
for i in range(size):
    if num[i][0] > num[i][1]:
        big = num[i][0]
        small = num[i][1]
    else:
        big = num[i][1]
        small = num[i][0]
    product_number = 1
    small_measure = 0
    while True:
        if big * product_number % small == 0:
            small_measure = big * product_number
            break
        product_number += 1
    sys.stdout.write(str(small_measure) + '\n')