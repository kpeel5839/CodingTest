size = int(input())
count_list = [0] * 8001
num_list = [0 for _ in range(size)]
num_index = 0
many_number = 0
many_count = 0
sum = 0
for i in range(size):
    x = int(input())
    count_list[x+4000] += 1
for i in range(8001):
    if count_list[i] != 0:
        for j in range(count_list[i]):
            if many_number < count_list[i]:
                many_number = count_list[i]
            num_list[num_index] = i-4000
            num_index += 1
for i in range(8001):
    if many_number == count_list[i]:
        many_count += 1
if many_count == 1:
    for i in range(8001):
        if many_number == count_list[i]:
            many_number = i - 4000
else:
    i_idx = 0
    many_count = 0
    while many_count != 2:
        if many_number == count_list[i_idx]:
            many_count += 1
        if many_count == 2:
            many_number = i_idx - 4000
        i_idx += 1
min = 4001
max = -4001
for i in range(size):
    if min > num_list[i]:
        min = num_list[i]
    if max < num_list[i]:
        max = num_list[i]
    sum += num_list[i]
print(int(round(sum/size,0)))
print(num_list[(size - 1) // 2])
print(many_number)
print(max - min)