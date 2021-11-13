number = int(input())
count = 0
number_int = 666
result = 0
while number != count:
    number_str = str(number_int)
    for i in range(0,len(number_str)-2):
        if number_str[i] == '6' and number_str[i+1] == '6' and number_str[i+2] == '6':
            count += 1
            break
    if number == count:
        result = number_int
    number_int += 1
print(result)