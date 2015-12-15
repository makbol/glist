(function() {
  'use strict';

  function Menu() {}

  

  Menu.prototype = {
    create: function () {
      var text = this.add.text(this.game.width * 0.5, this.game.height * 0.1,
        'MENU', {font: '42px Arial', fill: '#ffffff', align: 'center'
      });
	  var start_button = this.add.button(this.game.width * 0.5, this.game.height * 0.3, 'button_menu_start', startGame, this, 'akt', 'std');
	  var results_button = this.add.button(this.game.width * 0.5, this.game.height * 0.5, 'button_menu_results', showResults, this, 'akt', 'std');
	  var exit_button = this.add.button(this.game.width * 0.5, this.game.height * 0.7, 'button_menu_exit', exitGame, this, 'akt', 'std');
      text.anchor.set(0.5);
	  start_button.anchor.set(0.5);
	  results_button.anchor.set(0.5);
	  exit_button.anchor.set(0.5);
	}
	  
  };
  
  function startGame() {
    ws.send("startNewGame");
    // this.game.state.start('game');
  }
  
  function showResults() {
  }
  
  function exitGame() {
  }

  window['tron'] = window['tron'] || {};
  window['tron'].Menu = Menu;
}());
