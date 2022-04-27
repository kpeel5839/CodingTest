import math

start_number , finish_number = map(int, input().split())
number = start_number
if start_number <= 2 and finish_number >= 2:
    print(2)
if start_number <= 3 and finish_number >= 3:
    print(3)
while number <= finish_number: 
    for j in range(2,int(math.floor(math.sqrt(number)))+1):
        if number % j ==0:
            break
        if int(math.floor(math.sqrt(number))) == j:
            print(number) #어떻게 접근할 수 있을까? 다른 규칙이 있을까? 소수를 찾는데?
    if number == start_number and number % 2 ==0:
        number += 1
        continue
    number +=2
    