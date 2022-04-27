num_list = list(map(int, input().split()))
sum = 0
for i in range(len(num_list)):
    sum += num_list[i]**2

print(sum % 10)