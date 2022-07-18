# 이 문제는 sql 문제중에서도 굉장히 신기하게 변수를 사용하는 문제였음
SET @hour := -1; # 이런식으로 sql 에서 변수 선언이 가능하다.

SELECT (@hour := @hour + 1) as HOUR, (SELECT COUNT(*)
                                      FROM animal_outs
                                      WHERE HOUR(datetime) = @hour) AS count # SELECT 에서 쿼리를 날리면서 해당 hour 마다의 결과값을 구할 수 있음
FROM animal_outs
where @hour < 23 # 23보다 더 작을 때까지만
