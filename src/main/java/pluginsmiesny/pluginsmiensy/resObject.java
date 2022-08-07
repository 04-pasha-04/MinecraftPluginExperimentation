package pluginsmiesny.pluginsmiensy;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class resObject {

    private final UUID owner;

    private List<UUID> members;
    private Location x;
    private Location y;


    public resObject(Location x, Location y, UUID owner){
        this.x = x;
        this.y = null;
        this.owner = owner;
        this.members = new ArrayList<UUID>();

    }

    public void setX(Location x){
        this.x = x;
    }

    public void setY(Location y){
        this.y = y;
    }

    public Location getY(){
        if(this.y != null){
            return this.y;
        }else{
            return null;
        }
    }

    public Location getX(){
        if(this.x != null){
            return this.x;
        }else{
            return null;
        }
    }

    public void locClear(){
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
        if(newmember != null) {
            this.members.add(newmember);
        }
    }



    boolean isOwner(UUID id){
        if(this.owner == id){
            return true;
        }else{
            return false;
        }
    }


}
