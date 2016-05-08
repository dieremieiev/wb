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
    'btnExit'        : 'Выход',
    'btnRepeat'      : 'Повторить',
    'btnCkeckWord'   : 'Проверить слово',
    'errResponse'    : 'Ошибка запроса к серверу. Пожалуйста повторите запрос позже.',
    'tipCheckWord'   : 'Запрос к серверу на проверку слова',
    'tipExit'        : 'Выход',
    'tipRepeat'      : 'Повторить последний запрос к серверу',
    'txtLoading'     : 'Обработка запроса...',
    'txtSession'     : 'Текущая сессия:',
    'txtSelectDict'  : 'Выберите словарь',
    'txtWordsLearned': 'Выучено слов:',
    'txtWordsTotal'  : 'Слов в словаре:',
    'txtWordsWait'   : 'Одижают повторения:'
  }
}

AppController.prototype.handleGetUserState = function(response)
{
  var m = this.scope.model

  if (this.isUserStateValid(response)) {
    m.dictionaries = response.body.dictionaries
    m.dictionary   = this.isDictionaryValid(response)
                   ? response.body.dictionary : null
    m.email = response.body.email
    m.error = false
  } else {
    m.error = true
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
    m.error = false
  } else {
    m.error = true
  }

  this.updateView()
}

AppController.prototype.isDictionaryValid = function(response)
{
  return response && (response.result == 0) && response.body
                  && response.body.dictionary
}

AppController.prototype.isUserStateValid = function(response)
{
  return response && (response.result == 0) && response.body
                  && response.body.dictionaries
}

AppController.prototype.logout = function()
{
  var self = this

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
  o.style.top = '5em'
  o.style.left = w + 'px'
}

AppController.prototype.updateView = function()
{
    this.scope.$apply()
}
