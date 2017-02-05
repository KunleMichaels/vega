INSERT INTO FARE
(FARE_ID, PRICE, VALIDITY, POLY_PATH, VERSION)
VALUES
(1, 'EUR 25.25', 'P1Y1M1D', '[{"latitude":0.0,"longitude":0.0},{"latitude":0.0,"longitude":11.0},{"latitude":11.0,"longitude":0.0},{"latitude":11.0,"longitude":11.0}]', 0);

INSERT INTO FARE
(FARE_ID, PRICE, VALIDITY, POLY_PATH, VERSION)
VALUES
(2, 'EUR 12.11', 'P1Y1M1D', '[{"latitude":0.0,"longitude":0.0},{"latitude":0.0,"longitude":22.0},{"latitude":22.0,"longitude":0.0},{"latitude":22.0,"longitude":22.0}]', 0);

INSERT INTO FARE_VEHICLE_TYPE
(FARE_ID, VEHICLE_TYPES)
VALUES
(1, 'BUS');

INSERT INTO FARE_VEHICLE_TYPE
(FARE_ID, VEHICLE_TYPES)
VALUES
(2, 'TRAM');

INSERT INTO FARE_OPERATOR
(FARE_ID, OPERATOR_ID)
VALUES
(1, 1);

INSERT INTO FARE_OPERATOR
(FARE_ID, OPERATOR_ID)
VALUES
(2, 2);


