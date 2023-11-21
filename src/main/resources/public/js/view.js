'use strict';

//app to draw polymorphic shapes on canvas
let app;
let intervalID = -1;
let Direction = "null";
let GridSize = 31;
let selectedLevel = 1; // Default to level 1
let lastBlinkToggle = Date.now();
let blinkPoint = 1;
/**
 * Create the ball world app for a canvas
 * @param canvas The canvas to draw balls on
 * @returns {{clear: clear, drawMap: drawMap}}
 */
function createApp(canvas) {
    let ctx = canvas.getContext("2d");
    let fruitImg = new Image();
    fruitImg.src = 'images/fruit.svg';
    const gridSize = GridSize;
    const cellSize = canvas.width / gridSize;
    let mouthAngle = 0.2;
    let mouthChange = 0.04;

    let drawMap = function (map) {
        for (let row = 0; row < map.length; row++) {
            for (let col = 0; col < map[row].length; col++) {
                const value = map[row][col];
                if (value === 1) {
                    // Draw wall
                    ctx.fillStyle = 'blue';
                    ctx.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                } else if (value === 0) {
                    // Draw 10 points dot
                    ctx.fillStyle = 'white';
                    ctx.beginPath();
                    ctx.arc(col * cellSize + cellSize / 2, row * cellSize + cellSize / 2, cellSize / 8, 0, Math.PI * 2);
                    ctx.fill();
                } else if (value === 2) {
                    // Draw 50 points dot
                    if (blinkPoint === 0) {
                        // If blinkPoint is 0, don't draw the dot to create a blinking effect
                    } else {
                        // Draw the dot
                        ctx.fillStyle = 'white';
                        ctx.beginPath();
                        ctx.arc(col * cellSize + cellSize / 2, row * cellSize + cellSize / 2, cellSize / 4, 0, Math.PI * 2);
                        ctx.fill();
                    }
                } else if (value === 3) {
                    // Draw a fruit (100 points)
                    // ctx.drawImage(img, col * cellSize, row * cellSize, cellSize, cellSize);
                    ctx.fillStyle = 'green';
                    ctx.beginPath();
                    ctx.drawImage(fruitImg, col * cellSize, row * cellSize, cellSize, cellSize);
                    ctx.fill();
                } else if (value === 8) {
                    // Draw the ghost house door
                    ctx.fillStyle = 'pink';
                    ctx.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                } else if (value === 7) {
                    // Draw the APEX logo
                    ctx.fillStyle = 'green';
                    ctx.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                }

                // Empty spaces don't need to be drawn
            }
        }
    };

    let drawPackMan = function(x, y, direction) {

        ctx.fillStyle = 'yellow';
        ctx.beginPath();

        // Calculate the start and end angles for the arc based on the direction
        let startAngle;
        let endAngle;

        if (direction === 'null') {
            direction = 'right';
        }
        if (direction === 'right') {
            startAngle = 0.3 * Math.PI - mouthAngle * Math.PI;
            endAngle = 1.7 * Math.PI + mouthAngle * Math.PI;
        } else if (direction === 'left') {
            startAngle = 1.35 * Math.PI - mouthAngle * Math.PI;
            endAngle = 0.65 * Math.PI + mouthAngle * Math.PI;
        } else if (direction === 'up') {
            startAngle = 1.85 * Math.PI - mouthAngle * Math.PI;
            endAngle = 1.15 * Math.PI + mouthAngle * Math.PI;
        } else if (direction === 'down') {
            startAngle = 0.85 * Math.PI - mouthAngle * Math.PI;
            endAngle = 0.15 * Math.PI + mouthAngle * Math.PI;
        }

        ctx.arc(
            x * cellSize + cellSize / 2,
            y * cellSize + cellSize / 2,
            cellSize / 2,
            startAngle,
            endAngle
        );

        ctx.lineTo(x * cellSize + cellSize / 2, y * cellSize + cellSize / 2);
        ctx.closePath();
        ctx.fill();

        // Update the mouth angle for the next frame
        mouthAngle += mouthChange;

        // If the mouth is fully open or closed, change the direction of the angle change
        if (mouthAngle <= 0 || mouthAngle >= 0.3) {
            mouthChange = -mouthChange;
        }
    }

    let drawGhost = function(x, y, direction, color, canEaten, beEaten, eatenTime) {
        if (canEaten) {
            color = 'darkblue';
            if ( eatenTime < 5000) {
                if (blinkPoint === 0) {
                    // If blinkPoint is 0, don't draw the dot to create a blinking effect
                    color = 'blue';
                } else {
                    color = 'white';
                }
            }
        }
        let base = y * cellSize + cellSize / 2;
        let high = cellSize;
        let low = cellSize;
        let tl = x * cellSize + cellSize;
        let inc = cellSize / 10;
        if (!beEaten) {
            ctx.beginPath();
            ctx.fillStyle = color;
            // Body of the ghost
            ctx.arc(x * cellSize + cellSize / 2, y * cellSize + cellSize / 2, cellSize / 2, Math.PI, 0, false);

            ctx.quadraticCurveTo(tl - (inc * 1), base + high, tl - (inc * 2), base);
            ctx.quadraticCurveTo(tl - (inc * 3), base + low, tl - (inc * 4), base);
            ctx.quadraticCurveTo(tl - (inc * 5), base + high, tl - (inc * 6), base);
            ctx.quadraticCurveTo(tl - (inc * 7), base + low, tl - (inc * 8), base);
            ctx.quadraticCurveTo(tl - (inc * 9), base + high, tl - (inc * 10), base);

            ctx.closePath();
            ctx.fill();
        }

        ctx.beginPath();
        // Eyes
        ctx.fillStyle = 'white';
        ctx.arc(x * cellSize + cellSize / 3, y * cellSize + cellSize / 2, cellSize / 5, 0, 2 * Math.PI);
        ctx.arc(x * cellSize + 2 * cellSize / 3, y * cellSize + cellSize / 2, cellSize / 5, 0, 2 * Math.PI);
        ctx.fill();
        ctx.closePath();

        ctx.beginPath();
        // Pupils
        ctx.fillStyle = 'gray';
        let directionX = 0;
        let directionY = 0;
        if (direction === 'left') {
            directionX = -2;
        } else if (direction === 'right') {
            directionX = 2;
        } else if (direction === 'up') {
            directionY = -2;
        } else if (direction === 'down') {
            directionY = 2;
        }
        let pupilX = x * cellSize + cellSize / 3 + directionX;
        ctx.arc(pupilX, y * cellSize + cellSize / 2 + directionY, cellSize / 10, 0, 2 * Math.PI);
        pupilX = x * cellSize + 2 * cellSize / 3 + directionX;
        ctx.arc(pupilX, y * cellSize + cellSize / 2 + directionY, cellSize / 10, 0, 2 * Math.PI);
        ctx.closePath();
        ctx.fill();
    }

    let drawScore = function(score) {
        ctx.fillStyle = 'white';
        ctx.font = '20px Arial';
        ctx.fillText(`Score: ${score}`, cellSize * 26, cellSize * 29.7);
    };

    let drawLife = function(life) {
        ctx.fillStyle = 'white';
        ctx.font = '20px Arial';
        ctx.fillText(`Life: `, cellSize, cellSize * 29.7);
        for (let i = 0; i < life; i++) {
            app.drawPackMan(3 + i, 29, 'right')
        }
    };

    let drawWinMessage = function() {
        ctx.fillStyle = 'white';
        ctx.font = '40px Arial';
        ctx.fillText('You Win!', canvas.width / 2 - 100, canvas.height / 2);
    };

    let drawGameOverMessage = function() {
        ctx.fillStyle = 'white';
        ctx.font = '40px Arial';
        ctx.fillText('Game Over!', canvas.width / 2 - 100, canvas.height / 2);
    };

    let clear = function() {
        ctx.clearRect(0,0, canvas.width, canvas.height);
    };


    return {
        "clear":clear,
        "drawScore":drawScore,
        "drawPackMan":drawPackMan,
        "drawWinMessage":drawWinMessage,
        "drawGameOverMessage":drawGameOverMessage,
        "drawLife":drawLife,
        "drawGhost":drawGhost,
        "drawMap":drawMap
    }
}

