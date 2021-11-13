import sys
def gcd(x,y): #이것은 두 수에서 최대 공약수를 찾는 알고리즘 그냥 이것은 직관적으로 봤을 때 이렇게 구할 수 있을 것 같음
    mod = x % y
    while mod != 0:
        x = y
        y = mod
        mod = x % y
    return y
def div(number): #관건은 여기서 결정됨 이것이 그냥 약수를 구할 때 그니까 최대 공약수의 약수를 구할 때 제곱근 까지만 구하면서 대폭상ㅅ으
    #그것이 가능한 이유는 i 가 약수일 때 그것을 number // i 로 나눠서 저장하기 때문에 제곱근으로 줄여도 가능함
    div_list = [number]
    for i in range(2, int(number ** (1/2)) + 1): #범위를 여기까지밖에 안해줘서 set 연산이 필요가 없음
        if number % i == 0:
            div_list.append(i)
            if number // i != i: #같은 숫자일 경우 한번 한번만 넣음
                div_list.append(number // i)
    div_list.sort() 
    return div_list

size = int(sys.stdin.readline())
num = [int(sys.stdin.readline()) for _ in range(size)]
num.sort()

num_diff = []
for i in range(size - 1):
    num_diff.append(num[i + 1] - num[i]) #여기서 차를 입력해줌 차를 입력해주는 이유는 차를 구해준 뒤 나머지가 같은 경우에 빼게 되면 그 차는 약수를 공유 하기 때문이다.

answer = 0

if len(num_diff) == 1:
    answer = num_diff[0]
elif len(num_diff) == 2:
    answer = gcd(num_diff[1],num_diff[0])
else: #그래서 여기서 이제 최대 공약수들끼리 계속 최대 공약수를 구하게 된다. 그래서 아마 숫자는 계속 작아지지 않을 까 싶다. 이것이 계속 반복 될 수록
    answer = num_diff[0]
    sys.stdout.write("initial answer : " + str(answer) + '\n')
    for i in range(1, len(num_diff)):
        answer = gcd(answer, num_diff[i])
        sys.stdout.write("answer : " + str(answer) + '\n') #계속 같거나 아니면 작아짐

for i in div(answer):
    sys.stdout.write(str(i) + ' ') #그니까 결국 이념은 큰 수와 작은 수의 차이들의 최대공약수를 계속 구하고 결과적으로 나온 최대공약수의 약수를 구하면 그것이 나머지가 같은 값이다.