
package com.dgcrawler.controller;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.dgcrawler.backend.model.Monstro;
import com.dgcrawler.backend.repository.MonstroRepository;
import com.dgcrawler.model.MonstroModel;
import com.badlogic.gdx.maps.objects.PointMapObject;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MonstroController {

    private final List<MonstroModel> inimigos;

    public MonstroController(
        MapObjects objetosSpawn,
        MonstroRepository monstroRepository
    ) {

        inimigos = new ArrayList<>();

        carregarInimigos(objetosSpawn, monstroRepository);
    }

    private void carregarInimigos(
        MapObjects objetosSpawn,
        MonstroRepository monstroRepository
    ) {

        if (objetosSpawn == null || monstroRepository == null) {
            throw new IllegalArgumentException(
                "Os objetos de spawn e o repositório não podem ser nulos."
            );
        }

        List<Monstro> monstrosDisponiveis =
            monstroRepository.loadAll();

        if (monstrosDisponiveis == null) {
            throw new IllegalStateException(
                "Não foi possível carregar os monstros do banco."
            );
        }

        for (MapObject objeto : objetosSpawn) {

            String tipo = objeto.getProperties().get(
                "tipo",
                String.class
            );

            String spawnId = objeto.getProperties().get(
                "spawnId",
                String.class
            );

            if (tipo == null || tipo.isBlank()) {
                System.out.println(
                    "Objeto de spawn ignorado: propriedade tipo ausente."
                );
                continue;
            }

            String nomeMonstro = resolverNomeMonstro(tipo);

            Monstro monstroEncontrado = null;

            for (Monstro monstro : monstrosDisponiveis) {

                if (monstro != null &&
                    monstro.getNomeMonstro() != null &&
                    monstro.getNomeMonstro().equalsIgnoreCase(nomeMonstro)) {

                    monstroEncontrado = monstro;
                    break;
                }
            }

            if (monstroEncontrado == null) {
                System.out.println(
                    "Monstro não encontrado no banco: " + nomeMonstro
                );
                continue;
            }

            if (!(objeto instanceof PointMapObject)) {
                System.out.println("Objeto de spawn ignorado: não é um ponto.");
                continue;
            }

            PointMapObject objetoPonto = (PointMapObject) objeto;

            Vector2 posicao = objetoPonto.getPoint();

            float x = posicao.x;
            float y = posicao.y;

            MonstroModel inimigo = new MonstroModel(
                monstroEncontrado,
                spawnId,
                x,
                y
            );

            inimigos.add(inimigo);

            System.out.println(
                "Inimigo carregado: " + nomeMonstro +
                    " | Spawn: " + spawnId +
                    " | X: " + x +
                    " | Y: " + y
            );
        }
    }

    private String resolverNomeMonstro(String tipo) {

        String tipoNormalizado = tipo.trim().toLowerCase(Locale.ROOT);

        if (tipoNormalizado.isEmpty()) {
            return "";
        }

        return tipoNormalizado.substring(0, 1).toUpperCase(Locale.ROOT)
            + tipoNormalizado.substring(1);
    }

    public List<MonstroModel> getInimigos() {
        return inimigos;
    }
}
