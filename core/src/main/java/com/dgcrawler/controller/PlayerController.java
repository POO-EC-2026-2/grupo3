
package com.dgcrawler.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import com.dgcrawler.model.PlayerModel;


public class PlayerController {

    private final PlayerModel player;
    private final TiledMapTileLayer camadaColisoes;
    private boolean estaAndando;


    public PlayerController(
        PlayerModel player,
        TiledMapTileLayer camadaColisoes
    ) {

        this.player = player;
        this.camadaColisoes = camadaColisoes;

        this.estaAndando = false;
    }

    public void update(float delta) {

        float direcaoX = 0;
        float direcaoY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            direcaoY += 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            direcaoY -= 1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            direcaoX -= 1;
            player.setOlhandoParaDireita(false);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            direcaoX += 1;
            player.setOlhandoParaDireita(true);
        }

        if (direcaoX != 0 && direcaoY != 0) {

            direcaoX *= 0.7071f;
            direcaoY *= 0.7071f;
        }

        float movimentoX =
            direcaoX * player.getVelocidade() * delta;

        float movimentoY =
            direcaoY * player.getVelocidade() * delta;


        float novaPosicaoX = player.getX() + movimentoX;

        if (podeMover(novaPosicaoX, player.getY())) {

            player.setPosition(
                novaPosicaoX,
                player.getY()
            );
        }

        float novaPosicaoY = player.getY() + movimentoY;

        if (podeMover(player.getX(), novaPosicaoY)) {

            player.setPosition(
                player.getX(),
                novaPosicaoY
            );
        }

        estaAndando = direcaoX != 0 || direcaoY != 0;
    }

    private boolean podeMover(float x, float y) {

        float raioJogador = player.getRaioColisao();

        float esquerda = x - raioJogador;
        float direita = x + raioJogador;

        float baixo = y - raioJogador;
        float cima = y + raioJogador;


        return temColisao(esquerda, baixo)
            && temColisao(esquerda, cima)
            && temColisao(direita, baixo)
            && temColisao(direita, cima);
    }

    private boolean temColisao(float x, float y) {

        int coluna = (int) Math.floor(
            x / camadaColisoes.getTileWidth()
        );

        int linha = (int) Math.floor(
            y / camadaColisoes.getTileHeight()
        );

        if (coluna < 0 || linha < 0 ||
            coluna >= camadaColisoes.getWidth() ||
            linha >= camadaColisoes.getHeight()) {

            return false;
        }

        return camadaColisoes.getCell(coluna, linha) == null;
    }

    public boolean isEstaAndando() {
        return estaAndando;
    }


    public PlayerModel getPlayer() {
        return player;
    }
}
