package com.crio.rentRead.exchanges;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserRequest {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
    
}
