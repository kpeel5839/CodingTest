import math

sosu_number = 10010
sosu_list = []
sosu_list.append(2)
sosu_list.append(3)
for i in range(sosu_number):
    for j in range(2, int(math.floor(math.sqrt(i))+1)):
        if i % j == 0:
            break
        if int(math.floor(math.sqrt(i))) == j:
            sosu_list.append(i)
size = int(input())
min = 10001
for i in range(size):
    number = int(input())
    start_index = 0
    min = 10001
    min_number = 0
    big_number = 0
    while True:
        search_index = start_index
        while sosu_list[search_index] < number:
            if sosu_list[start_index] + sosu_list[search_index] == number:
                if min > sosu_list[search_index]-sosu_list[start_index]:
                    min_number = sosu_list[start_index]
                    big_number = sosu_list[search_index]
            search_index += 1
        start_index += 1
        if (number / 2) < sosu_list[start_index]:
            break
    print(min_number,big_number) 
                 
                    
                    