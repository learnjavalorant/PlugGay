package vietnam.tansenpai.pluggay.messaging;

/*
 * #%L
 * PlugMan
 * %%
 * Copyright (C) 2010 - 2015 PlugMan
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;

/**
 * Class that allows reading from a YAML file embedded in the JAR.
 *
 * @author rylinaux
 */
public class MessageFile {

    /**
     * The configuration.
     */
    private FileConfiguration config = null;

    /**
     * Construct the object.
     *
     * @param name the name of the file.
     */
    public MessageFile(String name) {
        this.config = YamlConfiguration.loadConfiguration(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(name)));
    }

    /**
     * Construct the object.
     *
     * @param file the file.
     */
    public MessageFile(File file) {
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Get the FileConfiguration.
     *
     * @return the FileConfiguration.
     */
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Get a key from the file.
     *
     * @param key the key.
     * @return the value.
     */
    public String get(String key) {
        return config.getString(key);
    }

}
