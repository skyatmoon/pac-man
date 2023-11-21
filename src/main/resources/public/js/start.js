let selectedLevel = 1; // Default to level 1

document.getElementById('level1').addEventListener('click', function() {
    selectedLevel = 1;
    document.getElementById('startGame').disabled = false;
});

document.getElementById('level2').addEventListener('click', function() {
    selectedLevel = 2;
    document.getElementById('startGame').disabled = false;
});

document.getElementById('level3').addEventListener('click', function() {
    selectedLevel = 3;
    document.getElementById('startGame').disabled = false;
});

document.getElementById('startGame').addEventListener('click', function() {
    // Save the selected level to local storage or pass it to the game page
    localStorage.setItem('selectedLevel', selectedLevel);
    // Redirect to the main game page
    window.location.href = 'play.html';
});