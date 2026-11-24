
package com.dgcrawler.view.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapRenderer {

    private static final String CAMINHO_MAPA = "maps/ambiente.tmx";
    private static final String NOME_CAMADA_COLISOES = "Colisoes";
    private static final String NOME_CAMADA_SPAWN = "SpawnInimigos";

    private TiledMap mapa;
    private OrthogonalTiledMapRenderer renderizadorMapa;
    private TiledMapTileLayer camadaColisoes;
    private MapLayer camadaSpawnInimigos;

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

        camadaSpawnInimigos =
            mapa.getLayers().get(NOME_CAMADA_SPAWN);

        if (camadaSpawnInimigos == null) {
            throw new IllegalStateException(
                "A camada SpawnInimigos não foi encontrada!"
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

    public MapObjects getObjetosSpawn() {

        return camadaSpawnInimigos.getObjects();
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
