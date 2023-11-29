'use strict';

//app to draw polymorphic shapes on canvas
let app;
let intervalID = -1;
let Direction = "null";
let lastBlinkToggle = Date.now();
let blinkPoint = 1;
let isMuted = false;
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
    let mouthChange = 0.04;
    let selectedLevel = localStorage.getItem('selectedLevel');
    const audiostart = new Audio('images/opening_song.mp3');
    const audioeat = new Audio('images/eating.mp3');
    const audiodie = new Audio('images/die.mp3');
    let prevScore = 0;
    let prevLife = -99;

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
        if (prevScore !== score) {
            prevScore = score;
            audioeat.play();
        }
        ctx.fillStyle = 'white';
        ctx.font = '20px Arial';
        ctx.clearRect(cellSize * 25, cellSize * 27, cellSize * 15, cellSize * 20);
        ctx.fillText(`Score: ${score}`, cellSize * 26, cellSize * 29.7);
    };

    let drawLife = function(life) {
        if (prevLife !== life) {
            if (life < prevLife) {
                // If life is less than the previous life, play the die sound
                audiodie.play();
                clearInterval(intervalID);
                intervalID = -1;
                audiodie.onended = function() {
                    intervalID = setInterval(updatePacmanWorld, 100);
                };
            }
            prevLife = life;
        }
        ctx.fillStyle = 'white';
        ctx.font = '20px Arial';
        ctx.clearRect(0, cellSize * 27, cellSize * 15, cellSize * 20);
        ctx.fillText(`Life: `, cellSize, cellSize * 29.7);
        for (let i = 0; i < life; i++) {
            // app.drawPackMan(3 + i, 29, 'right')
            ctx.fillStyle = 'yellow';
            ctx.beginPath();
            ctx.arc(
                (3 + i) * cellSize + cellSize / 2,
                29 * cellSize + cellSize / 2,
                cellSize / 2,
                0.2 * Math.PI,
                1.8 * Math.PI
            );
            ctx.lineTo((3 + i) * cellSize + cellSize / 2, 29 * cellSize + cellSize / 2);
            ctx.closePath();
            ctx.fill();
        }
    };

    let drawStartMessage = function() {
        audiostart.play();
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
            ctx.fillText(`Next Level in 5s.`, canvas.width / 2 - 100, canvas.height / 2 + 80);
            countdown--;
            if (countdown < 0) {
                // When countdown finishes, clear interval and go to next level
                clear();
                clearInterval(countdownInterval);
                clearInterval(intervalID);
                intervalID = -1;
                // selectedLevel ++; // Set next level 并发问题不能用++

                if (selectedLevel == 1) {// cannot use === here, type is not same, value is same
                    localStorage.setItem('selectedLevel', '2'); // Save the new level
                    window.location.reload();
                    return; // Exit the function to prevent further execution
                } else if (selectedLevel == 2) {
                    localStorage.setItem('selectedLevel', '3'); // Save the new level
                    window.location.reload();
                    return; // Exit the function to prevent further execution
                } else if (selectedLevel == 3) {
                    window.location.href = 'index.html'; // Go to main menu if it's the last level
                    return; // Exit the function to prevent further execution
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
                // ctx.clearRect(canvas.width / 2 - 200, canvas.height / 2 + 100, 250, 150);
            ctx.fillText(`Restart in 5s.`, canvas.width / 2 - 100, canvas.height / 2 + 80);
            countdown--;
            if (countdown < 0) {
                // When countdown finishes, clear interval and go to next level
                clear();
                clearInterval(countdownInterval);
                clearInterval(intervalID);
                intervalID = -1;
                localStorage.setItem('selectedLevel', selectedLevel); // Save the new level
                // window.location.href = 'play.html'; // Go to the next level
                window.location.reload();
            }
        }, 1000); // Update the countdown every 1 second (1000 milliseconds)
    };

    let clearDirty = function(x = 0, y = 0) {
        ctx.clearRect(x * cellSize - 2, y * cellSize - 2, cellSize + 5, cellSize + 5);
    };

    let clear = function() {
        ctx.clearRect(0,0, canvas.width, canvas.height);
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
    let points = 240;
    let ghosts = 4;
    let life = 3;
    app = createApp(document.querySelector("canvas"));
    levelSelected(localStorage.getItem('selectedLevel'));
    clear();
    app.drawStartMessage();

    // Start the game loop
    $('#startGame').click(function() {
        $.post("/set", { pointNum: points, ghostNum: ghosts, lifeNum: life }, function(data) {
            // appendJsonToBox(data);
        }, "json");
        document.getElementById('saveSettings').disabled = true;
        clear();
        updatePacmanWorld();
        clearInterval(intervalID);
        intervalID = setInterval(updatePacmanWorld, 100);
    });

    $('#pauseGame').click(function() {
        // window.location.href = 'play.html';
        if (intervalID !== -1) {
            clearInterval(intervalID);
            intervalID = -1;
            document.getElementById("pauseGame").innerHTML = "Continue Game!";
        } else {
            intervalID = setInterval(updatePacmanWorld, 100);
            document.getElementById("pauseGame").innerHTML = "Pause";
        }
    });

    $('#restartGame').click(function() {
        // window.location.href = 'play.html';
        window.location.reload();
    });

    $('#backHome').click(function() {
        window.location.href = 'index.html';
    });

    $('#saveSettings').click(function() {
        points = document.getElementById("pointValue").innerHTML;
        ghosts = document.getElementById("ghostValue").innerHTML;
        life = document.getElementById("lifeValue").innerHTML;
        // print "save settings" message
        document.getElementById("saveSettings").innerHTML = "Settings Saved Successes!";
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
function toggleMute() {
    isMuted = !isMuted;
    audiostart.muted = isMuted;
    audioeat.muted = isMuted;
    audiodie.muted = isMuted;

    // Update the button text based on the mute state
    document.getElementById('muteButton').textContent = isMuted ? '🔊 Unmute' : '🔇 Mute';
}
function updateBlinkState() {
    let now = Date.now();
    // Toggle the blink state every 500 milliseconds as an example
    if (now - lastBlinkToggle > 200) {
        blinkPoint = 1 - blinkPoint; // This will toggle between 0 and 1
        lastBlinkToggle = now;
    }
}

let prevMap = null;
let prevPacman = null;
let prevGhost = null;
function drawMap() {
    $.post("/update", "json").done(function(mapResponse) {
        // Update the map and other related elements
        app.drawMap(mapResponse.map.map);
        app.drawScore(mapResponse.map.score);
        if (mapResponse.map.isWin === true) {
            clearInterval(intervalID); // Stop the game loop
            intervalID = -1;
            app.drawWinMessage();
        }
        prevMap = mapResponse; // Store the successful response
    }).fail(function() {
        console.error('Failed to get map data');
        app.drawMap(prevMap.map.map);
        app.drawScore(prevMap.map.score);
        // Handle error, perhaps use prevMap if available
    });
}
let lastPacmanPosition = { x: null, y: null };
function drawPacman() {
    $.post("/pacman", { direction: Direction }, "json").done(function(pacmanResponse) {
        // Update Pacman
        if (lastPacmanPosition.x !== null && lastPacmanPosition.y !== null) {
            app.clearDirty(lastPacmanPosition.x, lastPacmanPosition.y);
        }
        app.drawPackMan(pacmanResponse.pacman.x, pacmanResponse.pacman.y, pacmanResponse.pacman.direction);
        app.drawLife(pacmanResponse.pacman.life);
        if (pacmanResponse.pacman.life <= 0) {
            clearInterval(intervalID); // Stop the game loop
            intervalID = -1;
            app.drawGameOverMessage(); // Draw game over message if life is less than 0
        }
        lastPacmanPosition = {x: pacmanResponse.pacman.x, y: pacmanResponse.pacman.y};
        prevPacman = pacmanResponse; // Store the successful response
    }).fail(function() {
        console.error('Failed to get pacman data');
        app.drawPackMan(prevPacman.pacman.x, prevPacman.pacman.y, prevPacman.pacman.direction);
        app.drawLife(prevPacman.pacman.life);
        // Handle error, perhaps use prevPacman if available
    });
}

let lastGhostPositions = [];
function drawGhost() {
    $.post("/ghost", "json").done(function(ghostResponse) {
        // clear prev ghosts
        lastGhostPositions.forEach(pos => {
            app.clearDirty(pos.x, pos.y);
        });
        lastGhostPositions = ghostResponse.ghosts.map(ghost => ({x: ghost.x, y: ghost.y}));
        // Update Ghosts
        for (let i = 0; i < ghostResponse.ghosts.length; i++) {
            app.drawGhost(
                ghostResponse.ghosts[i].x,
                ghostResponse.ghosts[i].y,
                ghostResponse.ghosts[i].direction,
                ghostResponse.ghosts[i].color,
                ghostResponse.ghosts[i].canEaten,
                ghostResponse.ghosts[i].beEaten,
                prevPacman ? prevPacman.pacman.eatenTime : 0
            );
        }
        prevGhost = ghostResponse; // Store the successful response
    }).fail(function() {
        console.error('Failed to get ghost data');
        for (let i = 0; i < prevGhost.ghosts.length; i++) {
            app.drawGhost(
                prevGhost.ghosts[i].x,
                prevGhost.ghosts[i].y,
                prevGhost.ghosts[i].direction,
                prevGhost.ghosts[i].color,
                prevGhost.ghosts[i].canEaten,
                prevGhost.ghosts[i].beEaten,
                prevPacman ? prevPacman.pacman.eatenTime : 0
            );
        }
        // Handle error, perhaps use prevGhost if available
    });
}
function updatePacmanWorld() {
    // console.log(selectedLevel);
    updateBlinkState();
    // app.clear();
    drawMap();
    drawPacman();
    drawGhost();
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
