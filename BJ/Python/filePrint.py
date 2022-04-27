f = open("/Users/jaeyeonkim/Desktop/data.txt" , "r" , encoding = "UTF-8")

data = []

for line in f.readlines():
    data.append(line.strip())

print(data) 