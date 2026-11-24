
package com.dgcrawler.view.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.dgcrawler.model.MonstroModel;
import com.dgcrawler.model.PlayerModel;

import java.util.List;

public class MonstroRenderer {

    private static final String BASE_PATH = "enemies/zombie/";

    private static final int TOTAL_FRAMES = 6;
    private static final float DURACAO_FRAME = 0.15f;

    private Texture texturaDown;
    private Texture texturaUp;
    private Texture texturaSide;
    private Texture texturaSideLeft;

    private Animation<TextureRegion> animacaoDown;
    private Animation<TextureRegion> animacaoUp;
    private Animation<TextureRegion> animacaoSide;
    private Animation<TextureRegion> animacaoSideLeft;

    private float tempoAnimacao;

    public MonstroRenderer() {
        carregarTexturas();
        configurarAnimacoes();
        tempoAnimacao = 0;
    }

    private void carregarTexturas() {

        texturaDown = carregarTextura(
            "Zombie_Small_Down_Idle-Sheet6.png"
        );

        texturaUp = carregarTextura(
            "Zombie_Small_Up_Idle-Sheet6.png"
        );

        texturaSide = carregarTextura(
            "Zombie_Small_Side_Idle-Sheet6.png"
        );

        texturaSideLeft = carregarTextura(
            "Zombie_Small_Side-left_Idle-Sheet6.png"
        );
    }

    private Texture carregarTextura(String nomeArquivo) {

        Texture textura = new Texture(
            Gdx.files.internal(BASE_PATH + nomeArquivo)
        );

        textura.setFilter(
            Texture.TextureFilter.Nearest,
            Texture.TextureFilter.Nearest
        );

        return textura;
    }

    private Animation<TextureRegion> criarAnimacao(
        Texture textura,
        int totalFrames
    ) {

        int larguraFrame = textura.getWidth() / totalFrames;
        int alturaFrame = textura.getHeight();

        TextureRegion[][] quadros = TextureRegion.split(
            textura,
            larguraFrame,
            alturaFrame
        );

        TextureRegion[] frames = new TextureRegion[totalFrames];

        System.arraycopy(
            quadros[0],
            0,
            frames,
            0,
            totalFrames
        );

        Animation<TextureRegion> animacao = new Animation<>(
            DURACAO_FRAME,
            frames
        );

        animacao.setPlayMode(Animation.PlayMode.LOOP);

        return animacao;
    }

    private void configurarAnimacoes() {

        animacaoDown = criarAnimacao(texturaDown, TOTAL_FRAMES);
        animacaoUp = criarAnimacao(texturaUp, TOTAL_FRAMES);
        animacaoSide = criarAnimacao(texturaSide, TOTAL_FRAMES);
        animacaoSideLeft = criarAnimacao(
            texturaSideLeft,
            TOTAL_FRAMES
        );
    }

    public void update(float delta) {
        tempoAnimacao += delta;
    }

    public void render(
        SpriteBatch batch,
        List<MonstroModel> inimigos,
        PlayerModel player
    ) {

        batch.begin();

        for (MonstroModel inimigo : inimigos) {

            TextureRegion frame = obterFrame(inimigo, player);

            float largura = frame.getRegionWidth();
            float altura = frame.getRegionHeight();

            float x = inimigo.getX() - largura / 2f;
            float y = inimigo.getY() - altura / 2f;

            batch.draw(frame, x, y, largura, altura);
        }

        batch.end();
    }

    private TextureRegion obterFrame(
        MonstroModel inimigo,
        PlayerModel player
    ) {

        float diferencaX = player.getX() - inimigo.getX();
        float diferencaY = player.getY() - inimigo.getY();

        if (Math.abs(diferencaX) > Math.abs(diferencaY)) {

            if (diferencaX >= 0) {
                return animacaoSide.getKeyFrame(tempoAnimacao);
            }

            return animacaoSideLeft.getKeyFrame(tempoAnimacao);
        }

        if (diferencaY >= 0) {
            return animacaoUp.getKeyFrame(tempoAnimacao);
        }

        return animacaoDown.getKeyFrame(tempoAnimacao);
    }

    public void dispose() {

        if (texturaDown != null) texturaDown.dispose();
        if (texturaUp != null) texturaUp.dispose();
        if (texturaSide != null) texturaSide.dispose();
        if (texturaSideLeft != null) texturaSideLeft.dispose();
    }
}
