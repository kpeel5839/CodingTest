length = int(input())
count = 0 
already_list = []
tile_list = ['00' , '1']
def tile(word,lng):
    global count

    if lng > length:
        return
        
    if lng == length:
        already_list.append(word)
        count += 1

    else:
        for i in range(2):
            word.append(tile_list[i])
            if i == 0:
                tile(word,lng + 2)
            if i == 1:
                tile(word,lng + 1)
            word.pop()
        
tile([],0)
print(count % 15746)