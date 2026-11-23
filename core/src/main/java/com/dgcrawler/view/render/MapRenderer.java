package com.dgcrawler.view.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapRenderer {

    private static final String CAMINHO_MAPA = "maps/ambiente.tmx";
    private static final String NOME_CAMADA_COLISOES = "Colisoes";

    private TiledMap mapa;
    private OrthogonalTiledMapRenderer renderizadorMapa;
    private TiledMapTileLayer camadaColisoes;

    private float larguraMapa;
    private float alturaMapa;

    public MapRenderer() {

        carregarMapa();
    }

    private void carregarMapa() {

        mapa = new TmxMapLoader().load(CAMINHO_MAPA);

        renderizadorMapa = new OrthogonalTiledMapRenderer(mapa);

        camadaColisoes = (TiledMapTileLayer)
            mapa.getLayers().get(NOME_CAMADA_COLISOES);

        if (camadaColisoes == null) {
            throw new IllegalStateException(
                "A camada Colisoes não foi encontrada!"
            );
        }

        larguraMapa = camadaColisoes.getWidth()
            * camadaColisoes.getTileWidth();

        alturaMapa = camadaColisoes.getHeight()
            * camadaColisoes.getTileHeight();
    }

    public void render(OrthographicCamera camera) {

        renderizadorMapa.setView(camera);

        renderizadorMapa.render();
    }

    public TiledMapTileLayer getCamadaColisoes() {

        return camadaColisoes;
    }

    public float getLarguraMapa() {

        return larguraMapa;
    }

    public float getAlturaMapa() {

        return alturaMapa;
    }

    public void dispose() {

        if (renderizadorMapa != null) {
            renderizadorMapa.dispose();
        }

        if (mapa != null) {
            mapa.dispose();
        }
    }
}
