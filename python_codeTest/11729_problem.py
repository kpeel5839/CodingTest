number = int(input())
count = 0
hanoi_list = []
def hanoi(number,start,to,other):
    if number == 0:
        return
    hanoi(number - 1 ,start,other,to)
    hanoi_list.append(start)
    hanoi_list.append(to)
    hanoi(number - 1 ,other,to ,start)
hanoi(number,1,3,2)
for i in range(len(hanoi_list)):
    if i % 2 != 0 and i != 0:
        count += 1 
print(count)
count = 0
for i in range(len(hanoi_list)):
    count += 1
    print(hanoi_list[i], end = " ")
    if count % 2 == 0:
        print(end = "\n")