window.onload = function() {
    app = createApp(document.querySelector("canvas"));
    selectedLevel = localStorage.getItem('selectedLevel') || 1;
    levelSelected(selectedLevel);
    clear();
    updatePacmanWorld();

    // Start the game loop
    $('#startGame').click(function() {
        clear();
        updatePacmanWorld();
        clearInterval(intervalID);
        intervalID = setInterval(updatePacmanWorld, 100);
    });

    $('#backHome').click(function() {
        window.location.href = 'index.html';
    });
};

/**
 * Append a json string to a text box
 * @param jsonData
 */
function appendJsonToBox(jsonData) {
    const jsonOutput = document.getElementById("json-output");
    jsonOutput.textContent = JSON.stringify(jsonData, null, 2);
}

document.addEventListener('keydown', function(event) {
    if (event.key === "ArrowLeft") {
        Direction = 'left';
    } else if (event.key === "ArrowUp") {
        Direction = 'up';
    } else if (event.key === "ArrowRight") {
        Direction = 'right';
    } else if (event.key === "ArrowDown") {
        Direction = 'down';
    }
});

function updateBlinkState() {
    let now = Date.now();
    // Toggle the blink state every 500 milliseconds as an example
    if (now - lastBlinkToggle > 200) {
        blinkPoint = 1 - blinkPoint; // This will toggle between 0 and 1
        lastBlinkToggle = now;
    }
}

