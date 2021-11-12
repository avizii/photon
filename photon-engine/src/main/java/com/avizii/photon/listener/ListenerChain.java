package com.avizii.photon.listener;

import java.util.LinkedList;
import java.util.List;

/**
 * 责任链模式处理Antlr Listener
 *
 * @author : Avizii
 * @create : 2021.05.21
 */
public class ListenerChain {

  private final List<GlintHandler> handlerList = new LinkedList<>();

  public void addHandle(GlintHandler handler) {
    handlerList.add(handler);
  }

  public void handle() {
    handlerList.forEach(GlintHandler::handle);
  }

  public static ListenerChain of() {
    ListenerChain chain = new ListenerChain();
    chain.addHandle(new IncludeProcessListener());
    chain.addHandle(new PreProcessListener());
    chain.addHandle(new GrammarProcessListener());
    chain.addHandle(new AuthProcessListener());
    chain.addHandle(new ScriptProcessListener());
    return chain;
  }

  public static void main(String[] args) {
    ListenerChain chain = ListenerChain.of();
    for (GlintHandler handler : chain.handlerList) {
      System.out.println(handler.getClass().getSimpleName());
    }
  }
}
