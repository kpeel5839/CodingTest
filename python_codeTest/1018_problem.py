height, width = map(int, input().split())
chess_list = [[0 for _ in range(width)] for _ in range(height)]
black_start_list = [[0 for _ in range(8)] for _ in range(8)]
white_start_list = [[0 for _ in range(8)] for _ in range(8)]
white_count = 0
black_count = 0
min = 3000
for i in range(height):
    chess_list[i] = input()
for i in range(0,height - 7):
    for j in range(0, width - 7):
        i_index = 0
        for h_idx in range(i,i+8):
            j_index = 0
            for w_idx in range(j,j+8):
                black_start_list[i_index][j_index] = chess_list[h_idx][w_idx]
                white_start_list[i_index][j_index] = chess_list[h_idx][w_idx]
                j_index += 1
            i_index += 1
        black_count = 0
        white_count = 0
        if black_start_list[0][0] != 'B':
            black_start_list[0][0] = 'B'
            black_count += 1
        if white_start_list[0][0] != 'W':
            white_start_list[0][0] = 'W'
            white_count += 1 #여기서 이제 각각의 min 연산 하면서 count 세면 됨 그러면 무조건 끝남
        for i_idx in range(0,8):
            for j_idx in range(0,8):
                if j_idx != 7 and black_start_list[i_idx][j_idx] == 'B' and black_start_list[i_idx][j_idx + 1] == 'B':
                    black_start_list[i_idx][j_idx+1] = 'W'
                    black_count += 1
                if i_idx != 7 and black_start_list[i_idx][j_idx] == 'B' and black_start_list[i_idx + 1][j_idx] == 'B':
                    black_start_list[i_idx+1][j_idx] = 'W'
                    black_count += 1
                if j_idx != 7 and black_start_list[i_idx][j_idx] == 'W' and black_start_list[i_idx][j_idx + 1] == 'W':
                    black_start_list[i_idx][j_idx+1] = 'B'
                    black_count += 1
                if i_idx != 7 and black_start_list[i_idx][j_idx] == 'W' and black_start_list[i_idx + 1][j_idx] == 'W':
                    black_start_list[i_idx+1][j_idx] = 'B'
                    black_count += 1
                if j_idx != 7 and white_start_list[i_idx][j_idx] == 'B' and white_start_list[i_idx][j_idx + 1] == 'B':
                    white_start_list[i_idx][j_idx+1] = 'W'
                    white_count += 1
                if i_idx != 7 and white_start_list[i_idx][j_idx] == 'B' and white_start_list[i_idx + 1][j_idx] == 'B':
                    white_start_list[i_idx+1][j_idx] = 'W'
                    white_count += 1
                if j_idx != 7 and white_start_list[i_idx][j_idx] == 'W' and white_start_list[i_idx][j_idx + 1] == 'W':
                    white_start_list[i_idx][j_idx+1] = 'B'
                    white_count += 1
                if i_idx != 7 and white_start_list[i_idx][j_idx] == 'W' and white_start_list[i_idx + 1][j_idx] == 'W':
                    white_start_list[i_idx+1][j_idx] = 'B'
                    white_count += 1
        if min > black_count:
            min = black_count
        if min > white_count:
            min = white_count
print(min)
    