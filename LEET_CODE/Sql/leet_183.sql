# Customers who never order
SELECT NAME AS CUSTOMERS
FROM CUSTOMERS c
LEFT OUTER JOIN ORDERS o ON c.id = o.CUSTOMERID
WHERE o.CUSTOMERID IS NULL;
