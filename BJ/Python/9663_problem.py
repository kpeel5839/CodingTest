def queen(row):
    global count
    if row == n: #이게 결국 프로미싱 되서 재귀 호출을 마지막까지 성공해서 row 와 n의 값이 같아지면 카운트 +1 이다
        count += 1
    else:
        for j in range(n):
            col[row] = j #여기다가 j 를 넣는 것은 해당 row에 열들을 부여 하는 것임
            if promising(row): #만일 이게 쌉 가능이면 프로미싱해도 놓은 상태로 다음 체스도 놓는다.
                queen(row + 1) #row 를 하나 증가 시켜서 다음 열에 놓는다.

def promising(row): #지금 해당열에 말을 놓았는데 그것이 잘 놓은건지 확인좀 해줘 제대로 놓은 거 아니면 안 놓게
    for k in range(row): #알았어 확인해줄게 근데 너 지금 행은 확인 안해도 되지? 너 해당 행은 짜피 하나 더 못 놓잖아 놓을 것도 아니고 왜냐하면 이거 맞으면 로우 + 1 추가해서 짜피 재귀적 호출 할 꺼니까
        if col[row] == col[k] or abs(col[row] - col[k]) == abs(row - k): #row와 k가 같으면 당연히 col 값도 같을테니까 알아서 걸러짐 애초에 그리고 row까지 안감
            return False #위에 것들과 비교해서 해당 이 체스가 여기에 놓아져도 되는지 확인 하는 것
    return True #만약 조건이 맞으면 트루를 반환해서 함

n = int(input())
col = [0] * n
count = 0
queen(0)
print(count)
# 이런식으로 재귀적 호출을 프로미싱 없이 할 수도 있지만 백 트래킹은 재귀적 호출을 극적으로 줄이는 행위임