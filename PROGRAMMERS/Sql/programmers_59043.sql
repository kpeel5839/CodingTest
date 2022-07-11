SELECT i.animal_id AS ANIMAL_ID, i.name AS NAME
FROM animal_ins i JOIN animal_outs o ON i.animal_id = o.animal_id # 조인해주고
WHERE o.datetime <= i.datetime # 입양일이 보호시작일보다 빠른 경우를 걸러준다.
ORDER BY i.datetime; # 입양일로 Sort 하고 있었음
