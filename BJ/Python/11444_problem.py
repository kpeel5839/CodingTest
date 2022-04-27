import sys
size = int(sys.stdin.readline())
p = 1000000007
process = [[1,1] , [1,0]]
process_size = 2
result_process = [[0 for _ in range(2)] for _ in range(2)]
def procession(first_list , second_list):
    global process_size
    tmp_list = [[0 for _ in range(process_size)] for _ in range(process_size)]
    for i in range(process_size):
        for j in range(process_size):
            x = i
            y = j
            insert_value = 0
            for c in range(process_size):
                insert_value += ((first_list[x][c] % p) * (second_list[c][y] % p)) % p
            tmp_list[i][j] = insert_value % p
    return tmp_list
def process_squared(b):
    global process
    if b == 1:
        return process
    if b % 2 == 0:
        tmp_process = process_squared(b // 2)
        return procession(tmp_process, tmp_process)
    else:
        tmp_process = process_squared(b // 2)
        return procession(procession(tmp_process, tmp_process) , process)
if size != 0:
    result_process = process_squared(size)
if size == 0:
    result_process[0][1] = 0
sys.stdout.write(str(result_process[0][1]))