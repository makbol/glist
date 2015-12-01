'use strict';

var player;

var playerId; 

var GAME_SERVER_ADDRESS = "ws://10.22.107.19:1666";

var playersList =  [{
    'x' : 700,
    'y' : 700,
    'v_x' : 5,
    'v_y' : 5,
    'color' : 'red',
    'id' : 1000,
    'userName' : 'Player1'
  }, {
    'x' : 800,
    'y' : 800,
    'v_x' : 2,
    'v_y' : 2,
    'color' : 'yellow',
    'id' : 1100,
    'userName' : 'Player2'
  }];

function makeid()
{
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for( var i=0; i < 15; i++ )
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
}

var ws;

window.addEventListener('load', function () {

   var ns = window['tron'];

   function initiateWebSocketConnection() {
      if ("WebSocket" in window) {
         ws = new WebSocket(GAME_SERVER_ADDRESS);
         
         ws.onopen = function(event) {
         	  var id = makeid();
            ws.send("joinGame," + id);
         };
         
         ws.onmessage = function (evt) {
            console.log(evt.data)
            try {
              var received_msg = JSON.parse(evt.data);
              switch (received_msg.commandName) {
      			    case "joinGame":
                  playerId = received_msg.result;
                  break;
                case "startNewGame":
                  game.state.start('game');
      			      break;
      			    case "UPDATE":
                  console.log(playersList)
                  playersList = JSON.parse(received_msg.result);
                  console.log(received_msg.result)
      			      console.log(playersList)
                  break;
      			    case "GAME_OVER":
      			    	break;
              }
			     } catch(err) {
              console.warn(err)
           }
         };
  
         ws.onclose = function() { 
         };

      } else {
         alert("WebSocket NOT supported by your Browser!");
      }
   }


  var game = new Phaser.Game(1200, 800, Phaser.AUTO, 'tron-game');

  game.state.add('boot', ns.Boot);
  game.state.add('preloader', ns.Preloader);
  game.state.add('menu', ns.Menu);
  game.state.add('game', ns.Game);
  game.state.add('gameover', ns.Gameover);
  console.log(ns.Gameover);
  /* yo phaser:state new-state-files-put-here */
  game.state.start('boot');
  initiateWebSocketConnection();
}, false);
