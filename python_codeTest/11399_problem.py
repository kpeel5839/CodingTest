import sys
size = int(sys.stdin.readline())
atm_list = list(map(int,sys.stdin.readline().split()))
add_list = [0 for _ in range(size)]
atm_list.sort()
for i in range(size):
    result = atm_list[i]
    for j in range(i):
        result += atm_list[j]
    add_list[i] = result
sum = 0
for i in range(size):
    sum += add_list[i]
sys.stdout.write(str(sum))