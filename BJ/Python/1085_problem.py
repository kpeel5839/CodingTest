x_pos, y_pos , width, height = map(int, input().split())
x_distance_left = x_pos
x_distance_right = width - x_pos
y_distance_bottom = y_pos
y_distance_top = height - y_pos
min = 0
min_x = 0
min_y = 0
if x_distance_left > x_distance_right:
    min_x = x_distance_right
else:
    min_x = x_distance_left

if y_distance_top > y_distance_bottom:
    min_y = y_distance_bottom
else:
    min_y = y_distance_top

if min_x > min_y:
    min = min_y
else:
    min = min_x
    
print(min)