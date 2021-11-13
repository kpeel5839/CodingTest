import sys
p = 1000000007
n , k = map(int,sys.stdin.readline().split())
def fac(a, b):
    global p
    if b == 1:
        return a % p
    if b % 2 == 0:
        return ((fac(a, b // 2) % p) ** 2) % p
    else:
        return ((a % p) * ((fac(a,b//2)%p) ** 2)) % p
fac_list = [1 for _ in range(n + 1)]
for i in range(1, n+1):
    fac_list[i] = fac_list[i-1]*i%p
up = fac_list[n]
down = (fac_list[k] * fac_list[n - k]) % p  
sys.stdout.write(str((up % p) * (fac(down, p - 2) % p) % p))
