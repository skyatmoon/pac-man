'use strict';

//app to draw polymorphic shapes on canvas
let app;
let Direction = "null";
let selectedLevel;
let lastBlinkToggle = Date.now();
let blinkPoint = 1;
let animationFrameId;
let eatenTime = 0;
/**
 * Create the ball world app for a canvas
 * @param canvas The canvas to draw balls on
 * @returns {{clear: clear, drawMap: drawMap}}
 */
function createApp(canvas) {
    let ctx = canvas.getContext("2d");
    let fruitImg = new Image();
    fruitImg.src = 'images/fruit.svg';
    const gridSize = 31;
    const cellSize = canvas.width / gridSize;
    let mouthAngle = 0.2;
    let mouthChange = 0.004;

    let drawMap = function (map) {
        for (let row = 0; row < map.length; row++) {
            for (let col = 0; col < map[row].length; col++) {
                const value = map[row][col];
                if (value === 0) {
                    // Draw 10 points dot
                    ctx.fillStyle = 'white';
                    ctx.beginPath();
                    ctx.arc(col * cellSize + cellSize / 2, row * cellSize + cellSize / 2, cellSize / 8, 0, Math.PI * 2);
                    ctx.fill();
                } else if (value === 2) {
                    // Draw 50 points dot
                    if (blinkPoint === 0) {
                        ctx.fillStyle = "#171616";
                        ctx.beginPath();
                        ctx.arc(col * cellSize + cellSize / 2, row * cellSize + cellSize / 2, cellSize / 3, 0, Math.PI * 2);
                        ctx.fill();
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
                }
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
            if ( eatenTime < 300) { // 5seconds
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
        ctx.clearRect(cellSize * 25, cellSize * 27, cellSize * 15, cellSize * 20);
        ctx.fillText(`Score: ${score}`, cellSize * 26, cellSize * 29.7);
    };

    let drawLife = function(life) {
        ctx.fillStyle = 'white';
        ctx.font = '20px Arial';
        ctx.clearRect(0, cellSize * 27, cellSize * 15, cellSize * 20);
        ctx.fillText(`Life: `, cellSize, cellSize * 29.7);
        for (let i = 0; i < life; i++) {
            app.drawPackMan(3 + i, 29, 'right')
        }
    };

    let drawStartMessage = function() {
        ctx.fillStyle = 'white';
        ctx.font = '40px Arial';
        ctx.fillText(`Level: ${selectedLevel}`, canvas.width / 2 - 100, canvas.height / 2);
        ctx.fillStyle = 'white';
        ctx.font = '30px Arial';
        ctx.fillText('Press START button', canvas.width / 2 - 155, canvas.height / 2 + 80);
    }

    let drawWinMessage = function() {
        let countdown = 5; // Start countdown from 3 seconds
        ctx.fillStyle = 'white';
        ctx.font = '40px Arial';
        ctx.fillText('You Win!', canvas.width / 2 - 100, canvas.height / 2);

        // Update the canvas with the countdown
        let countdownInterval = setInterval(function() {
            // Clear the area where the countdown is displayed
            ctx.fillStyle = 'black';
            ctx.fillRect(canvas.width / 2 - 100, canvas.height / 2 + 40, 200, 50);
            // Draw the countdown
            ctx.fillStyle = 'white';
            ctx.font = '30px Arial';
            ctx.fillText(`Go to next in ${countdown}`, canvas.width / 2 - 100, canvas.height / 2 + 80);
            countdown--;
            if (countdown < 0) {
                // When countdown finishes, clear interval and go to next level
                clearInterval(countdownInterval);
                selectedLevel ++; // Set next level
                if (selectedLevel > 3) {
                    window.location.href = 'index.html';
                } else {
                    localStorage.setItem('selectedLevel', selectedLevel); // Save the new level
                    // console.log(selectedLevel);
                    window.location.href = 'play.html'; // Go to the next level
                }
            }
        }, 1000); // Update the countdown every 1 second (1000 milliseconds)
    };

    let drawGameOverMessage = function() {
        let countdown = 5; // Start countdown from 3 seconds
        ctx.fillStyle = 'white';
        ctx.font = '40px Arial';
        ctx.fillText('Game Over!', canvas.width / 2 - 100, canvas.height / 2);

        // Update the canvas with the countdown
        let countdownInterval = setInterval(function() {
            // Clear the area where the countdown is displayed
            ctx.fillStyle = 'black';
            ctx.fillRect(canvas.width / 2 - 100, canvas.height / 2 + 40, 200, 50);
            // Draw the countdown
            ctx.fillStyle = 'white';
            ctx.font = '30px Arial';
            ctx.fillText(`Restart in ${countdown}`, canvas.width / 2 - 100, canvas.height / 2 + 80);
            countdown--;
            if (countdown < 0) {
                // When countdown finishes, clear interval and go to next level
                clearInterval(countdownInterval);
                localStorage.setItem('selectedLevel', selectedLevel); // Save the new level
                window.location.href = 'play.html'; // Go to the next level
            }
        }, 1000); // Update the countdown every 1 second (1000 milliseconds)
    };

    let clearDirty = function(x = 0, y = 0) {
        ctx.clearRect(x * cellSize - 2, y * cellSize - 2, cellSize + 5, cellSize + 5);
    };

    let clear = function() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
    };


    return {
        "clear":clear,
        "clearDirty":clearDirty,
        "drawScore":drawScore,
        "drawPackMan":drawPackMan,
        "drawWinMessage":drawWinMessage,
        "drawGameOverMessage":drawGameOverMessage,
        "drawLife":drawLife,
        "drawGhost":drawGhost,
        "drawMap":drawMap,
        "drawStartMessage":drawStartMessage
    }
}

window.onload = function() {
    let canvas;
    let points = 240;
    let ghosts = 4;
    let life = 3;
    stopGameLoop();
    canvas = document.querySelector("canvas");
    app = createApp(canvas);
    selectedLevel = localStorage.getItem('selectedLevel');
    levelSelected(selectedLevel);
    app.drawStartMessage();

    $('#startGame').click(function() {
        $.post("/set", { pointNum: points, ghostNum: ghosts, lifeNum: life }, function(data) {
            // Handle the response if needed
        }, "json");
        stopGameLoop();
        clear();
        document.getElementById('saveSettings').disabled = true;
        // Start the game loop with requestAnimationFrame instead of setInterval
        animationFrameId = requestAnimationFrame(gameLoop);
    });

    $('#restartGame').click(function() {
        window.location.href = 'play.html';
    });

    $('#backHome').click(function() {
        window.location.href = 'index.html';
    });

    $('#saveSettings').click(function() {
        points = document.getElementById("pointValue").innerHTML;
        ghosts = document.getElementById("ghostValue").innerHTML;
        life = document.getElementById("lifeValue").innerHTML;
    });
};

/**
 * Append a json string to a text box
 * @param jsonData
 */
// function appendJsonToBox(jsonData) {
//     const jsonOutput = document.getElementById("json-output");
//     jsonOutput.textContent = JSON.stringify(jsonData, null, 2);
// }

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
    // Toggle the blink state every 200 milliseconds as an example
    if (now - lastBlinkToggle > 200) {
        blinkPoint = 1 - blinkPoint; // This will toggle between 0 and 1
        lastBlinkToggle = now;
    }
}

function drawMap() {
    $.post("/update", function(data) {
        app.drawMap(data.map.map);
        app.drawScore(data.map.score);

        // Check if the player has won
        if (data.map.isWin === true) {
            clear();
            app.drawWinMessage(); // Draw win message if score is greater than 2400
            // clearInterval(intervalID); // Stop the game loop
            stopGameLoop();
        }
    }, "json");
}

let lastPacmanPosition = { x: null, y: null };
function drawPacman() {
    let timestamp;
    timestamp = Date.now();
    if (timestamp - lastFrameTime >= frameDelay) {
        $.post("/pacman", {direction: Direction}, function (data) {
            if (lastPacmanPosition.x !== null && lastPacmanPosition.y !== null) {
                app.clearDirty(lastPacmanPosition.x, lastPacmanPosition.y);
            }
            app.drawPackMan(data.pacman.x, data.pacman.y, data.pacman.direction);
            app.drawLife(data.pacman.life);
            eatenTime = data.pacman.eatenTime;
            lastPacmanPosition = {x: data.pacman.x, y: data.pacman.y};

            // Check if the player has loss
            if (data.pacman.life <= 0) {
                clear();
                app.drawGameOverMessage(); // Draw game over message if life is less than 0
                // clearInterval(intervalID); // Stop the game loop
                stopGameLoop();
            }
        }, "json");
        lastFrameTime = timestamp;
    }

}

let lastGhostPositions = [];
function drawGhost() {
    let timestamp;
    timestamp = Date.now();
    if (timestamp - lastFrameTime2 >= frameDelay) {
        $.post("/ghost", function (data) {
            lastGhostPositions.forEach(pos => {
                app.clearDirty(pos.x, pos.y);
            });
            lastGhostPositions = data.ghosts.map(ghost => ({x: ghost.x, y: ghost.y}));
            for (let i = 0; i < data.ghosts.length; i++) {
                app.drawGhost(
                    data.ghosts[i].x,
                    data.ghosts[i].y,
                    data.ghosts[i].direction,
                    data.ghosts[i].color,
                    data.ghosts[i].canEaten,
                    data.ghosts[i].beEaten,
                    eatenTime);
            }
        }, "json");
        lastFrameTime2 = timestamp;
    }
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
        // appendJsonToBox(data);
    }, "json");
}

function updatePoints(val) {
    document.getElementById("pointValue").innerHTML = val;
}
function updateGhosts(val) {
    document.getElementById("ghostValue").innerHTML = val;
}
function updateLife(val) {
    document.getElementById("lifeValue").innerHTML = val;
}
//************* GAME LOOP HERE ***********
let lastFrameTime = 0;
let lastFrameTime2 = 0;
const frameDelay = 1000 / 60; // For 60 FPS

function gameLoop() {
    // Clear the canvas
    updateBlinkState();
    drawMap();
    drawPacman();
    drawGhost();
    // Queue the next frame
    animationFrameId = requestAnimationFrame(gameLoop);
}

function stopGameLoop() {
    if (animationFrameId) {
        cancelAnimationFrame(animationFrameId);
    }
}