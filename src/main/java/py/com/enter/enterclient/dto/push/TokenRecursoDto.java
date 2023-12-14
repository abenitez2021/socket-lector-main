package py.com.enter.enterclient.dto.push;

public class TokenRecursoDto {
    private String kwfxtoken;
    private Integer idRecurso;

    public String getKwfxtoken(){
        return kwfxtoken;
    }

    public void setKwfxtoken(String kwfxtoken) {
        this.kwfxtoken = kwfxtoken ;
    }

    public Integer getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(Integer idRecurso) {
        this.idRecurso = idRecurso;
    }
}
