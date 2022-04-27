import sys
import collections
size = int(sys.stdin.readline())
imp = sys.stdin.readline
for i in range(size):
    command_st = sys.stdin.readline()
    array_size = int(sys.stdin.readline())
    number_list = imp().lstrip('[').rstrip(']\n')
    if len(number_list) > 0:
        queue_1 = collections.deque(list(map(int,number_list.split(','))))
        queue_2 = collections.deque(list(reversed(queue_1)))
    else:
        queue_1 = []
        queue_2 = []
    select = 1
    correct = True
    for j in range(len(command_st)):
        if command_st[j] == '\n':
            continue
        elif command_st[j] == 'R':
            if select == 1:
                select = 2
            else:
                select = 1
            continue
        elif command_st[j] == 'D':
            if len(queue_1) == 0:
                sys.stdout.write('error' + '\n')
                correct = False
                break
            elif select == 1:
                queue_1.popleft()
                queue_2.pop()
            elif select == 2:
                queue_2.popleft()
                queue_1.pop()
            continue
    if correct == True:
        if len(queue_1) > 0:
            for j in range(len(queue_1)):
                if select == 1:
                    if len(queue_1) == 1:
                        sys.stdout.write('[' + str(queue_1[j]) + ']' + '\n')
                        break
                    elif j == 0:
                        sys.stdout.write('[' + str(queue_1[j]) +',')
                        continue
                    elif j == len(queue_1) - 1:
                        sys.stdout.write(str(queue_1[j]) + ']' + '\n')
                        continue
                    sys.stdout.write(str(queue_1[j]) + ',')
                elif select == 2:
                    if len(queue_2) == 1:
                        sys.stdout.write('[' + str(queue_2[j]) + ']' + '\n')
                        break
                    elif j == 0:
                        sys.stdout.write('[' + str(queue_2[j]) +',')
                        continue
                    elif j == len(queue_2) - 1:
                        sys.stdout.write(str(queue_2[j]) + ']' + '\n')
                        continue
                    sys.stdout.write(str(queue_2[j]) + ',')
        else:
            sys.stdout.write('[]' + '\n')