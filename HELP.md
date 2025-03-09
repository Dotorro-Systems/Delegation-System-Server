# Aby lepiej się wspólnie pracowało

## 1. Pracę rozpoczynamy poprzez zpullowanie najnowszych zmian
```
// Pobiera najnowsze zmiany do katalogu roboczego
git pull
```

## 2. Pracę nad nowym taskiem zaczynamy zawsze na nowym branchu
```
// Upewniamy się, że jesteśmy na głównym branchu
git checkout main

// Pobieramy najnowszą wersję głównego brancha
git pull

// Tworzymy nowego brancha poprzez kopię aktualnego (branche nazywamy podobnie jak nasz task, każde słowo oddzielone '-' oraz rozpoczęte dużą literą, np. Delegation-Entity)
git branch -b <nazwa-brancha>
```

## 3. Pracę zawsze kończymy commitując najnowsze zmiany aby zawsze w repozytorium znajdował się najświeższy kod
```
// Sprawdzamy status naszego repozytorium
git status

// Dodajemy wszystkie (lub nie) zmiany w plikach
git add -A

// Tworzymy commita
git commit -m <nazwa-commita>
```

## 4. Do nazw commitów stosujemy zasady
1. Pierwszy człon to bezokolicznikowy czasownik np. `Add`, `Fix`, `Improve`, `Refactor`
2. Dwukropek `:`
3. Zmiany jakie poczyniliśmy oddzielane przecinkami np. `delegation entity, expenses service`
4. Jeśli chcemy zawrzeć więcej czasowników, oddzielamy je średnikami `;`

Przykład:
`"Add: delegation and notes entity, expenses service; Refactor: user password hashing algorithm`

P.S. Jeśli wyjdzie wam tak długa nazwa, to na przyszłość commitujcie częściej z mniejszą ilością zmian