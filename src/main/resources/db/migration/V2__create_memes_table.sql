create table if not exists memes (
    id SERIAL primary key,
    user_id bigint NOT NULL,
    chat_id bigint NOT NULL,
    media_path text NOT NULL,
    media_type text NOT NULL,
    created_at timestamp without time zone NOT NULL,
    CONSTRAINT chat_id_foreign_key FOREIGN KEY (chat_id)
    REFERENCES chat_settings (chat_id)
)