package pluginsmiesny.pluginsmiensy;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "", "");
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

        JsonObject jo = new JsonParser().parse(obj).getAsJsonObject();

        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery("SELECT * FROM userreslists WHERE uuid='"+id_s+"'");
        JsonArray ja;
        if(rs.next()){
            String temp = rs.getString("userreslists");
            JsonParser jsonParser = new JsonParser();
            ja = (JsonArray) jsonParser.parse(temp);
            ja.add(jo);
            state.executeUpdate("" +
                    "UPDATE userreslists " +
                    "SET userreslists='"+ja+"' " +
                    "WHERE uuid='"+id_s+"';"
            );

        }else{
            ja = new JsonArray();
            ja.add(jo);
            state.executeUpdate("INSERT INTO userreslists VALUES('"+id_s+"', '"+ja+"');");
        }


    }

    public void deleteRes(UUID ido, String resName) throws SQLException {
        String id_s = ido.toString();
        String obj = this.pl.getRes(ido, resName).toJson();
        JsonObject jo = new JsonParser().parse(obj).getAsJsonObject();

        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery("SELECT * FROM userreslists WHERE uuid='"+id_s+"'");
        JsonArray ja;
        if(rs.next()) {
            String temp = rs.getString("userreslists");
            JsonParser jsonParser = new JsonParser();
            ja = (JsonArray) jsonParser.parse(temp);
            ja.remove(jo);
            state.executeUpdate("" +
                    "UPDATE userreslists " +
                    "SET userreslists='" + ja + "' " +
                    "WHERE uuid='" + id_s + "';"
            );
        }

    }

    public void addResMember(UUID ido, UUID idm, String resName) throws SQLException {
        String id_s = ido.toString();
        String obj = this.pl.getRes(ido, resName).toJson();
        JsonObject jo = new JsonParser().parse(obj).getAsJsonObject();

        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery("SELECT * FROM userreslists WHERE uuid='"+id_s+"'");
        JsonArray ja;
        if(rs.next()){
            String temp = rs.getString("userreslists");
            JsonParser jsonParser = new JsonParser();
            ja = (JsonArray) jsonParser.parse(temp);
            for(int i=0; i<ja.size(); i++){
                if(ja.get(i).getAsJsonObject().get("name").getAsString().equalsIgnoreCase(resName)){
                    ja.remove(i);
                    String mems = jo.get("members").getAsString();
                    jo.remove("members");
                    if(mems.equalsIgnoreCase("")){
                        jo.addProperty("members", mems.concat(idm.toString()));
                    }else{
                        jo.addProperty("members", mems.concat(","+idm.toString()));
                    }
                    ja.add(jo);
                    state.executeUpdate("" +
                            "UPDATE userreslists " +
                            "SET userreslists='" + ja + "' " +
                            "WHERE uuid='" + id_s + "';"
                    );

                }
            }



        }

    }

    public void removeResMember(UUID ido, UUID idm, String resName) throws SQLException {
        String id_s = ido.toString();
        String obj = this.pl.getRes(ido, resName).toJson();
        JsonObject jo = new JsonParser().parse(obj).getAsJsonObject();

        Bukkit.getLogger().info(jo.toString());

        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery("SELECT * FROM userreslists WHERE uuid='"+id_s+"'");
        JsonArray ja;
        if(rs.next()){
            String temp = rs.getString("userreslists");
            JsonParser jsonParser = new JsonParser();
            ja = (JsonArray) jsonParser.parse(temp);

            for(int i=0; i<ja.size(); i++){
                if(ja.get(i).getAsJsonObject().get("name").getAsString().equalsIgnoreCase(resName)){
                    ja.remove(i);
                    String mems = jo.get("members").getAsString();
                    jo.remove("members");
                    if(mems.equalsIgnoreCase(idm.toString())){
                        jo.addProperty("members", mems.replace(idm.toString(), ""));
                    }else if(mems.length() > idm.toString().length() + 2){
                        jo.addProperty("members", mems.replace(","+idm.toString(), ""));
                    }
                    ja.add(jo);
                    state.executeUpdate("" +
                            "UPDATE userreslists " +
                            "SET userreslists='" + ja + "' " +
                            "WHERE uuid='" + id_s + "';"
                    );

                }
            }



        }

    }


    public void updateResesMap() throws SQLException, ClassNotFoundException {
        if(con == null){
            getConnection();
        }
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT * FROM userreslists");

        while(res.next()) {
            String rls = res.getString("userreslists");
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = (JsonArray) jsonParser.parse(rls);

            for(int i=0; i<jsonArray.size(); i++){
                JsonObject jsonObject = (JsonObject) jsonParser.parse(String.valueOf(jsonArray.get(i)));

                World w = Bukkit.getWorlds().get(0);
                double x1 = jsonObject.get("x1").getAsDouble();
                double y1 = jsonObject.get("y1").getAsDouble();
                double z1 = jsonObject.get("z1").getAsDouble();
                double x2 = jsonObject.get("x2").getAsDouble();
                double y2 = jsonObject.get("y2").getAsDouble();
                double z2 = jsonObject.get("z2").getAsDouble();
                UUID id = UUID.fromString(jsonObject.get("owner").getAsString());
                String name = jsonObject.get("name").getAsString();

                Location l1 = new Location(w, x1, y1, z1);
                Location l2 = new Location(w, x2, y2, z2);

                resObject residence = new resObject(l1, l2, id);

                //members
                if(jsonObject.get("members").getAsString().equalsIgnoreCase("")) {
                    Bukkit.getLogger().info("nomem");
                }else{
                    String members_str_temp = jsonObject.get("members").getAsString();
                    Bukkit.getLogger().info(members_str_temp);
                    ArrayList<String> members = new ArrayList<>(Arrays.asList(members_str_temp.split(",")));
                    for(int y=0; y< members.size(); y++){
                        residence.addMember(UUID.fromString(members.get(y)), null);
                    }
                }

                Bukkit.getLogger().info(jsonArray.toString());

                ///////////////

                residence.setName(name);
                residence.solidify();


                this.pl.hmAddRes(id, residence);
            }
        }

    }
}
