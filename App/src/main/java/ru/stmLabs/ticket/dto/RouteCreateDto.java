package ru.stmLabs.ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.stmLabs.ticket.model.Carrier;

@Schema(description = "Информация о билете")
public class RouteCreateDto {
    @Schema(description = "Пункт отправления", example = "Москва")
    private String origin;

    @Schema(description = "Пункт назначения", example = "Санкт-Петербург")
    private String destination;

    @Schema(description = "Перевозчик")
    private Carrier carrier;

    @Schema(description = "Длительность в мин", example = "100")
    private int time;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
