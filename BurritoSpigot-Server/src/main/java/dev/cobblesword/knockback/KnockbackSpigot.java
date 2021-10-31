package dev.cobblesword.knockback;

import dev.cobblesword.knockback.commons.FileUtils;

import java.io.File;

public class KnockbackSpigot
{
    private static KnockbackSpigot INSTANCE;//Meh, memory leak but oh well, closing will fix this
    private static final File CONFIG_FILE = new File("KnockbackSpigot.json");
    private KnockbackConfig config;

    public KnockbackSpigot()
    {
        INSTANCE = this;

        this.config = new KnockbackConfig();
        while (!CONFIG_FILE.exists())
            FileUtils.toFile(this.config, CONFIG_FILE);
        this.config = FileUtils.toObject(CONFIG_FILE, KnockbackConfig.class);
    }

    public KnockbackConfig getConfig()
    {
        return config;
    }

    public static KnockbackSpigot get() {
        return INSTANCE;
    }
}
