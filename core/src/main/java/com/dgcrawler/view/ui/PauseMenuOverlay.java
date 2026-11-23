
package com.dgcrawler.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.dgcrawler.view.screens.MainMenuScreen.getBitmapFont;

public class PauseMenuOverlay {

    private final Stage stage;

    private Texture backgroundTexture;
    private Texture dimTexture;

    private BitmapFont titleFont;
    private BitmapFont buttonFont;

    private final Runnable onResume;
    private final Runnable onOptions;
    private final Runnable onMainMenu;
    private final Runnable onExit;

    public PauseMenuOverlay(
        Runnable onResume,
        Runnable onOptions,
        Runnable onMainMenu,
        Runnable onExit) {

        this.onResume = onResume;
        this.onOptions = onOptions;
        this.onMainMenu = onMainMenu;
        this.onExit = onExit;

        stage = new Stage(new FitViewport(640, 360));

        carregarRecursos();
        criarInterface();

        stage.getRoot().setVisible(false);
    }

    private void carregarRecursos() {

        backgroundTexture = new Texture(
            Gdx.files.internal("ui/pause_menu/pauseMenu.png")
        );

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();

        dimTexture = new Texture(pixmap);
        pixmap.dispose();

        titleFont = carregarFonte("fonts/Cinzel-Bold.ttf", 96);
        buttonFont = carregarFonte("fonts/Cinzel-Regular.ttf", 48);

        titleFont.getData().setScale(1f / 3f);
        buttonFont.getData().setScale(1f / 3f);
    }

    private BitmapFont carregarFonte(String caminho, int tamanho) {

        return getBitmapFont(caminho, tamanho);
    }

    private void criarInterface() {

        Image escurecimento = new Image(
            new TextureRegionDrawable(new TextureRegion(dimTexture))
        );

        escurecimento.setFillParent(true);
        escurecimento.setColor(0f, 0f, 0f, 0.55f);

        stage.addActor(escurecimento);

        Image pergaminho = new Image(backgroundTexture);

        Table painel = new Table();
        painel.setSize(260, 300);

        Label.LabelStyle titleStyle = new Label.LabelStyle(
            titleFont,
            new Color(0.25f, 0.15f, 0.08f, 1f)
        );

        Label titulo = new Label("PAUSADO", titleStyle);
        titulo.setAlignment(Align.center);

        TextButton.TextButtonStyle buttonStyle =
            new TextButton.TextButtonStyle();

        buttonStyle.font = buttonFont;
        buttonStyle.fontColor = new Color(0.28f, 0.18f, 0.10f, 1f);
        buttonStyle.overFontColor = new Color(0.55f, 0.32f, 0.12f, 1f);
        buttonStyle.downFontColor = new Color(0.12f, 0.08f, 0.04f, 1f);

        TextButton continuar = criarBotao(
            "CONTINUAR", buttonStyle, onResume
        );

        TextButton opcoes = criarBotao(
            "OPÇÕES", buttonStyle, onOptions
        );

        TextButton menuPrincipal = criarBotao(
            "VOLTAR AO MENU", buttonStyle, onMainMenu
        );

        TextButton sair = criarBotao(
            "SAIR DO JOGO", buttonStyle, onExit
        );

        painel.add(titulo).width(210).height(42).padBottom(12);
        painel.row();

        painel.add(continuar).width(210).height(34).padBottom(7);
        painel.row();

        painel.add(opcoes).width(210).height(34).padBottom(7);
        painel.row();

        painel.add(menuPrincipal).width(210).height(34).padBottom(7);
        painel.row();

        painel.add(sair).width(210).height(34);

        Table conteudo = new Table();
        conteudo.setFillParent(true);
        conteudo.add(pergaminho).size(260, 300);

        Table botoes = new Table();
        botoes.setFillParent(true);
        botoes.add(painel).size(260, 300);

        stage.addActor(conteudo);
        stage.addActor(botoes);
    }

    private TextButton criarBotao(
        String texto,
        TextButton.TextButtonStyle estilo,
        Runnable acao) {

        TextButton botao = new TextButton(texto, estilo);

        botao.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (acao != null) {
                    acao.run();
                }
            }
        });

        return botao;
    }

    public void setVisible(boolean visible) {
        stage.getRoot().setVisible(visible);
    }

    public void render(float delta) {
        stage.act(Math.min(delta, 0.1f));
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public Stage getStage() {
        return stage;
    }

    public void dispose() {
        stage.dispose();

        backgroundTexture.dispose();
        dimTexture.dispose();

        titleFont.dispose();
        buttonFont.dispose();
    }
}
