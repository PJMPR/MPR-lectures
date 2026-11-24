package pl.edu.pjatk.mpr.convention.program.domain;
public record ExternalPersonId(String value) {
    public ExternalPersonId { if (value == null || value.isBlank()) throw new IllegalArgumentException("External person id is required"); }
}
