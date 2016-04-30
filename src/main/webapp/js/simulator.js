function Simulator() {
  this.response = null
}

Simulator.prototype.handle = function(request) {
  if (request == null) { return null }

  switch (request.action) {
      case 'checkWord'       : return this.handleCheckWord()
      case 'getUserState'    : return this.handleGetUserState()
      case 'selectDictionary': return this.handleSelectDictionary()
  }

  return null
}

Simulator.prototype.handleCheckWord = function() {
  return {
    'result': 0,
    'body': {
      'dictionary': {
        'id': '8a6d2093-57fb-41be-9517-fb4cad8229c2',
        'active': 1,
        'learned': 4,
        'total': 100,
        'word': {
          'id': 'bdfee856-471a-42d8-abc2-3e3a4de1ca6d',
          'tip': 'такое же написание используется еще в значении "один"',
          'translation': 'неопределенный артикль;один'
        }
      },
      'evaluation': {
          'result': 0,
          'spelling': 'xxx'
      }
    }
  }
}

Simulator.prototype.handleGetUserState = function() {
  return {
    'result': 0,
    'body': {
      'dictionary': {
        'id': '4a0c7667-d971-45d0-8309-0e475a063164',
        'active': 2,
        'learned': 4,
        'total': 100,
        'word': {
          'id': 'bdfee856-471a-42d8-abc2-3e3a4de1ca6d',
          'tip': 'такое же написание используется еще в значении "один"',
          'translation': 'неопределенный артикль;один'
        }
      },
      'dictionaries': [
        { 'id': '5f6b2f41-d144-42aa-8a18-5a9e5e344f04', 'name': 'Словарь: 100 основных слов' },
        { 'id': '8a6d2093-57fb-41be-9517-fb4cad8229c2', 'name': 'Словарь: Числа' },
        { 'id': '4a0c7667-d971-45d0-8309-0e475a063164', 'name': 'Словарь: Дом' },
        { 'id': '6eff9378-beb1-4527-b8c4-9354396a2c32', 'name': 'Словарь: Транспорт' }
      ]
    }
  }
}

Simulator.prototype.handleSelectDictionary = function() {
  return {
    'result': 0,
    'body': {
      'dictionary': {
        'id': '8a6d2093-57fb-41be-9517-fb4cad8229c2',
        'active': 20,
        'learned': 40,
        'total': 1000,
        'word': {
          'id': 'bdfee856-471a-42d8-abc2-3e3a4de1ca6d',
          'tip': 'такое же написание используется еще в значении "один"',
          'translation': 'неопределенный артикль;один'
        }
      }
    }
  }
}

Simulator.prototype.post = function(url, request) {
  this.response = this.handle(request)

  return this
}

Simulator.prototype.then = function(success, error) {
  var self = this;

  setTimeout(function() {
    if (self.response) {
      success({ data: self.response })
    } else {
      error({ data: self.response })
    }
  }, 5000)
}
