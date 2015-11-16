(function () {
    'use strict';

    var directions = {
        WEST: 0,
        EAST: 1,
        NORTH: 2,
        SOUTH: 3
    };

    function State(initialDirection, initialPosition) {

        this._direction = initialDirection;
        this._position = initialPosition;

        var isVertical = function (direction) {
            return direction === directions.EAST || direction === directions.WEST;
        };

        this.setDirectionIfAllowed = function (newDirection) {
            var allowed = false;
            if (isVertical(this._direction)) {
                allowed = !isVertical(newDirection);
            } else {
                allowed = isVertical(newDirection);
            }
            allowed && (this._direction = newDirection);
        };

        this.getDirection = function () {
            return this._direction;
        };

        this.setHorizontalPosition = function (x) {
            this._position.x = x;
        };

        this.setVerticalPosition = function (y) {
            this._position.y = y;
        };

        this.getPosition = function () {
            return this._position;
        }
    }

    window['tron'] = window['tron'] || {};
    window['tron'].DIRECTIONS = directions;

    /* FIXME  missing initialDirection and initialPosition argument*/
    window['tron'].State = new State();
}());

