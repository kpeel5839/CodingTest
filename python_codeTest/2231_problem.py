number_str = input()
number_int = int(number_str)
sum = 0
answer = 0
if len(number_str) >= 3:
    start_number = number_int - (len(number_str) * 10)
else:
    start_number = 1
for i in range(start_number, number_int + 1):
    sum += i
    for j in range(len(str(i))):
        sum += int(str(i)[j])
    if sum == number_int:
        answer = i
        break
    sum = 0
print(answer)

