(function () {
    'use strict';

    //nie ruszac cyferek!!! ważne żeby zostały takie jakie są
    //bo po dupie dostaniecie :O
    var directions = {
        NORTH: 0,
        EAST: 1,
        SOUTH: 2,
        WEST: 3
    };

    function State(initialDirection, initialPosition) {

        this._direction = initialDirection;
        this._position = initialPosition;

        this.setDirection = function (newDirection) {
            console.log(newDirection)
            this._direction = newDirection
        };

        this.getDirection = function () {
            return this._direction;
        };
    }

    window['tron'] = window['tron'] || {};
    window['tron'].DIRECTIONS = directions;

    /* FIXME  missing initialDirection and initialPosition argument*/
    window['tron'].State = new State(directions.WEST, (500, 500));
}());

