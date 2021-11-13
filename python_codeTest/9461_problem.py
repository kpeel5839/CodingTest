f = [0 for _ in range(100000000)]

def tri(number):
    for i in range(1, 4):
        f[i] = 1
    for i in range(4, 6):
        f[i] = 2
    if 1 <= number <= 3:
        return f[number]
    if 4 <= number <= 5:
        return f[number]
    for number in range(6, number + 1):
        f[number] = f[number - 1] + f[number - 5]
    
    return f[number]

size = int(input())

num_list = []
for i in range(size):
    num_list.append(int(input()))

for i in range(size):
    len = tri(num_list[i])
    print(len)