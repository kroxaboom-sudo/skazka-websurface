# Skazka WebSurface

**RU:** Переиспользуемый web-контур Skazka и основа будущего браузерного клиента.

**EN:** Reusable Skazka web surface and a foundation for a future browser client.

## Что здесь будет / What belongs here

- жизненный цикл WebView / web surface;
- навигация;
- безопасная обработка внутренних и внешних ссылок;
- точки интеграции с Auth и Download;
- phone/tablet layouts;
- portrait/landscape behaviour;
- восстановление состояния страницы.

## Граница / Boundary

WebSurface не должен знать о конкретном контентном сайте. Source-specific правила остаются в Source SDK или приложении.

## Статус / Status

Существующие наработки SkazkaWebSurface будут выноситься сюда после очистки зависимостей от Skazka Hub.
