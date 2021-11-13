def fibonacci(number):
    if number == 0:
        return 0
    if number == 1 or number == 2:
        return 1
    return fibonacci(number - 1) + fibonacci(number - 2)
number = int(input())
print(fibonacci(number))