package com.training.digginggame;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameModelTest {

    private GameModel gameModel;

    @Before
    public void setUp() {
        gameModel = new GameModel();
    }

    @Test
    public void testInitialItems() {
        assertFalse(gameModel.getFoundItems().isEmpty());
    }

    // ... other tests
}
