-- TODO
DROP TABLE "todo";
CREATE TABLE "todo"
(
  "id"              TEXT        NOT NULL,
  "user_id"         TEXT        NOT NULL,
  "content"         TEXT        NOT NULL,
  "created_at"      TIMESTAMPTZ NOT NULL
);
