/*****************************************************************************
 * Lifecycle
 ****************************************************************************/

function AppController(scope, dialog)
{
  this.scope       = scope
  this.dialog      = dialog
  this.lastRequest = null
  this.sim         = new Simulator()

  this.initScope(scope)
}

AppController.prototype.initScope = function(scope)
{
  scope.model = {}
  scope.ml = this.getML()

  var self = this

  scope.askLogout        = function() { self.askLogout() }
  scope.checkWord        = function() { self.checkWord() }
  scope.repeatRequest    = function() { self.repeatRequest() }
  scope.selectDictionary = function() { self.selectDictionary() }
}


/*****************************************************************************
 * Public
 ****************************************************************************/

AppController.prototype.askLogout = function()
{
  var ml = this.scope.ml

  var dialog = this.dialog.alert()
    .clickOutsideToClose(true)
    .htmlContent('<p>' + ml.txtSession + '</p>' + this.scope.model.email)
    .ok(ml.btnExit)

  var self = this

  this.dialog
    .show(dialog)
    .finally(function() { dialog = undefined })
    .then(function(b) {
      if (b) { self.logout() }
    })

  setTimeout(function() { self.setLogoutDialogPosition() }, 0)
}

AppController.prototype.checkWord = function()
{
  var dict = this.scope.model.dictionary

  var data = { 'dictionary': {
    'id': dict.id,
    'word': {
      'id': dict.word.id,
      'spelling': dict.word.spelling
    }
  }}

  var self = this

  this.post('checkWord', null, function(response) {
    self.handleCheckWord(response)
  })
}

AppController.prototype.loadUserState = function()
{
  var self = this

  this.post('getUserState', null, function(response) {
      self.handleGetUserState(response)
  })
}

AppController.prototype.repeatRequest = function()
{
  var oa = this.lastRequest

  if ( oa != null ) {
    this.post(oa[0], oa[1], oa[2])
  }
}

AppController.prototype.selectDictionary = function()
{
  var data = { 'dictionary': { 'id': this.scope.model.dictionary.id } }

  var self = this

  var m = this.scope.model

  m.showDictionary = false
  m.showError = false

  this.post('selectDictionary', data, function(response) {
    self.handleSelectDictionary(response)
  })
}


/*****************************************************************************
 * Private
 ****************************************************************************/

AppController.prototype.getML = function()
{
  return {
    'btnExit'            : 'Выход',
    'btnRepeat'          : 'Повторить',
    'btnCkeckWord'       : 'Проверить слово',
    'errResponse'        : 'Ошибка запроса к серверу. Пожалуйста повторите запрос позже.',
    'errSpelling'        : 'Пожалуйста введите перевод слова',
    'tipExit'            : 'Выход',
    'tipRepeat'          : 'Повторить последний запрос к серверу',
    'txtLoading'         : 'Обработка запроса...',
    'txtSession'         : 'Текущая сессия:',
    'txtSelectDictionary': 'Выберите словарь',
    'txtTip'             : 'Совет:',
    'txtWordsLearned'    : 'выучено слов:',
    'txtWordsTotal'      : 'Слов в словаре:',
    'txtWordsWait'       : 'ожидают повторения:'
  }
}

AppController.prototype.handleCheckWord = function(response)
{
  var m = this.scope.model

  if (this.isDictionaryValid(response)) {
    m.dictionary = response.body.dictionary
    m.showDictionary = true
    m.showError = false
  } else {
    m.showDictionary = false
    m.showError = true
  }

  this.updateView()
}

AppController.prototype.handleGetUserState = function(response)
{
  var m = this.scope.model

  if (this.isUserStateValid(response)) {
    m.dictionaries = response.body.dictionaries
    m.dictionary   = this.isDictionaryValid(response)
                   ? response.body.dictionary : null
    m.email = response.body.email
    m.showDictionary = true
    m.showError = false
  } else {
    m.showDictionary = false
    m.showError = true
  }

  this.updateView()
}

AppController.prototype.handleLogout = function(response)
{
  location.reload()
}

AppController.prototype.handleSelectDictionary = function(response)
{
  var m = this.scope.model

  if (this.isDictionaryValid(response)) {
    m.dictionary = response.body.dictionary
    m.showDictionary = true
    m.showError = false
  } else {
    m.showDictionary = false
    m.showError = true
  }

  this.updateView()
}

AppController.prototype.isDictionaryValid = function(response)
{
  return response && (response.result == 0) && response.body
                  && response.body.dictionary
                  && response.body.dictionary.word
}

AppController.prototype.isUserStateValid = function(response)
{
  return response && (response.result == 0) && response.body
                  && response.body.dictionaries
                  && response.body.email
}

AppController.prototype.logout = function()
{
  var self = this

  var m = this.scope.model

  m.showDictionary = false
  m.showError = false

  this.post('logout', null, function(response) {
      self.handleLogout(response)
  })
}

AppController.prototype.post = function(action, data, callback)
{
  this.lastRequest = [ action, data, callback ]

  var self = this

  this.scope.model.loading = true

  var f = function(response) {
    self.scope.model.loading = false
    callback(response)
  }

  this.sim.post(action, data, f);
}

AppController.prototype.setLogoutDialogPosition = function()
{
  var oa = document.getElementsByTagName('md-dialog')
  if ( !oa ) { return }

  var o = oa[0]
  var w = window.innerWidth - o.offsetWidth - 4

  o.style.position = 'fixed'
  o.style.top = '2em'
  o.style.left = w + 'px'
}

AppController.prototype.updateView = function()
{
    this.scope.$apply()
}