function updatePacmanWorld() {
    updateBlinkState();
    // Fetch all required data
    const mapPromise = $.get("/update", "json");
    const pacmanPromise = $.get("/pacman", { direction: Direction }, "json");
    const ghostPromise = $.get("/ghost", "json");

    // Use $.when to synchronize the asynchronous calls
    $.when(mapPromise, pacmanPromise, ghostPromise).done(function(mapData, pacmanData, ghostData) {
        // Destructure the response data from each promise
        app.clear();
        // for debug
        appendJsonToBox({mapData, pacmanData, ghostData});
        const [mapResponse] = mapData;
        const [pacmanResponse] = pacmanData;
        const [ghostResponse] = ghostData;

        // Update the map
        GridSize = mapResponse.map.map.length;
        app.drawMap(mapResponse.map.map);
        app.drawScore(mapResponse.map.score);

        // Check if the player has won
        if (mapResponse.map.score > 2400) {
            app.drawWinMessage(); // Draw win message if score is greater than 2400
            clearInterval(intervalID); // Stop the game loop
        }

        // Update Pacman
        app.drawPackMan(pacmanResponse.pacman.x, pacmanResponse.pacman.y, pacmanResponse.pacman.direction);
        app.drawLife(pacmanResponse.pacman.life);

        if (pacmanResponse.pacman.life <= 0) {
            app.drawGameOverMessage(); // Draw game over message if life is less than 0
            clearInterval(intervalID); // Stop the game loop
        }

        // Update Ghost
        for (let i = 0; i < ghostResponse.ghosts.length; i++) {
            app.drawGhost(
                ghostResponse.ghosts[i].x,
                ghostResponse.ghosts[i].y,
                ghostResponse.ghosts[i].direction,
                ghostResponse.ghosts[i].color,
                ghostResponse.ghosts[i].canEaten,
                ghostResponse.ghosts[i].beEaten,
                pacmanResponse.pacman.eatenTime);
        }
    });
}

function levelSelected(level) {
    $.get("/level", { level: level }, "json");
}

/**
 * Clear the canvas
 */
function clear() {
    app.clear();
    $.get("/clear",function(data) {
        appendJsonToBox(data);
    }, "json");
}