package labweb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 163ty
 */
public class LabWeb {

    public static void selectMus(Connection conn) {
        try ( Statement stat = conn.createStatement();) {
            ResultSet rs = stat.executeQuery("SELECT * FROM Musician");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + ", " + rs.getString(2));
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public static void main(String[] args) {
        ConnectBD conn = new ConnectBD();
        try ( Connection c = conn.getConnection()) {
            //1 Задание    
            Statement stat = c.createStatement();
            ResultSet rs = stat.executeQuery("Select name_composition, duration_composition From Composition "
                    + "Where (duration_composition not between 5 and 10) "
                    + "ORDER BY duration_composition DESC");
            System.out.println("Название и длительность композиций, длительность которых лежит вне диапазона от 5 до 10, "
                    + "отсортированных по убыванию длительности композиции:");
            while (rs.next()) {
                System.out.println(rs.getString(1) + "," + rs.getInt(2));
            }
            stat.executeUpdate("INSERT INTO Musician VALUES (nextval('id_for_musician'), 'ALEXANDER')");
            System.out.println("Исполнители после добавления:");
            selectMus(c);
            stat.executeUpdate("UPDATE Musician SET name_musician='GENA' WHERE name_musician='ALEXANDER'");
            System.out.println("Исполнители после изменения:");
            selectMus(c);
            stat.executeUpdate("Delete from Musician Where name_musician='GENA'");
            System.out.println("Исполнители после удаления:");
            selectMus(c);
            stat.close();
            //2 Задание
            String s = "Select name_composition, duration_composition From Composition "
                    + "Where (duration_composition not between ? and ?) "
                    + "ORDER BY duration_composition DESC";
            PreparedStatement ps = c.prepareStatement(s);
            ps.setInt(1, 5);
            ps.setInt(2, 10);
            ResultSet rs2 = ps.executeQuery();
            System.out.println("Название и длительность композиций, длительность которых лежит вне диапазона от 5 до 10, "
                    + "отсортированных по убыванию длительности композиции:");
            while (rs2.next()) {
                System.out.println(rs2.getString(1) + "," + rs2.getInt(2));
            }
            System.out.println("Все исполнители:");
            selectMus(c);
            String s2 = "INSERT INTO Musician VALUES (?, ?)";
            ps = c.prepareStatement(s2);
            ps.setInt(1, 1111111111);
            ps.setString(2, "ALEXANDER");
            ps.executeUpdate();
            System.out.println("Исполнители после добавления:");
            selectMus(c);

            String s3 = "UPDATE Musician SET name_musician=? WHERE name_musician=?";
            ps = c.prepareStatement(s3);
            ps.setString(1, "GENA");
            ps.setString(2, "ALEXANDER");
            ps.executeUpdate();
            System.out.println("Исполнители после изменения:");
            selectMus(c);

            String s4 = "Delete from Musician Where name_musician=?";
            ps = c.prepareStatement(s4);
            ps.setString(1, "GENA");
            ps.executeUpdate();
            System.out.println("Исполнители после удаления:");
            selectMus(c);
            ps.close();
            //3 Задание
            DatabaseMetaData meta = c.getMetaData();
            ResultSet rs3 = meta.getTables(null, null, null, new String[]{"TABLE"});
            System.out.println("Названия таблиц:");
            while (rs3.next()) {
                System.out.println(rs3.getString(3));
            }
            Statement st3 = c.createStatement();
            rs3 = st3.executeQuery("Select * From Composition");
            ResultSetMetaData rsmd = rs3.getMetaData();
            System.out.println("Названия столбцов:");
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.println(rsmd.getColumnName(i));
            }
            st3.close();
        } catch (SQLException ex) {
            System.out.println("Ошибка соединения!");
        } catch (IOException ex) {
            System.out.println("Ошибка чтения!");

        }
    }
}
