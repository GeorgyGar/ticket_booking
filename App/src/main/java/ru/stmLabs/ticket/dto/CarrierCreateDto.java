package ru.stmLabs.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Информация о перевозчике")
public class CarrierCreateDto {
    @Schema(description = "Название перевозчика", example = "РЖД")
    private String carrierName;

    @Schema(description = "Номер телефона", example = "+7-900-123-45-67")
    private String phone;

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
