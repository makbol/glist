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
    this.game.add.tileSprite(0, 0, 1000, 600, 'background'); 
    this.game.world.setBounds(0, 0, 1000, 600);
    this.game.physics.startSystem(Phaser.Physics.P2JS);
    
    playersList.forEach(function (tmpPlayerObject){
      var tmpSpirite = self.game.add.sprite(self.game.world.centerX, self.game.world.centerY, 'player');
      playerToIdMap[tmpPlayerObject.userId] = tmpSpirite;
      self.game.physics.p2.enable(tmpSpirite);
      self.scoreTable.addUser(tmpPlayerObject.userName, tmpPlayerObject.color);
    });
    
    player = playerToIdMap[playerId];
    this.game.camera.follow(player);
    this.cursors = this.game.input.keyboard.createCursorKeys();
    console.log("Me: ")
    console.log(player)
    // playerToIdMap[playerId] = player;
    // this.game.physics.p2.enable(player);

    for(var i = 0; i < this.scoreTable.users.length; i++) {
      var userText = this.game.add.text(10, i*30, this.scoreTable.users[i].name, { font: "24px Arial", fill: this.scoreTable.users[i].color, align: "left" });
      userText.fixedToCamera = true;
    }

    this.leftKey = this.game.input.keyboard.addKey(Phaser.Keyboard.LEFT);
    this.rightKey = this.game.input.keyboard.addKey(Phaser.Keyboard.RIGHT);

    // this.upKey = this.game.input.keyboard.addKey(Phaser.Keyboard.UP);
    // this.downKey = this.game.input.keyboard.addKey(Phaser.Keyboard.DOWN);

    this.keyLock = false;
    this.velocity = 200;
  },

  update: function () {
    player.body.setZeroVelocity();
    var state = window['tron'].State;

    if(window['ws'].readyState != 0) {
      window['ws'].send( { 'id' : playerId,
                            'x' : player.body.x, 
                            'y' : player.body.y,  
                          'v_x' : player.body.velocity.x, 
                          'v_y' : player.body.velocity.y } );
    }

    // player.body.setZeroVelocity();
    
    // if (this.cursors.up.isDown) {
    //   player.body.moveUp(300)
    // } else if (this.cursors.down.isDown) {
    //   player.body.moveDown(300);
    // }
    
    // if (this.cursors.left.isDown) {
    //   player.body.velocity.x = -300;
    // } else if (this.cursors.right.isDown) {
    //   player.body.moveRight(300);
    // }

    playersList.forEach(function (tmpPlayer){
      
      var playerObject = playerToIdMap[tmpPlayer.userId];
      if (playerObject != undefined) {
          playerObject.body.velocity.x = 0;//tmpPlayer.v_x;
          playerObject.body.velocity.y = 0;//tmpPlayer.v_y;
          playerObject.body.x = tmpPlayer.x;
          playerObject.body.y = tmpPlayer.y;
      } else {
        console.log(tmpPlayer)
      }

    });

    if (!this.keyLock) {
        if (this.leftKey.isDown) {
            state.setDirection((state.getDirection() +  3)%4);
            this.keyLock = true;
            window['ws'].send('turnCommand,' + Object.keys(window['tron'].DIRECTIONS)[state.getDirection()])
        } else if (this.rightKey.isDown) {
            state.setDirection((state.getDirection() + 1)%4);
            this.keyLock = true;
            window['ws'].send('turnCommand,' + Object.keys(window['tron'].DIRECTIONS)[state.getDirection()])
        }
        // else if (this.upKey.isDown) {
        //     state.setDirection((state.getDirection() + 2)%4);
        //     this.keyLock = true;
        //     window['ws'].send('turnCommand,' + Object.keys(window['tron'].DIRECTIONS)[state.getDirection()])
        // } else if (this.downKey.isDown) {
        //     state.setDirection((state.getDirection() + 4)%4);
        //     this.keyLock = true;
        //     window['ws'].send('turnCommand,' + Object.keys(window['tron'].DIRECTIONS)[state.getDirection()])
        // }
    } else if (this.leftKey.isUp && this.rightKey.isUp) { //} && this.upKey.isUp && this.downKey.isUp) {
        this.keyLock = false;
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