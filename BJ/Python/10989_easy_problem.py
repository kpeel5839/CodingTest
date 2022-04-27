import sys
n = int(input())
c = [0] * 10001 
for i in range(n): 
    x = int(sys.stdin.readline()) 
    c[x] += 1 
for i in range(10001): 
    if c[i] != 0: 
        for j in range(c[i]):
            sys.stdout.write(str(i) + "\n")