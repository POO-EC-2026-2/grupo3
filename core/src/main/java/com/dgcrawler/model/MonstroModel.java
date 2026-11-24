
package com.dgcrawler.model;

import com.dgcrawler.backend.model.Monstro;

public class MonstroModel {

    private final Monstro monstro;
    private final String spawnId;

    private float x;
    private float y;

    public MonstroModel(Monstro monstro, String spawnId, float x, float y) {

        if (monstro == null) {
            throw new IllegalArgumentException(
                "O monstro não pode ser nulo."
            );
        }

        this.monstro = monstro;
        this.spawnId = spawnId;
        this.x = x;
        this.y = y;
    }

    public Monstro getMonstro() {
        return monstro;
    }

    public String getSpawnId() {
        return spawnId;
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
}
