DROP TABLE IF EXISTS securities_info;
DROP TABLE IF EXISTS securities_history;

CREATE TABLE securities_info (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  secid INT,
  regnumber INT,
  name VARCHAR(250),
  emitent_title VARCHAR(250)
);

CREATE TABLE securities_history (
  secid INT AUTO_INCREMENT  PRIMARY KEY,
  tradedate DATE,
  numtrades INT,
  open BOOLEAN
);

INSERT INTO securities_info (secid, regnumber, name,emitent_title) VALUES
    (1,911,"One","WB"),
    (2,822,"Two","Disney"),
    (3,722,"Three","Marvel");

INSERT INTO securities_history (secid,tradedate,numtrades,open) VALUES
    (1, "2021.01.23", 3,TRUE),
    (2, "2021.02.28", 1,FALSE),
    (3, "2021.03.13", 4,FALSE);