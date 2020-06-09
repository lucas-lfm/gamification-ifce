package io.github.lucasifce.gamification.domain.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class NegocioListException extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Getter
    @Setter
    private List<String> erros;

    public NegocioListException(String msg){
        super(msg);
    }

    public NegocioListException(List<String> listaMsgErros, String msg){
        super(msg);
        this.erros = listaMsgErros;
    }
}
