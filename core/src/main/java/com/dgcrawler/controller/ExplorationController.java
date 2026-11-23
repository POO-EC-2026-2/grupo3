
package com.dgcrawler.controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dgcrawler.model.PlayerModel;
import com.dgcrawler.model.ExplorationState;

public class ExplorationController {

    private final PlayerModel player;
    private final PlayerController playerController;
    private final OrthographicCamera camera;

    private final float larguraMapa;
    private final float alturaMapa;

    private ExplorationState estado;

    public ExplorationController(
        PlayerModel player,
        PlayerController playerController,
        OrthographicCamera camera,
        float larguraMapa,
        float alturaMapa
    ) {
        this.player = player;
        this.playerController = playerController;
        this.camera = camera;
        this.larguraMapa = larguraMapa;
        this.alturaMapa = alturaMapa;

        this.estado = ExplorationState.EXPLORANDO;
    }

    public void update(float delta, Viewport viewport) {

        if (estado != ExplorationState.EXPLORANDO) {
            return;
        }

        playerController.update(delta);

        atualizarCamera(viewport);
    }

    private void atualizarCamera(Viewport viewport) {

        camera.position.set(player.getX(), player.getY(), 0);

        float metadeLargura = viewport.getWorldWidth() / 2f;
        float metadeAltura = viewport.getWorldHeight() / 2f;

        if (larguraMapa <= viewport.getWorldWidth()) {

            camera.position.x = larguraMapa / 2f;

        } else {

            camera.position.x = Math.clamp(
                camera.position.x,
                metadeLargura,
                larguraMapa - metadeLargura
            );
        }

        if (alturaMapa <= viewport.getWorldHeight()) {

            camera.position.y = alturaMapa / 2f;

        } else {

            camera.position.y = Math.clamp(
                camera.position.y,
                metadeAltura,
                alturaMapa - metadeAltura
            );
        }

        camera.update();
    }

    public void pausar() {

        if (estado == ExplorationState.EXPLORANDO) {
            estado = ExplorationState.PAUSADO;
        }
    }

    public void retomar() {

        if (estado == ExplorationState.PAUSADO) {
            estado = ExplorationState.EXPLORANDO;
        }
    }

    public void alternarPausa() {

        if (estado == ExplorationState.EXPLORANDO) {

            pausar();

        } else if (estado == ExplorationState.PAUSADO) {

            retomar();
        }
    }

    public ExplorationState getEstado() {

        return estado;
    }

    public boolean isEstaAndando() {

        return estado == ExplorationState.EXPLORANDO
            && playerController.isEstaAndando();
    }
}
