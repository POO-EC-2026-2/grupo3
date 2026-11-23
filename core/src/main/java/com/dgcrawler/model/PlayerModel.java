
package com.dgcrawler.model;

public class PlayerModel {

    public static final float POSICAO_INICIAL_X = 240f;
    public static final float POSICAO_INICIAL_Y = 240f;

    public static final float VELOCIDADE_PADRAO = 180f;
    public static final float RAIO_COLISAO_PADRAO = 8f;


    private float x;
    private float y;

    private float velocidade;
    private float raioColisao;

    private boolean olhandoParaDireita;

    public PlayerModel() {

        this.x = POSICAO_INICIAL_X;
        this.y = POSICAO_INICIAL_Y;

        this.velocidade = VELOCIDADE_PADRAO;
        this.raioColisao = RAIO_COLISAO_PADRAO;

        this.olhandoParaDireita = false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(float velocidade) {

        if (velocidade < 0) {
            throw new IllegalArgumentException(
                "A velocidade não pode ser negativa."
            );
        }

        this.velocidade = velocidade;
    }

    public float getRaioColisao() {
        return raioColisao;
    }

    public void setRaioColisao(float raioColisao) {

        if (raioColisao < 0) {
            throw new IllegalArgumentException(
                "O raio de colisão não pode ser negativo."
            );
        }

        this.raioColisao = raioColisao;
    }

    public boolean isOlhandoParaDireita() {
        return olhandoParaDireita;
    }

    public void setOlhandoParaDireita(boolean olhandoParaDireita) {
        this.olhandoParaDireita = olhandoParaDireita;
    }
}

