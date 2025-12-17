package com.marotech.recording.ws;

import com.marotech.recording.config.Config;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AudioStreamHandler extends BinaryWebSocketHandler {

    @Autowired
    private Config config;

    private final Map<String, ByteArrayOutputStream> buffers = new ConcurrentHashMap<>();
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private String saveDir;

    @PostConstruct
    public void setParameters() {
        saveDir = config.getProperty("app.audio.storage.path");
        LOG.info("Audio storage directory set to: {}", saveDir);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String mobileNumber = getMobileNumberFromHeaders(session);
        if (mobileNumber != null) {
            sessions.put(mobileNumber, session);
            LOG.info("Session established for mobile: {}", mobileNumber);
        } else {
            LOG.warn("No mobile number found in headers for session: {}", session.getId());
        }
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        String sessionId = session.getId();
        buffers.computeIfAbsent(sessionId, k -> new ByteArrayOutputStream())
                .write(message.getPayload().array(), 0, message.getPayload().array().length);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();
        ByteArrayOutputStream baos = buffers.remove(sessionId);

        // Remove from sessions map too
        sessions.entrySet().removeIf(entry -> entry.getValue().getId().equals(sessionId));

        if (baos != null) {
            String mobileNumber = getMobileNumberFromHeaders(session);
            String name = (mobileNumber != null ? mobileNumber : "unknown") + "_" + sessionId;
            String filePath = saveDir + File.separator + name + ".wav";

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                baos.writeTo(fos);
                LOG.info("Audio saved to: {}", filePath);
            } catch (IOException e) {
                LOG.error("Failed to save audio for session {} to {}", sessionId, filePath, e);
            }
        }
        super.afterConnectionClosed(session, status);
    }

    private String getMobileNumberFromHeaders(WebSocketSession session) {
        try {
            return session.getHandshakeHeaders().get("Mobile-Number") != null
                    ? session.getHandshakeHeaders().get("Mobile-Number").get(0)
                    : null;
        } catch (Exception e) {
            LOG.warn("Error extracting mobile number from headers", e);
            return null;
        }
    }

    public void playBackAudio(String mobileNumber, String fileName) {
        WebSocketSession session = sessions.get(mobileNumber);
        if (session != null && session.isOpen()) {
            try (InputStream inputStream = new FileInputStream(fileName)) {
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byte[] chunk = Arrays.copyOf(buffer, bytesRead);
                    BinaryMessage message = new BinaryMessage(chunk);
                    session.sendMessage(message);
                    Thread.sleep(20); // Pace playback
                }
                LOG.info("Playback completed for mobile: {}", mobileNumber);
            } catch (IOException | InterruptedException e) {
                LOG.error("Failed to stream audio playback for file: {}", fileName, e);
                if (e instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
            }
        } else {
            LOG.warn("No active session found for mobile number: {}", mobileNumber);
        }
    }
    private static final Logger LOG = LoggerFactory.getLogger(AudioStreamHandler.class);

    public WebSocketSession getSession(String mobileNumber) {
        return sessions.get(mobileNumber);
    }
}
