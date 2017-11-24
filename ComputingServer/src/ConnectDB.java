import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectDB {
    final private String URL="jdbc:mysql://localhost:3306/bankdb";
    private Connection connection=null;
    private Statement statement=null;
    ResultSet rS =null;
    Object temp;

    public ConnectDB(Object o) throws SQLException {
        temp=o;
            try {
               connect();
            }catch (SQLException e){
               System.out.println("Cannot connect-constructor");
            }
    }
    /**
     *Creates connection with database
     * @throws SQLException
     */
    private void connect() throws SQLException {

        try {
            connection = DriverManager.getConnection(URL,"root","");
            statement=connection.createStatement();
            temp=chooseOperation(statement);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }finally {
            if(statement!=null)
                statement.close();
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }

    private Object chooseOperation(Statement statement) throws SQLException {
        SQLcommand sqlCommand=new SQLcommand(statement,temp);
        String operation=temp.getClass().toString();

       switch (operation){
           case"class LogTo":
                temp=sqlCommand.logTo();
               break;
           case "class PersonalData":
               temp=sqlCommand.addNewAccReq();
           case "przelew":
           //    temp=sqlCommand.transfer();
               break;
       }

       return temp;
    }

    public Object getObject() {
        return temp;
    }
}
