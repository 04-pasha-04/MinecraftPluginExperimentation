package pluginsmiesny.pluginsmiensy;

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

    public void setX(Location x){
        if(solidified){return;}
        this.x = x;
    }

    public void setY(Location y){
        if(solidified){return;}
        this.y = y;
    }

    public Location getY(){
        if(solidified){return null;}
        if(this.y != null){
            return this.y;
        }else{
            return null;
        }
    }

    public Location getX(){
        if(solidified){return null;}
        if(this.x != null){
            return this.x;
        }else{
            return null;
        }
    }

    public void locClear(){
        if(solidified){return;}
        this.y = null;
        this.x = null;
    }

    public boolean locEmpty(){
        if(this.y == null && this.x == null){
            return true;
        }else{
            return  false;
        }
    }

    public void addMember(UUID newmember){
        if(newmember != null && newmember != owner) {
            this.members.add(newmember);
        }
    }

    public void removeMember(UUID member){
        if(this.members.contains(member)){
            this.members.remove(member);
        }
    }

    public String getName(){
        return this.name;
    }



    boolean isOwner(UUID id){
        if(this.owner == id){
            return true;
        }else{
            return false;
        }
    }

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

    public boolean isSolidified(){
        return solidified;
    }

    public void solidify(){
        if(solidified){return;}
        solidified = true;
    }

    public void setName(String name){
        if(solidified){return;}
        this.name = name;
    }

    public UUID getOwner(){
        return this.owner;
    }

    public List getMembers(){
        return this.members;
    }


}
