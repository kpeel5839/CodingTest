import sys
a, b = map(int ,sys.stdin.readline().split())
small = 0
big = 0
max_measure = 0
if a > b:
    small = b
    big = a
else:
    small = a
    big =b
for i in range(small, 1, -1): # measure get
    if a % i == 0 and b % i == 0:
        max_measure = i
        break
if max_measure == 0:
    max_measure = 1
big_product = 1
small_product = 1
result = 0
while True:
    if big * big_product == small_product * small:
        result = big * big_product
        break
    if big * big_product < small_product * small:
        big_product += 1
    small_product += 1
sys.stdout.write(str(max_measure) + '\n')
sys.stdout.write(str(result))