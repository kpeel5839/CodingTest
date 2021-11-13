import sys
first_st = sys.stdin.readline()
second_st = ''
tmp_st = ''
st_pointer = 0
f_idx = 0
l_idx = 0
num_list = ['0','1','2','3','4','5','6','7','8','9']
num_start = False
for i in range(len(first_st)):
    if num_start == False and first_st[i] == '0':
        continue
    if first_st[i] in num_list:
        num_start = True
    if first_st[i] == '-' or first_st[i] == '+':
        num_start = False
    second_st += first_st[i]
    
while st_pointer < len(second_st) - 1:
    meet_count = 0 
    prev_pointer = st_pointer
    for i in range(st_pointer,len(second_st)):
        if meet_count == 0 and second_st[i] == '-':
            f_idx = i + 1
            meet_count += 1
            continue
        if meet_count == 0 and i == len(second_st) - 1:
            st_pointer = i
            f_idx = -1
            l_idx = -1
            break
        if meet_count == 1 and (second_st[i] == '-' or i == len(second_st) - 1):
            l_idx = i
            st_pointer = i
            break
    tmp_index = prev_pointer
    while tmp_index < len(second_st):
        if tmp_index == f_idx:
            tmp_st += '('
            f_idx = -1
            continue
        if tmp_index == l_idx:
            tmp_st += ')'
            l_idx = -1
            tmp_index += 1
            break
        tmp_st += second_st[tmp_index]
        tmp_index += 1
sys.stdout.write(str(eval(tmp_st)))