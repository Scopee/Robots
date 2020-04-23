package gui.serialization;

import com.google.gson.JsonSerializer;

public interface Savable {
    JsonSerializer getSerializer();
}
