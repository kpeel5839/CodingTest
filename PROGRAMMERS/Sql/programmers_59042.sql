# 그냥 LEFT 조인 이용해서 쉽게 풀 수 있는 문제이다.
SELECT o.animal_id, o.name
FROM animal_outs o LEFT JOIN animal_ins i
ON o.animal_id = i.animal_id
WHERE i.INTAKE_CONDITION IS NULL;
