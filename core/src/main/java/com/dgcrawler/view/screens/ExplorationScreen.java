package com.dgcrawler.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.dgcrawler.model.PlayerModel;
import com.dgcrawler.controller.PlayerController;
import com.dgcrawler.view.render.PlayerRenderer;
import com.dgcrawler.view.render.MapRenderer;
import com.dgcrawler.controller.ExplorationController;
import com.dgcrawler.DGCrawlerGame;
import com.dgcrawler.view.ui.PauseMenuOverlay;
import com.dgcrawler.controller.MonstroController;
import com.dgcrawler.view.render.MonstroRenderer;

public class ExplorationScreen extends ScreenAdapter {

    private MapRenderer mapRenderer;
    private ExplorationController explorationController;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private MonstroController monstroController;
    private MonstroRenderer monstroRenderer;
    private SpriteBatch batch;
    private PlayerRenderer playerRenderer;

    private PlayerModel player;
    private PlayerController playerController;

    private float larguraMapa;
    private float alturaMapa;

    // menu de pause
    private final DGCrawlerGame game;

    private PauseMenuOverlay pauseMenu;

    public ExplorationScreen(DGCrawlerGame game) {
        this.game = game;
    }

    @Override
    public void show() {

        mapRenderer = new MapRenderer();

        monstroController = new MonstroController(
            mapRenderer.getObjetosSpawn(),
            game.getDatabaseManager().getMonstroRepository()
        );

        monstroRenderer = new MonstroRenderer();

        player = new PlayerModel();

        playerController = new PlayerController(
            player,
            mapRenderer.getCamadaColisoes()
        );

        playerRenderer = new PlayerRenderer();

        batch = new SpriteBatch();

        larguraMapa = mapRenderer.getLarguraMapa();
        alturaMapa = mapRenderer.getAlturaMapa();

        camera = new OrthographicCamera();
        viewport = new FitViewport(400, 300, camera);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        explorationController = new ExplorationController(
            player,
            playerController,
            camera,
            larguraMapa,
            alturaMapa
        );

        pauseMenu = new PauseMenuOverlay(
            // Continuar
            () -> setPausado(false),

            // Opções (implementaremos futuramente)
            () -> {
            },

            // Voltar ao menu principal
            () -> {
                setPausado(false);
                game.setScreen(new MainMenuScreen(game));
            },

            // Sair do jogo
            () -> Gdx.app.exit()
        );

        InputProcessor pauseInput = new InputAdapter() {

            @Override
            public boolean keyDown(int keycode) {

                if (keycode == Input.Keys.ESCAPE) {

                    explorationController.alternarPausa();

                    boolean pausado =
                        explorationController.getEstado()
                            == com.dgcrawler.model.ExplorationState.PAUSADO;

                    pauseMenu.setVisible(pausado);

                    return true;
                }

                return false;
            }
        };

        Gdx.input.setInputProcessor(
            new InputMultiplexer(
                pauseInput,
                pauseMenu.getStage()
            )
        );

    }

    private void setPausado(boolean pausado) {

        if (pausado) {
            explorationController.pausar();
        } else {
            explorationController.retomar();
        }

        pauseMenu.setVisible(pausado);
    }

    @Override
    public void render(float delta) {
        delta = Math.min(delta, 0.1f);

        Gdx.gl.glClearColor(0.12f, 0.12f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        boolean estaAndando = false;

        explorationController.update(delta, viewport);

        playerRenderer.update(
            delta,
            explorationController.isEstaAndando()
        );

        monstroRenderer.update(delta);

        viewport.apply();

        mapRenderer.render(camera);

        batch.setProjectionMatrix(camera.combined);

        monstroRenderer.render(
            batch,
            monstroController.getInimigos(),
            player
        );

        playerRenderer.render(batch, player);

        if (explorationController.getEstado()
            == com.dgcrawler.model.ExplorationState.PAUSADO) {

            pauseMenu.render(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);

        pauseMenu.resize(width, height);
    }

        @Override
        public void dispose() {

            if (batch != null) {
                batch.dispose();
            }

            if (playerRenderer != null) {
                playerRenderer.dispose();
            }

            if (mapRenderer != null) {
                mapRenderer.dispose();
            }

            if (pauseMenu != null) {
                pauseMenu.dispose();
            }

            if (monstroRenderer != null) {
                monstroRenderer.dispose();
            }

        }
}
