/*****************************************************************************
 * Lifecycle
 ****************************************************************************/

function AppController()
{
  this.scope       = null
  this.dialog      = null
  this.lastRequest = null
  this.sim         = null
}

AppController.prototype.init = function(scope, dialog)
{
  scope.model = {}
  scope.ml    = this.getML()

  this.scope  = scope
  this.dialog = dialog
  this.sim    = new Simulator()

  // TODO - implement ENTER

  // var self = this
  // scope.onKeyDown = function(event) {
  //   if (event.keyCode == 13) { self.onEnter() }
  // }
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
  this.checkWordImpl(this.scope.model.dictionary.word.spelling)
}

AppController.prototype.login = function()
{
  var self = this

  this.post('login', null, function(response) {
    self.handleLogin(response)
  })
}

AppController.prototype.nextWord = function()
{
  var m = this.scope.model

  m.evaluation = null

  m.dictionary.word = m.nextWord

  var self = this
  setTimeout(function() { self.updateView() }, 0)
}

AppController.prototype.repeatRequest = function()
{
  var oa = this.lastRequest

  if ( oa != null ) { this.post(oa[0], oa[1], oa[2]) }
}

AppController.prototype.selectDictionary = function()
{
  var data = { 'dictionary': { 'id': this.scope.model.dictionary.id } }

  var self = this

  var m = this.scope.model

  m.evaluation = null
  m.showDictionary = false
  m.showError = false

  this.post('selectDictionary', data, function(response) {
    self.handleSelectDictionary(response)
  })
}

AppController.prototype.suggestWord = function()
{
  this.checkWordImpl(null)
}


/*****************************************************************************
 * Private
 ****************************************************************************/

AppController.prototype.checkWordImpl = function(spelling)
{
  var dict = this.scope.model.dictionary

  var data = { 'dictionary': {
    'id': dict.id,
    'word': {
      'id': dict.word.id,
      'spelling': spelling
    }
  }}

  var self = this

  this.post('checkWord', null, function(response) {
    self.handleCheckWord(response)
  })
}

AppController.prototype.getML = function()
{
  return {
    'btnContinue'        : 'Продолжить',
    'btnExit'            : 'Выход',
    'btnLogin'           : 'Войти',
    'btnRepeat'          : 'Повторить',
    'btnSuggestWord'     : 'Подсказка',
    'btnCkeckWord'       : 'Проверить',
    'errResponse'        : 'Ошибка запроса к серверу. Пожалуйста повторите запрос позже.',
    'tipExit'            : 'Выход',
    'tipLogin'           : 'Авторизация через Google аккаунт',
    'tipRepeat'          : 'Повторить последний запрос к серверу',
    'txtAppInfo'         : 'Проект WB: учим слова нидерландского языка',
    'txtCorrect'         : 'Правильно!',
    'txtCorrectVariant'  : 'Правильный вариант:',
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
    var w = m.dictionary.word

    if (this.isEvaluationValid(response)) {
      m.evaluation = response.body.evaluation
      m.nextWord = response.body.dictionary.word
    }

    m.dictionary = response.body.dictionary

    if (w) { m.dictionary.word = w }

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

AppController.prototype.handleLogin = function(response)
{
  this.loadUserState() // TODO
}

AppController.prototype.handleLogout = function(response)
{
  this.scope.model = {}

  this.updateView()
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

AppController.prototype.isEvaluationValid = function(response)
{
  return response && response.body && response.body.evaluation
                  && response.body.evaluation.spelling
}

AppController.prototype.isUserStateValid = function(response)
{
  return response && (response.result == 0) && response.body
                  && response.body.dictionaries
                  && response.body.email
}

AppController.prototype.loadUserState = function()
{
  var self = this

  this.post('getUserState', null, function(response) {
      self.handleGetUserState(response)
  })
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

  if (this.scope.model.showDictionary) {
    document.getElementById('spelling').focus()
  }
}
