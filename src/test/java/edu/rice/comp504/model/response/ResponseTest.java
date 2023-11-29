package edu.rice.comp504.model.response;

import edu.rice.comp504.model.map.Map;
import edu.rice.comp504.model.objects.Pacman;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ResponseTest {

    @Test
    public void testMakeResponse() {
        ResponseGhost responseGhost = new ResponseGhost();
        ResponseGhosts responseGhosts = new ResponseGhosts();

        int x = 10;
        int y = 20;
        String direction = "left";
        String color = "red";
        boolean canEaten = true;
        boolean beEaten = false;
        ArrayList<ResponseGhost> ghosts = new ArrayList<>();

        responseGhost = responseGhost.makeResponse(x, y, direction, color, canEaten, beEaten);
        ghosts.add(responseGhost);
        responseGhosts = responseGhosts.makeResponse(ghosts);
        assertEquals("X should be 10", x,10);

    }


    @Test
    public void testMakeResponse2() {
        ResponseMap responseMap = new ResponseMap();
        Map testMap = new Map();

        responseMap = responseMap.makeResponse(testMap);

    }

    @Test
    public void testMakeResponse3() {
        ResponsePacMan responsePacMan = new ResponsePacMan();
        Pacman testPacman = new Pacman(10, 10, "left");

        responsePacMan = responsePacMan.makeResponse(testPacman);
        assertEquals("X should be 10", testPacman.getX(),10);


    }
}
