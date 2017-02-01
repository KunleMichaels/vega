INSERT INTO OPERATOR
(OPERATOR_ID, IS_ACTIVE, DESCRIPTION, NAME)
VALUES
(1, true, 'First operator', 'Transporter');

INSERT INTO OPERATOR
(OPERATOR_ID, IS_ACTIVE, DESCRIPTION, NAME)
VALUES
(2, true, 'Second operator', 'Operator');

INSERT INTO PASSENGER
(PASSENGER_ID, IS_ACTIVE, EMAIL, IS_SHADOW, NAME, PASSWORD)
VALUES
(1, true, 'first@email.com', false, 'John Doe', 'q1qq1qwe');

INSERT INTO PASSENGER
(PASSENGER_ID, IS_ACTIVE, EMAIL, IS_SHADOW, NAME, PASSWORD)
VALUES
(2, true, 'second@email.com', true, 'John Smith', 'kljk34lj43kj');

INSERT INTO PASSENGER_PMETHODS
(PASSENGER_ID, DESCRIPTION, LOCAL_DATE, IDENTIFIER, IS_PRIMARY)
VALUES
(1, 'first payment method', '2015-12-03', 'Identifier1', true);

INSERT INTO PASSENGER_PMETHODS
(PASSENGER_ID, DESCRIPTION, LOCAL_DATE, IDENTIFIER, IS_PRIMARY)
VALUES
(1, 'second payment method', '2016-12-03', '1265434563446', false);

INSERT INTO PASSENGER_PMETHODS
(PASSENGER_ID, DESCRIPTION, LOCAL_DATE, IDENTIFIER, IS_PRIMARY)
VALUES
(2, 'first payment method', '2014-6-03', '23423142314', true);

INSERT INTO PASSENGER_TOKEN
(PASSENGER_ID, TAG_ID)
VALUES
(1, 1);

INSERT INTO PASSENGER_TOKEN
(PASSENGER_ID, TAG_ID)
VALUES
(2, 2);
