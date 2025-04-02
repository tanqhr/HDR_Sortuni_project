package bg.softuni.hdr_rest_server.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {
    private Long id;

    private String name;


    private String email;

    private String text;
}
