import sys
first_row, first_col = map(int,sys.stdin.readline().split())
first_list = [[0 for _ in range(first_col)] for _ in range(first_row)]
for i in range(first_row):
    first_list[i][:first_col] = map(int,sys.stdin.readline().split())
second_row, second_col = map(int,sys.stdin.readline().split())
second_list = [[0 for _ in range(second_col)] for _ in range(second_row)]
for i in range(second_row):
    second_list[i][:second_col] = map(int,sys.stdin.readline().split())
result_row = first_row
result_col = second_col
roof_size = first_col
result_list = [[0 for _ in range(result_col)] for _ in range(result_row)]
for i in range(result_row):
    for j in range(result_col):
        insert_value = 0
        x = i
        y = j
        for c in range(roof_size):
            insert_value += first_list[x][c] * second_list[c][y]
        result_list[i][j] = insert_value
for i in range(result_row):
    for j in range(result_col):
        sys.stdout.write(str(result_list[i][j]) + ' ')
    sys.stdout.write('\n')
