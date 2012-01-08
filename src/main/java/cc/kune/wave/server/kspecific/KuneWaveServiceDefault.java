/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.wave.server.kspecific;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Nonnull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.waveprotocol.box.server.CoreSettings;
import org.waveprotocol.box.server.robots.OperationContextImpl;
import org.waveprotocol.box.server.robots.OperationServiceRegistry;
import org.waveprotocol.box.server.robots.util.ConversationUtil;
import org.waveprotocol.box.server.robots.util.OperationUtil;
import org.waveprotocol.box.server.waveserver.WaveletProvider;
import org.waveprotocol.box.server.waveserver.WaveletProvider.SubmitRequestListener;
import org.waveprotocol.wave.model.id.InvalidIdException;
import org.waveprotocol.wave.model.id.WaveId;
import org.waveprotocol.wave.model.id.WaveletId;
import org.waveprotocol.wave.model.version.HashedVersion;
import org.waveprotocol.wave.model.wave.ParticipantId;
import org.waveprotocol.wave.model.waveref.WaveRef;

import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.errors.DefaultException;
import cc.kune.wave.server.ParticipantUtils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.wave.api.Annotation;
import com.google.wave.api.ApiIdSerializer;
import com.google.wave.api.Blip;
import com.google.wave.api.BlipContent;
import com.google.wave.api.BlipData;
import com.google.wave.api.BlipThread;
import com.google.wave.api.Element;
import com.google.wave.api.ElementType;
import com.google.wave.api.FormElement;
import com.google.wave.api.Gadget;
import com.google.wave.api.Image;
import com.google.wave.api.JsonRpcConstant.ParamsProperty;
import com.google.wave.api.JsonRpcResponse;
import com.google.wave.api.Line;
import com.google.wave.api.OperationQueue;
import com.google.wave.api.OperationRequest;
import com.google.wave.api.OperationRequest.Parameter;
import com.google.wave.api.Participants;
import com.google.wave.api.ProtocolVersion;
import com.google.wave.api.Range;
import com.google.wave.api.Wavelet;
import com.google.wave.api.data.converter.EventDataConverterManager;
import com.google.wave.api.impl.DocumentModifyAction;
import com.google.wave.api.impl.DocumentModifyAction.BundledAnnotation;
import com.google.wave.api.impl.DocumentModifyAction.ModifyHow;
import com.google.wave.api.impl.DocumentModifyQuery;
import com.google.wave.api.impl.WaveletData;
import com.google.wave.splash.rpc.ClientAction;
import com.google.wave.splash.web.template.WaveRenderer;

public class KuneWaveServiceDefault implements KuneWaveService {
  public static final Log LOG = LogFactory.getLog(KuneWaveServiceDefault.class);

  // See: DocumentModifyServiceTest
  private static final String NO_ANNOTATION_KEY = null;
  private static final List<BundledAnnotation> NO_BUNDLED_ANNOTATIONS = Collections.emptyList();
  private static final List<String> NO_VALUES = Collections.<String> emptyList();
  private static final String NO_WAVE_TO_COPY = null;

  /**
   * 
   * Copy blips
   * 
   * @param fromBlip
   * @param toBlip
   * 
   * @author yurize@apache.org (Yuri Zelikov)
   * 
   */
  public static void copyWavelet(final Blip fromBlip, final Blip toBlip) {
    for (final BlipContent blipContent : fromBlip.all().values()) {
      toBlip.append(blipContent);
    }
    // Deep copy annotations
    for (final Annotation annotation : fromBlip.getAnnotations()) {
      final Range range = annotation.getRange();
      try {
        toBlip.range(range.getStart() + 1, range.getEnd() + 1).annotate(annotation.getName(),
            annotation.getValue());
      } catch (final IndexOutOfBoundsException e) {
        // Don't copy faulty annotations
      }
    }
  }

