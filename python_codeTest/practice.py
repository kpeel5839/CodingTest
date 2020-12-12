from tkinter import *
from random import *
window  = Tk()
randomNumber = 0
#문제 생성하는 함수
def random():
    global randomNumber
    randomNumber = randint(0 , 100)
#맞나 봐서 확인하는 함수

def check():
    userNumber = int(e1.get())
    if(userNumber == randomNumber):
        e2.insert(0, "맞췄습니다!")
    elif(userNumber > randomNumber): #더 큰 걸 입력한 경우
        e2.insert(0, "더 낮은 숫자입니다.")
    elif(userNumber < randomNumber):
        e2.insert(0, "더 높은 숫자 입니다.")

l3 = Label(window , text="랜덤한 숫자를 맞춰보세요!")
l4 = Label(window , text="(범위 0 ~ 100)")
l1 = Label(window , text="입력하세요")
l2 = Label(window, text="결과")

l3.grid(row=0 , column = 0)
l4.grid(row=0 , column = 1)
l1.grid(row=1, column=0)
l2.grid(row=2, column=0)

e1 = Entry(window)
e2 = Entry(window)
e1.grid(row=1, column=1)
e2.grid(row=2, column=1)

b1 = Button(window, text="문제생성" , command=random)
b2 = Button(window, text="맞추기" , command=check)
b1.grid(row=3, column=0)
b2.grid(row=3, column=1)

window.mainloop()