number = int(input())
def factorial (number):
    if number <= 1:
        return 1
    return number * factorial(number-1)
print(factorial(number))