# Model

## User
1. id
2. email
id => email

## Dictionary
1. id
2. name
3. caption
4. language from
5. language to
6. version
id => {}

## Level
1. id
2. number
3. delay
dictionary.id/level.id => {}

## Word
1. id
2. spelling
3. translation
dictionary.id/word.id => spelling, translation

## User dictionary
1. id
2. name
3. caption
4. language from
5. language to
6. version
8. num words
9. num shown words
user.id/dictionary.id => {}

## User level
1. id
2. number
3. delay
4. num words
user.id/dictionary.id/level.id => {}


## User Word
1. id
2. spelling
3. translation
4. level
5. next show date
user.id/dictionary.id/word.id => {}


## Checks log
1. timestamp
2. user
3. dictionary
4. word
5. check_result
timestamp/user.id/dictionary.id/word.id => check_result


When user starts new dictionary, User dictionary, User level and User word are copied from appropriate Dictionary, Level and Word


