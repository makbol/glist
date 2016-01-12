(function() {
  'use strict';

  function Menu() {}

  Menu.prototype = {
    create: function () {
      var text = this.add.text(this.game.width * 0.5, this.game.height * 0.1,
        'MENU', {font: '42px Arial', fill: '#ffffff', align: 'center'
      });
	  
	  var signup_button = this.add.button(this.game.width * 0.5, this.game.height * 0.3, 'button_menu_signup', signUp, this, 'akt', 'std'); 
	  var signin_button = this.add.button(this.game.width * 0.5, this.game.height * 0.5, 'button_menu_signin', signIn, this, 'akt', 'std');	       
	  
	  text.anchor.set(0.5);
	  signin_button.anchor.set(0.5);
	  signup_button.anchor.set(0.5);
	}
	  
  };
  
  function signUp() {
	  this.game.state.start('usermenu');
  }

  function signIn() {
	  this.game.state.start('usermenu');
  }

  window['tron'] = window['tron'] || {};
  window['tron'].Menu = Menu;
}());
