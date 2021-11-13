coin_count , cost = map(int,input().split())
coin_list = [int(input()) for _ in range(coin_count)]
count = 0
for i in range(coin_count - 1,-1,-1):
    count += cost // coin_list[i]
    cost -= (cost // coin_list[i]) * coin_list[i]
print(count)