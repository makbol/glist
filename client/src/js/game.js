(function() {
  'use strict';
  
  var playerToIdMap = {}

  var getDistance = function(x1, y1, x2, y2) {
    return Math.sqrt( Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2) );
  }

  function Game() {}

  Game.prototype = {
    create: function () {
      self = this;
      // var this.game.add.group();

      this.input.onDown.add(this.onInputDown, this);
      this.game.add.tileSprite(0, 0, 1920, 1920, 'background'); 
      this.game.world.setBounds(0, 0, 1920, 1920);
      this.game.physics.startSystem(Phaser.Physics.P2JS);
      player = this.game.add.sprite(this.game.world.centerX, this.game.world.centerY, 'player');
      this.game.physics.p2.enable(player);
      this.cursors = this.game.input.keyboard.createCursorKeys();
      this.game.camera.follow(player);

      playersList.forEach(function (tmpPlayerObject){
        playerToIdMap[tmpPlayerObject.id] = self.game.add.sprite(self.game.world.centerX, self.game.world.centerY, 'player');
        self.game.physics.p2.enable(playerToIdMap[tmpPlayerObject.id]);
        self.scoreTable.addUser(tmpPlayerObject.userName, tmpPlayerObject.color);
      });

      for(var i = 0; i < this.scoreTable.users.length; i++) {
        var userText = this.game.add.text(10, i*30, this.scoreTable.users[i].name, { font: "24px Arial", fill: this.scoreTable.users[i].color, align: "left" });
        userText.fixedToCamera = true;
      }

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

      if(ws.readyState != 0) {
        // ws.send( {'id' : playerId, 
        //            'x' : player.body.x, 
        //            'y' : player.body.y,  
        //            'v_x' : player.body.velocity.x, 
        //            'v_y' : player.body.velocity.y } );
      }

      playersList.forEach(function (tmpPlayer){
        var playerObject = playerToIdMap[tmpPlayer.id];
        playerObject.body.velocity.x = tmpPlayer.v_x;
        playerObject.body.velocity.y = tmpPlayer.v_y;
        
        if (getDistance(playerObject.body.x, playerObject.body.y, tmpPlayer.x, tmpPlayer.y) > 10) {
          playerObject.body.x = tmpPlayer.x;
          playerObject.body.y = tmpPlayer.y;
        }

      });
    },

    onInputDown: function () {
      this.game.state.start('gameover');
    }
  };

  Game.prototype.scoreTable = {
    users: [],
    addUser: function(name, color) {
      this.users.push({
        name: name,
        color: color
      });
    }
  }

  window['tron'] = window['tron'] || {};
  window['tron'].Game = Game;
}());
