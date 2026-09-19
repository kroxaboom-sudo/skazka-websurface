# Skazka WebSurface

> RU — основной язык · EN — required second language

## RU

Небольшая Android-библиотека для единообразного открытия пользовательских HTTPS-страниц через Custom Tabs.

Модуль вынесен из Skazka Hub и больше не зависит от его `MainActivity`. Он намеренно не содержит WebView fallback и не открывает обычный браузер молча, если Custom Tabs недоступны.

**Статус:** `0.1.0-preview` — самостоятельная библиотека. Policy-test и `:websurface:assembleDebug` проверены на HOSTKEY.

## EN

A small Android library for consistent user-facing HTTPS navigation through Custom Tabs.

The module was extracted from Skazka Hub and no longer depends on its `MainActivity`. It intentionally has no WebView fallback and does not silently open a standalone browser when Custom Tabs are unavailable.

**Status:** `0.1.0-preview` — standalone library. The policy test and `:websurface:assembleDebug` have been verified on HOSTKEY.

## Module

- coordinates: `com.kroxaboom.skazka:skazka-websurface:0.1.0-preview`
- namespace: `com.kroxaboom.skazka.websurface`
- minSdk: 33
- compileSdk: 35
- Java: 17
- external runtime dependencies: none

## Verification / Проверка

```bash
npm test
gradle :websurface:assembleDebug
```

See [docs/POLICY.md](docs/POLICY.md) and [DEVELOPMENT_RULES.md](DEVELOPMENT_RULES.md).

> A license will be selected before the first stable public release. Until then, publication of the source does not grant reuse rights.
