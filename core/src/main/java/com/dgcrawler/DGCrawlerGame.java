package com.dgcrawler;

import com.badlogic.gdx.Game;
import com.dgcrawler.view.screens.MainMenuScreen;

public class DGCrawlerGame extends Game {

    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));
    }
}
