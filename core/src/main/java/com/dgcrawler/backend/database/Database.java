package com.dgcrawler.backend.database;

import java.sql.SQLException;
import java.io.File;

import com.j256.ormlite.jdbc.JdbcConnectionSource;

public class Database {

    private final String databaseName;
    private JdbcConnectionSource connection;

    public Database(String databaseName) {
        this.databaseName = databaseName;
    }

    public JdbcConnectionSource getConnection() throws SQLException {

        if (databaseName == null || databaseName.trim().isEmpty()) {
            throw new SQLException("Database name is null or empty");
        }

        if (connection == null) {

            File arquivo = new File(databaseName);
            File pasta = arquivo.getAbsoluteFile().getParentFile();

            // Garante que a pasta do banco exista.
            if (pasta != null && !pasta.exists()) {
                if (!pasta.mkdirs()) {
                    throw new SQLException(
                        "Não foi possível criar a pasta do banco: "
                            + pasta.getAbsolutePath()
                    );
                }
            }

            System.out.println(
                "Caminho do banco: " + arquivo.getAbsolutePath()
            );

            connection = new JdbcConnectionSource(
                "jdbc:sqlite:" + arquivo.getAbsolutePath()
            );

            System.out.println("Opened database successfully");
        }

        return connection;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Database closed successfully");
            } catch (Exception e) {
                System.err.println(
                    "Erro ao fechar o banco: " + e.getMessage()
                );
            }
        }
    }
}
