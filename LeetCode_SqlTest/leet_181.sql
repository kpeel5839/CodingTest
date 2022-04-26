SELECT NAME AS EMPLOYEE
FROM EMPLOYEE e1
WHERE e1.SALARY > (SELECT e2.SALARY
                FROM EMPLOYEE e2
                WHERE e2.id = e1.MANAGERID);
