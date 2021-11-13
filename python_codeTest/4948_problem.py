import math 


while True:
    number = int(input())
    if number == 0 :
        break
    count = 0
    start_number = number+1
    finish_number = 2*number
    number += 1
    if start_number <= 2 and finish_number >= 2:
        count += 1
    if start_number <= 3 and finish_number >= 3:
        count += 1
    while number <= finish_number: 
        for j in range(2,int(math.floor(math.sqrt(number)))+1):
            if number % j ==0:
                break
            if int(math.floor(math.sqrt(number))) == j:
                count += 1 
        if number == start_number and number % 2 ==0:
            number += 1
            continue
        number +=2
    print(count)
    