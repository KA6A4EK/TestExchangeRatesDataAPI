## TestExchangeRatesDataAPI

Приложение для просмотра актуальных курсов валют на базе **Exchange Rates Data API (apilayer)**. Написано на **Kotlin** с использованием **Jetpack Compose**, **Hilt**, **Retrofit**, **Room** и **DataStore**.

### Основные возможности

- **Список курсов валют**: отображение актуальных курсов относительно выбранной базовой валюты (по умолчанию `EUR`).
- **Выбор базовой валюты**: смена базовой валюты через выпадающий список.
- **Сортировка**: экран фильтров позволяет сортировать список:
  - по коду валюты (A–Z / Z–A);
  - по курсу (по возрастанию / по убыванию).
- **Избранные валютные пары**:
  - добавление/удаление пар в избранное;
  - отдельный экран избранных валют с сохранением в локальную БД.
- **Сохранение настроек**:
  - выбор типа сортировки и базовой валюты сохраняется через DataStore и применяется при следующем запуске.
- **Навигация по нижней панели**:
  - `Currencies` — список курсов;
  - `Favorites` — избранные пары;
  - `Filter` — экран выбора типа сортировки.

### Архитектура

- **Слоистая структура**:
  - `data` — работа с сетью (`ExchangeRatesApiService`), локальной БД (`Room`), DataStore и реализация репозиториев;
  - `domain` — доменные модели, интерфейсы репозиториев и use case’ы;
  - `presentation` — экраны Jetpack Compose, `ViewModel` и UI-состояния.
- **DI / Hilt**:
  - `NetworkModule` — настройка `OkHttp`, `Retrofit`, конвертеров и `ExchangeRatesApiService`;
  - `DatabaseModule` — создание экземпляра `CurrencyDatabase` и DAO;
  - `RepositoryModule` — биндинги `CurrencyRepository` и `SettingsRepository`;
  - `UseCaseModule` — предоставление use case’ов доменного слоя.
- **Use case’ы (пример)**:
  - `GetRatesUseCase`, `SortRatesUseCase`, `ToggleFavoriteUseCase`, `GetFavoritesUseCase`,
  - `ApplySortingUseCase`, `GetSortSettingsUseCase`, `UpdateLastRefreshTimeUseCase` и др.
- **Навигация**:
  - `CurrencyTrackerNavGraph` + `NavRoute` (sealed class);
  - корневой composable `CurrencyTrackerRoot` со `Scaffold` и нижней навигацией.

### Технологии и библиотеки

- **Язык и UI**:
  - Kotlin
  - Jetpack Compose (Material 3)
- **DI**:
  - Hilt
- **Сеть**:
  - Retrofit
  - OkHttp + `HttpLoggingInterceptor`
  - Gson (основной конвертер), Moshi (подготовлен, частично закомментирован)
- **Локальное хранение**:
  - Room (`CurrencyDatabase`, `FavoriteCurrencyPairDao`, сущности для избранных пар)
  - DataStore (для сохранения настроек сортировки и базовой валюты)
- **Архитектурные подходы**:
  - Чёткое разделение на `data / domain / presentation`
  - Use case-ориентированный доменный слой
  - StateFlow/UiState для управления состоянием экранов

### Настройка и запуск

- **Требования**:
  - Android Studio (Arctic Fox / Flamingo и новее);
  - JDK 11;
  - Android SDK с API level не ниже 28 (minSdk = 28, targetSdk = 36).

1. **Клонируйте репозиторий**:

   ```bash
   git clone <url-репозитория>
   cd TestExchangeRatesDataAPI
   ```

2. **Откройте проект в Android Studio**:
   - выберите корневую папку проекта;
   - дождитесь завершения Gradle sync.

3. **Сборка и запуск**:
   - выберите конфигурацию `app`;
   - запустите на эмуляторе или реальном устройстве (minSdk 28).

### API‑ключ и безопасность

В проекте API‑ключ для `Exchange Rates Data API` передаётся через `BuildConfig` и файл `local.properties`:

- в `app/build.gradle.kts` ключ читается из `local.properties` (свойство `EXCHANGE_RATES_API_KEY`) и пробрасывается в `BuildConfig.EXCHANGE_RATES_API_KEY`;
- в `NetworkModule` он используется в заголовке `apikey` при настройке `OkHttpClient`.

Чтобы приложение работало, в корне проекта должен быть файл `local.properties` (обычно он уже есть и не коммитится в репозиторий) со строкой:

```properties
EXCHANGE_RATES_API_KEY=ВАШ_КЛЮЧ_ОТ_APILAYER
```

**Важно**:

- не коммитьте реальные ключи в VCS (GitHub и т.п.);
- при необходимости используйте дополнительные механизмы защиты (Secrets Gradle Plugin, CI‑секреты и т.д.).

### Структура пакетов (кратко)

- `com.example.testexchangeratesdataapi`
  - `data.remote` — Retrofit API и DTO (`ExchangeRatesApiService`, `ExchangeRatesResponseDto`);
  - `data.local` — Room (`CurrencyDatabase`, DAO, сущности избранных пар) и DataStore;
  - `data.repository` — реализации репозиториев (курсы и настройки);
  - `domain.model` — доменные модели (`CurrencyRate`, `FavoriteCurrencyPair`, `SortType` и др.);
  - `domain.repository` — интерфейсы репозиториев;
  - `domain.usecase` — бизнес‑логика в отдельных use case классах;
  - `presentation.currencies / favorites / filter` — экраны, ViewModel’и и UI‑состояния;
  - `presentation.common` — общие компоненты UI (верхний/нижний бар, элементы списка, корневой `CurrencyTrackerRoot`);
  - `di` — Hilt‑модули;
  - `ui.theme` — тема оформления Compose.

### Краткая оценка кода

- **Сильные стороны**:
  - хорошее разделение по слоям (data / domain / presentation);
  - использование современных инструментов: Hilt, Room, DataStore, Jetpack Compose;
  - навигация через Compose Navigation, понятные `NavRoute`;
  - `ViewModel` используют `StateFlow` и явные UiState‑классы.


В целом проект выглядит аккуратным, с современной архитектурой и хорошим разделением ответственности между слоями.