  public static void copyWaveletElements(final Blip fromBlip, final Blip toBlip) {
    // Deep copy form elements.
    // DocumentModifyService don't permit this:
    // "Can't insert other elements than text and gadgets at the moment");
    for (final Entry<Integer, Element> entry : fromBlip.getElements().entrySet()) {
      final ElementType type = entry.getValue().getType();
      Element result = null;
      if (FormElement.getFormElementTypes().contains(type)) {
        result = new FormElement(type, entry.getValue().getProperties());
      } else if (type == ElementType.GADGET) {
        result = new Gadget(entry.getValue().getProperties());
      } else if (type == ElementType.IMAGE) {
        result = new Image(entry.getValue().getProperties());
      } else if (type == ElementType.LINE) {
        result = new Line(entry.getValue().getProperties());
      } else {
        result = new Element(type, entry.getValue().getProperties());
      }
      toBlip.append(result);
    }
  }

  private final ConversationUtil conversationUtil;
  private final EventDataConverterManager converterManager;
  private final String domain;
  private final OperationServiceRegistry operationRegistry;
  private final ParticipantUtils participantUtils;
  private final WaveletProvider waveletProvider;
  private final WaveRenderer waveRenderer;

  @Inject
  public KuneWaveServiceDefault(final EventDataConverterManager converterManager,
      @Named("DataApiRegistry") final OperationServiceRegistry operationRegistry,
      final WaveletProvider waveletProvider, final ConversationUtil conversationUtil,
      final ParticipantUtils participantUtils, final WaveRenderer waveRenderer,
      @Named(CoreSettings.WAVE_SERVER_DOMAIN) final String domain) {
    this.converterManager = converterManager;
    this.waveletProvider = waveletProvider;
    this.conversationUtil = conversationUtil;
    this.operationRegistry = operationRegistry;
    this.participantUtils = participantUtils;
    this.waveRenderer = waveRenderer;
    this.domain = domain;
  }

  @Override
  public void addGadget(final WaveRef waveName, final String author, final URL gadgetUrl) {
    // See DocumentModifyServiceTest
    final List<Element> elementsIn = Lists.newArrayListWithCapacity(1);
    final Map<String, String> properties = Maps.newHashMap();
    properties.put(Gadget.URL, gadgetUrl.toString());
    properties.put(Gadget.AUTHOR, participantUtils.of(author).getAddress());
    final Gadget gadget = new Gadget(properties);

    elementsIn.add(gadget);
    final Wavelet wavelet = fetchWave(waveName, author);
    final OperationQueue opQueue = new OperationQueue();
    final Blip rootBlip = wavelet.getRootBlip();

    final OperationRequest operationRequest = opQueue.modifyDocument(rootBlip);
    operationRequest.addParameter(Parameter.of(ParamsProperty.MODIFY_ACTION, new DocumentModifyAction(
        ModifyHow.INSERT, NO_VALUES, NO_ANNOTATION_KEY, elementsIn, NO_BUNDLED_ANNOTATIONS, false)));
    operationRequest.addParameter(Parameter.of(ParamsProperty.INDEX, 1));
    doOperation(author, opQueue, "add gadget");
  }

  @Override
  public void addParticipants(final WaveRef waveName, final String author, final String userWhoAdds,
      final String... newLocalParticipants) {
    final Wavelet wavelet = fetchWave(waveName, author);
    final Participants currentParticipants = wavelet.getParticipants();
    // Removing duplicates
    for (final String participant : toSet(newLocalParticipants)) {
      final String newPartWithDomain = participantUtils.of(participant).toString();
      if (!currentParticipants.contains(newPartWithDomain)) {
        // FIXME This is very costly. Seems like only one participant per
        // opQueue is added (try to
        // fix this in WAVE)
        final OperationQueue opQueue = new OperationQueue();
        LOG.debug("Adding as participant: " + newPartWithDomain);
        opQueue.addParticipantToWavelet(wavelet, newPartWithDomain);
        final String whoAdd = wavelet.getParticipants().contains(participantUtils.of(userWhoAdds)) ? userWhoAdds
            : author;
        doOperation(whoAdd, opQueue, "add participant");
      }
    }
  }

  @Override
  public WaveRef createWave(final String message, final ParticipantId... participants) {
    return createWave(NO_TITLE, message, participants);
  }

