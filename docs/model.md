# Model

## User
1. id: string
2. email: string
id => email

## Dictionary
1. id: string
2. name: string
3. caption: string
4. from: string
5. to: string
6. version: string
7. number: int
id => {}

## Level
1. id: string
2. number: int
3. delay: int
dictionary.id/level.id => {}

## Word
1. id: string
2. spelling: string
3. translation: string
4. tip
5. index
dictionary.id/word.id => spelling, translation, tip

## User dictionary
1. id: string
2. name: string
3. caption: string
4. language from: string
5. language to: string
6. version: string
8. num words: int
9. num shown words: int
user.id/dictionary.id => {}

## User level
1. id: string
2. number: string
3. delay: int
4. num words: int
user.id/dictionary.id/level.id => {}


## User Word
1. id: string
2. spelling: string
3. translation: string
4. level: int
5. next show date: date
user.id/dictionary.id/word.id => {}


## Checks log
1. timestamp: date
2. user: string
3. dictionary: string
4. word: string
5. check_result: string
timestamp/user.id/dictionary.id/word.id => check_result


When user starts new dictionary, User dictionary, User level and User word are copied from appropriate Dictionary, Level and Word