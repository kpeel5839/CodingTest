import sys
size = int(sys.stdin.readline())
dis = list(map(int,sys.stdin.readline().split()))
price = list(map(int, sys.stdin.readline().split()))
total_price = 0
i = 0
count = 0
while i < size - 1:
    j = i
    dis_sum = dis[i]
    count = 0
    while True:
        if j + 1 < size and (price[i] < price[j + 1] or j + 1 == size - 1):
            j += 1
            if j != size - 1:
                dis_sum += dis[j]
            count += 1
            continue
        break
    total_price += price[i] * dis_sum
    i = i + 1 + count
sys.stdout.write(str(total_price))