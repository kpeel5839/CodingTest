import sys
number = int(sys.stdin.readline())
result_list = []
count = 0 
def dfs(number_list):
    global number
    global count
    global result_list
    if len(number_list) == 1 and number_list[0] == 0:
        return
    if len(number_list) > 1 and abs(number_list[len(number_list) - 1] - number_list[len(number_list) - 2]) != 1:
        return
    if len(number_list) == number:
        count += 1
        result_list.append(number_list.copy())
        sys.stdout.write("number list: " + str(number_list) + "\n")
        return
    for i in range(0, 10):
        number_list.append(i)
        dfs(number_list)
        number_list.pop()
dfs([])
sys.stdout.write("count :" + str(count) + "\n")
sys.stdout.write("result list: " + str(result_list))