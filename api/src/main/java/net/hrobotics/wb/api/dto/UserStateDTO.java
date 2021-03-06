package net.hrobotics.wb.api.dto;

import java.util.List;

/**
 *  {
 *      'result': 0,
 *      'body': {
 *          'dictionary': {
 *              'id': '4a0c7667-d971-45d0-8309-0e475a063164',
 *              'active': 2,
 *              'learned': 4,
 *              'total': 100,
 *              'word': {
 *                  'id': 'bdfee856-471a-42d8-abc2-3e3a4de1ca6d',
 *                  'tip': 'такое же написание используется еще в значении "один"',
 *                  'translation': 'неопределенный артикль;один'
 *              }
 *          },
 *          'dictionaries': [
 *              { 'id': '5f6b2f41-d144-42aa-8a18-5a9e5e344f04', 'name': 'Словарь: 100 основных слов' },
 *              { 'id': '8a6d2093-57fb-41be-9517-fb4cad8229c2', 'name': 'Словарь: Числа' },
 *              { 'id': '4a0c7667-d971-45d0-8309-0e475a063164', 'name': 'Словарь: Дом' },
 *              { 'id': '6eff9378-beb1-4527-b8c4-9354396a2c32', 'name': 'Словарь: Транспорт' }
 *          ]
 *      }
 *  }
 */
public class UserStateDTO {
    private CurrentDictionaryDTO dictionary;
    private WordDTO word;
    private List<DictionaryDTO> dictionaries;
    private String email;

    public UserStateDTO(CurrentDictionaryDTO dictionary,
                        List<DictionaryDTO> dictionaries,
                        String email,
                        WordDTO word) {
        this.dictionary = dictionary;
        this.dictionaries = dictionaries;
        this.email = email;
        this.word = word;
    }

    public UserStateDTO() {
    }

    public CurrentDictionaryDTO getDictionary() {
        return dictionary;
    }

    public void setDictionary(CurrentDictionaryDTO dictionary) {
        this.dictionary = dictionary;
    }

    public List<DictionaryDTO> getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(List<DictionaryDTO> dictionaries) {
        this.dictionaries = dictionaries;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public WordDTO getWord() {
        return word;
    }

    public void setWord(WordDTO word) {
        this.word = word;
    }
}
