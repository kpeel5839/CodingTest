from itertools import permutations

direction = [0 , 1 , 2 , 3]
for c in permutations(direction , 6):
    print(c)