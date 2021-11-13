import sys #let's go baby
size , squared = map(int,sys.stdin.readline().split())
process = [list(map(int,sys.stdin.readline().split())) for _ in range(size)]
result_process = [[0 for _ in range(size) ] for _ in range(size)]
p = 1000
for i in range(size):
    for j in range(size):
        process[i][j] = process[i][j] % p
def procession(first_list , second_list):
    global size
    result_list = [[0 for _ in range(size)] for _ in range(size)]
    for i in range(size):
        for j in range(size):
            x = i
            y = j
            insert_value = 0
            for c in range(size):
                insert_value += ((first_list[x][c] % p) * (second_list[c][y] % p)) % p
            result_list[i][j] = insert_value % p
    return result_list
def process_squared(squared):
    global process
    global size
    tmp_process = [[0 for _ in range(size)] for _ in range(size)]
    if squared == 1:
        return process
    if squared % 2 == 0:
        tmp_process = process_squared(squared // 2)
        # sys.stdout.write("squared : {0} " .format(squared) +'\n')
        # for i in range(size):
        #     for j in range(size):
        #         sys.stdout.write(str(tmp_process[i][j]) +' ')
        #     sys.stdout.write('\n')
        return procession(tmp_process,tmp_process)
    else:
        tmp_process = process_squared(squared // 2)
        # sys.stdout.write("squared : {0} " .format(squared) +'\n')
        # for i in range(size):
        #     for j in range(size):
        #         sys.stdout.write(str(tmp_process[i][j]) +' ')
        #     sys.stdout.write('\n')
        return procession(procession(tmp_process,tmp_process), process)

result_process = process_squared(squared)
for i in range(size):
    for j in range(size):
        sys.stdout.write(str(result_process[i][j]) +' ')
    sys.stdout.write('\n')