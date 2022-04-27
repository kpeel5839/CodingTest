def myabs(number): #myabs 함수 정의
    if number < 0: #0보다 작으면 -1을 곱해주어서 절댓값을 만들어준다.
        number *= -1
    return number #결과 반환

def mymax(x,y):
    if x < y:
        return x #x가 더 크면 반환
    else:
        return y #y가 더 크면 반환

def mymin(x, y):
    if x < y:
        return x #y가 더 크면 반환
    else:
        return y #x가 더 크면 반환