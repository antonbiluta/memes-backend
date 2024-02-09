create table if not exists memes (
    id SERIAL PRIMARY KEY,
    chat_id BIGINT NOT NULL,
    user_id INT NOT NULL,
    author TEXT,
    media BYTEA NOT NULL,
    chat_prefix TEXT,
    media_type TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
)