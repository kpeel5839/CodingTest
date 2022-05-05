# HOUR(date) 를 사용해서, 시간을 뽑아내는 것
# 이 문제는 그래도, LEVEL 2 치고는 조금 어려웠던 듯
# HOUR 를 이용해서, 쉽게 풀 수 있었고, GROUP BY 이전에, WHERE 을 사용해야 한다라는 것을 알았음
SELECT HOUR(datetime) AS hour, COUNT(datetime) AS count
FROM animal_outs
WHERE 9 <= HOUR(datetime) AND HOUR(datetime) <= 19
GROUP BY HOUR(datetime)
ORDER BY HOUR(datetime);

