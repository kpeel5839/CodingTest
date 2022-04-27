import math
size = int(input())
for i in range(size): 
    x1, y1, r1, x2, y2, r2 = map(int, input().split())
    distance = math.sqrt(math.pow(x2 - x1, 2) + math.pow(y2 - y1, 2))
    if r1 > r2:
        big_radius = r1
        small_radius = r2
    else:
        big_radius = r2
        small_radius = r1
    if x1 == x2 and y1 == y2 and r1==r2:
        print(-1) 
        continue
    if (distance >= big_radius and distance < (r1 + r2)) or (distance < big_radius and distance > (big_radius - small_radius)):
        print(2)
        continue
    if (distance >= big_radius and distance == (r1 + r2)) or (distance < big_radius and distance == (big_radius - small_radius)):
        print(1)
        continue
    if (distance >= big_radius and distance > (r1 + r2)) or (distance < big_radius and distance < (big_radius - small_radius)):
        print(0)
        continue

    
    
    