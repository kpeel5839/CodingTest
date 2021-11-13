import sys 
number = int(sys.stdin.readline())
result = 0
result = (number // 5) + (number // 25) + (number // 125)
sys.stdout.write(str(result))