  @Override
  public WaveRef createWave(@Nonnull final String title, final String message,
      @Nonnull final ParticipantId... participantsArray) {
    return createWave(title, message, WITHOUT_GADGET, participantsArray);
  }

  @Override
  public WaveRef createWave(final String title, final String message, final String... participantsArray) {
    return createWave(title, message, participantUtils.listFrom(participantsArray));
  }

  @Override
  public WaveRef createWave(@Nonnull final String title, final String message,
      final String waveIdToCopy, final URL gadgetUrl, @Nonnull final ParticipantId... participantsArray) {
    String newWaveId = null;
    String newWaveletId = null;
    final Set<String> participants = new HashSet<String>();
    for (final ParticipantId participant : participantsArray) {
      participants.add(participant.toString());
    }
    final ParticipantId user = participantsArray[0];
    final OperationQueue opQueue = new OperationQueue();
    final Wavelet newWavelet = opQueue.createWavelet(domain, participants);
    opQueue.setTitleOfWavelet(newWavelet, title);
    final Blip rootBlip = newWavelet.getRootBlip();
    rootBlip.append(new com.google.wave.api.Markup(message).getText());

    if (waveIdToCopy != NO_WAVE_TO_COPY && TextUtils.notEmpty(waveIdToCopy)) {
      try {
        WaveId copyWaveId;
        copyWaveId = WaveId.ofChecked(domain, waveIdToCopy);
        final Wavelet waveletToCopy = fetchWave(copyWaveId, WaveletId.of(domain, "conv+root"),
            participantsArray[0].toString());
        if (waveletToCopy != null) {
          copyWavelet(waveletToCopy.getRootBlip(), rootBlip);
        }
      } catch (final InvalidIdException e) {
        LOG.error("Error copying wave content", e);
      } catch (final DefaultException e2) {
        LOG.error("Error copying wave content", e2);
      }
    }

    if (gadgetUrl != WITHOUT_GADGET) {
      final Gadget gadget = new Gadget(gadgetUrl.toString());
      rootBlip.append(gadget);
    }

    final OperationContextImpl context = new OperationContextImpl(waveletProvider,
        converterManager.getEventDataConverter(ProtocolVersion.DEFAULT), conversationUtil);
    for (final OperationRequest req : opQueue.getPendingOperations()) {
      OperationUtil.executeOperation(req, operationRegistry, context, user);
      final String reqId = req.getId();
      final JsonRpcResponse response = context.getResponse(reqId);
      if (response != null) {
        if (response.isError()) {
          onFailure(context.getResponse(reqId).getErrorMessage());
        } else {
          final Object responseWaveId = response.getData().get(ParamsProperty.WAVE_ID);
          final Object responseWaveletId = response.getData().get(ParamsProperty.WAVELET_ID);
          if (responseWaveId != null && responseWaveletId != null) {
            // This is serialized use
            // ApiIdSerializer.instance().deserialiseWaveId (see
            // WaveService)
            newWaveId = (String) responseWaveId;
            newWaveletId = (String) responseWaveletId;
          }
        }
      }
    }
    OperationUtil.submitDeltas(context, waveletProvider, new SubmitRequestListener() {
      @Override
      public void onFailure(final String arg0) {
        KuneWaveServiceDefault.this.onFailure("Wave creation failed, onFailure: " + arg0);
      }

      @Override
      public void onSuccess(final int arg0, final HashedVersion arg1, final long arg2) {
        LOG.info("Wave creation success: " + arg1);
      }
    });
    LOG.info("WaveId: " + newWaveId + " waveletId: " + newWaveletId);
    WaveRef wavename;
    try {
      wavename = WaveRef.of(ApiIdSerializer.instance().deserialiseWaveId(newWaveId),
          ApiIdSerializer.instance().deserialiseWaveletId(newWaveletId));
    } catch (final InvalidIdException e) {
      throw new DefaultException("Error getting wave id");
    }
    return wavename;
  }

  @Override
  public WaveRef createWave(@Nonnull final String title, final String message, final URL gadgetUrl,
      @Nonnull final ParticipantId... participantsArray) {
    return createWave(title, message, NO_WAVE_TO_COPY, gadgetUrl, participantsArray);
  }

