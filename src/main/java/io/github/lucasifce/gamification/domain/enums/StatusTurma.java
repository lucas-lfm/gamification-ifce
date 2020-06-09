package io.github.lucasifce.gamification.domain.enums;

import java.util.Arrays;

public enum StatusTurma {

    ATIVO,
    ARQUIVADO,
    FINALIZADO;

    public static boolean verificarStatus(String status){
        for(StatusTurma stt : StatusTurma.values()){
            if(stt.toString().equalsIgnoreCase(status)){
                return true;
            }
        }
        return false;
    }

}
