SELECT FIRSTNAME, LASTNAME, CITY, STATE
FROM PERSON p
LEFT OUTER JOIN ADDRESS a ON p.PERSONID = a.PERSONID;
