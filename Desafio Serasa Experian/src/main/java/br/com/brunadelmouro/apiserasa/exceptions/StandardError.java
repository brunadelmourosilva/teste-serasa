package br.com.brunadelmouro.apiserasa.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardError {

    private String message;

    @JsonFormat(pattern = "dd/MM/yyyy H:mm:ss", locale = "pt_BR", timezone = "Brazil/East")
    private Date timestamp;
}
