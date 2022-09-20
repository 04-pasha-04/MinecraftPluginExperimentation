package pluginsmiesny.pluginsmiensy;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.List;

public class resObjectTypeAdapter extends TypeAdapter <resObject>{

    @Override
    public void write(JsonWriter out, resObject value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }

        out.beginObject();
        out.name("owner");
        out.value(value.getOwner().toString());
        out.name("members");
        List <?> l = value.getMembers();
        String mem;
        if(!l.isEmpty()) {
            mem = l.get(0).toString();
            for (int i = 1; i < l.size(); i++) {
                mem = mem.concat("," + l.get(i).toString());
            }

        }else {
            mem = "";
        }
        Bukkit.getLogger().info(mem);
        out.value(mem);

        out.name("x1");
        out.value(value.getX().getX());
        out.name("y1");
        out.value(value.getX().getY());
        out.name("z1");
        out.value(value.getX().getZ());
        out.name("x2");
        out.value(value.getY().getX());
        out.name("y2");
        out.value(value.getY().getY());
        out.name("z2");
        out.value(value.getY().getZ());
        out.name("solidified");
        out.value(true);
        out.name("name");
        out.value(value.getName());
        out.endObject();
    }

    @Override
    public resObject read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        return (resObject) Bukkit.getServer().getWorld(reader.nextString());
    }
}
