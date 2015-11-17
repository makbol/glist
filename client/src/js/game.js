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

      this.leftKey = this.game.input.keyboard.addKey(Phaser.Keyboard.LEFT);
      this.rightKey = this.game.input.keyboard.addKey(Phaser.Keyboard.RIGHT);

      this.keyLock = false;
      this.velocity = 200;
    },

    update: function () {
      var state = window['tron'].State;

        if (!this.keyLock) {
            if (this.leftKey.isDown) {
                state.setDirection((state.getDirection() +  3)%4);
                this.updateVelocity();
                this.keyLock = true;
            } else if (this.rightKey.isDown) {
                state.setDirection((state.getDirection() + 1)%4);
                this.updateVelocity();
                this.keyLock = true;
            }
        } else if (this.leftKey.isUp && this.rightKey.isUp) {
                this.keyLock = false;
        }
    },

    onInputDown: function () {
      this.game.state.start('menu');
    },

   updateVelocity: function() {
       var state = window['tron'].State;
       var directions = window['tron'].DIRECTIONS;
       if (state.getDirection() == directions.WEST) {
           player.body.velocity.x -= this.velocity;
           player.body.velocity.y = 0;
       } else if (state.getDirection() == directions.EAST) {
           player.body.velocity.x += this.velocity;
           player.body.velocity.y = 0;
       } else if (state.getDirection() == directions.NORTH) {
           player.body.velocity.y -= this.velocity;
           player.body.velocity.x = 0;
       } else if (state.getDirection() == directions.SOUTH) {
           player.body.velocity.y += this.velocity;
           player.body.velocity.x = 0;
       }
   }

  };

  window['tron'] = window['tron'] || {};
  window['tron'].Game = Game;

}());
