package de.seipler.network.chat;

import java.nio.channels.SocketChannel;

class NioServerDataEvent {
  
  public NioChatServer server;
  public SocketChannel socket;
  public byte[] data;
  
  public NioServerDataEvent(NioChatServer server, SocketChannel socket, byte[] data) {
    this.server = server;
    this.socket = socket;
    this.data = data;
  }
  
}