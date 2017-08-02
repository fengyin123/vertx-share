package io.vertx.blog.first;

import io.vertx.core.Vertx;

/**
 * Created by ruhou on 17-8-1.
 */
public class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle("io.vertx.blog.first.MyFirstVerticle");
    }
}
