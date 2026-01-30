package uce.edu.web.api.matricula.application.representation;

public class LinkDto {
    private String href;
    private String rel;

    public LinkDto() {
    }

    public LinkDto(String href, String rel) {
        this.href = href;
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

}