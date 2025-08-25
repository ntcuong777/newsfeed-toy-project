CREATE TABLE media
(name text PRIMARY KEY,
 owner text references users(login),
 type text NOT NULL,
 data bytea NOT NULL);