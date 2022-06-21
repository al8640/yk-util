package com.yangke.base.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


/**
 * @author cuiguibin
 */

@NoArgsConstructor
@AllArgsConstructor
public enum AesEnum {
    /**
     * 账户业务key及iv
     */
    AES_ACCOUNT("Db84oY7RmD35uo4M", "5955807308184266");
    private String key;
    private String iv;

    public String getKey() {
        return key;
    }

    public String getIv() {
        return iv;
    }
}
