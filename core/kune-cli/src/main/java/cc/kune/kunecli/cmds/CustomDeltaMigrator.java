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
import java.io.IOException;
import java.util.ArrayList;

import org.waveprotocol.box.common.ExceptionalIterator;
import org.waveprotocol.box.server.persistence.PersistenceException;
import org.waveprotocol.box.server.waveserver.DeltaStore;
import org.waveprotocol.box.server.waveserver.DeltaStore.DeltasAccess;
import org.waveprotocol.box.server.waveserver.WaveletDeltaRecord;
import org.waveprotocol.wave.model.id.WaveId;
import org.waveprotocol.wave.model.id.WaveletId;
import org.waveprotocol.wave.model.id.WaveletName;
import org.waveprotocol.wave.model.version.HashedVersion;
import org.waveprotocol.wave.util.logging.Log;

import com.google.common.collect.ImmutableSet;

/**
 *
 * An utility class to copy all deltas between storages. Already existing Waves
 * in the target store wont be changed.
 *
 * It is NOT an incremental process.
 *
 * @author pablojan@gmail.com (Pablo Ojanguren)
 *
 */
public class CustomDeltaMigrator {

  private static final Log LOG = Log.get(CustomDeltaMigrator.class);

  private Progressbar bar;
  int countMigrated = 0;
  int countMigratedBecausePartial = 0;

  int countSkipped = 0;
  protected DeltaStore sourceStore = null;

  protected DeltaStore targetStore = null;

  public CustomDeltaMigrator(final DeltaStore sourceStore, final DeltaStore targetStore) {
    this.sourceStore = sourceStore;
    this.targetStore = targetStore;
  }

  private String getStats() {
    return "migrated/skipped/partial: " + countMigrated + "/" + countSkipped + "/"
        + countMigratedBecausePartial;
  }

  private void printStats() {
    LOG.info("Total " + getStats());
  }

  public void run() {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        printStats();
      }
    });

    LOG.info("Starting Wave migration from " + sourceStore.getClass().getSimpleName() + " to "
        + targetStore.getClass().getSimpleName());

    final long startTime = System.currentTimeMillis();

    try {
      // We need the total number of waves to make a progress bar
      int waveCount = 0;
      final ExceptionalIterator<WaveId, PersistenceException> countIterator = sourceStore.getWaveIdIterator();
      while (countIterator.hasNext()) {
        waveCount++;
        countIterator.next();
      }

      final ExceptionalIterator<WaveId, PersistenceException> srcItr = sourceStore.getWaveIdIterator();
      final File stopFile = new File(
          System.getProperty("java.io.tmpdir") + "/wave-mongo-migration-stop");

      bar = new Progressbar(waveCount, "Migrating " + waveCount + " Waves");

      int currentWave = 0;
      // Waves
      while (srcItr.hasNext()) {
        currentWave++;

        if (stopFile.exists()) {
          LOG.info("Stopping Wave migration as requested. Partial migration.");
          break;
        }

        bar.setVal(currentWave);

        final WaveId waveId = srcItr.next();
        final ImmutableSet<WaveletId> sourceLookup = sourceStore.lookup(waveId);
        final ImmutableSet<WaveletId> targetLookup = targetStore.lookup(waveId);
        final ImmutableSet<WaveletId> waveletIds = sourceLookup;

        boolean wavesAreNotTheSame = false;

        if (!targetLookup.isEmpty()) {
          if (targetLookup.size() != sourceLookup.size()) {
            wavesAreNotTheSame = true;
          } else {
            // Comparing source and target deltas
            for (final WaveletId waveletId : waveletIds) {
              setStatus("comparing");
              bar.setVal(currentWave);

              final DeltasAccess sourceDeltas = sourceStore.open(WaveletName.of(waveId, waveletId));
              final DeltasAccess targetDeltas = targetStore.open(WaveletName.of(waveId, waveletId));
              final HashedVersion deltaResultingVersion = sourceDeltas.getEndVersion();
              final HashedVersion deltaResultingTargetVersion = targetDeltas.getEndVersion();

              if (!deltaResultingVersion.equals(deltaResultingTargetVersion)) {
                wavesAreNotTheSame = true;
                break;
              }
            }

            if (wavesAreNotTheSame) {
              for (final WaveletId targetWaveletId : targetLookup) {
                // LOG.info(
                // "Deleting and appending Wave in target because it's found and
                // has not the same size: "
                // + waveId.toString());
                targetStore.delete(WaveletName.of(waveId, targetWaveletId));
              }
              // Continue migrating that wave
            } else {
              setStatus("skipping already migrated");
              bar.setVal(currentWave);
              countSkipped++;
              continue;
            }
          }
        }

        if (wavesAreNotTheSame) {
          setStatus("migrating partial wave");
          countMigratedBecausePartial++;
        } else {
          setStatus("migrating");
          countMigrated++;
        }
        setStatus(wavesAreNotTheSame ? "migrating partial wave" : "migrating");

        // LOG.info("--- Migrating Wave " + waveId.toString() + " with " +
        // waveletIds.size() + " wavelets");
        // final long waveStartTime = System.currentTimeMillis();
        //
        // Wavelets
        for (final WaveletId waveletId : waveletIds) {
          bar.setVal(currentWave);

          // LOG.info(
          // "Migrating wavelet " + waveletsCount + "/" + waveletsTotal + " : "
          // + waveletId.toString());

          final DeltasAccess sourceDeltas = sourceStore.open(WaveletName.of(waveId, waveletId));
          final DeltasAccess targetDeltas = targetStore.open(WaveletName.of(waveId, waveletId));

          // Get all deltas from last version to initial version (0): reverse
          // order
          // int deltasCount = 0;

          final ArrayList<WaveletDeltaRecord> deltas = new ArrayList<WaveletDeltaRecord>();
          HashedVersion deltaResultingVersion = sourceDeltas.getEndVersion();

          // Deltas
          while (deltaResultingVersion != null && deltaResultingVersion.getVersion() != 0) {
            // deltasCount++;
            final WaveletDeltaRecord deltaRecord = sourceDeltas.getDeltaByEndVersion(
                deltaResultingVersion.getVersion());
            deltas.add(deltaRecord);
            // get the previous delta, this is the appliedAt
            deltaResultingVersion = deltaRecord.getAppliedAtVersion();
          }
          // LOG.info("Appending " + deltasCount + " deltas to target");
          targetDeltas.append(deltas);
        }

      } // While Waves

      bar.finish();
      final long endTime = System.currentTimeMillis();
      LOG.info("Migration completed. Total time = " + (endTime - startTime) + "ms");
      printStats();

    } catch (final PersistenceException e) {

      throw new RuntimeException(e);

    } catch (final IOException e) {

      throw new RuntimeException(e);

    }
  }

  private void setStatus(final String status) {
    bar.setStatus(status, getStats());
  }

}
