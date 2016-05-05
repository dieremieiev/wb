/*****************************************************************************
 * Lifecycle
 ****************************************************************************/

function AppController(scope)
{
  this.sim = new Simulator()

  this.lastRequest = null

  this.initScope(scope)
}

AppController.prototype.initScope = function(scope)
{
  this.scope = scope

  scope.model = {}
  scope.ml = this.getML()

  var self = this

  scope.repeatRequest    = function() { self.repeatRequest() }
  scope.selectDictionary = function() { self.selectDictionary() }
}


/*****************************************************************************
 * Public
 ****************************************************************************/

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
    'btnRepeat'    : 'Повторить',
    'errResponse'  : 'Ошибка запроса к серверу. Пожалуйста повторите запрос позже.',
    'tipExit'      : 'Выход',
    'tipRepeat'    : 'Повторить последний запрос к серверу',
    'txtSelectDict': 'Выберите словарь'
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
    m.error = true // TODO
  } else {
    m.error = true
  }

  this.updateView()
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

AppController.prototype.post = function(action, data, callback)
{
  this.lastRequest = [ action, data, callback ]

  this.sim.post(action, data, callback);
}

AppController.prototype.updateView = function()
{
    this.scope.$apply()
}
