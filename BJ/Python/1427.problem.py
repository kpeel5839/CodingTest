number_str = input()
number_list = [0 for _ in range(len(number_str))]
for i in range(len(number_str)):
    number_list[i] = number_str[i]
number_list.sort()
number_list.reverse()
for i in range(len(number_str)):
    print(number_list[i], end = "")