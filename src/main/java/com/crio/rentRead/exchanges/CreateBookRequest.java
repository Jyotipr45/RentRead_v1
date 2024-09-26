package com.crio.rentRead.exchanges;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    @NotEmpty
    private String genre;

    @NotEmpty
    private String availabilityStatus;
}
