score_list = [int(input()) for _ in range(5)]
sum = 0
for i in range(5):
    if score_list[i] < 40:
        sum += 40
    else:
        sum += score_list[i]

print(sum // 5)