package pluginsmiesny.pluginsmiensy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class resObject {

    private final UUID owner;

    private List<UUID> members;
    private Location x;
    private Location y;

    private boolean solidified;

    private String name;




    public resObject(Location x, Location y, UUID owner){
        this.x = x;
        this.y = y;
        this.owner = owner;
        this.members = new ArrayList<UUID>();
        this.solidified = false;

    }

    //if res isn't created sets the first selection point
    public void setX(Location x){
        if(solidified){return;}
        this.x = x;
    }

    //if res isn't created sets the second selection point
    public void setY(Location y){
        if(solidified){return;}
        this.y = y;
    }

    //if res isn't created return the second selection point
    public Location getY(){
        if(this.y != null){
            return this.y;
        }else{
            return null;
        }
    }

    public String toJson(){
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().registerTypeAdapter(this.getClass(), new resObjectTypeAdapter()).create();
        return gson.toJson(this);
    }

    //if res isn't created return the first selection point
    public Location getX(){
        if(this.x != null){
            return this.x;
        }else{
            return null;
        }
    }





    //adds a member's id to the members list
    public void addMember(UUID newmember){
        if(newmember != null && newmember != owner) {
            this.members.add(newmember);
        }
    }

    //removes the specified id from the members list
    public void removeMember(UUID member){
        if(this.members.contains(member)){
            this.members.remove(member);
        }
    }

    //returns the name of the res
    public String getName(){
        return this.name;
    }



    //returns true if the specified id is the same as owners id. If not return false
    boolean isOwner(UUID id){
        if(this.owner == id){
            return true;
        }else{
            return false;
        }
    }

    //checks if the specified location is in this res
    public boolean isInRes(Location l){

        double bx,sx,by,sy,bz,sz;

        if(!solidified){
            Bukkit.getLogger().info("notsol!");
            return false;
        }

        if(x.getX() < y.getX()){bx = y.getX();sx = x.getX();}else{sx = y.getX();bx = x.getX();}
        if(x.getY() < y.getY()){by = y.getY();sy = x.getY();}else{sy = y.getY();by = x.getY();}
        if(x.getZ() < y.getZ()){bz = y.getZ();sz = x.getZ();}else{sz = y.getZ();bz = x.getZ();}

        if(l.getX() >= sx && l.getX() <= bx){
            if(l.getY() >= sy && l.getY() <= by){
                if(l.getZ() >= sz && l.getZ() <= bz){
                    return true;
                }
                return  false;
            }
            return false;
        }
        return false;
    }

    //returns true if this res is created. False if just selected
    public boolean isSolidified(){
        return solidified;
    }

    //creates the res
    public void solidify(){
        if(solidified){return;}
        solidified = true;
    }

    //sets the name
    public void setName(String name){
        if(solidified){return;}
        this.name = name;
    }

    //returns the owners id
    public UUID getOwner(){
        return this.owner;
    }

    //returns a list of all member id's
    public List getMembers(){
        return this.members;
    }


}
