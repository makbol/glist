'use strict';

var player;

var GAME_SERVER_ADDRESS = "ws://10.22.107.19:1666";

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
   window['ws'] = ws;

   function initiateWebSocketConnection() {
      if ("WebSocket" in window) {
         ws = new WebSocket(GAME_SERVER_ADDRESS);
         
         ws.onopen = function() {
         	var id = makeid();
            ws.send(id);
         };
         
         ws.onmessage = function (evt) { 
            var received_msg = JSON.parse(evt.data);
            switch (received_msg.commandType) {
  			    case "START":
  			        break;
  			    case "UPDATE":
  			        break;
  			    case "GAME_OVER":
  			    	break;
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
  /* yo phaser:state new-state-files-put-here */
  game.state.start('boot');
  //initiateWebSocketConnection();
}, false);
