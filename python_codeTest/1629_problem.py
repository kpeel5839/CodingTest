import sys
a , b , c = map(int,sys.stdin.readline().split())
def fac(b):
    global a
    global c 
    if b == 1:
        return a % c
    if b % 2 == 0:
        return (((fac(b // 2) % c) ** 2)) % c
    else:
        return ((a % c) * (fac((b - 1) // 2) % c) ** 2) % c
sys.stdout.write(str(fac(b)))