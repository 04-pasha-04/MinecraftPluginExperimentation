package pluginsmiesny.pluginsmiensy;

import com.google.gson.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class database {

    private final PluginSmiensy pl;
    public database(PluginSmiensy pl){
        this.pl = pl;
    }



    private static Connection con = null;
    private static boolean hasdata = false;

    public ResultSet getHomes() throws SQLException, ClassNotFoundException {
        if(con == null){
            getConnection();
        }
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM homes");
        return res;
    }

    private void getConnection() throws ClassNotFoundException, SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "pashAdniwe147");
    }



    public void addHome(UUID id, Location l, World wd) throws SQLException, ClassNotFoundException {
        String id_id = id.toString();
        String w = wd.getName();
        double x = l.getX();
        double y = l.getY();
        double z = l.getZ();


        Statement state = con.createStatement();
        try{
            state.executeUpdate("INSERT INTO homes VALUES('"+id_id+"', '"+w+"', '"+x+"', '"+y+"', '"+z+"');");
        }catch(SQLIntegrityConstraintViolationException e){
            state.executeUpdate("" +
                    "UPDATE homes " +
                    "SET world='"+w+"', x='"+x+"', y='"+y+"', z='"+z+"' " +
                    "WHERE uuid='"+id_id+"';"
            );
        }

    }

    public void updateHomesMap() throws SQLException, ClassNotFoundException {
        if(con == null){
            getConnection();
        }
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM homes");

        while(res.next()){

            UUID id = UUID.fromString(res.getString("uuid"));
            World w = Bukkit.getServer().getWorld(res.getString("world"));
            double x = res.getDouble("x");
            double y = res.getDouble("y");
            double z = res.getDouble("z");

            Location loc = new Location (w, x, y, z);

            this.pl.addHome(id, loc);
        }

    }

    public void addRes(UUID id, resObject res) throws SQLException, ClassNotFoundException {


        String id_s = id.toString();
        String obj = res.toJson();

        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery("SELECT * FROM userreslists WHERE uuid='"+id_s+"'");
        JsonArray ja;
        obj = obj.replace("\\\\", "");
        if(rs.next()){
            ja = (JsonArray) rs.getObject("userreslists");
            ja.add(obj);
            Bukkit.getLogger().info(String.valueOf(ja));
            state.executeUpdate("" +
                    "UPDATE userreslists " +
                    "SET userreslists='"+ja+"' " +
                    "WHERE uuid='"+id_s+"';"
            );

        }else{
            ja = new JsonArray();
            ja.add(obj);
            Bukkit.getLogger().info(String.valueOf(ja));
            state.executeUpdate("INSERT INTO userreslists VALUES('"+id_s+"', '"+ja+"');");
        }


    }

    public void updateResesMap() throws SQLException, ClassNotFoundException {
        if(con == null){
            getConnection();
        }
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM reses");

        while(res.next()){

            JsonArray ja = (JsonArray) res.getObject("uuid");
            UUID id = UUID.fromString(res.getString("uuid"));
            World w = Bukkit.getServer().getWorld(res.getString("world"));

            for(int i=0; i<ja.size(); i++){

                double x = ja.get(i).getAsJsonObject().get("x1").getAsDouble();
                double y = ja.get(i).getAsJsonObject().get("y1").getAsDouble();
                double z = ja.get(i).getAsJsonObject().get("z1").getAsDouble();
                Location l1 = new Location(w,x,y,z);
                x = ja.get(i).getAsJsonObject().get("x2").getAsDouble();
                y = ja.get(i).getAsJsonObject().get("y2").getAsDouble();
                z = ja.get(i).getAsJsonObject().get("z2").getAsDouble();
                Location l2 = new Location(w,x,y,z);

                resObject ro = new resObject(l1,l2,id);
                this.pl.addRes(UUID.fromString(res.getString("uuid")), ro);
            }


        }

        JsonObject data = new JsonObject();
    }
}
