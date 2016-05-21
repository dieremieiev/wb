/*******************************************************************************
 * Main
 ******************************************************************************/

"use strict";

function AppController(scope, dialog, tm)
{
  this.scope       = scope
  this.dialog      = dialog
  this.tm          = tm
  this.lastRequest = null
  this.api         = null

  scope.model      = {}
  scope.ml         = this.getML()

  this.showLoading(true)
}

AppController.prototype.init = function()
{
  this.api = new WBService()

  var self = this

  this.api.init(function(b) {
    self.showLoading(false)
    if (b) { self.loadUserState() }
  })
}


/*******************************************************************************
 * Public
 ******************************************************************************/

AppController.prototype.askLogout = function()
{
  var ml = this.scope.ml

  var panel = this.dialog.alert()
    .clickOutsideToClose(true)
    .htmlContent('<p>' + ml.txtSession + '</p>' + this.scope.model.email)
    .ok(ml.btnExit)

  var self = this

  this.dialog
    .show(panel)
    .finally(function() {
      panel = undefined
      self.updateView()
    })
    .then(function(b) {
      if (b) { self.logout() }
    })

  this.tm(function() { AppController.prototype.setLogoutDialogPosition() })
}

AppController.prototype.checkWord = function()
{
  this.checkWordImpl(this.scope.model.word.spelling)
}

AppController.prototype.login = function()
{
  if (this.api == null) { return }

  var self = this

  this.api.login(false, function(b) {
    if (b) { self.loadUserState() }
  })
}

AppController.prototype.nextWord = function()
{
  var m = this.scope.model

  m.evaluation = null

  m.word = m.nextWord

  this.updateView()
}

AppController.prototype.onKeyPress = function(event)
{
  if (event.keyCode == 13) { this.checkWord() }
}

AppController.prototype.repeatRequest = function()
{
  var oa = this.lastRequest

  if ( oa != null ) { this.post(oa[0], oa[1], oa[2]) }
}

AppController.prototype.selectDictionary = function()
{
  var data = { 'dictionaryId': this.scope.model.dictionary.id }

  var self = this

  var m = this.scope.model

  m.evaluation     = null
  m.showDictionary = false
  m.showError      = false

  this.post('selectDictionary', data, function(response) {
    self.handleSelectDictionary(response)
  })
}

AppController.prototype.suggestWord = function()
{
  this.checkWordImpl(null)
}


/*******************************************************************************
 * Private
 ******************************************************************************/

AppController.prototype.checkWordImpl = function(spelling)
{
  var m = this.scope.model

  var data = {
    'dictionaryId': m.dictionary.id,
    'wordId'      : m.word.id,
    'spelling'    : spelling
  }

  var self = this

  this.post('checkWord', data, function(response) {
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
    'txtAppInfo'         : 'Проект WB: учим слова нидерландского языка.',
    'txtCorrect'         : 'Правильно!',
    'txtCorrectVariant'  : 'Правильный вариант:',
    'txtLoading'         : 'Обработка запроса...',
    'txtNoDictionary'    : 'На данный момент словарь не выбран.',
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
    if (this.isEvaluationValid(response)) {
      m.evaluation = response.body.evaluation
      m.nextWord   = response.body.word
    }

    m.dictionary = response.body.dictionary

    m.showDictionary = true
    m.showError      = false
  } else {
    m.showDictionary = false
    m.showError      = true
  }
}

AppController.prototype.handleGetUserState = function(response)
{
  var m = this.scope.model

  if (this.isUserStateValid(response)) {
    var bD = this.isDictionaryValid(response)
    var bW = this.isWordValid(response)

    m.dictionaries = response.body.dictionaries
    m.dictionary   = bD ? response.body.dictionary : null
    m.word         = bW ? response.body.word       : null
    m.email        = response.body.email

    m.showDictionary = bD
    m.showError      = false
  } else {
    m.showDictionary = false
    m.showError      = true
  }
}

AppController.prototype.handleSelectDictionary = function(response)
{
  var m = this.scope.model

  if (this.isDictionaryValid(response)) {
    m.dictionary     = response.body.dictionary
    m.word           = this.isWordValid(response) ? response.body.word : null

    m.showDictionary = true
    m.showError      = false
  } else {
    m.showDictionary = false
    m.showError      = true
  }
}

AppController.prototype.isDictionaryValid = function(response)
{
  return response && (response.result == 0) && response.body
                  && response.body.dictionary
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

AppController.prototype.isWordValid = function(response)
{
  return response && (response.result == 0) && response.body
                  && response.body.word
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
  if (this.api == null) { return }

  var m = this.scope.model

  m.showDictionary = false
  m.showError      = false

  var self = this

  this.showLoading(true)

  this.tm(function() {
    self.scope.model = {}
    self.api.logout()
  })
}

AppController.prototype.post = function(action, data, callback)
{
  if (this.api == null) {
    collback(null)
    return
  }

  this.lastRequest = [ action, data, callback ]

  var self = this

  this.showLoading(true)

  this.api.post(action, data, function(response) {
    callback(response ? response.result : null)
    self.showLoading(false)
  });
}

AppController.prototype.setLogoutDialogPosition = function()
{
  var oa = document.getElementsByTagName('md-dialog')
  if ( !oa ) { return }

  var o = oa[0]
  var w = window.innerWidth - o.offsetWidth - 4

  o.style.position = 'fixed'
  o.style.top      = '2em'
  o.style.left     = w + 'px'
}

AppController.prototype.showLoading = function(b)
{
  this.scope.model.loading = b

  this.updateView()
}

AppController.prototype.updateView = function()
{
  var self = this

  this.tm(function() {
    if (self.scope.model.showDictionary) {
      self.tm(function() {
        document.getElementById('spelling').focus()
      }, 0, false)
    }
  })
}
