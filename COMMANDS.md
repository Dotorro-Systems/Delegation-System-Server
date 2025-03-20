Do uruchomienia serwera używamy poleceń
```
// Jeśli chcemy hostować serwer lokalnie na naszym systemie, używamy
// należy pamiętać, aby mieć włączoną bazę danych
// zmienna HOST w .env powinna być ustawiona na jdbc:postgresql://localhost:5432/Delegation-System !
./gradlew bootRun

// Jeśli chcemy hostować serwer w kontenerze dockera, używamy
// zmienna HOST w .env powinna być ustawiona na jdbc:postgresql://postgres:5432/Delegation-System !
docker compose up         (jeśli mamy wybudowane obrazy i od tamtej pory nie poczyniliśmy zmian)
docker compose up --build (jeśli wymagane jest wybudowanie obrazów ponownie)
```