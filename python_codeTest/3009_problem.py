#하나만 다른놈이 기준점이고 그리고 그 기준 점을 알면 다른 것들과 비교해서 다른 한점을 뺀다음 자신의 위치에 더하면 됨
position = []
count = 0
for i in range(3):
    x , y = map(int,(input().split()))
    position.append([])
    position[i].append(x)
    position[i].append(y)
for i in range(3):
    count = 0
    for j in range(0,3):
        if i == j:
            continue
        if (position[i][0] != position[j][0] and position[i][1] == position[j][1]) or \
            (position[i][0] == position[j][0] and position[i][1] != position[j][1]):
            count += 1
    if count == 2:
        pivot_index = i

for i in range(3):
    if pivot_index == i:
        continue
    if position[pivot_index][0] != position[i][0]:
        new_x_pos = position[pivot_index][0]-(position[pivot_index][0] - position[i][0])    
    if position[pivot_index][1] != position[i][1]:
        new_y_pos = position[pivot_index][1]-(position[pivot_index][1] - position[i][1])
print(new_x_pos,new_y_pos)