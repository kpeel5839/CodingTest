number, size =  map(int, input().split())
start_number = int("1" + "0" * (size - 1))
num_list = []
if size == 1:
    for i in range(1,number + 1):
        print(i)
else:
    for i in range(start_number, start_number * (number + 1)):
        judge_list = [0 for _ in range(size)]
        for j in range(0,size):
            judge_list[j] = int(str(i)[j])
        judge = True
        if size == len(set(judge_list)):
            for c in range(size):
                if judge_list[c] == 0 or judge_list[c] > number:
                    judge = False
                    break
            if judge == True:
                num_list.append(i)
    num_list.sort()
    for i in range(len(num_list)):
        for j in range(size):
            print(str(num_list[i])[j], end = " ")
        print(end = "\n")
        
    