import sys
size , squared = map(int,sys.stdin.readline().split())
process = [list(map(int,sys.stdin.readline().split())) for _ in range(size)]
result_process = [[0 for _ in range(size) ] for _ in range(size)]
for i in range(size):
    for j in range(size):
        result_process[i][j] = process[i][j]
p = 1000
sys.stdout.write(str(result_process) +'\n')
sys.stdout.write(str(process) +'\n')
def procession(result_process, process):
    global size
    tmp_process = [[0 for _ in range(size)] for _ in range(size)]
    for i in range(size):
        for j in range(size):
            tmp_process[i][j] = result_process[i][j]
    for i in range(size):
        for j in range(size):
            x = i
            y = j
            insert_value = 0
            for c in range(size):
                insert_value += tmp_process[x][c] * process[c][y]
                sys.stdout.write("insert_value operationing .. tmp_process[{0}][{1}] + process[{1}][{2}] = {3} + {4}".format(x, c, y, tmp_process[x][c], process[c][y]) +'\n' )
            result_process[i][j] = insert_value
            sys.stdout.write("result_process[{0}][{1}] = {2}".format(i, j, insert_value) +'\n')
for i in range(squared - 1):
    procession(result_process, process)
    sys.stdout.write(str(result_process) +'\n')
for i in range(size):
    for j in range(size):
        sys.stdout.write(str(result_process[i][j]) +' ')
    sys.stdout.write('\n')
            