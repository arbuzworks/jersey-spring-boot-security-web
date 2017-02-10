SET FOREIGN_KEY_CHECKS = 0;

DROP DATABASE IF EXISTS `demo`;
CREATE DATABASE IF NOT EXISTS `demo`;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE demo.`TRANSACTION` (
  id                BIGINT(20)   NOT NULL AUTO_INCREMENT,
  transaction_id    VARCHAR(255) NOT NULL UNIQUE,
  customer          VARCHAR(255) NOT NULL,
  amount            DOUBLE       NOT NULL,
  card_number       VARCHAR(255),
  cvv               VARCHAR(255),
  expiration_date   VARCHAR(255),
  routing_number    VARCHAR(255),
  account_number    VARCHAR(255),
  check_number      VARCHAR(255),
  status            VARCHAR(255),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS demo.`oauth_client_details`;

CREATE TABLE demo.`oauth_client_details` (
  client_id varchar(256) NOT NULL,
  resource_ids varchar(256) DEFAULT NULL,
  client_secret varchar(256) DEFAULT NULL,
  scope varchar(256) DEFAULT NULL,
  authorized_grant_types varchar(256) DEFAULT NULL,
  web_server_redirect_uri varchar(256) DEFAULT NULL,
  authorities varchar(256) DEFAULT NULL,
  access_token_validity int(11) DEFAULT NULL,
  refresh_token_validity int(11) DEFAULT NULL,
  additional_information varchar(4096) DEFAULT NULL,
  autoapprove varchar(4096) DEFAULT NULL,
  PRIMARY KEY (client_id)
);

INSERT INTO demo.`oauth_client_details`(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity)
VALUES ('test', 'demo_api', '$2a$11$diFS9cU4kYWfu7luoocRHOGrCMLGx1j5ZZEnJC1zd5zIM1xfeuk8y', 'trust,read,write', 'client_credentials,authorization_code,implicit,password,refresh_token', 'ROLE_USER', '4500', '45000');

DROP TABLE IF EXISTS demo.`oauth_access_token`;

CREATE TABLE demo.`oauth_access_token` (
  token_id varchar(256) DEFAULT NULL,
  token blob,
  authentication_id varchar(256) DEFAULT NULL,
  user_name varchar(256) DEFAULT NULL,
  client_id varchar(256) DEFAULT NULL,
  authentication blob,
  refresh_token varchar(256) DEFAULT NULL
);


DROP TABLE IF EXISTS demo.`oauth_refresh_token`;

CREATE TABLE demo.`oauth_refresh_token` (
  token_id varchar(256) DEFAULT NULL,
  token blob,
  authentication blob
);
