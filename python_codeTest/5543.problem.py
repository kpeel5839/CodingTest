ham = [int(input()) for _ in range(3)]
s = [int(input()) for _ in range(2)]

min = 4001

for i in range(3):
    for j in range(2):
        if min > ham[i] + s[j] - 50:
            min = ham[i] + s[j] - 50

print(min)