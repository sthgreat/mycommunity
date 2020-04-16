package com.dzkjdx.mycommunity_dbservice.Enum;

import lombok.Getter;

@Getter
public enum MsgStatus {
    SENDED("1"),

    INQUEUE("2"),

    NEEDRESEND("3"),

    SUCCESS("4"),

    NEEDRECONSUME("5");


    String status;

    MsgStatus(String integer) {
        this.status = integer;
    }
}
