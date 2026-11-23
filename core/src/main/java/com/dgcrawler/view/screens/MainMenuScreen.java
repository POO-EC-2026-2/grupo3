
package com.dgcrawler.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.dgcrawler.DGCrawlerGame;
import com.dgcrawler.view.ui.MainMenuTheme;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class MainMenuScreen implements Screen {

    private final DGCrawlerGame game;
    private Stage stage;
    private BitmapFont titleFont;
    private BitmapFont buttonFont;
    private BitmapFont subtitleFont;
    private Texture backgroundTexture;
    private Texture buttonTexture;
    private Texture buttonHoverTexture;
    private Texture buttonPressedTexture;
    private Texture decorationTexture;
    private Image backgroundImage;

    public MainMenuScreen(DGCrawlerGame game) {
        this.game = game;
    }

    @Override
    public void show() {

        FitViewport viewport = new FitViewport(
            MainMenuTheme.VIRTUAL_WIDTH,
            MainMenuTheme.VIRTUAL_HEIGHT
        );

        stage = new Stage(
            new FitViewport(
                MainMenuTheme.VIRTUAL_WIDTH,
                MainMenuTheme.VIRTUAL_HEIGHT
            )
        );

        backgroundTexture = new Texture(
            Gdx.files.internal("ui/main_menu/background.png")
        );

        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true);

        Gdx.input.setInputProcessor(stage);

        titleFont = carregarFonte(
            MainMenuTheme.TITLE_FONT_PATH,
            MainMenuTheme.TITLE_FONT_SIZE
        );

        buttonFont = carregarFonte(
            MainMenuTheme.BUTTON_FONT_PATH,
            MainMenuTheme.BUTTON_FONT_SIZE
        );

        subtitleFont = carregarFonte(
            MainMenuTheme.SUBTITLE_FONT_PATH,
            MainMenuTheme.SUBTITLE_FONT_SIZE
        );

        titleFont.getData().setScale(1f / 3f);
        buttonFont.getData().setScale(1f / 3f);
        subtitleFont.getData().setScale(1f / 3f);

        criarTexturas();

        criarInterface();
    }

    private void criarTexturas() {

        backgroundTexture = criarTextura(
            MainMenuTheme.BACKGROUND
        );

        buttonTexture = criarTextura(
            MainMenuTheme.BUTTON
        );

        buttonHoverTexture = criarTextura(
            MainMenuTheme.BUTTON_HOVER
        );

        buttonPressedTexture = criarTextura(
            MainMenuTheme.BUTTON_PRESSED
        );

        decorationTexture = criarTextura(
            MainMenuTheme.DECORATION
        );
    }

    private BitmapFont carregarFonte(String caminho, int tamanho) {

        return getBitmapFont(caminho, tamanho);
    }

    public static BitmapFont getBitmapFont(String caminho, int tamanho) {
        FreeTypeFontGenerator generator =
            new FreeTypeFontGenerator(Gdx.files.internal(caminho));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
            new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = tamanho;

        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;

        parameter.characters =
            FreeTypeFontGenerator.DEFAULT_CHARS +
                "áàâãéêíóôõúçÁÀÂÃÉÊÍÓÔÕÚÇ";

        BitmapFont fonte = generator.generateFont(parameter);

        generator.dispose();

        return fonte;
    }

    private void criarInterface() {

        Label.LabelStyle labelStyle = new Label.LabelStyle();

        Label.LabelStyle titleStyle = new Label.LabelStyle();

        titleStyle.font = titleFont;
        titleStyle.fontColor =
            MainMenuTheme.PRIMARY_TEXT.cpy();

        Label.LabelStyle subtitleStyle = new Label.LabelStyle();

        subtitleStyle.font = subtitleFont;
        subtitleStyle.fontColor =
            MainMenuTheme.PRIMARY_TEXT.cpy();


        Label.LabelStyle footerStyle = new Label.LabelStyle();

        footerStyle.font = subtitleFont;
        footerStyle.fontColor =
            MainMenuTheme.SECONDARY_TEXT.cpy();

        Label titulo = new Label(
            MainMenuTheme.GAME_TITLE,
            titleStyle
        );

        Label subtitulo = new Label(
            MainMenuTheme.SUBTITLE,
            subtitleStyle
        );

        Label rodape = new Label(
            MainMenuTheme.FOOTER,
            footerStyle
        );


        rodape.setColor(
            MainMenuTheme.SECONDARY_TEXT
        );

        TextButton.TextButtonStyle buttonStyle =
            criarEstiloDosBotoes();

        TextButton novoJogo = criarBotao(
            MainMenuTheme.BUTTON_NEW_GAME,
            buttonStyle
        );

        TextButton opcoes = criarBotao(
            MainMenuTheme.BUTTON_OPTIONS,
            buttonStyle
        );

        TextButton sair = criarBotao(
            MainMenuTheme.BUTTON_EXIT,
            buttonStyle
        );

        configurarAcoes(novoJogo, opcoes, sair);

        Table root = new Table();

        root.setFillParent(true);

        stage.addActor(backgroundImage);

        backgroundImage.setColor(
            0.75f,
            0.75f,
            0.75f,
            1f
        );

        Table content = new Table();

        content.add(titulo)
            .padBottom(MainMenuTheme.TITLE_BOTTOM_SPACE)
            .row();

        content.add(subtitulo)
            .padBottom(MainMenuTheme.SUBTITLE_BOTTOM_SPACE)
            .row();

        Image decoration = new Image(
            new TextureRegionDrawable(
                new TextureRegion(decorationTexture)
            )
        );

        content.add(decoration)
            .width(MainMenuTheme.DECORATION_WIDTH)
            .height(MainMenuTheme.DECORATION_HEIGHT)
            .padBottom(MainMenuTheme.DECORATION_BOTTOM_SPACE)
            .row();

        content.add(novoJogo)
            .width(MainMenuTheme.BUTTON_WIDTH)
            .height(MainMenuTheme.BUTTON_HEIGHT)
            .padBottom(MainMenuTheme.BUTTON_BOTTOM_SPACE)
            .row();

        content.add(opcoes)
            .width(MainMenuTheme.BUTTON_WIDTH)
            .height(MainMenuTheme.BUTTON_HEIGHT)
            .padBottom(MainMenuTheme.BUTTON_BOTTOM_SPACE)
            .row();

        content.add(sair)
            .width(MainMenuTheme.BUTTON_WIDTH)
            .height(MainMenuTheme.BUTTON_HEIGHT)
            .row();

        root.add(content)
            .expand()
            .center()
            .row();

        root.add(rodape)
            .center()
            .padBottom(MainMenuTheme.FOOTER_BOTTOM_SPACE);

        stage.addActor(root);
    }

    private TextButton.TextButtonStyle criarEstiloDosBotoes() {

        TextButton.TextButtonStyle style =
            new TextButton.TextButtonStyle();

        style.font = buttonFont;

        style.fontColor =
            MainMenuTheme.PRIMARY_TEXT.cpy();

        style.up = new TextureRegionDrawable(
            new TextureRegion(buttonTexture)
        );

        style.over = new TextureRegionDrawable(
            new TextureRegion(buttonHoverTexture)
        );

        style.down = new TextureRegionDrawable(
            new TextureRegion(buttonPressedTexture)
        );

        return style;
    }

    private TextButton criarBotao(
        String texto,
        TextButton.TextButtonStyle style
    ) {

        TextButton botao = new TextButton(
            texto,
            style
        );


        return botao;
    }

    private void configurarAcoes(
        TextButton novoJogo,
        TextButton opcoes,
        TextButton sair
    ) {

        // Novo jogo.
        novoJogo.addListener(new ChangeListener() {

            @Override
            public void changed(
                ChangeEvent event,
                Actor actor
            ) {

                game.setScreen(
                    new ExplorationScreen(game)
                );
            }
        });


        // Opcoes.
        opcoes.addListener(new ChangeListener() {

            @Override
            public void changed(
                ChangeEvent event,
                Actor actor
            ) {

                Gdx.app.log(
                    "DG Crawler",
                    "Menu de opcoes ainda nao implementado."
                );
            }
        });


        // Sair.
        sair.addListener(new ChangeListener() {

            @Override
            public void changed(
                ChangeEvent event,
                Actor actor
            ) {

                Gdx.app.exit();
            }
        });
    }

    private Texture criarTextura(Color cor) {

        Pixmap pixmap = new Pixmap(
            1,
            1,
            Pixmap.Format.RGBA8888
        );

        pixmap.setColor(cor);
        pixmap.fill();

        Texture texture = new Texture(pixmap);

        pixmap.dispose();

        return texture;
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(
            MainMenuTheme.BACKGROUND
        );

        stage.act(
            Math.min(delta, 1f / 30f)
        );

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

        if (stage != null) {
            stage.dispose();
            stage = null;
        }

        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }

        if (titleFont != null) {
            titleFont.dispose();
            titleFont = null;
        }

        if (buttonFont != null) {
            buttonFont.dispose();
            buttonFont = null;
        }

        if (subtitleFont != null) {
            subtitleFont.dispose();
            subtitleFont = null;
        }

        if (backgroundTexture != null) {
            backgroundTexture.dispose();
            backgroundTexture = null;
        }

        if (buttonTexture != null) {
            buttonTexture.dispose();
            buttonTexture = null;
        }

        if (buttonHoverTexture != null) {
            buttonHoverTexture.dispose();
            buttonHoverTexture = null;
        }

        if (buttonPressedTexture != null) {
            buttonPressedTexture.dispose();
            buttonPressedTexture = null;
        }

        if (decorationTexture != null) {
            decorationTexture.dispose();
            decorationTexture = null;
        }
    }
}
