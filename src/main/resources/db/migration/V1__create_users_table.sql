-- Tabla de usuarios. UUID como clave primaria para compatibilidad con Supabase Auth
-- y para evitar exposición de IDs secuenciales.
CREATE TABLE IF NOT EXISTS users (
    id              UUID PRIMARY KEY,
    email           VARCHAR(255) NOT NULL UNIQUE,
    username        VARCHAR(30)  NOT NULL,
    password_hash   VARCHAR(255) NOT NULL,
    role            VARCHAR(20)  NOT NULL DEFAULT 'PLAYER',
    created_at      TIMESTAMP    NOT NULL,
    updated_at      TIMESTAMP    NOT NULL,
    CONSTRAINT chk_users_role CHECK (role IN ('PLAYER', 'COACH', 'ADMIN'))
);

CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

-- NOTA: en el Sprint 5 se habilitará Row Level Security (RLS) en esta tabla
-- para que cada usuario solo pueda acceder a sus propios datos directamente desde Supabase.