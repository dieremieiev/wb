# Model

## UserState
1. id: string
2. email: string
3. dictionary: string
id => {}

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
2. dictionaryId: string
2. number: int
3. delay: int
dictionary.id/level.id => {}

## Word
1. id: string
2. dictionaryId: string
3. spelling: string
4. translation: string
5. tip: string
dictionary.id/word.id => spelling, translation, tip

## User dictionary
1. userId: string
2. dictionaryId: string
3. words: int
4. active: int
5. learned: int
5. nextWord: string 
user.id/dictionary.id => {}

## User Word
1. userId: string
2. dictionaryId: string
3. wordId: string
2. level: int
3. checkDate: date
user.id/dictionary.id/word.id => {}


## Checks log
1. timestamp: date
2. user: string
3. dictionary: string
4. word: string
5. check_result: string
timestamp/user.id/dictionary.id/word.id => check_result


When user starts new dictionary, User dictionary, User level and User word are copied from appropriate Dictionary, Level and Word