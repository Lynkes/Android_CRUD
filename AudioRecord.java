package com.exemplocrud;
import android.media.MediaRecorder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
public class AudioRecord {
    // Inicialize o AudioRecord
    int bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);
    AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfig, audioFormat, bufferSize);

    // Inicialize o Socket para o servidor
    String serverIp = "192.168.1.100";  // Substitua pelo IP do seu servidor
    int serverPort = 12345;  // Substitua pela porta do seu servidor
    Socket socket;

    {
        try {
            socket = new Socket(serverIp, serverPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    OutputStream outputStream;

    {
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    byte[] buffer = new byte[bufferSize];
audioRecord.startRecording();

while (isRecording) {
        int bytesRead = audioRecord.read(buffer, 0, bufferSize);
        outputStream.write(buffer, 0, bytesRead);
    }
// Ao parar de gravar ou quando desejar enviar o áudio completo
audioRecord.stop();
audioRecord.release();
outputStream.close();
socket.close();
}
