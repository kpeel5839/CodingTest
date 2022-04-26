# 테이블 하나를 JOIN 한다.
# DATE_ADD 를 통해서, yesterday 의 날짜를 하나 더한다음에, TODAY 와 RECORDDATE 로 JOIN 한다.
# 그러고서, JOIN 된 테이블을 두고, TEMPERATURE 를 비교해서, today에 TEMPERATURE 가 더 큰 것을 출력
SELECT today.ID
FROM WEATHER AS today
INNER JOIN WEATHER AS yesterday ON DATE_ADD(yesterday.RECORDDATE, INTERVAL 1 DAY) = today.RECORDDATE
WHERE yesterday.TEMPERATURE < today.TEMPERATURE
