# OfficeMemes Backend
![GitHub License](https://img.shields.io/github/license/antonbiluta/memes-backend)
![Build Status](https://drone.biluta.ru/api/badges/antonbiluta/memes-backend/status.svg)
![Website](https://img.shields.io/website?url=https%3A%2F%2Fmem-api.biluta.ru%2Fswagger-ui%2Findex.html)
![Swagger Validator](https://img.shields.io/swagger/valid/3.0?specUrl=https%3A%2F%2Fmem-api.biluta.ru%2Fv3%2Fapi-docs%2FMemes-Service)
![Static Badge](https://img.shields.io/badge/backend_version-v0.1.0-8A2BE2)

## Project Structure
```mermaid
---
title: OfficeMemes Structure
---
graph TD
    subgraph System["HomeLab"]
        subgraph Container["Docker"]
            frontend("Frontend\nReact")
            backend{"Backend\nKotlin (Spring)"}
            telegram_bot("Telegram Bot\nPython (Aiogram)")
            storage[("Storage\nMinio S3")]
            database[("Database\nPostgreSQL")]

            frontend -->|API calls| backend
            telegram_bot -->|API calls| backend
            backend -->|Reads/Writes| storage
            backend -->|Reads/Writes| database
        end
    end

    %% Добавление пояснения "Вы здесь" для конкретного контейнера (например, для frontend)
    note{{"this repo"}} -.-> backend

    %% Стилизация
    style frontend fill:#333,stroke:#fff,stroke-width:2px,color:#fff
    style telegram_bot fill:#333,stroke:#fff,stroke-width:2px,color:#fff
    style backend fill:#319273,stroke:#fff,stroke-width:2px,color:#fff
    style storage fill:#333,stroke:#fff,stroke-width:2px,color:#fff
    style database fill:#333,stroke:#fff,stroke-width:2px,color:#fff
    style note fill:transparent,stroke:transparent
```
