import sys
def fac(number):
    result = number
    for i in range(number - 1,0,-1):
        result *= i
    return result
def combination(a, b):
    if a > b:
        big = a
        small = b
    else:
        big = b
        small = a
    if a != b:
        return fac(big) // (fac(big - small) * fac(small))
    if a == b:
        return 1
size = int(sys.stdin.readline())
for i in range(size):
    a, b = map(int,sys.stdin.readline().split())
    sys.stdout.write(str(combination(a, b)) + '\n')