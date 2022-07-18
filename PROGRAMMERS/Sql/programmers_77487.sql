# 이 문제는 GROUP BY 를 통해 새로운 TABLE 을 만들어내고, JOIN 을 하여서 원하는 결과를 얻어내는 문제이다.
SELECT p1.id, p1.name, p1.host_id
FROM places AS p1 JOIN (SELECT host_id
                     FROM places
                     GROUP BY host_id
                     HAVING COUNT(host_id) >= 2) AS p3
                     ON p1.host_id = p3.host_id
ORDER BY p1.id
