(function() {
  'use strict';

  function UserMenu() {}

  UserMenu.prototype = {
    create: function () {
      var text = this.add.text(this.game.width * 0.5, this.game.height * 0.1,
        'Welcome John', {font: '42px Arial', fill: '#ffffff', align: 'center'
      });
	  
	  var newgame_button = this.add.button(this.game.width * 0.5, this.game.height * 0.25, 'button_menu_newgame', newGame, this, 'akt', 'std');
	  var join_button = this.add.button(this.game.width * 0.5, this.game.height * 0.45, 'button_menu_join', join, this, 'akt', 'std');
	  var results_button = this.add.button(this.game.width * 0.5, this.game.height * 0.65, 'button_menu_results', showResults, this, 'akt', 'std');
	  var logout_button = this.add.button(this.game.width * 0.5, this.game.height * 0.85, 'button_menu_logout', logout, this, 'akt', 'std');	       
	  
	  text.anchor.set(0.5);
	  newgame_button.anchor.set(0.5);
	  join_button.anchor.set(0.5);
	  results_button.anchor.set(0.5);
	  logout_button.anchor.set(0.5);
	}
	  
  };
  
  function newGame() {
	  ws.send("startNewGame");
  }

  function join() {
  }
  
  function showResults() {
  }
  
  function logout() {
	  this.game.state.start('menu');
  }

  window['tron'] = window['tron'] || {};
  window['tron'].UserMenu = UserMenu;
}());
