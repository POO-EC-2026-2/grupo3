
package com.dgcrawler.backend.database;

import com.dgcrawler.backend.model.Habilidade;
import com.dgcrawler.backend.model.Player;
import com.dgcrawler.backend.model.Monstro;

import com.dgcrawler.backend.repository.HabilidadeRepository;
import com.dgcrawler.backend.repository.PlayerRepository;
import com.dgcrawler.backend.repository.MonstroRepository;

import java.util.ArrayList;
import java.util.List;

public class GameDataInitializer {

    private final HabilidadeRepository habilidadeRepository;
    private final PlayerRepository playerRepository;
    private final MonstroRepository monstroRepository;

    public GameDataInitializer(DatabaseManager databaseManager) {

        this.habilidadeRepository =
            databaseManager.getHabilidadeRepository();

        this.playerRepository =
            databaseManager.getPlayerRepository();

        this.monstroRepository =
            databaseManager.getMonstroRepository();
    }

    public void inicializarJogadorInicial() {

        List<Player> jogadores = playerRepository.loadAll();

        if (jogadores != null && !jogadores.isEmpty()) {
            System.out.println("Jogador já cadastrado.");
            return;
        }

        Habilidade corteDuplo = buscarHabilidade("Corte Duplo");
        Habilidade corteForte = buscarHabilidade("Corte Forte");
        Habilidade corteHeroico = buscarHabilidade("Corte Heroico");
        Habilidade corteEscarlate = buscarHabilidade("Corte Escarlate");

        if (corteDuplo == null ||
            corteForte == null ||
            corteHeroico == null ||
            corteEscarlate == null) {

            throw new IllegalStateException(
                "Não foi possível encontrar todas as habilidades da espada."
            );
        }

        Player player = new Player();

        player.setName("Jogador");

        player.setHpMax(20);
        player.setHp(20);

        player.setStmMax(10);
        player.setStm(10);

        player.setAgility(10);

        player.setPotionsHp(3);
        player.setPotionsStm(3);

        player.setAtaque1(corteDuplo.getIdSkill());
        player.setAtaque2(corteForte.getIdSkill());
        player.setAtaque3(corteHeroico.getIdSkill());
        player.setAtaque4(corteEscarlate.getIdSkill());

        playerRepository.create(player);

        System.out.println("Jogador inicial cadastrado.");
    }

    public void inicializarMonstrosIniciais() {

        List<Monstro> monstros = monstroRepository.loadAll();

        if (monstros != null) {
            for (Monstro monstro : monstros) {

                if (monstro != null &&
                    "Zumbi".equalsIgnoreCase(monstro.getNomeMonstro())) {

                    System.out.println("Zumbi já cadastrado.");
                    return;
                }
            }
        }

        Habilidade mordida = buscarHabilidade("Mordida");
        Habilidade arranhao = buscarHabilidade("Arranhão");

        if (mordida == null || arranhao == null) {
            throw new IllegalStateException(
                "Não foi possível encontrar as habilidades do Zumbi."
            );
        }

        Monstro zumbi = new Monstro(
            "Zumbi",
            "Slash",
            7,
            7,
            7,
            mordida.getIdSkill(),
            arranhao.getIdSkill(),
            0,
            0
        );

        monstroRepository.create(zumbi);

        // Confirma se o monstro realmente foi salvo.
        List<Monstro> monstrosCadastrados = monstroRepository.loadAll();

        boolean encontrado = false;

        if (monstrosCadastrados != null) {
            for (Monstro monstro : monstrosCadastrados) {

                if (monstro != null &&
                    "Zumbi".equalsIgnoreCase(monstro.getNomeMonstro())) {

                    encontrado = true;
                    break;
                }
            }
        }

        if (!encontrado) {
            throw new IllegalStateException(
                "Não foi possível cadastrar o Zumbi no banco de dados."
            );
        }

        System.out.println("Zumbi inicial cadastrado.");
    }

    private Habilidade buscarHabilidade(String nome) {

        List<Habilidade> habilidades =
            habilidadeRepository.loadAll();

        if (habilidades == null) {
            return null;
        }

        for (Habilidade habilidade : habilidades) {

            if (habilidade != null &&
                habilidade.getNomeSkill() != null &&
                habilidade.getNomeSkill().equalsIgnoreCase(nome)) {

                return habilidade;
            }
        }

        return null;
    }

    public void inicializarHabilidades(
        List<Habilidade> habilidadesIniciais
    ) {

        if (habilidadesIniciais == null ||
            habilidadesIniciais.isEmpty()) {
            return;
        }

        List<Habilidade> habilidadesExistentes =
            habilidadeRepository.loadAll();

        if (habilidadesExistentes == null) {
            habilidadesExistentes = new ArrayList<>();
        }

        for (Habilidade habilidadeInicial : habilidadesIniciais) {

            if (habilidadeInicial == null) {
                continue;
            }

            boolean jaExiste = false;

            for (Habilidade habilidadeExistente : habilidadesExistentes) {

                if (habilidadeExistente != null &&
                    habilidadeExistente.getNomeSkill() != null &&
                    habilidadeInicial.getNomeSkill() != null &&
                    habilidadeExistente.getNomeSkill().equalsIgnoreCase(
                        habilidadeInicial.getNomeSkill()
                    )) {

                    jaExiste = true;
                    break;
                }
            }

            if (!jaExiste) {
                habilidadeRepository.create(habilidadeInicial);
            }
        }
    }

    // =====================================================
    // HABILIDADES INICIAIS DO JOGO
    // =====================================================

    public void inicializarHabilidadesIniciais() {

        List<Habilidade> habilidades = List.of(

            // Habilidades da espada
            new Habilidade(3, false, false, false, false, false,
                "Slash", "Corte Duplo", 3),

            new Habilidade(2, false, false, false, false, false,
                "Slash", "Corte Forte", 1),

            new Habilidade(3, true, false, false, false, false,
                "Slash", "Corte Heroico", 4),

            new Habilidade(8, false, true, false, false, false,
                "Slash", "Corte Escarlate", 5),

            // Habilidades do martelo
            new Habilidade(4, false, false, false, false, false,
                "Blunt", "Golpe Esmagador", 2),

            new Habilidade(3, false, false, false, false, false,
                "Blunt", "Varredura de Impacto", 1),

            new Habilidade(4, true, false, false, false, false,
                "Blunt", "Quebra-Escudo", 4),

            new Habilidade(10, true, false, false, false, false,
                "Blunt", "Investida Brutal", 6),

            // Habilidades do Zumbi
            new Habilidade(1, false, false, false, false, false,
                "Slash", "Mordida", 0),

            new Habilidade(3, false, false, false, false, false,
                "Slash", "Arranhão", 0)
        );

        inicializarHabilidades(habilidades);

        List<Habilidade> cadastradas = habilidadeRepository.loadAll();

        System.out.println(
            "Habilidades cadastradas: " +
                (cadastradas == null ? 0 : cadastradas.size())
        );

        if (cadastradas != null) {
            for (Habilidade habilidade : cadastradas) {

                System.out.println(
                    habilidade.getIdSkill() + " - " +
                        habilidade.getNomeSkill()
                );
            }
        }
    }
}
