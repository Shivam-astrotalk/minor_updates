package com.astrotalk.live.agora;

public interface PackableEx extends Packable {
    void unmarshal(ByteBuf in);
}
