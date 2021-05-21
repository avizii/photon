package com.avizii.glint.datasource;

/**
 * @Author : Avizii
 * @Create : 2021.05.21
 */
public interface DataSource {

    String fullFormat();

    String shortFormat();

/*    default String aliasFormat() {
        return shortFormat();
    }*/
}
