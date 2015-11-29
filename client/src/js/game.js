(function() {
  'use strict';
  
  var playerToIdMap = {}

  var getDistance = function(x1, y1, x2, y2) {
    return Math.sqrt( Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2) );
  }

  function Game() {}

  Game.prototype = {
    create: function () {

      // var this.game.add.group();

      this.input.onDown.add(this.onInputDown, this);
      this.game.add.tileSprite(0, 0, 1920, 1920, 'background'); 
      this.game.world.setBounds(0, 0, 1920, 1920);
      this.game.physics.startSystem(Phaser.Physics.P2JS);
      player = this.game.add.sprite(this.game.world.centerX, this.game.world.centerY, 'player');
      this.game.physics.p2.enable(player);
      this.cursors = this.game.input.keyboard.createCursorKeys();
      this.game.camera.follow(player);

      for (var tmpPlayer in playersList) {
        var tmpPlayerObject = playersList[tmpPlayer];
        playerToIdMap[tmpPlayerObject.id] = this.game.add.sprite(this.game.world.centerX, this.game.world.centerY, 'player');
        this.game.physics.p2.enable(playerToIdMap[tmpPlayerObject.id]);
        this.scoreTable.addUser(tmpPlayerObject.userName, tmpPlayerObject.color);
      }

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
        ws.send( {'id' : playerId, 
                   'x' : player.body.x, 
                   'y' : player.body.y,  
                   'v_x' : player.body.velocity.x, 
                   'v_y' : player.body.velocity.y } );
      }
      for (var tmpPlayer in playersList) {
        var tmpPlayerObject = playersList[tmpPlayer];

        playerToIdMap[tmpPlayerObject.id].body.velocity.x = playersList[tmpPlayer].v_x;
        playerToIdMap[tmpPlayerObject.id].body.velocity.y = playersList[tmpPlayer].v_y;
        
        if (getDistance(playerToIdMap[tmpPlayerObject.id].body.x, playerToIdMap[tmpPlayerObject.id].body.y,
                        playersList[tmpPlayer].x, playersList[tmpPlayer].y) > 10) {
          playerToIdMap[tmpPlayerObject.id].body.x = playersList[tmpPlayer].x;
          playerToIdMap[tmpPlayerObject.id].body.y = playersList[tmpPlayer].y;
        }
      }
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
