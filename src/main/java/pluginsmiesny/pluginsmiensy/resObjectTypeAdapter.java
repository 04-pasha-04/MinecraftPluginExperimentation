package pluginsmiesny.pluginsmiensy;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;

import java.io.IOException;

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
        out.value(value.getMembers().toString());
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
