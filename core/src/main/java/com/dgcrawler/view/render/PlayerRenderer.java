
package com.dgcrawler.view.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.Gdx;

import com.dgcrawler.model.PlayerModel;


public class PlayerRenderer {

    private static final String IDLE_PATH = "characters/player/Knight/knight_idle.png";
    private static final String WALK_PATH = "characters/player/Knight/knight_walk-Sheet.png";
    private static final int TOTAL_FRAMES_WALK = 8;
    private static final float DURACAO_FRAME_WALK = 0.10f;
    private static final float OFFSET_CORPO_ESQUERDA = 0.65f;
    private static final float OFFSET_CORPO_DIREITA = 0.35f;
    private Texture texturaIdle;
    private Texture texturaWalk;
    private TextureRegion frameIdle;
    private Animation<TextureRegion> animacaoWalk;
    private float tempoAnimacao;
    private boolean estaAndando;

    public PlayerRenderer() {

        carregarTexturas();

        configurarAnimacoes();

        tempoAnimacao = 0;
        estaAndando = false;
    }

    private void carregarTexturas() {

        texturaIdle = new Texture(
            Gdx.files.internal(IDLE_PATH)
        );

        texturaWalk = new Texture(
            Gdx.files.internal(WALK_PATH)
        );

        texturaIdle.setFilter(
            Texture.TextureFilter.Nearest,
            Texture.TextureFilter.Nearest
        );

        texturaWalk.setFilter(
            Texture.TextureFilter.Nearest,
            Texture.TextureFilter.Nearest
        );
    }

    private void configurarAnimacoes() {

        frameIdle = new TextureRegion(texturaIdle);

        int larguraFrame =
            texturaWalk.getWidth() / TOTAL_FRAMES_WALK;

        int alturaFrame =
            texturaWalk.getHeight();


        TextureRegion[][] quadros = TextureRegion.split(
            texturaWalk,
            larguraFrame,
            alturaFrame
        );


        TextureRegion[] framesWalk =
            new TextureRegion[TOTAL_FRAMES_WALK];

        System.arraycopy(
            quadros[0],
            0,
            framesWalk,
            0,
            TOTAL_FRAMES_WALK
        );


        animacaoWalk = new Animation<>(
            DURACAO_FRAME_WALK,
            framesWalk
        );

        animacaoWalk.setPlayMode(
            Animation.PlayMode.LOOP
        );
    }

    public void update(float delta, boolean andando) {

        estaAndando = andando;

        if (estaAndando) {

            tempoAnimacao += delta;

        } else {

            tempoAnimacao = 0;
        }
    }

    public void render(
        SpriteBatch batch,
        PlayerModel player
    ) {

        TextureRegion frameAtual = estaAndando
            ? animacaoWalk.getKeyFrame(tempoAnimacao)
            : frameIdle;


        float largura = frameAtual.getRegionWidth();
        float altura = frameAtual.getRegionHeight();


        float offsetCorpoX = player.isOlhandoParaDireita()
            ? largura * OFFSET_CORPO_DIREITA
            : largura * OFFSET_CORPO_ESQUERDA;


        float posDesenhoX = player.getX() - offsetCorpoX;

        float posDesenhoY = player.getY() - altura / 2f;


        batch.begin();


        if (player.isOlhandoParaDireita()) {

            batch.draw(
                frameAtual,
                posDesenhoX + largura,
                posDesenhoY,
                -largura,
                altura
            );

        } else {

            batch.draw(
                frameAtual,
                posDesenhoX,
                posDesenhoY,
                largura,
                altura
            );
        }


        batch.end();
    }

    public void dispose() {

        if (texturaIdle != null) {
            texturaIdle.dispose();
        }

        if (texturaWalk != null) {
            texturaWalk.dispose();
        }
    }
}
