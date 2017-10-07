package dentist.office.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DentalService {

    @JsonProperty("Przegląd zębów")
    CHECK_UP,
    @JsonProperty("Usuwanie kamienia nazębnego")
    SCALING,
    @JsonProperty("Leczenie zębów")
    TEETH_TREATMENT,
    @JsonProperty("Wypełnianie zębów")
    TEETH_FILLING,
    @JsonProperty("Licówki materiał Gradia Direct")
    VENEERS,
    @JsonProperty("Protezy akrylowe")
    ACRYLIC_DENTURES,
    @JsonProperty("Protezy szkieletowe")
    SKELETAL_DENTURES,
    @JsonProperty("Protezy elastyczne")
    ELASTIC_DENTURES,
    @JsonProperty("Protezy szkieletowo-elastyczne")
    SKELETAL_ELASTIC_DENTURES,
    @JsonProperty("Inne")
    OTHER;

}
