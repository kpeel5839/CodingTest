count_zero = 0
count_one = 0
fib = [[0, 0] for _ in range(10000)]
def fibonacci(number):

    global fib
    global count_zero
    global count_one

    if number == 0:
        fib[number][0] = 1
        return fib[number]
    elif number == 1:
        fib[number][1] = 1
        return fib[number]
    if fib[number][0] != 0 or fib[number][1] != 0:
        return fib[number]
    fib[number][0] = fibonacci(number - 1)[0] + fibonacci(number - 2)[0]
    fib[number][1] = fibonacci(number - 1)[1] + fibonacci(number - 2)[1]
    return fib[number]

size = int(input())
for i in range(size):
    count_zero , count_one= fibonacci(int(input()))[0:2]
    print(count_zero , count_one)
    for i in range(40):
        for j in range(2):
            fib[i][j] = 0
    