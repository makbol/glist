(function() {
  'use strict';

  function Preloader() {
    this.asset = null;
    this.ready = false;
  }

  Preloader.prototype = {
    preload: function () {
      this.asset = this.add.sprite(this.game.width * 0.5 - 110, this.game.height * 0.5 - 10, 'preloader');
      this.load.setPreloadSprite(this.asset);

       this.load.onLoadComplete.addOnce(this.onLoadComplete, this);
       this.loadResources();

      this.ready = true;
    },

    loadResources: function () {
        this.game.load.image('background','assets/grass1.png');
        this.game.load.image('player','assets/ufo.png');
		this.game.load.atlas('button_menu_start', 'assets/buttons/start.png', 'assets/buttons/start.json');
		this.game.load.atlas('button_menu_results', 'assets/buttons/results.png', 'assets/buttons/results.json');
		this.game.load.atlas('button_menu_exit', 'assets/buttons/exit.png', 'assets/buttons/exit.json');
    },

    create: function () {

    },

    update: function () {
      // if (!!this.ready) {
        this.game.state.start('menu');
      // }
    },

    onLoadComplete: function () {
       this.ready = true;
    }
  };

  window['tron'] = window['tron'] || {};
  window['tron'].Preloader = Preloader;
}());
