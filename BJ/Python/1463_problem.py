#게속해서 나누어야 하는데 이것도 당연히 점화식으로 표현 해야 할 듯 이것은 심지어 0.15초임
#일단 이것들로 이런식으로 나올 수 있다는 것만을 알아야 할 듯
import sys
n = int(sys.stdin.readline())
min = 1000
min_list = []
def dfs(value, count , div_list):
    global min
    global min_list
    if len(div_list) > 20:
        return
    if value == 1:
        sys.stdout.write("div_list : " + str(div_list) + '\n')
        if min > count:
            min = count
            min_list = div_list.copy()
        return
    else:
        for i in range(3,0,-1):
            if i == 1:
                div_list.append(1)
                dfs(value - 1, count + 1,div_list)
                div_list.pop()
                continue
            else:
                if i == 2:
                    if value % i == 0:
                        div_list.append(2)
                        dfs(value / 2, count + 1, div_list)
                        div_list.pop()
                        continue
                if i == 3:
                    if value % i == 0:
                        div_list.append(3)
                        dfs(value / 3, count + 1, div_list)
                        div_list.pop()
                        continue
    
dfs(n , 0 , [])
sys.stdout.write("min_count : " + str(min) + '\n')
sys.stdout.write("min_list : " + str(min_list) + '\n')
