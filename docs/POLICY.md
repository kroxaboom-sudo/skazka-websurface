# Web Surface Policy / Политика веб-переходов

## RU

1. Обычные пользовательские HTTPS-страницы открываются через `SkazkaWebSurface`.
2. Модуль принимает только `https://` URL с валидным host.
3. Произвольные внешние страницы не встраиваются в WebView.
4. Если Custom Tabs недоступны, вызывающий код получает явную ошибку. Скрытого fallback в отдельный браузер нет.
5. Системные действия вроде Android Settings, установщика APK и выбора файлов не относятся к этому модулю.

## EN

1. Ordinary user-facing HTTPS pages open through `SkazkaWebSurface`.
2. The module accepts only `https://` URLs with a valid host.
3. Arbitrary external pages are not embedded in a WebView.
4. If Custom Tabs are unavailable, the caller gets an explicit error. There is no silent fallback to a standalone browser.
5. System actions such as Android Settings, APK installation, and file picking are outside this module.
