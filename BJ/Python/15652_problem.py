number , length = map(int, input().split())
def dfs(level , word, judge):
    if level == length:
        if len(word) == 1:
            print(' '.join(list(map(str,word))))
            return
        for i in range(1,length):
           if word[i] < word[i-1]:
               judge = False
        if judge == True:
            print(' '.join(list(map(str,word))))
        return
    
    for i in range(1,number + 1):
        temp = word.copy()
        temp.append(i)
        dfs(level + 1, temp , True)
dfs(0 , [], True)