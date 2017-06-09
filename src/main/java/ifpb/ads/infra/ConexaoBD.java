/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.ads.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author joseph
 */
public class ConexaoBD {
    private Connection conn;

    public void criarConexao() {
        try {
            String url = "jdbc:postgresql://localhost:5432/postgres";
//            String url = "jdbc:postgresql://localhost:5433/cliente";
            String user = "postgres";
            String password = "123";
            Class.forName("org.postgresql.Driver");
            this.conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException |ClassNotFoundException  e) {
            System.err.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        criarConexao();
        return this.conn;
    }

    public void desconecta() {
        try {
            conn.close();
        } catch (SQLException ex) {
        }
}
}
