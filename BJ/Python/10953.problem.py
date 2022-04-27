size = int(input())
num_list = [0 for _ in range(size)]
for i in range(size):
    x ,y = map(int, input().split(','))
    num_list[i] = x + y

for i in range(size):
    print(num_list[i])