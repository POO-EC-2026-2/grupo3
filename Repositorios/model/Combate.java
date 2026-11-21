
package model;

import java.util.Scanner;

public class Combate {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Database database = new Database("rpg.db");

        try {

            HabilidadeRepository habilidadeRepo =
                new HabilidadeRepository(database);

            PlayerRepository playerRepo =
                new PlayerRepository(database);

            MonstroRepository monstroRepo =
                new MonstroRepository(database);

            System.out.println("===== INICIAR COMBATE =====");

            System.out.println("Digite o ID do jogador:");
            int idPlayer = scanner.nextInt();

            Player player = playerRepo.loadFromId(idPlayer);

            if (player == null) {
                System.out.println("Jogador não encontrado!");
                return;
            }

            System.out.println("Digite o ID do monstro:");
            int idMonstro = scanner.nextInt();

            Monstro monstro = monstroRepo.loadFromId(idMonstro);

            if (monstro == null) {
                System.out.println("Monstro não encontrado!");
                return;
            }

            System.out.println("\n===== PERSONAGENS CARREGADOS =====");

            System.out.println("Jogador: " + player.getName());
            System.out.println("HP: " + player.getHp() + "/" + player.getHpMax());
            System.out.println("STM: " + player.getStm() + "/" + player.getStmMax());

            System.out.println("\nMonstro:");
            System.out.println("HP: " + monstro.getHp() + "/" + monstro.getHpMax());
            System.out.println("STM: " + monstro.getStm() + "/" + monstro.getStmMax());
            System.out.println("Fraqueza: " + monstro.getFraqueza());

            int turno = 1;

            while (player.getHp() > 0 && monstro.getHp() > 0) {

                System.out.println("\n========== TURNO " + turno + " ==========");

                System.out.println("\n===== TURNO DO JOGADOR =====");

                player.processarEfeitosTurno();

                if (player.getHp() <= 0) {
                    break;
                }

                if (player.estaAtordoado()) {

                    System.out.println("O jogador está atordoado e perdeu o turno!");

                } else {

                    boolean acaoRealizada = false;

                    while (!acaoRealizada) {

                        System.out.println("\n===== " + player.getName() + " =====");
                        System.out.println("HP: " + player.getHp() + "/" + player.getHpMax());
                        System.out.println("STM: " + player.getStm() + "/" + player.getStmMax());

                        System.out.println("\n1 - Ataque básico");
                        System.out.println("2 - Skills");
                        System.out.println("3 - Inventário");
                        System.out.println("Escolha uma opção:");

                        int opcao = scanner.nextInt();

                        switch (opcao) {

                            case 1:

                                System.out.println("\nVocê utilizou o ataque básico!");

                                int danoBasico = player.atacar(9, monstro);

                                if (danoBasico > 0) {
                                    System.out.println("Você causou " + danoBasico + " de dano!");
                                }

                                acaoRealizada = true;
                                break;

                            case 2:

                                System.out.println("\n===== SKILLS EQUIPADAS =====");

                                int[] ataques = {
                                    player.getAtaque1(),
                                    player.getAtaque2(),
                                    player.getAtaque3(),
                                    player.getAtaque4()
                                };

                                for (int i = 0; i < ataques.length; i++) {

                                    System.out.print((i + 1) + " - ");

                                    if (ataques[i] <= 0) {
                                        System.out.println("Vazio");
                                    } else {

                                        Habilidade habilidade =
                                            habilidadeRepo.loadFromId(ataques[i]);

                                        if (habilidade == null) {
                                            System.out.println("Habilidade não encontrada!");
                                        } else {
                                            System.out.println(
                                                habilidade.getNomeSkill() +
                                                " | Custo: " +
                                                habilidade.getCusto() +
                                                " STM"
                                            );
                                        }
                                    }
                                }

                                System.out.println("0 - Voltar");
                                System.out.println("Escolha uma skill:");

                                int escolhaSkill = scanner.nextInt();

                                if (escolhaSkill == 0) {
                                    break;
                                }

                                if (escolhaSkill < 1 || escolhaSkill > 4) {
                                    System.out.println("Opção inválida!");
                                    break;
                                }

                                int idSkill = ataques[escolhaSkill - 1];

                                if (idSkill <= 0) {
                                    System.out.println("Espaço vazio!");
                                    break;
                                }

                                int danoSkill = player.atacar(idSkill, monstro);

                                if (danoSkill > 0) {
                                    System.out.println("Você causou " + danoSkill + " de dano!");
                                }

                                acaoRealizada = true;
                                break;

                            case 3:

                                boolean acaoInventario = false;

                                while (!acaoInventario) {

                                    System.out.println("\n===== INVENTÁRIO =====");

                                    System.out.println("Poções HP: " + player.getPotionsHp());
                                    System.out.println("Poções STM: " + player.getPotionsStm());

                                    System.out.println("\n1 - Trocar arma");
                                    System.out.println("2 - Usar poção de HP");
                                    System.out.println("3 - Usar poção de STM");
                                    System.out.println("0 - Voltar");

                                    int opcaoInventario = scanner.nextInt();

                                    switch (opcaoInventario) {

                                        case 1:

                                            player.trocarArma();

                                            System.out.println("Arma trocada!");

                                            acaoInventario = true;
                                            break;

                                        case 2:

                                            player.potionHp();

                                            acaoInventario = true;
                                            break;

                                        case 3:

                                            player.potionStm();

                                            acaoInventario = true;
                                            break;

                                        case 0:

                                            break;

                                        default:

                                            System.out.println("Opção inválida!");
                                            break;
                                    }

                                    if (opcaoInventario == 0) {
                                        break;
                                    }
                                }

                                if (acaoInventario) {
                                    acaoRealizada = true;
                                }

                                break;

                            default:

                                System.out.println("Opção inválida!");
                                break;
                        }
                    }
                }

                if (monstro.getHp() <= 0) {
                    break;
                }

                System.out.println("\n===== TURNO DO MONSTRO =====");

                monstro.processarEfeitosTurno();

                if (monstro.getHp() <= 0) {
                    break;
                }

                if (monstro.estaAtordoado()) {

                    System.out.println("O monstro está atordoado e perdeu o turno!");

                } else {

                    int dano = monstro.atacar(player);

                    if (dano > 0) {
                        System.out.println("O monstro causou " + dano + " de dano!");
                    }
                }

                System.out.println("\n===== STATUS DO COMBATE =====");

                System.out.println("Jogador: " + player.getHp() + "/" + player.getHpMax() + " HP");
                System.out.println("Jogador STM: " + player.getStm() + "/" + player.getStmMax());

                System.out.println("Poções HP: " + player.getPotionsHp());
                System.out.println("Poções STM: " + player.getPotionsStm());

                System.out.println("Monstro: " + monstro.getHp() + "/" + monstro.getHpMax() + " HP");
                System.out.println("Monstro STM: " + monstro.getStm() + "/" + monstro.getStmMax());

                turno++;
            }

            System.out.println("\n========== FIM DO COMBATE ==========");

            if (player.getHp() <= 0 && monstro.getHp() <= 0) {
                System.out.println("Empate! Os dois morreram!");
            } else if (player.getHp() <= 0) {
                System.out.println("O monstro venceu!");
            } else {
                System.out.println("O jogador venceu!");
            }

            System.out.println("Total de turnos: " + turno);

        } finally {

            database.close();
            scanner.close();
        }
    }
}
