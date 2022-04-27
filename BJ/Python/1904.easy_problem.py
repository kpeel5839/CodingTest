f = [0 for _ in range(1000001)]

def tile(number):
    global f
    f[1] = 1
    f[2] = 2
    if number == 1:
        return f[number]
    if number == 2:
        return f[number]
    for number in range(3,number + 1):
        f[number] = (f[number - 1] + f[number - 2]) % 15746
    return f[number]

n = int(input())
count = tile(n)
print(count)