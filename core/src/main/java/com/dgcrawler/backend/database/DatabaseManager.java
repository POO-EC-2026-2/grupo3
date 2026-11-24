package com.dgcrawler.backend.database;

import com.dgcrawler.backend.repository.PlayerRepository;
import com.dgcrawler.backend.repository.MonstroRepository;
import com.dgcrawler.backend.repository.HabilidadeRepository;

import java.sql.SQLException;

public class DatabaseManager {

    private final Database database;

    private PlayerRepository playerRepository;
    private MonstroRepository monstroRepository;
    private HabilidadeRepository habilidadeRepository;

    public DatabaseManager(String databasePath) {
        database = new Database(databasePath);
    }

    public void initialize() throws SQLException {

        try {
            database.getConnection();

            playerRepository = new PlayerRepository(database);
            monstroRepository = new MonstroRepository(database);
            habilidadeRepository = new HabilidadeRepository(database);

        } catch (SQLException | RuntimeException e) {

            database.close();

            playerRepository = null;
            monstroRepository = null;
            habilidadeRepository = null;

            throw new SQLException(
                "Falha ao inicializar o banco de dados.",
                e
            );
        }
    }

    public PlayerRepository getPlayerRepository() {
        return playerRepository;
    }

    public MonstroRepository getMonstroRepository() {
        return monstroRepository;
    }

    public HabilidadeRepository getHabilidadeRepository() {
        return habilidadeRepository;
    }

    public void close() {
        database.close();
    }
}
