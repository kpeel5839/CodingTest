# DATE_FORMAT 을 통해서 datetime 에서 연도, 달, 날짜만 뽑아내는 것이 핵심인 문제였음
SELECT animal_id, name, DATE_FORMAT(datetime, '%Y-%m-%d')
FROM animal_ins
ORDER BY animal_id;
