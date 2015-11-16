(function() {
  'use strict';

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
      var state = window['tron'].State;
      var directions = window['tron'].DIRECTIONS;
      player.body.setZeroVelocity();

      if (this.cursors.up.isDown) {
        player.body.moveUp(300);
        state.setDirectionIfAllowed(directions.NORTH);
      } else if (this.cursors.down.isDown) {
        player.body.moveDown(300);
        state.setDirectionIfAllowed(directions.SOUTH);
      }

      if (this.cursors.left.isDown) {
        player.body.velocity.x = -300;
        state.setDirectionIfAllowed(directions.LEFT);
      } else if (this.cursors.right.isDown) {
        player.body.moveRight(300);
        state.setDirectionIfAllowed(directions.RIGHT);
      }
    },

    onInputDown: function () {
      this.game.state.start('menu');
    }
  };

  window['tron'] = window['tron'] || {};
  window['tron'].Game = Game;
}());
