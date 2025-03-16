Do uruchomienia serwera używamy poleceń
```
// Jeśli chcemy hostować serwer lokalnie na naszym systemie, używamy
// ! (odradzane, ponieważ nie tworzy to bazy danych) !
./gradlew bootRun

// Jeśli chcemy hostować serwer w kontenerze dockera, używamy
// ! (zalecane, tworzy dwa kontenery, gdzie jeden z nich to baza danych) !
docker compose up --build
```