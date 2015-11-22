(function() {
  'use strict';


  var players = [{
    'x' : 1,
    'y' : 1,
    'v_x' : 1,
    'v_y' : 1,
    'color' : 'red'
  }]

  function Game() {}


  Game.prototype = {
    create: function () {
      this.input.onDown.add(this.onInputDown, this);
      this.game.add.tileSprite(0, 0, 1920, 1920, 'background');
      this.game.world.setBounds(0, 0, 1920, 1920);
      this.game.physics.startSystem(Phaser.Physics.P2JS);
      player = this.game.add.sprite(this.game.world.centerX, this.game.world.centerY, 'player');
      this.game.physics.p2.enable(player);
      this.cursors = this.game.input.keyboard.createCursorKeys();
      this.game.camera.follow(player);
    },

    update: function () {
      player.body.setZeroVelocity();
      if (this.cursors.up.isDown) {
        player.body.moveUp(300)
      } else if (this.cursors.down.isDown) {
        player.body.moveDown(300);
      }

      if (this.cursors.left.isDown) {
        player.body.velocity.x = -300;
      } else if (this.cursors.right.isDown) {
        player.body.moveRight(300);
      }
    },

    onInputDown: function () {
      this.game.state.start('menu');
    }
  };

  window['tron'] = window['tron'] || {};
  window['tron'].Game = Game;
}());
