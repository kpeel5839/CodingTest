import sys
while True:
    a , b = map(int ,sys.stdin.readline().split())
    if a == 0 and b == 0:
        break
    if a < b and b % a == 0:#factor case
        sys.stdout.write("factor" + '\n')
        continue
    if a > b and a % b == 0:
        sys.stdout.write("multiple" + '\n')
        continue
    sys.stdout.write("neither" + '\n')