  @Override
  public void delParticipants(final WaveRef waveName, final String whoDel,
      final String... participantsToDel) {
    final Wavelet wavelet = fetchWave(waveName, whoDel);
    final Participants currentParticipants = wavelet.getParticipants();
    final Set<String> set = toSet(participantsToDel);
    LOG.debug("Removing participants: " + set.toString());
    for (final String participant : set) {
      // FIXME Seems like only one participant per opQueue is added (try to fix
      // this in WAVE)
      final String partWithDomain = participantUtils.of(participant).toString();
      if (currentParticipants.contains(partWithDomain)) {
        final OperationQueue opQueue = new OperationQueue();
        LOG.debug("Removing as participant: " + partWithDomain);
        opQueue.removeParticipantFromWavelet(wavelet, partWithDomain);
        doOperation(whoDel, opQueue, "del participant");
      }
    }
  }

  private void doOperation(final String author, final OperationQueue opQueue, final String logComment) {
    // FIXME: do here a callback!!!
    final OperationContextImpl context = new OperationContextImpl(waveletProvider,
        converterManager.getEventDataConverter(ProtocolVersion.DEFAULT), conversationUtil);
    for (final OperationRequest request : opQueue.getPendingOperations()) {
      // final OperationRequest request = opQueue.getPendingOperations().get(0);
      OperationUtil.executeOperation(request, operationRegistry, context, participantUtils.of(author));
      final String reqId = request.getId();
      final JsonRpcResponse response = context.getResponse(reqId);
      if (response != null && response.isError()) {
        onFailure(context.getResponse(reqId).getErrorMessage());
      }
      OperationUtil.submitDeltas(context, waveletProvider, new SubmitRequestListener() {
        @Override
        public void onFailure(final String arg0) {
          KuneWaveServiceDefault.this.onFailure("Wave " + logComment + " failed, onFailure: " + arg0);
        }

        @Override
        public void onSuccess(final int arg0, final HashedVersion arg1, final long arg2) {
          LOG.info("Wave " + logComment + " success: " + arg1);
        }
      });
    }
  }

  public void doOperations(final WaveRef waveName, final String author, final OperationQueue opQueue,
      final SubmitRequestListener listener) {
    final OperationContextImpl context = new OperationContextImpl(waveletProvider,
        converterManager.getEventDataConverter(ProtocolVersion.DEFAULT), conversationUtil);
    for (final OperationRequest req : opQueue.getPendingOperations()) {
      OperationUtil.executeOperation(req, operationRegistry, context, participantUtils.of(author));
    }
    OperationUtil.submitDeltas(context, waveletProvider, listener);
  }

  @Override
  public Wavelet fetchWave(final WaveId waveId, final WaveletId waveletId, final String author) {
    final OperationQueue opQueue = new OperationQueue();
    opQueue.fetchWavelet(waveId, waveletId);
    Wavelet wavelet = null;
    final OperationContextImpl context = new OperationContextImpl(waveletProvider,
        converterManager.getEventDataConverter(ProtocolVersion.DEFAULT), conversationUtil);
    final OperationRequest request = opQueue.getPendingOperations().get(0);
    OperationUtil.executeOperation(request, operationRegistry, context, participantUtils.of(author));
    final String reqId = request.getId();
    final JsonRpcResponse response = context.getResponse(reqId);
    if (response != null && response.isError()) {
      onFailure(context.getResponse(reqId).getErrorMessage());
    } else {
      // Duplicate code from WaveService
      assert response != null;
      final WaveletData waveletData = (WaveletData) response.getData().get(ParamsProperty.WAVELET_DATA);
      final Map<String, Blip> blips = new HashMap<String, Blip>();
      final Map<String, BlipThread> threads = new HashMap<String, BlipThread>();
      wavelet = Wavelet.deserialize(opQueue, blips, threads, waveletData);

      // Deserialize threads.
      @SuppressWarnings("unchecked")
      final Map<String, BlipThread> tempThreads = (Map<String, BlipThread>) response.getData().get(
          ParamsProperty.THREADS);
      for (final Map.Entry<String, BlipThread> entry : tempThreads.entrySet()) {
        final BlipThread thread = entry.getValue();
        threads.put(entry.getKey(),
            new BlipThread(thread.getId(), thread.getLocation(), thread.getBlipIds(), blips));
      }

      // Deserialize blips.
      @SuppressWarnings("unchecked")
      final Map<String, BlipData> blipDatas = (Map<String, BlipData>) response.getData().get(
          ParamsProperty.BLIPS);
      for (final Map.Entry<String, BlipData> entry : blipDatas.entrySet()) {
        blips.put(entry.getKey(), Blip.deserialize(opQueue, wavelet, entry.getValue()));
      }
    }
    return wavelet;
  }

