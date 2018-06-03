CREATE TABLE t_users (
  username VARCHAR2(50)  NOT NULL UNIQUE,
  password VARCHAR2(100) NOT NULL,
  enabled  CHAR(1)       NOT NULL,
  first_name VARCHAR2(50),
  last_name VARCHAR2(50),
  CONSTRAINT enabled_ck CHECK enabled in ('0','1')
);

CREATE TABLE t_user_authorities (
  auth_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY ,
  username  VARCHAR2(50)  NOT NULL,
  authority VARCHAR2(100) NOT NULL,
  CONSTRAINT uname_fk FOREIGN KEY (username) REFERENCES t_users (username),
  CONSTRAINT uname_auth_unique UNIQUE (username, authority)
);

INSERT INTO t_users
VALUES ('ALEX_ADMIN', '$2a$10$E15ih8eCS4xGchLEUuXzWObWM2soirZczW.a2TGzjslt6ZrOUtcyC', '1','ALEX', 'ADMIN');

INSERT INTO t_user_authorities(username, authority)
VALUES ('ALEX_ADMIN', 'ROLE_ADMIN');
INSERT INTO t_user_authorities(username, authority)
VALUES ('ALEX_ADMIN', 'ROLE_USER');

SELECT * from t_users NATURAL JOIN
    t_user_authorities;