size = int(input())
num_list = [int(input()) for _ in range(size)]
max_number = 0
for i in range(size):
    if max_number < num_list[i]:
        max_number = num_list[i]
counting_list = [0 for _ in range(max_number + 1)]
for i in range(size):
    counting_list[num_list[i]] += 1
for i in range(1,max_number + 1):
    counting_list[i] += counting_list[i-1]
counting_idx = max_number
while counting_idx >= 0:
    if counting_list[counting_idx] == 0:
        counting_idx -= 1
        continue
    if counting_idx == 0:
        num_list[counting_list[0]-1] = counting_idx
        counting_list[counting_idx] -= 1
        if counting_list[counting_idx] == 0:
            counting_idx -= 1
        continue
    if counting_idx != 0 and counting_list[counting_idx] != counting_list[counting_idx - 1]:
        num_list[counting_list[counting_idx]-1] = counting_idx
        counting_list[counting_idx] -= 1
        continue
    counting_idx -= 1
for i in range(size):
    print(num_list[i])