  @Override
  public Wavelet fetchWave(final WaveRef waveName, final String author) {
    final WaveId waveId = waveName.getWaveId();
    final WaveletId waveletId = waveName.getWaveletId();
    return fetchWave(waveId, waveletId, author);
  }

  @Override
  public boolean isParticipant(final Wavelet wavelet, final String user) {
    return wavelet.getParticipants().contains(participantUtils.of(user).toString());
  }

  private void onFailure(final String message) {
    final String errorMsg = TextUtils.notEmpty(message) ? message : "Wave operation failed";
    LOG.error(errorMsg);
    throw new DefaultException(errorMsg);
  }

  @Override
  public String render(final Wavelet wavelet) {
    final ClientAction clientPage = waveRenderer.render(wavelet, 0);
    final String html = clientPage.getHtml();
    return html;
  }

  @Override
  public String render(final WaveRef waveRef, final String author) {
    return render(fetchWave(waveRef, author));
  }

  @Override
  public void setGadgetProperty(final WaveRef waveletName, final String author, final URL gadgetUrl,
      final String someProperty, final String someValue) {
    // See BlipContentRefs DocumentModifyService
    final Wavelet wavelet = fetchWave(waveletName, author);
    // FIXME
    final OperationQueue opQueue = new OperationQueue();
    final Blip rootBlip = wavelet.getRootBlip();
    for (final Element elem : rootBlip.getElements().values()) {
      if (elem.isGadget()) {
        final Map<String, String> properties = elem.getProperties();
        if (properties.get(Gadget.URL).equals(gadgetUrl.toString())) {
          // This is the gadget we want to modify (the first of that type)
          final List<Element> updatedElementsIn = Lists.newArrayListWithCapacity(1);
          properties.put(someProperty, someValue);
          // properties.put(propertyNameToDelete, null);
          final Gadget gadget = (Gadget) elem;
          gadget.setProperty(someProperty, someValue);
          // updatedElementsIn.add(new Gadget(properties));
          updatedElementsIn.add(gadget);
          final OperationRequest operationRequest = opQueue.modifyDocument(rootBlip);
          operationRequest.addParameter(Parameter.of(ParamsProperty.MODIFY_ACTION,
              new DocumentModifyAction(ModifyHow.UPDATE_ELEMENT, NO_VALUES, NO_ANNOTATION_KEY,
                  updatedElementsIn, NO_BUNDLED_ANNOTATIONS, false)));
          operationRequest.addParameter(Parameter.of(
              ParamsProperty.MODIFY_QUERY,
              new DocumentModifyQuery(ElementType.GADGET, ImmutableMap.of(Gadget.URL,
                  gadgetUrl.toString()), 1)));
          doOperation(author, opQueue, "set gadget property");
          break;
        }
      }
    }
  }

  @Override
  public void setTitle(final WaveRef waveName, final String title, final String author) {
    final Wavelet wavelet = fetchWave(waveName, author);
    final OperationQueue opQueue = new OperationQueue();
    opQueue.setTitleOfWavelet(wavelet, title);
    doOperation(author, opQueue, "set title");
  }

  private Set<String> toSet(final String[] array) {
    final Set<String> set = new TreeSet<String>(Collections.reverseOrder());
    set.addAll(Arrays.asList(array));
    return set;
  }
}
