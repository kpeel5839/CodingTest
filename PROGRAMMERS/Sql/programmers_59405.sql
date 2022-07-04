# Where 문에 date 형태를 사용 못하는 건가? 그래서 그냥 정렬한 뒤 LIMIT 으로 해결하였음
SELECT name
FROM animal_ins
ORDER BY datetime
LIMIT 1;
