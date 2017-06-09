package ifpb.ads.infra;

import ifpb.ads.cliente.Cliente;
import ifpb.ads.cliente.Clientes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Joseph
 *
 */
public class ClientesJDBC implements Clientes {

    private final ConexaoBD conex = new ConexaoBD();

    private final Connection connection;

    public ClientesJDBC() {
        connection = conex.getConnection();
    }

    @Override
    public boolean salvar(Cliente cliente) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("INSERT into cliente(nome)VALUES (?)");
            prepareStatement.setString(1, cliente.getNome());
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean remover(Cliente cliente) {
        PreparedStatement pst = null;
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM cliente WHERE id=?");
            prepareStatement.setInt(1, cliente.getId());
            prepareStatement.execute();
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean atualizar(Cliente cliente) {
        PreparedStatement pst = null;

        try {
            PreparedStatement prepareStatement = connection.prepareStatement("UPDATE cliente set nome=? WHERE id=?");
            prepareStatement.setString(1, cliente.getNome());
            prepareStatement.setInt(2, cliente.getId());
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Cliente> todosOsClientes() {
        try {
            List<Cliente> clientes = new ArrayList<>();

            ResultSet result = consultarTodosOsClientes();

            while (result.next()) {
               clientes.add(criarCliente(result));
            }
            return clientes;

        } catch (SQLException ex) {
            Logger.getLogger(ClientesJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    private ResultSet consultarTodosOsClientes() throws SQLException {
        PreparedStatement prepareStatement = connection.prepareStatement("Select * from cliente");
        ResultSet result = prepareStatement.executeQuery();
        return result;
    }
    
    private Cliente criarCliente(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        String nome = result.getString("nome");
        return new Cliente(nome,id);
    }
}
