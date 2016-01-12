'use strict';

var player;

var playerId; 

var GAME_SERVER_ADDRESS = "ws://10.22.107.19:1777";

var playersList =  [];

var gameToStart = false;

function makeid()
{
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for( var i=0; i < 15; i++ )
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
}

window.addEventListener('load', function () {

   var ns = window['tron'];
   var ws;

    function initiateWebSocketConnection() {
        if ("WebSocket" in window) {
            ws = new WebSocket(GAME_SERVER_ADDRESS);

            window['ws'] = ws;
            ws.onopen = function(event) {
                var id = makeid();
                ws.send("joinGame," + id);
            };
         
         ws.onmessage = function (evt) {
            try {
              var received_msg = JSON.parse(evt.data.replace('\'', '\"'));
              switch (received_msg.commandName) {
      			    case "joinGame":
                  playerId = received_msg.result;
                  break;
                case "startNewGame":
                  // playersList = received_msg.result;
                  console.log("Game Started")
                  gameToStart = true;
                  break;
                case "UPDATE":
                  if (gameToStart) {
                    game.state.start('game');
                    gameToStart = false;
                  }
                  playersList = received_msg.result;
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
