import sys
def gcd(x, y):
    if x > y:
        big = x
        small = y
    else:
        big = y
        small = x
    big_product = 1
    small_product = 1
    while big * big_product != small * small_product:
        if big * big_product < small * small_product:
            big_product += 1
            continue
        small_product += 1
    return big * big_product
        
size = int(sys.stdin.readline())
ling = list(map(int, sys.stdin.readline().split()))
pivot = ling[0]
for i in range(1, size):
    number = gcd(pivot, ling[i])
    mole = number // ling[i]
    deno = number // pivot
    sys.stdout.write(str(mole) + '/' + str(deno) + '\n')