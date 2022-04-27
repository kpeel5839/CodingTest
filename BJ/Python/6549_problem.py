import sys

def max_of_value(first_number , second_number):
    if first_number < second_number:
        return second_number
    else:
        return first_number

number_list = [0 for _ in range(100000)]
stack_value = []
stack_index = []
while True:
    input_list = list(map(int,sys.stdin.readline().split()))
    size = input_list[0]
    max = 0
    if size == 0:
        break
    for i in range(size):
        number_list[i] = input_list[i + 1]
    for i in range(size + 1):
        if i == size:
            if len(stack_value) != 0:
              while True:
                    if len(stack_value) == 1:
                        value = i * stack_value[0]
                        max = max_of_value(max, value)
                        stack_value.pop()
                        stack_index.pop()
                        break
                    value = (i - stack_index[len(stack_index) - 2] - 1) * stack_value[len(stack_value) - 1]
                    max = max_of_value(max, value)
                    stack_value.pop()
                    stack_index.pop()
            break
        if i == 0:
            stack_value.append(number_list[i])
            stack_index.append(i)
            continue
        while number_list[i] < stack_value[len(stack_value) - 1]:
            if len(stack_value) == 1:
                value = stack_value[0] * i
                max = max_of_value(max, value)
                stack_value.pop()
                stack_index.pop()
                break
            value = (i - stack_index[len(stack_index) - 2] - 1) * stack_value[len(stack_value) - 1]
            max = max_of_value(max, value)
            stack_value.pop()
            stack_index.pop()
        stack_value.append(number_list[i])
        stack_index.append(i)
    sys.stdout.write(str(max) +'\n')