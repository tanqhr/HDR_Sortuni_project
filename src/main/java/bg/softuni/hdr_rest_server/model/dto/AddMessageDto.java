package bg.softuni.hdr_rest_server.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMessageDto {


    private String name;


    private String email;


    private String text;
}
