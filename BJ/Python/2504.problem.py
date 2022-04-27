tmp_list = input()
list = []
sum = 0
already_list = [] # 숫자로 저장해서 이미 들어간 것은 포함 하지 않기로 하게끔 함
for i in range(len(tmp_list)):
    list.append(tmp_list[i])

def dfs(index,prev,my_sum):
    global sum

    for i in range(index,len(list)):
        if i not in already_list:
            if list[i] == '(' or list[i] == '[':
                if i == len(list) - 1:
                    sum = 0
                    my_sum = 0
                already_list.append(i)
                x = dfs(index + 1  , list[i] , 1)
                if my_sum == 1:
                    my_sum = 0
                my_sum += x
                continue
            if prev == '(' and list[i] == ']':
                return
            if prev == '[' and list[i] == ')':
                return
            if prev == '(' and list[i] == ')':
                already_list.append(i)
                my_sum = my_sum * 2
                return my_sum
            if prev == '[' and list[i] == ']':
                already_list.append(i)
                my_sum = my_sum * 3
                return my_sum

for i in range(len(list)):
    if i not in already_list:
        already_list.append(i)
        try:
            sum += dfs(i+1, list[i] , 1)
        except TypeError:
            sum = 0

print(sum)
    
    
    
        