package org.ourproject.kune.app.server.wave;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.waveprotocol.wave.examples.fedone.Flag;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.name.Names;

public class WavePropertiesParser {
    private static final String PREFIX = "kune.wave.";
    private static final Set<Class<?>> supportedFlagTypes; // NOPMD by vjrj on
    // 30/08/09 12:37

    static {
        supportedFlagTypes = new HashSet<Class<?>>();
        supportedFlagTypes.add(int.class);
        supportedFlagTypes.add(boolean.class);
        supportedFlagTypes.add(String.class);
    }

    public static Module parseFlags(final Class<?>... flagSettings) {

        final Properties properties;
        try {
            properties = new Properties();
            final InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(
                    "kune.properties");
            properties.load(input);
            input.close();
        } catch (final Exception excep) {
            throw new RuntimeException("Error reading properties", excep); // NOPMD
            // by
            // vjrj on
            // 30/08/09
            // 16:59
        }

        final List<Field> fields = new ArrayList<Field>();
        for (final Class<?> settings : flagSettings) {
            fields.addAll(Arrays.asList(settings.getDeclaredFields()));
        }

        // Reflect on flagSettings class and absorb flags
        final Map<Flag, Field> flags = new LinkedHashMap<Flag, Field>();
        for (final Field field : fields) {
            if (!field.isAnnotationPresent(Flag.class)) {
                continue;
            }

            // Validate target type
            // if (supportedFlagTypes.contains(field.getType())) {
            // throw new IllegalArgumentException(field.getType() +
            // " is not one of the supported flag types "
            // + supportedFlagTypes);
            // }

            final Flag flag = field.getAnnotation(Flag.class);

            flags.put(flag, field);
        }

        // Now validate them
        for (final Flag flag : flags.keySet()) {
            if (flag.mandatory()) {
                final String help = !"".equals(flag.description()) ? flag.description() : flag.name();
                mandatoryOption(properties, flag.name(), "must supply " + help);
            }
        }

        // bundle everything up in an injectable guice module
        return new AbstractModule() {

            @Override
            protected void configure() {
                // We must iterate the flags a third time when binding.
                // Note: do not collapse these loops as that will damage
                // early error detection. The runtime is still O(n) in flag
                // count.
                for (final Map.Entry<Flag, Field> entry : flags.entrySet()) {
                    final Class<?> type = entry.getValue().getType();
                    final Flag flag = entry.getKey();

                    // Skip non-mandatory, missing flags.
                    if (!flag.mandatory()) {
                        continue;
                    }

                    final String flagValue = properties.getProperty(PREFIX + flag.name());

                    // Coerce String flag into target type.
                    // NOTE(dhanji): only supported types are int, String and
                    // boolean.
                    if (int.class.equals(type)) {
                        bindConstant().annotatedWith(Names.named(flag.name())).to(Integer.parseInt(flagValue));
                    } else if (boolean.class.equals(type)) {
                        bindConstant().annotatedWith(Names.named(flag.name())).to(Boolean.parseBoolean(flagValue));
                    } else {
                        bindConstant().annotatedWith(Names.named(flag.name())).to(flagValue);
                    }
                }
            }
        };
    }

    /**
     * Checks a mandatory option is set, spits out help and dies if not.
     * 
     * @param cmd
     *            parsed options
     * @param option
     *            the option to check
     * @param helpString
     *            the error message to emit if not.
     */
    static void mandatoryOption(final Properties properties, final String option, final String helpString) {
        if (properties.getProperty(PREFIX + option) == null) {
            throw new RuntimeException("Wave option " + PREFIX + option + " in kune.properties is mandatory"); // NOPMD
            // by
            // vjrj
            // on
            // 30/08/09
            // 12:36
        }
    }
}
