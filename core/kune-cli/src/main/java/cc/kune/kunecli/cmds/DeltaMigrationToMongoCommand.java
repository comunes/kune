/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package cc.kune.kunecli.cmds;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.SystemConfiguration;
import org.naturalcli.Command;
import org.naturalcli.ExecutionException;
import org.naturalcli.ICommandExecutor;
import org.naturalcli.InvalidSyntaxException;
import org.naturalcli.ParseResult;
import org.waveprotocol.box.server.persistence.PersistenceModule;
import org.waveprotocol.box.server.waveserver.DeltaStore;
import org.waveprotocol.wave.util.logging.Log;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * A cmd line utility to perform data migration from a store type to another
 * one. Initially developed to replicate deltas from a file store to a mongodb
 * store.
 *
 *
 * @author pablojan@gmail.com (Pablo Ojanguren)
 *
 */
public class DeltaMigrationToMongoCommand extends Command {

  public static class DeltaMigrationToMongoICommand implements ICommandExecutor {

    @Override
    public void execute(final ParseResult result) throws ExecutionException {
      final String source = result.getParameterValue(0).toString();
      final String target = result.getParameterValue(1).toString();

      final Module sourceSettings = bindCmdLineSettings(source);
      final Injector sourceSettingsInjector = Guice.createInjector(sourceSettings);
      final Module sourcePersistenceModule = sourceSettingsInjector.getInstance(PersistenceModule.class);
      final Injector sourceInjector = sourceSettingsInjector.createChildInjector(
          sourcePersistenceModule);

      final Module targetSettings = bindCmdLineSettings(target);
      final Injector targetSettingsInjector = Guice.createInjector(targetSettings);
      final Module targetPersistenceModule = targetSettingsInjector.getInstance(PersistenceModule.class);
      final Injector targetInjector = targetSettingsInjector.createChildInjector(
          targetPersistenceModule);

      runDeltasMigration(sourceInjector, targetInjector);

    }
  }

  private static final Log LOG = Log.get(DeltaMigrationToMongoCommand.class);

  private static Module bindCmdLineSettings(final String cmdLineProperties) {
    // Get settings from cmd line, e.g.
    // Key = delta_store_type
    // Value = mongodb
    final Map<String, String> propertyMap = new HashMap<>();

    for (final String arg : cmdLineProperties.split(",")) {
      final String[] argTokens = arg.split("=");
      propertyMap.put(argTokens[0], argTokens[1]);
    }

    return new AbstractModule() {

      @Override
      protected void configure() {
        final SystemConfiguration sysConf = new SystemConfiguration();
        final String waveConfig = sysConf.getString("wave.server.config");
        final Config config = ConfigFactory.load().withFallback(
            ConfigFactory.parseFile(new File("custom-" + waveConfig)).withFallback(
                ConfigFactory.parseFile(new File("reference.conf"))));
        bind(Config.class).toInstance(ConfigFactory.parseMap(propertyMap).withFallback(config));
      }

    };

  }

  public static void main(final String... args) {

  }

  private static void runDeltasMigration(final Injector sourceInjector, final Injector targetInjector) {

    final String sourceDeltaStoreType = sourceInjector.getInstance(Config.class).getString(
        "core.delta_store_type");

    final String targetDeltaStoreType = targetInjector.getInstance(Config.class).getString(
        "core.delta_store_type");

    if (sourceDeltaStoreType.equalsIgnoreCase(targetDeltaStoreType)) {
      usageError("Source and Target Delta store types must be different");
    }

    final CustomDeltaMigrator dm = new CustomDeltaMigrator(sourceInjector.getInstance(DeltaStore.class),
        targetInjector.getInstance(DeltaStore.class));

    dm.run();

  }

  public static void usageError() {
    usageError("");
  }

  public static void usageError(final String msg) {
    LOG.severe(msg);
    LOG.severe("Use: DataMigrationTool <data type> <source options> <target options>\n");
    LOG.severe("supported data types : deltas");
    LOG.severe("source options example : delta_store_type=file,delta_store_directory=./_deltas");
    LOG.severe(
        "target options example : delta_store_type=mongodb,mongodb_host=127.0.0.1,mongodb_port=27017,mongodb_database=wiab");
    System.exit(1);
  }

  @Inject
  public DeltaMigrationToMongoCommand(final DeltaMigrationToMongoICommand cmd)
      throws InvalidSyntaxException {
    super("deltaMigrationToMongo <source:string> <target:string>",
        "migrates deltas to mongodb, source options example: delta_store_type=file,delta_store_directory=./_deltas ; "
            + "target options example: delta_store_type=mongodb,mongodb_host=127.0.0.1,mongodb_port=27017,mongodb_database=wiab ; "
            + "touch /tmp/wave-mongo-migration-stop # to stop the migration at any time",
        cmd);
  }
}
