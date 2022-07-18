SELECT i.animal_id, i.name
FROM animal_ins AS i JOIN (SELECT i1.animal_id, DATEDIFF(i1.datetime, o1.datetime) AS during
                           FROM animal_ins i1 JOIN animal_outs o1
                           ON i1.animal_id = o1.animal_id) AS n
                           ON i.animal_id = n.animal_id
ORDER BY n.during
LIMIT 2

